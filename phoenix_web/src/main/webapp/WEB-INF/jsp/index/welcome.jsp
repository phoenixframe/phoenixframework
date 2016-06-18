<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>系统说明</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
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
<body>
<table class="table table-bordered table-hover definewidth m10" >
        <tr>
           <th colspan="3" align="center">版本说明</th>
        </tr>
        <tr>
            <td>版本1.4.8</td>
            <td>
1、phoenix_node:优化性能测试时，监控机的CPU及内存数据等的可读性<br>
2、phoenix_web:增加了ehcache缓存<br>
3、phoenix_web:增加数据库连接池查看<br>
4、phoenix_webdriver:commandExecutor方法bug修复<br>
5、phoenix_webdriver:检查点bug修复<br>
6、phoenix_web:定时任务细节bug修复<br>
7、phoenix_webdriver:增加js执行的驱动，再也无需手动强转<br>
8、phoenix_web:修复接口测试结果统计bug<br>
9、phoenix_web:个别页面的js重构<br>
10、phoenix_node:性能测试相关模版细节bug修复<br>
11、phoenix_web:多个地方增加删除确认提示<br>
12、phoenix_web:日志增加批量删除方法<br>
13、phoenix_webdriver:selenide更新到3.6,selenium更新到2.48稳定版（兼容safari），selendroid更新到0.17<br>
14、phoenix_webdriver:支持最新的Firefox47/chrome50/IE10/IE11/IE Edge版本。IE驱动更新到2.53，chrome驱动更新到2.21（增加32位支持）<br>
			</td>
            <td>2016.06.18</td>
        </tr>
        <tr>
            <td>版本1.4.7</td>
            <td>
1、修复多个反人类的唯一性约束<br>
2、phoenix_node:jmeter性能测试增加对body参数的支持<br>
3、对平台的各模块代码进行了部分重构，重构后的效果是插件可配置<br>
4、在phoenix_web端增加查看node详细信息的入口<br>
5、phoenix_interface增加对https地址的支持<br>
6、phoenix_develop中增加了一个自己写的并发测试工具<br>
7、抽离出了公共的phoenix_common模块<br>
8、重构了平台项目组织架构，使导入调试等更方便<br>	
			</td>
            <td>2016.03.20</td>
        </tr>
        <tr><th colspan="3" align="center">联系方式</th></tr>
        <tr>
           <td>email</td>
           <td colspan="2">5156meng.feiyang@163.com</td>
        </tr>
        <tr>
           <td>网站</td>
           <td colspan="2"><a href="http://www.cewan.la" target="_blank">http://www.cewan.la</a>（测完啦！）</td>
        </tr>
        <tr>
           <td>QQ群</td>
           <td colspan="2">246776066</td>
        </tr>
        <tr><th colspan="3" align="center">系统支持类型</th></tr>
        <tr>
           <td>目前最新版本</td>
           <td colspan="2">支持Web GUI/Web接口/移动mobile的自动化测试和性能测试、Web GUI/Web接口自动监控与报警，并支持对svn，socketserver，ftpserver服务器的操作。</td>
        </tr>
        <tr>
           <td>下一版本计划</td>
           <td colspan="2">增加对redis数据库支持,phoenix_web增加测试报告汇总页面</td>
        </tr>
        <tr><th colspan="3" align="center">平台说明</th></tr>
        <tr>
           <td>部署方式</td>
           <td colspan="2">J2EE，Jenkins，maven，J2SE，分布式部署，Jetty部署</td>
        </tr>
        <tr>
           <td>技术说明</td>
           <td colspan="2">Apache quartz，Webmagic，httpunit，selendroid，
selenide，Spring+SpringMVC+Hibernate4+Shiro，Executor，Forkjoin，Maven项目管理，
Bootstrap，JQuery，JDK动态编译+反射+执行，DWR，highchat </td>
        </tr>
        <tr>
           <td>模块划分</td>
           <td colspan="2">
phoenix_develop：用例代码开发模块<br>
phoenix_node：分布式执行node节点<br>
phoenix_web：平台控制端<br>
phoenix_webdriver：webGUI自动化测试模块<br>
phoenix_mobiledriver：移动设备测试模块<br>
phoenix_interface：接口测试系统<br>
phoenix_db:数据库操作模块，对hibernate4的封装<br>	
phoenix_ftpclient：ftp服务器操作<br>
phoenix_svnclient：对svn进行操作<br>
phoenix_telentclient：对socket服务器进行操作<br>
phoenix_imgreader：验证码及图片的识别模块<br>
phoenix_browser：phoenix定制浏览器，用于属性录制<br>
phoenix_recorder：用于对执行过程录制回放<br>
phoenix_tcpserver：可独立部署在Windows和Linux，用于特殊场景下的操作。如生成或执行shell	<br>
phoenix_jbehave：行为驱动支持<br>
phoenix_jmeter：基于jmeter定制的专门用于web系统性能测试的模块<br>
		</td>
        </tr>
     </table>   
</body>
</html>
