package bill;

import logic.Product;

public class Item extends Product{
	
	private int itemQuantity;
	private double discount;
	private double amount;
	private Product product;
	public Item(Product product,int itemQuantity,double discount) {
		// TODO Auto-generated constructor stub
		super(product.getCode(),product.getDescription(),product.getUnit(),product.getPrice(),product.getQuantity());
		this.setProduct(product);
		this.setDiscount(discount);
		this.setItemQuantity(itemQuantity);
		this.setAmount();
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

	public void setAmount() {
		this.amount = this.getProduct().getPrice() * this.getItemQuantity() * (1- this.getDiscount()/100);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


	public int getItemQuantity() {
		return itemQuantity;
	}


	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	
	
	

	
}
