<?xml version="1.0" encoding="UTF-8"?>
<jmeterTestPlan version="1.2" properties="2.4" jmeter="2.13 r1665067">
  <hashTree>
    <TestPlan guiclass="TestPlanGui" testclass="TestPlan" testname="jDemoTestPlan" enabled="true">
      <stringProp name="TestPlan.comments"></stringProp>
      <boolProp name="TestPlan.functional_mode">false</boolProp>
      <boolProp name="TestPlan.serialize_threadgroups">false</boolProp>
      <elementProp name="TestPlan.user_defined_variables" elementType="Arguments" guiclass="ArgumentsPanel" testclass="Arguments" testname="jDemoArgs" enabled="true">
        <collectionProp name="Arguments.arguments"/>
      </elementProp>
      <stringProp name="TestPlan.user_define_classpath"></stringProp>
    </TestPlan>
    <hashTree>
      <ThreadGroup guiclass="ThreadGroupGui" testclass="ThreadGroup" testname="jDemoThreadGroup" enabled="true">
        <stringProp name="ThreadGroup.on_sample_error">${sampleErrorControl}</stringProp>
        <elementProp name="ThreadGroup.main_controller" elementType="LoopController" guiclass="LoopControlPanel" testclass="LoopController" testname="jDemoController" enabled="true">
          <boolProp name="LoopController.continue_forever">${continueForever}</boolProp>
          <!--loops-->
          <stringProp name="LoopController.loops">${controllerLoops}</stringProp>
        </elementProp>
        <!--threads-->
        <stringProp name="ThreadGroup.num_threads">${numThreads}</stringProp>
        <stringProp name="ThreadGroup.ramp_time">${rampTime}</stringProp>
        <longProp name="ThreadGroup.start_time">${startTime}</longProp>
        <longProp name="ThreadGroup.end_time">${endTime}</longProp>
        <boolProp name="ThreadGroup.scheduler">${scheduler}</boolProp>
        <stringProp name="ThreadGroup.duration">${duration}</stringProp>
        <stringProp name="ThreadGroup.delay">${delayTime}</stringProp>
      </ThreadGroup>
      <hashTree>
        <JavaSampler guiclass="JavaTestSamplerGui" testclass="JavaSampler" testname="jDemoSampler" enabled="true">
          <elementProp name="arguments" elementType="Arguments" guiclass="ArgumentsPanel" testclass="Arguments" enabled="true">
            <!--parameters-->
            <collectionProp name="Arguments.arguments">
              <elementProp name="size" elementType="Argument">
                <stringProp name="Argument.name">size</stringProp>
                <stringProp name="Argument.value">8192</stringProp>
                <stringProp name="Argument.metadata">=</stringProp>
              </elementProp>
              <elementProp name="method" elementType="Argument">
                <stringProp name="Argument.name">method</stringProp>
                <stringProp name="Argument.value">${methodName}</stringProp>
                <stringProp name="Argument.metadata">=</stringProp>
              </elementProp>
            </collectionProp>
          </elementProp>
          <stringProp name="classname">${className}</stringProp>
        </JavaSampler>
        <hashTree>
          <ResultCollector guiclass="StatVisualizer" testclass="ResultCollector" testname="jDemoReport" enabled="true">
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
              </value>
            </objProp>
            <stringProp name="filename"></stringProp>
          </ResultCollector>
          <hashTree/>
        </hashTree>
      </hashTree>
    </hashTree>
  </hashTree>
</jmeterTestPlan>