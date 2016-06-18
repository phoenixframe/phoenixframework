/*

jquery.sorted - super simple jQuery sorting utility

Copyright (c) 2010 Jacek Galanciak

Dual licensed under the MIT and GPL version 2 licenses.
http://github.com/jquery/jquery/blob/master/MIT-LICENSE.txt
http://github.com/jquery/jquery/blob/master/GPL-LICENSE.txt

Github/docs site: http://github.com/razorjack/jquery.sorted

*/

(function($) {
	$.fn.sorted = function(customOptions) {

		var options = {
			reversed: false,
			by: function(a) { return a.text(); }
		};

		$.extend(options, customOptions);

		$data = $(this);
		arr = $data.get();
		arr.sort(function(a, b) {
			var valA = options.by($(a));
			var valB = options.by($(b));
			if (options.reversed) {
				return (valA < valB) ? 1 : (valA > valB) ? -1 : 0;				
			} else {		
				return (valA < valB) ? -1 : (valA > valB) ? 1 : 0;	
			}
		});
		return $(arr);
	};
})(jQuery);