package com.ryaltech.whitewater.gauges.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class EcWaterWebDataCollector implements GaugeDataCollector {
	private static SimpleDateFormat GaugeDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static Logger logger = Logger
			.getLogger(EcWaterWebDataCollector.class);

	private static XPathExpression expr;

	static {
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();

		try {
			expr = xpath
					.compile("//table[@id=\"dataTable\"]//tr[last()]/td/text()");
		} catch (Exception ex) {
			throw new RuntimeException("Unable to compile XPath", ex);
		}
	}

	public Date yesterday() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
		return calendar.getTime();

	}

	public Date tomorrow() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
		return calendar.getTime();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ryaltech.whitewater.gauges.server.GaugeValuesService#getGaugeValues
	 * (java.util.List)
	 */
	public RiverLevel getRiverLevelValues(String gaugeId) {

		//TODO: simplify and perform clean up
		try {
			Date tomorrow = tomorrow();
			Date yesterday = yesterday();

			String referer = String
					.format("http://www.wateroffice.ec.gc.ca/graph/graph_e.html?mode=text&stn=%s&syr=%d&smo=%d&sday=%d&eyr=%d&emo=%d&eday=%d&prm1=6",
							gaugeId, yesterday.getYear() + 1900,
							yesterday.getMonth() + 1, yesterday.getDate(),
							tomorrow.getYear() + 1900, tomorrow.getMonth() + 1,
							tomorrow.getDate());
			// referer="http://www.wateroffice.ec.gc.ca/graph/graph_e.html?mode=text&stn=02HB004&syr=2010&smo=09&sday=25&eyr=2010&emo=09&eday=27&prm1=6";
			URL url = new URL(
					"http://www.wateroffice.ec.gc.ca/include/disclaimer.php");
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();

			httpURLConnection
					.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401");
			httpURLConnection.setRequestProperty("Referer", referer);
			httpURLConnection.setInstanceFollowRedirects(false);

			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);

			httpURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			StringBuffer sbContent = new StringBuffer();
			sbContent.append("disclaimer_action=");
			sbContent.append(URLEncoder.encode("I Agree", "UTF-8"));
			DataOutputStream stream = new DataOutputStream(
					httpURLConnection.getOutputStream());
			stream.writeBytes(sbContent.toString());
			stream.flush();
			stream.close();

			if (httpURLConnection.getResponseCode() != 302) {
				logger.error(String
						.format("Unexpected HTTP code: %d while trying to agree to EC Water Office disclaimer for gauge %s. Expected HTTP code 302",
								httpURLConnection.getResponseCode(), gaugeId));
				throw new RuntimeException(
						String.format(
								"Unexpected HTTP code: %d while trying to agree to EC Water Office disclaimer for gauge %s. Expected HTTP code 302",
								httpURLConnection.getResponseCode(), gaugeId));

			} else {
				String newPageUri = httpURLConnection
						.getHeaderField("Location");
				if (newPageUri == null) {
					logger.error(String
							.format("Unexpected lack of redirect location after agree to EC Water Office disclaimer for gauge %s.",
									gaugeId));
					newPageUri = referer;
				}
				List<String> cookies = httpURLConnection.getHeaderFields().get(
						"Set-Cookie");
				httpURLConnection.disconnect();

				// follow redirect
				httpURLConnection = (HttpURLConnection) new URL(newPageUri)
						.openConnection();
				// copy cookies
				StringBuffer cookieBuffer = new StringBuffer();
				for (String cookie : cookies) {
					String chunks[] = cookie.split(";");
					if (cookieBuffer.length() > 0)
						cookieBuffer.append("; ");
					cookieBuffer.append(chunks[0]);
				}
				httpURLConnection.setRequestProperty("Cookie",
						cookieBuffer.toString());
				// do the usual
				httpURLConnection.setUseCaches(false);
				httpURLConnection.setDoOutput(false);
				httpURLConnection.setDoInput(true);
				InputStream inputStream = httpURLConnection.getInputStream();

				BufferedReader in = new BufferedReader(new InputStreamReader(
						inputStream));
				StringBuffer html = new StringBuffer();
				String line;
				while ((line = in.readLine()) != null) {
					line = line.replace("xmlns=", "ignore=");
					html.append(line);

				}
				logger.debug(String.format(
						"For gauge id: %s got the following HTML: %s", gaugeId,
						html));
				inputStream.close();

				if (httpURLConnection.getResponseCode() == 200) {

					DocumentBuilderFactory factory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();

					builder.setEntityResolver(new EntityResolver() {

						@Override
						public InputSource resolveEntity(String publicId,
								String systemId) throws SAXException,
								IOException {
							return new InputSource(new StringReader(""));
						}
					});
					Document doc = builder.parse(new InputSource(
							new StringReader(html.toString())));

					NodeList nodeList;
					synchronized (expr) {
						nodeList = (NodeList) expr.evaluate(doc,
								XPathConstants.NODESET);
					}
					if (nodeList != null && nodeList.getLength() == 2) {

						String lastUpdateStr = nodeList.item(0).getNodeValue();
						String gaugeValueStr = nodeList.item(1).getNodeValue();
						Date lastUpdated = GaugeDateFormat.parse(lastUpdateStr);
						float gaugeValue = Float.parseFloat(gaugeValueStr);
						RiverLevel gv = new RiverLevel().setGaugeId(gaugeId)
								.setLastUpdated(lastUpdated)
								.setLevel(gaugeValue);
						logger.debug(String
								.format("Parsed gauge value: %s", gv));
						return gv;

					}
					logger.warn(String
							.format("Unable to parse out any gauge values for gauge: %s", gaugeId));
					throw new RuntimeException(String
							.format("Unable to parse out any gauge values for gauge: %s", gaugeId));

				}else{
					logger.warn(String
							.format("Did not get 200 as HTTP response when trying to retrieve data for gauge: %s", gaugeId));
					throw new RuntimeException(String
							.format("Did not get 200 as HTTP response when trying to retrieve data for gauge: %s", gaugeId));
				}

			}
		} catch (Exception ex) {
			throw new RuntimeException(String
					.format("Unexpected exception when trying to retrieve data from gauge: %s", gaugeId), ex);
		}

	}

	@Override
	public String getName() {
		return "Environment Canada Water Office";
	}
}
