<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<style>
@media screen and (min-width: 800px) {
.atm_btn_question {position:fixed;bottom:20px;left:50%;padding:0;margin-left:290px;width:200px;display:table}
}
</style>
<%--
	if ($libWriteURL)
		echo '<a href="${libWriteURL}"><img src="/common/images/btn_ask'.(strpos($libWriteURL,'QuestionWrite')!==false ? '':3).'.png" class="atm_btn_question" id="sticky"/></a>';
	else
		echo '<a href="/question/questionWrite"><img src="/Common/images/btn_ask.png" class="atm_btn_question" id="sticky"/></a>';
--%>

</body>
<!--2017.9.8 차건환 질문하기 아이콘 스크롤 작업-->
<script>
$('.atm_btn_question').fadeIn('slow');
$.fn.scrollBottom = function() { 
    return $(document).height() - this.scrollTop() - this.height(); 
};
<?/*
deadLine = $(window).height() - $(".atm_btn_question").position().top - $(".atm_btn_question").height()
$('#white-space-question').height(deadLine);
$(window).scroll(function() {
	if($(window).scrollBottom() <= deadLine)
		$('.atm_btn_question').fadeOut('fast');
	else
		$('.atm_btn_question').fadeIn('slow');
});
*/?>
</script>
<%@ include file="/Common/include/MenuItem.jsp" %>