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
	private static final String MAX_UNDEPLOY = "maxundeploy";


	private static final String NODE_CAUSE = "cause";

	private static final String EXPECTED_MAXERROR = "Maximun of 5 already deployed.";


	@Test(description="Maximum application deployment test case, Only 5 Application war file need to be uploaded in cloud")	
	public void verifyMaxDeployApplicationAPI() throws ParseException {
		log.info("Started running maximum APP deployment.");		
		String JsonResponse = RestApiCall.getJSONResponse(MAX_DEPLOY);
		log.info("Completed max app deployment.");

		JsonNode node = getJsonNode(JsonResponse);

		Assert.assertEquals(node.findValue(NODE_CAUSE).getTextValue(), EXPECTED_MAXERROR, "Mismatch in displayed cause, displayed cause is "+node.findValue(NODE_CAUSE));

	}

	@Test(dependsOnMethods="verifyMaxDeployApplicationAPI",description="Maximun application deployment test case, Only 5 Application war file need to be uploaded in cloud")
	public void verifyMaxUnDeployApplicationAPI() throws ParseException {
		log.info("Started undeploying maximum APP.");		
		String JsonResponse = RestApiCall.getJSONResponse(MAX_UNDEPLOY);
		log.info("Completed undeploying max app.");

	}
}