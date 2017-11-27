import java.io.IOException;

import java.util.ArrayList;

import org.w3c.dom.Document;

public class Main {

	public static void main(String[] args) throws IOException {
		DisciplineManipulator disciplineManipulator = DisciplineManipulator.getInstance();
		PdfManipulator pdfManipulator = PdfManipulator.getInstance();
		StudentManipulator studentManipulator = StudentManipulator.getInstance();
		
		String svgPath = "src/resources/grade_curricular.svg";
		
		//System.out.println(pdfManipulator.extractTextFromPdf("src/resources/file.pdf"));
		
		Student student = studentManipulator.getStudentFromRecord("src/resources/file.pdf");
		
		System.out.println(student.getAdmissionSemester());
		
		/*ArrayList<Discipline> courseDisciplines = disciplineManipulator.getListOfDisciplinesBasedOnSvg(svgPath);
		ArrayList<Discipline> studentDisciplines = disciplineManipulator
				.getDisciplinesFromDisciplineRecord(courseDisciplines, "src/resources/file_2.pdf");
		studentDisciplines.forEach(d -> System.out.println(d.getCode() + ": " + d.getSituation()));
		SvgManipulator svgManipulator = SvgManipulator.getInstance();
		Document doc = svgManipulator.getDocumentFromSvgPath(svgPath);
		studentDisciplines.forEach(d -> svgManipulator.paintDiscipine(doc, d));
		svgManipulator.writeSvgDoc(doc);*/
	}
}
