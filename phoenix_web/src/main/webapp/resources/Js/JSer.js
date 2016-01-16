(function() {
    /********************************************************************
     **  JSer(v2.8)  -  a lightweight js framework. and GPL license.
     **  -----------------------------------------------------
     **  @author     : 子秋(Folier)
     **  @qq         : 39886616
     *   @Document   : http://www.jdiy.org/jser-doc/
     **  @copyright  : http://www.jdiy.org
     **
     *********************************************************************/

    var /*** Hello, world,  Welcome to Use JSer. ***/

        __agent  = navigator.userAgent.toLowerCase(),
        _browser = {
            agent: __agent,
            msie: /msie/.test(__agent),
            firefox: /mozilla/.test(__agent) && !/(compatible|webkit|chrome)/.test(__agent),
            opera: /opera/.test(__agent),
            chrome: /chrome/.test(__agent),
            safari: /webkit/.test(__agent) && !/chrome/.test(__agent),
            version: (__agent.match(/.+(?:chrome|firefox|msie|version)[\/: ]([\d.]+)/) || [0,0])[1]
        },

        _evtAdd  = function(dom, handle, handler,once){
            var d = _dataCache(dom);
            if(!d)return;
            if(!d.events)d.events={};
            if(!d.events[handle]){
                d.events[handle]={
                    ls: [],
                    fn: function(e){
                        var ls=d.events[handle].ls;
                        if (!e) e = window.event;
                        if (!e.target) e.target = e.srcElement || document;
                        for(var i= 0, l=ls.length;i<l;i++){
                            var rtn =ls[i].called || ls[i].handler.call(dom, e);
                            if(ls && ls[i] && ls[i].once){
                                //once-handler can not be remove because used at clone doms.
                                ls[i].called=true;
                            }
                            if (rtn=== false) {
                                if (e.stopPropagation) e.stopPropagation(); else e.cancelBubble = true;
                                if (e.preventDefault)e.preventDefault();    else e.returnValue = false;
                                return;
                            }
                        }
                    }
                };
                if (dom.addEventListener)  dom.addEventListener(handle, d.events[handle].fn, true);
                else dom.attachEvent('on' + handle, d.events[handle].fn);
            }
            d.events[handle].ls.push({handler:handler, once:once, called:false});
        },
        _evtDel=function(dom, handle, handler) {
            var d = _dataCache(dom), e, i;
            if(!d.events || !d.events[handle]) return;
            if(handle.eq('active')){
                if(e=d.events['focus'].ls) for(i=e.length;i--;) if(e[i].handler.active) e.splice(i,1);
                if(e=d.events['blur'].ls)  for(i=e.length;i--;) if(e[i].handler.active) e.splice(i,1);
            }else if(handle.eq('hover')){
                if(e=d.events['mouseover'].ls) for(i=e.length;i--;) if(e[i].handler.hover) e.splice(i,1);
                if(e=d.events['mouseout'].ls)  for(i=e.length;i--;) if(e[i].handler.hover) e.splice(i,1);
            }else if(handler){
                for(e=d.events[handle].ls,i=e.length;i--;) if(e[i].handler==handler) e.splice(i,1);
            }else{
                d.events[handle].ls=[];
            }
            if(d.events[handle].ls.length==0){
                if (dom.removeEventListener) dom.removeEventListener(handle, d.events[handle].fn, true);
                else dom.detachEvent("on" + handle, d.events[handle].fn);
                delete d.events[handle];
            }
        },

        _taskList   = [],
        _taskLoaded = false,
        _taskRuned  = false,
        _taskAdd    = function(t){ _taskList.push(t); },
        _taskProxy  = function() {
            if (_taskList && !_taskLoaded) {
                for(var i=0;i<_taskList.length;i++){_taskList[i].call(document);}
                _taskList = null; _taskLoaded= true;
            }
        },
        _taskExec   = function(){
            if (_taskRuned) return; _taskRuned = true;
            if (document.addEventListener && !_browser.opera) {
                document.addEventListener("DOMContentLoaded", _taskProxy, false);
            } else if (_browser.msie && window == top) {
                (function() {
                    if (_taskLoaded) return;
                    try {
                        document.documentElement.doScroll("left");
                    } catch(error) {
                        setTimeout(arguments.callee, 0);
                        return;
                    }
                    _taskProxy();
                })();
            } else if (_browser.opera) {
                document.addEventListener("DOMContentLoaded", function () {
                    if (_taskLoaded) return;
                    var s = document.styleSheets;
                    for (var i = 0, l= s.length; i < l; i++)
                        if (s[i].disabled) {
                            setTimeout(arguments.callee, 0);
                            return;
                        }
                    _taskProxy();
                }, false);
            } else if (_browser.safari) {
                var num;
                (function() {
                    if (_taskLoaded) return;
                    var ready = document.readyState;
                    if (ready != "loaded" && ready != "complete") {
                        setTimeout(arguments.callee, 0);
                        return;
                    }
                    if (num === undefined) num = JSer("style, link[@rel=stylesheet]").length;
                    if (document.styleSheets.length != num) {
                        setTimeout(arguments.callee, 0);
                        return;
                    }
                    _taskProxy();
                })();
            } else {
                _evtAdd(window, 'load', _taskProxy, true);
            }
        },

        _execScript = function(content) {
            if (typeof content == 'string') {
                var hh = document.getElementsByTagName("head"),h;
                if (hh && (h=hh[0])) {
                    var script = document.createElement("SCRIPT");
                    script.type = "text/ecmascript";
                    if (_browser.msie)script.text = content;
                    else script.appendChild(document.createTextNode(content));
                    h.insertBefore(script, h.firstChild);
                    h.removeChild(script);
                }
            } else if (content.src) JSer.url(content.src).ajax({ansyc:false, type:'script'});
            else _execScript(content.text || content.textContent || content.innerHTML || content.value || "");
            return content;
        },

        _styleSet = function(jcObj, dom, style, value) {
            if (/opacity/i.test(style)) return jcObj.opacity(value);
            if (!isNaN(value) &&
                style.match(/width|height|padding|margin|spacing|border|left|top|bottom|right/i))
                value += "px";
            style = _css2js(style);
            if (style == 'display') {
                if (value != 'none') value = '';
                if (value == '')dom.style.display = '';
                if (value == '' && (dom.style.display == '') && ( document.defaultView
                    ? document.defaultView.getComputedStyle(dom, null)
                    : (dom.currentStyle || [])).display == 'none')value = 'block';
            }
            try {
                dom.style[style] = value;
            } catch(e) {
            }
        },
        _styleGet = function(jso, style, realWidthAndHeight) {
            if (jso.length < 1) return null;
            if (/opacity/i.test(style)) return jso.opacity();
            var dom = jso[0];
            style = _css2js(style);

            var ds=dom.style, value = ds[style] == undefined ? '' : ds[style];
            if (String(value).trim() == '') {
                var styles = document.defaultView
                    ? document.defaultView.getComputedStyle(dom, null)
                    : (dom.currentStyle || []);
                value = styles[style];
            }
            if ((value == '' || value == 'auto' || /^\d+%$/.test(value)
                && realWidthAndHeight)
                && (style == 'width' || style == 'height')) {

                var xy = style == 'width' ? ['Left','Right'] : ['Top','Bottom'];
                var isHide = _styleGet(jso, 'display') == 'none';
                if (isHide) JSer(dom).css('display', '');
                value = style == 'width' ? dom.offsetWidth : dom.offsetHeight;
                JSer.each(xy, function() {
                    value -= parseInt(_styleGet(jso, 'padding' + this));
                });
                if (isHide) JSer(dom).css('display', 'none');
                return  Math.max(value, 0) + "px";
            }

            if (_browser.opera && "DISPLAY".eq(style)) {
                var s = ds.outline;
                ds.outline = "0 solid black";
                ds.outline = s;
            }
            return value;
        },
        _css2js = function(style) {
            return /float/i.test(style) ?
                (_browser.msie ? 'styleFloat' : 'cssFloat') :
                style.replace(/\-(\w)/g, function(all, letter) {
                    return letter.toUpperCase();
                });
        },

        _domConvert = function(html, content) {
            var doc = document, prop = null,div, wrap, tags;
            if (content) {
                doc = content.ownerDocument || content[0] && content[0].ownerDocument || document;
                if (doc == document && typeof content == 'object' && !content.nodeType) prop = content;
            }
            html = html.replace(/(<(\w+)[^>]*?)\/>/g,
                function(all, left, tag) {
                    return tag.match(/^(abbr|area|br|col|embed|hr|img|input|link|meta|param)$/i)
                        ? all : left + "></" + tag + ">";
                }).trim();
            wrap = (!(tags = html.toLowerCase()).indexOf("<td") || !tags.indexOf("<th")) &&
                [ 3, "<table><tbody><tr>", "</tr></tbody></table>" ] ||
                !tags.indexOf("<tr") && [ 2, "<table><tbody>", "</tbody></table>" ] ||
                !tags.indexOf("<opt") && [ 1, "<select multiple=\"multiple\">", "</select>" ] ||
                !tags.indexOf("<leg") && [ 1, "<fieldset>", "</fieldset>" ] ||
                tags.match(/^<(thead|tbody|tfoot|colg|cap)/) && [ 1, "<table>", "</table>" ] ||
                !tags.indexOf("<col") && [ 2, "<table><tbody></tbody><colgroup>", "</colgroup></table>" ] ||
                _browser.msie && [ 1, "<div>", "</div>" ] ||
                [0, "", ""];
            (div = doc.createElement("DIV")).innerHTML = wrap[1] + html + wrap[2];
            while (wrap[0]--) div = div.lastChild;
            if (_browser.msie) {//for ie: remove tbody
                var tbody = tags.indexOf("<table") == 0 && tags.indexOf("<tbody") == -1
                    ? div.firstChild && div.firstChild.childNodes
                    : wrap[1] == "<table>" && tags.indexOf("<tbody") < 0 ? div.childNodes : [];
                JSer.each(tbody, function() {
                    if (this.nodeName && 'TBODY'.eq(this.nodeName) && !this.childNodes.length)
                        this.parentNode.removeChild(this);
                });
                if (/^\s/.test(html)) div.insertBefore(doc.createTextNode(html.match(/^\s*/)[0]), div.firstChild);
            }
            return prop ? JSer.each(div.childNodes, function() {
                var jso = JSer(this);
                for (var i in prop) {
                    if(prop.hasOwnProperty(i)){
                        //the 'type' is a readonly attribute for tag 'input' in IE.
                        if (!('type'.eq(i) && 'INPUT'.eq(this.tagName))) {
                            if (typeof prop[i] == 'function') jso.on(i, prop[i]);
                            else if (typeof prop[i] == 'object' && 'css'.eq(i)) jso.css(prop[i]);
                            else jso.attr(i, prop[i]);
                        }
                    }
                }
            }) : div.childNodes;
        },

        _dataSet = function(dom, data) {
            if (!dom) return;
            var d = _dataCache(dom);
            if(!d)return;
            if(data){
                d.datas=data;
            }else if(d.datas){
                delete d.datas;
            }
        },
        _dataGet  = function(dom) {
            if (!dom) return null;
            var d = _dataCache(dom);
            if(!d || !d.datas)return null;
            else return d.datas;
        },
        _dataCacheList = [],
        _dataGuid    = function(dom){
            var gA =dom.getAttribute, g = gA ? dom.getAttribute("data-jserguid") : dom['data-jserguid'];
            return g===undefined || g===null || g==='' ? -1:g;
        },
        _dataCache   = function(dom){
            try{
                var g = _dataGuid(dom);
                if(g==-1){
                    _dataCacheList[g=_dataCacheList.length]={dom:dom, data:{}};
                    if(dom.setAttribute) dom.setAttribute("data-jserguid",g);
                    else dom['data-jserguid']=g;
                }
                return _dataCacheList[g];
            }catch(e){
                return null;
            }
        },
        _dataCopy = function(dom){
            JSer('@data-jserguid', dom).add(dom).each(function(){
                var g  = _dataGuid(this);
                if(g!=-1){
                    JSer(this).removeAttr('data-jserguid');
                    var d = _dataCacheList[g],de;
                    if(d){
                        var dd;
                        if(dd=d.datas) _dataCache(this).datas = JSer.clone(dd);
                        if(de=d.events){
                            for(var i in de)
                                if(de.hasOwnProperty(i)){
                                    JSer(this).off(i);
                                    for(var ls=de[i].ls, l=ls.length,j=0;j<l;j++)
                                        _evtAdd(this, i, ls[j].handler, ls[j].once);
                                }
                        }
                    }
                }
            });
            return dom;
        },
        _argsToArray = function(args){
            var ss='';
            JSer.each(args, function(){ ss+=this+' '; });
            return ss.trim().split(/[\s;,]+/);
        },

        _ajaxAccepts = {
            xml: "application/xml, text/xml, */*",
            html: "text/html, */*",
            script: "text/javascript, application/javascript, */*",
            json: "application/json, text/javascript, */*",
            text: "text/plain, */*",
            other: "*/*"
        },
        _ajaxLastModified = {},
        _ajaxEtag = {},

        _searchEngine =function() {
            var pseudo = {
                disabled: function() {return this.disabled;},
                enabled: function() {return !this.disabled;},
                checked: function() {return this.checked;},
                selected: function() {return this.selected;},
                text: function() {return "text" == this.type; },
                input: function() {return /input|select|textarea|button/i.test(this.nodeName);},
                radio: function() {return "radio" == this.type;},
                checkbox: function() { return "checkbox" == this.type;},
                file: function() { return "file" == this.type;},
                password: function() { return "password" == this.type;},
                submit: function() {return "submit" == this.type;},
                image: function() { return "image" == this.type;},
                reset: function() { return "reset" == this.type;},
                button: function() { return "button".or(this.type, this.nodeName);},
                visible: function() { return "hidden" != this.type && JSer(this).css("display") != "none"
                    && JSer(this).css("visibility") != "hidden";},
                hidden: function() {return "hidden" == this.type || JSer(this).css("display") == "none"
                    || JSer(this).css("visibility") == "hidden";},
                first:function(i){return i==0},
                last:function(i){
                    var len=0;
                    for(var s = this.parentNode.firstChild; s; s=s.nextSibling) if(s.nodeType==1) len++;
                    return i==len-1;
                },
                odd:function(i){return i%2==1},
                even:function(i){return i%2==0},
                lt: function(i,p){return i<p-0;},
                gt: function(i,p){return i>p-0;},
                eq: function(i,p){return i==p;},
                'nth-child':function(i,p){
                    if(isNaN(p)){
                        p = p=='odd' && "2n+1" || p=='even' && '2n' || p;
                        if(/^\d+n$/.test(p)){
                            return (i-0+1)%(parseInt(p)-0)==0;// 5n
                        }
                        var t = /(-?)(\d*)n(\+|-)(\d+)/.exec(p);
                        if(t){
                            if(t[1]=='-'){
                                if(t[2]=='') return i<=t[4]-0;//-n+8
                                else return (t[4]-i-1)%t[2]==0 && t[4]-1-i>=0;//-3n+8
                            }else{
                                return (i-0+1)%t[2] == (t[3]=='-'?t[2]-t[4]:t[4]-0);//5n+2,  5n-2
                            }
                        }
                        return false;
                    }else{
                        return i==p-1;// 5  match line 5
                    }
                },
                parent: function(){return this.firstChild;},
                empty: function(){return !this.firstChild;},
                contains:function(i,p){return (this.textContent||this.innerText||JSer(this).attr('text')
                    ||this.value||'').indexOf(p.replace(/'/g,''))!=-1;},
                has: function(i,p){return JSer(p, this).length;},
                hasnot: function(i,p){return JSer(p).length==0;},
                not: function(i,p, a){return !isMatched(this, i, toSeo(p), a);},
                header:function(){return /H[1-6]/.test(this.tagName);}
            },testFn = {
                '#': function(s) {return this.getAttribute && this.getAttribute("id") == s.substring(1);},
                '.': function(s) {return (this.className || '').split(/\s+/).indexOf(s.substring(1)) != -1;},
                '*': function() {return true;},
                '@':function(s) {
                    if (/checked|selected|disabled/i.test(s)) {//simple attrubite
                        return pseudo[s.substring(1)].call(this);
                    } else if (s.indexOf("=") == -1) {// has attribute
                        return this.getAttribute(s.substring(1));
                    } else {                                        //fixed attribute
                        var pv = s.substring(1).split("=");
                        return this.getAttribute(pv[0]) == pv[1];
                    }
                },
                ':': function(s, i, a) {
                    var k = s.indexOf("("), n, p;
                    if(k!=-1){
                        n=s.substring(1,k);
                        p=s.substring(k+1, s.lastIndexOf(")"));
                    }else{
                        n=s.substring(1);
                        p='';
                    }
                    return pseudo[n] && pseudo[n].call(this, i, p, a);}
            };

            //if one selector matched, return true
            function isMatchOne(dom, domsub, mArr, topDomArr){
                for (var i = 0; i < mArr.length; i++) {
                    var fn = testFn[mArr[i].charAt(0)];
                    if (fn) {
                        try{if (!fn.call(dom, mArr[i], domsub, topDomArr))return false;
                        }catch(e){return false;}//try for ie6
                    } else {
                        if (!dom || !dom.tagName || !dom.tagName.eq(mArr[i])) return false;
                    }
                }
                return true;
            }

            //if all selectors matched, return true
            function isMatched(dom, domsub, seo, topDomArr, topSubArr){
                var ok=false;
                JSer.each(seo, function() {
                    if (this.subMatch) {
                        if (!isMatchOne(dom, domsub, this[this.length - 1], topDomArr)) return;

                        var iStart = this.length - 2, jStart = topDomArr.length - 1, okLen = 0;
                        for (var i = iStart; i >= 0; i--) {
                            if (iStart > jStart) return;
                            for (var j = jStart; j >= 0; j--) {
                                if (isMatchOne(topDomArr[j], topSubArr[j], this[i], topDomArr)) {
                                    iStart = i - 1; jStart = j - 1; j = -1;
                                    if (this.length - 1 == ++okLen) {
                                        ok=true;
                                        return false;
                                    }
                                }
                            }
                        }
                    }else{
                        if(ok= isMatchOne(dom, domsub, this, topDomArr))return false;
                    }
                });
                return ok;
            }

            //iterator all dom elements.
            function iterator(node, seo, topDomArr, topSubArr) {
                var ls = [],mi=0;
                if (node.hasChildNodes()) {
                    var le = topDomArr.length, ta = topDomArr.slice(0, le), nodes = node.childNodes, j= 0, sub=0;
                    ta[le] = node;
                    for (var i = 0, l =nodes.length; i < l; i++) {
                        if(!nodes[i] || nodes[i].nodeType !=1) continue;

                        var len = topSubArr.length, sa = topSubArr.slice(0, len);
                        sa[len++]=sub++;

                        if(isMatched(nodes[i], j++, seo, ta, sa)){
                            ls[mi++]=nodes[i];
                        }
                        ls = ls.concat(iterator(nodes[i], seo, ta, sa));
                        mi=ls.length;
                    }
                }
                return ls;
            }

            this.findAll = function(selectors, rootNode){
                return iterator(rootNode, toSeo(selectors), [], [0]);
            };
            this.findInParent = function(selectors, dom){
                var ps = [],pi= 0, item=[],ii=0, seo = toSeo(selectors), ta=[], sa=[], k;
                while(dom=dom.parentNode) ps[pi++]=dom;
                for(var i=ps.length, j=i-1; i--;){
                    ta[k=j-i] = ps[i];
                    sa[k] = JSer(ps[i]).index(0);
                    if(isMatched(ps[i], k, seo, ta,sa)) item[ii++]=ps[i];
                }
                return item;
            };

            //convert selectors to seo, 'seo' is a Three-dimensional array:
            // seo: array1[ array2[ array3[] ] ]
            // dimensional array1: every selector. (eg.: A, B, C, ..)
            // dimensional array2: sub-dom selector(when subMatch=true) (eg.: A B C ..)
            //             or one dom matche array(when !subMatch) (eg.: ['td', '@a=b', ':visible'])
            // dimensional array3: [Optional] one dom match array (eg.: ['td', '@a=b', ':visible'])
            function toSeo(selectors){
                var kh = 0, s1 = (selectors+",").split(""),yh=false, iStart= 0, jStart= 0, jArr=[], iSub=-1, jSub=-1;
                var seo =[];
                for (var i = 0; i < s1.length; i++) {
                    if (s1[i].or('(', '[')) kh++;
                    else if (s1[i].or(')', ']')) kh--;
                    else if (s1[i]=="'") yh=!yh;
                    else if (s1[i]== ',' && kh == 0){
                        if(jArr.length){
                            jArr[++jSub] = analysis(s1.slice(jStart, i));
                            jArr.subMatch=true;
                            seo[++iSub] = jArr;
                            jArr=[];
                        }else seo[++iSub]=analysis(s1.slice(iStart, i));
                        iStart = jStart = i+1;
                        jSub = -1;
                    }else if (s1[i]==' ' && !yh && kh==0){
                        jArr[++jSub]=analysis(s1.slice(jStart, i));
                        jStart=i+1;
                    }
                }
                return seo;
            }

            //analysis one selector and return a array, example: ['td', '@a=b', ':visible']
            function analysis(charArray) {
                var selector=charArray.join("");
                var s1 = selector.split(""), x = 0,z = 0,b = false, attr = '',wei = '', i,l;
                var left = selector.replace(/(^[^:\[]*).*/g, "$1");
                for (i = left.length, l =s1.length; i < l; i++) {
                    switch(s1[i]){
                        case '(' : x++; break;
                        case ')' : x--; if(x==0) b=false; break;
                        case '[' : z++; if(x==0) b=false; break;
                        case ']' : z--; break;
                        case ':' : b=true; break;
                    }

                    if (x == 0 && z > 0 && s1[i] != '[') attr += s1[i];

                    if (b || s1[i] == ')' ) {
                        if(x==0 && s1[i]==":") wei+="<1>:";
                        else  wei += s1[i];
                    }
                }
                left = left.replace(/(@|#|\.)/g,"<2>$&").split("<2>");
                if(left[0]=='')left.shift();

                var mArr = left;
                if (attr.length > 2) {
                    var attrA = attr.split(",");
                    for (i = 0; i < attrA.length; i++) if (attrA[i].charAt(0) != '@')attrA[i] = '@' + attrA[i];
                    mArr = mArr.concat(attrA);
                }
                if (wei != '') {
                    var weiA = wei.split("<1>");
                    weiA.shift();
                    mArr = mArr.concat(weiA);
                }
                return mArr;
            }
        };

    var searchEngine = new _searchEngine();
    var JSer = window.JSer = function(s, r) {
        _taskExec();
        return new Element().newInstance(s, r);
    };
    JSer.version = 2.8;
    JSer.browser = _browser;

    var Element = function() {
            this.length=0;
            this.endData=null;
        },

        setEnd = function(jso){
            var o=JSer(jso.items());
            o.endData=jso.endData;
            jso.endData=o;
        };

    Element.prototype = {
        active: function(a, b) {
            if(!a&&!b) return this.on('active');
            var ta = typeof a, tb = typeof b,fa,fb;
            if('STRING'.eq(ta,tb)){
                fa = function(){JSer(this).addClass(a).removeClass(b);};
                fb=function(){JSer(this).addClass(b).removeClass(a);};
            }else if("OBJECT".eq(ta,tb)){
                fa = function(){JSer(this).css(a);};
                fb = function(){JSer(this).css(b);};
            }else if("FUNCTION".eq(ta,tb)){fa = a;fb = b;}
            else return this;
            fa.active=fb.active=true;
            return this.on({focus: fa, active: fb, blur:fb});

        },
        add: function(content, noData) {
            var e = noData? null:this.items();

            if (content && content.nodeType && this.items().indexOf(content) == -1)
                this[this.length++] = content;
            else if (content instanceof Element || content instanceof Array)
                for (var i = 0; i < content.length; i++) this.add(content[i],true);
            else if (content && content.constructor && content.constructor == String)
                this.add(JSer(content),true);
            if(e) this.endData=JSer(e);
            return this;
        },

        addClass: function() {
            var sa=_argsToArray(arguments);
            return sa.length ==0 ? this : this.each(function() {
                var ca = ' ' + (this.className || '')+' ';
                JSer.each(sa, function() {
                    if(ca.indexOf(' '+this+' ')==-1) ca+=' '+this;
                });
                this.className = ca.trim().replace(/\s+/,' ');
            });
        },

        after: function(content, isClone) {
            var jso = JSer(content), d;
            this.each(function() {
                for (var i = 0; i < jso.length; i++)
                    if ((d = jso[i]).nodeName && 'SCRIPT'.eq(d.nodeName)) _execScript(d);
                    else{
                        var o = jso.get(i).clone(true)[0];
                        if(o) _dataCopy(this.parentNode.insertBefore(o, this.nextSibling));
                    }
            });
            if (!isClone) jso.remove();
            return this;
        },

        afterTo: function(content, isClone){
            var jso=JSer(content), r=JSer([]);
            for(var i= 0,l=this.length;i<l;i++){
                var dom = this[i];
                if(dom.nodeName && 'SCRIPT'.eq(dom.nodeName)) _execScript(dom);
                else jso.each(function(){
                    var o =JSer(dom).clone(true)[0];
                    if(o) r.add(_dataCopy(this.parentNode.insertBefore(o , this.nextSibling)),true);

                });
            }
            if(!isClone) this.remove();
            r.endData=this;
            return r;
        },

        and: function(){
            var r = JSer([]);
            r.add(this);
            JSer.each(arguments,function(){
                r.add(this);
            });
            r.endData = this;
            return r;
        },

        append: function(content) {
            return this.each(function() {
                var o;
                if (content && content.nodeType){
                    if(content.nodeName && 'SCRIPT'.eq(content.nodeName))
                        this.appendChild(_execScript(content));
                    else{
                        o = JSer(content).clone(true)[0];
                        if(o) _dataCopy(this.appendChild(o));
                    }
                }
                else if (content.constructor == String) {
                    o = document.createElement("DIV");
                    o.innerHTML = content;
                    for (var i = 0; i < o.childNodes.length; i++) {
                        var n = o.childNodes[i];
                        if (n && n.nodeType){
                            if(n.nodeType == 3) this.appendChild(document.createTextNode(n.nodeValue));
                            else JSer(this).append(n);
                        }//else alert(n);
                    }
                } else if (content instanceof Element) {
                    o = JSer(this);
                    content.reverse().each(function() {
                        o.append(this);
                    });
                } else if (content.length)
                    JSer(this).append(JSer(content));
                else
                    JSer(this).append(String(content));
            });
        },

        appendTo: function(selector) {
            var n, r=JSer([]),t = JSer(selector);
            for(var i= 0,l=this.length;i<l;i++){
                var d =this[i];
                JSer.each(t, function() {
                    if (d.nodeName && 'SCRIPT'.eq(d.nodeName))
                        r.add(_execScript(d),true);
                    else {
                        n = _dataCopy(this.appendChild(JSer(d).clone(true)[0]));
                        r.add(n, true);
                    }
                });
            }
            r.endData=this;
            return r;
        },

        attr: function(name, val) {
            if (name.constructor != String) {
                for (var j in name) if(name.hasOwnProperty(j)) this.attr(j, name[j]);
                return this;
            } else if (val !== undefined)
                return this.each(function() {
                    if (!('TYPE'.eq(name) && 'INPUT'.eq(this.tagName))) {
                        var f = _browser.msie ? "styleFloat" :"cssFloat";
                        var vo={
                            "for": "htmlFor",
                            "class": "className",
                            "float": f,
                            "cssFloat": f,
                            "styleFloat": f,
                            "readonly": "readOnly",
                            "maxlength": "maxLength",
                            "cellspacing": "cellSpacing"
                        };
                        if(vo[name] && _browser.msie) name=vo[name];
                        if (this[name] !== undefined) {
                            this[name] = val;
                        } else {
                            this.setAttribute(name, val);
                        }
                    }
                });
            else  return this[0] ? (/checked|selected|disabled/i.test(name)? this[0][name]: this[0].getAttribute(name)) : null;
        },

        before: function(content, isClone) {
            var jso = JSer(content), d;
            this.each(function() {
                for (var i = 0; i < jso.length; i++)
                    if ((d = jso[i]).nodeName && 'SCRIPT'.eq(d.nodeName)) _execScript(d);
                    else {
                        var o=JSer(jso[i]).clone(true)[0];
                        if(o) _dataCopy(this.parentNode.insertBefore(o, this));
                    }
            });
            if (!isClone) jso.remove();
            return this;
        },


        beforeTo: function(content, isClone){
            var jso=JSer(content), r=JSer([]);
            for(var i= 0,l=this.length;i<l;i++){
                var d = this[i];
                if(d.nodeName && 'SCRIPT'.eq(d.nodeName)) _execScript(d);
                else jso.each(function(){
                    var o =JSer(d).clone(true)[0];
                    if(o) r.add(_dataCopy(this.parentNode.insertBefore( o, this)),true);
                });
            }
            if(!isClone)this.remove();
            r.endData=this;
            return r;
        },

        clone: function(cloneEvents) {
            var r=JSer([]);
            this.each(function() {
                var n = this.cloneNode(true), d;
                if(_browser.msie){
                    d=document.createElement("DIV");
                    d.appendChild(n);
                    n =_domConvert(d.innerHTML)[0];
                }
                /*
                 * @JSER-WARNING: Do not call '_dataCopy(dom)' here, whether it will have a repeat of events.
                 * The method call will be postponed until the clone elements when applied to the document.
                 * */
                if (!cloneEvents){
                    JSer('@data-jserguid', n).add(n).each(function(){
                        JSer(this).removeAttr('data-jserguid');
                    });
                }
                r.add(n,true);
            });
            r.reverse();
            r.endData=this;
            return r;
        },

        css: function(style, value) {
            if (typeof style == 'object') {
                for (var i in style) if(style.hasOwnProperty(i)) this.css(i, style[i]);
                return this;
            }
            style = style.trim();
            var o = this;
            return value !== undefined ? this.each(function() {
                _styleSet(o, this, style, value);
            }) : _styleGet(this, style);
        },

        data: function(name, value) {
            if (name === null) {
                return this.each(function() {
                    _dataSet(this, null);
                });
            } else if (name == undefined) {
                return _dataGet(this[0]);
            } else if ('OBJECT'.eq(typeof name)) {
                var o = this;
                JSer.each(name, function(i){
                    o.data(i, this);
                });
                return this;
            } else if (value === undefined) {
                var d = _dataGet(this[0]);
                return d && d[name]!=undefined ? d[name] : null;
            } else {
                return this.each(function() {
                    var d = _dataGet(this) || {};
                    if (value === null){
                        delete d[name];
                        var no = true,i;
                        for(i in d) if(d.hasOwnProperty(i)){no=false;break;}
                        if(no) d=null;
                    }else d[name] = value;
                    _dataSet(this, d);
                });
            }
        },

        del: function(content, noData) {
            var e = noData?null:JSer(this.items()), vc;
            if (content != undefined) {
                if (content == (vc = parseInt(content)) && vc > -1 && vc < this.length) {
                    for (var ia = vc; ia < content.length; ia++)
                        if (ia < content.length - 1) this[ia] = this[ia + 1];
                        else delete this[ia];
                    this.length--;
                } else if (content.nodeType) this.del(this.items().indexOf(content),true);
                else if (content instanceof Element || content instanceof Array)
                    for (var i = content.length; i--;) this.del(content[i],true);
                else if (content.constructor == String) this.del(JSer(content), true);
            }
            if(e) this.endData=e;
            return this;
        },

        each: function(callback) {
            return JSer.each(this, callback);
        },

        empty: function() {
            var t;
            return this.each(function() {
                while ( t=this.firstChild ) this.removeChild(t);
            });
        },

        end: function() {
            return this.endData || JSer([]);
        },

        first: function() {
            return this.get(0);
        },

        get: function(sub) {
            var r=JSer([]);
            if (sub == 0 && this.length == 1) r.add(this[0],true);
            else if(sub>=0 && sub<this.length) r.add(this[sub],true);
            r.endData=this;
            return r;
        },

        height: function(val) {
            return val == undefined ? parseInt(_styleGet(this,'height', true)) : this.css('height', val);
        },

        hide: function(time, bywhat, callback) {// 动画待处理: bywhat: w | h | o

            return this.each(function() {
                var o =JSer(this);
                if(o.data("__stop")) o.data("__stop").call(o);
                if (o.css('display') == 'none') return;

                var oldCss =  {
                    width: o.width(),
                    height:o.height(),
                    opacity: o.opacity(),
                    overflow:o.css('overflow')
                };

                if (!time) time = 0;
                else if ('fast'.eq(time)) time = 50;
                else if ('normal'.eq(time)) time = 100;
                else if ('slow'.eq(time)) time = 200;
                else if (isNaN(time)) time = 0;
                if(_browser.msie) time/=10;
                else if(_browser.opera || _browser.safari) time/=10;
                else if(_browser.chrome) time/=5;

                if (time <= 0) {
                    o.css('display', 'none');
                    return;
                }

                function up() {
                    leavetime--;
                    if(leavetime>0){
                        if(wStep)o.width(leavetime*wStep);
                        if(hStep)o.height(leavetime*hStep);
                        if(oStep)o.opacity(leavetime*oStep);
                        timer=setTimeout(arguments.callee, 0);
                    }else{
                        o.removeData("__stop");
                        o.css(oldCss);
                        o.css('display','none');
                        if (callback) callback.call(o[0]);
                    }
                }

                var timer=null;
                function stop(){
                    if(timer!=null) clearTimeout(timer);
                    this.css(oldCss);
                    this.removeData("__stop");
                }


                if (typeof bywhat == 'function' && !callback) {
                    callback = bywhat;
                    bywhat = 'who';
                }
                bywhat = (bywhat || 'who').toLowerCase();
                o.css('overflow', 'hidden');
                var leavetime=time,wStep=0,hStep=0,oStep=0;
                if(bywhat.indexOf("w")!=-1){
                    wStep = o.width()/time;
                    o.width(0);
                }
                if(bywhat.indexOf("h")!=-1){
                    hStep = o.height()/time;
                    o.height(0);
                }

                if(bywhat.indexOf("o")!=-1){
                    oStep = o.opacity()/time;
                    o.opacity(0);
                }
                o.data("__stop", stop);
                up();
            });
        },

        hover: function(a, b) {
            if(!a&&!b) return;
            if(!a&&!b)return this.on('hover');
            var ta = typeof a, tb = typeof b,fa,fb;
            if('STRING'.eq(ta,tb)){
                fa = function(){JSer(this).addClass(a).removeClass(b);};
                fb=function(){JSer(this).addClass(b).removeClass(a);};
            }else if("OBJECT".eq(ta,tb)){
                fa = function(){JSer(this).css(a);};
                fb = function(){JSer(this).css(b);};
            }else if("FUNCTION".eq(ta,tb)){ fa = a;fb = b;}
            else return this;
            fa.hover=fb.hover=true;
            return this.on({mouseover: fa, hover: fb, mouseout:fb});
        },

        html: function(val) {
            return val == undefined
                ? (this[0] ? this[0].innerHTML : null)
                : this.empty().append(val);
        },

        inter: function(content) {
            if (content instanceof Element || content instanceof Array) {
                var ls = this.items(),r=JSer([]);
                for(var i= 0,l=content.length;i<l;i++){
                    if (ls.indexOf(content[i]) != -1) r.add(content[i],true);
                }
                r.endData=this;
                return r;
            }
            return this.inter(typeof content == 'string' ? JSer(content) : [content]);
        },

        index:function(n){
            var r = JSer([]), p;
            if(isNaN(n)) n=0;
            for(var i= 0,l=this.length;i<l;i++)
                if(p=this[i].parentNode){
                    var a =[],j= 0, c=-1;
                    for(var s = p.firstChild; s; s=s.nextSibling){
                        if(s.nodeType==1) a[j++]=s;
                        if(s==this[i]){
                            c=j-1;
                            if(n==0) return c;
                            else if(n<0)break;
                        }
                    }
                    if(n<0 && n+c>=0) r.add(a[n+c],true);
                    if(n>0 && a.length-c>n) r.add(a[n-0+c],true);
                }
            r.endData=this;
            return r;
        },

        children:function(n){
            var r=JSer([]);
            if(isNaN(n)) n=null;
            for(var i= 0,l=this.length;i<l;i++){
                var a=[],j= 0;
                for(var s=this[i].firstChild;s;s= s.nextSibling)
                    if(s.nodeType==1){
                        if(n===null || n==j) r.add(s,true);
                        if(n==j) break;
                        a[j++]=s;
                    }
                if(n<0 && (j=j+n)>0) r.add(a[j],true);
            }
            r.endData=this;
            return r;
        },

        items: function() {
            var a = [];
            for (var i = 0, l=this.length; i < l; i++) a[i]=this[i];
            return a;
        },

        last: function() {
            return this.get(this.length - 1);
        },

        loadUrl: function(url, prms, fn) {
            var u = JSer.url(url);
            if ('FUNCTION'.eq(typeof prms) && !fn) {
                fn = prms;
                prms = null;
            }
            if (prms) u.set(prms);
            var ok = function(d) {
                arguments.callee.jso.html(d);
                if (arguments.callee.fn)fn.call(arguments.callee.jso, d);
            };
            ok.jso = this;
            ok.fn = fn;
            u.ajax({
                success:ok
            });
        },

        newInstance: function(search, rootNode) {
            this.length = 0;
            search = search || document.documentElement || document.body || 'body';
            if (search == window) search.nodeType = 27;
            if (search.nodeType) {
                this.add(search,true);
            } else if (search instanceof Element) {
                return search;
            } else if (typeof search == 'string') {
                search = search.trim().replace(/\s*,\s*/g, ",").replace(/,{2,}/g, ',').replace(/\s{2,}/g," ");
                if (search == '') {
                    this.newInstance();
                } else if (/^#[^\s,:@]+$/.test(search)) { // by id
                    if (rootNode && rootNode != document.documentElement && rootNode!=document.body && rootNode!='body') {
                        this.newInstance("@id=" + search.substring(1), rootNode);
                    } else {
                        var d = document.getElementById(search.substring(1));
                        if (d) this.newInstance(d);
                    }
                } else if (/.*<(.|\s)+>.*/i.test(search.trim())) { // by html
                    var ls = _domConvert(search, rootNode);
                    for (var ii = 0; ii < ls.length; ii++) this.add(ls[ii],true);
                } else { // by Multiple
                    if (rootNode) {
                        if (rootNode.nodeType && rootNode.nodeType == 1)      // DOM Node  1:element
                            rootNode = [rootNode];
                        else if (typeof rootNode == 'string') // search string
                            rootNode = JSer(rootNode);
                        else if (! (rootNode instanceof Element || rootNode instanceof Array))
                            rootNode = [document.documentElement || document.body];
                    }
                    rootNode = rootNode || [document.documentElement || document.body];

                    var oo = this;
                    JSer.each(rootNode, function() {
                        oo.add(searchEngine.findAll(search, this), true);
                    })

                }
            } else if (search.length) { // by array or node list
                this.add(search, true);
            } else {
                this.length = 0;
            }
            return this;
        },

        off: function(handle, fn) {
            if(handle && handle.constructor ==Object){
                for (var i in handle) if(handle.hasOwnProperty(i)) this.off(i, handle[i]);
                return this;
            }
            return JSer.each(this, function() {
                _evtDel(this, handle, fn);
            });
        },

        on: function(handle, handler, once) {
            if (!handle) return this;
            else if (handle.constructor == Object)
                for (var i in handle){
                    if(handle.hasOwnProperty(i)) this.on(i, handle[i], once);
                }
            else if (handler) this.each(function() {
                if ((this == document) && handle == 'unload') _evtAdd(window, 'unload', handler, once);
                else _evtAdd(this, handle, handler, once);
            });
            else for(var j= 0, l=this.length;j<l;j++){
                    var t = this[j],fn;
                    try{
                        eval('t.'+handle+'()');
                    }catch(e){
                        if((fn=_dataCache(t)) && (fn=fn.events) && (fn=fn[handle])) fn.fn.call(t,{});
                    }
                }
            return this;
        },

        once: function(handle, handler) {
            return this.on(handle, handler, true);
        },

        opacity: function(value) {
            if (value == undefined) {
                if (!this.length) return 0;
                var mh;
                if (_browser.msie) {
                    if (mh = (this[0].style.filter || '').match(/alpha\s*\(opacity\s*=(.*)\)/))
                        return mh[1] ? parseFloat(mh[1].trim()) : 100;
                }
                return !_browser.msie ? 100 * (this[0].style.opacity || 1) : this[0].style.opacity || 100;
            } else {
                if (isNaN(value)) value = 100;
                return this.each(function() {
                    var opacity = parseInt(value);
                    if (!_browser.msie) opacity /= 100;
                    if (_browser.msie) this.style.filter = "alpha(opacity = " + opacity + ")";
                    else this.style.opacity = opacity;
                });
            }
        },

        parent: function(n){
            var r = JSer([]),i;
            if(n==undefined) n=1;
            if(isNaN(n))
                for(i= 0, l=this.length;i<l;i++){
                    r.add(searchEngine.findInParent(n, this[i]));
                }
            else
                for(i= 0,l=this.length;i<l;i++){
                    var m= n, t=null;
                    while(m--) if(! (t= (t || this[i]).parentNode)) break;
                    if(t && m==-1) r.add(t);
                }
            r.endData=this;
            return r;
        },


        prepend: function(content) {
            return this.each(function() {
                var o;
                if (content && content.nodeType){
                    if(content.nodeName && 'SCRIPT'.eq(content.nodeName))
                        this.insertBefore(_execScript(content), this.firstChild);
                    else{
                        o = JSer(content).clone(true)[0];
                        if(o)_dataCopy(this.insertBefore(o, this.firstChild));
                    }
                }
                else if (content.constructor == String) {
                    o = document.createElement("DIV");
                    o.innerHTML = content;
                    for (var i = 0, nn = o.childNodes, l= nn.length; i < l; i++) {
                        var n = nn[i];
                        if (n && n.nodeType && n.nodeType == 3) {
                            JSer(this).prepend(document.createTextNode(n.nodeValue));
                        } //text node
                        else JSer(this).prepend(n);
                    }
                } else if (content instanceof Element) {
                    o = JSer(this);
                    content.each(function() {
                        o.prepend(this);
                    });
                } else if (content.length)
                    this.prepend(JSer(content));
            });
        },

        prependTo: function(selector) {
            var t = JSer(selector), r=JSer([]);
            for(var i=0,l=this.length;i<l;i++){
                var dom = this, dn;
                JSer.each(t, function() {
                    if (dn=dom.nodeName && 'SCRIPT'.eq(dn))
                        r.add(_execScript(dom));
                    else {
                        var o = JSer(dom).clone(true)[0];
                        if(o) r.add(_dataCopy(this.insertBefore(o, this.firstChild)));
                    }
                });
            }
            r.endData=this;
            return r;
        },

        remove: function() {
            var t;
            return this.each(function() {
                if (t=this.parentNode) t.removeChild(this);
            });
        },

        removeAttr:function() {
            var sa = _argsToArray(arguments);

            var f = _browser.msie ? "styleFloat" :"cssFloat";
            var so={
                "for": "htmlFor",
                "class": "className",
                "float": f,
                "cssFloat": f,
                "styleFloat": f,
                "readonly": "readOnly",
                "maxlength": "maxLength",
                "cellspacing": "cellSpacing"
            };
            return this.each(function() {
                if(this.nodeType==1){
                    var d = this;
                    JSer.each(sa, function(){
                        if(so[this] && _browser.msie)d.removeAttribute(so[this]);
                        else d.removeAttribute(this);
                    });
                }
            });
        },

        removeClass: function() {
            var sa=_argsToArray(arguments);
            return sa.length ==0
                ? this.each(function(){this.className='';})
                : this.each(function() {
                    var d = this, ca = ' '+(this.className || '')+' ';
                    JSer.each(sa, function(){
                        ca=ca.replace(' '+ this +' ', ' ');
                    });
                    d.className = ca.trim().replace(/\s+/, ' ');
            });
        },


        removeData:function(name){
            return name ? this.data(name, null): this.data(null);
        },

        removeToggle: function(name){
            return this.toggle(name, null);
        },

        replace: function(selector, formatter){
            var arr=[], oa=JSer(selector).reverse();
            this.each(function(){
                var o=JSer(this);
                oa.each(function(){
                    var n=JSer(this);
                    if(formatter && formatter instanceof Function) formatter.call(null, n, o);
                    arr.push(n.beforeTo(o,true)[0]);
                });
                o.remove();
            });
            return JSer(arr);
        },


        reverse: function() {
            setEnd(this);
            for (var i = 0; i < this.length / 2; i++) {
                var t = this[i];
                this[i] = this[this.length - i - 1];
                this[this.length - i - 1] = t;
            }
            return this;
        },

        serialize: function() {
            var r=[], j = 0;
            this.each(function() {
                if((this.type || '').or('radio','checkbox') && !this.checked) return;
                if (this.value != undefined && this.name){
                    if(this.tagName.eq('select')){
                        var n=this.name;
                        JSer.each(this.options, function(){
                            if(this.selected){
                                r[j++] = n+"="+encodeURIComponent(this.getAttribute('value'));
                            }
                        });
                    }else{
                        r[j++] = this.name+"="+encodeURIComponent(this.value);
                    }
                }
            });
            return r.join("&");
        },

        show: function(time, bywhat, callback) {//动画待处理: bywhat: w | h | o
            return this.each(function() {
                var jso =JSer(this);
                if(jso.data("__stop")) jso.data("__stop").call(jso);
                if (jso.css('display') != 'none'){
                    return;
                }

                var oldCss =  {
                    width: jso.width(),
                    height:jso.height(),
                    opacity: jso.opacity(),
                    overflow: jso.css('overflow')
                };

                if (!time) time = 0;
                else if ('fast'.eq(time)) time = 50;
                else if ('normal'.eq(time)) time = 100;
                else if ('slow'.eq(time)) time = 200;
                else if (isNaN(time)) time = 0;
                if(_browser.msie) time/=25;
                else if(_browser.opera || _browser.safari) time/=10;
                else if(_browser.chrome) time/=5;

                if (time <= 0) {
                    jso.css('display', '');
                    return;
                }

                function up() {
                    leavetime++;
                    if(leavetime<time){
                        if(wStep)jso.width(leavetime*wStep);
                        if(hStep)jso.height(leavetime*hStep);
                        if(oStep)jso.opacity(leavetime*oStep);
                        timer=setTimeout(arguments.callee, 0);
                    }else{
                        jso.removeData("__stop");
                        jso.css(oldCss);
                        if (callback) callback.call(jso[0]);
                    }
                }

                var timer=null;
                function stop(){
                    if(timer!=null) clearTimeout(timer);
                    this.css(oldCss);
                    this.removeData("__stop");
                }

                jso.css('overflow', 'hidden').css("display", '');
                if (typeof bywhat == 'function' && !callback) {
                    callback = bywhat;
                    bywhat = 'who';
                }
                var leavetime=0,wStep=0,hStep=0,oStep=0;
                bywhat = (bywhat || 'who').toLowerCase();
                if(bywhat.indexOf("w")!=-1){
                    wStep = jso.width()/time;
                    jso.width(0);
                }
                if(bywhat.indexOf("h")!=-1){
                    hStep = jso.height()/time;
                    jso.height(0);
                }

                if(bywhat.indexOf("o")!=-1){
                    oStep = jso.opacity()/time;
                    jso.opacity(0);
                }
                jso.data("__stop", stop);
                up();
            });
        },

        text: function(text){
            if (text !== undefined )
                return this.empty().append( (this[0] && this[0].ownerDocument || document).createTextNode( text ) );
            else if(!this[0]) return null;
            var o=this[0], cn= o.childNodes, sRet = '',val, style;
            for (var i = 0; i < cn.length; i ++)
                if (cn[i].childNodes.length != 0)
                    sRet += arguments.callee.call(JSer(cn[i]));
                else if (val=cn[i].nodeValue)
                    sRet += (style=o.currentStyle) && style.display == "block" ? val + "\n" : val;
            return sRet;
        },

        toggle: function(name, array, callback) {
            return this.each(function(){
                if(array===null){
                    JSer(this).data("__toggle:"+name, null);
                }else if(array instanceof Array){
                    JSer(this).data("__toggle:"+name, {next:0, toggles: array, callback:callback});
                }else{
                    var jso=JSer(this), t=jso.data("__toggle:"+name);
                    if(t && t.toggles.length>t.next){
                        JSer.each(t.toggles,function(){if('string'.eq(typeof this)) jso.removeClass(this);});
                        var next=t.next,o = t.toggles[next];
                        if('STRING'.eq(typeof o)){
                            var lasto = t.toggles[next==0? t.toggles.length-1:next-1];
                            if('STRING'.eq(typeof lasto)) jso.removeClass(lasto);
                            jso.addClass(o);
                        }
                        else if('FUNCTION'.eq(typeof o)) o.call(this);
                        else if('OBJECT'.eq(typeof o)) jso.css(o);
                        t.next = next >= t.toggles.length - 1 ? 0 : next + 1;
                        jso.data('__toggle:'+name, t);
                        if('FUNCTION'.eq(typeof t.callback)) t.callback.call(this, next, t.toggles);
                    }
                }
            });
        },



        toggleClass: function(r, a){
            var ra = r ? r.replace(/[;,\|]/gi, " ").trim().split(/\s+/) : [],
                aa = a ? a.replace(/[;,\|]/gi, " ").trim().split(/\s+/) : [];
            return this.each(function(){
                var o =' '+(this.className||'')+' ', n;
                if(o=='  ') n=aa.join(' ');
                else{
                    var s,i;
                    for(i=0;i<ra.length;i++) if(ra[i]!='' && o.indexOf(' '+ra[i]+' ')!=-1) o=o.replace(' '+ra[i]+' ',' ');
                    for(i=0;i<aa.length;i++) if(aa[i]!='' && o.indexOf(s=' '+aa[i]+' ')==-1) o+=s;
                    n = o.replace(/\s{2,}/,' ').trim();
                }
                this.className=n;
            });
        },

        unmenu: function(b) {
            this.each(function() {
                if (b === false) JSer(this).removeAttr('__noContextMenu');
                else JSer(this).attr("__noContextMenu", '1');
            });
            if (!document.oncontextmenu) document.oncontextmenu = function(e) {
                var t = _browser.msie ? event.srcElement : e.target;
                if (JSer(t).attr('__noContextMenu') == 1) return false;
            };
            return this;
        },

        unselect: function(b) {
            if (b === false) {
                if (_browser.firefox)
                    this.css("-moz-user-select", null);
                if (_browser.opera || _browser.msie)
                    this.attr("unselectable", "off");
                if (_browser.msie || _browser.safari || _browser.chrome)
                    this.off('selectstart');
            } else {
                if (_browser.firefox)
                    this.css("-moz-user-select", "-moz-none");
                else if (_browser.opera)
                    this.attr("unselectable", "on");
                else
                    this.on('selectstart', function() {
                        return false;
                    });
            }
            return this;
        },

        val:function(arr){
            if(arr!=undefined){
                if(! (arr instanceof Array)) arr=[arr];
                return this.each(function(){
                    if((this.type || '').or('radio','checkbox')) this.checked=arr.indexOf(this.value)!=-1;
                    else if('select'.eq(this.tagName)){
                        JSer.each(this.options, function(){this.selected = arr.indexOf(this.getAttribute('value'))!=-1;});
                    }else if(this.value!=undefined){this.value=arr.join(", ");}
                });
            }else{
                if(this.length){
                    var ra=[];
                    if(this.length==1) return this[0].value!= undefined ? this[0].value : null;
                    this.each(function(){
                        if((this.type || '').or('radio','checkbox')){
                            if(this.checked) ra.push(this.value);
                        }else if('select'.eq(this.tagName)){
                            JSer.each(this.options,function(){if(this.selected) ra.push(this.getAttribute('value'));});
                        }else if(this.value!=undefined) ra.push(this.value);
                    });
                    ra.reverse();
                    return ra;
                }else return null;
            }
        },

        width: function(val) {
            return val == undefined ? parseInt(_styleGet(this,'width', true)) : this.css('width', val);
        },

        xy: function() {
            var el,p = null, pos = [], box, els;
            if (!this.length || (el = this[0]).parentNode === null || el.style.display == 'none') return {x:0, y:0};
            if (el.getBoundingClientRect) {    //msie & firefox
                box = el.getBoundingClientRect();
                var dd = document.documentElement, db = document.body;
                var scrollTop = Math.max(dd.scrollTop, db.scrollTop);
                var scrollLeft = Math.max(dd.scrollLeft, db.scrollLeft);
                return {x:box.left + scrollLeft, y:box.top + scrollTop};
            } else if (document.getBoxObjectFor) {    // gecko & chrome
                box = document.getBoxObjectFor(el); els = el.style;
                var borderLeft = (els.borderLeftWidth) ? parseInt(els.borderLeftWidth) : 0;
                var borderTop = (els.borderTopWidth) ? parseInt(els.borderTopWidth) : 0;
                pos = [box.x - borderLeft, box.y - borderTop];
            } else {    // safari & opera
                pos = [el.offsetLeft, el.offsetTop];
                p = el.offsetParent;
                if (p != el) {
                    while (p) {
                        pos[0] += p.offsetLeft;
                        pos[1] += p.offsetTop;
                        p = p.offsetParent;
                    }
                }
                if (_browser.opera || ( _browser.safari && el.style.position == 'absolute' )) {
                    var db1 = document.body;
                    pos[0] -= db1.offsetLeft;
                    pos[1] -= db1.offsetTop;
                }
            }
            p = el.parentNode;
            while (p && !String(p.tagName).or('BODY', 'HTML')) {
                pos[0] -= p.scrollLeft;
                pos[1] -= p.scrollTop;
                p = p.parentNode;
            }
            return {x:pos[0], y:pos[1]};
        }
    };

    var Url = function(url) {
        var _loc = document.location;
        if (url == null || url == "") url = _loc.href;
        var anchor = "";
        if (url.indexOf("#") != -1) {
            anchor = url.substring(url.indexOf("#"));
            url = url.substring(0, url.indexOf("#"));
        }
        var file, prma, t_mh;
        if (t_mh = url.match(/(.*?)\?(.*)/)) {
            file = t_mh[1];
            prma = t_mh[2];
        } else if (t_mh = url.match(/(.+?=.*)+/)) {
            file = "";
            prma = url;
        }else{
            file=url;
            prma="";
        }
        var _names = ":"+prma.replace(/=[^&]*/g,"=:").replace(/&/g,"");
        prma =  prma=='' ? [] :prma.replace(/&amp;/i,'&').split("&");


        this.set = function(name, value) {
            if (typeof(name) == 'string' && name!='') {
                this.del(name);
                if (value != null) {
                    prma.push(name+"="+encodeURIComponent(value));
                } else {
                    var sa = name.replace(/&amp;/i,"&").split("&");
                    if(sa.length) prma=prma.concat(sa);
                }
            } else if (typeof(name) == 'object'){
                for (var pty in name) {
                    if(name.hasOwnProperty(pty)){
                        var t = name[pty];
                        if (typeof(t) == 'object' || typeof(t) == 'function') continue;
                        this.set(pty,t);
                    }
                }
            }
            return this;
        };

        this.sel = function(a, b){
            return this.set(JSer(a,b).serialize());
        };

        this.getF = function() {
            return file;
        };

        this.setF = function(f) {
            file = (t_mh = f.match(/.*\?(.+)/)) ? t_mh[1] : f;
            return this;
        };

        this.get = function(name) {
            if (name) {
                var sa=[],j=0;
                for(var i= 0,l=prma.length;i<l;i++){
                    if(prma[i].indexOf(name+"=")==0) sa[j++]=decodeURIComponent(prma[i].substring(name.length+1));
                }
                if(sa.length)
                    return sa.length==1?sa[0]:sa;
                else
                    return null;
            } else {
                return prma.join("&");
            }
        };

        this.del = function() {
            var da=[], ds;
            JSer.each(arguments, function() {
                JSer.each(this.replace(/[;,]/, " ").split(/[\s;,]+/), function() {
                    da.push(this+"=");
                });
            });
            ds= "|" + da.join("|");
            for(var i=prma.length;i--;)
                if(ds.indexOf("|"+prma[i].split("=")[0]+"=")!=-1){
                    prma.splice(i, 1);
                }
            return this;
        };

        this.rdel = function(){
            var da=[], ds;
            JSer.each(arguments, function() {
                JSer.each(this.split(/[\s;,]+/), function() {
                    da.push(this+"=");
                });
            });
            ds= "|" + da.join("|");
            for(var i=prma.length;i--;)
                if(ds.indexOf("|"+prma[i].split("=")[0]+"=")==-1)
                    prma.splice(i, 1);
            return this;
        };

        this.anchor=function(b){
            if(b){
                anchor=b.charAt(0)=='#'?b:"#"+b;
                return this;
            }else{
                return anchor==''? null : anchor.substring(1);
            }
        };

        this.toString = function() {
            url = file + (prma.length ? "?" + prma.join("&") : "");
            return url + anchor;
        };

        //接受target,modal,width,height,left,top参数,left,top为负时自动居中,modal=1时,只有IE才启用modal.
        this.open = function(prm, height) {
            if( prm && height && !isNaN(prm) && !isNaN(height) ) prm = {width: prm, height: height};
            else prm = 'OBJECT'.eq(typeof prm) && prm || {
                target:'',
                modal:false,
                width:800,
                height:600,
                left:-1,
                top:-1
            };

            var w = parseInt(prm.width) || 600,h = parseInt(prm.height) || 800,
                l = isNaN(prm.left) ? -1 : prm.left, t = isNaN(prm.top) ? -1 : prm.top;
            if (l < 0)  l = Math.max((screen.availWidth - w) / 2, 0);
            if (t < 0)  t = Math.max((screen.availHeight - h) / 2, 0);

            if (prm.modal && _browser.msie) {//为保证兼容,模态窗口不应该有返回值.
                showModalDialog(this.toString(), window, "edge:Raised;resizable:Yes;status:No;dialogWidth:" + w +
                    "px;dialogHeight:" + h + "px;dialogLeft:" + l + "px;dialogTop:" + t + "px");
                return null;
            } else {
                var win = open(this.toString(), prm.target, "resizable=yes,statusbar=yes,scrollbars=yes,width=" + w + ",height=" + h);
                if (!win) {
                    alert('似乎某个弹出窗口拦截器导致了无法打开该网页。\n\n如果您的浏览器使用了弹出窗口拦截，请尝试关闭它。');
                    return null;
                } else {
                    win.moveTo(l, t);
                    win.focus();
                    return win;
                }
            }
        };

        this.go = function(target) {
            var tUrl = this.toString(), wl;
            if (target == 0) {
                _loc.href == tUrl ? _loc.reload() : _loc.replace(tUrl);
            } else if (target) {
                if ('_blank'.eq(target))  top.window.open(tUrl);
                else if ('_top'.eq(target))  top.location.href = tUrl;
                else if ('_parent'.eq(target)) parent.location.href = tUrl;
                else if ('_self'.eq(target))   _loc.href = tUrl;
                else if ('_opener'.eq(target)){
                    var win = window.opener || (wl=window.dialogArguments) && wl.opener;
                    if(win && (wl=win.document.location)) wl.href==tUrl ? wl.reload():wl.href=tUrl;
                }else {
                    var o = JSer("#"+target+", frame@name="+target+ ", iframe@name="+target);
                    if(o.length) o.attr('src',tUrl);
                    else
                        try {
                            (top.document.getElementById(target) || top.window.frames[target]).setAttribute('src',tUrl);
                        } catch(e1) {}
                }
            } else {
                if (_loc.href == tUrl) {
                    _loc.reload();
                } else {
                    _loc.href = tUrl;
                }
            }
        };

        this.loadTo = function(selector, callback) {
            var o = JSer(selector);
            this.ajax(function(d) {
                o.html(d);
                if(callback && callback instanceof Function) callback.call(o, d);
            });
        };

        this.ajax = function(obj) {
            var _default = {
                method: "AUTO",
                type: "HTML",
                async: true,
                timeout:0,
                cache:true,
                ifModified:false,
                enctype:"application/x-www-form-urlencoded",
                username:null,
                password:null,
                send:null,
                beforeSend:null,
                success: null,
                error: null,
                complete:null
            };

            var gData=[], pData=[]; //init: 'getConnection' what, or 'post' what.
            for(var i= 0,l=prma.length;i<l;i++){
                if(_names.indexOf(":"+prma[i].split("=")[0]+"=:")==-1){
                    pData.push(prma[i]);
                }else{
                    gData.push(prma[i]);
                }
            }
            if(obj.cache===false)gData.push("_="+new Date().getTime());


            if(!obj) obj=_default;
            else if('FUNCTION'.eq(typeof obj)) obj=JSer.merge(_default, {success:obj});
            else obj=JSer.merge(_default, obj);

            if(obj.type) obj.type=(obj.type+'').toLowerCase();

            if(obj.method=='AUTO') obj.method = pData.length ? "POST":"GET";

            var oajax, requestUrl = this.toString();
            if (window.ActiveXObject){
                try{
                    oajax = new ActiveXObject("Msxml2.XMLHTTP");
                }catch(e){
                    oajax = new ActiveXObject("Microsoft.XMLHTTP");
                }
            }else if (window.XMLHttpRequest) oajax = new XMLHttpRequest();
            else throw "unsupport ajax.";

            var owner=this, finished=false, timer=null, doReturn = function() {
                    var rtn;
                    if ('XML'.eq(obj.type)) {
                        rtn = oajax.responseXML;
                        if(rtn!=null && "parsererror".eq(rtn.documentElement.tagName)) rtn = "parsererror";
                    } else {
                        rtn = (oajax.responseText || "").trim();
                        if ('JSON'.eq(obj.type)) {
                            try{rtn = eval( "(" + rtn + ")" );}catch(e){
                                throw "JSer: ajax returned data is not a json object.";
                            }
                        } else if ('SCRIPT'.eq(obj.type)) {
                            _execScript(rtn);
                        }
                    }
                    return rtn;
                },  now = new Date().getTime(), reqUrl = 'POST'.eq(obj.method) && !obj.send
                    ? JSer.url(this.getF()).set(gData.join("&")).toString()
                    : (obj.cache===false?JSer.url(requestUrl).set("_", now) :this).toString()
                , onReadyStateChange = function(state){

                    if(finished) return; finished=true;

                    if(state=='timeout'){
                        oajax.abort();
                    }

                    if(state.or('success', 'notmodified')){
                        var d = doReturn();
                        if('string'.eq(typeof d) && d=='parsererror' && 'XML'.eq(obj.type)) state=d;
                        else if('FUNCTION'.eq(typeof obj.success)) obj.success.call(owner, d, state);
                    }

                    if(state!='success' && 'FUNCTION'.eq(typeof obj.error)) obj.error.call(owner, oajax, state);

                    if ('FUNCTION'.eq(typeof obj.complete)) obj.complete.call(owner, oajax, state);

                    if(timer){ clearTimeout(timer);timer=null; }

                };

            if (obj.async) oajax.onreadystatechange = function() {
                if (oajax.readyState == 4) {
                    // status 0 for opera  1223 for IE
                    if (oajax.status >= 200 && oajax.status<300 || oajax.status == 304
                        || oajax.status==1223 || oajax.status==0) {
                        if(obj.ifModified){
                            var m = oajax.getResponseHeader("Last-Modified"), e=oajax.getResponseHeader("Etag");
                            if(m) _ajaxLastModified[requestUrl] = m;
                            if(e) _ajaxEtag[requestUrl] = e;
                        }
                        onReadyStateChange(oajax.status == 304||oajax.status==0?"notmodified":"success");
                    } else {
                        onReadyStateChange("error");
                    }
                }
            };

            if(obj.send && "GET".eq(obj.method)) obj.method="POST";//don't move up!
            if(obj.username) oajax.open(obj.method,reqUrl, obj.async, obj.username, obj.password);
            else oajax.open(obj.method,reqUrl, obj.async);
            oajax.setRequestHeader("CONTENT-TYPE", obj.enctype);
            if(obj.ifModified){
                var m = _ajaxLastModified[requestUrl], e=_ajaxEtag[requestUrl];
                if(m) oajax.setRequestHeader("If-Modified-Since", m);
                if(e) oajax.setRequestHeader("If-None-Match", e);
            }
            oajax.setRequestHeader("Accept",_ajaxAccepts[obj.type] || _ajaxAccepts['other']);
            oajax.setRequestHeader("Ajax-Agent", "www.jdiy.org/JSer-AJAX");

            if(obj.beforeSend && 'FUNCTION'.eq(typeof obj.beforeSend) && obj.beforeSend.call(this, oajax)===false){
                oajax.abort();
                return;
            }
            if(obj.send) oajax.send(obj.send);
            else if('GET'.eq(obj.method)) oajax.send(null);
            else oajax.send(pData.join("&"));
            if (!obj.async) {
                if('SCRIPT'.eq(obj.type))_execScript((oajax.responseText || "").trim());
                return oajax;
            }else{
                if(obj.timeout && !isNaN(obj.timeout)){
                    timer = setTimeout(function(){
                        onReadyStateChange("timeout");
                    }, obj.timeout);
                }
            }
        }
    };

    JSer.url = function(url) {
        return new Url(url);
    };

    JSer.cookie = function(name, value, expires, path, domain, secure){
        if('object'.eq(typeof name)){
            for(s in name) if(name.hasOwnProperty(s)) JSer.cookie(s, name[s], value, expires, path, domain);
        }else if(value===undefined){
            var sa = (document.cookie ||'').split(";"),s;
            for(var i= 0,l=sa.length;i<l;i++)
                if((s=sa[i].trim()).indexOf(name+"=")==0)
                    return decodeURIComponent(s.substring(name.length+1));
            return null;
        }else if('string'.eq(typeof name)){
            function expDate(minu){
                var e = new Date();
                if(!isNaN(minu))e.setMinutes(e.getMinutes()+parseInt(minu));
                return e.toGMTString();
            }
            if(value===null){
                value = '';expires = -100;
            }
            document.cookie = name + "=" + encodeURIComponent(value) +
                ((expires) ? "; expires=" + expDate(expires) : "") +
                ((path) ? "; path=" + path : "") +
                ((domain) ? "; domain=" + domain : "") +
                ((secure) ? "; secure" : "");
        }
        return this;
    };

    JSer.css = function(href){
        var c = document.createElement("link");
        c.setAttribute('type', 'text/css');
        c.setAttribute('rel', 'stylesheet');
        c.setAttribute('href', href);
        JSer("head")[0].appendChild(c);
    };

    JSer.exec = function(fn) {
        _taskExec();
        if(typeof fn =='string'){
            _execScript({src:fn});
        }else{
            if (_taskLoaded) fn.call(document, JSer);
            else _taskAdd(function() {
                return fn.call(this, JSer);
            });
        }
        return this;
    };

    JSer.each = function(obj, callback) {
        if(!obj) return obj;
        var len = obj.length, i;
        if (len == undefined) {
            for (i in obj) if (obj.hasOwnProperty(i) && callback.call(obj[i], i) === false) break;
        } else {
            for (i=len; i--;) if (callback.call(obj[i], i) === false) break;
        }
        return obj;
    };


    JSer.extend = function(method, fn) {
        if ('OBJECT'.eq(typeof method) && !fn) {
            JSer.each(method, function(i) {
                Element.prototype[i] = this;
            });
        } else if ('STRING'.eq(typeof method) && fn) {
            Element.prototype[method] = fn;
        }
        return this;
    };

    JSer.favorite = function(title, url) {
        if (!title)title = window.self.title;
        if (!url)url = JSer.url().toString();
        if (window.sidebar && 'function'.eq(typeof window.sidebar.addPanel))
            window.sidebar.addPanel(title, url, '');
        else if (window.external)
            window.external.AddFavorite(url, title);
        else alert("请按快捷键CTRL+D将本页添加到收藏夹.");
    };

    JSer.merge = function(obj1, obj2) {
        for (var i in obj2) if(obj2.hasOwnProperty(i)) obj1[i] = obj2[i];
        return obj1;
    };

    JSer.each(("abort,blur,change,click,dblclick,error,focus,keydown,keypress,keyup,load,"
        + "mousedown,mousemove,mouseout,mouseover,mouseup,reset,resize,select,submit,"
        + "unload,scroll").split(","), function() {
        var h = this;
        Element.prototype[this] = function(handler) {
            return this.on(h, handler);
        };
    });

    JSer.toJSON = function(obj){
        return (function(s,t) {
            var i, arr=[];
            if(s===null || s===undefined) return s;
            switch(s.constructor){
                case String:
                    return '"' + s.replace(/\r/g,'\\r').replace(/\n/g,'\\n')
                        .replace(/"/g,'\\"').replace(/\t/g,'\\t') + '"';
                case Number:
                case Boolean:
                    return s;
                case Date:
                    return 'new Date('+ s.getTime()+')';
                case Array:
                    var arr1=[];
                    for(i=0;i< s.length;i++)
                        arr1.push(String(arguments.callee(s[i], t+"   ")).replace(/\r\n|\n/g,"\r\n   "));
                    return '[\r\n   '+t + arr1.join(', \r\n   '+t) + '\r\n'+t+']';
                case Function:
                    return String(s).replace(/\r\n|\n/g,"\r\n"+t).replace(/(\{\s*)(\[native code\])(\s*\})/,'$1"$2"$3');
            }
            for (i in s)
                if(s.hasOwnProperty && s.hasOwnProperty(i))
                    arr.push('   "' + i + '": ' + arguments.callee(s[i],t+'   '));
            return '{\r\n'+t + arr.join(',\r\n'+t) + '\r\n'+t+'}';
        })(obj, '');
    };

    JSer.clone = function(o){
        try{
            var n = o.constructor ? (o.constructor == Object ? new o.constructor() : new o.constructor(o.valueOf())) :o;
            for (var k in o) if (o.hasOwnProperty(k) && n[k] != o[k]) n[k] = typeof(o[k]) == 'object' ? JSer.clone(o[k]) : o[k];
            return n;
        }catch(e){
            return null;
        }
    };


    //**********  global object prototype expand:

    String.prototype.trim = function() {
        var t = this.replace(/^\s+/,"");
        for(var i=t.length;i--;)
            if(/\S/.test(t.charAt(i))){
                t=t.substring(0,i+1);
                break;
            }
        return t;
    };

    String.prototype.eq = function() {
        for (var i = 0; i < arguments.length; i++) {
            var o = arguments[i];
            if (o == null || this.toUpperCase() != (o + '').toUpperCase()) return false;
        }
        return true;
    };

    String.prototype.or = function() {
        for (var i = 0; i < arguments.length; i++) {
            var o = arguments[i];
            if (o != null && this.toUpperCase() == (o + '').toUpperCase()) return true;
        }
        return false;
    };

    Array.prototype.indexOf = function(o) {
        for (var i = 0; i < this.length; i++)//don't use each
            if (this[i] == o) return i;
        return -1;
    };
})();