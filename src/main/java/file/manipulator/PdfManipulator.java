package file.manipulator;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.IOException;

import java.util.ArrayList;

public class PdfManipulator {
	private static PdfManipulator pdfManipulator = null;

	/**
	 * @return an instance of PdfManipulator, if it's the first time it is
	 *         called than the instance is instantiated.
	 */
	public static PdfManipulator getInstance() {
		if (pdfManipulator == null)
			pdfManipulator = new PdfManipulator();
		return pdfManipulator;
	}

	/**
	 * @param pdfFilePath
	 *            Path of the PDF file to be read and extract text.
	 * @return ArrayList containing the text lines, in String format, of the
	 *         read PDF file.
	 * @throws IOException
	 */
	public ArrayList<String> extractTextFromPdf(String pdfFilePath) throws IOException {
		PdfReader pdfReader = new PdfReader(pdfFilePath);
		ArrayList<String> documentLines = new ArrayList<>();

		for (int i = 1; i < (pdfReader.getNumberOfPages() + 1); i++) {
			String[] pdf = PdfTextExtractor.getTextFromPage(pdfReader, i).split("\n", -1);

			for (int j = 0; j < pdf.length; j++) {
				documentLines.add(pdf[j]);
			}
		}
		pdfReader.close();

		return documentLines;
	}
}
