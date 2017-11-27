package model;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import enums.DisciplineResultEnum;
import file.manipulator.PdfManipulator;
import file.manipulator.SvgManipulator;

public class DisciplineManipulator {
	private static DisciplineManipulator reader = null;

	private DisciplineManipulator() {
	};

	/**
	 * @return an instance of DisciplineManipulator, if it's the first time it
	 *         is called than the instance is instantiated.
	 */
	public static DisciplineManipulator getInstance() {
		if (reader == null)
			reader = new DisciplineManipulator();
		return reader;
	}

	/**
	 * @param pdfPath
	 *            of the pdf file to be readen
	 * @return an array list of strings containg data about the disciplines, in the
	 *         bellow pattern</br>
	 *         DISCIPLINE_CODE DISCIPLINE_NAME DISCIPLINE_RESULT DISCIPLINE_SEMESTER
	 * @throws IOException 
	 */
	public ArrayList<String> getListOfDisciplines(String pdfPath) throws IOException {
		ArrayList<String> documentLines = new ArrayList<>();
		ArrayList<String> lines = PdfManipulator.getInstance().extractTextFromPdf(pdfPath);
		int semesterCount = 0;
		for (String line : lines) {
			if (line.contains("semestre de")) {
				semesterCount++;
			}
			if (stringBeginsWithDisciplineCode(line)) {
				line = semesterCount + "º Periodo - " + line;
				documentLines.add(line);
			}
		}
		return documentLines;
	}

	/**
	 * @param disciplineRecordPath
	 *            to a pdf file with the students Discipline Record
	 * @return
	 * @throws IOException 
	 */
	public Map<String, DisciplineResultEnum> getMapOfDisciplinesAndResult(String disciplineRecordPath) throws IOException {
		Map<String, DisciplineResultEnum> documentLines = new HashMap<>();
		ArrayList<String> lines = PdfManipulator.getInstance().extractTextFromPdf(disciplineRecordPath);
		for (String line : lines) {
			if (stringBeginsWithDisciplineCode(line)) {
				for (DisciplineResultEnum result : DisciplineResultEnum.values()) {
					if (containsIgnoreCase(line, result.getText())) {
						documentLines.put(getDisciplineCode(line), getDisciplineResult(line));
					}
				}
			}
		}
		return documentLines;
	}

	/**
	 * @param stringEntry
	 * @param contentToFind
	 * @return it the entered string contains the content to find, ignoring case
	 *         sensitive
	 */
	public boolean containsIgnoreCase(String stringEntry, String contentToFind) {
		if (contentToFind.equals(""))
			return true;
		if (stringEntry == null || contentToFind == null || stringEntry.equals(""))
			return false;

		Pattern pattern = Pattern.compile(contentToFind, Pattern.CASE_INSENSITIVE + Pattern.LITERAL);
		Matcher matcher = pattern.matcher(stringEntry);
		return matcher.find();
	}

	/**
	 * @param line
	 * @return if the 7 first letters of the line make a disciplineCode (PATTERN: 3
	 *         characthers and 4 integers) return this code
	 */
	public String getDisciplineCode(String line) {
		if (stringBeginsWithDisciplineCode(line))
			return line.substring(0, 7);
		return null;
	}

	/**
	 * @param line
	 * @return if the entered line referes to a discipline gets the result
	 */
	public DisciplineResultEnum getDisciplineResult(String line) {
		if (stringBeginsWithDisciplineCode(line)) {
			for (DisciplineResultEnum result : DisciplineResultEnum.values()) {
				if (containsIgnoreCase(line, result.getText())) {
					return result;
				}
			}
		}
		return null;
	}

	/**
	 * @param line
	 * @return if the line refers to a semester. To do so the line must containt the
	 *         string "semestre de"
	 */
	public boolean lineRefersToSemester(String line) {
		return containsIgnoreCase(line, "semestre de");
	}

	/**
	 * Methods goes throught the line from the end to the begining checking if there
	 * is a sequence of characters same as the result text
	 * 
	 * @param line
	 * @param result
	 * @return if there is a sequence of characters same as the result text
	 */
	public boolean stringHasResult(String line, DisciplineResultEnum result) {
		return (containsIgnoreCase(line, result.getText()));
	}

