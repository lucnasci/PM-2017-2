import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.Document;

public class Main {

	public static void main(String[] args) throws IOException {
		DisciplineManipulator disciplineManipulator = DisciplineManipulator.getInstance();

		String svgPath = "src/resources/grade_curricular.svg";

		ArrayList<Discipline> courseDisciplines = disciplineManipulator.getListOfDisciplinesBasedOnSvg(svgPath);
		ArrayList<Discipline> studentDisciplines = disciplineManipulator
				.getDisciplinesFromDisciplineRecord(courseDisciplines, "file.pdf");
		
		SvgManipulator svgManipulator = SvgManipulator.getInstance();
		Document doc = svgManipulator.getDocumentFromSvgPath(svgPath);
		studentDisciplines.forEach(d -> svgManipulator.paintDiscipine(doc, d));
		svgManipulator.writeSvgDoc(doc);
	}
}
