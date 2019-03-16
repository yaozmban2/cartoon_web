var showmorechapter = false;
var commentUser = "";
var zanbtn;
$(function () {
    zanbtn = $(".zanbtn");
    $(".detail-list-more").click(function () {
        $(".detail-list-select li").show();
        $(this).hide();
        showmorechapter = true;
        $(".detail-list-select").each(function () {
            $(this).css("padding-bottom", "0px");
        });
    });
    if (DM5_POSTCOUNT > 0) {
        getMangaPost(DM5_PAGEINDEX, DM5_PAGEPCOUNT, DM5_COMIC_MID, DM5_PAGETYPE);
    }
    zanbtn.click(function () {
        praisepost($(this).attr("pid"), $(this));
    });

    $(".detail-list-more").hide();
    $(".detail-list-select").each(function () {
        if(!$(this).is(":hidden"))
        {
            if (!showmorechapter) {
                var j = 15;
                if (DM5_SHOWCHAPTERIMAGE == "True") {
                    j = 5;
                }
                var id = $(this).attr("id");
                var charlen = $("#"+id + " li").length;
                if (charlen > j) {
                    $(".detail-list-more").show();
                }
                else {
                    $(this).css("padding-bottom", "0px");
                }
            }
            return;
        }
    });

    $(".btncomment").click(function () {
        if (!isLogin()) {
            showLoginModal();
            return false;
        }
        if (isPostCheck()) {
            showCheckPostModal();
            return false;
        }
        commentPID = 0;
        $(".win-comment-btn").text("发表评论");
        $(".comment-input").val("");
        $('.comment-input').attr('placeholder', '');
        $(".comment-input").attr("tocommentuser", "");
        $(".mask").show();
        $(".win-comment").show();
    });
    $(".recommentbtn").click(function () {
        if (!isLogin()) {
            showLoginModal();
            return false;
        }
        if (isPostCheck()) {
            showCheckPostModal();
            return false;
        }
        commentPID = $(this).attr("pid");
        $(".win-comment-btn").text("回复评论");
        $(".comment-input").val("");
        $('.comment-input').attr('placeholder', '@' + $(this).attr("poster"));
        $(".comment-input").attr("tocommentuser", $(this).attr("poster"));
        $(".mask").show();
        $(".win-comment").show();
    });
    $(".closecomment").click(function () {
        $(".win-comment").hide();
        $(".mask").hide();
    });

    $(".win-comment-btn").click(function () {
        var btn = $(this);
        if (commentVerify())
        {
            commentSubmit(function () {
                btn.data('isenable', 1);
            });
        }
    });
    $("#checkAdult").click(function () {
        $.cookie("isAdult", 1, { path: "/", expires: 1 });
        return true;
    });
    $(".chapteritem").click(function () {
        if (DM5_ISSHOWADULT=="True")
        {
            var v = $.cookie('isAdult');
            if (v == "1")
            {
                return true;
            }
            $("#checkAdult").attr("href",$(this).attr("href"));
            $(".wincheckAdult").show();
            $(".mask").show();
            return false;
        }
        return true;
    });

    $(".closewincheckAdult").click(function () {
        $(".wincheckAdult").hide();
        $(".mask").hide();
    });

    $(".btndownload").click(function () {
        $(".windownload").show();
        $(".mask").show();
    });

    $(".closedownload").click(function () {
        $(".windownload").hide();
        $(".mask").hide();
    });
});

function commentSubmit(callback) {

    var _commentInput = $(".comment-input");
    var content = _commentInput.val();
    var tocommentuser = _commentInput.attr('tocommentuser');
    if (tocommentuser != '' && tocommentuser != null && tocommentuser != undefined) {
        content = '@' + tocommentuser + ' ' + content;
    }

    var code = "";
    if (commentPID != 0) {
        if (content.indexOf("@" + commentUser) == -1) {
            commentPID = 0;
            commentUser = "";
        }
    }
    var cid = 0;
    if (typeof (DM5_CID) != "undefined") {
        cid = DM5_CID;
    }
    $.ajax({
        url: '/post.ashx?d=' + new Date().getTime(),
        type: 'POST',
        dataType: 'json',
        data: { message: content, code: code, mid: COMIC_MID, cid: cid, commentid: commentPID },
        error: function (msg) {
            ShowDialog("评论发生异常，请重试");
            if (callback != undefined)
                callback();
            $(".win-comment").hide();
            $(".mask").hide();
        },
        success: function (json) {
            if (json.msg == 'success') {
                if ($(".commentcount").length>0) {
                    if (DM5_POSTCOUNT < 999) {
                        DM5_POSTCOUNT++;
                        $(".commentcount").show();
                        $(".commentcount").html(DM5_POSTCOUNT);
                    }
                }
                //$(".nocomments").hide();
                if (commentPID > 0) {
                    ShowDialog("回复成功");
                }
                else {
                    ShowDialog("评论成功");
                }
                commentPID = 0;
                commentUser = "";
                $(".comment-input").val("");
                $('.comment-input').attr('placeholder', "");
                $('.comment-input').attr('tocommentuser',"");
                if (typeof (DM5_PAGETYPE) != "undefined") {
                    if (DM5_PAGETYPE == 9) {
                        getPost(DM5_PAGEINDEX, DM5_PAGEPCOUNT, DM5_TIEBATOPICID, DM5_PAGETYPE);
                    }
                    else if (DM5_PAGETYPE == 4) {
                        getMangaPost(DM5_PAGEINDEX, DM5_PAGEPCOUNT, DM5_COMIC_MID, DM5_PAGETYPE);
                    }
                }
            }
            else if (json.msg == 'unlogin') {
                showLoginModal();
            }
            else {
                ShowDialog(json.msg);
            }
            if (callback != undefined)
                callback();
            $(".win-comment").hide();
            $(".mask").hide();
        }
    });
}

