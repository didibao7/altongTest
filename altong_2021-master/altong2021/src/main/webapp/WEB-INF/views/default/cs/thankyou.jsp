<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body style="background:#e6e6e6">
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
                            <p style="font-size:1.5em;font-weight:bold;line-height: 18px;" >고객센터</p>
                            <p style="font-size:2.2em;font-weight:bold;color:#154d88;line-height: 51px;">02-330-3000</p>
                            <p style="font-size:0.9em;line-height: 20px;">월~금 AM 10:00 ~ PM 5:00</p>
                            <p style="font-size:0.9em;line-height: 23px;">주말, 공휴일 휴무</p>
                        </div>
                    </div> 
                    <br/> 
                    <br/> 
                    <br/> 
                    <br/>
                    <div class="atm_cs_btn" style="margin-top:10%;width:100%;margin-left:3.5%;text-align: left;">
                        <div class="atm_phone_call_btn_wrapper cs_btn" onclick="location.href='tel:02-330-3000'">
                            <img class="cs_btn_img" src="/Common/images/cs/cs_call.png" ></img>
                            전화걸기
                        </div>
                        <div class="cs_btn" onclick="location.href='/default/cs/notice/notice?Page=1'">
                            <img src="/Common/images/cs/cs.png" class="cs_btn_img"></img>
                            공지사항
                        </div>
                        <div class="cs_btn" onclick="location.href='/default/userGuide'">
                            <img src="/Common/images/cs/cs_faq.png" class="cs_btn_img"></img>
                            FAQ
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