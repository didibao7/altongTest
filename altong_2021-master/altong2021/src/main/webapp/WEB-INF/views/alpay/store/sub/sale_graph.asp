<!-- #include virtual = "/Common/Global.asp" -->
<!-- #include virtual = "/Common/Function/Fnc_CallPHP.asp" -->
<!-- #include virtual = "/Common/Function/Fnc_Common.asp" -->
<%


if Request.Form("ACT") <> "" then
	Fn_CallPHP(6000)
end if


%>
<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>알pay (알맹이)</title>
	<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="/alpay/common/css/style.css?v=2.1">
	<link rel="stylesheet" type="text/css" href="/alpay/store/css/store.css?v=2">
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="apple-touch-icon" sizes="57x57" href="/alpay/store/favicon/apple-icon-57x57.png">
	<link rel="apple-touch-icon" sizes="60x60" href="/alpay/store/favicon/apple-icon-60x60.png">
	<link rel="apple-touch-icon" sizes="72x72" href="/alpay/store/favicon/apple-icon-72x72.png">
	<link rel="apple-touch-icon" sizes="76x76" href="/alpay/store/favicon/apple-icon-76x76.png">
	<link rel="apple-touch-icon" sizes="114x114" href="/alpay/store/favicon/apple-icon-114x114.png">
	<link rel="apple-touch-icon" sizes="120x120" href="/alpay/store/favicon/apple-icon-120x120.png">
	<link rel="apple-touch-icon" sizes="144x144" href="/alpay/store/favicon/apple-icon-144x144.png">
	<link rel="apple-touch-icon" sizes="152x152" href="/alpay/store/favicon/apple-icon-152x152.png">
	<link rel="apple-touch-icon" sizes="180x180" href="/alpay/store/favicon/apple-icon-180x180.png">
	<link rel="icon" type="image/png" sizes="192x192" href="/alpay/store/favicon/android-icon-192x192.png">
	<link rel="icon" type="image/png" sizes="32x32" href="/alpay/store/favicon/favicon-32x32.png">
	<link rel="icon" type="image/png" sizes="96x96" href="/alpay/store/favicon/favicon-96x96.png">
	<link rel="icon" type="image/png" sizes="16x16" href="/alpay/store/favicon/favicon-16x16.png">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript" src="/alpay/common/js/common.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.js"></script>
</head>

<script>
	function searchDate(type) {
		if (type == 'inputDate') {
			var input_1 = $('#datePicker1').val();
			var input_2 = $('#datePicker2').val();
			fAjax('ACT=SALE_GRAPH&S_date=' + type + '&S_input1=' + input_1 + '&S_input2=' + input_2);
		} else {
			fAjax('ACT=SALE_GRAPH&S_date=' + type);
		}
	}

	function fAjax(param) {
		if (document.xhr) {
			$('#Tip').text('이전 검색이 진행중입니다.').css('display', 'block');
			setTimeout(function () { $('#Tip').stop().fadeOut(300) }, 3000);
			return;
		}

		document.xhr = $.ajax({
			type: 'post',
			url: '<%=Request.ServerVariables("SCRIPT_NAME") %>',
			data: param,
			dataType: 'json',
			success: function (r) {
				switch (r.result) {
					case 'SALE_GRAPH':
						{
							
							break;
						}
					default:
						if (r.result) alert(r.result);
						break;
				}
			},
			error: function (r, textStatus, err) {
				if (r.responseText && r.responseText.substr(0, 13) == '<!--LOGOFF-->') { top.location = '<%=MORMAL_SEND_URL%>'; return; }

				alert('서버와의 통신에 실패하였습니다.');
				var str = '';
				for (var key in r) str += key + '=' + r[key] + '\n';
				console.log(str);
			},
			complete: function () { document.xhr = false; }
		});
	}
</script>

<body>
	<header>
		<div>
			<p><a href="/alpay/store/AlpayStore.asp"><i class="material-icons">arrow_back</i></a></p>
		</div>
		<div>
			<h3>매출 확인</h3>
		</div>
	</header>

	<section class="sale_graph">
		<div class="tab_dateSearch">
			<form action="">
				<button type="button" class="active" onclick="dateSearchBtn(this);searchDate('day');">일별</button>
				<button type="button" onclick="dateSearchBtn(this);searchDate('week');">주별</button>
				<button type="button" onclick="dateSearchBtn(this);searchDate('month');">월별</button>
				<button type="button" class="date_input" onclick="dateSearchBtn(this);searchDate('year');">년별</button>
			</form>
		</div>
		<div class="dateSearch_input">
			<form action="">
				<p><input type="date" id="datePicker1" min="2000-01-01"></p>
				<span>~</span>
				<p><input type="date" id="datePicker2" min="2000-01-01"></p>
				<button type="button" onclick="searchDate('inputDate');"><i class="material-icons">search</i></button>
			</form>
		</div>
		<script>
			document.getElementById('datePicker1').valueAsDate = new Date();
			document.getElementById('datePicker2').valueAsDate = new Date();

			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth() + 1; //January is 0!
			var yyyy = today.getFullYear();
			if (dd < 10) {
				dd = '0' + dd
			}
			if (mm < 10) {
				mm = '0' + mm
			}

			today = yyyy + '-' + mm + '-' + dd;
			document.getElementById('datePicker1').setAttribute("max", today);
			document.getElementById('datePicker2').setAttribute("max", today);

		</script>

		<div class="graph_wrap">
			<canvas id="line_graph"style="position: relative; height:40vh; width:80vw"></canvas>
			<script>
				var ctx = document.getElementById("line_graph");
				var myChart = new Chart(ctx, {
					type: 'line',
					data: {
						labels: ["10/1", "10/2", "10/3", "10/4", "10/5", "10/6"],
						datasets: [{
							label: '매출',
							data: [10, 15, 22, 15.2, 18, 16.5],
							backgroundColor: [
								'rgba(255, 99, 132, 0.2)',
								'rgba(54, 162, 235, 0.2)',
								'rgba(255, 206, 86, 0.2)',
								'rgba(75, 192, 192, 0.2)',
								'rgba(153, 102, 255, 0.2)',
								'rgba(255, 159, 64, 0.2)'
							],
							borderColor: [
								'rgba(255,99,132,1)',
								'rgba(54, 162, 235, 1)',
								'rgba(255, 206, 86, 1)',
								'rgba(75, 192, 192, 1)',
								'rgba(153, 102, 255, 1)',
								'rgba(255, 159, 64, 1)'
							],
							borderWidth: 1,
							fill : false,
							lineTension : 0
						}]
					},
					options: {
						scales: {
							yAxes: [{
								type: 'linear',
								scaleLabel : {
									display:true,
									labelString: '(단위:만원)'
								},
								ticks: {
									beginAtZero: true,
									
									// callback: function (value, index, values) {
									// 	return value;
									// },
									min : 8,
									max : 24
								}
							}]
						}
					}
				});

				function fAddComma(v) {
						v = String(v);
						return v.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
					}
			</script>
		</div>
	</section>

	<div style="position:absolute;bottom:10px;width:100%;font-size:13px;color:#aaa;text-align:center">아직 지원되지 않습니다.</div>
</body>

</html>