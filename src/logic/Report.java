package logic;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import bill.Item;
import bill.Quotation;

public class Report {

	private static final float DPI = 96.0f;
	private static final float MMPI = 25.4f;

	private static float cpx(float x) {
		return (x / MMPI * DPI);
	}

	private static float cpy(float y) {
		return 1123.0f - (y / MMPI * DPI);
	}

	private static void setupParagraph() {

	
	}

	public static void printQuotation(Quotation form) throws Exception {

		// Creating a PdfWriter
		String dest = "C:/Users/Kirk Pig/Desktop/PdfTest/sample.pdf";
		
		System.out.println("PDF Created");

	}

	public static void main(String[] args) {

		try {
			ArrayList<Item> a = new ArrayList<>();
			a.add(new Item(new Product("TRI-1235", "Product Test 1", "Piece", 157.2), 45, 35));
			a.add(new Item(new Product("COM-5623", "Product Test 2", "Set", 63.52), 45, 22));
			Quotation test = new Quotation("100", "10-10-2025", new Customer("525", "Pig Co.LTD", "546546-55454515",
					"35 SamuthSakhon, jaskdklkdf, afjls, 12032", "090-841-5626", "02-4546455", "yourname@address.com"),
					a, "5545", "4562");
			printQuotation(test);
			Desktop.getDesktop().open(new File("C:/Users/Kirk Pig/Desktop/PdfTest/sample.pdf"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
