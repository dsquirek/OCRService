package com.careerbuilder.ocr.request;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.careerbuilder.datascience.BlankSpace.AbstractRequestHandler;
import com.careerbuilder.datascience.BlankSpace.exceptions.*;
import com.careerbuilder.ocr.response.OcrResponse;

public class OcrRequestHandler extends AbstractRequestHandler<OcrRequest> {

	private String documentID;

	public OcrRequestHandler(Class<OcrRequest> requestClass) {
		super(requestClass);
	}

	@Override
	public OcrResponse handleRequest(OcrRequest request) throws BadGatewayException,
			InternalServerErrorException {

		documentID = getNewDocumentID();
		File imageFile = writeFile(decodeImage(request.getImageDocument()));
		callOcr(imageFile);
		return new OcrResponse(readFile());
	}

	File writeFile(byte[] image) throws InternalServerErrorException {
		File file = new File(documentID + ".jpg");
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream output = new FileOutputStream(file);
			IOUtils.write(image, output);
			IOUtils.closeQuietly(output);
			return file;
		} catch (IOException e) {
			throw new InternalServerErrorException(new DataSciError("Error Writing Image File",
					"Unable to write image file: " + file.getName()));
		}
	}

	byte[] decodeImage(String imageDoc) {
		return Base64.getDecoder().decode(imageDoc);
	}

	String encodeText(byte[] docbytes) {
		return Base64.getEncoder().encodeToString(docbytes);
	}

	void callOcr(File imageFile) throws InternalServerErrorException {
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec("./ocr " + imageFile.getName());
		} catch (IOException e) {
			throw new InternalServerErrorException(new DataSciError("Error Running OCR",
					"Ocr failed on document: " + imageFile.getName()));
		}
		deleteFile(imageFile);
	}

	String readFile() throws InternalServerErrorException {
		File file = new File(documentID + ".txt");
		try {
			String encodedString = encodeText(FileUtils.readFileToByteArray(file));
			deleteFile(file);
			return encodedString;
		} catch (IOException e) {
			throw new InternalServerErrorException(new DataSciError("Error Reading Text File",
					"Unable to write text file: " + file.getName()));
		}
	}

	void deleteFile(File file) throws InternalServerErrorException {
		try {
			Files.delete(Paths.get(file.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
			throw new InternalServerErrorException(new DataSciError("Unable To Delete File",
					"Unable to delete file: " + file.getName()));
		}
	}

	String getNewDocumentID() {
		return UUID.randomUUID().toString();
	}

}
