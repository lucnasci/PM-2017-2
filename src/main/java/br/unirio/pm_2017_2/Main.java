package br.unirio.pm_2017_2;

public class Main
{
	public static void main(String[] args)
	{
		PdfManager pdfManager = new PdfManager();
		
		String academicRecord = pdfManager.extractTextFromPdf("C:/Users/Inmetrics/Documents/historicoEscolar.pdf");
		
		System.out.println(academicRecord);
	}

}
