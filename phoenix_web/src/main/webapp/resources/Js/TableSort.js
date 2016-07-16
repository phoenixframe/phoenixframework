//************************************************************************************************
// 说明：用于把Table转为可排序的Table
//
// 使用：1、给设置标题行添加名自定义属性role="head"
//       2、标题行中需要排序的列添加自定义属性sort="true"
//       3、调用扩展方法，如：$("#tableId").sorttable();
//
// 参数：JSON格式
//       ==普通参数==
//       sorttingMsg:      排序时显示的文字（默认为"排序中……"）,
//       sorttingMsgColor: 排序时显示的文字的颜色（默认为"#FFF"）,
//       allowMask:        是否允许遮罩层（默认为true）,
//       maskOpacity:      遮罩层的透明度（默认为"0.5"）,
//       maskColor:        遮罩层的颜色（默认为"#000"）,
//       ascImgUrl:        升序图片（默认为不显示）,
//       ascImgSize:       升序图片大小（默认为"8px"）,
//       descImgUrl:       降序图片（默认为不显示）,
//       descImgSize:      降序图片大小（默认为"8px"）
//
//       ==回调函数==
//       onSorted(cell):   排序完成回调函数（参数：cell，当前排序列的头(一般是th或者td的jquery对象)）
//*************************************************************************************************
$.fn.extend({
    sorttable: function (setting) {
        // 配置参数
        var configer = $.fn.extend({
            // 属性
            sorttingMsg: "排序中……",
            sorttingMsgColor: "#FFF",
            allowMask: true,
            maskOpacity: "0.5",
            maskColor: "#000",
            ascImgUrl: "",
            ascImgSize: "8px",
            descImgUrl: "",
            descImgSize: "8px",

            // 事件
            onSorted: null // 排序完成回调函数
        }, setting);

        // 获取扩展对象
        var extObj = $(this);
        // 用于锁住当前操作的对象
        var lock = false;
        // 排序属性的可取值
        var sortOrder = {
            byAsc: "asc",
            byDesc: "desc"
        };
        // 自定义属性名
        var myAttrs = {
            order: "order",
            by: "by",
            sort: "sort"
        };
        // 可排序行的头列（jquery对象——集合）
        var headCells = extObj.find("tr[role='head']>[" + myAttrs.sort + "='true']");
        headCells.each(function () {
            if (configer.ascImgUrl != "" && configer.descImgUrl != "") {
                $("&nbsp;<img class='asc' src='" + configer.ascImgUrl + "' style='width:" + configer.ascImgSize + "; height:" + configer.ascImgSize + ";display:none;' title='升序' alt='升序'/>").appendTo($(this));
                $("&nbsp;<img class='desc' src='" + configer.descImgUrl + "' style='width:" + configer.descImgSize + "; height:" + configer.descImgSize + ";display:none;' title='降序' alt='降序'/>").appendTo($(this));
            }
/*            else {
                $("&nbsp;<span class='asc' style='width:12px; height:12px;display:none;' title='升序'>&#118;</span>").appendTo($(this));
                $("&nbsp;<span class='desc' style='width:12px; height:12px;display:none;' title='降序'>&#94;</span>").appendTo($(this));
            }*/
            $(this).css("cursor", "default");
        });

        // 设置头列点击事件
        headCells.click(function () {
            var thisCell = $(this);

            if (lock == false) {
                lock = true; // 锁事件

                if (configer.allowMask) {
                    var tw = extObj.outerWidth();
                    var th = extObj.outerHeight();
                    var tOffSet = extObj.offset();
                    var tTop = tOffSet.top;
                    var tLeft = tOffSet.left;
                    // 添加遮罩层
                    var mark = $("<div></div>").attr("id", "TableSort_Mark").css("background-color", configer.maskColor).css("position", "absolute").css("top", tTop + "px").css("left", tLeft + "px").css("opacity", configer.maskOpacity).css("z-index", "9999").css("width", tw + "px").css("height", th + "px");
                    mark.html("<h3 id='TableSort_Sortting' style='opacity:1;color:" + configer.sorttingMsgColor + ";padding:36px 0 0 0;text-align:center;'>" + configer.sorttingMsg + "</h3>");
                    mark.appendTo($("body"));

                    // 延时执行排序方法，显示遮罩层需要时间~
                    window.setTimeout(function () {
                        // 设置列排序
                        SetColumnOrder(thisCell);
                        // 触发排序完成回调函数
                        FireHandleAfterSortting(thisCell);

                        // 解锁，撤销遮罩层
                        lock = false;
                        mark.remove();

                    }, 100);
                }
                else {
                    // 设置列排序
                    SetColumnOrder(thisCell);
                    // 触发排序完成回调函数
                    FireHandleAfterSortting(thisCell);

                    // 解锁，撤销遮罩层
                    lock = false;
                }

                // 所有头部的列的排序标记设置为false
                headCells.attr(myAttrs.order, false);
                // 被点击列的排序标志设置为true
                thisCell.attr(myAttrs.order, true);
                // 设置排序列的排序规则
                var by = thisCell.attr(myAttrs.by);
                thisCell.attr(myAttrs.by, (by == sortOrder.byAsc ? sortOrder.byDesc : sortOrder.byAsc));

            }
        });

        //====================================
        // 说明：触发排序完成回调函数
        // 参数：sortCell = 当前排序的列头
        //------------------------------------
        function FireHandleAfterSortting(sortCell) {
            if (configer.onSorted != null) {
                configer.onSorted(sortCell);
            }
        }

        //====================================
        // 说明：设置列排序
        // 参数：headCell = 列头（jquery对象）
        //------------------------------------
        function SetColumnOrder(headCell) {
            var by = headCell.attr(myAttrs.by);
            var index = headCell.index();
            var rs = extObj.find("tr[role!='head']");
            rs.sort(function (r1, r2) {
                var a = $.trim($(r1).children("td").eq(index).text());
                var b = $.trim($(r2).children("td").eq(index).text());
                if (a == b) {
                    return 0;
                }

                var isDt = IsTime(a) && IsTime(b);

                if (isDt) {
                    var dt1 = new Date(a.replace(/-/g, "/"));
                    var dt2 = new Date(b.replace(/-/g, "/"));
                    if (dt1.getTime() > dt2.getTime()) {
                        return (by == sortOrder.byAsc) ? 1 : -1;
                    }
                    else {
                        return (by == sortOrder.byAsc) ? -1 : 1;
                    }
                }
                else if (isNaN(a) || isNaN(b)) {
                    return (by == sortOrder.byAsc) ? a.localeCompare(b) : b.localeCompare(a);
                }
                else {

                    if (a - b > 0) {
                        return (by == sortOrder.byAsc) ? 1 : -1;
                    }
                    else {
                        return (by == sortOrder.byAsc) ? -1 : 1;
                    }
                }
            });
            extObj.find("tr[role!='head']").remove();
            extObj.append(rs);
            // 显示箭头排序列图标
/*            headCells.children(".asc,.desc").hide();
            if (by == sortOrder.byAsc) {
                headCell.children(".asc").show();
            }
            else {
                headCell.children(".desc").show();
            }*/
        }

        //================================================
        // 说明：判断字符串是否是时间
        //------------------------------------------------
        function IsTime(dateString) {
            dateString = $.trim(dateString);
            if (dateString == null && dateString.length == 0) {
                return false;
            }

            dateString = dateString.replace(/\//g, "-");
            var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
            var r = dateString.match(reg);
            if (r == null) {
                var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
                var r = dateString.match(reg);
            }

            return r != null;
        }
    }
});