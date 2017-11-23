import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Main {

	public static void main(String[] args) throws IOException {
		 DisciplineRecordReader reader = DisciplineRecordReader.getInstance();
//		 ArrayList<String> lines = reader.getListOfDisciplines("file.pdf");
		// lines.forEach(line -> System.out.println(line));
		SvgManipulator svgManipulator = SvgManipulator.getInstance();
		Document doc = svgManipulator.getDocumentFromSvgPath("grade_curricular.svg");
		// System.out.println(doc.getElementById("TIN0107"));
		// String propriedade =
		// "fill:#7B68EE;fill-rule:evenodd;stroke:#000000;stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;stroke-opacity:1";
		// doc.getElementById("TIN0107").setAttribute("style", propriedade);
		ArrayList<Discipline> dises = new ArrayList<>();
		DisciplineRecordReader dis = DisciplineRecordReader.getInstance();
		for (int index = 0; index < doc.getElementsByTagName("path").getLength(); index++) {
			Element node = (Element) doc.getElementsByTagName("path").item(index);
			for (int nodeIndex = 0; nodeIndex < node.getAttributes().getLength(); nodeIndex++) {
				node.getAttributes().item(nodeIndex).getAttributes();
				if (node.getAttributes().item(nodeIndex).getNodeName().equals("id")) {
					String line = node.getAttributes().item(nodeIndex).getTextContent();
					if (dis.stringBeginsWithDisciplineCode(line)) {
						dises.add(new Discipline(line, null));
					}
				}
			}
			// System.out.println(node.getAttributes().getLength());
		}
//		dises.forEach(di -> System.out.println(di.getCode()));
		Map<String, DisciplineResultEnum> ma = reader.getMapOfDisciplinesAndResult("file.pdf");
		dises.forEach(di -> {
			DisciplineResultEnum result = ma.get(di.getCode());
			if (result != null)
				System.out.println(di.getCode() + ": " + result);
		});
	}
}
