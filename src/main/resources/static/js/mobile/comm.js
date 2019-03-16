$(function () {
    $(".closeopenvip").click(function () {
        $(".winopenvip").hide();
        $(".mask").hide();
      
    });
    $(".closenextchapter").click(function () {
        $(".winnextchapter").hide();
        $(".mask").hide();
    });
    if ($(".collection").length > 0) {
        var iscollection = false;
        $(".collection").each(function () {
            var collimg = $(this).find("img");
            if (collimg.attr("src").indexOf("book-list-bottom-right-2")!=-1)
            {
                iscollection = true;
            }
        });
        if (!iscollection) {
            $.ajax({
                url: 'userdata.ashx?d=' + new Date().getTime(),
                dataType: 'json',
                data: { tp: 7, mid: DM5_MID },
                type: 'POST',
                success: function (data) {
                    if (data && data.msg == "1") {
                        $(".collection").each(function () {
                            var collimg = $(this).find("img");
                            if (collimg.attr("src").indexOf("detail-bottom-1")!=-1) {
                                collimg.attr("src", collimg.attr("src").replace("detail-bottom-1", "book-list-bottom-right-2"));
                            }
                        });
                    }
                }
            });
        }
    }
    $(".collection").click(function () {
        if (DM5_USERID == 0) {
            showLoginModal();
        }
        else {
            var page = 1;
            var cid = 0;
            if ("undefined" != typeof indexImg) {
                page = indexImg;
            }
            else if (typeof (DM5_PAGE) != "undefined") {
                page = DM5_PAGE;
            }
            else if (typeof (DM5_CID) != "undefined") {
                cid = DM5_CID;
            }
            var collection = $(this);
            var iscollection = false;
            var collimg = collection.find("img");
            if (collimg.attr("src").indexOf("book-list-bottom-right-2")!=-1) {
                iscollection = true;
            }
            else if (collimg.attr("src").indexOf("-active") != -1) {
                iscollection = true;
            }
            if (iscollection) {
                $.ajax({
                    url: 'bookmarker.ashx?d=' + new Date().getTime(),
                    dataType: 'json',
                    data: { cid: cid, mid: DM5_MID, page: page, uid: DM5_USERID, language: 1, cancel: 1 },
                    type: 'POST',
                    success: function (msg) {
                        if (msg.Value == "1") {
                            $(".collection").each(function () {
                                var collimg = $(this).find("img");
                                if (collimg.attr("src").indexOf("book-list-bottom-right-2") != -1) {
                                    collimg.attr("src", collimg.attr("src").replace("book-list-bottom-right-2", "detail-bottom-1"));
                                }
                                else if (collimg.attr("src").indexOf("-active") != -1) {
                                    collimg.attr("src", collimg.attr("src").replace("-active", ""));
                                }
                            });
                            ShowDialog("取消收藏");
                        }
                        else if (msg.Value == "2") {
                            ShowDialog("取消收藏失败");
                        }
                        else {
                            ShowDialog("取消收藏失败");
                        }
                    }
                });
            }
            else {
                $.ajax({
                    url: 'bookmarker.ashx?d=' + new Date().getTime(),
                    dataType: 'json',
                    data: { cid: cid, mid: DM5_MID, page: page, uid: DM5_USERID, language: 1 },
                    type: 'POST',
                    success: function (msg) {
                        if (msg.Value == "1") {
                            $(".collection").each(function () {
                                var collimg = $(this).find("img");
                                if (collimg.attr("src").indexOf("detail-bottom-1") != -1) {
                                    collimg.attr("src", collimg.attr("src").replace("detail-bottom-1", "book-list-bottom-right-2"));
                                }
                                else if (collimg.attr("src").indexOf("view-top-logo-1") != -1) {
                                    collimg.attr("src", collimg.attr("src").replace("view-top-logo-1", "view-top-logo-1-active"));
                                }
                            });
                            ShowDialog("收藏成功");
                        }
                        else if (msg.Value == "2") {
                            ShowDialog("收藏失败");
                        }
                        else {
                            ShowDialog("收藏失败");
                        }
                    }
                });
            }
        }
    });

    if ($(".btn_collection").length > 0) {
        if ($(".btn_collection").length == 1) {
            $(".btn_collection").each(function () {
                var mid = $(this).attr("mid");
                var collection = $(this);
                $.ajax({
                    url: 'userdata.ashx?d=' + new Date().getTime(),
                    dataType: 'json',
                    data: { tp: 7, mid: mid },
                    type: 'POST',
                    success: function (data) {
                        if (data && data.msg == "1") {
                            collection.addClass("active");
                            collection.html('已收藏');
                        }
                    }
                });
            });
        }
        else {
            var collmids = "";
            $(".btn_collection").each(function () {
                var mid = $(this).attr("mid");
                collmids += mid + ",";

            });
            $.ajax({
                url: 'userdata.ashx?d=' + new Date().getTime(),
                dataType: 'json',
                data: { tp: 13, mids: collmids },
                type: 'POST',
                success: function (data) {
                    if (data && data.comicids) {
                        $(".btn_collection").each(function () {
                            var mid = parseInt($(this).attr("mid"));
                            if ($.inArray(mid, data.comicids) >= 0) {
                                $(this).addClass("active");
                                $(this).html('已收藏');
                            }
                        });
                    }
                }
            });
        }
    }
});

