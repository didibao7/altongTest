<?
if (getenv('REMOTE_ADDR') != '127.0.0.1') exit;
include(getenv('DOCUMENT_ROOT').'/common/lib/DataBase.alt');

if ($_POST['ACT'] == "PAY_HISTORY")
{   

	$maxRow = 20;
	$pg = (int)$_POST['pg'];
	$orderby = ($_POST['H_sort'] == 'new') ? 'desc' : 'asc';

	$sql = "
		SELECT alpay_text, alpay_amount, alpay_balance, convert(varchar(10),regdate,21) as condate
		FROM T_STORE_PAY_LOG
		WHERE user_seq=? and regdate>dateadd(month, -6, getdate())
		order by user_seq $orderby, regdate $orderby
		OFFSET ? ROWS
		FETCH NEXT ? ROWS ONLY;
	";
	$params = array($_POST['UserSeq'], $maxRow * $pg, $maxRow);
	$r = libRowsSql('altong', $sql, $params);
	

	if (!$r) $r = array();
	libJsonExit($_POST['ACT'], array('rows'=>$r, 'pg'=>$_POST['ACT'], 'pg'=>$_POST['pg'], 'H_sort'=>$_POST['H_sort']));
}

libJsonExit('');

?>
