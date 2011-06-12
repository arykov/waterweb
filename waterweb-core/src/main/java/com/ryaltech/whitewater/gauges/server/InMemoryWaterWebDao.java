package com.ryaltech.whitewater.gauges.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryWaterWebDao implements WaterWebDao {

	private Map<String, RiverInfo> runnableConditionsMap = new HashMap<String, RiverInfo>();
	private Map<String, List<RiverLevel>> gaugeValuesMap = new HashMap<String, List<RiverLevel>>();

	public RiverInfo[] getRunnableConditions(RiverLevel... riverLevelValues) {
		String[] gaugeIds = new String[riverLevelValues.length];
		for (int i = 0; i < riverLevelValues.length; i++) {
			gaugeIds[i] = riverLevelValues[i].getGaugeId();
		}
		return getRunnableConditions(gaugeIds);

	}

	@Override
	public RiverInfo[] getRunnableConditions(String... gaugeIds) {
		RiverInfo[] runnableConditions = new RiverInfo[gaugeIds.length];
		for (int i = 0; i < runnableConditions.length; i++) {
			runnableConditions[i] = runnableConditionsMap.get(gaugeIds[i]);
		}
		return runnableConditions;
	}

	@Override
	public RiverLevel[] getAllRecentGaugeValues() {
		List<RiverLevel> retVal = new ArrayList<RiverLevel>();
		for (List<RiverLevel> gaugeValueList : gaugeValuesMap.values()) {
			if (gaugeValueList.size() > 0)
				retVal.add(gaugeValueList.get(gaugeValueList.size() - 1));
		}
		return retVal.toArray(new RiverLevel[retVal.size()]);
	}

	@Override
	public void insertRunnableConditions(RiverInfo... conditions) {
		for (RiverInfo condition : conditions) {
			runnableConditionsMap.put(condition.getGaugeId(), condition);
		}

	}

	@Override
	public void insertGaugeValues(RiverLevel... gaugeValues) {
		for (RiverLevel gaugeValue : gaugeValues) {
			List<RiverLevel> values = gaugeValuesMap.get(gaugeValue
					.getGaugeId());
			if (values == null) {
				values = new ArrayList<RiverLevel>();
				gaugeValuesMap.put(gaugeValue.getGaugeId(), values);
			}
			values.add(gaugeValue);
			Collections.sort(values);

		}

	}

	@Override
	public void purgeGaugeValues(Date before) {
		throw new UnsupportedOperationException();

	}

	@Override
	public RunnableRiverLevel[] getRunnableRiverLevels() {
		RiverLevel[] recentGaugeValues = getAllRecentGaugeValues();
		return RunnableRiverLevel.combine(recentGaugeValues,
				getRunnableConditions(recentGaugeValues));
	}
}
