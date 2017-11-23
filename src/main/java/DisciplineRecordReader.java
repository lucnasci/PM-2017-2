import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class DisciplineRecordReader {
	private static DisciplineRecordReader reader = null;
	
	private DisciplineRecordReader() {};
	
	/**
	 * @return an instance of DisciplineRecordReader, if it's the first time
	 * it is called than the instance is intantiaded
	 */
	public static DisciplineRecordReader getInstance() {
		if (reader == null)
			reader = new DisciplineRecordReader();
		return reader;
	}
	
	/**
	 * @param path of the pdf file to be readen
	 * @return An array list of strings with the content of the pdf.
	 * The array list is stripped by '\n'
	 */
	public ArrayList<String> readPdf(String path) {
		ArrayList<String> documentLines = new ArrayList<>();
		try (PDDocument document = PDDocument.load(new File("file.pdf"))) {
            document.getClass();
            PDFTextStripper tStripper = new PDFTextStripper();
            String pdfFileInText = tStripper.getText(document);
            for (String line : pdfFileInText.split("\\n")) {
            	documentLines.add(line);
            }
        } catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return documentLines;
	}
	
	/**
	 * @param path of the pdf file to be readen 
	 * @return an array list of strings containg data about the disciplines, in the bellow pattern</br>
	 * DISCIPLINE_CODE DISCIPLINE_NAME DISCIPLINE_RESULT DISCIPLINE_SEMESTER
	 */
	public ArrayList<String> getListOfDisciplines(String path){
		ArrayList<String> documentLines = new ArrayList<>();
		ArrayList<String> lines = readPdf(path);
		int semesterCount = 0;
		for (String line : lines) {
			if (lineRefersToSemester(line))
				semesterCount ++;
			if (stringBeginsWithDisciplineCode(line)) {
				for (DisciplineResultEnum result : DisciplineResultEnum.values()) {
					String resultAsText = result.getText();
					if(stringHasResultWithThreeCharacters(line, resultAsText.charAt(0), resultAsText.charAt(1), resultAsText.charAt(2))) {
						line = line + " periodo" + semesterCount;
						documentLines.add(line);
					}
				}				
			}
		}
		return documentLines;
	}
	
	public static boolean lineRefersToSemester(String line) {
		return line.contains("semestre de");
	}
	
	public static boolean stringHasResultWithThreeCharacters(String line, char charactertOne, char characterTwo, char characterThree) {
		for (int index = line.length() - 1; index >= 0; index = index - 1) {
    		char c = line.charAt(index);
    		if (c == characterThree) {
    			if (line.charAt(index - 1) == characterTwo) {
    				if (line.charAt(index - 2) == charactertOne) {
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }
    
    public static boolean stringBeginsWithDisciplineCode(String line) {
    	if (line.length() >= 7) {
	    	for (int index = 0; index < 3; index ++) {
	    		char character = line.charAt(index);
	    		if (!Character.isAlphabetic(character))
	    			return false;
	    	}
	    	for (int index = 3; index < 7; index ++) {
	    		char character = line.charAt(index);
	    		if (!Character.isDigit(character))
	    			return false;
	    	}
	    	return true;
    	}
    	return false;
    }

}
