package com.ryaltech.whitewater.gauges.server;

import java.util.List;

public interface GaugeDataCollector {

	RiverLevel getRiverLevelValues(String gaugeId);
	String getName();

	
}