<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/Common/include/headerAdmin.jsp" %>
<body>
<style type="text/css">
    .tg  {border-collapse:collapse;border-spacing:0;border-color:#999; width:100%;margin-bottom:2em;}
    .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#999;color:#444;background-color:#F7FDFA;}
    .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#999;color:#fff;background-color:#26ADE4;}
    .tg .tg-yw4l{vertical-align:top}
</style>
<script id="noticeRow" type="text/x-template">
    <tr>
		<td class="tg-yw4l" id="{seq}">{lang}</td>
        <td class="tg-yw4l" class="title">{title}</td>
        <td class="tg-yw4l">{contents}</td>
        <td class="tg-yw4l">{editTimeReg}</td>
        <td class="tg-yw4l" onclick="edit($(this).parent().children()[0].id)" style="cursor:default;">수정</td>
        <td class="tg-yw4l" onclick="location.href='/aadmin/notice/delNotice?Seq=' + $(this).parent().children()[0].id" style="cursor:default;">삭제</td>
    </tr>
</script>

<div id="wrapper">
	<div id="M_wrapper">
	<%@ include file="/Common/include/menuAdmin.jsp" %>
		<br>
		<div id="list">
			<div class="atm_wa_con">
                <table class="tg">
                    <tr>
                    	<th class="tg-yw4l">언어</th>
                        <th class="tg-yw4l">제목</th>
                        <th class="tg-yw4l">내용</th>
                        <th class="tg-yw4l">작성시간</th>
                        <th class="tg-yw4l">뭐라</th>
                        <th class="tg-yw4l">할까</th>
                    </tr>
                </table>
                <form id="newNotice" action="/aadmin/notice/writeNotice" method="post">
                	<input type="hidden" id="b_seq" name="Seq" />
                    <input type="text" id="b_title" name="Title" style="width:100%;margin-bottom:1em;" placeholder="제목">
                    <select name="lang" id="b_lang">
                    	<option value="ko" selected>한글</option>
                    	<option value="en">영어</option>
                    </select>
                    <textarea id="b_content" name="Content" rows="20" style="width:100%;" class="atm_wa_input2" maxlength="10000" placeholder="공지를 입력해 주세요."></textarea>
                </form>
                <button id="writeBtn">작성</button>
			</div>
		</div>
	</div>
</div>
</form>
<script>
    $('#writeBtn').on('click', function() {
        document.getElementById('newNotice').submit()
    });
    
    function edit(seq) {
    	//console.log('seq : ' + seq);
    	var page = '${page}';
        var noticeData = $.parseJSON(
                $.ajax({
                    async: false,
                    url: "/default/cs/notice/getNoticeAdmin?Page=" + page + "&Seq=" + seq,
                    contentType: "application/x-www-form-urlencoded; charset=utf-8"
                }).responseText
            );
        
        noticeData = noticeData[0]
        //console.log('noticeData : ' + noticeData);
		$('#b_seq').val(seq);
        
        $('#b_title').val(noticeData.title);
        //console.log('lang : ' + noticeData.lang);
        $("#b_lang").val(noticeData.lang).prop("selected", true);

        $('#b_content').val(noticeData.contents.replace(/(<br>|<br\/>|<br \/>)/g, '\r\n'));
    }
    
    (function() {
    	var page = '${page}';
        var noticeData = $.parseJSON(
                $.ajax({
                    async: false,
                    url: "/default/cs/notice/getNoticeAdmin?Page=" + page,
                    contentType: "application/x-www-form-urlencoded; charset=utf-8"
                }).responseText
            );
        const noticeCount = noticeData[0][0]['NoticeCount']
        const pagingSize = 30
        noticeData = noticeData[1]

        const $table = $('.tg')

        for(let i = 0; i < noticeData.length; ++i) {
            let htmlCode = $('#noticeRow').html()
            noticeData[i]['editTimeReg'] = noticeData[i]['editTimeReg'].substring(0, 16)
            noticeData[i]['contents'] = noticeData[i]['contents'].substring(0, 20) + '......'
            for(let key in noticeData[i]) {
                htmlCode = htmlCode.replaceAll('{' + key + '}', noticeData[i][key])
            }
            $table.append(htmlCode)
        }
    })();
</script>

</body>