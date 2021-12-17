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
	<link rel="stylesheet" type="text/css" href="/alpay/common/css/style.css?v=2.1">
	<link rel="stylesheet" type="text/css" href="/alpay/store/css/store.css?v=2">
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR" rel="stylesheet">
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
</head>

<script>
    $(document).ready(function () {
        fAjax('ACT=MEMBER_LIST');
    });
    
    function addMem() {
        var name = $('.member_set .add_member input[name="name"]').val();

        if (name == '') {
            alert('내용을 입력해주세요.');
        } else {
            fAjax('ACT=MEMBER_ADD&NickName='+name);
        }
    }

    function removeMem(removeBtn) {
        var name = $(removeBtn).closest('tr').find('td:first-child').text();
        var grade = $(removeBtn).closest('tr').find('td:nth-child(2)').text();
       
        if (confirm(name + '님을 삭제하시겠습니까?')) {
            fAjax('ACT=MEMBER_DEL&NickName='+name);
            $(removeBtn).closest('tr').addClass('remove');
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
                    case 'MEMBER_LIST':
                        {
                            $('.rowDynamicMember').remove();

                            for (var i = 0; i < r.arr.length; i++) {
                                var memberList = $('.member_set table tbody tr:nth-child(2)').clone();
                                memberList.addClass('rowDynamicMember');
                                memberList.find('td:first-child').text(r.arr[i].NickName);
                                memberList.css('display', 'table');
                                $('.member_set table tbody tr:last-child').before(memberList);
                            }
                            break;
                        }
                    case 'MEMBER_ADD':
                        {
                            var memberAdd = $('.member_set table tbody tr:nth-child(2)').clone();
                            memberAdd.find('td:first-child').text(r.arr.NickName);
                            $('.member_set table tbody tr:last-child').before(memberAdd);
                            memberAdd.css('display','table');
                            $('.member_set table tbody tr:last-child input').val("");
                            break;
                        }
                    case 'MEMBER_DEL':
                        {
                            $('.member_set table tbody tr.remove').remove();
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
            <h3>직원 관리</h3>
        </div>
    </header>
  
    <section class="member_set">
        <form action="">
            <table>
                <thead>
                    <tr>
                        <th>닉네임</th>
                        <th>추가/삭제</th>
                    </tr>
                </thead>
                <tbody>
                    <tr style="display:none">
                        <td><%=Session("UserNickName")%></td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr style="display:none">
                        <td></td>
                        <td><button type="button" onclick="removeMem(this);">삭제</button></td>
                    </tr>
                    <tr class="add_member">
                            <td><input type="text" name="name" placeholder="닉네임"></td>
                            <td><button type="button" onclick="addMem(this);">추가</button></td>
                        </tr> 
                    </tbody>
            </table>
        </form>
    </section>
	<div style="position:absolute;bottom:10px;width:100%;font-size:13px;color:#aaa;text-align:center">알페이 결제를 승인할 수 있는 직원을 추가/삭제합니다.</div>
</body>
</html>