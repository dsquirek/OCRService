package com.careerbuilder.ocr;

import com.careerbuilder.datascience.BlankSpace.AbstractRequestHandler;
import com.careerbuilder.datascience.BlankSpace.AbstractResource;
import com.careerbuilder.datascience.BlankSpace.ResourceDefinition;
import com.careerbuilder.ocr.request.OcrRequest;
import com.careerbuilder.ocr.request.OcrRequestHandler;
import com.careerbuilder.ocr.response.OcrResponse;

public class OcrApp extends AbstractResource {

	OcrApp() {
		super(buildResourceDefinition());
	}

	static ResourceDefinition<OcrRequest, OcrResponse> buildResourceDefinition() {
		String route = "ocrdocument";
		AbstractRequestHandler<OcrRequest> requestHandler = new OcrRequestHandler(OcrRequest.class);
		return new ResourceDefinition<OcrRequest, OcrResponse>(route, OcrRequest.class,
				OcrResponse.class, requestHandler);
	}

}
