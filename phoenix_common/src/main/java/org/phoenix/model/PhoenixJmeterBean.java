package org.phoenix.model;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.phoenix.utils.GetNow;

/**
 * Jmeter执行参数配置
 * @author mengfeiyang
 *
 */
@Entity
@Table(name="t_performance")
@BatchSize(size=30)
public class PhoenixJmeterBean {
	private int id;
	private int userId;
	private int slaveId;
	private String status;
	private Set<PerfBatchLogModel> perfBatchLogModels;
	private HashMap<String,String> params;
	private String caseName = "Jmeter用例";
	
	private String numThreads = "1";
	private String rampTime = "0";
	private String sampleErrorControl = "continue";
	private String continueForever = "false";
	private String controllerLoops = "1";
	private String requestImpl = "HttpClient4";
	
	private String taskAssort = "false";
	private String startTime = GetNow.getCurrentTime();
	private String endTime = GetNow.getCurrentTime();
	private String duration = "0";
	private String delayTime = "0";
	private String delayedStart = "true";
	
	private String fullUrl = "";
	private String domainURL = "";
	private String connectTimeOut = "";
	private String responseTimeOut = "";
	private String contentEncoding = "UTF-8";
	private String requestProtocol = "http";
	private String requestMethod = "GET";
	private String urlPort = "-1";
	private String urlPath = "";

	private String checkPointValue = "";
	private String checkPointType = "2";
	
	private String clearCache = "false";
	
	private String emailAttemper = "false";
	private String successLimit  = "2";
	private String failureLimit = "2";
	private String failureSubject = "Jmeter测试结果至少有"+failureLimit+"项失败";
	private String successSubject = "Jmeter测试结果至少有"+successLimit+"项成功";
	private String fromAddress = "";
	private String smtpHost = "";
	private String sendTo  = "";
	private String emailServerLoginName = "";
	private String emailServerLoginPassword = "";
	private String authType = "SSL";
	
	private String enableRendzvous = "false"; 
	private String groupSize = "1";
	private String rendzvousTimeOut = "30000";
	
	private String enableThinkTime = "false";
	private String thinkTime = "0";
	
	private String checkType = "response_data";
	
	private String enableProxy = "false";
	private String proxyHost = "";
	private String proxyPort = "80";
	private String proxyUserName = "";
	private String proxyPassword = "";
	
	private String enableDataSet = "false";
	private String fileEncoding = "UTF-8";
	private String fileName = "";
	private String filePath = "";
	private String recycle = "true";
	private String variableNames = "";
	
	private String enableHeaders  = "false";
	private String requestHeaders = "";
	
	private String className = "";
	private String methodName = "";
	
	private String monitedSlaves = "";
	
	private String useBodyString="false";
	private String bodyString = "";
	
	private HashMap<String,String> analysisHeaders(){
		if(!"".equals(requestHeaders.trim()) && !requestHeaders.equals("\n")){
			HashMap<String,String> headMap = new HashMap<String,String>();
			setEnableHeaders("true");
			String[] headers = requestHeaders.split("\n");
			for(String header : headers){
				if(header.contains("->")){
					String[] h = header.split("->");
					headMap.put(h[0], h[1]);
				}
			}
			return headMap;
		}else setEnableHeaders("false");
		return null;
	}
	
