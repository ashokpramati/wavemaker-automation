package com.pramati.cloudjee.test.rest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.pramati.cloudjee.utils.BaseTest;
import com.pramati.cloudjee.utils.RestApiCall;

/**
 * Check for multiple war file upload error message
 * 
 * @author krishnakumarnellore
 *
 */
public class MaxWarUploadTest extends BaseTest {

	protected static Logger log = Logger.getLogger(RestApiTest.class);

	@Test	
	public void verifyMaxDeployApplicationAPI() throws ParseException {
		log.info("Started deploying of APP.");
		
		String JsonResponse = RestApiCall.getJSONResponse("maxdeploy");
		log.info("Completed deployment of APP.");
		JsonNode node = getJsonNode(JsonResponse);
		System.out.println(node.getValueAsText());
	}
}