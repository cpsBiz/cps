var now_index = 0;
var zone_list = [];
var isOverIFrame = false;
var nowAdZone = "";
var coupang_yn = "N";

$(document).ready(function() {

    var with_aos_zone = ["10886254", "10886255", "10886254", "10886255", "10886254", "10886255"]; //aos용 지면
    var with_ios_zone = ["10886256", "10886253", "10886256", "10886253", "10886256", "10886253"]; //ios용 지면

    var serverType = $("#serverType").val();

    if (serverType == "prod") {
        with_aos_zone = ["10886285", "10886286", "10886285", "10886286", "10886285", "10886286"]; //aos용 지면
        with_ios_zone = ["10886287", "10886288", "10886287", "10886288", "10886287", "10886288"]; //ios용 지면
    }

    var adid = $("#adid").val();

    var userkey = $("#userkey").val();
    var type = $("#type").val();
    var os = $("#os").val().toLowerCase();

    console.log("call param - "+"adid:"+adid+", userkey:"+userkey+", type:"+type+", os:"+os);



    // 기기 별 zone값 구분
    if(os == "aos"){
        zone_list = with_aos_zone;
    } else if(os == "ios"){
        zone_list = with_ios_zone;
    }

    //adid 값이 있을때만 광고 실행
    //if(adid != ""){
    //now_index = Number(type) - 1;

    //console.log(resPointCnt);
    // 포인트 적립 내역 조회하여 쿠팡광고 호출여부 결정
    //if(resPointCnt >= 2){
    //    callCoupangApi();      //쿠팡광고 호출
    //} else {
    //    callGetApi(now_index); //일반광고 호출
    //}

//        var ad_coupang = localStorage.getItem("ad_coupang_" + adid + "_" + todayDate());
//        //마지막 광고 시 한번도 쿠팡광고가 나오지 않았다면 쿠팡광고 호출
//        if( Number(type) == zone_list.length && ad_coupang == "N"){
//            callCoupangApi();
//        } else {
//            //getCheckMobWithData(zone_list[now_index], adid, os); //모비위드 개선건 input lastMoment 체크
//            callGetApi(now_index);
//        }
    //}

    now_index = resPointCnt;

    if(coupang_useYn == "N" && mobcomms_useYn == "Y") {
        //일반광고 호출
        callGetApi(now_index);
    }else if(coupang_useYn == "Y" && mobcomms_useYn == "N") {
        //쿠팡광고 호출
        callCoupangApi();
    }else{
        if (resPointCnt >= 2) {
            callCoupangApi();      //쿠팡광고 호출
        } else {
            callGetApi(now_index); //일반광고 호출
        }
    }

    // 메시지 수신하기
    window.addEventListener('message', function(e) {
        //console.log(e);
        if (e.data == 'executeCoupangFunc') {
            executeCoupangFunc();
        } else if (e.data == 'executeClickFunc') {
            executeClickFunc();
        }

        // 테스트를 위한 코드: 현재 페이지가 최상단에 있을때 실행
        if (window === window.top) {
            // 모비위드 광고 호출시 전달받은 메시지 확인
            console.log( '=====window.top====');
            console.log( 'now zone:' + zone_list[now_index] +" | "+ e.data);
            console.log( '====================');
            console.log( e );
            if(e.data == "no ad"){
                callCoupangApi();
            } else {
                console.log('** yes ad');
            }
        }
    });
    initAdIframe(); //iframe click 감지
});

function setWithExpiry(key, value, ttl) {
    var now = new Date()

    var item = {
        value: value,
        expiry: now.getTime() + ttl,
    }
    localStorage.setItem(key, JSON.stringify(item))
}

function getWithExpiry(key) {
    var itemStr = localStorage.getItem(key)
    // if the item doesn't exist, return null
    if (!itemStr) {
        return null
    }
    var item = JSON.parse(itemStr)
    var now = new Date()
    // compare the expiry time of the item with the current time
    if (now.getTime() > item.expiry) {
        localStorage.removeItem(key)
        return null
    }
    return item.value
}

// 쿠팡 파트너스 실행
function executeCoupangFunc() {
    //console.log('executeCoupangFunc이 실행되었습니다.');
    callCoupangApi();
}

// 클릭 후 통계 적용
function executeClickFunc() {
    //console.log('executeCoupangFunc이 실행되었습니다.');
}

//iframe click 감지
function initAdIframe(){
    // 일부 광고중 화면에 diaplay 되지 않으면 iframe 이 로드 되지 않는 경우가 있기 때문에 display 될때까지 체크 함.
    var load = setInterval(function(){
        //var iframe = document.getElementById(elementId);
        var iframe = document.getElementById("mobwith");
        //var iframe = document.getElementsByTagName("iframe");

        if(iframe != null) {
            window.focus();
            window.addEventListener('blur', function() {
                if(document.activeElement == document.querySelector('iframe')) {
                    //onAdClick();
                    //console.log("onAdClick");
                    if(coupang_yn == "N") adClickOk(); //쿠팡이 아니라 일반광고일 경우 실행(쿠팡은 쿠팡 호출부에서 함수실행됨)
                }
            })

            clearInterval(load);
        }
    }, 500);
}

