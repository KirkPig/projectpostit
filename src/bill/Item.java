package bill;

import logic.Product;

public class Item {
	
	private int quantity;
	private double discount;
	private double amount;
	private Product product;
	public Item(Product product,int quantity,double discount) {
		// TODO Auto-generated constructor stub
		this.setProduct(product);
		this.setDiscount(discount);
		this.setQuantity(quantity);
		this.setAmount(this.getProduct().getPrice() * this.getQuantity() * (1- this.getDiscount()/100));
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	

	
}
