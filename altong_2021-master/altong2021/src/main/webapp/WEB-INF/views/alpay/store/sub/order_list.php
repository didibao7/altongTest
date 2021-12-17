<?
if (getenv('REMOTE_ADDR') != '127.0.0.1') exit;
include(getenv('DOCUMENT_ROOT').'/common/lib/DataBase.alt');



$userSeq = (int)$_POST['UserSeq'];
$Almaeng = (int)$_POST['Almaeng'];
$AlmaengCode = (int)$_POST['AlmaengCode'];


if ($_POST['ACT'] == "ORDER_NEW")
{   
	$sql = "
		select a.pay_seq, b.NickName as user_nickname, a.alpay_amount, convert(varchar(16), a.regdate, 21) as regdate
		from T_STORE_PAY_PROG a LEFT OUTER JOIN T_MEMBERS b on a.user_seq=b.Seq
		where store_code=? and step=1";
	$params = array($AlmaengCode);
	$r = libRowsSql('altong', $sql, $params);

	if (!$r) $r = array();
	libJsonExit($_POST['ACT'], $r);
}

if ($_POST['ACT'] == "ORDER_HISTORY")
{   
	$dyWhere = '';
	$dyParam = array();

	switch ($_POST['S_date'])
	{
		case 'weeks':
			$dyWhere = 'and a.regdate > dateadd(day,-7,getdate())';
			break;
		case 'month':
			$dyWhere = 'and a.regdate > dateadd(month,-1,getdate())';
			break;
		case 'month_6':
			$dyWhere = 'and a.regdate > dateadd(month,-6,getdate())';
			break;
		case 'inputDate':
			$dyWhere = 'and a.regdate > convert(datetime,?,21) and a.regdate < convert(datetime,dateadd(day,1,?),21)';
			$dyParam[] = $_POST['S_input1'];
			$dyParam[] = $_POST['S_input2'];
			break;
		default:
			libJsonExit('');
	}

	$sql = "
		select a.pay_seq, b.NickName as user_nickname, a.alpay_amount, convert(varchar(16), a.regdate, 21) as regdate
		from T_STORE_PAY_ALMAENGLOG a LEFT OUTER JOIN T_MEMBERS b on a.user_seq=b.Seq
		where store_code=? $dyWhere and alpay_amount<0
		order by a.store_code desc, a.regdate desc";
	$params = array_merge(array($AlmaengCode), $dyParam);
	$r = libRowsSql('altong', $sql, $params);

	if (!$r) $r = array();
	libJsonExit($_POST['ACT'], $r);
}

// 신규요청 승인
if ($_POST['ACT'] == "ORDER_ACCEPT")
{
	$pay_seq = $_POST['pay_seq'];

	$sql = "select pay_seq, user_seq, alpay_amount from T_STORE_PAY_PROG where pay_seq=? and store_code=? and step=1";
	$params = array($pay_seq, $AlmaengCode);
	$r = libRowSql('altong', $sql, $params);
	if (!$r) libJsonExit("[주의]\n\n결제가 체결되지 않았습니다.\n승인 내역을 확인하시기 바랍니다.");

	$sql = "
		declare @isSuccess int = 0
		declare @store_money money

		begin try
			begin tran
				update T_STORE set store_money=store_money+(?) where store_code=?
				select @store_money=store_money from T_STORE where store_code=?
				insert into T_STORE_PAY_ALMAENGLOG(pay_seq, store_code, regdate, user_seq, alpay_amount, alpay_balance, employee_code)
					values(?, ?, getdate(), ?, ?, @store_money, ?)
				update T_STORE_PAY_PROG set step=2 where pay_seq=? and store_code=? and step=1
			commit tran
			set @isSuccess = 1
		end try
		begin catch
			rollback tran
		end catch

		select @isSuccess
	";
	$params = array(
		-$r['alpay_amount'], $AlmaengCode,
		$AlmaengCode,
		$pay_seq, $AlmaengCode, $r['user_seq'], -$r['alpay_amount'], $userSeq,
		$pay_seq, $AlmaengCode
	);
	$r = libColSql('altong', $sql, $params);
	if (!$r) libJsonExit('승인불가');

	libJsonExit($_POST['ACT']);
}

// 신규요청 거절
if ($_POST['ACT'] == "ORDER_REFUSE")
{
	$pay_seq = $_POST['pay_seq'];

	$sql = "update T_STORE_PAY_PROG set step=3 where pay_seq=? and store_code=? and step=1";
	$params = array($pay_seq, $AlmaengCode);
	libWriteSql('altong', $sql, $params);

	libJsonExit($_POST['ACT']);
}

libJsonExit('');

?>
