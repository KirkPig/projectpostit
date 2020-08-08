package logic;

import java.text.DecimalFormat;

public class Product {
	
	private String code;
	private String description;
	private String unit;
	private double price;
	private int quantity;
	private String priceForTable;
	public Product(String code, String description,String unit, double price, int quantity) {
		this.setCode(code);
		this.setDescription(description);
		this.setPrice(price);
		this.setUnit(unit);
		this.setQuantity(quantity);
		this.setPriceForTable(price);
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getPriceForTable() {
		return priceForTable;
	}


	public void setPriceForTable(double price) {
		DecimalFormat df = new DecimalFormat("#,###.##");
		this.priceForTable = df.format(price);
	}
}