function praisepost(id, t) {
    if (!isLogin()) {
        showLoginModal();
        return false;
    }
    if (isPostCheck()) {
        showCheckPostModal();
        return false;
    }
    var praise = 0;
    if (!$(t).hasClass("active")) {
        praise = 1;
    }
    $.ajax({
        url: 'post.ashx?d=' + new Date().getTime(),
        dataType: 'json',
        data: { pid: id, praise: praise, action: "praisepost" },
        error: function (msg) {
            ShowDialog("操作出现异常");
        },
        success: function (json) {
            if (json.msg == 'success') {
                if (!$(t).hasClass("active")) {
                    $(t).addClass("active");
                    var praisecount = parseInt($(t).text());
                    if (isNaN(praisecount)) {
                        praisecount = 0;
                    }
                    praisecount++;
                    $(t).text(praisecount);
                    ShowDialog("点赞成功");
                }
                else {
                    $(t).removeClass("active");
                    var praisecount = parseInt($(t).text());
                    if (isNaN(praisecount)) {
                        praisecount = 0;
                    }
                    if (praisecount > 0) {
                        praisecount--;
                    }
                    if (praisecount == 0) {
                        $(t).text("赞");
                    }
                    else {
                        $(t).text(praisecount);
                    }
                    ShowDialog("取消点赞成功");
                }
            }
            else {
                if (!$(t).hasClass("active")) {
                    ShowDialog("点赞失败");
                }
                else {
                    ShowDialog("取消点赞成功");
                }
            }
        }
    });
}

function commentVerify() {
    if (!isLogin()) {
        showLoginModal();
        return false;
    }
    if (isPostCheck()) {
        showCheckPostModal();
        return false;
    }
    var content = $(".comment-input").val();
    if (content.length < 5) {
        ShowDialog("评论字数不能小于5个");

        return false;
    }
    else {
        return true;
    }
}

function sortBtnClick(item) {
    if ($(item).hasClass("sort-1")) {
        $(item).removeClass("sort-1");
        $(item).addClass("sort-2");
        $(item).text("倒序");
    }
    else {
        $(item).removeClass("sort-2");
        $(item).addClass("sort-1");
        $(item).text("正序");
    }
    $(".detail-list-select").each(function () {
        var childObj = $(this).find('li');
        var total = childObj.length;
        var chapterul = $(this);
        var j = 14;
        if (DM5_SHOWCHAPTERIMAGE=="True") {
            j = 4;
        }
        childObj.each(function (i) {
            childObj.eq((total - 1) - i).show();
            if (!showmorechapter&&i>j) {
                childObj.eq((total - 1) - i).hide();
            }
            chapterul.append(childObj.eq((total - 1) - i));
        });

    });
}


function sortBtnClick2(item) {
    if ($(item).hasClass("sort-1")) {
        $(item).removeClass("sort-1");
        $(item).addClass("sort-2");
        $(item).text("倒序");
    }
    else {
        $(item).removeClass("sort-2");
        $(item).addClass("sort-1");
        $(item).text("正序");
    }
    $(".detail-list-select").each(function () {
        var childObj = $(this).find('li');
        var total = childObj.length;
        var chapterul = $(this);
        childObj.each(function (i) {
            childObj.eq((total - 1) - i).show();
            chapterul.append(childObj.eq((total - 1) - i));
        });

    });
}

String.prototype.startWith = function (str) {
    var reg = new RegExp("^" + str);
    return reg.test(this);
}


function titleSelect(item, className, id) {
    if ($('.' + className).length > 0) {
        $(item).parent().find('a').removeClass('active');
        $(item).addClass('active');
    }
    $('.' + className).hide();
    $('#' + id).show();
    //showchaptertype = id;
    //var chapteritem = $('#' + id).find(".chapteritem");
    //if (chapteritem.length > 0) {
    //    if (chapteritem.is(":hidden")) {
    //        $(".detail-more").show();
    //    }
    //    else {
    //        $(".detail-more").hide();
    //    }
    //}
    //else {
    //    $(".detail-more").hide();
    //}
    $(".detail-list-comment").hide();
    $(".detail-fix-bottom").show();
    if (!showmorechapter)
    {
        var j = 15;
        if (DM5_SHOWCHAPTERIMAGE == "True") {
            j = 5;
        }
        var charlen = $("#"+id + " li").length;
        if (charlen > j) {
            $(".detail-list-more").show();
        }
        else {
            $(".detail-list-more").hide();
        }
    }
    if($(".detail-list-more").is(":hidden"))
    {
        $('#' + id).css("padding-bottom", "0px");
    }
    $(".detail-list-title").show();
    if ($(".detail-bootom-ad-con").length > 0) {
        $(".detail-bootom-ad-con").hide();
    }
    $(".detail-comment-fix-bottom").hide();
}

