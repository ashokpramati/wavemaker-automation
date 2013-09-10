package com.pramati.cloudjee.test.rest;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.pramati.cloudjee.utils.BaseTest;
import com.pramati.cloudjee.utils.ConfigProperties;
import com.pramati.cloudjee.utils.RestApiCall;

/**
 * Test Case for Rest API Call.Below test case are covered.
 * 
 * <b>Deploy Application</b> </br> <b>Stop Application</b> </br> <b>Start
 * Application</b> </br> <b>List Application</b> </br> <b>Undeploy
 * Applications</b>
 * 
 * 
 * @author krishnakumarnellore
 * 
 */
public class RestApiTest extends BaseTest {

	protected static Logger log = Logger.getLogger(RestApiTest.class);

	@Test
	@Parameters({ "type", "appFileName", "appState", "instanceGroupName",
			"name", "sslEnabled", "url" })
	public void verifyDeployApplicationAPI(String type, String appFileName,
			String appState, String instanceGroupName, String name,
			String sslEnabled, String url) throws ParseException {
		log.info("Started deploying of APP.");
		JsonNode bodyNode = null;
		String JsonResponse = RestApiCall.getJSONResponse("deploy");
		log.info("Completed deployment of APP.");
		JsonNode node = getJsonNode(JsonResponse);
		bodyNode = node.findValue("type");
		Assert.assertEquals(bodyNode.getTextValue(), "application",
				"type doesn't match");
		bodyNode = node.findValue("appFileName");
		Assert.assertEquals(bodyNode.getTextValue(), ConfigProperties.APP_NAME
				+ ".war", "APP Name doesn't match");
		bodyNode = node.findValue("appState");
		Assert.assertEquals(bodyNode.getTextValue(), "STARTED",
				"type doesn't match");
		bodyNode = node.findValue("instanceGroupName");
		Assert.assertEquals(bodyNode.getTextValue(), "defaultgroup",
				"Default group doesn't match");
		bodyNode = node.findValue("name");
		Assert.assertEquals(bodyNode.getTextValue(), ConfigProperties.APP_NAME,
				"APP Name doesn't match");
		bodyNode = node.findValue("sslEnabled");
		Assert.assertFalse(bodyNode.getBooleanValue(), "SSL is not enabled");
		bodyNode = node.findValue("url");
		Assert.assertEquals(bodyNode.getTextValue(),
				"http://ashok.apps.mywavemaker.com/sample/",
				"type doesn't match");
		Assert.assertTrue(node.findValue("time-taken").getIntValue() < 10,
				"Time taken is more than 10");
	}

	@Test(dependsOnMethods = "verifyDeployApplicationAPI")
	public void verifyStopApplicationAPI() throws ParseException {
		log.info("Beginning to stop APP.");
		String JsonResponse = RestApiCall.getJSONResponse("stop");
		log.info("Stopped APP.");
		JsonNode node = getJsonNode(JsonResponse);
		JsonNode bodyNode = node.findValue("served-by");
		Assert.assertEquals(bodyNode.getTextValue(), "www.cloudjee.com");
		Assert.assertTrue(node.findValue("time-taken").getIntValue() < 10,
				"Time taken is more than 10");
	}

	@Test(dependsOnMethods = "verifyStopApplicationAPI")
	public void verifyStartApplicationAPI() throws ParseException {
		log.info("Starting APP.");
		String JsonResponse = RestApiCall.getJSONResponse("start");
		log.info("Started APP.");
		JsonNode node = getJsonNode(JsonResponse);
		JsonNode bodyNode = node.findValue("served-by");
		Assert.assertEquals(bodyNode.getTextValue(), "www.cloudjee.com");
		Assert.assertTrue(node.findValue("time-taken").getIntValue() < 10,
				"Time taken is more than 10");
	}

	@Test(dependsOnMethods = "verifyStartApplicationAPI")
	public void verifyListApplicationsAPI() throws ParseException {
		log.info("Listing APP.");
		List<JsonNode> bodyNode = null;
		String JsonResponse = RestApiCall.getJSONResponse("list");
		log.info("APP's are listed.");
		JsonNode node = getJsonNode(JsonResponse);
		bodyNode = node.findValues("type");
		Assert.assertTrue(bodyNode.toString().contains("application"),
				"Type doesn't match");
		bodyNode = node.findValues("appFileName");
		Assert.assertTrue(
				bodyNode.toString()
						.contains(ConfigProperties.APP_NAME + ".war"),
				"APP Name doesn't match");
		bodyNode = node.findValues("appState");
		Assert.assertTrue(bodyNode.toString().contains("STARTED"),
				"type doesn't match");
		bodyNode = node.findValues("instanceGroupName");
		Assert.assertTrue(bodyNode.toString().contains("defaultgroup"),
				"Default group doesn't match");
		bodyNode = node.findValues("name");
		Assert.assertTrue(
				bodyNode.toString().contains(ConfigProperties.APP_NAME),
				"APP Name doesn't match");
		bodyNode = node.findValues("sslEnabled");
		System.out.println(bodyNode);
		Assert.assertFalse(bodyNode.contains(false), "SSL is not enabled");
		bodyNode = node.findValues("url");
		Assert.assertTrue(
				bodyNode.toString().contains(
						"http://ashok.apps.mywavemaker.com/sample/"),
				"type doesn't match");
		Assert.assertTrue(node.findValue("time-taken").getIntValue() < 10,
				"Time taken is more than 10");

	}

	@Test(dependsOnMethods = "verifyListApplicationsAPI")
	public void verifyUndeployApplicationAPI() throws ParseException {
		log.info("Undeploying APP.");
		String JsonResponse = RestApiCall.getJSONResponse("undeploy");
		log.info("Completed undeployment of APP.");
		JsonNode node = getJsonNode(JsonResponse);
		JsonNode bodyNode = node.findValue("served-by");
		Assert.assertEquals(bodyNode.getTextValue(), "www.cloudjee.com");
		Assert.assertTrue(node.findValue("time-taken").getIntValue() < 10,
				"Time taken is more than 10");

	}

}
