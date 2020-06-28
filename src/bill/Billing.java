package bill;

import logic.Customer;

public class Billing {

	private String id;
	private String date;
	private Customer customer;
	private Invoice[] invoiceList;
	private String[] psList;
	private String billingBy;
	private String billingDate;
	private String ps;
	private double value;

	public Billing(String id, String date, Customer customer, Invoice[] invoiceList, String[] psList, String billingBy,
			String billingDate, String ps) {
		// TODO Auto-generated constructor stub
		this.setCustomer(customer);
		this.setDate(date);
		this.setId(id);
		this.setBillingBy(billingBy);
		this.setBillingDate(billingDate);
		this.setPs(ps);
		double total = 0.00;
		for(Invoice i : invoiceList) {
			total = total + i.getPlusTax7();
		}
		this.setValue(total);
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Invoice[] getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(Invoice[] invoiceList) {
		this.invoiceList = invoiceList;
	}

	public String[] getPsList() {
		return psList;
	}

	public void setPsList(String[] psList) {
		this.psList = psList;
	}

	public String getBillingBy() {
		return billingBy;
	}

	public void setBillingBy(String billingBy) {
		this.billingBy = billingBy;
	}

	public String getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
