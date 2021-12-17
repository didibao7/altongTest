<?
if (getenv('REMOTE_ADDR') != '127.0.0.1') exit;
include(getenv('DOCUMENT_ROOT').'/common/lib/DataBase.alt');

if ($_POST['ACT'] == "SALE_GRAPH")
{   
	if (isset($_POST['S_date']))
	{
		// 기간 검색
		$search_date = $_POST['S_date'];

		if ($search_date == 'day')
		{
			// 일별
		}
		else if ($search_date == 'week')
		{
			// 주별
		}
		else if ($search_date == 'month')
		{
			// 월별
		}
		else if ($search_date == 'year')
		{
			// 년별
		}
		else if ($search_date == 'inputDate')
		{
			// 조건검색
			$search_input1 = $_POST['S_input1'];
			$search_input2 = $_POST['S_input2'];
		}
	}
    libJsonExit($_POST['ACT'], $sampleArr);
}

libJsonExit('');

?>
