<?xml version="1.0" encoding="UTF-8"?>
<jmeterTestPlan version="1.2" properties="2.8" jmeter="2.13 r1665067">
  <hashTree>
    <TestPlan guiclass="TestPlanGui" testclass="TestPlan" testname="TestPlan" enabled="true">
      <stringProp name="TestPlan.comments"></stringProp>
      <boolProp name="TestPlan.functional_mode">false</boolProp>
      <boolProp name="TestPlan.serialize_threadgroups">false</boolProp>
      <elementProp name="TestPlan.user_defined_variables" elementType="Arguments" guiclass="ArgumentsPanel" testclass="Arguments" testname="UserArguments" enabled="true">
        <collectionProp name="Arguments.arguments"/>
      </elementProp>
      <stringProp name="TestPlan.user_define_classpath"></stringProp>
    </TestPlan>
    <hashTree>
      <ThreadGroup guiclass="ThreadGroupGui" testclass="ThreadGroup" testname="ThreadGroup" enabled="true">
        <stringProp name="ThreadGroup.on_sample_error">${sampleErrorControl}</stringProp>
        <elementProp name="ThreadGroup.main_controller" elementType="LoopController" guiclass="LoopControlPanel" testclass="LoopController" testname="LoopController" enabled="true">
          <boolProp name="LoopController.continue_forever">${continueForever}</boolProp>
          <stringProp name="LoopController.loops">${controllerLoops}</stringProp>
        </elementProp>
        <stringProp name="ThreadGroup.num_threads">${numThreads}</stringProp>
        <stringProp name="ThreadGroup.ramp_time">${rampTime}</stringProp>
        <longProp name="ThreadGroup.start_time">${startTime}</longProp>
        <longProp name="ThreadGroup.end_time">${endTime}</longProp>
        <boolProp name="ThreadGroup.scheduler">${taskAssort}</boolProp>
        <stringProp name="ThreadGroup.duration">${duration}</stringProp>
        <stringProp name="ThreadGroup.delay">${delayTime}</stringProp>
        <boolProp name="ThreadGroup.delayedStart">${delayedStart}</boolProp>
      </ThreadGroup>
      <hashTree>
        <HTTPSamplerProxy guiclass="HttpTestSampleGui" testclass="HTTPSamplerProxy" testname="${caseName}" enabled="true">
          <#if bodyString?exists>
			    <boolProp name="HTTPSampler.postBodyRaw">true</boolProp>
          		<elementProp name="HTTPsampler.Arguments" elementType="Arguments">
		  </#if>
			
          <#if params?exists><elementProp name="HTTPsampler.Arguments" elementType="Arguments" guiclass="HTTPArgumentsPanel" testclass="Arguments" testname="UserArguments" enabled="true"></#if>
            <collectionProp name="Arguments.arguments">
		        <#if params?exists>
					<#list params?keys as key>
			            <elementProp name="${key}" elementType="HTTPArgument">
			              <boolProp name="HTTPArgument.always_encode">false</boolProp>
			              <stringProp name="Argument.value">${params[key]}</stringProp>
			              <stringProp name="Argument.metadata">=</stringProp>
			              <boolProp name="HTTPArgument.use_equals">true</boolProp>
			              <stringProp name="Argument.name">${key}</stringProp>
			            </elementProp>
		            </#list>
				</#if>
				<#if bodyString?exists>
						  <elementProp name="" elementType="HTTPArgument">
			                <boolProp name="HTTPArgument.always_encode">false</boolProp>
			                <stringProp name="Argument.value">1111111111112333</stringProp>
			                <stringProp name="Argument.metadata">=</stringProp>
			              </elementProp>
			    </#if>
			</collectionProp>
          </elementProp>
          <stringProp name="HTTPSampler.domain">${domainURL}</stringProp>
          <stringProp name="HTTPSampler.port">${urlPort}</stringProp>
          <#if enableProxy == "true">
          <stringProp name="HTTPSampler.proxyHost">${proxyHost}</stringProp>
          <stringProp name="HTTPSampler.proxyPort">${proxyPort}</stringProp>
          <stringProp name="HTTPSampler.proxyUser">${proxyUserName}</stringProp>
          <stringProp name="HTTPSampler.proxyPass">${proxyPassword}</stringProp>
		  </#if>
          <stringProp name="HTTPSampler.connect_timeout">${connectTimeOut}</stringProp>
          <stringProp name="HTTPSampler.response_timeout">${responseTimeOut}</stringProp>
          <stringProp name="HTTPSampler.protocol">${requestProtocol}</stringProp>
          <stringProp name="HTTPSampler.contentEncoding">${contentEncoding}</stringProp>
          <stringProp name="HTTPSampler.path">${urlPath}</stringProp>
          <stringProp name="HTTPSampler.method">${requestMethod}</stringProp>
          <boolProp name="HTTPSampler.follow_redirects">true</boolProp>
          <boolProp name="HTTPSampler.auto_redirects">false</boolProp>
          <boolProp name="HTTPSampler.use_keepalive">true</boolProp>
          <boolProp name="HTTPSampler.DO_MULTIPART_POST">false</boolProp>
          <stringProp name="HTTPSampler.implementation">HttpClient4</stringProp>
          <boolProp name="HTTPSampler.monitor">false</boolProp>
          <stringProp name="HTTPSampler.embedded_url_re"></stringProp>
        </HTTPSamplerProxy>
        <hashTree>
          <ResponseAssertion guiclass="AssertionGui" testclass="ResponseAssertion" testname="ResponseAssertion" enabled="true">
            <collectionProp name="Asserion.test_strings">
              <stringProp name="-149112576">${checkPointValue}</stringProp>
            </collectionProp>
            <stringProp name="Assertion.test_field">Assertion.${checkType}</stringProp>
            <boolProp name="Assertion.assume_success">true</boolProp>
            <intProp name="Assertion.test_type">${checkPointType}</intProp>
          </ResponseAssertion>
          <hashTree/>
          <CacheManager guiclass="CacheManagerGui" testclass="CacheManager" testname="CacheManager" enabled="true">
            <boolProp name="clearEachIteration">${clearCache}</boolProp>
            <boolProp name="useExpires">false</boolProp>
          </CacheManager>
          <hashTree/>
          <MailerResultCollector guiclass="MailerVisualizer" testclass="MailerResultCollector" testname="EmailSender" enabled="${emailAttemper}">
            <boolProp name="ResultCollector.error_logging">false</boolProp>
            <objProp>
              <name>saveConfig</name>
              <value class="SampleSaveConfiguration">
                <time>true</time>
                <latency>true</latency>
                <timestamp>true</timestamp>
                <success>true</success>
                <label>true</label>
                <code>true</code>
                <message>true</message>
                <threadName>true</threadName>
                <dataType>true</dataType>
                <encoding>false</encoding>
                <assertions>true</assertions>
                <subresults>true</subresults>
                <responseData>false</responseData>
                <samplerData>false</samplerData>
                <xml>false</xml>
                <fieldNames>false</fieldNames>
                <responseHeaders>false</responseHeaders>
                <requestHeaders>false</requestHeaders>
                <responseDataOnError>false</responseDataOnError>
                <saveAssertionResultsFailureMessage>false</saveAssertionResultsFailureMessage>
                <assertionsResultsToSave>0</assertionsResultsToSave>
                <bytes>true</bytes>
                <threadCounts>true</threadCounts>
              </value>
            </objProp>
            <elementProp name="MailerResultCollector.mailer_model" elementType="MailerModel">
              <stringProp name="MailerModel.successLimit">${successLimit}</stringProp>
              <stringProp name="MailerModel.failureLimit">${failureLimit}</stringProp>
              <stringProp name="MailerModel.failureSubject">${failureSubject}</stringProp>
              <stringProp name="MailerModel.fromAddress">${fromAddress}</stringProp>
              <stringProp name="MailerModel.smtpHost">${smtpHost}</stringProp>
              <stringProp name="MailerModel.successSubject">${successSubject}</stringProp>
              <stringProp name="MailerModel.addressie">${sendTo}</stringProp>
              <stringProp name="MailerModel.login">${emailServerLoginName}</stringProp>
              <stringProp name="MailerModel.password">${emailServerLoginPassword}</stringProp>
              <stringProp name="MailerModel.authType">${authType}</stringProp>
            </elementProp>
            <stringProp name="filename"></stringProp>
          </MailerResultCollector>
          <hashTree/>
          <SyncTimer guiclass="TestBeanGUI" testclass="SyncTimer" testname="SyncTimer" enabled="${enableRendzvous}">
            <intProp name="groupSize">${groupSize}</intProp>
            <longProp name="timeoutInMs">${rendzvousTimeOut}</longProp>
          </SyncTimer>
          <hashTree/>
          <ConstantTimer guiclass="ConstantTimerGui" testclass="ConstantTimer" testname="ThinkTime" enabled="${enableThinkTime}">
            <stringProp name="ConstantTimer.delay">${thinkTime}</stringProp>
          </ConstantTimer>
          <hashTree/>
          <CSVDataSet guiclass="TestBeanGUI" testclass="CSVDataSet" testname="paramData" enabled="${enableDataSet}">
            <stringProp name="delimiter">,</stringProp>
            <stringProp name="fileEncoding">${fileEncoding}</stringProp>
            <stringProp name="filename">${fileName}</stringProp>
            <boolProp name="quotedData">false</boolProp>
            <boolProp name="recycle">${recycle}</boolProp>
            <stringProp name="shareMode">shareMode.all</stringProp>
            <boolProp name="stopThread">false</boolProp>
            <stringProp name="variableNames">${variableNames}</stringProp>
          </CSVDataSet>
          <hashTree/>
          <HeaderManager guiclass="HeaderPanel" testclass="HeaderManager" testname="requestHeaders" enabled="${enableHeaders}">
            <collectionProp name="HeaderManager.headers">
            	<#if requestHeaders?exists>
					<#list requestHeaders?keys as key>
		              <elementProp name="" elementType="Header">
		                <stringProp name="Header.name">${key}</stringProp>
		                <stringProp name="Header.value">${requestHeaders[key]}</stringProp>
		              </elementProp>
              		</#list>
				</#if>
            </collectionProp>
          </HeaderManager>
          <hashTree/>
        </hashTree>
      </hashTree>
    </hashTree>
  </hashTree>
</jmeterTestPlan>
