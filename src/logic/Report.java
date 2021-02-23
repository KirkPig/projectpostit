package logic;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import bill.Billing;
import bill.CreditNote;
import bill.Delivery;
import bill.Invoice;
import bill.Item;
import bill.Order;
import bill.ProductLoan;
import bill.Quotation;
import database.User;

public class Report {

	private static final float DPI = 72.0f;
	private static final float MMPI = 25.4f;
	private static final PDRectangle NORMAL_PAGE = PDRectangle.A4;

	private static float cpx(float x) {
		return (x / MMPI * DPI);
	}

	private static float cpy(float y) {
		return 842.0f - (y / MMPI * DPI);
	}

	public enum HAlignment {
		LEFT, CENTER, RIGHT
	}

	public enum VAlignment {
		TOP, CENTER, BOTTOM
	}

	public enum FontType {
		BOLD, REGULAR, ITALIC, BOLD_ITALIC
	}

	public static String addParagraph(PDDocument document, PDPageContentStream cs, String str, float fontSize, float x,
			float y, float width, float height, HAlignment hAlignment, VAlignment vAlignment, FontType fontType)
			throws Exception {

		cs.beginText();
		
		str = str.replace("\n", " ");
		

		PDFont font;
		switch (fontType) {
		case BOLD:
			font = PDType0Font.load(document, new File("C:\\Program Files\\ProjectPostIt/font/THSarabunNew Bold.ttf"));
			break;
		case BOLD_ITALIC:
			font = PDType0Font.load(document, new File("C:\\Program Files\\ProjectPostIt/font/THSarabunNew BoldItalic.ttf"));
			break;
		case ITALIC:
			font = PDType0Font.load(document, new File("C:\\Program Files\\ProjectPostIt/font/THSarabunNew Italic.ttf"));
			break;
		case REGULAR:
			font = PDType0Font.load(document, new File("C:\\Program Files\\ProjectPostIt/font/THSarabunNew.ttf"));
			break;
		default:
			font = PDType0Font.load(document, new File("C:\\Program Files\\ProjectPostIt/font/THSarabunNew.ttf"));
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

	public static String addParagraphUPC(PDDocument document, PDPageContentStream cs, String str, float fontSize,
			float x, float y, float width, float height, HAlignment hAlignment, VAlignment vAlignment,
			FontType fontType) throws Exception {

		cs.beginText();
		
		str = str.replace("\n", " ");

		PDFont font;
		switch (fontType) {
		case BOLD:
			font = PDType0Font.load(document, new File("C:\\Program Files\\ProjectPostIt/font/AngsanaUPC Bold.ttf"));
			break;
		case BOLD_ITALIC:
			font = PDType0Font.load(document, new File("C:\\Program Files\\ProjectPostIt/font/AngsanaUPC BoldItalic.ttf"));
			break;
		case ITALIC:
			font = PDType0Font.load(document, new File("C:\\Program Files\\ProjectPostIt/font/AngsanaUPC Italic.ttf"));
			break;
		case REGULAR:
			font = PDType0Font.load(document, new File("C:\\Program Files\\ProjectPostIt/font/AngsanaUPC.ttf"));
			break;
		default:
			font = PDType0Font.load(document, new File("C:\\Program Files\\ProjectPostIt/font/AngsanaUPC.ttf"));
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
		PDImageXObject logoImage = PDImageXObject.createFromFile("C:\\Program Files\\ProjectPostIt/res/yono_logo.png", document);
		contentStream.drawImage(logoImage, cpx(10f), cpy(10f) - cpx(26.9f), cpx(46.1f), cpx(26.9f));

		/*
		 * Header
		 */

		contentStream.addRect(cpx(122.2f), cpy(10f) - cpx(15.6f), cpx(87.8f), cpx(15.6f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		addParagraph(document, contentStream, formName, 28f, 122.2f, 10f, 87.8f, 15.6f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "NO." + id + "  DATE: " + date, 14f, 122.2f, 25.6f, 75f, 12.8f,
				HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "YONO TOOLS CO.,LTD.", 24f, 12.8f, 38.4f, 80.8f, 8.7f, HAlignment.LEFT,
				VAlignment.CENTER, FontType.BOLD);

		float addressFontSize = 13.0f;
		addParagraph(document, contentStream, "108/314 M.5 T.Phanthai Norasing, A.Muang Samut Sakhon,", addressFontSize,
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
				output = addParagraph(document, contentStream, s, 12f, 134f, rowY, 57.5f, 8.7f, HAlignment.LEFT,
						VAlignment.TOP, FontType.BOLD);

			if (output != "") {
				strList.add(0, output);
			}
			rowY = rowY + 5.0f;

		}

	}

	@SuppressWarnings("deprecation")
	public static void printOrder(Order form, String dest) throws Exception {

		PDDocument document = new PDDocument();

		PDPage page = new PDPage(NORMAL_PAGE);

		document.addPage(page);

		Color base = Color.LIGHT_GRAY;

		float shFontSize = 14.0f;
		float lhFontSize = 12.0f;
		float listFontSize = 12.0f;
		float footerFontSize = 14.0f;
		float signatureFontSize = 14.0f;

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		/*
		 * Header
		 */
		addHeader(document, contentStream, base, "ใบสั่งซื้อ(Order)", form.getId(), form.getDate());

		/*
		 * Side Header
		 */

		contentStream.addRect(cpx(12.7f), cpy(89.2f) - cpx(17.2f), cpx(69.9f), cpx(17.2f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();

		addParagraph(document, contentStream, "Term of Payment: " + form.getPaymentTerm(), shFontSize, 12.7f, 89.2f,
				69.9f, 17.2f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		/*
		 * Customer Header
		 */
		addCustomerInfo(document, contentStream, base, form.getCustomer());

		/*
		 * List Header
		 */

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

		ArrayList<Item> itemList = form.getItemList();
		DecimalFormat formatterDouble = new DecimalFormat("#,###.00");
		DecimalFormat formatterInt = new DecimalFormat("#,###");
		for (int i = 0; i < itemList.size(); i++) {

			Item item = itemList.get(i);
			float a = 6f;
			addParagraph(document, contentStream, Integer.toString(i + 1), listFontSize, 12.7f, (123.1f + (i * a)),
					6.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, item.getProduct().getDescription(), listFontSize, 19.7f,
					(123.1f + (i * a)), 75f, a, HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterInt.format(item.getItemQuantity()), listFontSize, 93.6f,
					(123.1f + (i * a)), 23.3f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, item.getProduct().getUnit(), listFontSize, 116.7f, (123.1f + (i * a)),
					11.4f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterDouble.format(item.getProduct().getPrice()), listFontSize,
					128.1f, (123.1f + (i * a)), 23.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, String.format("%.0f", item.getDiscount()) + "%", listFontSize, 151.1f,
					(123.1f + (i * a)), 23.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterDouble.format(item.getAmount()), listFontSize, 174.1f,
					(123.1f + (i * a)), 22f, a, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

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

		addParagraph(document, contentStream, new ThaiBaht().getText(form.getValueAfterTax()), footerFontSize, 12.7f,
				241.2f, 103.8f, 8.7f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "  มูลค่าก่อนภาษี", footerFontSize, 116.7f, 224.3f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  ภาษีมูลค่าเพิ่ม", footerFontSize, 116.7f, 233.0f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  รวมสุทธิ", footerFontSize, 116.7f, 241.2f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, formatterDouble.format(form.getValueBeforeTax()), footerFontSize, 151.1f,
				224.3f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueTax()), footerFontSize, 151.1f,
				233.0f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueAfterTax()), footerFontSize, 151.1f,
				241.2f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

		/*
		 * Signature
		 */

		addParagraph(document, contentStream, "ลงชื่อ.............................................", signatureFontSize,
				12.8f, 258.1f, 93.1f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "ผู้ขอซื้อ", signatureFontSize, 12.8f, 265.1f, 93.1f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "วันที่....../....../......", signatureFontSize, 12.8f, 272.1f, 93.1f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "ลงชื่อ.............................................", signatureFontSize,
				105.9f, 258.1f, 91.2f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "ผู้อนุมัติ", signatureFontSize, 105.9f, 265.1f, 91.2f, 6f,
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

	@SuppressWarnings("deprecation")
	public static void printDelivery(Delivery form, String dest) throws Exception {

		PDDocument document = new PDDocument();

		PDPage page = new PDPage(NORMAL_PAGE);
		PDPage page2 = new PDPage(NORMAL_PAGE);

		document.addPage(page);

		Color base = new Color(144, 238, 144); // Another Page Yellow Color

		float shFontSize = 14.0f;
		float lhFontSize = 12.0f;
		float listFontSize = 12.0f;
		float footerFontSize = 14.0f;
		float signatureFontSize = 14.0f;

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		/*
		 * Header
		 */
		addHeader(document, contentStream, base, "ใบส่งสินค้า(Delivery Note)", form.getId(), form.getDate());

		/*
		 * Side Header
		 */

		contentStream.addRect(cpx(12.7f), cpy(89.2f) - cpx(17.2f), cpx(69.9f), cpx(17.2f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();

		addParagraph(document, contentStream, "Contact: " + form.getContact(), shFontSize, 12.7f, 89.2f, 69.9f, 17.2f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		/*
		 * Customer Header
		 */
		addCustomerInfo(document, contentStream, base, form.getCustomer());

		/*
		 * List Header
		 */

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

		ArrayList<Item> itemList = form.getItemList();
		DecimalFormat formatterDouble = new DecimalFormat("#,###.00");
		DecimalFormat formatterInt = new DecimalFormat("#,###");
		for (int i = 0; i < itemList.size(); i++) {

			Item item = itemList.get(i);
			float a = 6f;
			addParagraph(document, contentStream, Integer.toString(i + 1), listFontSize, 12.7f, (123.1f + (i * a)),
					6.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, item.getProduct().getDescription(), listFontSize, 19.7f,
					(123.1f + (i * a)), 75f, a, HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterInt.format(item.getItemQuantity()), listFontSize, 93.6f,
					(123.1f + (i * a)), 23.3f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, item.getProduct().getUnit(), listFontSize, 116.7f, (123.1f + (i * a)),
					11.4f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterDouble.format(item.getProduct().getPrice()), listFontSize,
					128.1f, (123.1f + (i * a)), 23.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, String.format("%.0f", item.getDiscount()) + "%", listFontSize, 151.1f,
					(123.1f + (i * a)), 23.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterDouble.format(item.getAmount()), listFontSize, 174.1f,
					(123.1f + (i * a)), 22f, a, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

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

		addParagraph(document, contentStream, new ThaiBaht().getText(form.getValueAfterTax()), footerFontSize, 12.7f,
				241.2f, 103.8f, 8.7f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "  มูลค่าก่อนภาษี", footerFontSize, 116.7f, 224.3f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  ภาษีมูลค่าเพิ่ม", footerFontSize, 116.7f, 233.0f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  รวมสุทธิ", footerFontSize, 116.7f, 241.2f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, formatterDouble.format(form.getValueBeforeTax()), footerFontSize, 151.1f,
				224.3f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueTax()), footerFontSize, 151.1f,
				233.0f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueAfterTax()), footerFontSize, 151.1f,
				241.2f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

		/*
		 * P.S.
		 */
		addParagraph(document, contentStream,
				"หมายเหตุ : สินค้าตามรายการข้างต้น หากมีการเสียหายหรือขาดตกบกพร่อง โปรดแจ้งให้ทราบภายใน 3 วัน นับจากวันที่ได้รับสินค้า มิฉะนั้น ทางบริษัทฯ จะไม่รับผิดชอบใดๆ ทั้งสิ้น",
				11f, 14f, 251f, 1000f, 1000f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);

		/*
		 * Signature
		 */

		addParagraph(document, contentStream, "ลงชื่อ.............................................", signatureFontSize,
				12.8f, 258.1f, 93.1f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "ผู้ส่งสินค้า", signatureFontSize, 12.8f, 265.1f, 93.1f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "วันที่....../....../......", signatureFontSize, 12.8f, 272.1f, 93.1f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "ลงชื่อ.............................................", signatureFontSize,
				105.9f, 258.1f, 91.2f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "ผู้รับสินค้า", signatureFontSize, 105.9f, 265.1f, 91.2f, 6f,
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

		document.addPage(page2);

		base = Color.YELLOW; // Another Page Yellow Color

		contentStream = new PDPageContentStream(document, page2);

		/*
		 * Header
		 */
		addHeader(document, contentStream, base, "ใบส่งสินค้า(Delivery Note)", form.getId(), form.getDate());

		/*
		 * Side Header
		 */

		contentStream.addRect(cpx(12.7f), cpy(89.2f) - cpx(17.2f), cpx(69.9f), cpx(17.2f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();

		addParagraph(document, contentStream, "Contact: " + form.getContact(), shFontSize, 12.7f, 89.2f, 69.9f, 17.2f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		/*
		 * Customer Header
		 */
		addCustomerInfo(document, contentStream, base, form.getCustomer());

		/*
		 * List Header
		 */

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

		itemList = form.getItemList();
		for (int i = 0; i < itemList.size(); i++) {

			Item item = itemList.get(i);
			float a = 6f;
			addParagraph(document, contentStream, Integer.toString(i + 1), listFontSize, 12.7f, (123.1f + (i * a)),
					6.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, item.getProduct().getDescription(), listFontSize, 19.7f,
					(123.1f + (i * a)), 75f, a, HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterInt.format(item.getItemQuantity()), listFontSize, 93.6f,
					(123.1f + (i * a)), 23.3f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, item.getProduct().getUnit(), listFontSize, 116.7f, (123.1f + (i * a)),
					11.4f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterDouble.format(item.getProduct().getPrice()), listFontSize,
					128.1f, (123.1f + (i * a)), 23.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, String.format("%.0f", item.getDiscount()) + "%", listFontSize, 151.1f,
					(123.1f + (i * a)), 23.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterDouble.format(item.getAmount()), listFontSize, 174.1f,
					(123.1f + (i * a)), 22f, a, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

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

		addParagraph(document, contentStream, new ThaiBaht().getText(form.getValueAfterTax()), footerFontSize, 12.7f,
				241.2f, 103.8f, 8.7f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "  มูลค่าก่อนภาษี", footerFontSize, 116.7f, 224.3f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  ภาษีมูลค่าเพิ่ม", footerFontSize, 116.7f, 233.0f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  รวมสุทธิ", footerFontSize, 116.7f, 241.2f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, formatterDouble.format(form.getValueBeforeTax()), footerFontSize, 151.1f,
				224.3f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueTax()), footerFontSize, 151.1f,
				233.0f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueAfterTax()), footerFontSize, 151.1f,
				241.2f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

		/*
		 * P.S.
		 */
		addParagraph(document, contentStream,
				"หมายเหตุ : สินค้าตามรายการข้างต้น หากมีการเสียหายหรือขาดตกบกพร่อง โปรดแจ้งให้ทราบภายใน 3 วัน นับจากวันที่ได้รับสินค้า มิฉะนั้น ทางบริษัทฯ จะไม่รับผิดชอบใดๆ ทั้งสิ้น",
				11f, 14f, 251f, 1000f, 1000f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);

		/*
		 * Signature
		 */

		addParagraph(document, contentStream, "ลงชื่อ.............................................", signatureFontSize,
				12.8f, 258.1f, 93.1f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "ผู้ส่งสินค้า", signatureFontSize, 12.8f, 265.1f, 93.1f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "วันที่....../....../......", signatureFontSize, 12.8f, 272.1f, 93.1f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "ลงชื่อ.............................................", signatureFontSize,
				105.9f, 258.1f, 91.2f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "ผู้รับสินค้า", signatureFontSize, 105.9f, 265.1f, 91.2f, 6f,
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

	@SuppressWarnings("deprecation")
	public static void printProductLoan(ProductLoan form, String dest) throws Exception {

		PDDocument document = new PDDocument();

		PDPage page = new PDPage(NORMAL_PAGE);

		document.addPage(page);

		Color base = new Color(255, 182, 193);

		float shFontSize = 14.0f;
		float lhFontSize = 12.0f;
		float listFontSize = 12.0f;
		float footerFontSize = 14.0f;
		float signatureFontSize = 14.0f;

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		/*
		 * Header
		 */
		addHeader(document, contentStream, base, "ใบยืมสินค้า(Product Loan)", form.getId(), form.getDate());

		/*
		 * Side Header
		 */
		contentStream.addRect(cpx(12.7f), cpy(89.2f) - cpx(17.2f), cpx(69.9f), cpx(17.2f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();

		addParagraph(document, contentStream, "Contact: " + form.getContact(), shFontSize, 12.7f, 89.2f, 69.9f, 17.2f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		/*
		 * Customer Header
		 */
		addCustomerInfo(document, contentStream, base, form.getCustomer());

		/*
		 * List Header
		 */

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

		ArrayList<Item> itemList = form.getItemList();
		DecimalFormat formatterDouble = new DecimalFormat("#,###.00");
		DecimalFormat formatterInt = new DecimalFormat("#,###");
		for (int i = 0; i < itemList.size(); i++) {

			Item item = itemList.get(i);
			float a = 6f;
			addParagraph(document, contentStream, Integer.toString(i + 1), listFontSize, 12.7f, (123.1f + (i * a)),
					6.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, item.getProduct().getDescription(), listFontSize, 19.7f,
					(123.1f + (i * a)), 75f, a, HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterInt.format(item.getItemQuantity()), listFontSize, 93.6f,
					(123.1f + (i * a)), 23.3f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, item.getProduct().getUnit(), listFontSize, 116.7f, (123.1f + (i * a)),
					11.4f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterDouble.format(item.getProduct().getPrice()), listFontSize,
					128.1f, (123.1f + (i * a)), 23.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, String.format("%.0f", item.getDiscount()) + "%", listFontSize, 151.1f,
					(123.1f + (i * a)), 23.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterDouble.format(item.getAmount()), listFontSize, 174.1f,
					(123.1f + (i * a)), 22f, a, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

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

		addParagraph(document, contentStream, new ThaiBaht().getText(form.getValueAfterTax()), footerFontSize, 12.7f,
				241.2f, 103.8f, 8.7f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "  มูลค่าก่อนภาษี", footerFontSize, 116.7f, 224.3f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  ภาษีมูลค่าเพิ่ม", footerFontSize, 116.7f, 233.0f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  รวมสุทธิ", footerFontSize, 116.7f, 241.2f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, formatterDouble.format(form.getValueBeforeTax()), footerFontSize, 151.1f,
				224.3f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueTax()), footerFontSize, 151.1f,
				233.0f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueAfterTax()), footerFontSize, 151.1f,
				241.2f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

		/*
		 * Signature
		 */

		addParagraph(document, contentStream, "ลงชื่อ.............................................", signatureFontSize,
				12.8f, 258.1f, 93.1f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "ผู้ส่งสินค้า", signatureFontSize, 12.8f, 265.1f, 93.1f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "วันที่....../....../......", signatureFontSize, 12.8f, 272.1f, 93.1f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "ลงชื่อ.............................................", signatureFontSize,
				105.9f, 258.1f, 91.2f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "ผู้รับสินค้า", signatureFontSize, 105.9f, 265.1f, 91.2f, 6f,
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

	@SuppressWarnings("deprecation")
	public static void printCreditNote(CreditNote form, String dest) throws Exception {

		PDDocument document = new PDDocument();

		PDPage page = new PDPage(NORMAL_PAGE);

		document.addPage(page);

		Color base = new Color(255, 127, 80);

		float shFontSize = 14.0f;
		float lhFontSize = 12.0f;
		float listFontSize = 12.0f;
		float footerFontSize = 14.0f;
		float signatureFontSize = 14.0f;

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		/*
		 * Header
		 */
		addHeader(document, contentStream, base, "ใบลดหนี้(Credit Note)", form.getId(), form.getDate());

		/*
		 * Side Header
		 */
		contentStream.addRect(cpx(12.7f), cpy(89.2f) - cpx(17.2f), cpx(84.9f), cpx(17.2f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();

		addParagraph(document, contentStream, "อ้างถึงเลขที่ใบกำกับภาษี(ฉบับเดิม) " + form.getInvoice().getId(),
				shFontSize, 15f, 89.2f, 84.9f, 8.6f, HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "วันที่ตามใบกำกับภาษี(ฉบับเดิม) " + form.getInvoice().getDate(),
				shFontSize, 15f, 96.2f, 84.9f, 8.6f, HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);

		/*
		 * Customer Header
		 */
		addCustomerInfo(document, contentStream, base, form.getCustomer());

		/*
		 * List Header
		 */

		contentStream.addRect(cpx(12.7f), cpy(114.6f) - cpx(8.7f), cpx(6.1f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		contentStream.addRect(cpx(18.7f), cpy(114.6f) - cpx(8.7f), cpx(132.8f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		contentStream.addRect(cpx(151.1f), cpy(114.6f) - cpx(8.7f), cpx(46.1f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();

		addParagraph(document, contentStream, "NO", lhFontSize, 12.7f, 114.6f, 6.1f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "DESCRIPTION", lhFontSize, 18.7f, 114.6f, 132.8f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "AMOUNT", lhFontSize, 151.1f, 114.6f, 46.1f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);

		/*
		 * List
		 */

		ArrayList<Item> itemList = form.getInvoice().getItemList();
		DecimalFormat formatterDouble = new DecimalFormat("#,###.00");
		for (int i = 0; i < itemList.size(); i++) {

			Item item = itemList.get(i);
			float a = 6f;
			addParagraph(document, contentStream, Integer.toString(i + 1), listFontSize, 12.7f, (123.1f + (i * a)),
					6.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, item.getProduct().getDescription(), listFontSize, 19.7f,
					(123.1f + (i * a)), 132.8f, a, HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterDouble.format(item.getAmount()), listFontSize, 151.1f,
					(123.1f + (i * a)), 45.1f, a, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

		}

		/*
		 * Footer
		 */
		contentStream.addRect(cpx(12.7f), cpy(189.5f) - cpx(8.7f), cpx(184.6f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		contentStream.addRect(cpx(12.7f), cpy(206.9f) - cpx(8.7f), cpx(184.6f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		contentStream.addRect(cpx(12.7f), cpy(224.3f) - cpx(8.7f), cpx(184.6f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();

		addParagraph(document, contentStream, new ThaiBaht().getText(form.getValueAfterTax()), footerFontSize, 12.7f,
				224.3f, 103.8f, 8.7f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "  มูลค่าตามใบกำกับภาษีเดิม", footerFontSize, 116.7f, 189.5f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  มูลค่าที่ถูกต้อง", footerFontSize, 116.7f, 198.2f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  มูลค่าผลต่าง(ก่อนVAT)", footerFontSize, 116.7f, 206.9f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  VAT 7%", footerFontSize, 116.7f, 215.6f, 34.4f, 8.7f, HAlignment.LEFT,
				VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  รวมมูลค่า(รวมVAT)", footerFontSize, 116.7f, 224.3f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, formatterDouble.format(form.getValueOld()), footerFontSize, 151.1f,
				189.5f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueReal()), footerFontSize, 151.1f,
				198.2f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueBeforeTax()), footerFontSize, 151.1f,
				206.9f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueTax()), footerFontSize, 151.1f,
				215.6f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueAfterTax()), footerFontSize, 151.1f,
				224.3f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

		/*
		 * Side Footer
		 */

		contentStream.addRect(cpx(12.7f), cpy(241.2f) - cpx(8.7f), cpx(50f), cpx(8.7f));
		contentStream.setNonStrokingColor(base);
		contentStream.fill();
		addParagraph(document, contentStream, "เหตุผลการลดหนี้", 16f, 12.7f, 241.2f, 50f, 8.7f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);

		/*
		 * Signature
		 */

		addParagraph(document, contentStream, "ลงชื่อ.............................................", signatureFontSize,
				12.8f, 258.1f, 62.4f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "ผู้จัดทำ", signatureFontSize, 12.8f, 265.1f, 62.4f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "วันที่....../....../......", signatureFontSize, 12.8f, 272.1f, 62.4f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "ลงชื่อ.............................................", signatureFontSize,
				75.2f, 258.1f, 62.4f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "ผู้มีอำนาจลงนาม", signatureFontSize, 75.2f, 265.1f, 62.4f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "วันที่....../....../......", signatureFontSize, 75.2f, 272.1f, 62.4f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "ลงชื่อ.............................................", signatureFontSize,
				136.8f, 258.1f, 62.4f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "ผู้อนุมัติ", signatureFontSize, 136.8f, 265.1f, 62.4f, 6f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "วันที่....../....../......", signatureFontSize, 136.8f, 272.1f, 62.4f,
				6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		/*
		 * DrawLine
		 */
		// Vertical
		contentStream.drawLine(cpx(12.7f), cpy(114.6f), cpx(12.7f), cpy(233f));
		contentStream.drawLine(cpx(18.7f), cpy(114.6f), cpx(18.7f), cpy(189.5f));
		contentStream.drawLine(cpx(116.7f), cpy(189.5f), cpx(116.7f), cpy(233f));
		contentStream.drawLine(cpx(151.1f), cpy(114.6f), cpx(151.1f), cpy(189.5f));
		contentStream.drawLine(cpx(197.1f), cpy(114.6f), cpx(197.1f), cpy(233f));

		contentStream.drawLine(cpx(12.7f), cpy(241.2f), cpx(12.7f), cpy(249.9f));
		contentStream.drawLine(cpx(62.7f), cpy(241.2f), cpx(62.7f), cpy(249.9f));
		contentStream.drawLine(cpx(197.1f), cpy(241.2f), cpx(197.1f), cpy(249.9f));

		// Horizontal

		contentStream.drawLine(cpx(12.7f), cpy(114.6f), cpx(197.1f), cpy(114.6f));
		contentStream.drawLine(cpx(12.7f), cpy(123.1f), cpx(197.1f), cpy(123.1f));
		contentStream.drawLine(cpx(12.7f), cpy(189.5f), cpx(197.1f), cpy(189.5f));

		contentStream.drawLine(cpx(116.7f), cpy(198.1f), cpx(197.1f), cpy(198.1f));
		contentStream.drawLine(cpx(116.7f), cpy(206.8f), cpx(197.1f), cpy(206.8f));
		contentStream.drawLine(cpx(116.7f), cpy(215.5f), cpx(197.1f), cpy(215.5f));
		contentStream.drawLine(cpx(116.7f), cpy(224.2f), cpx(197.1f), cpy(224.2f));

		contentStream.drawLine(cpx(12.7f), cpy(233f), cpx(197.1f), cpy(233f));
		contentStream.drawLine(cpx(12.7f), cpy(241.2f), cpx(197.1f), cpy(241.2f));
		contentStream.drawLine(cpx(12.7f), cpy(249.9f), cpx(197.1f), cpy(249.9f));

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

		float shFontSize = 14.0f;
		float lhFontSize = 12.0f;
		float listFontSize = 12.0f;
		float footerFontSize = 14.0f;
		float signatureFontSize = 14.0f;

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
		ArrayList<Item> itemList = form.getItemList();
		DecimalFormat formatterDouble = new DecimalFormat("#,###.00");
		DecimalFormat formatterInt = new DecimalFormat("#,###");
		for (int i = 0; i < itemList.size(); i++) {

			Item item = itemList.get(i);
			float a = 6f;
			addParagraph(document, contentStream, Integer.toString(i + 1), listFontSize, 12.7f, (123.1f + (i * a)),
					6.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, item.getProduct().getDescription(), listFontSize, 19.7f,
					(123.1f + (i * a)), 75f, a, HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterInt.format(item.getItemQuantity()), listFontSize, 93.6f,
					(123.1f + (i * a)), 23.3f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, item.getProduct().getUnit(), listFontSize, 116.7f, (123.1f + (i * a)),
					11.4f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterDouble.format(item.getProduct().getPrice()), listFontSize,
					128.1f, (123.1f + (i * a)), 23.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, String.format("%.0f", item.getDiscount()) + "%", listFontSize, 151.1f,
					(123.1f + (i * a)), 23.1f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, contentStream, formatterDouble.format(item.getAmount()), listFontSize, 174.1f,
					(123.1f + (i * a)), 22f, a, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

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

		addParagraph(document, contentStream, new ThaiBaht().getText(form.getValueAfterTax()), footerFontSize, 12.7f,
				241.2f, 103.8f, 8.7f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, "  มูลค่าก่อนภาษี", footerFontSize, 116.7f, 224.3f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  ภาษีมูลค่าเพิ่ม", footerFontSize, 116.7f, 233.0f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, "  รวมสุทธิ", footerFontSize, 116.7f, 241.2f, 34.4f, 8.7f,
				HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);

		addParagraph(document, contentStream, formatterDouble.format(form.getValueBeforeTax()), footerFontSize, 151.1f,
				224.3f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueTax()), footerFontSize, 151.1f,
				233.0f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
		addParagraph(document, contentStream, formatterDouble.format(form.getValueAfterTax()), footerFontSize, 151.1f,
				241.2f, 45.1f, 8.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

		/*
		 * Signature
		 */

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

	public static void printInvoice(Invoice form, String dest) throws Exception {

		PDDocument document = new PDDocument();

		PDPage page = new PDPage(NORMAL_PAGE);

		document.addPage(page);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		/*
		 * Header
		 */

		float headerFontSize = 15f;
		addParagraphUPC(document, contentStream, form.getId(), headerFontSize, 167.4f, 65.6f, 1000f, 1000f,
				HAlignment.LEFT, VAlignment.TOP, FontType.REGULAR);
		addParagraphUPC(document, contentStream, form.getDate(), headerFontSize, 167.4f, 76.5f, 1000f, 1000f,
				HAlignment.LEFT, VAlignment.TOP, FontType.REGULAR);

		Customer customer = form.getCustomer();
		addParagraphUPC(document, contentStream, "เลขประจำตัวผู้เสียภาษีอากร : " + customer.getTaxID(), headerFontSize,
				17.5f, 55f, 1000f, 1000f, HAlignment.LEFT, VAlignment.TOP, FontType.REGULAR);
		addParagraphUPC(document, contentStream, customer.getName(), headerFontSize, 25.9f, 66f, 1000f, 1000f,
				HAlignment.LEFT, VAlignment.TOP, FontType.REGULAR);
		addParagraphUPC(document, contentStream, customer.getAddress(), headerFontSize, 25.9f, 72f, 1000f, 1000f,
				HAlignment.LEFT, VAlignment.TOP, FontType.REGULAR);
		addParagraphUPC(document, contentStream, "Tel: " + customer.getTel() + " Fax: " + customer.getFax(),
				headerFontSize, 25.9f, 78f, 1000f, 1000f, HAlignment.LEFT, VAlignment.TOP, FontType.REGULAR);

		/*
		 * Side Header
		 */
		float shFontSize = 15f;

		addParagraphUPC(document, contentStream, form.getPoNum(), shFontSize, 2.4f, 98f, 33.6f, 7.1f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.REGULAR);
		addParagraphUPC(document, contentStream, form.getOrderBy(), shFontSize, 36f, 98f, 37.6f, 7.1f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
		addParagraphUPC(document, contentStream, form.getPaymentTerm(), shFontSize, 69f, 98f, 39.6f, 7.1f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
		addParagraphUPC(document, contentStream, form.getDateDue(), shFontSize, 110.1f, 98f, 46.7f, 7.1f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
		addParagraphUPC(document, contentStream, form.getSales(), shFontSize, 155.8f, 98f, 46.3f, 7.1f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);

		/*
		 * List
		 */
		float listFontSize = 15f;
		float a = 6f;
		DecimalFormat formatterDouble = new DecimalFormat("#,###.00");
		DecimalFormat formatterInt = new DecimalFormat("#,###");

		for (int i = 0; i < form.getItemList().size(); i++) {

			Item item = form.getItemList().get(i);

			addParagraphUPC(document, contentStream, Integer.toString(i + 1), listFontSize, 0f, (117.7f + (i * a)),
					6f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
			addParagraphUPC(document, contentStream, item.getProduct().getDescription(), listFontSize, 10.8f,
					(117.7f + (i * a)), 90.2f, a, HAlignment.LEFT, VAlignment.CENTER, FontType.REGULAR);
			addParagraphUPC(document, contentStream, formatterInt.format(item.getItemQuantity()), listFontSize, 105f,
					(117.7f + (i * a)), 18f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);
			addParagraphUPC(document, contentStream, item.getProduct().getUnit(), listFontSize, 126f,
					(117.7f + (i * a)), 18f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.REGULAR);

			double price = item.getProduct().getPrice() * (1 - (item.getDiscount() / 100.0));
			addParagraphUPC(document, contentStream, formatterDouble.format(price), listFontSize, 138f,
					(117.7f + (i * a)), 26.2f, a, HAlignment.RIGHT, VAlignment.CENTER, FontType.REGULAR);
			addParagraphUPC(document, contentStream, formatterDouble.format(item.getAmount()), listFontSize, 167.2f,
					(117.7f + (i * a)), 29.9f, a, HAlignment.RIGHT, VAlignment.CENTER, FontType.REGULAR);

		}

		/*
		 * Footer
		 */

		float footerFontSize = 15f;

		addParagraphUPC(document, contentStream, new ThaiBaht().getText(form.getValueAfterTax()), footerFontSize, 17.5f,
				228.8f, 148.6f, 8.5f, HAlignment.LEFT, VAlignment.CENTER, FontType.REGULAR);
		addParagraphUPC(document, contentStream, formatterDouble.format(form.getValueBeforeTax()), footerFontSize,
				167.2f, 213.2f, 29.9f, 7.9f, HAlignment.RIGHT, VAlignment.CENTER, FontType.REGULAR);
		addParagraphUPC(document, contentStream, formatterDouble.format(form.getValueTax()), footerFontSize, 167.2f,
				221.1f, 29.9f, 7.7f, HAlignment.RIGHT, VAlignment.CENTER, FontType.REGULAR);
		addParagraphUPC(document, contentStream, formatterDouble.format(form.getValueAfterTax()), footerFontSize,
				167.2f, 228.8f, 29.9f, 8.5f, HAlignment.RIGHT, VAlignment.CENTER, FontType.REGULAR);

		contentStream.close();

		document.save(dest);

		document.close();

		System.out.println("PDF Created");

	}

	@SuppressWarnings("deprecation")
	public static void printBilling(Billing form, String dest) throws Exception {

		PDDocument document = new PDDocument();

		PDPage page = new PDPage(NORMAL_PAGE);

		document.addPage(page);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		/*
		 * Header
		 */
		addParagraphUPC(document, contentStream, "บริษัท โย โน ทูลส์ จำกัด (สำนักงานใหญ่)", 24f, 13.5f, 15.1f, 1000f,
				1000f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, "ต้นฉบับใบวางบิล", 24f, 151.3f, 15.1f, 1000f, 1000f, HAlignment.LEFT,
				VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, "108/314 หมู่ 5 ต.พันท้ายนรสิงส์ อ.เมืองสมุทรสาคร จ.สมุทรสาคร", 14f,
				13.5f, 27.5f, 1000f, 1000f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, "โทร.034-116655, 099-0568889 แฟ็กส์.034-116655", 14f, 13.5f, 33.5f,
				1000f, 1000f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, "หน้า 1/1", 14f, 154f, 27f, 1000f, 1000f, HAlignment.LEFT,
				VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, "TEX :ID 0 1 2 5 5 6 0 0 0 0 5 9 0", 14f, 154f, 33f, 1000f, 1000f,
				HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);

		/*
		 * Side Header
		 */

		float shFontSize = 14f;

		addParagraphUPC(document, contentStream, "เลขที่ใบวางบิล " + form.getId(), shFontSize, 154.1f, 43f, 46.6f, 6f,
				HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, "วันที่วางบิล " + form.getBillingDate(), shFontSize, 154.1f, 49f,
				46.6f, 6f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, "บันทึกโดย " + form.getBillingBy(), shFontSize, 154.1f, 55f, 46.6f, 6f,
				HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);

		Customer customer = form.getCustomer();
		addParagraphUPC(document, contentStream, "รหัสลูกค้า : " + customer.getCode(), shFontSize, 13.6f, 43f, 137.8f,
				6f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, customer.getName(), shFontSize, 13.6f, 49f, 137.8f, 6f,
				HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, "ที่อยู่ : " + customer.getAddress(), shFontSize, 13.6f, 55f, 137.8f,
				6f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, "โทร: " + customer.getTel() + " แฟ็กส์ : " + customer.getFax(),
				shFontSize, 13.6f, 61f, 137.8f, 6f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, "TEX :ID " + customer.getTaxID(), shFontSize, 13.6f, 67f, 137.8f, 6f,
				HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, "หมายเหตุ " + form.getPs(), shFontSize, 13.6f, 73f, 137.8f, 6f,
				HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);

		/*
		 * List Header
		 */

		float lhFontSize = 14f;

		addParagraphUPC(document, contentStream, "ลำดับที่", lhFontSize, 13.5f, 86f, 14.8f, 7.1f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraphUPC(document, contentStream, "เลขที่ใบขาย", lhFontSize, 28.3f, 86f, 34.4f, 7.1f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraphUPC(document, contentStream, "วันที่ขาย", lhFontSize, 62.7f, 86f, 34.4f, 7.1f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraphUPC(document, contentStream, "วันที่ครบกำหนด", lhFontSize, 97.1f, 86f, 29.4f, 7.1f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraphUPC(document, contentStream, "จำนวนเงิน", lhFontSize, 126.5f, 86f, 26.5f, 7.1f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);
		addParagraphUPC(document, contentStream, "หมายเหตุ", lhFontSize, 152.9f, 86f, 47.6f, 7.1f, HAlignment.CENTER,
				VAlignment.CENTER, FontType.BOLD);

		/*
		 * List
		 */

		ArrayList<Invoice> invoiceList = form.getInvoiceList();
		DecimalFormat formatterDouble = new DecimalFormat("#,###.00");
		float listFontSize = 14f;
		float a = 6.3f;

		for (int i = 0; i < invoiceList.size(); i++) {

			Invoice invoice = invoiceList.get(i);

			addParagraphUPC(document, contentStream, Integer.toString(i + 1), listFontSize, 13.5f, (93.1f + (i * a)),
					14.8f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraphUPC(document, contentStream, invoice.getId(), listFontSize, 28.3f, (93.1f + (i * a)), 34.4f, a,
					HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraphUPC(document, contentStream, invoice.getDate(), listFontSize, 62.7f, (93.1f + (i * a)), 34.4f,
					a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraphUPC(document, contentStream, invoice.getDateDue(), listFontSize, 97.1f, (93.1f + (i * a)),
					29.4f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
			addParagraphUPC(document, contentStream, formatterDouble.format(invoice.getValueAfterTax()), listFontSize,
					126.5f, (93.1f + (i * a)), 26.5f, a, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
			addParagraphUPC(document, contentStream, form.getPsList().get(i), listFontSize, 152.9f, (93.1f + (i * a)),
					47.6f, a, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		}

		/*
		 * Footer
		 */

		float footerFontSize = 14f;

		addParagraphUPC(document, contentStream, new ThaiBaht().getText(form.getValue()), footerFontSize, 13.6f, 219.8f,
				83.6f, 7.1f, HAlignment.LEFT, VAlignment.CENTER, FontType.BOLD);
		addParagraphUPC(document, contentStream, "จำนวนเงินรวม", footerFontSize, 97.1f, 219.8f, 29.4f, 7.1f,
				HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraphUPC(document, contentStream, formatterDouble.format(form.getValue()), footerFontSize, 126.5f,
				219.8f, 26.5f, 7.1f, HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);

		/*
		 * Signature
		 */
		float signatureFontSize = 14f;

		addParagraphUPC(document, contentStream, "รวมทั้งสิ้น " + invoiceList.size() + " ฉบับ", signatureFontSize,
				13.5f, 230.1f, 123.8f, 40.7f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, "ชื่อผู้รับวางบิล.............................", signatureFontSize,
				13.5f, 236.1f, 123.8f, 40.7f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, "วันที.......................................", signatureFontSize,
				13.5f, 242.1f, 123.8f, 40.7f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream,
				"นัดรับเช็ค/โอนเงิน วันที่............................. เวลา.............................",
				signatureFontSize, 13.5f, 248.1f, 123.8f, 40.7f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);
		addParagraphUPC(document, contentStream, "หมายเหตุ.........................................................",
				signatureFontSize, 13.5f, 253.1f, 123.8f, 40.7f, HAlignment.LEFT, VAlignment.TOP, FontType.BOLD);

		addParagraphUPC(document, contentStream, "ชื่อผู้วางบิล.............................................",
				signatureFontSize, 137.3f, 242.1f, 63.2f, 6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
		addParagraphUPC(document, contentStream, "วันที่....../....../......", signatureFontSize, 137.3f, 248.1f, 63.2f,
				6f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

		/*
		 * DrawLine
		 */
		// Vertical
		contentStream.drawLine(cpx(12f), cpy(40f), cpx(12f), cpy(93.1f));
		contentStream.drawLine(cpx(200.6f), cpy(40f), cpx(200.6f), cpy(93.1f));
		contentStream.drawLine(cpx(12f), cpy(219.8f), cpx(12f), cpy(227f));
		contentStream.drawLine(cpx(97.1f), cpy(219.8f), cpx(97.1f), cpy(227f));
		contentStream.drawLine(cpx(126.5f), cpy(219.8f), cpx(126.5f), cpy(227f));
		contentStream.drawLine(cpx(200.6f), cpy(219.8f), cpx(200.6f), cpy(227f));

		// Horizontal
		contentStream.drawLine(cpx(12f), cpy(40f), cpx(200.6f), cpy(40f));
		contentStream.drawLine(cpx(12f), cpy(86f), cpx(200.6f), cpy(86f));
		contentStream.drawLine(cpx(12f), cpy(93.1f), cpx(200.6f), cpy(93.1f));
		contentStream.drawLine(cpx(12f), cpy(219.8f), cpx(200.6f), cpy(219.8f));
		contentStream.drawLine(cpx(12f), cpy(227f), cpx(200.6f), cpy(227f));

		contentStream.close();

		document.save(dest);

		document.close();

		System.out.println("PDF Created");

	}

	public static void printQuotationReport(String header, ArrayList<Quotation> quotationList, String dest)
			throws Exception {

		int pageLimit = 24;
		int pageNum = quotationList.size() / pageLimit + 1;
		float sum1 = 0.0f, sum2 = 0.0f, sum3 = 0.0f;

		Collections.sort(quotationList);

		PDDocument document = new PDDocument();

		for (int i = 0; i < pageNum; i++) {

			PDPage page = new PDPage(NORMAL_PAGE);

			document.addPage(page);

			PDPageContentStream cs = new PDPageContentStream(document, page);

			float headerFontSize = 14f;
			float lhFontSize = 12f;
			float listFontSize = 12f;

			// header
			addParagraph(document, cs, header, headerFontSize, 12.8f, 10f, 197.2f, 15.2f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);

			// List Header
			addParagraph(document, cs, "วันที่", lhFontSize, 12.8f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "รหัส", lhFontSize, 38.4f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "ชื่อลูกค้า", lhFontSize, 64f, 35.6f, 51.2f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "มูลค่าก่อนภาษี", lhFontSize, 115.2f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "ภาษี 7 %", lhFontSize, 140.8f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "มูลค่าหลังภาษี", lhFontSize, 166.4f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);

			// List
			String dateTemp = "";
			int k = 0;
			for (int j = i * pageLimit; j < quotationList.size() && j < (i + 1) * pageLimit; j++) {

				Quotation quotation = quotationList.get(j);

				if (!dateTemp.equals(quotation.getDate())) {

					dateTemp = quotation.getDate();
					addParagraph(document, cs, dateTemp, listFontSize, 12.8f, 44.6f + (k * 9f), 25.6f, 9f,
							HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

				}

				addParagraph(document, cs, quotation.getId(), listFontSize, 38.4f, 44.6f + (k * 9f), 25.6f, 9f,
						HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, quotation.getCustomer().getName(), listFontSize, 64f, 44.6f + (k * 9f),
						51.2f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", quotation.getValueBeforeTax()), listFontSize, 115.2f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", quotation.getValueTax()), listFontSize, 140.8f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", quotation.getValueAfterTax()), listFontSize, 166.4f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

				sum1 += quotation.getValueBeforeTax();
				sum2 += quotation.getValueTax();
				sum3 += quotation.getValueAfterTax();
				k++;

			}

			if (i + 1 == pageNum) {

				addParagraph(document, cs, "รวมทั้งหมด", listFontSize, 64f, 44.6f + (k * 9f), 51.2f, 9f,
						HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum1), listFontSize, 115.2f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum2), listFontSize, 140.8f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum3), listFontSize, 166.4f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

			}

			cs.close();

		}

		document.save(dest);

		document.close();

		System.out.println("PDF Created");

	}
	
	public static void printDeliveryReport(String header, ArrayList<Delivery> deliveryList, String dest)
			throws Exception {

		int pageLimit = 24;
		int pageNum = deliveryList.size() / pageLimit + 1;
		float sum1 = 0.0f, sum2 = 0.0f, sum3 = 0.0f;

		Collections.sort(deliveryList);

		PDDocument document = new PDDocument();

		for (int i = 0; i < pageNum; i++) {

			PDPage page = new PDPage(NORMAL_PAGE);

			document.addPage(page);

			PDPageContentStream cs = new PDPageContentStream(document, page);

			float headerFontSize = 14f;
			float lhFontSize = 12f;
			float listFontSize = 12f;

			// header
			addParagraph(document, cs, header, headerFontSize, 12.8f, 10f, 197.2f, 15.2f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);

			// List Header
			addParagraph(document, cs, "วันที่", lhFontSize, 12.8f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "รหัส", lhFontSize, 38.4f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "ชื่อลูกค้า", lhFontSize, 64f, 35.6f, 51.2f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "มูลค่าก่อนภาษี", lhFontSize, 115.2f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "ภาษี 7 %", lhFontSize, 140.8f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "มูลค่าหลังภาษี", lhFontSize, 166.4f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);

			// List
			String dateTemp = "";
			int k = 0;
			for (int j = i * pageLimit; j < deliveryList.size() && j < (i + 1) * pageLimit; j++) {

				Delivery delivery = deliveryList.get(j);

				if (!dateTemp.equals(delivery.getDate())) {

					dateTemp = delivery.getDate();
					addParagraph(document, cs, dateTemp, listFontSize, 12.8f, 44.6f + (k * 9f), 25.6f, 9f,
							HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

				}

				addParagraph(document, cs, delivery.getId(), listFontSize, 38.4f, 44.6f + (k * 9f), 25.6f, 9f,
						HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, delivery.getCustomer().getName(), listFontSize, 64f, 44.6f + (k * 9f),
						51.2f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", delivery.getValueBeforeTax()), listFontSize, 115.2f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", delivery.getValueTax()), listFontSize, 140.8f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", delivery.getValueAfterTax()), listFontSize, 166.4f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

				sum1 += delivery.getValueBeforeTax();
				sum2 += delivery.getValueTax();
				sum3 += delivery.getValueAfterTax();
				k++;

			}

			if (i + 1 == pageNum) {

				addParagraph(document, cs, "รวมทั้งหมด", listFontSize, 64f, 44.6f + (k * 9f), 51.2f, 9f,
						HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum1), listFontSize, 115.2f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum2), listFontSize, 140.8f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum3), listFontSize, 166.4f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

			}

			cs.close();

		}

		document.save(dest);

		document.close();

		System.out.println("PDF Created");

	}
	
	public static void printInvoiceReport(String header, ArrayList<Invoice> invoiceList, String dest)
			throws Exception {

		int pageLimit = 24;
		int pageNum = invoiceList.size() / pageLimit + 1;
		float sum1 = 0.0f, sum2 = 0.0f, sum3 = 0.0f;

		Collections.sort(invoiceList);

		PDDocument document = new PDDocument();

		for (int i = 0; i < pageNum; i++) {

			PDPage page = new PDPage(NORMAL_PAGE);

			document.addPage(page);

			PDPageContentStream cs = new PDPageContentStream(document, page);

			float headerFontSize = 14f;
			float lhFontSize = 12f;
			float listFontSize = 12f;

			// header
			addParagraph(document, cs, header, headerFontSize, 12.8f, 10f, 197.2f, 15.2f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);

			// List Header
			addParagraph(document, cs, "วันที่", lhFontSize, 12.8f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "รหัส", lhFontSize, 38.4f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "ชื่อลูกค้า", lhFontSize, 64f, 35.6f, 51.2f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "มูลค่าก่อนภาษี", lhFontSize, 115.2f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "ภาษี 7 %", lhFontSize, 140.8f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "มูลค่าหลังภาษี", lhFontSize, 166.4f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);

			// List
			String dateTemp = "";
			int k = 0;
			for (int j = i * pageLimit; j < invoiceList.size() && j < (i + 1) * pageLimit; j++) {

				Invoice invoice = invoiceList.get(j);

				if (!dateTemp.equals(invoice.getDate())) {

					dateTemp = invoice.getDate();
					addParagraph(document, cs, dateTemp, listFontSize, 12.8f, 44.6f + (k * 9f), 25.6f, 9f,
							HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

				}

				addParagraph(document, cs, invoice.getId(), listFontSize, 38.4f, 44.6f + (k * 9f), 25.6f, 9f,
						HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, invoice.getCustomer().getName(), listFontSize, 64f, 44.6f + (k * 9f),
						51.2f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", invoice.getValueBeforeTax()), listFontSize, 115.2f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", invoice.getValueTax()), listFontSize, 140.8f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", invoice.getValueAfterTax()), listFontSize, 166.4f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

				sum1 += invoice.getValueBeforeTax();
				sum2 += invoice.getValueTax();
				sum3 += invoice.getValueAfterTax();
				k++;

			}

			if (i + 1 == pageNum) {

				addParagraph(document, cs, "รวมทั้งหมด", listFontSize, 64f, 44.6f + (k * 9f), 51.2f, 9f,
						HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum1), listFontSize, 115.2f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum2), listFontSize, 140.8f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum3), listFontSize, 166.4f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

			}

			cs.close();

		}

		document.save(dest);

		document.close();

		System.out.println("PDF Created");

	}
	
	public static void printOrderReport(String header, ArrayList<Order> orderList, String dest)
			throws Exception {

		int pageLimit = 24;
		int pageNum = orderList.size() / pageLimit + 1;
		float sum1 = 0.0f, sum2 = 0.0f, sum3 = 0.0f;

		Collections.sort(orderList);

		PDDocument document = new PDDocument();

		for (int i = 0; i < pageNum; i++) {

			PDPage page = new PDPage(NORMAL_PAGE);

			document.addPage(page);

			PDPageContentStream cs = new PDPageContentStream(document, page);

			float headerFontSize = 14f;
			float lhFontSize = 12f;
			float listFontSize = 12f;

			// header
			addParagraph(document, cs, header, headerFontSize, 12.8f, 10f, 197.2f, 15.2f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);

			// List Header
			addParagraph(document, cs, "วันที่", lhFontSize, 12.8f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "รหัส", lhFontSize, 38.4f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "ชื่อลูกค้า", lhFontSize, 64f, 35.6f, 51.2f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "มูลค่าก่อนภาษี", lhFontSize, 115.2f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "ภาษี 7 %", lhFontSize, 140.8f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "มูลค่าหลังภาษี", lhFontSize, 166.4f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);

			// List
			String dateTemp = "";
			int k = 0;
			for (int j = i * pageLimit; j < orderList.size() && j < (i + 1) * pageLimit; j++) {

				Order order = orderList.get(j);

				if (!dateTemp.equals(order.getDate())) {

					dateTemp = order.getDate();
					addParagraph(document, cs, dateTemp, listFontSize, 12.8f, 44.6f + (k * 9f), 25.6f, 9f,
							HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

				}

				addParagraph(document, cs, order.getId(), listFontSize, 38.4f, 44.6f + (k * 9f), 25.6f, 9f,
						HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, order.getCustomer().getName(), listFontSize, 64f, 44.6f + (k * 9f),
						51.2f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", order.getValueBeforeTax()), listFontSize, 115.2f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", order.getValueTax()), listFontSize, 140.8f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", order.getValueAfterTax()), listFontSize, 166.4f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

				sum1 += order.getValueBeforeTax();
				sum2 += order.getValueTax();
				sum3 += order.getValueAfterTax();
				k++;

			}

			if (i + 1 == pageNum) {

				addParagraph(document, cs, "รวมทั้งหมด", listFontSize, 64f, 44.6f + (k * 9f), 51.2f, 9f,
						HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum1), listFontSize, 115.2f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum2), listFontSize, 140.8f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum3), listFontSize, 166.4f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

			}

			cs.close();

		}

		document.save(dest);

		document.close();

		System.out.println("PDF Created");

	}
	
	public static void printProductLoanReport(String header, ArrayList<ProductLoan> productLoanList, String dest)
			throws Exception {

		int pageLimit = 24;
		int pageNum = productLoanList.size() / pageLimit + 1;
		float sum1 = 0.0f, sum2 = 0.0f, sum3 = 0.0f;

		Collections.sort(productLoanList);

		PDDocument document = new PDDocument();

		for (int i = 0; i < pageNum; i++) {

			PDPage page = new PDPage(NORMAL_PAGE);

			document.addPage(page);

			PDPageContentStream cs = new PDPageContentStream(document, page);

			float headerFontSize = 14f;
			float lhFontSize = 12f;
			float listFontSize = 12f;

			// header
			addParagraph(document, cs, header, headerFontSize, 12.8f, 10f, 197.2f, 15.2f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);

			// List Header
			addParagraph(document, cs, "วันที่", lhFontSize, 12.8f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "รหัส", lhFontSize, 38.4f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "ชื่อลูกค้า", lhFontSize, 64f, 35.6f, 51.2f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "มูลค่าก่อนภาษี", lhFontSize, 115.2f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "ภาษี 7 %", lhFontSize, 140.8f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);
			addParagraph(document, cs, "มูลค่าหลังภาษี", lhFontSize, 166.4f, 35.6f, 25.6f, 9f, HAlignment.CENTER,
					VAlignment.CENTER, FontType.BOLD);

			// List
			String dateTemp = "";
			int k = 0;
			for (int j = i * pageLimit; j < productLoanList.size() && j < (i + 1) * pageLimit; j++) {

				ProductLoan productLoan = productLoanList.get(j);

				if (!dateTemp.equals(productLoan.getDate())) {

					dateTemp = productLoan.getDate();
					addParagraph(document, cs, dateTemp, listFontSize, 12.8f, 44.6f + (k * 9f), 25.6f, 9f,
							HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

				}

				addParagraph(document, cs, productLoan.getId(), listFontSize, 38.4f, 44.6f + (k * 9f), 25.6f, 9f,
						HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, productLoan.getCustomer().getName(), listFontSize, 64f, 44.6f + (k * 9f),
						51.2f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", productLoan.getValueBeforeTax()), listFontSize, 115.2f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", productLoan.getValueTax()), listFontSize, 140.8f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", productLoan.getValueAfterTax()), listFontSize, 166.4f,
						44.6f + (k * 9f), 25.6f, 9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

				sum1 += productLoan.getValueBeforeTax();
				sum2 += productLoan.getValueTax();
				sum3 += productLoan.getValueAfterTax();
				k++;

			}

			if (i + 1 == pageNum) {

				addParagraph(document, cs, "รวมทั้งหมด", listFontSize, 64f, 44.6f + (k * 9f), 51.2f, 9f,
						HAlignment.RIGHT, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum1), listFontSize, 115.2f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum2), listFontSize, 140.8f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);
				addParagraph(document, cs, String.format("%,.2f", sum3), listFontSize, 166.4f, 44.6f + (k * 9f), 25.6f,
						9f, HAlignment.CENTER, VAlignment.CENTER, FontType.BOLD);

			}

			cs.close();

		}

		document.save(dest);

		document.close();

		System.out.println("PDF Created");

	}
	
	

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		try {
			/*
			 * Test Form Building
			 */
			String dest = "C:/Users/Kirk Pig/Desktop/PdfTest/sample.pdf";
			ArrayList<Item> itemList = new ArrayList<>();
			itemList.add(new Item(new Product("TRI-1235", "Product Test 1", "Piece", 157.2, 1), 1000, 35));
			itemList.add(new Item(new Product("COM-5623", "Product Test 2", "Set", 63.52, 35), 45, 22));
			Customer customer = new Customer("525", "Pig Co.LTD", "546546-55454515",
					"103/314 M.5 T.Phanthai Norasing, A.Muang Samut Sakhon, Samut Sakhon 74000", "090-841-5626",
					"02-4546455", "yourname@address.com");
			String date = "10-08-2563";
			String date2 = "09-08-2563";
			User user = new User("kirkpig", "postitpaper", "KirkPig");

			Invoice invoice = new Invoice("YN630008123", date, customer, itemList, "PO63008123", "Piggy", "CASH", date,
					"Pig", user.getName());
			Order order = new Order("PO63008123", date, customer, itemList, "CASH", user.getName());
			Delivery delivery = new Delivery("DE63008123", date, customer, itemList, "Pig", user.getName());
			ProductLoan productLoan = new ProductLoan("BL63008123", date, customer, itemList, "Pig", user.getName());
			CreditNote creditNote = new CreditNote("CR63008123", date, customer, invoice, 100000.00, user.getName());
			Quotation quotation = new Quotation("QY63008123", date, customer, itemList, "5545", "0", user.getName());
			Quotation quotation2 = new Quotation("QY63008124", date2, customer, itemList, "5545", "0", user.getName());

			ArrayList<Invoice> invoiceList = new ArrayList<>();
			ArrayList<String> psList = new ArrayList<>();
			invoiceList.add(invoice);
			psList.add("reserve");
			Billing billing = new Billing("RB63008123", date, customer, invoiceList, psList, "Piggy", date,
					"สำหรับการทดลองเพียงเท่านั้น", user.getName());

			ArrayList<Quotation> quotationList = new ArrayList<>();
			for (int i = 0; i < 18; i++) {
				quotationList.add(quotation);
			}
			for (int i = 0; i < 17; i++) {
				quotationList.add(quotation2);
			}

			/*
			 * Test Print Report
			 */

			// printOrder(order, dest);
			// printDelivery(delivery, dest);
			// printProductLoan(productLoan, dest);
			// printCreditNote(creditNote, dest);
			// printQuotation(quotation, dest);
			// printInvoice(invoice, dest);
			// printBilling(billing, dest);
			// printQuotationReport("HEADER", quotationList, dest);
			Desktop.getDesktop().open(new File(dest));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