function ShowDialog(title) {
    $(".toast").text(title);
    $(".toast").show();
    setTimeout(function () {
        $(".toast").hide();
    }, 1000);
}

function showLoginModal() {
    if (typeof (DM5_CURRENTURL) != "undefined") {
        window.location.href = "/login/?from="+DM5_CURRENTURL;
    }
    else {
        window.location.href = "/login/";
    }
}


function reLoad() {
    window.location.reload(true);
}

function readmode(mode) {
    if (mode == "2") {
        $.cookie("readmode", "1", { path: "/", expires: 365 });
        window.location.reload(true);
    }
    else if (mode == "1") {
        $.cookie("readmode", 2, { path: "/", expires: 365 });
        window.location.reload(true);
    }
}

function readmode(mode) {
    if (mode == "2") {
        $.cookie("readmode", "1", { path: "/", expires: 365 });
        window.location.reload(true);
    }
    else if (mode == "1") {
        $.cookie("readmode", 2, { path: "/", expires: 365 });
        window.location.reload(true);
    }
}

function openwinopenvip()
{
    $(".winopenvip").show();
    $(".mask").show();
}


function openwinnextchapter() {
    if ($(".winnextchapter").length > 0) {
        $(".winnextchapter").show();
        $(".mask").show();
    }
    else {
        if(typeof(DM5_CHAPTERENDURL)!="undefined")
        {
            window.location.href = DM5_CHAPTERENDURL;
        }
    }
}

function isLogin() {
    var ustatus = getLoginStatus();
    if (!ustatus || ustatus == "0") {
        return false;
    }
    return true;
}

function getLoginStatus() {
    var result;
    $.ajax({
        url: '/showstatus.ashx?d=' + new Date().getTime(),
        async: false,
        error: function (msg) {
            result = "0";
        },
        success: function (json) {
            result = json;
        }
    });
    return result;
}

function SetBookmarker(cid, mid, p, uid) {
    if (uid == 0) {
        showLoginModal();
    }
    else {
        var t = $("#btn_collection" + mid);
        var cancel = 0;
        if (t.length > 0) {
            if (t.hasClass("active")) {
                cancel = 1;
            }
        }
        $.ajax({
            url: 'bookmarker.ashx?d=' + new Date().getTime(),
            dataType: 'json',
            data: { cid: cid, mid: mid, page: p, uid: uid, language: 1, cancel: cancel },
            type: 'POST',
            success: function (msg) {
                if (msg.Value == "1") {
                    if (t.length > 0) {
                        if (t.hasClass("active")) {
                            t.removeClass("active");
                            t.html('收藏')
                        } else {
                            t.addClass("active");
                            t.html('已收藏')
                        }
                    }
                    if (cancel == 1) {
                        ShowDialog("取消收藏成功");
                    }
                    else {
                        ShowDialog("收藏成功");
                    }
                }
                else if (msg.Value == "2") {
                    ShowDialog("操作失败！书签重复");
                }
                else {
                    ShowDialog("操作失败！参数错误");
                }
            }
        });
    }
};


function getPostCheckStatus() {
    var result;
    $.ajax({
        url: '/showstatus.ashx?real=1&d=' + new Date().getTime(),
        async: false,
        error: function (msg) {
            result = "0";
        },
        success: function (json) {
            result = json;
        }
    });
    return result;
}
function isPostCheck() {
    var ustatus = getPostCheckStatus();
    if (!ustatus || ustatus == "0") {
        return false;
    }
    return true;
}
