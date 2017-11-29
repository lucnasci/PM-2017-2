package file.manipulator;
import org.w3c.dom.Document;

import enums.DisciplineResultColorEnum;
import enums.DisciplineResultEnum;
import model.Discipline;

public class SvgManipulator {

	private static SvgReader reader;
	private static SvgWriter writer;
	private static SvgManipulator manipulator = null;

	public static SvgManipulator getInstance() {
		if (reader == null)
			reader = SvgReader.getInstance();
		if (writer == null)
			writer = SvgWriter.getInstance();
		if (manipulator == null)
			manipulator = new SvgManipulator();
		return manipulator;
	}

	/**
	 * @param path
	 * @return a document from the given path to a svg file
	 */
	public Document getDocumentFromSvgPath(String path) {
		return reader.getDocFromSvgPath(path);
	}

	/**
	 * @param documentToEdit
	 * @param discipline
	 * Paint the discipline code according to the discipline result
	 */
	public void paintDiscipine(Document documentToEdit, Discipline discipline) {
		String color;
		DisciplineResultEnum disciplineSituation = discipline.getSituation();
		if (disciplineSituation != null) {
			switch (disciplineSituation) {
			case APROVADO:
			case DISPENSADO_COM_NOTA:
			case DISPENSADO_SEM_NOTA:
				color = DisciplineResultColorEnum.APROVADO.getText();
				break;
			case REPROVADO:
				color = DisciplineResultColorEnum.REPROVADO.getText();
				break;	
			case MATRICULA:
				color = DisciplineResultColorEnum.MATRICULA.getText();
				break;
			default:
				color = DisciplineResultColorEnum.NAO_CURSADA.getText();
				break;
			}
		}else{
			color = DisciplineResultColorEnum.NAO_CURSADA.getText();
		}
		editSvgPathColor(documentToEdit, discipline.getCode(), color);
	}

	/**
	 * @param documentToEdit
	 * @param pathId
	 * @param color
	 * Edit the color of the node according to the given color and pathId
	 */
	public void editSvgPathColor(Document documentToEdit, String pathId, String color) {
		documentToEdit.getElementById(pathId).setAttribute("style", "fill:" + color
				+ ";fill-rule:evenodd;stroke:#000000;stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;stroke-opacity:1");
	}

	/**
	 * @param docToWrite
	 * Receives a document and save in the path "src/resources/test.svg"
	 */
	public void writeSvgDoc(Document docToWrite) {
		writer.writeSvgDoc(docToWrite);
	}
}
