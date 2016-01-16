package org.phoenix.web.service;

import java.util.List;

import org.phoenix.web.dto.StatisticsDTO;

public interface IStatService {
	List<StatisticsDTO> getCaseStatistics(int id);
	List<StatisticsDTO> getScenarioStatistics(int id);
}
