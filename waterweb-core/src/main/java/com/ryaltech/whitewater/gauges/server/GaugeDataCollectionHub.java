package com.ryaltech.whitewater.gauges.server;

import java.util.HashMap;
import java.util.Map;

public class GaugeDataCollectionHub {
	private Map<String, GaugeDataCollector> gaugeDataCollectorsMap = new HashMap<String, GaugeDataCollector>();

	public GaugeDataCollectionHub(GaugeDataCollector ... gaugeDataCollectors) {
		//repackage array into map for faster search
		for(GaugeDataCollector gdc:gaugeDataCollectors){
			gaugeDataCollectorsMap.put(gdc.getName(), gdc);
		}		
		
	}

	public RiverLevel getRiverLevel(RiverInfo riverInfo){
		GaugeDataCollector dataCollector = gaugeDataCollectorsMap.get(riverInfo.getGaugeDataCollectorName());
		if(dataCollector == null)throw new RuntimeException(String.format("Gauge data collector for river %s with id %s does not exist.", riverInfo.getRiverName(), riverInfo.getGaugeDataCollectorName()));
		return dataCollector.getRiverLevelValues(riverInfo.getGaugeId());
		
	}
	

}
