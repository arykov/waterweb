package com.ryaltech.whitewater.gauges.services;

import com.ryaltech.whitewater.gauges.model.RiverInfo;
import com.ryaltech.whitewater.gauges.model.RiverLevel;



public interface GaugeDataCollector {

	RiverLevel getRiverLevelValues(RiverInfo riverInfo);
	String getName();
	String getId();

	
}