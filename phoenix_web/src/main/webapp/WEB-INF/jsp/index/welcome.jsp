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
            <td>版本1.4.6</td>
            <td>
1、定制浏览器：phoenix-browser<br>
2、行为驱动插件：phoenix-behave<br>
3、FTP连接客户端：FTPClient<br>
4、直连socket服务器插件：SocketClient<br>
5、图片文字识别插件：ImageReader<br>
6、svn客户端插件：SVNClient<br>
7、移动设备测试插件：phoneix-mobile,android/ios,MonkeyTest<br>
8、接口测试插件：phoenix-interface<br>
9、webUI自动化测试：phoneix-webdriver<br>
10、录制回放插件：phoenix-recorder<br>
11、phoenix_node:修复邮件发送相关的bug，增加对jmeter用例的执行<br>
12、phoenix_db:修复Druid支持问题<br>
13、phoenix_telnetclient:修复了读取响应流时可能引起的内存溢出问题	<br>	
14、phoenix_interface：增加对xml，随机字符，加密解密，list分割等工具类,增加对host 的配置,增加post请求时添加附件字段或文件方法<br>
15、phoenix_webdriver：修复webUI操作无效bug,增加启动url时的host支持,增加了HTMLunit驱动,webCase增加了多批次数据的支持<br>
16、phoenix_web：批量添加数据更方便,增加任务列表中可直接查看最后一次的批次日志,在任务列表中可直接跳转到任务数据筛选界面。<br>
17、phoenix_web：增加对jmeter任务的配置，jmeter任务监控，分机资源监控，以及监控图等。增加jmeter插件支持参数文件，支持csv和其他普通文本文件<br>
18、phoenix_web:引入shiro作为认证、授权、加密和会话管理器。<br>	
			</td>
            <td>2016.01.16</td>
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
           <td colspan="2">增加对redis数据库支持</td>
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
