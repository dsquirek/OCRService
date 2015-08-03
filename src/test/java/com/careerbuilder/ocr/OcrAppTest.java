package com.careerbuilder.ocr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.careerbuilder.datascience.BlankSpace.ResourceDefinition;
import com.careerbuilder.ocr.request.OcrRequest;
import com.careerbuilder.ocr.request.OcrRequestHandler;
import com.careerbuilder.ocr.response.OcrResponse;

public class OcrAppTest {

	@Test
	public void constructorTest() {
		new OcrApp();
	}

	@Test
	public void buildResourceDefinitionTest() {
		ResourceDefinition<OcrRequest, OcrResponse> rd = OcrApp.buildResourceDefinition();
		assertEquals(OcrRequest.class, rd.getRequestClass());
		assertEquals(OcrRequestHandler.class, rd.getRequestHandler().getClass());
		assertEquals(OcrResponse.class, rd.getResponseClass());
		assertEquals("ocrdocument", rd.getRoute());
	}
}
