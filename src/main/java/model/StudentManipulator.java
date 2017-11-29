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
	public Student getStudentFromRecord(String studentRecordPdf) throws IOException {
		ArrayList<String> studentRecordTextLines = PdfManipulator.getInstance().extractTextFromPdf(studentRecordPdf);

		return new Student(getRegistrationCode(studentRecordTextLines), getAdmissionYear(studentRecordTextLines),
				getAdmissionSemester(studentRecordTextLines), getSemestersTaken(studentRecordTextLines),
				getAccumulatedEfficiencyCoefficient(studentRecordTextLines));
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
	 * @return A integer containing the student admission year.
	 */
	private int getAdmissionYear(ArrayList<String> studentRecordTextLines) {
		String admissionYear = getRegistrationCode(studentRecordTextLines).substring(0, 4);

		return Integer.parseInt(admissionYear);
	}

	/**
	 * @param studentRecordTextLines
	 *            ArrayList containing the text lines, in String format, of the
	 *            student record PDF file.
	 * @return A integer containing the student admission semester, with 1 or 2
	 *         as possible result.
	 */
	private int getAdmissionSemester(ArrayList<String> studentRecordTextLines) {
		String admissionSemester = getRegistrationCode(studentRecordTextLines).substring(4, 5);

		return Integer.parseInt(admissionSemester);
	}
	
	/**
	 * @param studentRecordTextLines
	 *            ArrayList containing the text lines, in String format, of the
	 *            student record PDF file.
	 * @return A integer containing the number of semesters that the student has
	 *         taken.
	 */
	private int getSemestersTaken(ArrayList<String> studentRecordTextLines) {
		ArrayList<String> semestersTakenTextLine = new ArrayList<>();

		for (String studentRecordTextLine : studentRecordTextLines) {
			if (studentRecordTextLine.contains("Período Atual:")) {
				semestersTakenTextLine.add(studentRecordTextLine);
				break;
			}
		}

		String[] semestersTakenTextLineSubstrings = semestersTakenTextLine.get(0).split(" ", -1);
		String semestersTaken = String.valueOf(semestersTakenTextLineSubstrings[2].charAt(0));

		return Integer.parseInt(semestersTaken);
	}
	
	/**
	 * @param studentRecordTextLines
	 *            ArrayList containing the text lines, in String format, of the
	 *            student record PDF file.
	 * @return A float containing the student accumulated efficiency coefficient
	 *         (CRA).
	 */
	private float getAccumulatedEfficiencyCoefficient(ArrayList<String> studentRecordTextLines) {
		ArrayList<String> studentGradesTextLines = new ArrayList<>();

		for (String studentRecordTextLine : studentRecordTextLines) {
			if (stringBeginsWithDisciplineCode(studentRecordTextLine)) {
				if (!studentRecordTextLine.contains("Atividades Curriculares")
						&& !studentRecordTextLine.contains("Dispensa sem nota")
						&& !studentRecordTextLine.contains("ASC - Matrícula")
						&& !studentRecordTextLine.contains("TRA - Trancamento")) {
					studentGradesTextLines.add(studentRecordTextLine);
				}
			}
		}

		return calculateAccumulatedEfficiencyCoefficient(studentGradesTextLines);
	}
	
	/**
	 * @param studentGradesTextLines
	 *            ArrayList containing only the text lines, in String format,
	 *            about the disciplines that influence on the accumulated
	 *            efficiency coefficient.
	 * @return A float containing the student accumulated efficiency coefficient
	 *         (CRA).
	 */
	private float calculateAccumulatedEfficiencyCoefficient(ArrayList<String> studentGradesTextLines) {
		float disciplineGradesSum = 0;
		float disciplineCreditsSum = 0;

		for (String studentGradesTextLine : studentGradesTextLines) {
			disciplineGradesSum += getStudentGrade(studentGradesTextLine) * getDisciplineCredit(studentGradesTextLine);
			disciplineCreditsSum += getDisciplineCredit(studentGradesTextLine);
		}

		return disciplineGradesSum / disciplineCreditsSum;
	}

	/**
	 * @param studentGradesTextLine
	 *            A String containing info about a discipline that influence on
	 *            the accumulated efficiency coefficient.
	 * @return A integer containing the discipline credit value.
	 */
	private int getDisciplineCredit(String studentGradesTextLine) {
		String[] studentGradesTextLineSubstrings = studentGradesTextLine.split(",", 2);
		studentGradesTextLineSubstrings = studentGradesTextLineSubstrings[0].split(" ", -1);
		String disciplineCredit = studentGradesTextLineSubstrings[studentGradesTextLineSubstrings.length - 3];

		return Integer.valueOf(disciplineCredit);
	}

	/**
	 * @param studentGradesTextLine
	 *            A String containing info about a discipline that influence on
	 *            the accumulated efficiency coefficient.
	 * @return A float containing the student grade on the respective
	 *         discipline.
	 */
	private float getStudentGrade(String studentGradesTextLine) {
		String[] studentGradesTextLineSubstrings = studentGradesTextLine.split(",", 2);
		String studentGradeText = studentGradesTextLineSubstrings[0]
				.substring(studentGradesTextLineSubstrings[0].length() - 2, studentGradesTextLineSubstrings[0].length())
				+ "." + studentGradesTextLineSubstrings[1].substring(0, 2);

		return Float.parseFloat(studentGradeText);
	}
	
	private int getStudentMatriculatedDisciplinesNumber(){
		return 0;
	}

	/**
	 * @param text
	 *            String where the pattern of discipline code will be searched.
	 * @return true if the beginning of String matches the pattern (3 characters
	 *         and 4 digits), otherwise, returns false.
	 */
	private static boolean stringBeginsWithDisciplineCode(String text) {
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
