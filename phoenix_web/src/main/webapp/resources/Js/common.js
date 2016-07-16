/**
 * Created with phoenixframe.
 * User: mfy
 * Date: 14-8-28
 * Time: 下午4:44
 */
function U() {
    var url = arguments[0] || [];
    var param = arguments[1] || {};
    var url_arr = url.split('/');

    if (!$.isArray(url_arr) || url_arr.length < 2 || url_arr.length > 3) {
        return '';
    }

    if (url_arr.length == 2)
        url_arr.unshift(_GROUP_);

    var pre_arr = ['g', 'm', 'a'];

    var arr = [];
    for (d in pre_arr)
        arr.push(pre_arr[d] + '=' + url_arr[d]);

    for (d in param)
        arr.push(d + '=' + param[d]);

    return _APP_+'?'+arr.join('&');
}

jQuery.alerts = {
		alert:function(msg){
			layer.alert(msg);
		},
		success:function(msg){
			layer.alert(msg+'操作成功', {icon: 1});
		},
		warn:function (msg){
			layer.alert(msg, {icon: 0});
		},
		fail:function (msg){
			layer.alert(msg+'操作失败', {icon: 2})
		},
		smile:function (msg){
			layer.alert(msg+'操作完成', {icon: 6});
		},
		sad:function(msg){
			layer.alert(msg+'操作未成功', {icon: 5});
		},
		noauth:function (msg){
			layer.alert(msg+'无权进行此操作', {icon: 4});
		},
		ask:function (msg){
			layer.confirm(msg, {
				icon: 3,
				btn: ['确定','取消']
				}, function(index){
					
				  layer.close(index);
				}, function(){});			
		},
		delconfirm:function (url){
			layer.confirm('确认删除吗？',{
				icon: 3,
				btn:['确定','取消']
			},function(index){
				window.location.href=url;
				layer.close(index);
			},function(){})
		}
}

jQuery.tools = {
		add:function(url){
			window.location.href=url;
		}
}


