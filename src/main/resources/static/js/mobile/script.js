//右侧悬浮定时器
var backTopTimer;
var imgurl;
$(function () {
    $(window).off('resize.offcanvas.amui');
    setTimeout(function () {
        $(window).off('resize.offcanvas.amui');
    }, 500);

    // 搜索框绑定事件
    $("#searchtxt").focus(function () {
        $(".search-downlist").show();
    }).blur(function () {
        var timer = setTimeout(function() {
            $(".search-downlist").hide();
        },200);
    });

    $(document.body).click(function () {
        var $active = document.activeElement;
        if ($active.nodeName !== 'INPUT' && $active.id !== 'searchtxt') {
            $(".search-downlist").hide();
        }
    });

    $('.search-downlist-item').click(function (e) {
        var item = $(this).children('.search-downlist-item-title')[0];
        if (item && $.trim($(item).text()) !== '') {
            location.href = "/search?title=" + encodeURIComponent($.trim($(item).text())) + "&language=1";
        }

        e.stopPropagation();
        e.preventDefault();
    });

    $('.search-downlist-item-cross').click(function (e) {
        var self = this;
        var title = $(this).prev().text();
        $.get("dm5.ashx?action=removehistory&t=" + new Date().getTime(), { title: encodeURIComponent(title) }, function (data) {
            if (data && data["result"]) {
                $(self).parent("a.search-downlist-item").remove();
                if ($('.search-downlist-item').length === 0) {
                    $('.search-downlist').remove();
                }
            }
        }, "json");

        e.stopPropagation();
        e.preventDefault();
    });
});
//右侧悬浮返回顶部
$(window).scroll(function () {
    //$('.historyBar').hide();
    //$("#searchtxt").blur();
    $(window).off('resize.offcanvas.amui');
    clearTimeout(backTopTimer);
    $(".returnTop").fadeOut();
    var scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
    if (scrollTop > 300) {
        backTopTimer = setTimeout(function () {
            $(".returnTop").fadeIn();
        }, 500);
    }
});
//详情页获取阅读按钮高度
function getHeight() {
    var lineHeight = document.getElementById("readBtnImg").offsetHeight;
    document.getElementById("readInfo").style.lineHeight = (lineHeight + 1) + "px";
}
//显示详情简介内容
function showContent() {
    $(".detailContent p").toggleClass("d-nowrap-clamp-2");
    if ($(".detailContent p").hasClass("d-nowrap-clamp-2")) {
        $(".detailContent .more img").attr("src", imgurl + "arrow_down.png");
    } else {
        $(".detailContent .more img").attr("src", imgurl + "arrow_up.png");
    }
}

//按钮点击切换事件
function btnClick(item) {
    $(item).parent().children().removeClass("active");
    $(item).addClass("active");
    switch ($('.rank-selector .active').index() + 1) {
        case 0: document.documentElement.scrollTop = scrollTop0 || 0; break;
        case 1: document.documentElement.scrollTop = scrollTop1 || 0; break;
        case 2: document.documentElement.scrollTop = scrollTop2 || 0; break;
        case 3: document.documentElement.scrollTop = scrollTop3 || 0; break;
        case 4: document.documentElement.scrollTop = scrollTop4 || 0; break;
    }
}
//发表栏点击事件
function publishBarClick() {
    $("html").css("margin-top", "-" + $("body").scrollTop() + "px");
    $("body").addClass("am-offcanvas-page");
    $(".commentForm").show();
    $("#mask").show();
    $("#commentContent").focus();
}
//退出发表事件
function publishBarRecover() {
    var top = $("html").css("margin-top");
    $("html").css("margin-top", "0");
    $("body").removeClass("am-offcanvas-page");
    $("body").scrollTop(top.replace("-", "").replace("px", ""));
    $(".commentForm").hide();
    $("#mask").hide();
}
//章节列表事件
function chaptersClick(id, item) {
    btnClick(item);
    $('.chapters').hide();
    $('#chapterList_' + id).show();
    $('.commentList').hide();
    $('.recommendList').show();
    $('.new_ad').show();
    $('.commentBar').hide();
}
//评论列表事件
function commentsClick(item) {
    btnClick(item);
    $('.chapters').hide();
    $('.commentList').show();
    $('.recommendList').hide();
    $('.new_ad').hide();
    $('.commentBar').show();
}
//分类列表事件
function classClick(id, item) {
    btnClick(item);
    $(".classList").hide();
    $('#classList_' + id).show();
}
//注册显示密码事件
function showPassword(item) {
    $(item).toggleClass("active");
    if ($(item).hasClass("active")) {
        $(item).parent().find("input").attr("type", "text");
        $(item).parent().find("input").focus();
    }
    else {
        $(item).parent().find("input").attr("type", "password");
        $(item).parent().find("input").focus();
    }
}


