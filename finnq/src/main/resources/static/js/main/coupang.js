var swiper;
var now_index = 0;

$(document).ready(function() {
    var main_div = $(".track");
    var html = "";

    var mobon = $("#mobon").val();
    var coupangPoint = $("#coupangPoint").val();
    var os = $("#os").val();
    var zone = "houseIos";

    if(mobon=="Y"){
        if(os=="aos"){
            zone = "houseAos";
        }
        var param = { "ad_id" : zone , "point" : coupangPoint };
        console.log(param);
        reqNative("ad_info", param); // 앱 함수 호출
    }

    //쿠팡에서 받아온 상품 리스트 출력
    for (var index in resData) {
        var obj = resData[index];

        var productImage    = obj.productImage;

        html += '<div class="view swiper-slide" index="'+index+'" style="width: 298px;">'; //사이즈 조정
        html += '   <span class="item small" index="'+index+'" style="width: 298px; height: 218px;">'; //사이즈 조정
        html += '   <img src="'+ productImage +'" width="268" height="218" class="">'; //사이즈 조정
        html += '   </span>';
        html += '</div>';

    }
    main_div.append(html);

    // swiper 설정 (blur 이슈로 swiper처리)
    swiper = new Swiper('.swiper-ct', {
        direction: 'horizontal'
    });
    // swiper 설정
    swiper = new Swiper('.swiper-container', {
        direction: 'horizontal'
        ,loop: true
        ,autoplay: {
            delay: 6000
        }
    });
    swiper.on('slideChangeTransitionStart', mainSlideChangeStart);
    swiper.on('transitionEnd', function() {
        //현재 보여주는 상품 인덱스 확인
        now_index = swiper.realIndex;
    });

    // 클릭 이벤트 처리
    $('body').click(function() {
        clickCoupang();
    });

});


function mainSlideChangeStart() {
    var swiper_position_X = [0, 0, 0, 0];
    swiperPositionSet('.swiper-container-mainNav .swiper-wrapper', swiper_position_X, swiper.realIndex  );
}
function swiperPositionSet(el, Xposition, index) {
    $(el).attr('style', '');
}

// 쿠팡 ad를 클릭하였을 때
function clickCoupang(){

    var coupangPopUp;
    var productUrl = resData[now_index].productUrl;

    coupangPopUp = window.open(productUrl, '_blank');

    // 부모 페이지의 함수 존재 여부 확인
    if (typeof window.parent.adClickOk === 'function') {
        window.parent.adClickOk("Y");
    } else {
        console.error("function adClickOk not exist");
    }

}

// 앱 함수호출
function reqNative(event, data) {
    var userAgent = navigator.userAgent.toLowerCase();

    var params = {
        event: event,
        data: data
    };

    var strdata = JSON.stringify(params);

    try {
        if (userAgent.match(/android/) || userAgent.indexOf('android') > -1) {
            window.Native.onAppEvent(strdata);
        } else if (userAgent.match(/iphone|ipad|ipod/) || userAgent.indexOf("iphone") > -1 || userAgent.indexOf("ipad") > -1 || userAgent.indexOf("ipod") > -1) {
            window.webkit.messageHandlers.Native.postMessage(strdata);
        } else {
            console.log("User Agent Is Not Mobile:", userAgent);
        }
    } catch (err) {
        /*console.error("reqNative fn error:", err);*/
    }
}