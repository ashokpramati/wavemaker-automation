package com.imaginea.cloudjee.test.rest;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.imaginea.cloudjee.utils.RestApiCall;

public class ResrApiTest {
	
	@Test
	public void verifyCreateUser() throws ParseException{
		RestApiCall restApiCall = new RestApiCall();
		JSONObject jResponse = restApiCall.getJSONResponse("http://validate.jsontest.com/?json=[JSON-code-to-validate]");
		System.out.println(jResponse.toJSONString());
		System.out.println(jResponse.get("object_or_array").toString());
	}

}
