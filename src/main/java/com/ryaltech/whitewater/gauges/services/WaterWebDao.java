package com.ryaltech.whitewater.gauges.services;

import java.util.Date;

public interface WaterWebDao {
	RiverInfo [] getAllRiversInfo();
	RiverInfo  getRiverInfo(String riverId);
	RiverLevel getLatestRiverLevel(String riverId);
	RiverLevel [] getLatestRiverLevels();
	
	void insertRunnableConditions(RiverInfo ...riversInfo);
	void insertRiverLevels(RiverLevel ... riverLevels);
	void purgeRiverLevels(Date before);
	

}
