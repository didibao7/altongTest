<?
if (getenv('REMOTE_ADDR') != '127.0.0.1') exit;
include(getenv('DOCUMENT_ROOT').'/common/lib/DataBase.alt');



$userSeq = (int)$_POST['UserSeq'];
$Almaeng = (int)$_POST['Almaeng'];
$AlmaengCode = (int)$_POST['AlmaengCode'];


if ($_POST['ACT'] == "MEMBER_LIST")
{
	$sql = "select NickName from T_MEMBERS where Seq in (select store_employee from T_STORE_EMPLOYEE where store_code=?) order by NickName";
	$params = array($AlmaengCode);
	$r = libRowsSql('altong', $sql, $params);

	if (!$r) $r = array();
	libJsonExit($_POST['ACT'], $r);
}

if ($_POST['ACT'] == "MEMBER_ADD")
{
	if ($Almaeng != 1) libJsonExit('권한이 없습니다.');

	$sql = "
		declare @result int = 0
		declare @user_seq int

		select @user_seq=Seq from T_MEMBERS where NickName=?
		if @user_seq is null
			set @result = 1
		else if @user_seq=?
			set @result = 2

		if @result = 0
			if exists (select 1 from T_STORE_EMPLOYEE where store_code=? and store_employee=@user_seq)
				set @result = 3

		if @result = 0
			if exists (select 1 from T_STORE_EMPLOYEE where store_employee=@user_seq)
				set @result = 4

		if @result = 0
			if exists (select 1 from T_STORE where store_ceo=@user_seq)
				set @result = 4

		if @result = 0
			insert into T_STORE_EMPLOYEE(store_code, store_employee, regdate) values(?, @user_seq, getdate())

		select @result
	";
	$params = array($_POST['NickName'], $userSeq, $AlmaengCode, $AlmaengCode);
	$r = libColSql('altong', $sql, $params);

	switch ($r)
	{
		case 1:
			libJsonExit('닉네임이 존재하지 않습니다.');
		case 2:
			libJsonExit('본인을 추가할 수 없습니다.');
		case 3:
			libJsonExit('이미 등록된 닉네임입니다.');
		case 4:
			libJsonExit('다른 알맹이에 등록되어있습니다.');
	}

    libJsonExit($_POST['ACT'], array('NickName'=>$_POST['NickName']));
}

if ($_POST['ACT'] == "MEMBER_DEL")
{
	if ($Almaeng != 1) libJsonExit('권한이 없습니다.');

	$sql = "
		declare @result int = 0
		declare @user_seq int

		select @user_seq=Seq from T_MEMBERS where NickName=?
		if @user_seq is null
			set @result = 1

		if @result = 0
		begin
			delete from T_STORE_EMPLOYEE where store_code=? and store_employee=@user_seq
			if @@rowcount = 0
				set @result = 1
		end

		select @result
	";
	$params = array($_POST['NickName'], $AlmaengCode);
	$r = libColSql('altong', $sql, $params);

	switch ($r)
	{
		case 1:
			libJsonExit('닉네임이 존재하지 않습니다.');
	}

    libJsonExit($_POST['ACT']);
}


libJsonExit('');

?>