// 모비위드 광고 호출
function callGetApi(index){
    var adid = $("#adid").val();
    var with_domain = "https://www.mobwithad.com/api/banner/app/vp/v1/paybooc?zone=";
    var with_param  = "&count=1&pb=&w=250&h=250&adid="+adid+"&auid=&clientIp=";

    var zone = zone_list[index];
    var call_url = with_domain + zone + with_param;
    coupang_yn = "N";

    $("#mobwith").attr("src", call_url);
    //ad_info(ad_id, point);

    var param = { "ad_id" : zone , "point" : mobcomms_point};
    console.log("param : " + param);
    reqNative("ad_info", param); // 앱 함수 호출
}

// 쿠팡 파트너스 상품리스트를 가져와 호출
function callCoupangApi(){
    var adid = $("#adid").val();
    var os = $("#os").val();
    var zone = subIdIos;
    if (os == "aos") {
        zone = subIdAos;
    }

    coupang_yn ="Y";
    //비회원인 경우 adid 임의처리
    if(adid == ""){
        adid = "noadid00-0000-0000-0000-000000000000";
    }

    var call_url = "/hanapay/view/coupang"+"?adid="+adid+"&os="+os+"&coupangPoint="+coupangPoint;
    $("#mobwith").attr("src", call_url);

    var param = { "ad_id" : zone , "point" : coupangPoint };
    console.log(param);
    reqNative("ad_info", param); // 앱 함수 호출
}

// 광고 클릭 이벤트
function adClickOk(){
    //console.log("=======adClickOk=======");

    var adid = $("#adid").val();
    var userKey = $("#userKey").val();
    var box = $("#type").val();
    var os = $("#os").val().toLowerCase();
    var zone = zone_list[now_index];

    var adtype;
    if(coupang_yn == "Y"){
        adtype = "2";
        zone   = "";
    } else {
        adtype = "1";
    }

    // postChargeData(userKey, box, adtype, zone, 2); //포인트 적립요청(광고 sdk에서 직접 API호출함)

    // 클라이언트 페이지에 ad click 메시지 전달
    window.top.postMessage("ad click", '*');

}

// 오늘의 날짜를 리턴 (yyyyMMdd)
function todayDate(){
    // 현재 날짜 객체 생성
    var today = new Date();

    // 년, 월, 일 정보 가져오기
    var year = today.getFullYear();
    var month = ('0' + (today.getMonth() + 1)).slice(-2);
    var day = ('0' + today.getDate()).slice(-2);

    return year + "" + month + "" + day;
}

// 포인트 적립요청(미사용: 광고 sdk에서 직접 API호출함)
function postChargeData(userKey, box, adtype, zone, point){
    var params = JSON.stringify({
        user_id      : userKey
        ,box          : box
        ,type         : adtype
        ,zone         : zone
        ,point        : point
    });

    $.ajax({
        url			: "/hanapay/api/v1/point",
        type		: "POST",
        contentType	: "application/json",
        data		: params,
        dataType	: "json",
        //async		: false,
        success		: function(obj) {

            console.log(obj);
            var code = obj.result.code;
            var data = obj.data;
            if( code == "0000" ) {
                //setFaq(data);
                //commonWriteListPaging(data, "getFaq", "paging", page);
            } else if( code == "0002" ) {
                //reissueToken(getFaq);
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            alert("[" + textStatus + "] " + errorThrown);
        }
    });
}

// 포인트 적립 내역 조회(미사용: 페이지 로드시 조회)
function getPointData(){
//    var params = JSON.stringify({
//         user_id      : $("#userKey").val()
//        ,type         : "1"
//    });

    var userKey = $("#userKey").val();
    var type    = "1";  // 일반광고 type값 고정

    var call_url = "/webapi/point/all?user_id="+ userKey + "&type=" + type;

    $.ajax({
        url			: call_url,
        type		: "GET",
        //contentType	: "application/json",
        //data		: params,
        dataType	: "json",
        //async		: false,
        success		: function(obj) {

            console.log(obj);
            var code = obj.result_code;
            var data = obj.data;
            if( code == "0000" ) {
                //오늘 유저가 일반광고 2회 이상 적립 시 쿠팡광고 호출
                if(data != null && data.length >= 2){
                    callCoupangApi();      //쿠팡광고 호출
                } else {
                    callGetApi(now_index); //일반광고 호출
                }
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            alert("[" + textStatus + "] " + errorThrown);
        }
    });
}

// 모비위드 광고 존재여부 확인(테스트용: 미사용)
function getCheckMobWithData(zone, adid, os){
    //var params = {
    //     zone	: zone
    //    ,adid	: adid
    //    ,os	    : os
    //};

    var call_url = "/webapi/check?zone="+ zone + "&adid=" + adid + "&os=" + os;
    $.ajax({
        url			: call_url,
        type		: "GET",
        //contentType	: "application/json",
        //data		: params,
        dataType	: "text",
        //async		: false,
        success		: function(res) {

            console.log(res);
            console.log( '===== getCheckMobWithData ====');
            console.log( 'now zone:' + zone_list[now_index]);
            console.log( '====================');
            if( res.indexOf("no ad") != -1 ) {
                callCoupangApi();
            } else {
                callGetApi(now_index);
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            alert("[" + textStatus + "] " + errorThrown);
        }
    });
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
        console.error("reqNative fn error:", err);
    }
}
