package org.phoenix.web.dao;

import java.util.List;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.web.dto.StatisticsDTO;
/**
 * 统计图表操作
 * @author mengfeiyang
 *
 */
public interface IStatDao extends IBaseDao<StatisticsDTO>{
	List<StatisticsDTO> getCaseStatusByBatchLogId(int id);
	List<StatisticsDTO> getScenarioStatusByBatchLogId(int id);
}
