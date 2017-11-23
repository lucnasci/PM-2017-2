import org.w3c.dom.Document;

public class SvgManipulator {
	
	private static SvgReader reader;
	private static SvgWriter writer;
	private static SvgManipulator manipulator = null;
	
	public static SvgManipulator getInstance() {
		if (reader == null)
			reader = SvgReader.getInstance();
		if (writer == null)
			writer = SvgWriter.getInstance();
		if (manipulator == null)
			manipulator = new SvgManipulator();
		return manipulator;
	}
	
	public Document getDocumentFromSvgPath(String path) {
		return reader.getDocFromSvgPath(path);
	}
	
	public void writeSvgDoc(Document docToWrite) {
		writer.writeSvgDoc(docToWrite);
	}
}
