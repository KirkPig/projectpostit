package logic.report.template;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import bill.Billing;
import bill.CreditNote;
import bill.Delivery;
import bill.Invoice;
import bill.Item;
import bill.Order;
import bill.ProductLoan;
import bill.Quotation;
import database.User;
import logic.Customer;
import logic.Product;
import logic.report.base.ReportFontType;
import logic.report.base.ReportHAlignment;
import logic.report.base.ReportVAlignment;
import logic.report.component.ReportPictureBox;
import logic.report.component.ReportRectBox;
import logic.report.component.ReportTable;
import logic.report.component.ReportTableColumn;
import logic.report.component.ReportTextBox;
import logic.report.component.base.ReportComponent;
import logic.report.layout.ReportLayout;

public class ReportTemplate {
	
	@SuppressWarnings("unchecked")
	public static ReportLayout getQuotationLayoutTemplate(Quotation form) {
		
		Color base = new Color(224, 255, 255);
		ReportLayout layout = new ReportLayout("Quotaition #" + form.getId());
		
		/*
		 * Header
		 */
		
		ReportPictureBox logoBox = new ReportPictureBox("logoBox", 10f, 10f, 46.1f, 26.9f, "./src/res/yono_logo.png");
		ReportTextBox headerBox = new ReportTextBox("headerBox", 122.2f, 10f, 87.8f, 15.6f, "ใบเสนอราคา(Quotation)", 26f,
				ReportHAlignment.CENTER, ReportVAlignment.CENTER, ReportFontType.BOLD, base);
		ReportTextBox codeDateBox = new ReportTextBox("codeDateBox", 122.2f, 25.6f, 75f, 12.8f,
				"NO." + form.getId() + "  DATE: " + form.getDate(), 14f, ReportHAlignment.RIGHT,
				ReportVAlignment.CENTER, ReportFontType.BOLD);
		ReportTextBox titleBox = new ReportTextBox("titleBox", 12.8f, 38.4f, 80.8f, 8.7f, "YONO TOOLS CO.,LTD.", 24f,
				ReportHAlignment.LEFT, ReportVAlignment.CENTER, ReportFontType.BOLD);
		ReportTextBox sideTitleBox = new ReportTextBox("sideTitleBox", 12.8f, 47.1f, 98f, 33.8f,
				"108/314 M.5 T.Phanthai Norasing, A.Muang Samut Sakhon,\nSamut Sakhon 74000"
						+ "\nTEL: 034-116655  Fax: 034-116656  MOBILE: 099-0568889\nE-MAIL: sale.yonotools@gmail.com\n"
						+ "TAX-ID: 0125560000590",
				13f, ReportHAlignment.LEFT, ReportVAlignment.CENTER, ReportFontType.BOLD);

		// Header component added
		layout.addComponent(logoBox, headerBox, codeDateBox, titleBox, sideTitleBox);

		/*
		 * Side Header
		 */

		float shFontSize = 14.0f;
		ReportTextBox attnBox = new ReportTextBox("attnBox", 12.7f, 89.2f, 35f, 17.2f, "ATTN: " + form.getAttn(), shFontSize,
				ReportHAlignment.CENTER, ReportVAlignment.CENTER, ReportFontType.BOLD, base);
		ReportTextBox crBox = new ReportTextBox("crBox", 59.3f, 89.2f, 34.4f, 17.2f, "CR: " + form.getCr(), shFontSize,
				ReportHAlignment.CENTER, ReportVAlignment.CENTER, ReportFontType.BOLD, base);
		
		// Side header component added
		layout.addComponent(attnBox, crBox);

		/*
		 * Customer Header
		 */
		
		Customer customer = form.getCustomer();
		String customerString = "เลขประจำตัวผู้เสียภาษีอากร\n" + customer.getTaxID() + "\n \nข้อมูลลูกค้า\n"
				+ customer.getName() + "\n" + customer.getAddress() + "\nTel: " + customer.getTel() + "\nFax: "
				+ customer.getFax();
		
		ReportRectBox customerRectBox = new ReportRectBox("customerRectBox", 128.1f, 38.3f, 69.1f, 68f, base, null);
		ReportTextBox customerBox = new ReportTextBox("customerBox", 134f, 42.4f, 60f, 46.7f, customerString, 13f,
				ReportHAlignment.LEFT, ReportVAlignment.TOP, ReportFontType.BOLD);
		
		// Customer header component added
		layout.addComponent(customerRectBox, customerBox);

		/*
		 * List
		 */
		
		ReportTable<Item> itemTable = new ReportTable<>("itemTable", 12.7f, 114.6f, 184.4f, 109.7f);
		itemTable.setHeaderBackgroundColor(base);
		
		ReportTableColumn<Item, Integer> noColumn = new ReportTableColumn<>("noColumn", 6.1f, "AUTO_NUMBER");
		noColumn.setHeaderString("NO");
		ReportTableColumn<Item, String> descriptionColumn = new ReportTableColumn<>("descriptionColumn", 75f, "product/description");
		descriptionColumn.setHeaderString("DESCRIPTION");
		descriptionColumn.setAlignment(ReportHAlignment.LEFT);
		ReportTableColumn<Item, Integer> quantityColumn = new ReportTableColumn<>("quantityColumn", 23.3f, "itemQuantity");
		quantityColumn.setHeaderString("QUANTITY");
		quantityColumn.setFormatter("%,d");
		ReportTableColumn<Item, Integer> unitColumn = new ReportTableColumn<>("unitColumn", 11.4f, "product/unit");
		unitColumn.setHeaderString("UNIT");
		ReportTableColumn<Item, Double> priceColumn = new ReportTableColumn<>("priceColumn", 23.1f, "product/price");
		priceColumn.setHeaderString("PRICE");
		priceColumn.setFormatter("%,.2f");
		ReportTableColumn<Item, Double> discountColumn = new ReportTableColumn<>("discountColumn", 23.1f, "discount");
		discountColumn.setHeaderString("DISCOUNT");
		discountColumn.setFormatter("%.0f%%");
		ReportTableColumn<Item, Double> amountColumn = new ReportTableColumn<>("amountColumn", 23.1f, "amount");
		amountColumn.setHeaderString("AMOUNT");
		amountColumn.setAlignment(ReportHAlignment.RIGHT);
		amountColumn.setFormatter("%,.2f");
		
		itemTable.addColumn(noColumn, descriptionColumn, quantityColumn, unitColumn, priceColumn, discountColumn, amountColumn);
		
		
		ArrayList<Item> itemList = form.getItemList();
		
		for (int i = 0; i < itemList.size(); i++) {
			
			itemTable.addContent(itemList.get(i));

		}
		
		layout.addComponent(itemTable);

		/*
		 * Footer
		 */
		
		

		/*
		 * Signature
		 */
		
		float signatureFontSize = 14.0f;
		ReportTextBox buyerSignature = new ReportTextBox("buyerSignature", 12.8f, 258.1f, 93.1f, 27f,
				"ลงชื่อ.............................................\nผู้ขอซื้อ\nวันที่....../....../......",
				signatureFontSize, ReportHAlignment.CENTER, ReportVAlignment.CENTER, ReportFontType.BOLD);
		
		ReportTextBox sellerSignature = new ReportTextBox("buyerSignature", 105.9f, 258.1f, 91.2f, 27f,
				"ลงชื่อ.............................................\nพนักงานขาย\nวันที่....../....../......",
				signatureFontSize, ReportHAlignment.CENTER, ReportVAlignment.CENTER, ReportFontType.BOLD);
		
		// Signature component added
		layout.addComponent(buyerSignature, sellerSignature);

		/*
		 * DrawLine
		 */
		// Vertical

		// Horizontal
		
		return layout;
		
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		String dest = "C:\\Users\\Kirk Pig\\Desktop\\PdfTest\\sample.pdf";
		ArrayList<Item> itemList = new ArrayList<>();
		itemList.add(new Item(new Product("TRI-1235", "Product Test 1", "Piece", 157.2, 1), 1000, 35));
		itemList.add(new Item(new Product("COM-5623", "Product Test 2", "Set", 63.52, 35), 45, 22));
		Customer customer = new Customer("525", "Pig Co.LTD", "546546-55454515",
				"103/314 M.5 T.Phanthai Norasing, A.Muang Samut Sakhon, Samut Sakhon 74000", "090-841-5626",
				"02-4546455", "yourname@address.com");
		String date = "10-08-2563";
		User user = new User("kirkpig", "postitpaper", "KirkPig");
		
		Invoice invoice = new Invoice("YN630008123", date, customer, itemList, "PO63008123", "Piggy", "CASH", date,
				"Pig", user.getName());
		Order order = new Order("PO63008123", date, customer, itemList, "CASH", user.getName());
		Delivery delivery = new Delivery("DE63008123", date, customer, itemList, "Pig", user.getName());
		ProductLoan productLoan = new ProductLoan("BL63008123", date, customer, itemList, "Pig", user.getName());
		CreditNote creditNote = new CreditNote("CR63008123", date, customer, invoice, 100000.00, user.getName());
		Quotation quotation = new Quotation("QY63008123", date, customer, itemList, "5545", "CASH", user.getName());

		ArrayList<Invoice> invoiceList = new ArrayList<>();
		ArrayList<String> psList = new ArrayList<>();
		invoiceList.add(invoice);
		psList.add("reserve");
		Billing billing = new Billing("RB63008123", date, customer, invoiceList, psList, "Piggy", date,
				"สำหรับการทดลองเพียงเท่านั้น",user.getName());
		
		
		try {
			
			PDDocument document = new PDDocument();
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			
			PDPageContentStream cs = new PDPageContentStream(document, page);
			ReportLayout layout = getQuotationLayoutTemplate(quotation);
			
			for(ReportComponent rc: layout.getComponentList()) {
				
				rc.addOnReport(document, cs);
				
			}
			
			cs.close();
			document.save(dest);
			document.close();
			
			Desktop.getDesktop().open(new File(dest));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		System.out.println("PDF has been created");
		
	}

}
