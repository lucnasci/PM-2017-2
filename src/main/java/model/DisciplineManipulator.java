package model;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import enums.DisciplineResultEnum;

import file.manipulator.PdfManipulator;
import file.manipulator.SvgManipulator;

public class DisciplineManipulator {
	private static DisciplineManipulator disciplineManipulator = null;

	private DisciplineManipulator() {
	};

	/**
	 * @return an instance of DisciplineManipulator, if it's the first time it is
	 *         called than the instance is instantiated.
	 */
	public static DisciplineManipulator getInstance() {
		if (disciplineManipulator == null)
			disciplineManipulator = new DisciplineManipulator();
		return disciplineManipulator;
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
			if (lineRefersToSemester(line)) {
				semesterCount++;
			}
			if (stringBeginsWithDisciplineCode(line)) {
				line = semesterCount + "� Periodo - " + line;
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
	public Map<String, DisciplineResultEnum> getMapOfDisciplinesAndResult(String disciplineRecordPath)
			throws IOException {
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
	 * @param enteredLine
	 * @return if the 7 first letters of the line make a disciplineCode (PATTERN: 3
	 *         characthers and 4 integers) return this code
	 */
	public String getDisciplineCode(String enteredLine) {
		if (stringBeginsWithDisciplineCode(enteredLine))
			return enteredLine.substring(0, 7);
		return null;
	}

	/**
	 * @param enteredLine
	 * @return if the entered line referes to a discipline gets the result
	 */
	public DisciplineResultEnum getDisciplineResult(String enteredLine) {
		if (stringBeginsWithDisciplineCode(enteredLine)) {
			for (DisciplineResultEnum result : DisciplineResultEnum.values()) {
				if (containsIgnoreCase(enteredLine, result.getText())) {
					return result;
				}
			}
		}
		return null;
	}

	/**
	 * @param enteredLine
	 * @return if the line refers to a semester. To do so the line must containt the
	 *         string "semestre de"
	 */
	public boolean lineRefersToSemester(String enteredLine) {
		return containsIgnoreCase(enteredLine, "semestre de");
	}

	/**
	 * Methods goes throught the line from the end to the begining checking if there
	 * is a sequence of characters same as the result text
	 * 
	 * @param enteredLine
	 * @param result
	 * @return if there is a sequence of characters same as the result text
	 */
	public boolean stringHasResult(String enteredLine, DisciplineResultEnum result) {
		return (containsIgnoreCase(enteredLine, result.getText()));
	}

	/**
	 * @param enteredLine
	 *            String where the pattern of discipline code will be searched.
	 * @return true if the beginning of String matches the pattern (3 characters and
	 *         4 digits), otherwise, returns false.
	 */
	public static boolean stringBeginsWithDisciplineCode(String enteredLine) {
		if (enteredLine.length() >= 7) {
			for (int index = 0; index < 3; index++) {
				char character = enteredLine.charAt(index);
				if (!Character.isAlphabetic(character))
					return false;
			}
			for (int index = 3; index < 7; index++) {
				char character = enteredLine.charAt(index);
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
		NodeList gradeCurricularDocumentElements = gradeCurricularDocument.getElementsByTagName("path");
		ArrayList<Discipline> disciplines = new ArrayList<>();
		for (int index = 0; index < gradeCurricularDocumentElements.getLength(); index++) {
			Element node = (Element) gradeCurricularDocumentElements.item(index);
			for (int nodeIndex = 0; nodeIndex < node.getAttributes().getLength(); nodeIndex++) {
				Node loopNode = node.getAttributes().item(nodeIndex);
				Discipline nodeDiscipline = createDisciplineBasedOnNode(loopNode);
				if (nodeDiscipline != null) {
					disciplines.add(nodeDiscipline);
				}
			}
		}
		return disciplines;
	}

	/**
	 * @param node
	 * @return a discipline with the nodeName, if the node has a name and it begins
	 *         with a discipline code.If not returns null
	 */
	private Discipline createDisciplineBasedOnNode(Node node) {
		if (node.getNodeName().equalsIgnoreCase("id")) {
			String nodeTextContent = node.getTextContent();
			if (stringBeginsWithDisciplineCode(nodeTextContent)) {
				return new Discipline(nodeTextContent);
			} else if (stringRefersToOptionalDiscipline(nodeTextContent)) {
				return new Discipline(nodeTextContent);
			} else if (stringRefersToElectiveDiscipline(nodeTextContent)) {
				return new Discipline(nodeTextContent);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param nodeTextContent
	 * @return if the nodeTextContentRefersToADiscipline 'optativa'
	 */
	private boolean stringRefersToOptionalDiscipline(String nodeTextContent) {
		return nodeTextContent.contains("OPTATIVA");
	}

	/**
	 * 
	 * @param nodeTextContent
	 * @return if the nodeTextContentRefersToADiscipline 'eletiva'
	 */
	private boolean stringRefersToElectiveDiscipline(String nodeTextContent) {
		return nodeTextContent.contains("ELETIVA");
	}

	public ArrayList<Discipline> getDisciplinesFromDisciplineRecord(ArrayList<Discipline> courseDisciplines,
			String pdfPath) throws IOException {

		Map<String, DisciplineResultEnum> mapDisciplineCodeAndResult = getMapOfDisciplinesAndResult(pdfPath);
		ArrayList<Discipline> studentDisciplines = getStudentDisciplinesWithResults(courseDisciplines,
				mapDisciplineCodeAndResult);
		setEletivaDisciplinesResult(studentDisciplines, mapDisciplineCodeAndResult);
		setOptativaDisciplinesResult(studentDisciplines, mapDisciplineCodeAndResult);
		return studentDisciplines;
	}

	/**
	 * If the entered discipline list has eletiva then the results are setted
	 * 
	 * @param disciplines
	 * @param pdfPath
	 */
	public void setEletivaDisciplinesResult(ArrayList<Discipline> disciplines,
			Map<String, DisciplineResultEnum> mapDisciplineCodeAndResult) {
		HashMap<String, DisciplineResultEnum> mapEletiva = new HashMap<>();
		mapEletiva.put("TIN0151", mapDisciplineCodeAndResult.get("TIN0151"));
		mapEletiva.put("TIN0152", mapDisciplineCodeAndResult.get("TIN0152"));
		mapEletiva.put("TIN0153", mapDisciplineCodeAndResult.get("TIN0153"));
		mapEletiva.put("TIN0154", mapDisciplineCodeAndResult.get("TIN0154"));
		ArrayList<String> eletivaList = new ArrayList<>();
		mapEletiva.keySet().forEach(key -> eletivaList.add(key));

		disciplines.forEach(studentDiscipline -> {
			if (isEletiva(studentDiscipline)) {
				if (!studentDiscipline.getSituation().equals(DisciplineResultEnum.APROVADO)) {
					if (eletivaList.size() > 0 && mapEletiva.size() > 0) {
						String o = eletivaList.get(0);
						studentDiscipline.setSituation(mapEletiva.get(o));
						mapEletiva.remove(o);
						eletivaList.remove(0);
					}
				}
			}
		});
	}

	/**
	 * If the entered discipline list has optativa then the results are setted
	 * @param disciplines
	 * @param mapDisciplineCodeAndResult
	 */
	public void setOptativaDisciplinesResult(ArrayList<Discipline> disciplines,
			Map<String, DisciplineResultEnum> mapDisciplineCodeAndResult) {
		ArrayList<String> opt = new ArrayList<>();
		mapDisciplineCodeAndResult.keySet().forEach(key -> opt.add(key));
		disciplines.forEach(d -> {
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
	}

	/**
	 * @param discipline
	 * @return if the discipline code contains text refereing to elective
	 */
	public boolean isEletiva(Discipline discipline) {
		if (stringRefersToElectiveDiscipline(discipline.getCode()))
			return true;
		return false;
	}

	/**
	 * @param discipline
	 * @return if the discipline code contains text refereing to optional
	 */
	public boolean isOptativa(Discipline discipline) {
		if (stringRefersToOptionalDiscipline(discipline.getCode()))
			return true;
		return false;
	}

	/**
	 * Receives a list of the course's disciplines, a map with the discipline code
	 * and result and returns a list with instanced disciplines
	 * 
	 * @param courseDisciplines
	 * @param mapDisciplineCodeAndResult
	 * @return a list of instanced disciplines
	 */
	public ArrayList<Discipline> getStudentDisciplinesWithResults(ArrayList<Discipline> courseDisciplines,
			Map<String, DisciplineResultEnum> mapDisciplineCodeAndResult) {
		ArrayList<Discipline> studentDisciplines = new ArrayList<>();
		courseDisciplines.forEach(courseDiscipline -> {
			DisciplineResultEnum result = mapDisciplineCodeAndResult.get(courseDiscipline.getCode());
			result = result != null ? result : DisciplineResultEnum.NAO_CURSADA;
			studentDisciplines.add(new Discipline(courseDiscipline.getCode(), result));
			mapDisciplineCodeAndResult.remove(courseDiscipline.getCode());
		});
		return studentDisciplines;
	}
}
