package com.careerbuilder.ocr.request;

import org.junit.Test;

import com.careerbuilder.datascience.BlankSpace.exceptions.BadRequestException;
import com.careerbuilder.ocr.request.OcrRequest;

public class OcrRequestTest {

	@Test
	public void validRequestNoExceptionThrown() throws BadRequestException {
		new OcrRequest("SGVsbG8gV29ybGQ=").validate();
	}

	@Test(expected = BadRequestException.class)
	public void nullImageDocumentProvidedThrowsBadRequest() throws BadRequestException {
		new OcrRequest(null).validate();
	}

	@Test(expected = BadRequestException.class)
	public void EmptyImageDocumentProvidedThrowsBadRequest() throws BadRequestException {
		new OcrRequest("").validate();
	}

	@Test(expected = BadRequestException.class)
	public void nonEncodedDocumentProvidedThrowsBadRequest() throws BadRequestException {
		new OcrRequest("Hello World!").validate();
	}

}
