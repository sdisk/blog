/**
 * Created by huang on 8/4/2019.
 */
//Tags Input
$('#tags').tagsInput({
    width:'100%',
    height:'35px',
    defaultText:'请输入文章标签'
});

$('.toggle').toggle({
   on:true,
    text:{
       on:'开启',
        off:'关闭'
    }
});
$(".select2").select2({
    width:'100%'
});

function allow_comment(obj) {
    var this_ = $(obj);
    var on = this_.find('.toggle-on.active').length;
    var off = this_.find('.toggle-off.active').length;
    if ( on == 1){
        $('#allow_comment').val(false);
    }
    if (off == 1){
        $('#allow_comment').val(true);
    }
}

function allow_ping(obj) {
    var this_ = $(obj);
    var on = this_.find('.toggle-on.active').length;
    var off = this_.find('.toggle-off.active').length;
    if ( on == 1){
        $('#allow_ping').val(false);
    }
    if (off == 1){
        $('#allow_ping').val(true);
    }
}

function allow_feed(obj) {
    var this_ = $(obj);
    var on = this_.find('.toggle-on.active').length;
    var off = this_.find('.toggle-off.active').length;
    if ( on == 1){
        $('#allow_feed').val(false);
    }
    if (off == 1){
        $('#allow_feed').val(true);
    }
}

$('div.allow-false').toggle({
    off : true,
    text : {
        on : '开启',
        off : '关闭'
    }
});

var hui = new $.hui();
/**
 * 保存文章
 */
