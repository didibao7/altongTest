<?
if (getenv('REMOTE_ADDR') != '127.0.0.1') exit;
include(getenv('DOCUMENT_ROOT').'/common/lib/DataBase.alt');



if ($_POST['ACT'] == "NEAR_SEARCH")
{
	$Q_lat  = (float)$_POST['H_lat'];
	$Q_lon  = (float)$_POST['H_lon'];

	$sql = "
		select store_code, store_cate, store_name, store_tel, store_addr, store_lat, store_lon
		from T_STORE
		where is_use=1 and (store_lat between ? and ?) and (store_lon between ? and ?)
	";
	$params = array($Q_lat-0.004, $Q_lat+0.004, $Q_lon-0.005, $Q_lon+0.005);
	$r = libRowsSql('altong', $sql, $params);

	if (!$r) $r = array();
	libJsonExit($_POST['ACT'], $r);
}

if ($_POST['ACT'] == "FAV_ADD")
{
	if (!$_POST['F_code']) libJsonExit('');

	$sql = "select 1 from T_STORE_FAV where user_seq=? and store_code=?";
	$params = array($_POST['UserSeq'], $_POST['F_code']);
	$r = libRowSql('altong', $sql, $params);
	if ($r)  libJsonExit('');

	$sql = "insert into T_STORE_FAV(user_seq, store_code) values(?, ?)";
	$params = array($_POST['UserSeq'], $_POST['F_code']);
	libWriteSql('altong', $sql, $params);

	$r = array('store_code'=>$_POST['F_code']);
	libJsonExit($_POST['ACT'], $r);
}

if ($_POST['ACT'] == "FAV_DEL")
{
	if (!$_POST['F_code']) libJsonExit('');

	$sql = "delete from T_STORE_FAV where user_seq=? and store_code=?";
	$params = array($_POST['UserSeq'], $_POST['F_code']);
	libWriteSql('altong', $sql, $params);

	$r = array('store_code'=>$_POST['F_code']);
	libJsonExit($_POST['ACT'], $r);
}

if ($_POST['ACT'] == "GET_FAV")
{
	$sql = "select store_code from T_STORE_FAV where user_seq=?";
	$params = array($_POST['UserSeq']);
	$r = libRowsSql('altong', $sql, $params);

	if (!$r) $r = array();
	libJsonExit($_POST['ACT'], $r);
}



libJsonExit('');

?>
