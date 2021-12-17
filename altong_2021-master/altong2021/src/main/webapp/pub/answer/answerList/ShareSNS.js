// 사용할 앱의 JavaScript 키를 설정해 주세요.
$(function(){
	Kakao.init('efd4e6490f58a10c9322bada90293823');
	// 카카오링크 버튼을 생성합니다. 처음 한번만 호출하면 됩니다.

	var shareData = function(target) {
		var type = target.closest('.shareWrap').attr('data-type');
		var seq =  target.closest('.shareWrap').attr('data-seq');
		var recmd = target.closest('.shareWrap').attr('data-recmd');
		var Title = $('.question_box').find('.prgTitle').text();
		var url = "http://www.altong.com/answer/answerList?" + (type != 'Q' ? type : '') + "Seq=" + seq;
		//console.log("url : " + url)

		recmd ? url += "&recmd="+ recmd : "" ;

		if (type == "A") {
			Title += getLangStr("jsm_0014");
			var Content = target.closest('.answer_box').find('.answer_text').length > 0 ?
							target.closest('.answer_box').find('.answer_text').text() + "..." :
							target.closest('.answer_box').find('.prgContent_A').text() + "...";
		} else
			var Content = $('.question_box').find('.prgContent_Q').text();

		return {
			Title : Title,
			Content : Content,
			url : url
		};
	}

	// 트위터
	$('#shareWrap').find('.prgShareTwitter').click(function(){
		var datas = shareData($(this));
		var Htags = getLangStr("jsm_0015");

		$(this).attr('href', 'https://twitter.com/intent/tweet?text='+datas.Title+'&url='+encodeURIComponent(datas.url)+'&hashtags='+Htags);
	})

	// 네이버
	$('#shareWrap').find('.prgShareNaver').click(function(){
		var datas = shareData($(this));
		$(this).attr('href', 'https://share.naver.com/web/shareView.nhn?url='+ encodeURIComponent(datas.url) +'&title='+ datas.Title).attr('target','_blank');
	})

	
	// 페이스북
	// TODO: meta 오픈그래프 동적으로 생성하기
	$('#shareWrap').find('.prgShareFB').click(function(){
		var datas = shareData($(this));
		FB.ui({
			method: 'share',
			href: datas.url,
			mobile_iframe: true,
			quote: datas.Title,
		  }, function(response){});
	})
	
	// 카카오톡
	// TODO: 공유 방식 스크랩으로 변경하기
	$('#shareWrap').find('.prgSnsKakaoT').click(function(){
		var datas = shareData($(this));
		var readCount = $('.question_box').find('.prgViewcount').text();
		var answerCount = $('.answer_box').length;
		readCount = parseInt(readCount.replace(/,/g,""));
		answerCount = parseInt(answerCount);

		Kakao.Link.sendDefault({
			objectType: 'feed',
			content: {
			title: datas.Title,
			description: datas.Content,
			imageUrl: 'http://www.altong.com/Common/images/share_sns/k_feedimg.jpg',
			link: {
				mobileWebUrl: 'http://www.altong.com/default/main',
				webUrl: 'http://www.altong.com/default/main'
			}
			},
			social: {
			viewCount: readCount,
			commentCount: answerCount
			},
			buttons: [
			{
				title: getLangStr("jsm_0016"),
				link: {
				mobileWebUrl: datas.url,
				webUrl: datas.url
				}
			}
			]
		});
	})

	// 카카오스토리
	$('#shareWrap').find('.prgSnsKakaoS').click(function(){
		var datas = shareData($(this));

		var filter = "win16|win32|win64|mac|macintel";
		if ( navigator.platform ) {
			if ( filter.indexOf( navigator.platform.toLowerCase() ) < 0 )
			{ //mobile
				Kakao.Story.open({
					url: datas.url,
					text: datas.Title
				});
			}

			else
			{ //pc
				Kakao.Story.share({
					url: datas.url,
					text: datas.Title
				});
			}
		}
	})

});

function copyclipboard(e){
	var this_text2 = $(e).data("source");
	const tempElem = document.createElement('textarea');
	tempElem.value = this_text2;
	document.body.appendChild(tempElem);
	
	tempElem.select();
	document.execCommand('copy');
	document.body.removeChild(tempElem);
	
	alert(getLangStr("jsm_0017") + '\n' +  getLangStr("jsm_0018"));
}