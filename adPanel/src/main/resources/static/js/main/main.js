$(document).ready(function() {
    //TODO
    var zoneId = $("#zoneId").val();
    var adid = $("#adId").val();
    var os = $("#os").val().toLowerCase();

    var width = $("#width").val();
    var height = $("#height").val();

    //TODO width , heiht 추가.
    console.log("call param - "+"adid:"+adid+", os:"+os+", width:"+width,"height:"+height);

    var mobiwithSrc = "https://www.mobwithad.com/api/banner/app/mobicomms/v1/hanamoney?zone={zone}&adid={adid}";

    mobiwithSrc = mobiwithSrc.replace("{zone}",zoneId);
    mobiwithSrc = mobiwithSrc.replace("{adid}",adid);
    //일반광고 호출
    setMobiwithIframeSrc(mobiwithSrc,width,height);

    // 메시지 수신하기
    /*window.addEventListener('message', function (e) {
        // window.top.postMessage("no ad","*")
        //if (e.data == 'no ad') {}
    });*/

});

function setMobiwithIframeSrc(src,width,height){
    $("#mobwith").attr("src", src);

    var param = { "zoneid" : zoneId , "w" : width,"h": height };
    console.log("param : " + param);
    reqNative("ad_info", param); // 앱 함수 호출
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
