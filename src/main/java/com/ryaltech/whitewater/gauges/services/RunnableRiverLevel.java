package com.ryaltech.whitewater.gauges.services;

public class RunnableRiverLevel {
	private RiverLevel gaugeValue;
	private RiverInfo runnableConditions;
	static RunnableRiverLevel[] combine(RiverLevel[] gaugeValues, RiverInfo[] runnableConditions){
		assert gaugeValues.length == runnableConditions.length;
		RunnableRiverLevel runnableRiverLevels[] = new RunnableRiverLevel[runnableConditions.length];
		for(int i=0;i<runnableRiverLevels.length;i++)
			runnableRiverLevels[i] = new RunnableRiverLevel().setGaugeValue(gaugeValues[i]).setRunnableConditions(runnableConditions[i]);
		return runnableRiverLevels;
	}
	public RiverLevel getGaugeValue() {
		return gaugeValue;
	}
	public RunnableRiverLevel setGaugeValue(RiverLevel gaugeValue) {
		this.gaugeValue = gaugeValue;
		return this;
	}
	public RiverInfo getRunnableConditions() {
		return runnableConditions;
	}
	public RunnableRiverLevel setRunnableConditions(RiverInfo runnableConditions) {
		this.runnableConditions = runnableConditions;
		return this;
	}

}
