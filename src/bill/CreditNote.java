package bill;

import logic.Customer;

public class CreditNote {
	
	private String id;
	private String date;
	private Customer customer;
	private Invoice invoice;
	private double valueOld;
	private double valueReal;
	private double valueBeforeTax;
	private double valueTax;
	private double valueAfterTax;

	public CreditNote(String id, String date, Customer customer, Invoice invoice, double valueReal) {
		this.setId(id);
		this.setDate(date);
		this.setCustomer(customer);
		this.setInvoice(invoice);
		this.setValueOld(invoice.getValueAfterTax());
		this.setValueReal(valueReal);
		this.setValueBeforeTax(valueOld - valueReal);
		this.setValueTax(valueBeforeTax * 7 / 100);
		this.setValueAfterTax(this.getValueBeforeTax() + this.getValueTax());
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

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public double getValueBeforeTax() {
		return valueBeforeTax;
	}

	public void setValueBeforeTax(double valueBeforeTax) {
		this.valueBeforeTax = valueBeforeTax;
	}

	public double getValueTax() {
		return valueTax;
	}

	public void setValueTax(double valueTax) {
		this.valueTax = valueTax;
	}

	public double getValueAfterTax() {
		return valueAfterTax;
	}

	public void setValueAfterTax(double valueAfterTax) {
		this.valueAfterTax = valueAfterTax;
	}
}
