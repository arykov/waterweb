package com.ryaltech.whitewater.gauges.services;

import java.util.Date;

import com.ryaltech.whitewater.gauges.model.RiverInfo;
import com.ryaltech.whitewater.gauges.model.RiverLevel;

public interface WaterWebDao {
	RiverInfo [] getAllRiversInfo();
	RiverInfo  getRiverInfo(String riverId);
	RiverLevel getLatestRiverLevel(String riverId);
	RiverLevel [] getLatestRiverLevels();
	
	void insertRunnableConditions(RiverInfo ...riversInfo);
	void insertRiverLevels(RiverLevel ... riverLevels);
	void purgeRiverLevels(Date before);
	

}
