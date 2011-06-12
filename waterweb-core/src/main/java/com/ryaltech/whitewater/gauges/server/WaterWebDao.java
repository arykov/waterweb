package com.ryaltech.whitewater.gauges.server;

import java.util.Date;

public interface WaterWebDao {
	RiverInfo [] getRunnableConditions(String ... gaugeId);
	RiverLevel [] getAllRecentGaugeValues();
	void insertRunnableConditions(RiverInfo ...conditions);
	void insertGaugeValues(RiverLevel ... gaugeValue);
	void purgeGaugeValues(Date before);
	RunnableRiverLevel[] getRunnableRiverLevels();

}
