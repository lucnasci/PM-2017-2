import java.io.IOException;
import java.util.ArrayList;

public class StudentManipulator {
	private static StudentManipulator studentManipulator = null;

	private StudentManipulator() {
	};

	/**
	 * @return an instance of StudentManipulator, if it's the first time it
	 *         is called than the instance is instantiated.
	 */
	public static StudentManipulator getInstance() {
		if (studentManipulator == null)
			studentManipulator = new StudentManipulator();
		return studentManipulator;
	}
	
	/**
	 * @param pdfPath
	 *            Path of the PDF file to be read.
	 * @return A new Student object.
	 * @throws IOException
	 */
	public Student getStudentFromRecord(String pdfPath) throws IOException {
		ArrayList<String> studentRecord = PdfManipulator.getInstance().extractTextFromPdf(pdfPath);

		return new Student(getRegistrationCode(studentRecord),getAdmissionYear(studentRecord),getAdmissionSemester(studentRecord)/*,calculateCumulativeYieldCoefficient(lines)*/);
	}

	private String getRegistrationCode(ArrayList<String> lines) {
		ArrayList<String> registrationCodeTextLine = new ArrayList<>();

		for (String line : lines) {
			if (line.contains("Matrícula: ")) {
				registrationCodeTextLine.add(line);
				break;
			}
		}

		String[] registrationCodeTextLineSubstrings = registrationCodeTextLine.get(0).split(" ", -1);
		String registrationCode = registrationCodeTextLineSubstrings[1];

		return registrationCode;
	}

	private int getAdmissionYear(ArrayList<String> lines) {
		String admissionYear = getRegistrationCode(lines).substring(0, 4);

		return Integer.parseInt(admissionYear);
	}

	private int getAdmissionSemester(ArrayList<String> lines) {
		String admissionSemester = getRegistrationCode(lines).substring(4, 5);

		return Integer.parseInt(admissionSemester);
	}

	private float calculateCumulativeYieldCoefficient(ArrayList<String> lines) {
		// TODO Auto-generated method stub
		return 0;
	}
}
