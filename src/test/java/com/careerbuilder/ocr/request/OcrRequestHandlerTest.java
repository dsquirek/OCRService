package com.careerbuilder.ocr.request;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.careerbuilder.datascience.BlankSpace.exceptions.BadGatewayException;
import com.careerbuilder.datascience.BlankSpace.exceptions.InternalServerErrorException;
import com.careerbuilder.ocr.request.OcrRequest;
import com.careerbuilder.ocr.request.OcrRequestHandler;

public class OcrRequestHandlerTest {

	@Test
	public void documentIDReturnsUniqueID() throws BadGatewayException,
			InternalServerErrorException {
		OcrRequestHandler handler = new OcrRequestHandler(OcrRequest.class);
		String docID = handler.getNewDocumentID();
		String[] listIDs = createListOfUniqueIDs();
		for (String id : listIDs) {
			assertFalse(docID.equals(id));
		}
	}

	@Test
	public void decodeImageRetrunsDecdoedDocument() {
		OcrRequestHandler handler = new OcrRequestHandler(OcrRequest.class);
		byte[] bytes = handler.decodeImage("SGVsbG8gV29ybGQ=");
		assertTrue("Hello World".equals(new String(bytes)));
	}

	@Test
	public void encodeTextReturnsEncodedText() {
		OcrRequestHandler handler = new OcrRequestHandler(OcrRequest.class);
		String encodedString = handler.encodeText("Hello World".getBytes());
		assertTrue("SGVsbG8gV29ybGQ=".equals(encodedString));
	}

	@Test
	public synchronized void deleteFileRemovesFile() {
		File testFile = writeFile();
		OcrRequestHandler handler = new OcrRequestHandler(OcrRequest.class);
		String path = testFile.getPath();
		try {
			handler.deleteFile(testFile);
		} catch (InternalServerErrorException e) {
			e.printStackTrace();
		}
		File file = new File(path);
		assertFalse(file.exists());
	}

	private String[] createListOfUniqueIDs() {
		String[] listIDs = new String[100];
		for (int i = 0; i < listIDs.length; i++) {
			listIDs[i] = UUID.randomUUID().toString();
		}
		return listIDs;
	}

	private File writeFile() {
		File file = new File("TestFile.jpg");
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream output = new FileOutputStream(file);
			IOUtils.write("Hello World".getBytes(), output);
			IOUtils.closeQuietly(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
