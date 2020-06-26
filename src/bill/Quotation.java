package bill;
import bill.Item;
import logic.Customer;

public class Quotation {
		
	private String id;
	private String date;
	private Customer customer;
	private Item[] itemList; 
	private String attn;
	private String cr;
	private double totalAmount;
	private double tax7;
	private double plusTax7;
	
	public Quotation(String id,String date,Customer customer,Item[] itemList,String attn,String cr) {
		this.setAttn(attn);
		this.setCr(cr);
		this.setCustomer(customer);
		this.setDate(date);
		this.setId(id);
		this.setItemList(itemList);
		double total = 0.00;
		for (Item e: itemList){
			total += e.getAmount();
		this.setTotalAmount(total);
		this.setTax7(total*7/100);
		this.setPlusTax7(this.getTotalAmount() + this.getTax7());
		}
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCr() {
		return cr;
	}

	public void setCr(String cr) {
		this.cr = cr;
	}

	public Item[] getItemList() {
		return itemList;
	}

	public void setItemList(Item[] itemList) {
		this.itemList = itemList;
	}

	public String getAttn() {
		return attn;
	}

	public void setAttn(String attn) {
		this.attn = attn;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getTax7() {
		return tax7;
	}

	public void setTax7(double tax7) {
		this.tax7 = tax7;
	}

	public double getPlusTax7() {
		return plusTax7;
	}

	public void setPlusTax7(double plusTax7) {
		this.plusTax7 = plusTax7;
	}
}