//搜索事件
function doSearch(isreplace) {
    var keywords = $("#searchtxt").val();
    if ($.trim(keywords) != "") {
        if (isreplace) {
            pushHistory("/search?title=" + encodeURIComponent(keywords) + "&language=1");
        } else {
            location.href = "/search?title=" + encodeURIComponent(keywords) + "&language=1";
        }
    }
}
//重新搜索
function searchAgain(item) {
    var keywords = $(item).text();
    if ($.trim(keywords) != "") {
        pushHistory("/search?title=" + encodeURIComponent(keywords) + "&language=1");
    }
}
//清除搜索历史
function historyClear() {
    $.cookie("dm5_newsearch", "", { expires: 1, path: "/", domain: ".dm5.com" });
    $(".search-downlist").remove();
}
//登录验证
function loginvaldata() {
    var username = $("#txt_name");
    var pwd = $("#txt_password");

    if (username.val().trim() == "" || username.val() == username.attr("placeholder")) {
        $("#showerror").html("请输入用户名或邮箱");

        username.get(0).focus();
        return false;
    }
    if (pwd.val() == "" || pwd.val() == pwd.attr("placeholder")) {
        $("#showerror").html("请输入登录密码");
        pwd.get(0).focus();
        return false;
    }
    return true;
}


// 忘记密码
function forgotvaldata() {
    if ($("#txt_username").val() == "") {
        $("#showerror").text("邮箱不能为空！");
        $("#txt_username").focus();
        return false;
    } else if (!$("#txt_username").val().match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
        $("#showerror").text("邮箱格式不正确！");
        $("#txt_username").focus();
        return false;
    } else if ($("#txt_code").length != 0 && !validcode("forgotcode", $("#txt_code").val())) {
        $("#showerror").show();
        $("#showerror").text("验证码输入错误!");
        $("#txt_code").focus();
        return false;
    }
    return true;
}

