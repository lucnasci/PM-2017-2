import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.IOException;
import java.util.ArrayList;

public class PdfManipulator {
	private static PdfManipulator reader = null;

	public static PdfManipulator getInstance() {
		if (reader == null)
			reader = new PdfManipulator();
		return reader;
	}

	public ArrayList<String> extractTextFromPdf(String filePath) throws IOException {
		PdfReader reader = new PdfReader(filePath);
		ArrayList<String> documentLines = new ArrayList<>();

		for (int i = 1; i < (reader.getNumberOfPages() + 1); i++) {
			String[] pdf = PdfTextExtractor.getTextFromPage(reader, i).split("\n", -1);

			for (int j = 0; j < pdf.length; j++) {
				documentLines.add(pdf[j]);
			}
		}
		reader.close();

		return documentLines;
	}
}
