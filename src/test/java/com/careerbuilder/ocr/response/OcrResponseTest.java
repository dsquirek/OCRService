package com.careerbuilder.ocr.response;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.careerbuilder.ocr.response.OcrResponse;

public class OcrResponseTest {

	@Test
	public void OcrResponseMatchesInputDataTest() {
		String convertedDocument = "SGVsbG8gV29ybGQ=";
		OcrResponse resp = new OcrResponse(convertedDocument);
		assertEquals(convertedDocument, resp.getConvertedDocument());
	}

}
