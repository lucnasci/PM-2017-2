package model;

import java.io.IOException;

import java.util.ArrayList;

import file.manipulator.PdfManipulator;

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
	 * @param studentRecordPdf
	 *            Path of the student record PDF file to be read and extract
	 *            text.
	 * @return A Student object, created with the info collected from the
	 *         student record PDF file.
	 * @throws IOException
	 */
	public float getStudentFromRecord(String studentRecordPdf) throws IOException {
		ArrayList<String> studentRecordTextLines = PdfManipulator.getInstance().extractTextFromPdf(studentRecordPdf);

		return getCumulativeYieldCoefficient(
				studentRecordTextLines);/*
								 * new
								 * Student(getRegistrationCode(studentRecord),
								 * getAdmissionYear(studentRecord),
								 * getAdmissionSemester(studentRecord),
								 * calculateCumulativeYieldCoefficient(
								 * studentRecord));
								 */
	}

	/**
	 * @param studentRecordTextLines
	 *            ArrayList containing the text lines, in String format, of the
	 *            student record PDF file.
	 * @return A String containing the student registration code.
	 */
	private String getRegistrationCode(ArrayList<String> studentRecordTextLines) {
		ArrayList<String> registrationCodeTextLine = new ArrayList<>();

		for (String studentRecordTextLine : studentRecordTextLines) {
			if (studentRecordTextLine.contains("Matrícula: ")) {
				registrationCodeTextLine.add(studentRecordTextLine);
				break;
			}
		}

		String[] registrationCodeTextLineSubstrings = registrationCodeTextLine.get(0).split(" ", -1);
		String registrationCode = registrationCodeTextLineSubstrings[1];

		return registrationCode;
	}

	/**
	 * @param studentRecordTextLines
	 *            ArrayList containing the text lines, in String format, of the
	 *            student record PDF file.
	 * @return A int containing the student admission year.
	 */
	private int getAdmissionYear(ArrayList<String> studentRecordTextLines) {
		String admissionYear = getRegistrationCode(studentRecordTextLines).substring(0, 4);

		return Integer.parseInt(admissionYear);
	}

	/**
	 * @param studentRecordTextLines
	 *            ArrayList containing the text lines, in String format, of the
	 *            student record PDF file.
	 * @return A int containing the student admission semester, 1 or 2.
	 */
	private int getAdmissionSemester(ArrayList<String> studentRecordTextLines) {
		String admissionSemester = getRegistrationCode(studentRecordTextLines).substring(4, 5);

		return Integer.parseInt(admissionSemester);
	}
	
	private float getCumulativeYieldCoefficient(ArrayList<String> studentRecordTextLines) {
		ArrayList<String> studentGradesTextLines = new ArrayList<>();
		float cumulativeYieldCoefficient = 0;
		
		for (String studentRecordTextLine : studentRecordTextLines) {
			if (stringBeginsWithDisciplineCode(studentRecordTextLine)) {
				if (!studentRecordTextLine.contains("Atividades Curriculares") && !studentRecordTextLine.contains("Dispensa sem nota")
						&& !studentRecordTextLine.contains("ASC - Matrícula") && !studentRecordTextLine.contains("TRA - Trancamento")) {
					studentGradesTextLines.add(studentRecordTextLine);
				}
			}
		}

		return 0;
	}

	private int getDisciplineCredit(String studentGradesTextLine) {
		String[] studentGradesTextLineSubstrings = studentGradesTextLine.split(",", 2);
		studentGradesTextLineSubstrings = studentGradesTextLineSubstrings[0].split(" ", -1);
		String disciplineCredit = studentGradesTextLineSubstrings[studentGradesTextLineSubstrings.length - 3];

		return Integer.valueOf(disciplineCredit);
	}

	private float getStudentGrade(String studentGradesTextLine) {
		String[] studentGradesTextLineSubstrings = studentGradesTextLine.split(",", 2);
		String studentGradeText = studentGradesTextLineSubstrings[0]
				.substring(studentGradesTextLineSubstrings[0].length() - 2, studentGradesTextLineSubstrings[0].length())
				+ "." + studentGradesTextLineSubstrings[1].substring(0, 2);

		return Float.parseFloat(studentGradeText);
	}

	/**
	 * @param text
	 *            String where the pattern of discipline code will be searched.
	 * @return true if the beginning of String matches the pattern (3 characters
	 *         and 4 digits), otherwise, returns false.
	 */
	public static boolean stringBeginsWithDisciplineCode(String text) {
		if (text.length() >= 7) {
			for (int index = 0; index < 3; index++) {
				char character = text.charAt(index);
				if (!Character.isAlphabetic(character))
					return false;
			}
			for (int index = 3; index < 7; index++) {
				char character = text.charAt(index);
				if (!Character.isDigit(character))
					return false;
			}
			return true;
		}
		return false;
	}
}
