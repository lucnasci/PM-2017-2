import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.svg2svg.SVGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;

import org.w3c.dom.Document;

public class SvgManipulator {

	public static void main(String[] args) throws IOException, TranscoderException {

		///////////////
		// Load Template File (with embedded Fonts)
		///////////////
		File file = new File("C:/Inmetrics/Ferramentas/Eclipse Foundation/eclipse-jee-mars/workspace/PM20172/grade_curricular.svg");
		FileInputStream svgInputStream = new FileInputStream(file);

		////////////////////
		// Load SVG into DOM-Tree
		////////////////////
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
		Document doc = factory.createDocument(parser, svgInputStream);

		///////////////////////
		// Generate Output File
		///////////////////////
		String savepath = "C:/Inmetrics/Ferramentas/Eclipse Foundation/eclipse-jee-mars/workspace/PM20172/test.svg";
		byte[] fileData = transcodeToSVG(doc);
		FileOutputStream fileSave = new FileOutputStream(savepath);
		fileSave.write(fileData);
		fileSave.close();
		
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
