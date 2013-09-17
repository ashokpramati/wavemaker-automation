package com.pramati.cloudjee.test.rest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
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

	private static final String MAX_DEPLOY = "maxdeploy";
	private static final String NODE_CAUSE = "cause";

	@Test(description="Maximum application deployment test case, Only 5 Application war file need to be uploaded in cloud")	
	public void verifyMaxDeployApplicationAPI() throws ParseException {
		log.info("Started running maximum APP deployment.");		
		String JsonResponse = RestApiCall.getJSONResponse(MAX_DEPLOY);
		log.info("Completed max app deployment.");
		JsonNode node = getJsonNode(JsonResponse);
		Assert.assertEquals(node.findValues(NODE_CAUSE).get(0).getTextValue(),"Maximun of 5 already deployed.","More than 5 application deployment limit is crossed");
	}
}