//注册验证事件
function regvaldata() {
    var email = $("#txt_reg_email").val();
    if ($("#txt_reg_email").val() == "") {
        $("#showerror").text("邮箱不填可是不行的哦");
        $("#txt_reg_email").focus();
        return false;
    }
    else if (!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
        $("#showerror").text("邮箱格式不正确！请重新输入");
        $("#txt_reg_email").focus();
        return false;
    }
    else if ($("#txt_reg_name").val() == "") {
        $("#showerror").text("用户名不填可是不行的哦");
        $("#txt_reg_name").focus();
        return false;
    }
    else if (!$("#txt_reg_name").val().match(/^[a-zA-Z_0-9]*[a-zA-Z_]+[a-zA-Z_0-9]*$/)) {
        $("#showerror").text("用户名仅支持英文,数字和下划线,且不能为纯数字");
        $("#txt_reg_name").focus();
        return false;
    }
    else if (!$("#txt_reg_name").val().match(/^[a-zA-Z_0-9]{4,20}$/)) {
        $("#showerror").text("用户名为4-20个字符");
        $("#txt_reg_name").focus();
        return false;
    }
    else if ($("#txt_reg_password").val() == "") {
        $("#showerror").text("密码不填可是不行的哦");
        $("#txt_reg_password").focus();
        return false;
    }
    else if (/ /.test($("#txt_reg_password").val())) {
        $("#showerror").text("密码不能包含空格");
        $("#txt_reg_password").focus();
        return false;
    }
    else if ($("#txt_reg_password").val().length < 6) {
        $("#showerror").text("密码需大于6位");
        $("#txt_reg_password").focus();
        return false;
    }
    else if ($("#txt_reg_password").val().length > 16) {
        $("#showerror").text("密码需小于16位");
        $("#txt_reg_password").focus();
        return false;
    }
    else if ($("#txt_reg_password").val() != $("#txt_reg_password2").val()) {
        $("#showerror").text("确认密码填写不一致");
        $("#txt_reg_password2").focus();
        return false;
    }
    else if ($("#code").val() == "") {
        $("#showerror").text("验证码不填可是不行的哦");
        $("#code").focus();
        return false;
    }

    return true;
}
//获取验证码
function imgreload(v, action) {
    var id = "#" + v;
    var act = "";
    if (action) {
        act = "bar";
    }
    if (act == "bar") {
        $(id).attr("src", "/image.ashx?action=bar&d=" + new Date().getTime());
    }
    else {
        $(id).attr("src", "/image.ashx?d=" + new Date().getTime());
    }
}
var canBookmarker = true;
//收藏事件
function setBookmarker(cid, mid, p) {
    if (canBookmarker) {
        canBookmarker = false;
        if ($("#collect_icon").attr("src").indexOf("_a") == -1) {
            $.ajax({
                url: 'bookmarker.ashx?d=' + new Date().getTime(),
                dataType: 'json',
                data: { cid: cid, mid: mid, page: p, language: 1, cancel: 1 },
                type: 'POST',
                success: function (msg) {
                    canBookmarker = true;
                    if (msg.Value == "0") {
                        $("#collect_icon").attr("src", $("#collect_icon").attr("src").replace("detail_btn_2", "detail_btn_2_a"));
                        $(".alertMsg").text("取消收藏成功");
                        $(".alertMsg").show();
                        setTimeout(function () {
                            $(".alertMsg").hide();
                        }, 1000);
                    }
                    else if (msg.Value == "1") {
                        $("#collect_icon").attr("src", $("#collect_icon").attr("src").replace("detail_btn_2", "detail_btn_2_a"));
                        $(".alertMsg").text("取消收藏成功");
                        $(".alertMsg").show();
                        setTimeout(function () {
                            $(".alertMsg").hide();
                        }, 1000);
                    }
                    else if (msg.Value == "4") {
                        window.location.href = "/login/?from=" + ffUrl;
                    }
                }
            });
        }
        else {
            $.ajax({
                url: 'bookmarker.ashx?d=' + new Date().getTime(),
                dataType: 'json',
                data: { cid: cid, mid: mid, page: p, language: 1 },
                type: 'POST',
                success: function (msg) {
                    canBookmarker = true;
                    if (msg.Value == "0") {
                        $("#collect_icon").attr("src", $("#collect_icon").attr("src").replace("detail_btn_2_a", "detail_btn_2"));
                        $(".alertMsg").text("收藏成功");
                        $(".alertMsg").show();
                        setTimeout(function () {
                            $(".alertMsg").hide();
                        }, 1000);
                    }
                    else if (msg.Value == "1") {
                        $("#collect_icon").attr("src", $("#collect_icon").attr("src").replace("detail_btn_2_a", "detail_btn_2"));
                        $(".alertMsg").text("收藏成功");
                        $(".alertMsg").show();
                        setTimeout(function () {
                            $(".alertMsg").hide();
                        }, 1000);
                    }
                    else if (msg.Value == "4") {
                        window.location.href = "/login/?from=" + ffUrl;
                    }
                }
            });
        }
    }
}
//评论回复ID
var commentPID = 0;
//评论回复按钮事件
function returnBtnClick(pid) {
    var h = $("#commentText").offset().top - 100;
    $("html,body").stop().animate({ scrollTop: h }, 1000);
    $("#commentText").focus();
    commentPID = pid;
    //console.log($(".n_" + pid).text());
    $("#commentText").val("@" + $("#author_" + pid).text() + " ");
    publishBarClick();
}

