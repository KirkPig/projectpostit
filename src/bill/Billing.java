package bill;

import java.text.DecimalFormat;
import java.util.ArrayList;

import logic.Customer;

public class Billing {

	private String id;
	private String date;
	private Customer customer;
	private ArrayList<Invoice> invoiceList;
	private ArrayList<String> psList;
	private String billingBy;
	private String billingDate;
	private String ps;
	private double value;
	private String invoiceIdForTable = "";
	private String psForTable = "";
	private String amountForTable = "";
	private String customerName;
	private String creator;
	
	public Billing(String id, String date, Customer customer, ArrayList<Invoice> invoiceList, ArrayList<String> psList, String billingBy,
			String billingDate, String ps,String creator) {
		// TODO Auto-generated constructor stub
		this.setCustomer(customer);
		this.setDate(date);
		this.setId(id);
		this.setBillingBy(billingBy);
		this.setBillingDate(billingDate);
		this.setPs(ps);
		this.setInvoiceList(invoiceList);
		this.setPsList(psList);
		double total = 0.00;
		for(Invoice i : invoiceList) {
			total = total + i.getValueAfterTax();
			
		}
		this.setValue(total);
		for (Integer i = 0 ; i<invoiceList.size() ;i++) {
			invoiceList.get(i).setPs(psList.get(i));
		}
		this.setInvoiceIdForTable(invoiceList);
		this.setPsForTable(psList);
		this.setAmountForTable(invoiceList);
		this.setCustomerName(customer);
		this.setCreator(creator);
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
		DecimalFormat df = new DecimalFormat("#.##");
		this.value = Double.parseDouble(df.format(value));
	}

	public ArrayList<Invoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(ArrayList<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public ArrayList<String> getPsList() {
		return psList;
	}

	public void setPsList(ArrayList<String> psList) {
		this.psList = psList;
	}

	public String getInvoiceIdForTable() {
		return invoiceIdForTable;
	}

	public void setInvoiceIdForTable(ArrayList<Invoice> invoiceList) {
		for (Invoice invoice: invoiceList) {
			this.invoiceIdForTable += invoice.getId() + "\n";
		}
	}

	public String getPsForTable() {
		return psForTable;
	}

	public void setPsForTable(ArrayList<String> psList) {
		for (String e : psList) {
			this.psForTable += e + "\n";
		}
	}

	public String getAmountForTable() {
		return amountForTable;
	}

	public void setAmountForTable(ArrayList<Invoice> invoiceList) {
		for (Invoice invoice: invoiceList) {
			this.amountForTable += invoice.getValueAfterTaxForTable();
		}
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(Customer customerName) {
		this.customerName = customerName.getName();
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

}
