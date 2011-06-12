package com.ryaltech.whitewater.gauges.services;



public interface GaugeDataCollector {

	RiverLevel getRiverLevelValues(RiverInfo riverInfo);
	String getName();
	String getId();

	
}