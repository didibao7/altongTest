<?
if (getenv('REMOTE_ADDR') != '127.0.0.1') exit;
include(getenv('DOCUMENT_ROOT').'/common/lib/DataBase.alt');



$userSeq = (int)$_POST['UserSeq'];
$Almaeng = (int)$_POST['Almaeng'];
$AlmaengCode = (int)$_POST['AlmaengCode'];


if ($_POST['ACT'] == "store_money")
{
	$sql = "select store_money from T_STORE where store_code=?";
	$params = array($AlmaengCode);
	$r = libColSql('altong', $sql, $params);
	$money = (int)$r;

	libJsonExit($_POST['ACT'], array('store_money'=>number_format($money), 'percentage'=>floor($money/100000*100).'%'));
}


libJsonExit('');

?>
