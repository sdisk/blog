(function () {
    var commentId = $('#coid').val();
    if ( commentId != ""){
        var commentContent = $('#commentContent');
        var commentParentAuthor = $('#comment-id-' + commentId).find(".comment-list-one-head-name").html();
        commentContent.attr("placeholder", "@" + commentParentAuthor);
        $(".comment-cancel-reply").show();
        commentContent.focus();
    }
})();
addCommentInputValue();
$('.comment-list-one-footer-reback').click(function(){
    var commentContent = $('#commentContent');
    var at = $(this).attr("at");
    var commentParentAuthor = $('#comment-id-' + at).find(".comment-list-one-head-name").html();
    $('#coid').val(at);
    commentContent.attr("placeholder", "@" + commentParentAuthor);
    $(".comment-cancel-reply").show();
    commentContent.focus();
});
$('.comment-cancel-reply').click(function () {
    $('#coid').val(0);
    $('#commentContent').val("");
    $('#commentContent').attr("placeholder", "");
    $(".comment-cancel-reply").hide();
});
function subbimtComment() {
    var isAdmin = $("#isAdmin").val();
    $.ajax({
        type:'post',
        url:'/blog/comment',
        data:$('#comment-form').serialize(),
        async:false,
        dataType:'json',
        success:function(result){
            $("#comment-form input[name=coid]").val('');
            if (result && result.code =='200'){
                if (isAdmin == "1"){
                    layer.msg("已成功回复评论！",{
                            icon:6,
                            time:2000 },
                        function (){
                            window.location.reload();
                        });
                } else {
                    layer.msg("评论已提交到后台审核！",{
                            icon:6,
                            time:2000 },
                        function (){
                            window.location.reload();
                        })
                }
            }else {
                if (result.msg) {
                    layer.msg(result.msg,{
                        icon:1
                    });
                }
            }
        }
    });
    return false;
}
function getCommentCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(decodeURI(arr[2]));
    else
        return null;
}
function addCommentInputValue() {
    document.getElementById('author').value = getCommentCookie('remember_author');
    document.getElementById('mail').value = getCommentCookie('remember_mail');
    document.getElementById('url').value = getCommentCookie('remember_url');
}
