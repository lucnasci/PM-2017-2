package br.unirio.pm_2017_2;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.IOException;

public class PdfManager
{	
    public String extractTextFromPdf(String filePath)
    {
    	PdfReader pdfReader;
    	
        try
        {
            pdfReader = new PdfReader(filePath);
            
            String textFromPage = PdfTextExtractor.getTextFromPage(pdfReader, 1);

            return textFromPage;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
		return null;
    }
}
