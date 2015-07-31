package com.careerbuilder.ocr;

import request.OcrRequest;
import request.OcrRequestHandler;
import response.OcrResponse;

import com.careerbuilder.datascience.BlankSpace.AbstractRequestHandler;
import com.careerbuilder.datascience.BlankSpace.AbstractResource;
import com.careerbuilder.datascience.BlankSpace.ResourceDefinition;

public class OCRApp extends AbstractResource {

	OCRApp() {
		super(buildResourceDefinition());
	}
	
	static ResourceDefinition<OcrRequest, OcrResponse> buildResourceDefinition() {
		String route = "ocrdocument";
		AbstractRequestHandler<OcrRequest> requestHandler = new OcrRequestHandler(
				OcrRequest.class);
		return new ResourceDefinition<OcrRequest, OcrResponse>(route,
				OcrRequest.class, OcrResponse.class, requestHandler);
	}
	
}
