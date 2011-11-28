package com.ryaltech.whitewater.gauges.server;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.ryaltech.whitewater.gauges.model.RiverInfo;

public class JSONTest {

	@Test
	public void testRiverInfoArray() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		RiverInfo[] ri = {new RiverInfo().setGaugeDataCollectorId("com.ryaltech.whitewater.gauges.services.EcWaterWebDataCollector").setGaugeId("02KB001").setHighLevel(180).setMediumLevel(120).setRiverName("Petawawa park section")};
		String serialized = om.writeValueAsString(ri);
		System.out.println(serialized);
		RiverInfo[] deri = om.readValue(serialized, RiverInfo[].class);
		
	}

}