//评论提交事件
function commentSubmit() {
    if (typeof (muserid) == "undefined") {
        if (fromurl == "") {
            window.location.href = "/login/";
        }
        else {
            window.location.href = "/login/?from=" + fromurl;
        }
    }
    else {
        if ($("#commentText").val().indexOf("@" + $(".n_" + commentPID).text()) == -1) {
            commentPID = 0;
        }
        if (commentVerify()) {
            $("#comment_btn").attr("href", "javascript:void(0);");
            $("#comment_btn").text("正在发表...");
            $.ajax({
                url: '/post.ashx?d=' + new Date().getTime(),
                type: 'POST',
                dataType: 'json',
                data: { message: $("#commentText").val(), code: $("#code").val(), mid: MID, commentid: commentPID },
                error: function (msg) {
                    $(".alertMsg").text("评论发生异常，请重试");
                    $(".alertMsg").show();
                    $("#submitBtn").attr("href", "javascript:commentSubmit();");
                    $("#comment_btn").text("正在发表...");
                    setTimeout(function () {
                        $(".alertMsg").hide();
                    }, 1000);
                },
                success: function (json) {
                    if (json.msg == 'success') {
                        window.location.reload();
                    }
                    else if (json.msg == 'unlogin') {
                        if (fromurl == "") {
                            window.location.href = "/login/";
                        }
                        else {
                            window.location.href = "/login/?from=" + fromurl;
                        }
                    }
                    else {
                        $(".alertMsg").text(json.msg);
                        $(".alertMsg").show();
                        $("#comment_btn").attr("href", "javascript:commentSubmit();");
                        $("#comment_btn").text("发表");
                        setTimeout(function () {
                            $(".alertMsg").hide();
                        }, 1000);
                    }
                }
            });
        }
    }
}


//评论提交验证事件
function commentVerify() {
    if ($("#commentText").val().length < 5) {
        $(".alertMsg").text("评论字数不能低于5字");
        $(".alertMsg").show();
        setTimeout(function () {
            $(".alertMsg").hide();
        }, 1000);
        return false;
    }
    else if ($("#commentText").val().length > 180) {
        $(".alertMsg").text("评论字数不能高于180字");
        $(".alertMsg").show();
        setTimeout(function () {
            $(".alertMsg").hide();
        }, 1000);
        return false;
    }
    else if ($("#txt_code").length > 0 && !validcode("txt_code", $("#txt_code").val(), 1)) {
        $(".alertMsg").text("啊勒嘞？验证码不对诶!");
        $(".alertMsg").show();
        setTimeout(function () {
            $(".alertMsg").hide();
        }, 1000);
        return false;
    } else if ($("#code").val() == "") {
        $(".alertMsg").text("验证码不能为空!");
        $(".alertMsg").show();
        setTimeout(function () {
            $(".alertMsg").hide();
        }, 1000);
        return false;
    }
    else {
        return true;
    }
}


//章节加载更多事件
function charpterMore(id) {
    if ($("#chapterList_" + id + " ul").css("max-height") == "none") {
        $("#chapterList_" + id + " ul").css("max-height", "215px");
        $("#chapterList_" + id + " .mm").text("查看更多");
    }
    else {
        $("#chapterList_" + id + " ul").css("max-height", "none");
        $("#chapterList_" + id + " .mm").text("点击收起");
    }
}


