package logic;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

public class Report {

	public static void printQuotation() throws Exception{

		// Creating a PdfWriter
		String dest = "C:/Users/Kirk Pig/Desktop/PdfTest/sample.pdf";
		PdfWriter writer = new PdfWriter(dest);

		// Creating a PdfDocument
		PdfDocument pdfDoc = new PdfDocument(writer);

		// Adding a new page
		pdfDoc.addNewPage();

		// Creating a Document
		Document document = new Document(pdfDoc);

		// Closing the document
		document.close();
		System.out.println("PDF Created");

	}

	public static void main(String[] args) {

		try {
			printQuotation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
