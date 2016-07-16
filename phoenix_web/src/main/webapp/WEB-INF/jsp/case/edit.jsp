<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/chosen.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/codemirror/lib/codemirror.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/codemirror/addon/fold/foldgutter.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/codemirror/addon/dialog/dialog.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/codemirror/theme/monokai.css">
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/codemirror/lib/codemirror.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/codemirror/addon/search/searchcursor.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/codemirror/addon/search/search.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/codemirror/addon/dialog/dialog.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/codemirror/addon/edit/matchbrackets.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/codemirror/addon/edit/closebrackets.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/codemirror/addon/comment/comment.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/codemirror/addon/wrap/hardwrap.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/codemirror/addon/fold/foldcode.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/codemirror/addon/fold/brace-fold.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/codemirror/mode/javascript/javascript.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/codemirror/keymap/sublime.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/xheditor/xheditor-1.2.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/xheditor/xheditor_lang/zh-cn.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.sorted.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/ckform.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/chosen.jquery.js"></script>
    
    
    <style type="text/css">
        body {
            padding-bottom: 40px;
        }
        .sidebar-nav {
            padding: 9px 0;
        }
        .CodeMirror {border-top: 1px solid #eee; border-bottom: 1px solid #eee; line-height: 1.3; height: 500px}
  		.CodeMirror-linenumbers { padding: 0 8px; }

        @media (max-width: 980px) {
            /* Enable use of floated navbar text */
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }
    </style>
</head>
<sf:form method="post" action="editor" modelAttribute="caseDTO">
<sf:hidden path="id" value="${caseBean.id }"/>
<textarea id="code" style="display:none">${caseBean.codeContent }</textarea>
<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <td width="10%" class="tableleft">所属场景</td>
        <td>
        <sf:select data-placeholder="请选择一个场景" class="chosen-select" style="width:210px;" tabindex="2" path="scenId">
           <option value=""></option>
           <c:forEach items="${scenList }" var="sl">
             <c:choose>             
               <c:when test="${sl.id eq caseBean.scenarioBean.id }">
                  <sf:option selected="selected" value="${sl.id }">${sl.scenarioName }</sf:option>
               </c:when>
               <c:otherwise>
                  <sf:option value="${sl.id }">${sl.scenarioName }</sf:option>
               </c:otherwise>
             </c:choose>
           </c:forEach>
        </sf:select>
        </td>
    </tr>
    <tr>
        <td width="10%" class="tableleft">用例名称</td>
        <td><sf:input path="caseName" value="${caseBean.caseName}"/><sf:errors path="caseName"/></td>
    </tr>
    <tr>
        <td class="tableleft">用例说明</td>   
        <td><sf:input path="remark" value="${caseBean.remark}"/><sf:errors path="remark"/></td>
    </tr> 
    <tr>
        <td class="tableleft">用例类型</td>
        <td>${caseBean.caseType }</td>
    </tr>  
    <tr>
        <td class="tableleft">启用状态</td>
        <td>
        	<input type="radio" checked="checked" value="1" name="status">已启用&nbsp;&nbsp;&nbsp;&nbsp;
        	<input type="radio" <c:if test="${caseBean.status eq 0}">checked="checked"</c:if> value="0" name="status">未启用
        </td>
    </tr>
    <tr>
    	<td class="tableleft">消息发送类型</td>
    	<td>
    		<sf:select path="msgSendType">
    			<c:forEach items="${msgSendTypes }" var="ms">
    				<c:choose>
    					<c:when test="${ms.key eq  caseBean.msgSendType}">
    						<sf:option value="${caseBean.msgSendType }" selected="selected">${caseBean.msgSendType }</sf:option>
    					</c:when>
    					<c:otherwise>
    						<sf:option value="${ms.key }">${ms.value }</sf:option>
    					</c:otherwise>
    				</c:choose>
    			</c:forEach>
    		</sf:select>
    		&nbsp;&nbsp;
    		<c:choose>
    			<c:when test="${caseBean.deleteMsg eq true }">
    				<input type="checkbox" name="deleteMsg" checked="checked"/>
    			</c:when>
    			<c:otherwise>
    				<input type="checkbox" name="deleteMsg">
    			</c:otherwise>
    		</c:choose>
    		发送成功后删除消息
    	</td>
    </tr>
    <tr>
        <td class="tableleft">脚本内容</td>
        <td>
            <!-- cssClass="xheditor-simple" -->
            <textarea id="codeContent" name="codeContent" style="height:500px;width:100%;"></textarea>
        </td>
    </tr>
    <tr>
        <td class="tableleft"></td>
        <td>
            <button type="submit" class="btn btn-primary">提交</button>&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>
        </td>
    </tr>
</table>
</sf:form>
<script type="text/javascript">
    $(function () {       
		var editor = CodeMirror.fromTextArea(document.getElementById("codeContent"), {
		    lineNumbers: true,
		    mode: "javascript",
		    keyMap: "sublime",
		    autoCloseBrackets: true,
		    matchBrackets: true,
		    showCursorWhenSelecting: true,
		    theme: "monokai",
		    tabSize: 2
		  });
		$('#backid').click(function(){
			var localObj = window.location;
			var contextPath = localObj.pathname.split("/")[1];
			var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
			window.location.href=basePath+"/case/list";
		 });
		editor.setValue($("#code").val());
    });
</script>
  <script type="text/javascript">
  $(".chosen-select").chosen();

  </script>
</body>
</html>
