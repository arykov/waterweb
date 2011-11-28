package com.ryaltech.whitewater.gauges.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ryaltech.whitewater.gauges.model.RiverInfo;
import com.ryaltech.whitewater.gauges.model.RiverLevel;

public class InMemoryWaterWebDao implements WaterWebDao {

	private Map<String, RiverInfo> riverInfoMap = new HashMap<String, RiverInfo>();
	private Map<RiverInfo, List<RiverLevel>> riverLevelMap = new HashMap<RiverInfo, List<RiverLevel>>();

	public InMemoryWaterWebDao() {
	}

	public InMemoryWaterWebDao(Map<String, RiverInfo> riverInfoMap) {
		this.riverInfoMap = riverInfoMap;
	}

	@Override
	public RiverInfo getRiverInfo(String riverId) {

		return riverInfoMap.get(riverId);
	}

	@Override
	public RiverLevel getLatestRiverLevel(String riverId) {

		RiverInfo ri = getRiverInfo(riverId);
		List<RiverLevel> riverLevels = riverLevelMap.get(ri);
		if (riverLevels == null || riverLevels.size() == 0)
			return null;
		return riverLevels.get(riverLevels.size() - 1);
	}

	@Override
	public void persistRunnableConditions(RiverInfo... conditions) {
		for (RiverInfo condition : conditions) {
			riverInfoMap.put(condition.getGaugeId(), condition);
		}

	}

	@Override
	public void insertRiverLevels(RiverLevel... gaugeValues) {
		for (RiverLevel gaugeValue : gaugeValues) {
			List<RiverLevel> values = riverLevelMap.get(gaugeValue
					.getRiverInfo());
			if (values == null) {
				values = new ArrayList<RiverLevel>();
				riverLevelMap.put(gaugeValue.getRiverInfo(), values);
			}
			values.add(gaugeValue);
			Collections.sort(values);
		}

	}

	@Override
	public void purgeRiverLevels(Date before) {
		throw new UnsupportedOperationException();

	}

	@Override
	public RiverInfo[] getAllRiversInfo() {
		return riverInfoMap.values().toArray(new RiverInfo[0]);
	}

	@Override
	public RiverLevel[] getLatestRiverLevels() {
		List<RiverLevel> latestRiverLevels = new ArrayList<RiverLevel>();
		Collection<List<RiverLevel>> riverLevelsListCollection = riverLevelMap.values();
		for(List<RiverLevel> riverLevelsList:riverLevelsListCollection){
			if(riverLevelsList.size()>0)latestRiverLevels.add(riverLevelsList.get(riverLevelsList.size()-1));
			
		}
		return latestRiverLevels.toArray(new RiverLevel[latestRiverLevels.size()]);
	}

}
