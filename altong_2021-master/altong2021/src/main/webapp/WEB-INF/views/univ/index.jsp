<?
/****************************************
	김태환 2019년 05월 14일
*****************************************/
include $_SERVER['DOCUMENT_ROOT'] . '/Common/Global.alt';
include $_SERVER['DOCUMENT_ROOT'] . '/Common/Lib/DataBase.alt';
include $_SERVER['DOCUMENT_ROOT'] . '/Common/Lib/Security.alt';
libLogin($_COOKIE['SESS']);
include $_SERVER['DOCUMENT_ROOT'] . '/Common/Lib/SecurityUniv.alt';



//$sql = "select Univ_Email from T_MEMBERS where Seq = ?";
//$r = libColSql('altong', $sql, array($GLOBALS['SESS']['UserSeq']));

?>
<? include(getenv('DOCUMENT_ROOT').'/Common/Include/Header.alt'); ?>
<style type="text/css">
	ul,
	li {
		list-style-type: none;
	}

	img,
	p,
	li {
		border: 0;
		margin: 0;
		padding: 0;
		vertical-align: middle;
	}

	.atm_memjoin_img {
		width: 45px;
		margin-left: 10px
	}

	.atm_memjoin_btn {
		background-color: #2ac1bc;
		padding: 15px 0;
		color: #fff;
		font-weight: bold;
		font-size: 14px;
		margin-top: 25px;
		text-align: center;
		margin: 20px 0px;
		border-radius: 5px;
	}

	.atm_celti_img {
		width: 15px;
		margin-right: 3px;
	}

	/*p태그*/

	.atm_memjoin_c1 {
		font-size: 12px;
		font-weight: bold;
		letter-spacing: -1px;
		margin: 7px 0 0;
		color: #8f8f8f;
		display: inline-block;
		left: 65px;
		top: 10px;
	}

	.atm_memjoin_c2 {
		font-size: 14px;
		font-weight: bold;
		letter-spacing: -1px;
		margin: 7px 0 5px;
		color: #5a5a5a;
	}

	.atm_memjoin_c3 {
		font-size: 16px;
		font-weight: bold;
		letter-spacing: -1.5px;
		padding: 15px 0;
		color: #5a5a5a;
		display: inline-block;
	}

	.atm_memjoin_c4 {
		font-size: 12px;
		font-weight: bold;
		letter-spacing: 0px;
		margin: 0 4px;
		color: #fff;
		background-color: #ff5001;
		padding: 10px 0;
		border-radius: 100px;
		display: inline-block;
		width: 120px;
	}

	.atm_memjoin_c5 {
		font-size: 12px;
		font-weight: bold;
		letter-spacing: 0px;
		margin: 0 4px;
		color: #fff;
		background-color: #2ac1bc;
		padding: 10px 0;
		border-radius: 100px;
		display: inline-block;
		width: 120px;
	}

	.atm_memjoin_c6 {
		font-size: 12px;
		font-weight: bold;
		letter-spacing: 0px;
		margin: 0 4px;
		color: #fff;
		background-color: #2ac1bc;
		padding: 10px 0;
		border-radius: 100px;
		display: inline-block;
		width: 150px;
	}

	.atm_memjoin_c_R {
		font-size: 12px;
		font-weight: bold;
		letter-spacing: -1px;
		color: #5a5a5a;
		position: absolute;
		right: 0;
		top: 2px
	}

	.atm_celti_ridio {
		display: none;
	}

	/*본문부분*/

	#atm_memjoin_wrapper0 {
		width: 100%;
		padding: 0 0 0 0;
		background-color: #f2f2f2;
	}

	.atm_memjoin_con {
		padding: 4px 14px 11px;
		text-align: left;
		position: relative;
		background-color: #f2f2f2;
	}

	.atm_memjoin_con_el {
		position: relative;
		background-color: #fff;
		margin-top: 7px;
		padding: 15px 14px;
	}

	.atm_memjoin_top {
		margin: 10px 10px;
		position: relative;
	}

	.atm_memjoin_btnG {
		margin-top: 20px;
		text-align: center;
	}

	.atm_memjoin_opt {
		margin: 30px 0;
		position: relative;
	}

	.atm_memjoin_opt_F {
		margin: 10px 0 20px;
		position: relative;
	}

	.atm_memjoin_opt_L {
		margin: 20px 0 10px;
		position: relative;
	}

	.atm_memjoin_input1 {
		font-size: 14px;
		border: 0;
		border-bottom: #d1d1d1 1px solid;
		width: 18%;
		padding: 5px 0 5px;
		display: inline-block;
		text-align: center;
		margin: 0 1%;
		font-weight: bold;
		color: #5a5a5a;
		vertical-align: middle;
	}

	.atm_memjoin_input2 {
		font-size: 14px;
		border: 0;
		border-bottom: #d1d1d1 1px solid;
		width: 17%;
		padding: 5px 0 5px;
		display: inline-block;
		text-align: center;
		margin: 0 1%;
		font-weight: bold;
		color: #5a5a5a;
	}

	.atm_memjoin_input3 {
		font-size: 14px;
		border: 0;
		border-bottom: #d1d1d1 1px solid;
		width: 42%;
		padding: 5px 0 0;
		display: inline-block;
		text-align: center;
		margin: 0 1%;
		font-weight: bold;
		color: #5a5a5a;
	}

	.atm_memjoin_input4 {
		font-size: 14px;
		border: 0;
		border: #d1d1d1 1px solid;
		width: 96%;
		padding: 5px 0 0;
		display: inline-block;
		text-align: center;
		margin: 0 1%;
		font-weight: bold;
		color: #a7a7a7;
	}

	.atm_memjoin_input5 {
		font-size: 14px;
		border: 0;
		border-bottom: #d1d1d1 1px solid;
		width: 96%;
		padding: 5px 0 0;
		display: inline-block;
		text-align: left;
		margin: 0 1%;
		font-weight: bold;
		color: #5a5a5a;
	}

	.atm_wq_c5_button_p {
		display: inline-block;
		width: 120px;
		margin: 0 4px;
		padding: 10px 0;
		border-radius: 100px;
		font-size: 12px;
		font-weight: bold;
		letter-spacing: 0px;
		text-align: center;
		color: #fff;
		background-color: #ff5001;
		cursor: pointer;
	}

	input[type="text"].atm_memjoin_input5::-webkit-input-placeholder {
		color: #8f8f8f;
	}

	input[type="text"].atm_memjoin_input5::-moz-placeholder {
		color: #8f8f8f;
	}

	input[type="text"].atm_memjoin_input5:-ms-input-placeholder {
		color: #8f8f8f;
	}

	input[type="text"].atm_memjoin_input5:-moz-placeholder {
		color: #8f8f8f;
	}

	input[type="text"].atm_memjoin_input5:focus {
		outline: 0;
	}

	.atm_memjoin_time_left {
		color: #2ac1bc;
		display: inline;
	}

	@media screen and (min-width: 800px) {
		.atm_memjoin_con {
			width: 500px;
			display: inline-block;
		}
		form {
			text-align: center;
		}
	}
	.atm_memjoin_opt select[name="Phone0"]{width:34%;letter-spacing: -0.5px;}
	.atm_memjoin_opt input[name="Phone4"]{display: none;}

	.Phone_notKorea select[name="Phone0"] {width:43%;padding-bottom:6px;font-size:13px;}
	.Phone_notKorea input[name="Phone4"] {width:51%;display: inline-block;}
	.Phone_notKorea select[name="Phone1"],
	.Phone_notKorea input[name="Phone2"],
	.Phone_notKorea input[name="Phone3"] {display: none;
</style>
<body>

<div id="atm_wrapper">
	<? include(getenv('DOCUMENT_ROOT').'/Common/Include/MenuSub.alt'); ?>
	<div id="atm_base_wrapper1" style="text-align:center">
		<!--wrapper start -->
		<div class="atm_graytop_ttbar">
			<p class="atm_graytop_c1">대학별 커뮤니티</p>
			<a href="javascript:void()" onClick="history.back();">
				<img src="/Common/images/btn_back.png" class="atm_graytop_btn_L1" />
			</a>
		</div>
		<div class="atm_memjoin_con">
			<div class="atm_memjoin_con_el">
				<div class="atm_memjoin_opt">
					<div style="padding-bottom:10px">
						<p class="atm_memjoin_c2">대학별 커뮤니티 서비스는 현재 준비 중에 있사오니 학생 여러분의 너른 양해를 부탁드립니다. 빠른 시일 내에 선보일 수 있도록 최선을 다하겠습니다</p>
						<div class="panel panel-warning" style="margin-top:50px;display:none">
							<div class="panel-heading" style="text-align:center">
								인증 시 입력해 주신 메일 주소<br><br>
								<b><?=$r?></b>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--wrapper end -->
	</div>
	<? include(getenv("DOCUMENT_ROOT")."/Common/Include/MenuItem.alt"); ?>
</div>
</body>
</html>
