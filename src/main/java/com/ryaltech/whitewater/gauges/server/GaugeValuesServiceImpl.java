package com.ryaltech.whitewater.gauges.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Queue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class GaugeValuesServiceImpl implements GaugeValuesService {

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
	public List<GaugeValue> getGaugeValues(List<String> gaugeIds)
			throws Exception {
		List<GaugeValue> gaugeValues = new ArrayList<GaugeValue>();
		for (String gaugeId : gaugeIds) {
			Date tomorrow = tomorrow();
			Date yesterday = yesterday();

			String referer = String
					.format(
							"http://www.wateroffice.ec.gc.ca/graph/graph_e.html?mode=text&stn=%s&syr=%d&smo=%d&sday=%d&eyr=%d&emo=%d&eday=%d&prm1=6",
							gaugeId, yesterday.getYear() + 1900, yesterday
									.getMonth() + 1, yesterday.getDate(),
							tomorrow.getYear() + 1900, tomorrow.getMonth() + 1,
							tomorrow.getDate());
			// referer="http://www.wateroffice.ec.gc.ca/graph/graph_e.html?mode=text&stn=02HB004&syr=2010&smo=09&sday=25&eyr=2010&emo=09&eday=27&prm1=6";

			RelaxedHttpClient httpclient = new RelaxedHttpClient();

			HttpPost method = new HttpPost(
					"http://www.wateroffice.ec.gc.ca/include/disclaimer.php");

			method.addHeader("Referer", referer);
			List<NameValuePair> requestParams = new ArrayList<org.apache.http.NameValuePair>();
			requestParams.add(new BasicNameValuePair("disclaimer_action",
					"I Agree"));
			method.setEntity(new UrlEncodedFormEntity(requestParams));

			HttpResponse response = httpclient.execute(method);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();

				builder.setEntityResolver(new EntityResolver() {

					@Override
					public InputSource resolveEntity(String publicId,
							String systemId) throws SAXException, IOException {
						System.out.println("Ignoring " + publicId + ", "
								+ systemId);
						return new InputSource(new StringReader(""));
					}
				});
				Document doc = builder.parse(response.getEntity().getContent());

				XPathFactory xpathFactory = XPathFactory.newInstance();
				XPath xpath = xpathFactory.newXPath();
				// XPathExpression expr =
				// xpath.compile("//table[@id=\"dataTable\"]//tr/td");
				XPathExpression expr = xpath
						.compile("/html/body/div/div/div[6]/div[2]/div[2]/form[3]/div/div[2]/table/tbody/tr[last()]/td/text()");
				NodeList nodeList = (NodeList) expr.evaluate(doc,
						XPathConstants.NODESET);
				if (nodeList != null && nodeList.getLength() == 2) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String date = nodeList.item(0).getNodeValue();
					String value = nodeList.item(1).getNodeValue();
					System.out.println(sdf.parse(date));
					System.out.println(value);
				}

			}

		}

		return gaugeValues;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ryaltech.whitewater.gauges.server.GaugeValuesService#
	 * getRunnableConditions(java.util.List)
	 */
	public List<RunnableConditions> getRunnableConditions(List<String> gaugeIds) {
		return null;
	}

}
