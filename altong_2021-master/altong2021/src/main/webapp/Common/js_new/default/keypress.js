// 사운드 쿠키 확인 
// return [쿠키 존재여부 true/false,쿠키 value];
var cookiename = "keypresssound"; // 쿠키 이름
var checkSound = null;
var audio1 = null, audio2 = null,audio3=null,audio4=null,audio5=null,audio6=null;

$(document).ready(function(){
    checkSound = CheckSoundCkookie(); // 배열로 받아온다 [쿠키존재여부,쿠키 value]
	var tmp = $('#slide_menu li:nth-child(10) a');
	var content=$('#keycontents');
    if (checkSound[0] == false) {
    } else {
        if (checkSound[1] != 'true') {
            tmp.addClass("keysoundoff");
            tmp.removeClass("keysoundon");
            content.text("Keysound off");
        } else {
            tmp.addClass("keysoundon");
            tmp.removeClass("keysoundoff");
            content.text("Keysound on");
        }
    }
});

function CheckSoundCkookie() {
    cookie_value = null; // 쿠키 value;

    cookie_exist = false;
    tmp = document.cookie || '';

    if (document.cookie == "") {
        return [cookie_exist,cookie_value];    
    }
    cookie_sp = tmp.split(';');
    for (i = 0 ; i < cookie_sp.length ; i++) {
        var c_sp_i = cookie_sp[i].split('=');
        
        if (cookiename == c_sp_i[0].trim()){
            cookie_exist = true;
            cookie_value = c_sp_i[1];
        }
    }
    return [cookie_exist,cookie_value];
}

// 버튼 토글

function togglecookie(obj) {
    console.log(obj.childNodes);
    checkSound = CheckSoundCkookie(); // 배열로 받아온다 [쿠키존재여부,쿠키 value]
    if (checkSound[0] == false) {
        alsetCookie(cookiename, 'true', '150');
    } else {
        if (checkSound[1] == 'true') {
            alsetCookie(cookiename, 'false', '150');
            obj.classList.add("keysoundoff");
            obj.classList.remove("keysoundon");
            checkSound[1] = 'false';
            obj.childNodes[2].textContent = "Keysound off";
        } else {
            alsetCookie(cookiename, 'true', '150');
            obj.classList.add("keysoundon");
            obj.classList.remove("keysoundoff");
            checkSound[1] = 'true';
            obj.childNodes[2].textContent = "Keysound on";
        }
    }
    checkSound = CheckSoundCkookie();
}

function alsetCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
    return true;
}

function makeSoundDom() {
    var audio = null;
    for(i = 1 ; i <= 6 ; i++){
        audio = document.createElement('audio');
        audio.id        = 'audio' + i;
        audio.conrrols  = 'controls';
        audio.src       = '/Common/ks/audio' + i + '.mp3';
        audio.type      = 'audio/mpeg';
        audio.style.display = 'none';
        
        document.querySelector('body').appendChild(audio);
    }
    audio1 = document.getElementById('audio1');
    audio2 = document.getElementById('audio2');
    audio3 = document.getElementById('audio3');
    audio4 = document.getElementById('audio4');
    audio5 = document.getElementById('audio5');
    audio6 = document.getElementById('audio6');

}


function presssound(){
    if(!checkSound || checkSound[1] == 'false') {
        return false;
    }
    /* Audio */
    if (event.keyCode=='13') {
        audio1.play();
    } else if (event.keyCode=='32') {
        audio2.play();
    } else if (event.keyCode=='8'){
        audio3.play();
    } else {
        if(Math.floor(Math.random() * 2) == 0)
        {
            audio4.play();
            audio5.play();
        }
        else{
            audio6.play();
        }
    }
}


(function() {
    
	function _al_start() {
		if ( document.readyState === "complete" ) {
			if (!document.getElementById("audio1")) {
            	makeSoundDom();
            }
            checkSound = CheckSoundCkookie();
            if (checkSound[0] == false) {
                alsetCookie(cookiename, 'false', '150');
                checkSound[0] = false;
                checkSound[1] = false;
            }

            checkSound = CheckSoundCkookie();
            
            /*
            keysoundToggleBtn = document.getElementById('keysoundToggleBtn');

            if (checkSound[1] == 'true') {
                keysoundToggleBtn.className = "keysoundon";
            } else {
                keysoundToggleBtn.className = "keysoundoff";
            }
            */

		} else {
			return setTimeout(_al_start, 1 ); // 재귀
		}
	}
	
     _al_start();

})( window );

document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('input').forEach(function(iter){
        iter.addEventListener('keyup', presssound);
        iter.addEventListener('keypress', presssound);
    });
    document.querySelectorAll('textarea').forEach(function(iter){
       iter.addEventListener('keyup', presssound);
       iter.addEventListener('keypress', presssound);
   });
    document.querySelectorAll('.ql-editor').forEach(function(iter){
       iter.addEventListener('keyup', presssound);
       iter.addEventListener('keypress', presssound);
   });
});
