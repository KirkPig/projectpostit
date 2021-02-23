package bill;

public class Product {
	private String code;
	private String date;
	private String user;
	private int product_initial;
	private int product_change;
	private int product_total;
	
	public Product(String code,String date,String user,int product_initial,int product_change,int product_total)
	{
		this.setCode(code);
		this.setDate(date);
		this.setUser(user);
		this.setProduct_initial(product_initial);
		this.setProduct_change(product_change);
		this.setProduct_total(product_total);
		

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getProduct_initial() {
		return product_initial;
	}

	public void setProduct_initial(int product_initial) {
		this.product_initial = product_initial;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getProduct_change() {
		return product_change;
	}

	public void setProduct_change(int product_change) {
		this.product_change = product_change;
	}

	public int getProduct_total() {
		return product_total;
	}

	public void setProduct_total(int product_total) {
		this.product_total = product_total;
	}
	
	
	
}
