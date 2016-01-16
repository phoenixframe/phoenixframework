package org.phoenix.cases.kafka;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.commons.io.IOUtils;
import org.phoenix.action.WebElementActionProxy;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

/**
 * kafka测试
 * @author mengfeiyang
 *
 */
public class KafkaCases extends WebElementActionProxy{

	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init("",arg0);
		String sour = null;
		try {
			sour = IOUtils.toString(webProxy.webAPIAction().getResponseByHttpClientWithPost("http://10.138.65.216:19527/kmsg?key=1234&uid=1234&topic=qa_test", "222").getEntity().getContent());
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
