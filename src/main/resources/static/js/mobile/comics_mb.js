function addtogroup(lid, adgroupid, adid, adtag) {
    //    var o = new Object();
    //    o.GroupID = adgroupid;
    //    o.AdId = adid;
    //    o.Tag = adtag;
    //    DM5_AdGroupQueue.push(o);
    var o = $("#" + adtag);
    o.attr("AdGroupID", adgroupid);
    o.attr("AdID", lid);
    o.attr("AID", adid);
    o.addClass("clDiv");
}
function adLimit() {
    $(".clDiv").each(function () {
        //广告点击次数限制
        var left = $(this).offset().left;
        var right = $(this).offset().left + $(this).width();
        var top = $(this).offset().top;
        var bottom = $(this).offset().top + $(this).height();
        $(this).mouseover(function (e) {
            $(window).focus();
            DM5_ISINADVERTIS = !DM5_ISINPAY;
            DM5_AdGroupID = $(this).attr("AdGroupID");
            DM5_AdID = $(this).attr("AdID");
            DM5_AID = $(this).attr("AID");
        }).mouseleave(function (e) {
            if (e.clientX != -1 && (e.clientX <= left || e.clientX >= right || (e.clientY + $(window).scrollTop()) <= top || (e.clientY + $(window).scrollTop()) >= bottom)) {
                DM5_ISINADVERTIS = false;
            }
        });

        var thisHeight = parseInt($(this).css("height").replace("px", ""));
        var thisWidth = parseInt($(this).css("width").replace("px", ""));
        var obj = $(this).children().not("script").each(function () {
         try {
            if($(this).attr("class") != "ad_logo" && $(this).attr("class") != "ad_cross"){
                //$(this).width(thisWidth).height(thisHeight).css("float", "left").css("overflow", "hidden");
            }
        }
        catch (err)
         { }
     });
        try{
        $(this).height(thisHeight).width(thisWidth);
    }
    catch (err)
         { }
        var left = $(this).width() + parseInt($(this).css("padding-right").replace("px", ""))
            + parseInt($(this).css("border-right-width").replace("px", ""));
        var top = parseInt($(this).css("padding-top").replace("px", ""))// + parseInt($(this).css("margin-top").replace("px", ""))
            + parseInt($(this).css("border-top-width").replace("px", ""));
        if (getIEBrowserVer() < 8) {
            top += $(this).height();
        }
        
        return;
    });
}
function getIEBrowserVer() {
    var ver; //浏览器版本
    var bType; //浏览器类型
    var vNumber; //版本号
    ver = navigator.appVersion;
    bType = navigator.appName;
    if (bType == "Microsoft Internet Explorer") {
        vNumber = parseFloat(ver.substring(ver.indexOf("MSIE") + 5, ver.lastIndexOf("Windows")));
        return vNumber;
    }
    return 9;
}