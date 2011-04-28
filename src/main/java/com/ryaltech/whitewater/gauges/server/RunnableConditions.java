package com.ryaltech.whitewater.gauges.server;

public class RunnableConditions {

	private String gaugeId;
	private MeasuringUnit measurement;
	private float lowLevel;
	private float mediumLevel;
	private float highLevel;
	public String getGaugeId() {
		return gaugeId;
	}
	public void setGaugeId(String gaugeId) {
		this.gaugeId = gaugeId;
	}
	public MeasuringUnit getMeasurement() {
		return measurement;
	}
	public void setMeasurement(MeasuringUnit measurement) {
		this.measurement = measurement;
	}
	public float getLowLevel() {
		return lowLevel;
	}
	public void setLowLevel(float lowLevel) {
		this.lowLevel = lowLevel;
	}
	public float getMediumLevel() {
		return mediumLevel;
	}
	public void setMediumLevel(float mediumLevel) {
		this.mediumLevel = mediumLevel;
	}
	public float getHighLevel() {
		return highLevel;
	}
	public void setHighLevel(float highLevel) {
		this.highLevel = highLevel;
	}
	
	
	
}