	public HashMap<String,Object> toHashMap(){
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("caseName",caseName);
		hashMap.put("numThreads",numThreads);        
		hashMap.put("rampTime",rampTime);                  
		hashMap.put("sampleErrorControl",sampleErrorControl);
		hashMap.put("continueForever",continueForever);   
		hashMap.put("controllerLoops",controllerLoops);   
		try {
			hashMap.put("startTime",GetNow.currentTimeToLong(startTime, "yyyy-MM-dd HH:mm:ss")+"");
		} catch (ParseException e) {
			hashMap.put("startTime","startTime error!");
		}    
		try {
			hashMap.put("endTime",GetNow.currentTimeToLong(endTime, "yyyy-MM-dd HH:mm:ss")+"");
		} catch (ParseException e) {
			hashMap.put("endTime","endTime error!");
		}      
		hashMap.put("duration",duration);     
		hashMap.put("delayTime",delayTime);   
		hashMap.put("delayedStart",delayedStart);
		hashMap.put("domainURL",domainURL);     
		hashMap.put("requestImpl",requestImpl); 
		hashMap.put("connectTimeOut",connectTimeOut);                     
		hashMap.put("responseTimeOut",responseTimeOut);                    
		hashMap.put("contentEncoding",contentEncoding);                    
		hashMap.put("requestMethod",requestMethod);                                                                                            
		hashMap.put("checkPointValue",checkPointValue);                    
		hashMap.put("checkPointType",checkPointType);    
		hashMap.put("clearCache",clearCache.equals("on")?"true":"false");                                                                                               
		hashMap.put("emailAttemper",emailAttemper.equals("on")?"true":"false");                      
		hashMap.put("successLimit",successLimit);                       
		hashMap.put("failureLimit",failureLimit);                       
		hashMap.put("failureSubject",failureSubject);                     
		hashMap.put("fromAddress",fromAddress);                        
		hashMap.put("smtpHost",smtpHost);                           
		hashMap.put("successSubject",successSubject);                     
		hashMap.put("sendTo",sendTo);                             
		hashMap.put("emailServerLoginName",emailServerLoginName);               
		hashMap.put("emailServerLoginPassword",emailServerLoginPassword);           
		hashMap.put("authType",authType);
		hashMap.put("requestProtocol",requestProtocol);
		hashMap.put("className",className);
		hashMap.put("enableRendzvous",enableRendzvous.equals("on")?"true":"false");
		hashMap.put("groupSize",groupSize);
		hashMap.put("rendzvousTimeOut",rendzvousTimeOut);
		hashMap.put("enableThinkTime",enableThinkTime.equals("on")?"true":"false");
		hashMap.put("thinkTime",thinkTime);
		hashMap.put("taskAssort",taskAssort.equals("on")?"true":"false");
		hashMap.put("methodName",methodName);
		hashMap.put("urlPort",urlPort.equals("-1")?"80":urlPort);
		hashMap.put("urlPath",urlPath);
		hashMap.put("params", params);
		hashMap.put("enableProxy",enableProxy.equals("on")?"true":"false");
		hashMap.put("proxyHost",proxyHost);
		hashMap.put("proxyPort",proxyPort);
		hashMap.put("proxyUserName",proxyUserName);
		hashMap.put("proxyPassword",proxyPassword);
		hashMap.put("enableDataSet",enableDataSet.equals("on")?"true":"false");
		hashMap.put("fileEncoding",fileEncoding);
		hashMap.put("fileName",fileName);
		hashMap.put("recycle",recycle.equals("on")?"true":"false");
		hashMap.put("variableNames",variableNames);
		hashMap.put("checkType", checkType);
		hashMap.put("requestHeaders", analysisHeaders());
		hashMap.put("enableHeaders", enableHeaders);
		hashMap.put("useBodyString", useBodyString.equals("on")?"true":"false");
		hashMap.put("bodyString", bodyString);
		
		return hashMap;
	}
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCaseName() {
		return caseName;
	}
	/**
	 * 设置用例的名称
	 * @param caseName
	 */
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getNumThreads() {
		return numThreads;
	}
	/**
	 * 设置并发线程数
	 * @param numThreads
	 */
	public void setNumThreads(String numThreads) {
		this.numThreads = numThreads;
	}

	public String getRampTime() {
		return rampTime;
	}
	/**
	 * 表示启动全部线程所需要的时间。如线程数为20，rampTime为40秒，则40/20=2/个，<br>
	 * 即每隔2秒增加一个线程	 
	 * @param rampTime
	 */
	public void setRampTime(String rampTime) {
		this.rampTime = rampTime;
	}

