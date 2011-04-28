package com.ryaltech.whitewater.gauges.server;

import java.util.List;

public interface GaugeValuesService {

	public abstract List<GaugeValue> getGaugeValues(List<String> gaugeIds) throws Exception;

	public abstract List<RunnableConditions> getRunnableConditions(
			List<String> gaugeIds);

}