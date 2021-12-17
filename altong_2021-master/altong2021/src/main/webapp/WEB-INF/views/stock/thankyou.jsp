<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">

/*본문부분*/
.atm_base_wrapper1{ width:100%; background-color:#e6e6e6;}
.csWrapper{
    position: relative;
    background-color: #fff;
    margin-top: 1em;
    padding-top: 0.5em;
    border-bottom: #bbbbbb 1px solid;
}

.atm_cs_info{display:block;}
.atm_cs_btn{display:block;}
.atm_phone_number_wrapper{height:30%;width:50%;float:left;}
.cs_btn{padding:3%;height:30%; width:45%;border:1px solid black;border-radius:10px;display:inline-block;margin-right:2%;margin-bottom:4%;line-height: 27px;}
.cs_btn_img{height:2em;padding-right:10px;float:left;}

.test{border: 1px solid grey;}
@media screen and (min-width: 800px) {
#atm_wrapper {text-align : center;height:100%;}
.atm_base_wrapper1 {width : 100%;max-width:780px;margin:0 auto; }
}
</style>
</head>
<body style="background:#e6e6e6">
    <div id="atm_wrapper">

        <div id="atm_wrapper" style="overflow-x:hidden;">
        <%@ include file="/Common/include/MenuSub.jsp" %>
            <div class="atm_base_wrapper1">
                <!--wrapper start -->
                <div class="atm_edittop_ttbar" >
                    <div class="atm_edittop_ttbar_pc" >
                        <p class="atm_edittop_c1">청약 신청 완료</p>
                        <img src="/Common/images/btn_back_bold.png" class="atm_edittop_btn_L1" onclick="history.back()"/>
                    </div>
                </div>
                <!-- 추가할 곳 -->
                <div class="csWrapper">
                    <div class="atm_cs_info" style="width:100%">
                        <div class="atm_phone_number_wrapper" style="width:100%;text-align: center;margin-bottom:20px;padding:3%;">
                            <p style="font-size:1.8em;font-weight:bold;line-height: 18px;" >감사합니다.</p>
                            <p style="font-size:1.3em;font-weight:bold;line-height: 51px;">청약이 접수되었습니다!</p>
                            <p style="font-size:0.9em;color:red;line-height: 20px;">배정 기준은 접수 순이 아니라 청약 증거금 입금 순이므로<br>만약 입금 전일 경우에는 속히 입금하시기를 권해드립니다.</p>
<p style="font-size:1.0em;line-height: 33px;">(청약 증거금 납입 계좌: KB국민은행 0015-9017-328-964 (주)알통)</p>
                        </div>
                    </div> 
                    <br/> 
                    <br/> 
                    <br/> 
                    <br/>
                    <div class="atm_cs_btn" style="margin-top:10%;width:100%;margin-left:3.5%;text-align: left;">
                        <div class="cs_btn" onclick="location.href='/stock/index'">
                            <img src="/Common/images/cs/cs.png" class="cs_btn_img"></img>
                            첫 페이지로
                        </div>
                        <div class="atm_phone_call_btn_wrapper cs_btn" onclick="location.href='tel:02-330-3000'">
                            <img class="cs_btn_img" src="/Common/images/cs/cs_call.png" ></img>
                            전화로 문의
                        </div>
                        <div class="cs_btn" onclick="location.href='/default/joinRule'">
                            <img src="/Common/images/logo3.png" class="cs_btn_img"></img>
                            회원 가입
                        </div>
                        <!--
                        <div class="cs_btn" onclick="location.href='./Counselling.asp'">
                            <img src="/Common/Images/cs/cs_1.png" class="cs_btn_img"></img>
                            1:1 문의
                        </div>
                        -->
                        <!--
                        <div class="cs_btn">
                            <img src="/Common/Images/cs/cs.png" class="cs_btn_img"></img>
                            1:1 답변확인
                        </div>
                        -->
                    </div>
                </div>
                <%@ include file="/Common/include/MenuItem.jsp" %>
                <!--wrapper end -->
            </div>
        </div>
    </div>
    <iframe name="ifrm" width="100%" height="0" frameborder="0"></iframe>
    <script>
        if(!isMobile()) {
            document.getElementsByClassName('atm_phone_call_btn_wrapper')[0].style.display = 'none';
        }
    </script>
</body>
</html>