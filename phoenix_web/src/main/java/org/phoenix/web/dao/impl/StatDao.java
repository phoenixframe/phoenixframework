package org.phoenix.web.dao.impl;

import java.util.List;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.web.dao.IStatDao;
import org.phoenix.web.dto.StatisticsDTO;
import org.springframework.stereotype.Repository;

@Repository
public class StatDao extends BaseDao<StatisticsDTO> implements IStatDao{

	@Override
	public List<StatisticsDTO> getCaseStatusByBatchLogId(int id) {
		String hql = "select b.batchId as batchId,c.startTime as startTime,c.caseName as casename, l.stepType as type,count(*) as total,sum(case when l.`status`='SUCCESS' then 1 else 0 end) as success,sum(case when l.`status`='FAIL' then 1 else 0 end) as fail from l_web_batch b,l_web_case c,l_web_unit l WHERE b.id=c.batchLogId And c.id=l.caseLogId And b.id='"+id+"' group by l.stepType";
		return super.listBySql(hql, StatisticsDTO.class, false);
	}

	@Override
	public List<StatisticsDTO> getScenarioStatusByBatchLogId(int id) {
		String hql = "SELECT b.batchId AS batchId,s.scenarioName AS scenarioName,b.createDate AS createDate,c.caseName AS casename,l.stepType AS type,count(*) AS total,sum(CASE WHEN l.`status` = 'SUCCESS' THEN 1 ELSE 0 END) AS success,sum(CASE	WHEN l.`status` = 'FAIL' THEN	1	ELSE 0 END) AS fail FROM l_web_batch b,	l_web_scenario s,	l_web_case c,	l_web_unit l WHERE	b.id = s.batchLogId AND s.id = c.scenarioLogBeanId AND l.caseLogId = c.id AND b.id = '"+id+"' AND l.stepType IN('STEP','CHECKPOINT') GROUP BY	c.caseName,	l.stepType";
		return super.listBySql(hql, StatisticsDTO.class, false);
	}

}
