<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>策略设置说明</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/chosen.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.sorted.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/ckform.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/common.js"></script>
    <style type="text/css">
        body {
            padding-bottom: 40px;
        }
        .sidebar-nav {
            padding: 9px 0;
        }

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
<table class="table table-bordered table-hover definewidth m10">
				<tr>
					<th colspan="5">时间格式: [秒] [分] [小时] [日] [月] [周] [年]</th>
				</tr>
				<tr>
					<td>序号</td>
					<td>说明</td>
					<td>是否必填</td>
					<td>允许填写的值</td>
					<td>允许的通配符</td>
				</tr>
				<tr>
					<td>1</td>
					<td>秒</td>
					<td>是</td>
					<td>0-59</td>
					<td>, - * /</td>
				</tr>
				<tr>
					<td>2</td>
					<td>分</td>
					<td>是</td>
					<td>0-59</td>
					<td>, - * /</td>
				</tr>
				<tr>
					<td>3</td>
					<td>小时</td>
					<td>是</td>
					<td>0-23</td>
					<td>, - * /</td>
				</tr>
				<tr>
					<td>4</td>
					<td>日</td>
					<td>是</td>
					<td>1-31</td>
					<td>, - * ? / L W</td>
				</tr>
				<tr>
					<td>5</td>
					<td>月</td>
					<td>是</td>
					<td>1-12 or JAN-DEC</td>
					<td>, - * /</td>
				</tr>
				<tr>
					<td>6</td>
					<td>周</td>
					<td>是</td>
					<td>1-7 or SUN-SAT</td>
					<td>, - * ? / L #</td>
				</tr>
				<tr>
					<td>7</td>
					<td>年</td>
					<td>否</td>
					<td>empty 或 1970-2099</td>
					<td>, - * /</td>
				</tr>
				</tbody>
			</table>
			<p>&nbsp;</p>
			<table class="table table-bordered table-hover definewidth m10">
				<tr>
					<th colspan="2">常用示例</th>
				</tr>
				<tr>
					<td>0/1 * * * * ?</td>
					<td>每1秒触发</td>
				</tr>
				<tr>
					<td width="220" height="30">*/10 * * * * ?</td>
					<td>每10秒触发</td>
				</tr>
				<tr>
					<td width="220" height="30">0 0/5 * * * ?</td>
					<td>每5分钟触发</td>
				</tr>
				<tr>
					<td>0 0 12 * * ?</td>
					<td>每天12点触发</td>
				</tr>
				<tr>
					<td>0 15 10 ? * *</td>
					<td>每天10点15分触发</td>
				</tr>
				<tr>
					<td>0 15 10 * * ?</td>
					<td>每天10点15分触发</td>
				</tr>
				<tr>
					<td>0 15 10 * * ? *</td>
					<td>每天10点15分触发</td>
				</tr>
				<tr>
					<td>0 15 10 * * ? 2005</td>
					<td>2005年每天10点15分触发</td>
				</tr>
				<tr>
					<td>0 * 14 * * ?</td>
					<td>每天下午的 2点到2点59分每分触发</td>
				</tr>
				<tr>
					<td>0 0/5 14 * * ?</td>
					<td>每天下午的 2点到2点59分(整点开始，每隔5分触发)</td>
				</tr>
				<tr>
					<td>0 0/5 14,18 * * ?</td>
					<td>每天下午的 18点到18点59分(整点开始，每隔5分触发)</td>
				</tr>
				<tr>
					<td>0 0-5 14 * * ?</td>
					<td>每天下午的 2点到2点05分每分触发</td>
				</tr>
				<tr>
					<td>0 10,44 14 ? 3 WED</td>
					<td>3月分每周三下午的 2点10分和2点44分触发</td>
				</tr>
				<tr>
					<td>0 15 10 ? * MON-FRI</td>
					<td>从周一到周五每天上午的10点15分触发</td>
				</tr>
				<tr>
					<td>0 15 10 15 * ?</td>
					<td>每月15号上午10点15分触发</td>
				</tr>
				<tr>
					<td>0 15 10 L * ?</td>
					<td>每月最后一天的10点15分触发</td>
				</tr>
				<tr>
					<td>0 15 10 ? * 6L</td>
					<td>每月最后一周的星期五的10点15分触发</td>
				</tr>
				<tr>
					<td>0 15 10 ? * 6L 2002-2005</td>
					<td>从2002年到2005年每月最后一周的星期五的10点15分触发</td>
				</tr>
				<tr>
					<td>0 15 10 ? * 6#3</td>
					<td>每月的第三周的星期五开始触发</td>
				</tr>
				<tr>
					<td>0 0 12 1/5 * ?</td>
					<td>每月的第一个中午开始每隔5天触发一次</td>
				</tr>
				<tr>
					<td>0 11 11 11 11 ?</td>
					<td>每年的11月11号 11点11分触发(光棍节)</td>
				</tr>
</table>
</body>
</html>