	public String getStartTime() {
		return startTime;
	}
	/**
	 * 设置开始执行的时间
	 * @param startTime
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}
	/**
	 * 设置结束的时间
	 * @param endTime
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDuration() {
		return duration;
	}
	/**
	 * 设置持续时间
	 * @param duration
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}
	/**
	 * 延迟多少秒后启动
	 * @return
	 */
	public String getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}

	public String getSampleErrorControl() {
		return sampleErrorControl;
	}
	/**
	 * 设置迭代出错时的处理，如continue
	 * @param sampleErrorControl
	 */
	public void setSampleErrorControl(String sampleErrorControl) {
		this.sampleErrorControl = sampleErrorControl;
	}

	public String getContinueForever() {
		return continueForever;
	}
	/**
	 * 是否一直运行,true或false，如果为true，则需要手动停止
	 * @param continueForever
	 */
	public void setContinueForever(String continueForever) {
		this.continueForever = continueForever;
	}

	public String getControllerLoops() {
		return controllerLoops;
	}
	/**
	 * 设置重复执行被测对象的次数
	 * @param controllerLoops
	 */
	public void setControllerLoops(String controllerLoops) {
		this.controllerLoops = controllerLoops;
	}
	
	public String getDelayedStart() {
		return delayedStart;
	}
	/**
	 * 是否按需要动态生成所需要的线程数
	 * @param delayedStart
	 */
	public void setDelayedStart(String delayedStart) {
		this.delayedStart = delayedStart;
	}
	@Column(columnDefinition="mediumtext",length=2550)
	public String getDomainURL() {
		return domainURL;
	}
	/**
	 * 被测的url地址
	 * @param domainURL
	 */
	public void setDomainURL(String domainURL) {
		this.domainURL = domainURL;
	}
	
	public String getConnectTimeOut() {
		return connectTimeOut;
	}
	/**
	 * 设置超时时间，如果超过此时间则会报错
	 * @param connectTimeOut
	 */
	public void setConnectTimeOut(String connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	public String getResponseTimeOut() {
		return responseTimeOut;
	}
	/**
	 * 设置读取服务器返回内容的时间，如果在此时间内容服务器没有返回任何内容，则会报错
	 * @param responseTimeOut
	 */
	public void setResponseTimeOut(String responseTimeOut) {
		this.responseTimeOut = responseTimeOut;
	}

	public String getContentEncoding() {
		return contentEncoding;
	}
	/**
	 * 设置接收服务器返回内容的编码格式
	 * @param contentEncoding
	 */
	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

	public String getRequestMethod() {
		return requestMethod;
	}
	/**
	 * 设置请求地址的方式，如GET或POST
	 * @param requestMethod
	 */
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getCheckPointValue() {
		return checkPointValue;
	}
	/**
	 * 设置检查点字符串，用于校验是否通过的标准
	 * @param checkPointValue
	 */
	public void setCheckPointValue(String checkPointValue) {
		this.checkPointValue = checkPointValue;
	}

	public String getCheckPointType() {
		return checkPointType;
	}
	/**
	 * 设置检查点方式，如是否包含，是否等于
	 * @param checkPointType
	 */
	public void setCheckPointType(String checkPointType) {
		this.checkPointType = checkPointType;
	}

	public String getClearCache() {
		return clearCache;
	}
	/**
	 * 是否在每次迭代时，都清除缓存
	 * @param clearCache
	 */
	public void setClearCache(String clearCache) {
		this.clearCache = clearCache;
	}

	public String getEmailAttemper() {
		return emailAttemper;
	}
	/**
	 * 是否发送Email邮件
	 * @param emailAttemper
	 */
	public void setEmailAttemper(String emailAttemper) {
		this.emailAttemper = emailAttemper;
	}

	public String getSuccessLimit() {
		return successLimit;
	}
	/**
	 * 如果启用了Email发送，则超过此值时就会发送邮件
	 * @param successLimit
	 */
	public void setSuccessLimit(String successLimit) {
		this.successLimit = successLimit;
	}

	public String getFailureLimit() {
		return failureLimit;
	}
	/**
	 * 如果启用了Email发送，则超过此值时就会发送邮件
	 * @param failureLimit
	 */
	public void setFailureLimit(String failureLimit) {
		this.failureLimit = failureLimit;
	}

	public String getFailureSubject() {
		return failureSubject;
	}
	/**
	 * 设置超过失败次数时，发送邮件的主题内容
	 * @param failureSubject
	 */
	public void setFailureSubject(String failureSubject) {
		this.failureSubject = failureSubject;
	}

	public String getFromAddress() {
		return fromAddress;
	}
	/**
	 * 设置邮件发送人
	 * @param fromAddress
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getSmtpHost() {
		return smtpHost;
	}
	/**
	 * 设置邮件服务器host
	 * @param smtpHost
	 */
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	
	public String getSuccessSubject() {
		return successSubject;
	}
	/**
	 * 设置超过成功次数（检查点）时，发送邮件的主题内容
	 * @param successSubject
	 */
	public void setSuccessSubject(String successSubject) {
		this.successSubject = successSubject;
	}

	public String getSendTo() {
		return sendTo;
	}
	/**
	 * 邮件接收人列表，多用户使用;分割
	 * @param sendTo
	 */
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getEmailServerLoginName() {
		return emailServerLoginName;
	}
	/**
	 * 设置登录邮件服务器的用户名
	 * @param emailServerLoginName
	 */
	public void setEmailServerLoginName(String emailServerLoginName) {
		this.emailServerLoginName = emailServerLoginName;
	}

	public String getEmailServerLoginPassword() {
		return emailServerLoginPassword;
	}
	/**
	 * 设置登录邮件服务器的密码
	 * @param emailServerLoginPassword
	 */
	public void setEmailServerLoginPassword(String emailServerLoginPassword) {
		this.emailServerLoginPassword = emailServerLoginPassword;
	}

	public String getAuthType() {
		return authType;
	}
	/**
	 * 设置身份认证类型，默认为SSL
	 * @param authType
	 */
	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getRequestProtocol() {
		return requestProtocol;
	}
	/**
	 * 设置请求的协议，默认使用http
	 * @param requestProtocol
	 */
	public void setRequestProtocol(String requestProtocol) {
		this.requestProtocol = requestProtocol;
	}

	public String getClassName() {
		return className;
	}
	/**
	 * 自定义的用例路径，如：org.phoenix.cases.TestCase
	 * @param className
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}
	/**
	 * 被测的方法名
	 * @param methodName
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public int getSlaveId() {
		return slaveId;
	}

	public void setSlaveId(int slaveId) {
		this.slaveId = slaveId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskAssort() {
		return taskAssort;
	}

	public void setTaskAssort(String taskAssort) {
		this.taskAssort = taskAssort;
	}
	
	public String getEnableRendzvous() {
		return enableRendzvous;
	}
	/**
	 * 是否启用集合点，true或false
	 * @param enableRendzvous
	 */
	public void setEnableRendzvous(String enableRendzvous) {
		this.enableRendzvous = enableRendzvous;
	}

	public String getGroupSize() {
		return groupSize;
	}
	/**
	 * 到达集合点大小
	 * @param groupSize
	 */
	public void setGroupSize(String groupSize) {
		this.groupSize = groupSize;
	}

	public String getEnableThinkTime() {
		return enableThinkTime;
	}
	/**
	 * 是否启用思考时间
	 * @param enableThinkTime
	 */
	public void setEnableThinkTime(String enableThinkTime) {
		this.enableThinkTime = enableThinkTime;
	}

	public String getThinkTime() {
		return thinkTime;
	}
	/**
	 * 思考时间设置
	 * @param thinkTime
	 */
	public void setThinkTime(String thinkTime) {
		this.thinkTime = thinkTime;
	}

	public String getRendzvousTimeOut() {
		return rendzvousTimeOut;
	}
	/**
	 * 集合点超时时间
	 * @param rendzvousTimeOut
	 */
	public void setRendzvousTimeOut(String rendzvousTimeOut) {
		this.rendzvousTimeOut = rendzvousTimeOut;
	}

	public String getUrlPort() {
		return urlPort;
	}
	/**
	 * 设置URL端口值，此值将会自动分析生成
	 * @param urlPort
	 */
	public void setUrlPort(String urlPort) {
		this.urlPort = urlPort;
	}
	@Column(columnDefinition="mediumtext",length=2550)
	public String getUrlPath() {
		return urlPath;
	}
	/**
	 * 设置URLpath，此值将会自动分析生成
	 * @param urlPath
	 */
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	
	@Column(columnDefinition="mediumtext",length=2550)
	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}
	
	@OneToMany(mappedBy="phoenixJmeterBean",fetch=FetchType.EAGER,targetEntity=PerfBatchLogModel.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SUBSELECT)
	public Set<PerfBatchLogModel> getPerfBatchLogModels() {
		return perfBatchLogModels;
	}

	public void setPerfBatchLogModels(Set<PerfBatchLogModel> perfBatchLogModels) {
		this.perfBatchLogModels = perfBatchLogModels;
	}

	public String getMonitedSlaves() {
		return monitedSlaves;
	}

	public void setMonitedSlaves(String monitedSlaves) {
		this.monitedSlaves = monitedSlaves;
	}

	public String getEnableProxy() {
		return enableProxy;
	}

	public void setEnableProxy(String enableProxy) {
		this.enableProxy = enableProxy;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUserName() {
		return proxyUserName;
	}

	public void setProxyUserName(String proxyUserName) {
		this.proxyUserName = proxyUserName;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	public String getEnableDataSet() {
		return enableDataSet;
	}

	public void setEnableDataSet(String enableDataSet) {
		this.enableDataSet = enableDataSet;
	}

	public String getFileEncoding() {
		return fileEncoding;
	}

	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRecycle() {
		return recycle;
	}

	public void setRecycle(String recycle) {
		this.recycle = recycle;
	}

	public String getVariableNames() {
		return variableNames;
	}

	public void setVariableNames(String variableNames) {
		this.variableNames = variableNames;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Transient
	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	
	@Column(columnDefinition="mediumtext",length=1000)
	public String getRequestHeaders() {
		return requestHeaders;
	}

	public void setRequestHeaders(String requestHeaders) {
		this.requestHeaders = requestHeaders;
	}
	@Transient
	public String getEnableHeaders() {
		return enableHeaders;
	}

	public void setEnableHeaders(String enableHeaders) {
		this.enableHeaders = enableHeaders;
	}
	@Column(columnDefinition="mediumtext",length=6500)
	public String getBodyString() {
		return bodyString;
	}

	public void setBodyString(String bodyString) {
		this.bodyString = bodyString;
	}
	
	public String getUseBodyString() {
		return useBodyString;
	}

	public void setUseBodyString(String useBodyString) {
		this.useBodyString = useBodyString;
	}

	public String getRequestImpl() {
		return requestImpl;
	}

	public void setRequestImpl(String requestImpl) {
		this.requestImpl = requestImpl;
	}
}
