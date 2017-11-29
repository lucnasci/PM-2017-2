import java.io.IOException;

import java.util.ArrayList;

import org.w3c.dom.Document;

import file.manipulator.PdfManipulator;
import file.manipulator.SvgManipulator;

import model.*;

public class Main {

	public static void main(String[] args) throws IOException {
		DisciplineManipulator disciplineManipulator = DisciplineManipulator.getInstance();
		PdfManipulator pdfManipulator = PdfManipulator.getInstance();
		StudentManipulator studentManipulator = StudentManipulator.getInstance();

		String svgPath = "src/resources/grade_curricular.svg";

		// System.out.println(pdfManipulator.extractTextFromPdf("src/resources/file_2.pdf"));

		Student student = studentManipulator.getStudentFromRecord("src/resources/file_2.pdf");
		StudentVerificator studentVerificator = StudentVerificator.getInstance();
		studentVerificator
				.verifyStudentAccumulatedEfficiencyCoefficientSituation(student.getAccumulatedEfficiencyCoefficient());
		studentVerificator.verifyStudentGraduationDeadline(student.getAdmissionYear());

		ArrayList<Discipline> courseDisciplines = disciplineManipulator.getListOfDisciplinesBasedOnSvg(svgPath);
		ArrayList<Discipline> studentDisciplines = disciplineManipulator
				.getDisciplinesFromDisciplineRecord(courseDisciplines, "src/resources/file_2.pdf");
		SvgManipulator svgManipulator = SvgManipulator.getInstance();
		Document document = svgManipulator.getDocumentFromSvgPath(svgPath);
		studentDisciplines.forEach(discipline -> svgManipulator.paintDiscipine(document, discipline));
		svgManipulator.writeSvgDoc(document);
	}
}
