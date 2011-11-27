package com.ryaltech.whitewater.gauges.services;

import java.util.HashMap;
import java.util.Map;

import com.ryaltech.whitewater.gauges.model.RiverInfo;
import com.ryaltech.whitewater.gauges.model.RiverLevel;

public class GaugeDataCollectionHub {
	private Map<String, GaugeDataCollector> gaugeDataCollectorsMap = new HashMap<String, GaugeDataCollector>();

	public GaugeDataCollectionHub(GaugeDataCollector ... gaugeDataCollectors) {
		//repackage array into map for faster search
		for(GaugeDataCollector gdc:gaugeDataCollectors){
			gaugeDataCollectorsMap.put(gdc.getId(), gdc);
		}		
		
	}

	public RiverLevel getRiverLevel(RiverInfo riverInfo){
		GaugeDataCollector dataCollector = gaugeDataCollectorsMap.get(riverInfo.getGaugeDataCollectorId());
		if(dataCollector == null)throw new RuntimeException(String.format("Gauge data collector for river %s with id %s does not exist.", riverInfo.getRiverName(), riverInfo.getGaugeDataCollectorId()));
		return dataCollector.getRiverLevelValues(riverInfo);
		
	}
	

}
