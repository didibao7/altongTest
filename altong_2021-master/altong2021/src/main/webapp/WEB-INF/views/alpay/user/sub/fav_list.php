<?
if (getenv('REMOTE_ADDR') != '127.0.0.1') exit;
include(getenv('DOCUMENT_ROOT').'/common/lib/DataBase.alt');

if ($_POST['ACT'] == "FAV_HISTORY")
{   
    // 10개씩만 DB에서 불러오기
	/*
	$sampleArr = array();
	$sampleArr[] = array(
        'store_name'=>'빽다방 구로 코오릉점',
		'store_addr'=>'서울 구로구 디지털로 30길 31 코오릉 디지털 빌란트2',
		'store_code'=>'2005',
	);
	$sampleArr[] = array(
        'store_name'=>'대한서림',
		'store_addr'=>'서울특별시 구로구 구로3동 819',
		'store_code'=>'2006',
	);
	$sampleArr[] = array(
        'store_name'=>'다보여안경',
		'store_addr'=>'서울특별시 구로구 구로동 1284',
		'store_code'=>'2007',
	);
	$sampleArr[] = array(
        'store_name'=>'커피에반하다',
		'store_addr'=>'서울특별시 구로구 구로동 222-3',
		'store_code'=>'2001',
	);

    libJsonExit($_POST['ACT'], $sampleArr);
	*/


	$sql = "
		select a.store_code, b.store_name, b.store_tel, b.store_addr
		from T_STORE_FAV a LEFT OUTER JOIN T_STORE b on a.store_code=b.store_code
		where a.user_seq=? and b.is_use=1
	";
	$params = array($_POST['UserSeq']);
	$r = libRowsSql('altong', $sql, $params);

	if (!$r) $r = array();
	libJsonExit($_POST['ACT'], $r);
}

if ($_POST['ACT'] == "FAV_DEL")
{
	if (!$_POST['F_code']) libJsonExit('');

	$sql = "delete from T_STORE_FAV where user_seq=? and store_code=?";
	$params = array($_POST['UserSeq'], $_POST['F_code']);
	libWriteSql('altong', $sql, $params);

	libJsonExit($_POST['ACT']);
}



libJsonExit('');

?>
