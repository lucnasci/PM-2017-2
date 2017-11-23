import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.Document;

public class Main {

	public static void main(String[] args) throws IOException {
		DisciplineRecordReader reader = DisciplineRecordReader.getInstance();
		ArrayList<String> lines = reader.getListOfDisciplines("file.pdf");
		lines.forEach(line -> System.out.println(line));
		SvgManipulator svgManipulator = SvgManipulator.getInstance();
		Document doc = svgManipulator.getDocFromSvgPath("grade_curricular.svg");
		System.out.println(doc.getElementById("TIN0107"));
		String propriedade = "fill:#7B68EE;fill-rule:evenodd;stroke:#000000;stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;stroke-opacity:1";
		doc.getElementById("TIN0107").setAttribute("style", propriedade);	
		svgManipulator.writeSvgDoc(doc);
	}
}
