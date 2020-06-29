package logic;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import bill.Item;
import bill.Quotation;

public class Report {

	private static final float DPI = 96.0f;
	private static final float MMPI = 25.4f;
	private static final PageSize NORMAL_PAGE = new PageSize(new Rectangle(794, 1123));

	private static float cpx(float x) {
		return (x / MMPI * DPI);
	}

	private static float cpy(float y) {
		return 1123.0f - (y / MMPI * DPI);
	}

	public static void printQuotation(Quotation quotation) throws Exception {

		// Creating a PdfWriter
		String dest = "C:/Users/Kirk Pig/Desktop/PdfTest/sample.pdf";
		PdfWriter writer = new PdfWriter(dest);

		// Creating a PdfDocument
		PdfDocument pdfDoc = new PdfDocument(writer);

		// Adding a new page
		pdfDoc.addNewPage(NORMAL_PAGE);

		// Creating a Document
		Document document = new Document(pdfDoc);

		// Paragraph
		/*
		 * Header
		 */
		Paragraph title = new Paragraph("Quotation");
		title.setTextAlignment(TextAlignment.CENTER);
		title.setVerticalAlignment(VerticalAlignment.MIDDLE);
		title.setFontSize(28);
		title.setFixedPosition(cpx(122.2f), cpy(10f + 15.6f), cpx(87.8f));
		title.setHeight(cpx(15.6f));

		Paragraph codeDate = new Paragraph("NO." + quotation.getId() + " Date: " + quotation.getDate());
		codeDate.setTextAlignment(TextAlignment.RIGHT);
		codeDate.setVerticalAlignment(VerticalAlignment.MIDDLE);
		codeDate.setFontSize(14);
		codeDate.setFixedPosition(cpx(122.2f), cpy(25.6f + 12.8f), cpx(75f));
		codeDate.setHeight(cpx(12.8f));

		Paragraph corpName = new Paragraph("YONO TOOLS CO.,LTD.");
		corpName.setTextAlignment(TextAlignment.LEFT);
		corpName.setVerticalAlignment(VerticalAlignment.MIDDLE);
		corpName.setFontSize(24);
		corpName.setFixedPosition(cpx(12.8f), cpy(38.4f + 8.7f), cpx(80.8f));
		corpName.setHeight(cpx(8.7f));

		Paragraph corpAddress = new Paragraph(
				"103/314 M.5 T.Phanthai Norasing, A.Muang Samut Sakhon,\nSamut Sakhon 74000"
						+ "\nTEL: 034-116655  Fax: 034-116656  MOBILE: 099-0568889\nE-MAIL: sale.yonotools@gmail.com\n"
						+ "TAX-ID: 0125560000590");
		corpAddress.setTextAlignment(TextAlignment.LEFT);
		corpAddress.setVerticalAlignment(VerticalAlignment.MIDDLE);
		corpAddress.setFontSize(12);
		corpAddress.setFixedPosition(cpx(12.8f), cpy(47.1f + 33.8f), cpx(89.8f));
		corpAddress.setHeight(cpx(33.8f));

		/*
		 * Side Header
		 */
		Paragraph attn = new Paragraph("ATTN: " + quotation.getAttn());
		attn.setTextAlignment(TextAlignment.CENTER);
		attn.setVerticalAlignment(VerticalAlignment.MIDDLE);
		attn.setFontSize(14);
		attn.setFixedPosition(cpx(12.7f), cpy(89.2f + 17.2f), cpx(35f));
		attn.setHeight(cpx(17.2f));

		Paragraph cr;
		if (Integer.parseInt(quotation.getCr()) == 0) {
			cr = new Paragraph("CR: CASH");
		} else if (Integer.parseInt(quotation.getCr()) == 1) {
			cr = new Paragraph("CR: 1 DAY");
		} else {
			cr = new Paragraph("CR: " + Integer.parseInt(quotation.getCr()) + " DAYS");
		}
		cr.setTextAlignment(TextAlignment.CENTER);
		cr.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cr.setFontSize(14);
		cr.setFixedPosition(cpx(59.3f), cpy(89.2f + 17.2f), cpx(34.4f));
		cr.setHeight(cpx(17.2f));
		
		/*
		 * Customer Header
		 */
		

		// Rectangle Box

		// Add Rectangle Box

		// Add Paragraph
		document.add(title);
		document.add(codeDate);
		document.add(corpName);
		document.add(corpAddress);
		document.add(attn);
		document.add(cr);

		// Closing the document
		document.close();
		System.out.println("PDF Created");

	}

	public static void main(String[] args) {

		try {
			ArrayList<Item> a = new ArrayList<>();
			a.add(new Item(new Product("4654", "546545", "654", 123.52), 45, 87465));
			Quotation test = new Quotation("100", "10-10-2025",
					new Customer("525", "45465465", "546546", "656565", "545645", "564564", "5465165"), a, "5545",
					"4562");
			printQuotation(test);
			Desktop.getDesktop().open(new File("C:/Users/Kirk Pig/Desktop/PdfTest/sample.pdf"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
