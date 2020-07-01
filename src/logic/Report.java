package logic;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import bill.Item;
import bill.Quotation;

public class Report {

	private static final float DPI = 96.0f;
	private static final float MMPI = 25.4f;
	private static final PDRectangle NORMAL_PAGE = new PDRectangle(794, 1123);

	private static float cpx(float x) {
		return (x / MMPI * DPI);
	}

	private static float cpy(float y) {
		return 1123.0f - (y / MMPI * DPI);
	}

	private enum HAlignment {
		LEFT, CENTER, RIGHT
	}

	private enum VAlignment {
		TOP, CENTER, BOTTOM
	}

	private enum FontType {
		REGULAR, BOLD, ITALIC, BOLD_ITALIC
	}

	private static void addParagraph(PDDocument document, PDPageContentStream cs, String str, float fontSize, float x,
			float y, float width, float height, HAlignment hAlignment, VAlignment vAlignment, FontType fontType)
			throws Exception {

		cs.beginText();

		PDFont font;
		switch (fontType) {
		case BOLD:
			font = PDType0Font.load(document, new File("./src/font/THSarabunNew Bold.ttf"));
			break;
		case BOLD_ITALIC:
			font = PDType0Font.load(document, new File("./src/font/THSarabunNew BoldItalic.ttf"));
			break;
		case ITALIC:
			font = PDType0Font.load(document, new File("./src/font/THSarabunNew Italic.ttf"));
			break;
		case REGULAR:
			font = PDType0Font.load(document, new File("./src/font/THSarabunNew.ttf"));
			break;
		default:
			font = PDType0Font.load(document, new File("./src/font/THSarabunNew.ttf"));
			break;
		}
		cs.setFont(font, fontSize);
		cs.setNonStrokingColor(Color.BLACK);

		float strWidth = font.getStringWidth(str) / 1000.0f * fontSize;
		float strHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 2400.0f * fontSize;
		float pivotX = cpx(x);
		float pivotY = cpy(y) - strHeight;
		float pixelWidth = cpx(width);
		float pixelHeight = cpx(height);

		float pointX = 0.0f, pointY = 0.0f;

		// Set String List
		ArrayList<String> list = new ArrayList<>();
		if (strWidth > pixelWidth) {

		} else {
			list.add(str);
		}

		switch (hAlignment) {
		case CENTER:
			pointX = pivotX + ((pixelWidth - strWidth) / 2.0f);
			break;
		case LEFT:
			pointX = pivotX;
			break;
		case RIGHT:
			pointX = pivotX + ((pixelWidth - strWidth));
			break;
		default:
			pointX = pivotX;
			break;
		}

		switch (vAlignment) {
		case BOTTOM:
			pointY = pivotY - (pixelHeight) + (strHeight);
			break;
		case CENTER:
			pointY = pivotY - (pixelHeight / 2.0f) + (strHeight / 2.0f);
			break;
		case TOP:
			pointY = pivotY;
			break;
		default:
			pointY = pivotY;
			break;

		}

		cs.newLineAtOffset(pointX, pointY);

		for (String s : list) {
			cs.showText(s);
		}

		cs.endText();

	}

