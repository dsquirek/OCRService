package com.careerbuilder.ocr.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.careerbuilder.datascience.BlankSpace.exceptions.BadRequestException;
import com.careerbuilder.datascience.BlankSpace.exceptions.DataSciError;
import com.careerbuilder.datascience.BlankSpace.request.CachingServiceRequest;
import com.google.common.base.Strings;

public class OcrRequest implements CachingServiceRequest {

	private String imageDocument;

	public OcrRequest(String imageDocument) {
		this.imageDocument = imageDocument;
	}

	public String getImageDocument() {
		return imageDocument;
	}

	@Override
	public Map<String, String> getRequestParams() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("document", getImageDocument());
		return requestParams;
	}

	@Override
	public void validate() throws BadRequestException {
		if (Strings.isNullOrEmpty(getImageDocument())) {
			throw new BadRequestException(new DataSciError("No document to convert",
					" Document cannot be null or empty"));
		}
		if (!Base64.isBase64(getImageDocument().getBytes())) {
			throw new BadRequestException(new DataSciError("Document is not base64",
					" Document must be a base64 encoded string"));
		}
	}

	@Override
	public List<String> getAllowableCacheParams() {
		return Arrays.asList("document");
	}

}
