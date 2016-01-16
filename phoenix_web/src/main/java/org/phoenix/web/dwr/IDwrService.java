package org.phoenix.web.dwr;

import java.util.List;

import org.phoenix.model.CaseBean;
import org.phoenix.model.ScenarioBean;
import org.phoenix.web.dto.StatisticsDTO;

public interface IDwrService {

	
	List<StatisticsDTO> listCaseStatus(int id);
	List<StatisticsDTO> listScenarioStatus(int id);
	List<CaseBean> listWebCaseBeanByUT(String taskType);
	List<ScenarioBean> listWebScenarioBeanByUser(String taskType);
	
}