//赞评论事件
function position(pid, v) {
    if ($("#img_" + pid).attr("src").indexOf("_a") == -1) {
        $.ajax({
            url: "position.ashx?d=" + new Date().getTime(),
            dataType: "json",
            data: {
                pid: pid,
                v: v
            },
            error: function (meg) {
                console.log(meg);
            },
            success: function (e) {
                if ($("#support_" + pid).find("img").attr("src").indexOf("_a") == -1) {
                    $("#support_" + pid).find("img").attr("src", imgurl + "comment_logo_2_a.png");
                    var count = parseInt($("#support_" + pid).find("span").text());
                    count++;
                    $("#support_" + pid).find("span").text(count);
                }
            }
        });
    } else {
        $("#support_" + pid).find("img").attr("src", imgurl + "comment_logo_2.png");
        var count = parseInt($("#support_" + pid).find("span").text());
        count--;
        $("#support_" + pid).find("span").text(count);
    }
}
//加载页码
var loadPage = 1;
//加载更多评论
function getMoreComments() {
    loadPage++;
    $.ajax({
        url: "pagerdata.ashx?d=" + new Date().getTime(),
        dataType: "json",
        data: {
            t: 4, pageindex: loadPage, mid: MID
        },
        success: function (data) {
            //console.log(data);
            var result = '';
            if (data.length > 0) {
                for (var n = 0; n < data.length; n++) {
                    result += '<li class="d-item"><img class="avatar" src="' + data[n].HeadUrl + '">' +
                        '<div class="info d-item-content"><p class="title"><span class="d-nowrap n" id="author_v">' + data[n].Poster + '</span>' +
                        '<span class="d-fr"><a href="javascript:returnBtnClick(\'2441560\');" pid="2441560">' +
                        '<img src="' + imageHomePath + 'comment_logo_1.png" alt="回复">回复</a>' +
                        '<a href="javascript:position(2441560,1)" id="support_2441560">' +
                        '<img src="' + imageHomePath + 'comment_logo_2.png" alt="点赞">' +
                        '<span>' + data[n].Support + '</span></a></span></p><p class="subtitle">' + data[n].PostTime + '</p><p class="content">' + data[n].PostContent + ' </p></div></li>';
                }
            }
            $(".commentList .list").append(result);
        }
    });
}
//返回顶部事件
function returnTop() {
    $("html, body").animate({ scrollTop: 0 }, 500);
}
//获取阅读历史
function gethistory(comicid) {
    var result = "";
    $.ajax({
        url: '/showhistory.ashx?d=' + new Date().getTime(),
        dataType: 'json',
        async: true,
        data: { cid: comicid, language: 1 },
        error: function (msg) {
        },
        success: function (json) {
            if (json && json != "" && json.IsHasChapter && $("#readInfo").text() != "(已屏蔽)") {
                var lastread = "";
                if (json.ChapterPage <= 1) {
                    lastread = "/" + json.ChapterUrlkey + "/";
                } else {
                    lastread = "/" + json.ChapterUrlkey + "-p" + json.ChapterPage + "/";
                }
                $("#shownextchapter a").attr("href", lastread);
                $("#readInfo").text("(已读" + json.ChapterName + ")");
                var path = $("#shownextchapter img").attr("src");
                $("#shownextchapter img").attr("src", path.replace("btn_1_n", "btn_1"));
            }
        }
    });
}
//详情页返回
function detailBack() {
    if ($.cookie("manben_history") == "" || $.cookie("manben_history") == null) {
        window.location.href = "/";
    }
    else {
        window.location.href = $.cookie("manben_history");
    }
    //$.cookie("manben_history", "", { path: "/" });
}
//重置密码提交验证事件
function setpasswordConfirm() {
    if ($("#txtPasswordNew").val() == "") {
        $("#showerror").show();
        $("#showerror").text("新密码不填可是不行的哦");
        $("#txtPasswordNew").focus();
        return false;
    }
    else if ($("#txtPasswordNew").val().length < 6 || $("#txtPasswordNew").val().length > 50) {
        $("#showerror").show();
        $("#showerror").text("新密码格式错误");
        $("#txtPasswordNew").focus();
        return false;
    }
    else if ($("#txtPasswordConfirm").val() == "") {
        $("#showerror").show();
        $("#showerror").text("确认密码不填可是不行的哦");
        $("#txtPasswordConfirm").focus();
        return false;
    }
    else if ($("#txtPasswordConfirm").val() != $("#txtPasswordNew").val()) {
        $("#showerror").show();
        $("#showerror").text("确认密码和新密码不一致");
        $("#txtPasswordConfirm").focus();
        return false;
    }
    else {
        $("#showerror").hide();
        return true;
    }
}
//获取首章路径
function getFirstChapter(url, mid) {
    $.ajax({
        url: '/pagerdata.ashx?d=' + new Date().getTime(),
        dataType: 'text',
        data: { t: 3, mid: mid },
        type: 'POST',
        success: function (data) {
            if (data == "") {
                window.location.href = url;
            }
            else {
                window.location.href = "/" + data + "/";
            }
        }
    });
}

