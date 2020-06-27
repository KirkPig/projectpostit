package logic;

public class Product {
	
	private String code;
	private String description;
	private String unit;
	private double price;
	
	
	public Product(String code, String description,String unit, double price) {
		this.setCode(code);
		this.setDescription(description);
		this.setPrice(price);
		this.setUnit(unit);
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
}
