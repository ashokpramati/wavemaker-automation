package com.imaginea.cloudjee.test.rest;

import org.codehaus.jackson.JsonNode;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.imaginea.cloudjee.utils.BaseTest;
import com.imaginea.cloudjee.utils.RestApiCall;

/**
 * Test Case for Rest API Call.Below test case are covered.
 * 
 * <b>Deploy Application</b>
 * </br>
 * <b>Undeploy Application</b>
 * </br>
 * <b>Stop Application</b>
 * </br>
 * <b>Start Application</b>
 * </br>
 * <b>List Applications</b>
 * 
 * 
 * @author krishnakumarnellore
 *
 */
public class RestApiTest extends BaseTest{

	
	@Test(enabled=true)
	public void verifyDeployOfApp() throws ParseException{		
		String JsonResponse = RestApiCall.getJSONResponse("deploy");
		JsonNode node = getJsonNode(JsonResponse);
		JsonNode bodyNode = node.findValue("served-by");
		if (bodyNode != null) {			
			Assert.assertEquals(bodyNode.getTextValue(), "www.cloudjee.com");
		}
	}

	@Test(enabled=false)
	public void verifyListOfApp() throws ParseException{		
		String JsonResponse = RestApiCall.getJSONResponse("list");
		JsonNode node = getJsonNode(JsonResponse);
		JsonNode bodyNode = node.findValue("served-by");
		if (bodyNode != null) {			
			Assert.assertEquals(bodyNode.getTextValue(), "www.cloudjee.com");
		}
	}
	
	@Test(enabled=false)
	public void verifyStartOfApp() throws ParseException{		
		String JsonResponse = RestApiCall.getJSONResponse("start");
		JsonNode node = getJsonNode(JsonResponse);
		JsonNode bodyNode = node.findValue("served-by");
		if (bodyNode != null) {			
			Assert.assertEquals(bodyNode.getTextValue(), "www.cloudjee.com");
		}
	}
	
		
	@Test(enabled=false)
	public void verifyStopOfApp() throws ParseException{		
		String JsonResponse = RestApiCall.getJSONResponse("stop");
		JsonNode node = getJsonNode(JsonResponse);
		JsonNode bodyNode = node.findValue("served-by");
		if (bodyNode != null) {			
			Assert.assertEquals(bodyNode.getTextValue(), "www.cloudjee.com");
		}
	}
	
	
	@Test(enabled=false)
	public void verifyUndeployOfApp() throws ParseException{		
		String JsonResponse = RestApiCall.getJSONResponse("undeploy");
		JsonNode node = getJsonNode(JsonResponse);
		JsonNode bodyNode = node.findValue("served-by");
		if (bodyNode != null) {			
			Assert.assertEquals(bodyNode.getTextValue(), "www.cloudjee.com");
		}
	}
	
}
