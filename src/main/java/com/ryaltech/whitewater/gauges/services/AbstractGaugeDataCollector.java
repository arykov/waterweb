package com.ryaltech.whitewater.gauges.services;

/**
 * Implements getId method to uniformly return canonical class name as an id.
 * @author arykov
 *
 */
public abstract class AbstractGaugeDataCollector implements GaugeDataCollector {

	@Override
	public String getId(){
		return getClass().getCanonicalName();
	}
}
