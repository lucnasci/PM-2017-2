import java.io.IOException;

import java.util.ArrayList;

import org.w3c.dom.Document;

import file.manipulator.SvgManipulator;
import model.Discipline;
import model.DisciplineManipulator;

public class Main {

	public static void main(String[] args) throws IOException {
		DisciplineManipulator disciplineManipulator = DisciplineManipulator.getInstance();

		String svgPath = "src/resources/grade_curricular.svg";

		ArrayList<Discipline> courseDisciplines = disciplineManipulator.getListOfDisciplinesBasedOnSvg(svgPath);
		ArrayList<Discipline> studentDisciplines = disciplineManipulator
				.getDisciplinesFromDisciplineRecord(courseDisciplines, "src/resources/file.pdf");
		SvgManipulator svgManipulator = SvgManipulator.getInstance();
		Document doc = svgManipulator.getDocumentFromSvgPath(svgPath);
		studentDisciplines.forEach(d -> svgManipulator.paintDiscipine(doc, d));
		svgManipulator.writeSvgDoc(doc);
	}
}
