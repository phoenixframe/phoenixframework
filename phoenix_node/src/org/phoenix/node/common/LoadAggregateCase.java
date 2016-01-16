package org.phoenix.node.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.phoenix.dao.CaseDao;
import org.phoenix.model.CaseBean;
import org.phoenix.utils.MethodPattern;

/**
 * 将引入的类内容中的import和执行code添加到当前的类中
 * @author mengfeiyang
 *
 */
public class LoadAggregateCase extends CaseDao {
	private final String AGGREGATESTEP = "webProxy.addAggregateCase";
	private CaseBean caseBean;
	
	public String loadCase(String caseContent){
		if(!caseContent.contains(AGGREGATESTEP)){
			return caseContent;
		} else {
			LinkedList<String> linkedList = new LinkedList<String>();
			String[] units = caseContent.split("\n");
			for(String unit : units){
				linkedList.add(unit);
				if(unit.contains(AGGREGATESTEP)){
					String caseAct = MethodPattern.result(unit, AGGREGATESTEP+"\\((.*)\\);");
					caseAct = caseAct.replace("\"", "");
					try{
						caseBean = loadModel(Integer.parseInt(caseAct));
					}catch(Exception e){
						caseBean = loadModel(caseAct);
					}
					List<String> contents = getSubCaseData(caseBean.getCodeContent());
					linkedList.add(1, contents.get(0));
					linkedList.add(contents.get(1));
				}
			}
			return list2Str(linkedList);
		}
	}
	
	private List<String> getSubCaseData(String content){
		String[] units = content.split("\n");
		String importString = "";
		String codeContent = "";
		List<String> list = new ArrayList<String>();
		for(String unit : units){
			if(unit.trim().startsWith("import")){
				importString += unit + "\n";
			} else if(unit.trim().startsWith("init")) {
				unit = unit.trim().replace("(", "\\(").replace(")", "\\)");
				String[] units2 = content.split(unit)[1].split("\n");
				for(String u : units2){
					if(u.contains("getUnitLog"))break;
					codeContent += u + "\n";
				}
				break;
			}
		}
		list.add(importString);
		list.add(codeContent);
		return list;
	}
	
	private String list2Str(List<String> list){
		StringBuilder stringBuilder = new StringBuilder();
		for(String l : list){
			stringBuilder.append(l);
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
	
	public static void main(String[] args) {
		LoadAggregateCase l = new LoadAggregateCase();
		CaseBean c = l.loadModel(11);
		System.out.println(c.getCaseName());
		System.out.println(l.loadCase(c.getCodeContent()));
	}
}