function titleCommentSelect(item)
{
    $(".detail-selector-item").removeClass('active');
    $(item).addClass('active');
    $(".detail-list-comment").show();
    $(".detail-list-select").hide();
    $(".detail-list-more").hide();
    $(".detail-fix-bottom").hide();
    $(".detail-list-title").hide();
    if ($(".detail-bootom-ad-con").length > 0) {
        $(".detail-bootom-ad-con").show();
    }
    $(".detail-comment-fix-bottom").show();
}

function getTextCount(item, className) {
    $(item).parent().find('.red').text(180 - $(item).val().length);
}

function shareFormHide() {
    $("body").click(function (event) {
        if (event.target.id != "shareForm" && event.target.id != "share") {
            $("#shareForm").hide();
        }
    });
}

//章节加载更多事件
function charpterMore(item) {
    $(".chapteritem").show();
    $(item).remove();
    showmorechapter = true;
}

function getMangaPost(pageindex, pagesize, mid, type) {
    var html = "<div style=\"color:#666666;font-size:13px;width:830px;height:25px;text-align: center;\"><img src=\"" + DM5_LOADINGIMAGE +
        "\" style=\"margin-right: 10px;position: relative;top: 3px;\">加载中</div>";
    $(".detail-list-comment").html(html);
    $.ajax({
        url: 'pagerdata.ashx?d=' + new Date().getTime(),
        data: { pageindex: pageindex, pagesize: pagesize, mid: mid, t: type },
        error: function (msg) {
            //ShowDialog("服务器出现异常请重试");
        },
        success: function (json) {
            re = json;
            var objs = eval(json);
            html = "";
            for (var i = 0; i < objs.length; i++) {
                var obj = objs[i];
                html += '<li>';
                html += '<div class="detail-list-comment-cover"><img src="' + obj.HeadUrl + '"></div>';
                html += '<div class="detail-list-comment-info">';
                html += '<p class="detail-list-comment-title">' + obj.Poster + '<a class="detail-list-comment-right zanbtn';
                if (obj.IsPraise)
                {
                    html += " active";
                }
                html += '" href="javascript:void(0);" pid="' + obj.Id + '">';
                if (obj.PraiseCount > 0) {
                    html += obj.PraiseCount + '</a></p>';
                }
                else {
                    html += '赞</a></p>';
                }
                html += '<p class="detail-list-comment-subtitle">'+obj.PostTime+'</p>';
                html += '<p class="detail-list-comment-content recommentbtn" pid="' + obj.Id + '" poster="' + obj.Poster + '"  >' + obj.PostContent + '</p>';
                html += '</div>';
                html += '</li>';
            }
            $(".detail-list-comment").html(html);
            $(".detail-list-comment").find(".zanbtn").click(function () {
                praisepost($(this).attr("pid"), $(this));
            });
            $(".detail-list-comment").find(".recommentbtn").click(function () {
                if (!isLogin()) {
                    showLoginModal();
                    return false;
                }
                if (isPostCheck()) {
                    showCheckPostModal();
                    return false;
                }
                commentPID = $(this).attr("pid");
                $(".comment-input").val("");
                $(".win-comment-btn").text("回复评论");
                $(".comment-input").attr("tocommentuser", $(this).attr("poster"));
                $('.comment-input').attr('placeholder', '@' + $(this).attr("poster"));
                $(".mask").show();
                $(".win-comment").show();
            });
        }
    });
}


function opendetaildesc(item)
{
    $(item).hide();
    $("#detail-desc").show();
}

//显示分享列表
function showShareList() {
    $(".mask").show();
    $(".shareForm").show();
}
//关闭分享列表
function hideShareList() {
    $(".mask").hide();
    $(".shareForm").hide();
}

function showCheckPostModal() {
    if ($(".checkpost-mask").length <= 0) {
        var html = '';
        html += '<div class="checkpost-mask" id="checkpost-mask"></div>';
        html += '<div class="checkpost-win">';
        html += '<p class="checkpost-win-title">您尚未验证手机号</p>';
        html += '<p class="checkpost-win-tip">依照《移动互联网应用程序信息服务管理规定》，通过实名认证后才能进行发帖、评论等操作。请先绑定手机号完成实名认证。</p>';
        html += '<a href="javascript:void(0);" class="checkpost-win-btn" >下次再说</a>';
        html += '<a href="/bindphone/" class="checkpost-win-btn">去验证</a>';
        html += '</div>';
        $("body").append(html);
        $(".checkpost-win-btn").click(function () {
            $('.checkpost-mask').hide();
            $('.checkpost-win').hide();
        });
        $('.checkpost-mask').show();
        $('.checkpost-win').show();
    }
    else {
        $(".checkpost-mask").show();
        $(".checkpost-win").show();
    }
}