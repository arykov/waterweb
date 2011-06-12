package com.ryaltech.whitewater.gauges.server;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ryaltech.whitewater.gauges.services.EcWaterWebDataCollector;
import com.ryaltech.whitewater.gauges.services.GaugeDataCollectionHub;
import com.ryaltech.whitewater.gauges.services.RiverInfo;
import com.ryaltech.whitewater.gauges.services.RiverLevel;

import junit.framework.TestCase;

public class DataCollectorTest extends TestCase {


	public void testGaugeDataCollectorHub() {
		RiverInfo ri = new RiverInfo().setGaugeDataCollectorId(
				new EcWaterWebDataCollector().getId()).setGaugeId("02KB001");
		GaugeDataCollectionHub hub = new GaugeDataCollectionHub(
				new EcWaterWebDataCollector());
		RiverLevel rv = hub.getRiverLevel(ri);

		System.out.println(rv.getRiverInfo().getRiverId() + ":" + rv.getLevel() + ":"
				+ rv.getLastUpdated());
	}

	public void testGaugeDataCollectorHubFromSpringCtx() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"waterweb-web-spring.xml");

		ctx.getBean(GaugeDataCollectionHub.class);

		RiverInfo ri = new RiverInfo().setGaugeDataCollectorId(
				new EcWaterWebDataCollector().getId()).setGaugeId("02KB001");
		GaugeDataCollectionHub hub = ctx.getBean(GaugeDataCollectionHub.class);
		;
		RiverLevel rv = hub.getRiverLevel(ri);

		System.out.println(rv.getRiverInfo().getRiverId() + ":" + rv.getLevel() + ":"
				+ rv.getLastUpdated());
	}

}