	@SuppressWarnings("deprecation")
	public static void printQuotation(Quotation form, String dest) throws Exception {

		PDDocument document = new PDDocument();

		PDPage page = new PDPage(NORMAL_PAGE);

		document.addPage(page);
		
		Color base = new Color(224, 255, 255);
		PDImageXObject logoImage = PDImageXObject.createFromFile("./src/res/yono_logo.png", document);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		
		// Logo
		contentStream.drawImage(logoImage, cpx(10f), cpy(10f) - cpx(26.9f), cpx(35f), cpx(18f));

		// Title
		contentStream.addRect(cpx(122.2f), cpy(10f) - cpx(15.6f), cpx(87.8f), cpx(15.6f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();

		// ATTN
		contentStream.addRect(cpx(12.7f), cpy(89.2f) - cpx(17.2f), cpx(35f), cpx(17.2f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();

		// CR
		contentStream.addRect(cpx(59.3f), cpy(89.2f) - cpx(17.2f), cpx(34.4f), cpx(17.2f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();

		// Customer Header
		contentStream.addRect(cpx(128.1f), cpy(38.3f) - cpx(68f), cpx(69.1f), cpx(68f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		
		//List Header
		contentStream.addRect(cpx(12.7f), cpy(114.6f) - cpx(8.7f), cpx(6.1f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		contentStream.addRect(cpx(18.7f), cpy(114.6f) - cpx(8.7f), cpx(75f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		contentStream.addRect(cpx(93.6f), cpy(114.6f) - cpx(8.7f), cpx(23.3f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		contentStream.addRect(cpx(116.7f), cpy(114.6f) - cpx(8.7f), cpx(11.4f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		contentStream.addRect(cpx(128.1f), cpy(114.6f) - cpx(8.7f), cpx(23.1f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		contentStream.addRect(cpx(151.1f), cpy(114.6f) - cpx(8.7f), cpx(23.1f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		contentStream.addRect(cpx(174.1f), cpy(114.6f) - cpx(8.7f), cpx(23f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		
		// footer
		contentStream.addRect(cpx(12.7f), cpy(224.3f) - cpx(8.7f), cpx(184.6f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		contentStream.addRect(cpx(12.7f), cpy(241.2f) - cpx(8.7f), cpx(184.6f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();

		/*
		 * Header
		 */

		addParagraph(document, contentStream, "ใบเสนอราคา (Quotation)", 28f, 122.2f, 10f, 87.8f, 15.6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "NO." + form.getId() + "  DATE: " + form.getDate(), 14f, 122.2f, 25.6f,
				75f, 12.8f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "YONO TOOLS CO.,LTD.", 24f, 12.8f, 38.4f, 80.8f, 8.7f, HAlignment.LEFT,
				VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "103/314 M.5 T.Phanthai Norasing, A.Muang Samut Sakhon,", 13f, 12.8f,
				47.1f, 98f, 6f, HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "Samut Sakhon 74000", 13f, 12.8f, 50.1f, 98f, 6f, HAlignment.LEFT,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "TEL: 034-116655  Fax: 034-116656  MOBILE: 099-0568889", 13f, 12.8f,
				53.1f, 98f, 6f, HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "E-MAIL: sale.yonotools@gmail.com", 13f, 12.8f, 56.1f, 98f, 6f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "TAX-ID: 0125560000590", 13f, 12.8f, 59.1f, 98f, 6f, HAlignment.LEFT,
				VAlignment.CENTER, FontType.BOLD);

		/*
		 * Side Header
		 */

		addParagraph(document, contentStream, "ATTN: " + form.getAttn(), 14f, 12.7f, 89.2f, 35f, 17.2f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		String strCr = "CR: ";
		switch (Integer.parseInt(form.getCr())) {
		case 0:
			strCr += "CASH";
			break;
		case 1:
			strCr += (Integer.toString(Integer.parseInt(form.getCr())) + " DAY");
			break;
		default:
			strCr += (Integer.toString(Integer.parseInt(form.getCr())) + " DAYS");
			break;
		}
		addParagraph(document, contentStream, strCr, 14f, 59.3f, 89.2f, 34.4f, 17.2f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);

		/*
		 * Customer Header
		 */
		Customer customer = form.getCustomer();
		
		/*
		 * Rectangle r4 = new Rectangle((int)(134 / mmpi * dpi), (int)((42.4) / mmpi * dpi), (int)(57.5 / mmpi * dpi), (int)(17.5 / mmpi * dpi));
            e.Graphics.DrawString("เลขประจำตัวผู้เสียภาษีอากร\n" + CustomerTaxID, new Font(font, 12, FontStyle.Bold), Brushes.Black, r4, strformat);
            Rectangle r5 = new Rectangle((int)(134 / mmpi * dpi), (int)((59.7) / mmpi * dpi), (int)(63.3 / mmpi * dpi), (int)(46.7 / mmpi * dpi));
            e.Graphics.DrawString("ข้อมูลลูกค้า\n" + CustomerName + "\n" + CustomerAddress + "\nTel : " + CustomerTel + "\nFax : " + fax,
                new Font(font, 12, FontStyle.Bold), Brushes.Black, r5, strformat);
		 */
		
		ArrayList<String> strList = new ArrayList<>();
		strList.add("เลขประจำตัวผู้เสียภาษีอากร");
		strList.add(customer.getTaxID());
		strList.add("");
		strList.add("ข้อมูลลูกค้า");
		strList.add(customer.getName());
		strList.add(customer.getAddress());
		strList.add("Tel: " + customer.getTel());
		strList.add("Fax: " + customer.getFax());
		
		for(int i = 0;i<strList.size();i++) {
			
			String s = strList.get(i);
			if(s == "")
				continue;
			addParagraph(document, contentStream, s, 12f, 134f, 42.4f + (i * 5f), 100f, 8.7f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
			
		}

		/*
		 * List Header
		 */

		addParagraph(document, contentStream, "NO", 12f, 12.7f, 114.6f, 6.1f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "DESCRIPTION", 12f, 18.7f, 114.6f, 75f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "QUANTITY", 12f, 93.6f, 114.6f, 23.3f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "UNIT", 12f, 116.7f, 114.6f, 11.4f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "PRICE", 12f, 128.1f, 114.6f, 23.1f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "DISCOUNT", 12f, 151.1f, 114.6f, 23.1f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "AMOUNT", 12f, 174.1f, 114.6f, 23f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);

		/*
		 * List
		 */
		ArrayList<Item> itemList = form.getItemList();
		DecimalFormat formatterDouble = new DecimalFormat("#,###.00");
		DecimalFormat formatterInt = new DecimalFormat("#,###");
		for (int i = 0; i < itemList.size(); i++) {

			Item item = itemList.get(i);
			float a = 6f;
			addParagraph(document, contentStream, Integer.toString(i + 1), 12f, 12.7f, (123.1f + (i * a)), 6.1f, a,
					HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
			addParagraph(document, contentStream, item.getProduct().getDescription(), 12f, 19.7f, (123.1f + (i * a)),
					75f, a, HAlignment.LEFT, VAlignment.CENTER, FontType.REGULAR);
			addParagraph(document, contentStream, formatterInt.format(item.getQuantity()), 12f, 93.6f,
					(123.1f + (i * a)), 23.3f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
			addParagraph(document, contentStream, item.getProduct().getUnit(), 12f, 116.7f, (123.1f + (i * a)), 11.4f,
					a, HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
			addParagraph(document, contentStream, formatterDouble.format(item.getProduct().getPrice()), 12f, 128.1f,
					(123.1f + (i * a)), 23.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
			addParagraph(document, contentStream, String.format("%.2f", item.getDiscount()) + "%", 12f, 151.1f,
					(123.1f + (i * a)), 23.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
			addParagraph(document, contentStream, formatterDouble.format(item.getAmount()), 12f, 174.1f,
					(123.1f + (i * a)), 21f, a, HAlignment.RIGHT, VAlignment.CENTER, FontType.REGULAR);

		}

		/*
		 * Footer
		 */

		addParagraph(document, contentStream, new ThaiBaht().getText(form.getPlusTax7()), 14f, 12.7f, 241.2f, 103.8f,
				8.7f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "  มูลค่าก่อนภาษี", 12f, 116.7f, 224.3f, 34.4f, 8.7f, HAlignment.LEFT,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  ภาษีมูลค่าเพิ่ม", 12f, 116.7f, 233.0f, 34.4f, 8.7f, HAlignment.LEFT,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  รวมสุทธิ", 12f, 116.7f, 241.2f, 34.4f, 8.7f, HAlignment.LEFT,
				VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, formatterDouble.format(form.getTotalAmount()), 12f, 151.1f, 224.3f, 44.1f,
				8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getTax7()), 12f, 151.1f, 233.0f, 44.1f, 8.7f,
				HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getPlusTax7()), 12f, 151.1f, 241.2f, 44.1f,
				8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

		/*
		 * Signature
		 */

		addParagraph(document, contentStream, "ลงชื่อ.............................................", 14f, 12.8f, 258.1f,
				93.1f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "ผู้ขอซื้อ", 14f, 12.8f, 263.1f, 93.1f, 6f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "วันที่....../....../......", 14f, 12.8f, 268.1f, 93.1f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "ลงชื่อ.............................................", 14f, 105.9f,
				258.1f, 91.2f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "พนักงานขาย", 14f, 105.9f, 263.1f, 91.2f, 6f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "วันที่....../....../......", 14f, 105.9f, 268.1f, 91.2f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		
		/*
		 * DrawLine
		 */
		//Vertical
		contentStream.drawLine(cpx(12.7f), cpy(114.6f), cpx(12.7f), cpy(249.9f));
		contentStream.drawLine(cpx(18.7f), cpy(114.6f), cpx(18.7f), cpy(224.3f));
		contentStream.drawLine(cpx(93.6f), cpy(114.6f), cpx(93.6f), cpy(224.3f));
		contentStream.drawLine(cpx(116.7f), cpy(114.6f), cpx(116.7f), cpy(249.9f));
		contentStream.drawLine(cpx(128f), cpy(114.6f), cpx(128f), cpy(224.3f));
		contentStream.drawLine(cpx(151.1f), cpy(114.6f), cpx(151.1f), cpy(224.3f));
		contentStream.drawLine(cpx(174.1f), cpy(114.6f), cpx(174.1f), cpy(224.3f));
		contentStream.drawLine(cpx(197.1f), cpy(114.6f), cpx(197.1f), cpy(249.9f));
		
		//Horizontal
		contentStream.drawLine(cpx(12.7f), cpy(114.6f), cpx(197.1f), cpy(114.6f));
		contentStream.drawLine(cpx(12.7f), cpy(123.1f), cpx(197.1f), cpy(123.1f));
		contentStream.drawLine(cpx(12.7f), cpy(224.3f), cpx(197.1f), cpy(224.3f));
		contentStream.drawLine(cpx(116.7f), cpy(233f), cpx(197.1f), cpy(233f));
		contentStream.drawLine(cpx(116.7f), cpy(241.2f), cpx(197.1f), cpy(241.2f));
		contentStream.drawLine(cpx(12.7f), cpy(249.9f), cpx(197.1f), cpy(249.9f));
		
		
		contentStream.close();

		document.save(dest);

		document.close();

		System.out.println("PDF Created");

	}

	public static void main(String[] args) {

		try {
			String dest = "C:/Users/Kirk Pig/Desktop/PdfTest/sample.pdf";
			ArrayList<Item> a = new ArrayList<>();
			a.add(new Item(new Product("TRI-1235", "Product Test 1", "Piece", 157.2), 1000, 35));
			a.add(new Item(new Product("COM-5623", "Product Test 2", "Set", 63.52), 45, 22));
			Quotation test = new Quotation("QY63008123", "10/10/2025",
					new Customer("525", "Pig Co.LTD", "546546-55454515",
							"103/314 M.5 T.Phanthai Norasing, A.Muang Samut Sakhon, Samut Sakhon 74000", "090-841-5626",
							"02-4546455", "yourname@address.com"),
					a, "5545", "0");
			printQuotation(test, dest);
			Desktop.getDesktop().open(new File(dest));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
