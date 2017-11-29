package file.manipulator;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.svg2svg.SVGTranscoder;
import org.w3c.dom.Document;

public class SvgWriter {
	private static SvgWriter writer = null;

	public static SvgWriter getInstance() {
		if (writer == null)
			writer = new SvgWriter();
		return writer;
	}

	/**
	 * @param docToWrite
	 * Receives a document and save in the path "src/resources/test.svg"
	 */
	public void writeSvgDoc(Document docToWrite) {
		// Generate Output File
		String savepath = "src/resources/test.svg";
		byte[] fileData = null;
		try {
			fileData = transcodeToSVG(docToWrite);
		} catch (TranscoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileOutputStream fileSave = null;
		try {
			fileSave = new FileOutputStream(savepath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileSave.write(fileData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileSave.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param document
	 * @return 
	 * @throws TranscoderException
	 */
	public static byte[] transcodeToSVG(Document document) throws TranscoderException {

	    try {
	        //Determine output type:
	        SVGTranscoder transcoder = new SVGTranscoder();

	        //Set transcoder input/output
	        TranscoderInput transcoderInput = new TranscoderInput(document);
	        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
	        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bytestream, "UTF-8");
	        TranscoderOutput transcoderOutput = new TranscoderOutput(outputStreamWriter);

	        //Perform transcoding
	        transcoder.transcode(transcoderInput, transcoderOutput);
	        outputStreamWriter.flush();
	        outputStreamWriter.close();

	        return bytestream.toByteArray();

	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	    return null;
	}

}