//绑定邮箱验证时间
function bindemailConfirm() {
    if ($("#txtEmailNew").val() == "") {
        $("#showerror").show();
        $("#showerror").text("新邮箱不填可是不行的哦");
        $("#txtEmailNew").focus();
        return false;
    }
    else if (!$("#txtEmailNew").val().match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
        $("#showerror").text("格式不正确！请重新输入");
        $("#txtEmailNew").focus();
        return false;
    }
    else if ($("#txtEmailConfirm").val() == "") {
        $("#showerror").show();
        $("#showerror").text("确认邮箱不填可是不行的哦");
        $("#txtEmailConfirm").focus();
        return false;
    }
    else if ($("#txtEmailConfirm").val() != $("#txtEmailNew").val()) {
        $("#showerror").show();
        $("#showerror").text("确认邮箱和新邮箱不一致");
        $("#txtEmailConfirm").focus();
        return false;
    }
    else {
        $("#showerror").hide();
        return true;
    }
}
//验证码验证
function validcode(tid, code, action) {
    var result = false;
    var act = "";
    if (action) {
        act = "bar";
    }

    $.ajax({
        url: '/checkcode.ashx?d=' + new Date().getTime(),
        dataType: 'json',
        data: { code: code, action: act },
        async: false,
        error: function (msg) {
        },
        success: function (json) {
            if (json.result == 'success') {
                result = true;
            }
            else {
                var id = "#" + tid;
                if (act == "bar") {
                    $(id).attr("src", "/image.ashx?action=bar&d=" + new Date().getTime());
                }
                else {
                    $(id).attr("src", "/image.ashx?d=" + new Date().getTime());
                }
                result = false;
            }
        }
    });
    return result;
}
//弹出窗登录事件
function alertLogin() {
    if (loginvaldata()) {
        $("#loginBtn").text("正在登录...");
        $.ajax({
            url: '/login.ashx',
            type: 'POST',
            dataType: 'json',
            data: { name: $("#txt_name").val(), password: $("#txt_password").val(), code: $("#txt_code").val() },
            error: function (msg) {

            },
            success: function (json) {
                if (json.msg == 'success') {
                    $("#loginBtn").text("登录成功");
                    setTimeout(function () {
                        window.location.href = fromurl;
                    }, 1000);
                }
                else {
                    $("#showerror").html(json.error);
                    imgreload('rcode');
                    if (json.valcode == "3") {
                        $("#codeForm").show();
                    }
                    $("#loginBtn").text("立即登录");
                }
            }
        });
    }
}
//广告关闭
function detailAdClose() {
    $('.adform_2').remove();
    var date = new Date().getTime();
    date.setTime(date.getTime() + (2 * 60 * 60 * 1000));
    $.cookie("isShowDetailAd", "0", { expires: date, path: "/", domain: ".dm5.com" });
}

// 替换当前地址并跳转
function pushHistory(replaceUrl) {
    var state = {
        title: "title",
        url: replaceUrl
    };
    location.replace(replaceUrl);
    //window.history.replaceState(state, "title", replaceUrl);
    //window.location.reload();
}