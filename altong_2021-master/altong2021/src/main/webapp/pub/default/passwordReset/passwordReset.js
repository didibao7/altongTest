$('.sendBtn').on('click', function() {
    const $data = $('form[name="pw_frm_sch"]');
    if ( $data.find('input[name="phone"]').val().length == "") {
        alert(getLangStr("jsm_0023"));
        return;
    } else if($data.find('select[name="country"]').val() == "82" && $data.find('input[name="phone"]').val().length !== 11) {
        alert(getLangStr("jsm_0024"))
        return;
    }
    $.ajax({
        url: '/default/password/passwordResetProcess',
        data: $data.serialize(),
        success: function(data) {
            switch(data) {
                case '0':
                case '3':
                    alert(getLangStr("jsm_0025") + '\n' +  getLangStr("jsm_0026"));
                    break;
                case '1':
                    alert(getLangStr("jsm_0027"))
                    location.replace('/default/password/passwordResetOk')
                    break;
                case '2':
                    alert(getLangStr("jsm_0028"));
                    break;
                default:
                    alert(getLangStr("jsm_0029") + '\n' +  getLangStr("jsm_0030"))
                    break;
            }
        }
    });
});