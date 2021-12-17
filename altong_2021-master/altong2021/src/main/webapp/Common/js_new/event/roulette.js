window.onload = function(){
	
	//var pArr = ["1000","5000","3000","10000","2000","30000"];
	var pArr = ["1000","3000","5000","1000","7000","10000"];
	var play = true;
	var gameCnt = 0;
		
	$.ajax({
		url: '/roulette/getTicketCount',
		type: 'post',
		async: false,
		success: function(jsonOrg) {
			var data = $.parseJSON(jsonOrg);
			
			gameCnt = data.count;
			//console.log('gameCnt : ' + gameCnt);
			
			const queCount = data.queCount;
			const ansCount = data.ansCount;
			const replCount = data.replCount;
			
			const hunCount = data.hunCount;
			const estiCount = data.estiCount;
			
			//console.log('queCount : ' + queCount);
			$('.ticket > span').html(gameCnt);
			
			$('#queCount').html(queCount);
			$('#ansCount').html(ansCount);
			$('#replCount').html(replCount);
			
			$('#hunCount').html(hunCount);
			$('#estiCount').html(estiCount);
		}
	});
	
	$('.start').click(function(){
		// T_ROULETTE_GAME 테이블에서 최고 당첨금 이력(종료 되지 않은 이용권중 이번주 내) 카운트 가져오기
		$.ajax({
			url: '/roulette/getTicketCount',
			type: 'post',
			async: false,
			success: function(jsonOrg) {
				var data = $.parseJSON(jsonOrg);
				
				gameCnt = data.count;
				
				if(play == true) {
					if(gameCnt > 0) {
						$('.ticket > span').html(gameCnt);
						play = false;
						rotation(); 
					}
					else {
						$('.pop2').show();
				
						function exit(){
							setTimeout(() => {
								$('.pop2').hide();
							}, 1000);
						}
						exit();
					}
				}
			}
		});
	});

	function rotation(){
		$(".roul").rotate({
		  angle:0, 
		  animateTo:360 * 5 + randomize(0, 360),
		  center: ["50%", "50%"],
		  easing: $.easing.easeInOutElastic,
		  callback: function(){ 
						var n = $(this).getRotateAngle();
						//console.log('n : ' + n);
						endAnimate(n);
					},
		  duration:5000
	   });
	}
	
	function ratation_double() {
		$(".roul").rotate({
		  angle:0, 
		  animateTo:60 * 1,
		  center: ["50%", "50%"],
		  easing: $.easing.easeInOutElastic,
		  callback: function(){ 
						var n = $(this).getRotateAngle();
						console.log('n : ' + n);
						endAnimate(n);
					},
		  duration:5000
	   });
	}

	function endAnimate($n){
		var n = $n;
		var real_angle = n%360 + 29;
		//console.log('real_angle : ' + real_angle);
		var part = Math.floor(real_angle/60);
		//console.log('part : ' + part);

		if(part < 1){
			part = 0;
		}
		else if(part == 6){
			part = 0;
		}
		else if(part == 7){
			part = 1;
		}
		else if(part == 8){
			part = 2;
		}
		else if(part == 9){
			part = 3;
		}
		else if(part == 10){
			part = 4;
		}
		else if(part == 11){
			part = 5;
		}
		
		var moeny = pArr[parseInt(part)];
        $('.pop1').show();
        $('.pop1 .popUp p span').text(moeny.replace(/\B(?=(\d{3})+(?!\d))/g, ",")+ getLangStr("jsm_0003"));

        function exit(){
            setTimeout(() => {
                $('.pop1').hide();
            }, 2000);
        }
        exit();
		
		$.ajax({
			url: '/roulette/setTicketUse',
			type: 'post',
			data: {money : moeny},
			async: false,
			success: function(jsonOrg) {
				var data = $.parseJSON(jsonOrg);
				
				gameCnt = data.count;
				
				$('.ticket > span').html(gameCnt);
				
				play = true;
			}
		});
	}

	function randomize($min, $max){
		return Math.floor(Math.random() * ($max - $min + 1)) + $min;
	}
};