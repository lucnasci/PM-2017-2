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
		System.out.println(fileData.toString());
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
	
	public static byte[] transcodeToSVG(Document doc) throws TranscoderException {

	    try {
	        //Determine output type:
	        SVGTranscoder t = new SVGTranscoder();

	        //Set transcoder input/output
	        TranscoderInput input = new TranscoderInput(doc);
	        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
	        OutputStreamWriter ostream = new OutputStreamWriter(bytestream, "UTF-8");
	        TranscoderOutput output = new TranscoderOutput(ostream);

	        //Perform transcoding
	        t.transcode(input, output);
	        ostream.flush();
	        ostream.close();

	        return bytestream.toByteArray();

	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	    return null;
	}

}
