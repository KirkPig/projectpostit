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

import bill.CreditNote;
import bill.Delivery;
import bill.Invoice;
import bill.Item;
import bill.Order;
import bill.ProductLoan;
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

	private static String addParagraph(PDDocument document, PDPageContentStream cs, String str, float fontSize, float x,
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
		String k = "";
		if (strWidth > pixelWidth) {
			int j = 0;

			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == ' ') {
					if (font.getStringWidth(str.substring(0, i)) / 1000.0f * fontSize <= pixelWidth) {
						j = i;
					} else {
						break;
					}
				}
			}
			list.add(str.substring(0, j));
			k = str.substring(j + 1);

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

		return k;

	}

	public static void addHeader(PDDocument document, PDPageContentStream contentStream, Color base, String formName,
			String id, String date) throws Exception {

		/*
		 * Logo
		 */
		PDImageXObject logoImage = PDImageXObject.createFromFile("./src/res/yono_logo.png", document);
		contentStream.drawImage(logoImage, cpx(10f), cpy(10f) - cpx(26.9f), cpx(46.1f), cpx(26.9f));

		/*
		 * Header
		 */

		contentStream.addRect(cpx(122.2f), cpy(10f) - cpx(15.6f), cpx(87.8f), cpx(15.6f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		addParagraph(document, contentStream, formName, 36f, 122.2f, 10f, 87.8f, 15.6f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "NO." + id + "  DATE: " + date, 18f, 122.2f, 25.6f, 75f, 12.8f,
				HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "YONO TOOLS CO.,LTD.", 28f, 12.8f, 38.4f, 80.8f, 8.7f, HAlignment.LEFT,
				VAlignment.CENTER, FontType.BOLD);

		float addressFontSize = 16.0f;
		addParagraph(document, contentStream, "103/314 M.5 T.Phanthai Norasing, A.Muang Samut Sakhon,", addressFontSize,
				12.8f, 47.1f, 98f, 6f, HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "Samut Sakhon 74000", addressFontSize, 12.8f, 53.1f, 98f, 6f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "TEL: 034-116655  Fax: 034-116656  MOBILE: 099-0568889", addressFontSize,
				12.8f, 59.1f, 98f, 6f, HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "E-MAIL: sale.yonotools@gmail.com", addressFontSize, 12.8f, 65.1f, 98f,
				6f, HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "TAX-ID: 0125560000590", addressFontSize, 12.8f, 71.1f, 98f, 6f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);

	}

	public static void addCustomerInfo(PDDocument document, PDPageContentStream contentStream, Color base,
			Customer customer) throws Exception {

		contentStream.addRect(cpx(128.1f), cpy(38.3f) - cpx(68f), cpx(69.1f), cpx(68f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();

		ArrayList<String> strList = new ArrayList<>();
		strList.add("เลขประจำตัวผู้เสียภาษีอากร");
		strList.add(customer.getTaxID());
		strList.add("");
		strList.add("ข้อมูลลูกค้า");
		strList.add(customer.getName());
		strList.add(customer.getAddress());
		strList.add("Tel: " + customer.getTel());
		strList.add("Fax: " + customer.getFax());

		float rowY = 42.4f;

		while (!strList.isEmpty()) {

			String s = strList.get(0);
			String output = "";
			strList.remove(0);
			if (s != "")
				output = addParagraph(document, contentStream, s, 16f, 134f, rowY, 57.5f, 8.7f, HAlignment.LEFT,
						VAlignment.TOP, FontType.BOLD);

			if (output != "") {
				strList.add(0, output);
			}
			rowY = rowY + 5.0f;

		}

	}

	public static void printOrder(Order form, String dest) throws Exception {

		PDDocument document = new PDDocument();

		PDPage page = new PDPage(NORMAL_PAGE);

		document.addPage(page);

		Color base = Color.LIGHT_GRAY;

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		/*
		 * Header
		 */
		addHeader(document, contentStream, base, "ใบสั่งซื้อ(Order)", form.getId(), form.getDate());

		/*
		 * Side Header
		 */

		/*
		 * Customer Header
		 */
		addCustomerInfo(document, contentStream, base, form.getCustomer());

		/*
		 * List Header
		 */

		/*
		 * List
		 */

		/*
		 * Footer
		 */

		/*
		 * DrawLine
		 */
		// Vertical
		// Horizontal

		contentStream.close();

		document.save(dest);

		document.close();

		System.out.println("PDF Created");

	}

	public static void printDelivery(Delivery form, String dest) throws Exception {

		PDDocument document = new PDDocument();

		PDPage page = new PDPage(NORMAL_PAGE);

		document.addPage(page);

		Color base = new Color(224, 255, 255);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		/*
		 * Header
		 */
		addHeader(document, contentStream, base, "ใบส่งสินค้า(Delivery Note)", form.getId(), form.getDate());

		/*
		 * Side Header
		 */

		/*
		 * Customer Header
		 */
		addCustomerInfo(document, contentStream, base, form.getCustomer());

		/*
		 * List Header
		 */

		/*
		 * List
		 */

		/*
		 * Footer
		 */

		/*
		 * DrawLine
		 */
		// Vertical
		// Horizontal

		contentStream.close();

		document.save(dest);

		document.close();

		System.out.println("PDF Created");

	}

	public static void printProductLoan(ProductLoan form, String dest) throws Exception {

		PDDocument document = new PDDocument();

		PDPage page = new PDPage(NORMAL_PAGE);

		document.addPage(page);

		Color base = new Color(224, 255, 255);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		/*
		 * Header
		 */
		addHeader(document, contentStream, base, "ใบยืมสินค้า(Product Loan)", form.getId(), form.getDate());

		/*
		 * Side Header
		 */

		/*
		 * Customer Header
		 */
		addCustomerInfo(document, contentStream, base, form.getCustomer());

		/*
		 * List Header
		 */

		/*
		 * List
		 */

		/*
		 * Footer
		 */

		/*
		 * DrawLine
		 */
		// Vertical
		// Horizontal

		contentStream.close();

		document.save(dest);

		document.close();

		System.out.println("PDF Created");

	}

	public static void printCreditNote(CreditNote form, String dest) throws Exception {

		PDDocument document = new PDDocument();

		PDPage page = new PDPage(NORMAL_PAGE);

		document.addPage(page);

		Color base = new Color(224, 255, 255);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		/*
		 * Header
		 */
		addHeader(document, contentStream, base, "ใบลดหนี้(Credit Note)", form.getId(), form.getDate());

		/*
		 * Side Header
		 */

		/*
		 * Customer Header
		 */
		addCustomerInfo(document, contentStream, base, form.getCustomer());

		/*
		 * List Header
		 */

		/*
		 * List
		 */

		/*
		 * Footer
		 */

		/*
		 * DrawLine
		 */
		// Vertical
		// Horizontal

		contentStream.close();

		document.save(dest);

		document.close();

		System.out.println("PDF Created");

	}

	@SuppressWarnings("deprecation")
	public static void printQuotation(Quotation form, String dest) throws Exception {

		PDDocument document = new PDDocument();

		PDPage page = new PDPage(NORMAL_PAGE);

		document.addPage(page);

		Color base = new Color(224, 255, 255);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		/*
		 * Header
		 */
		addHeader(document, contentStream, base, "ใบเสนอราคา(Quotation)", form.getId(), form.getDate());

		/*
		 * Side Header
		 */

		contentStream.addRect(cpx(12.7f), cpy(89.2f) - cpx(17.2f), cpx(35f), cpx(17.2f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		contentStream.addRect(cpx(59.3f), cpy(89.2f) - cpx(17.2f), cpx(34.4f), cpx(17.2f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();

		float shFontSize = 20.0f;

		addParagraph(document, contentStream, "ATTN: " + form.getAttn(), shFontSize, 12.7f, 89.2f, 35f, 17.2f,
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
		addParagraph(document, contentStream, strCr, shFontSize, 59.3f, 89.2f, 34.4f, 17.2f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);

		/*
		 * Customer Header
		 */
		addCustomerInfo(document, contentStream, base, form.getCustomer());

		/*
		 * List Header
		 */
		float lhFontSize = 16.0f;

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

		addParagraph(document, contentStream, "NO", lhFontSize, 12.7f, 114.6f, 6.1f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "DESCRIPTION", lhFontSize, 18.7f, 114.6f, 75f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "QUANTITY", lhFontSize, 93.6f, 114.6f, 23.3f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "UNIT", lhFontSize, 116.7f, 114.6f, 11.4f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "PRICE", lhFontSize, 128.1f, 114.6f, 23.1f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "DISCOUNT", lhFontSize, 151.1f, 114.6f, 23.1f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "AMOUNT", lhFontSize, 174.1f, 114.6f, 23f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);

		/*
		 * List
		 */

		float listFontSize = 16.0f;
		ArrayList<Item> itemList = form.getItemList();
		DecimalFormat formatterDouble = new DecimalFormat("#,###.00");
		DecimalFormat formatterInt = new DecimalFormat("#,###");
		for (int i = 0; i < itemList.size(); i++) {

			Item item = itemList.get(i);
			float a = 6f;
			addParagraph(document, contentStream, Integer.toString(i + 1), listFontSize, 12.7f, (123.1f + (i * a)),
					6.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
			addParagraph(document, contentStream, item.getProduct().getDescription(), listFontSize, 19.7f,
					(123.1f + (i * a)), 75f, a, HAlignment.LEFT, VAlignment.CENTER, FontType.REGULAR);
			addParagraph(document, contentStream, formatterInt.format(item.getQuantity()), listFontSize, 93.6f,
					(123.1f + (i * a)), 23.3f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
			addParagraph(document, contentStream, item.getProduct().getUnit(), listFontSize, 116.7f, (123.1f + (i * a)),
					11.4f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
			addParagraph(document, contentStream, formatterDouble.format(item.getProduct().getPrice()), listFontSize,
					128.1f, (123.1f + (i * a)), 23.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
			addParagraph(document, contentStream, String.format("%.0f", item.getDiscount()) + "%", listFontSize, 151.1f,
					(123.1f + (i * a)), 23.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
			addParagraph(document, contentStream, formatterDouble.format(item.getAmount()), listFontSize, 174.1f,
					(123.1f + (i * a)), 22f, a, HAlignment.RIGHT, VAlignment.CENTER, FontType.REGULAR);

		}

		/*
		 * Footer
		 */
		contentStream.addRect(cpx(12.7f), cpy(224.3f) - cpx(8.7f), cpx(184.6f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		contentStream.addRect(cpx(12.7f), cpy(241.2f) - cpx(8.7f), cpx(184.6f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();

		float footerFontSize = 16.0f;

		addParagraph(document, contentStream, new ThaiBaht().getText(form.getValueAfterTax()), footerFontSize, 12.7f, 241.2f,
				103.8f, 8.7f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "  มูลค่าก่อนภาษี", footerFontSize, 116.7f, 224.3f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  ภาษีมูลค่าเพิ่ม", footerFontSize, 116.7f, 233.0f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  รวมสุทธิ", footerFontSize, 116.7f, 241.2f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, formatterDouble.format(form.getValueBeforeTax()), footerFontSize, 151.1f,
				224.3f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueTax()), footerFontSize, 151.1f, 233.0f,
				45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueAfterTax()), footerFontSize, 151.1f,
				241.2f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

		/*
		 * Signature
		 */

		float signatureFontSize = 18.0f;

		addParagraph(document, contentStream, "ลงชื่อ.............................................", signatureFontSize,
				12.8f, 258.1f, 93.1f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "ผู้ขอซื้อ", signatureFontSize, 12.8f, 265.1f, 93.1f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "วันที่....../....../......", signatureFontSize, 12.8f, 272.1f, 93.1f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "ลงชื่อ.............................................", signatureFontSize,
				105.9f, 258.1f, 91.2f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "พนักงานขาย", signatureFontSize, 105.9f, 265.1f, 91.2f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "วันที่....../....../......", signatureFontSize, 105.9f, 272.1f, 91.2f,
				6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		/*
		 * DrawLine
		 */
		// Vertical
		contentStream.drawLine(cpx(12.7f), cpy(114.6f), cpx(12.7f), cpy(249.9f));
		contentStream.drawLine(cpx(18.7f), cpy(114.6f), cpx(18.7f), cpy(224.3f));
		contentStream.drawLine(cpx(93.6f), cpy(114.6f), cpx(93.6f), cpy(224.3f));
		contentStream.drawLine(cpx(116.7f), cpy(114.6f), cpx(116.7f), cpy(249.9f));
		contentStream.drawLine(cpx(128f), cpy(114.6f), cpx(128f), cpy(224.3f));
		contentStream.drawLine(cpx(151.1f), cpy(114.6f), cpx(151.1f), cpy(224.3f));
		contentStream.drawLine(cpx(174.1f), cpy(114.6f), cpx(174.1f), cpy(224.3f));
		contentStream.drawLine(cpx(197.1f), cpy(114.6f), cpx(197.1f), cpy(249.9f));

		// Horizontal
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

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		try {
			String dest = "C:/Users/Kirk Pig/Desktop/PdfTest/sample.pdf";
			ArrayList<Item> itemList = new ArrayList<>();
			itemList.add(new Item(new Product("TRI-1235", "Product Test 1", "Piece", 157.2, 1), 1000, 35));
			itemList.add(new Item(new Product("COM-5623", "Product Test 2", "Set", 63.52, 35), 45, 22));
			Customer customer = new Customer("525", "Pig Co.LTD", "546546-55454515",
					"103/314 M.5 T.Phanthai Norasing, A.Muang Samut Sakhon, Samut Sakhon 74000", "090-841-5626",
					"02-4546455", "yourname@address.com");
			String date = "10/08/2563";

			Order order = new Order("PO63008123", date, customer, itemList, "0");
			Delivery delivery = new Delivery("DE63008123", date, customer, itemList, "Pig");
			ProductLoan productLoan = new ProductLoan("BL63008123", date, customer, itemList, "Pig");
			Quotation quotation = new Quotation("QY63008123", date, customer, itemList, "5545", "0");

			
			// printOrder(order, dest);
			// printQuotation(quotation, dest);
			Desktop.getDesktop().open(new File(dest));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
