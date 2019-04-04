/**
 *  hui全局函数对象   var hui = new $.hui();
 */
$.extend({
    hui: function () {
    }
});
/**
 * 全局post函数
 *
 * @param options   参数
 */
$.hui.prototype.post = function (options) {
    var self = this;
    $.ajax({
        type: 'POST',
        url: options.url,
        data: options.data || {},
        async: options.async || false,
        dataType: 'json',
        success: function (result) {
            self.hideLoading();
            options.success && options.success(result);
        },
        error: function () {
            //
        }
    });
};

/**
 * 显示动画
 */
$.hui.prototype.showLoading = function () {
    if ($('#hui-loading').length == 0) {
        $('body').append('<div id="hui-loading"></div>');
    }
    $('#hui-loading').show();
};

/**
 * 隐藏动画
 */
$.hui.prototype.hideLoading = function () {
    $('#hui-loading') && $('#hui-loading').hide();
};
//弹框设置
$.hui.prototype.alertOK = function (options) {
    options = options.length?{text:options}:(options || {} );
    options.title = options.title || '操作成功';
    options.text = options.text;
    options.showCancelButton = false;
    options.showCloseButton = false;
    options.type = 'success';
    this.alertBox(options);
};
$.hui.prototype.alertDelWarn = function (options) {
    options = options.length ? {text:options}:(options || {} );
    options.title = options.title || '确定要删除吗？';
    options.text = options.text || '删除后将无法恢复，请谨慎操作！';
    options.showCancelButton= true;
    options.type = 'warning';
    options.closeOnConfirm = false;
    options.closeOnCancel = true;
    this.alertBox(options);
};
$.hui.prototype.alertConfirm = function (options) {
    options = options.length ? {text:options}:(options || {} );
    options.title = options.title || '确定要删除吗？';
    options.text = options.text;
    options.showCancelButton= true;
    options.type = 'question';
    this.alertBox(options);
};
//500ms后刷新页面
$.hui.prototype.alertOkAndReload = function (text) {
    this.alertOK({text:text, then:function () {
            setTimeout(function () {
                window.location.reload();
            }, 500);
        }});
};
$.hui.prototype.alertWarn = function (options) {
    options = options.length ? {text:options} : ( options || {} );
    options.title = options.title || '警告信息';
    options.text = options.text;
    options.timer = 3000;
    options.type = 'warning';
    this.alertBox(options);
};
$.hui.prototype.alertError = function (options) {
    options = options.length ? {text:options} : ( options || {} );
    options.title = options.title || '错误信息';
    options.text = options.text;
    options.type = 'error';
    this.alertBox(options);
};
$.hui.prototype.alertBox = function (options) {
    swal({
        title: options.title,
        text: options.text,
        type: options.type,
        timer: options.timer || 9999,
        showCloseButton: options.showCloseButton,
        showCancelButton: options.showCancelButton,
        showLoaderOnConfirm: options.showLoaderOnConfirm || false,
        confirmButtonColor: options.confirmButtonColor || '#3085d6',
        cancelButtonColor: options.cancelButtonColor || '#d33',
        confirmButtonText: options.confirmButtonText || '确定',
        cancelButtonText: options.cancelButtonText || '取消'
    }).then(function (e) {
        options.then && options.then(e);
    }).catch(swal.noop);
};
$.hui.prototype.getNowFormatDate = function(){
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
};

$.hui.setAjaxPaginator = function (paginatorSelector, data, option) {
    var totals = data.count;//总条数
    var pageSize = option.pageSize; //每页条数
    var totalPages = 1;
    if (totals != 0) {
        if (totals % pageSize == 0) {
            totalPages = totals / pageSize;
        } else {
            totalPages = Math.ceil(totals / pageSize);
        }
    }
    if (totalPages > 1) {
        //当总页数大于1时生成显示分页否则不显示分页
        this.buildAjaxPaginator(paginatorSelector, $.extend(option, {totalPages: totalPages}))
    }else{
        //清除已创建的分页条
        paginatorSelector.empty();
    }
};

$.hui.buildAjaxPaginator = function (paginatorSelector, option) {
    var _option = {
        currentPage: 1, //当前页
        totalPages: 1, //总页数
        numberOfPages: 5, //设置控件显示的页码数
        bootstrapMajorVersion: 3,//如果是bootstrap3版本需要加此标识，并且设置包含分页内容的DOM元素为UL,如果是bootstrap2版本，则DOM包含元素是DIV
        useBootstrapTooltip: false,//是否显示tip提示框
        itemTexts: function (type, page, current) {//文字翻译
            switch (type) {
                case "first":
                    return "首页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "尾页";
                case "page":
                    return page;
            }
        },
        onPageClicked: function (event, originalEvent, type, page, pageSize) {
        }
    };
    $.extend(_option, option);
    paginatorSelector.bootstrapPaginator(_option);
}
/**
 * 在页面中任何嵌套层次的窗口中获取顶层窗口
 * @return 当前页面的顶层窗口对象
 */
function getTopWinow(){
    var p = window;
    while(p != p.parent){
        p = p.parent;
    }
    return p;
}
$.ajaxSetup({
    contentType: "application/x-www-form-urlencoded;charset=utf-8",
    complete: function (XMLHttpRequest, textStatus) {
        //通过XMLHttpRequest取得响应头，sessionstatus，
        var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
        if (sessionstatus == "timeout") {
            //如果超时就处理 ，指定要跳转的页面
            var top = getTopWinow();
            console.log("11");
            top.location = "global/sessionError";
        }
    }
});