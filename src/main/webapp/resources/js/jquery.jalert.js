/*
 * jQuery alert插件
*/
(function($) {
	// 弹出对话框,回调函数 可选
    //option: string or {}
    $.fn.jalert = function(option) {
        var ps = $.extend({
            buttonText: { ok: '确定', cancel: '关闭' },
            title: '提示',
            tip: false,
            tipTime: 1.5,
            content: typeof option == "string" ? option : "",
            callback: function(){return true;}
        },option || {});
        var allSel = $('select').hide();
        var cache, cacheKey = 'jay_modal';

        if (window[cacheKey] == undefined) {
            $('<div class="modal hide fade" id="jmodal-main" >\
	            		<div class="modal-header" >\
	    					<strong />\
	    				</div>\
                        <div class="modal-body" id="jmodal-container-content" />\
                        <div class="modal-footer">\
		            		<button class="btn btn-primary btn-large" />\
		            		<button class="btn btn-large btn-link"  />\
                        </div>\
                </div>').appendTo('body');
            cache = {
                modal: $('#jmodal-main'),
                body: $('#jmodal-container-content')
            };
            cache.title = cache.body.prev().children();
            cache.footer = cache.body.next();
            cache.buttons = cache.footer.children();
            cache.modal.modal({
                keyboard: true,
                backdrop: "static",
                show: false
            });
            window[cacheKey] = cache;
        }
        cache = window[cacheKey];
        ps.tip  ? cache.footer.addClass("hide") : cache.footer.removeClass("hide");
        cache.modal.modal("show");
        ps.tip&&ps.tipTime && setTimeout(function(){
            cache.modal.modal("hide");
        },ps.tipTime*1000);
        cache.title.html(ps.title);
        //OK BUTTON
        !ps.tip && cache.buttons.eq(0)
            .text(ps.buttonText.ok)
                    .one("click",function(e) {
                        allSel.show();
                        cache.modal.one("hidden",function(){
                            ps.callback.apply(this,cache.modal);
                        }).modal("hide");
                    })
        //CANCEL BUTTON
            .next()
                .text(ps.buttonText.cancel)
                    .one('click', function() { cache.modal.modal("hide"); allSel.show(); });

        if (typeof ps.content == 'string') {
            ps.content = ps.tip ? ('<div class="mt20 mb40">' + ps.content + '</div>') : ps.content;
            cache.body.html(ps.content);
        }
        if (typeof ps.content == 'function') {
            ps.content(cache.body);
        }
    };
})(jQuery)