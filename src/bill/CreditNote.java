package bill;

import logic.Customer;

public class CreditNote {
	private String description;
	private double valueOld;
	private double valueReal;
	private double valueBefore;
	private double totalAmount;
	private double tax7;
	private double plusTax7;
	private String id;
	private String date;
	private Customer customer;
	
	public CreditNote(String id,String date,Customer customer,double valueOld,double valueReal) {
		this.setId(id);
		this.setDate(date);
		this.setCustomer(customer);
		this.setValueOld(valueOld);
		this.setValueReal(valueReal);
		this.setValueBefore(valueOld-valueReal);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getValueOld() {
		return valueOld;
	}

	public void setValueOld(double valueOld) {
		this.valueOld = valueOld;
	}

	public double getValueReal() {
		return valueReal;
	}

	public void setValueReal(double valueReal) {
		this.valueReal = valueReal;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getValueBefore() {
		return valueBefore;
	}

	public void setValueBefore(double valueBefore) {
		this.valueBefore = valueBefore;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
}