	/**
	 * @param line
	 * @return if the begining of the line matches the pattern: 3 charachters and 4
	 *         integers
	 */
	public static boolean stringBeginsWithDisciplineCode(String line) {
		if (line.length() >= 7) {
			for (int index = 0; index < 3; index++) {
				char character = line.charAt(index);
				if (!Character.isAlphabetic(character))
					return false;
			}
			for (int index = 3; index < 7; index++) {
				char character = line.charAt(index);
				if (!Character.isDigit(character))
					return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * @param svgPath
	 * @return a list of disciplines based on the svg document. The disciplines have
	 *         null as situation
	 */
	public ArrayList<Discipline> getListOfDisciplinesBasedOnSvg(String svgPath) {
		SvgManipulator svgManipulator = SvgManipulator.getInstance();
		Document gradeCurricularDocument = svgManipulator.getDocumentFromSvgPath(svgPath);
		ArrayList<Discipline> disciplines = new ArrayList<>();
		for (int index = 0; index < gradeCurricularDocument.getElementsByTagName("path").getLength(); index++) {
			Element node = (Element) gradeCurricularDocument.getElementsByTagName("path").item(index);
			for (int nodeIndex = 0; nodeIndex < node.getAttributes().getLength(); nodeIndex++) {
				if (node.getAttributes().item(nodeIndex).getNodeName().equals("id")) {
					String nodeTextContent = node.getAttributes().item(nodeIndex).getTextContent();
					if (stringBeginsWithDisciplineCode(nodeTextContent)) {
						disciplines.add(new Discipline(nodeTextContent, null));
					} else if (stringRefersToOptionalDiscipline(nodeTextContent)) {
						disciplines.add(new Discipline(nodeTextContent, null));
					} else if (stringRefersToElectiveDiscipline(nodeTextContent)) {
						disciplines.add(new Discipline(nodeTextContent, null));
					}
				}
			}
		}
		return disciplines;
	}

	private boolean stringRefersToOptionalDiscipline(String nodeTextContent) {
		return nodeTextContent.contains("OPTATIVA");
	}

	private boolean stringRefersToElectiveDiscipline(String nodeTextContent) {
		return nodeTextContent.contains("ELETIVA");
	}

	public ArrayList<Discipline> getDisciplinesFromDisciplineRecord(ArrayList<Discipline> courseDisciplines,
			String pdfPath) throws IOException {
		ArrayList<Discipline> studentDisciplines = new ArrayList<>();
		Map<String, DisciplineResultEnum> mapDisciplineCodeAndResult = getMapOfDisciplinesAndResult(pdfPath);
		courseDisciplines.forEach(courseDiscipline -> {
			DisciplineResultEnum result = mapDisciplineCodeAndResult.get(courseDiscipline.getCode());
			result = result != null ? result : DisciplineResultEnum.NAO_CURSADA;
			studentDisciplines.add(new Discipline(courseDiscipline.getCode(), result));
			mapDisciplineCodeAndResult.remove(courseDiscipline.getCode());
		});
		HashMap<String, DisciplineResultEnum> hele = new HashMap<>();
		hele.put("TIN0151", mapDisciplineCodeAndResult.get("TIN0151"));
		hele.put("TIN0152", mapDisciplineCodeAndResult.get("TIN0152"));
		hele.put("TIN0153", mapDisciplineCodeAndResult.get("TIN0153"));
		hele.put("TIN0154", mapDisciplineCodeAndResult.get("TIN0154"));
		ArrayList<String> ele = new ArrayList<>();
		hele.keySet().forEach(key -> ele.add(key));
		
		hele.remove("TIN0151");
		hele.remove("TIN0152");
		hele.remove("TIN0153");
		hele.remove("TIN0154");

		studentDisciplines.forEach(d -> {
			if (isEletiva(d)) {
				if (!d.getSituation().equals(DisciplineResultEnum.APROVADO)) {
					if (ele.size() > 0 && hele.size() > 0) {
						String o = ele.get(0);
						System.out.println(hele.get(o));
						d.setSituation(hele.get(o));
						hele.remove(o);
						ele.remove(0);
					}
				}
			}
		});
		
		
		ArrayList<String> opt = new ArrayList<>();
		mapDisciplineCodeAndResult.keySet().forEach(key -> opt.add(key));
		studentDisciplines.forEach(d -> {
			if (isOptativa(d)) {
				if (!d.getSituation().equals(DisciplineResultEnum.APROVADO)) {
					if (opt.size() > 0 && mapDisciplineCodeAndResult.size() > 0) {
						String o = opt.get(0);
						d.setSituation(mapDisciplineCodeAndResult.get(o));
						mapDisciplineCodeAndResult.remove(o);
						opt.remove(0);
					}
				}
			}
		});
		return studentDisciplines;
	}
	
	public boolean isEletiva(Discipline discipline) {
		if (discipline.getCode().contains("ELETIVA"))
			return true;
		return false;
	}

	public boolean isOptativa(Discipline discipline) {
		if (discipline.getCode().contains("OPTATIVA"))
			return true;
		return false;
	}

}
