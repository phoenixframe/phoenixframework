package org.phoenix.cases.kafka;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.commons.io.IOUtils;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

/**
 * kafka测试
 * @author mengfeiyang
 *
 */
public class KafkaCases extends ActionProxy{

	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		String sour = null;
		try {
			sour = IOUtils.toString(phoenix.interfaceAPI().getResponseByHttpClientWithPost("http://10.138.65.216:19527/kmsg?key=1234&uid=1234&topic=qa_test", "222").getEntity().getContent());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(sour);
		
		return getUnitLog();
	}
	
	public static void main(String[] args) {
		KafkaCases kfc = new KafkaCases();
		LinkedList<UnitLogBean> ll = kfc.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}

}
