import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfReader {
	private static PdfReader reader = null;
	
	public static PdfReader getInstance() {
		if (reader == null)
			reader = new PdfReader();
		return reader;
	}
	
	/**
	 * @param path of the pdf file to be readen
	 * @return An array list of strings with the content of the pdf.
	 * The array list is stripped by '\n'
	 */
	public ArrayList<String> readPdf(String path) {
		ArrayList<String> documentLines = new ArrayList<>();
		try (PDDocument document = PDDocument.load(new File("src/resources/file.pdf"))) {
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
}
