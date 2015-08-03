package com.careerbuilder.ocr.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.careerbuilder.datascience.BlankSpace.response.ServiceResponse;

@XmlRootElement(name = "data")
public final class OcrResponse implements ServiceResponse {
	private String convertedDocument;

	protected OcrResponse() {
	}

	public OcrResponse(String convertedDocument) {
		this.convertedDocument = convertedDocument;
	}

	@XmlElement(name = "converted_doc")
	public String getConvertedDocument() {
		return convertedDocument;
	}
}
