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

	public void editSvgPathColor(Document docToEdit, String pathId, String color) {
		docToEdit.getElementById(pathId).setAttribute("style", "fill:" + color
				+ ";fill-rule:evenodd;stroke:#000000;stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;stroke-opacity:1");
	}

	public void writeSvgDoc(Document docToWrite) {
		writer.writeSvgDoc(docToWrite);
	}
}
