package com.ryaltech.whitewater.gauges.server;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

public class DataCollectorTest extends TestCase {

	public void testGetGaugeValues() throws Exception {

		// "02HB004";

		RiverLevel rv = new EcWaterWebDataCollector()
				.getRiverLevelValues("02KB001");

		System.out.println(rv.getGaugeId() + ":" + rv.getLevel() + ":"
				+ rv.getLastUpdated());
	}

	public void testGaugeDataCollectorHub() {
		RiverInfo ri = new RiverInfo().setGaugeDataCollectorName(
				new EcWaterWebDataCollector().getName()).setGaugeId("02KB001");
		GaugeDataCollectionHub hub = new GaugeDataCollectionHub(
				new EcWaterWebDataCollector());
		RiverLevel rv = hub.getRiverLevel(ri);

		System.out.println(rv.getGaugeId() + ":" + rv.getLevel() + ":"
				+ rv.getLastUpdated());
	}

	public void testGaugeDataCollectorHubFromSpringCtx() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring.xml");

		ctx.getBean(GaugeDataCollectionHub.class);

		RiverInfo ri = new RiverInfo().setGaugeDataCollectorName(
				new EcWaterWebDataCollector().getName()).setGaugeId("02KB001");
		GaugeDataCollectionHub hub = ctx.getBean(GaugeDataCollectionHub.class);
		;
		RiverLevel rv = hub.getRiverLevel(ri);

		System.out.println(rv.getGaugeId() + ":" + rv.getLevel() + ":"
				+ rv.getLastUpdated());
	}

}
