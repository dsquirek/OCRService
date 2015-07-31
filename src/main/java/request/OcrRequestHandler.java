package request;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import response.OcrResponse;

import com.careerbuilder.datascience.BlankSpace.AbstractRequestHandler;
import com.careerbuilder.datascience.BlankSpace.exceptions.*;

public class OcrRequestHandler extends AbstractRequestHandler<OcrRequest>{

	public OcrRequestHandler(Class<OcrRequest> requestClass) {
		super(requestClass);
	}

	@Override
	public OcrResponse handleRequest(OcrRequest request)
			throws BadGatewayException, InternalServerErrorException {
		
		String documentID = getNewDocumentID();
		File imageFile = writeFile(decodeImage(request.getImageDocument()),documentID);
		callOcr(imageFile);
		return new OcrResponse(readFile(documentID));
	}
	
	private File writeFile(byte[] image, String documentID) throws InternalServerErrorException {
		File file = new File(documentID+"jpg");
		try {		
			if(!file.exists()){
				file.createNewFile();
			}			
			FileOutputStream output = new FileOutputStream(file);
			IOUtils.write(image, output);
			return file;
		}
		catch (IOException e) {
			throw new InternalServerErrorException(new DataSciError("Error Writing Image File","Unable to write image file: "+file.getName()));
		}	
	}
	
	private byte[] decodeImage(String imageDoc){
		return Base64.getDecoder().decode(imageDoc);
	}
	
	private String encodeText(byte[] docbytes){
		return Base64.getEncoder().encodeToString(docbytes);
	}
	
	private void callOcr(File imageFile) throws InternalServerErrorException{
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec("./ocr "+imageFile.getName());
		} catch (IOException e) {
			throw new InternalServerErrorException(new DataSciError("Error Running OCR","Ocr failed on document: "+imageFile.getName()));
		}
		deleteFile(imageFile);
	}
	
	private String readFile(String docID) throws InternalServerErrorException{
		File file = new File(docID+".txt");
		try {
			String encodedString = encodeText(FileUtils.readFileToByteArray(file));
			deleteFile(file);
			return encodedString;
		} catch (IOException e) {
			throw new InternalServerErrorException(new DataSciError("Error Reading Text File","Unable to write text file: "+file.getName()));
		}
	}
	
	private void deleteFile(File file) throws InternalServerErrorException{
		try {
			Files.delete(Paths.get(file.getAbsolutePath()));
		} catch (IOException e) {
			throw new InternalServerErrorException(new DataSciError("Unable To Delete File","Unable to delete file: "+file.getName()));
		}
	}
	
	private String getNewDocumentID(){
		return UUID.randomUUID().toString();
	}

}
