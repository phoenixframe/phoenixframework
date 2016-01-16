package org.phoenix.web.dwr;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.phoenix.model.CaseBean;
import org.phoenix.model.ScenarioBean;
import org.phoenix.web.dto.StatisticsDTO;
import org.phoenix.web.model.User;
import org.phoenix.web.service.ICaseService;
import org.phoenix.web.service.IScenarioService;
import org.phoenix.web.service.IStatService;

@RemoteProxy(name="dwrService")
public class DwrService implements IDwrService{
	private IStatService statService;
	private ICaseService caseService;
	private IScenarioService scenarioService;

	public IScenarioService getScenarioService() {
		return scenarioService;
	}
	@Resource
	public void setScenarioService(IScenarioService scenarioService) {
		this.scenarioService = scenarioService;
	}
	public ICaseService getCaseService() {
		return caseService;
	}
	@Resource
	public void setCaseService(ICaseService caseService) {
		this.caseService = caseService;
	}
	public IStatService getStatService() {
		return statService;
	}
	@Resource
	public void setStatService(IStatService statService) {
		this.statService = statService;
	}

	@Override
	@RemoteMethod
	public List<StatisticsDTO> listCaseStatus(int id) {
		List<StatisticsDTO> statusList = statService.getCaseStatistics(id);
		return statusList;
	}
	@Override
	@RemoteMethod
	public List<StatisticsDTO> listScenarioStatus(int id) {
		List<StatisticsDTO> statusList = statService.getScenarioStatistics(id);
		return statusList;
	}
	@Override
	@RemoteMethod
	public List<CaseBean> listWebCaseBeanByUT(String taskType) {
		HttpSession session = WebContextFactory.get().getSession();
		User u = (User)session.getAttribute("loginUser");
		List<CaseBean> dataList = caseService.getCaseBeanListByUT(u.getId(),taskType);
		return dataList;
	}
	@Override
	@RemoteMethod
	public List<ScenarioBean> listWebScenarioBeanByUser(String taskType) {
		HttpSession session = WebContextFactory.get().getSession();
		User u = (User)session.getAttribute("loginUser");
		List<ScenarioBean> dataList = scenarioService.getScenarioBeanList(u.getId());
		return dataList;
	}

}
