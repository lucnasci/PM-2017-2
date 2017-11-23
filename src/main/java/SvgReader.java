import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;

public class SvgReader {
	private static SvgReader reader = null;
	
	/**
	 * @return an instance of SvgReader, if it's the first time
	 * it is called than the instance is intantiaded
	 */
	public static SvgReader getInstance() {
		if (reader == null)
			reader = new SvgReader();
		return reader;
	}

	public Document getDocFromSvgPath(String path) {
		// Load Template File (with embedded Fonts)
		FileInputStream svgInputStream = null;
		try {
			svgInputStream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Load SVG into DOM-Tree
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
		Document doc = null;
		try {
			doc = factory.createDocument(parser, svgInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}
}