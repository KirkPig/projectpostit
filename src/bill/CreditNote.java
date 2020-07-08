package bill;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
	private String creator;
	private String productName;
	private String productQuantity;
	private String productUnit;
	private String valueAfterTaxForTable;
	private String customerName;
	private String invoiceID;
	private String invoiceAmount;
	private String valueOldForTable;

	public CreditNote(String id, String date, Customer customer, Invoice invoice, double valueReal,String creator) {
		this.setId(id);
		this.setDate(date);
		this.setCustomer(customer);
		this.setInvoice(invoice);
		this.setValueOld(invoice.getValueAfterTax());
		this.setValueReal(valueReal);
		this.setValueBeforeTax(valueOld - valueReal);
		this.setValueTax(valueBeforeTax * 7 / 100);
		this.setValueAfterTax(this.getValueBeforeTax() + this.getValueTax());
		
		this.setProductName(invoice.getItemList());
		this.setProductQuantity(invoice.getItemList());
		this.setProductUnit(invoice.getItemList());
		this.setValueAfterTaxForTable(this.getValueBeforeTax() + this.getValueTax());
		this.setCustomerName(customer.getName());
		this.setCreator(creator);
		this.setInvoiceAmount(invoice.getValueAfterTaxForTable());
		this.setInvoiceID(invoice.getId());
		this.setValueOldForTable(this.getValueOld());
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(ArrayList<Item> itemList) {
		this.productName = "";
		for (Item item : itemList) {
			this.productName += item.getProduct().getDescription() + "\n";
			
		}
		
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(ArrayList<Item> itemList) {
		this.productUnit = "";
		for (Item item:itemList) {
			this.productUnit += item.getUnit() + "\n";
		}
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(ArrayList<Item> itemList) {
		this.productQuantity = "";
		for (Item item:itemList) {
			this.productQuantity += Integer.toString(item.getItemQuantity())+ "\n";
		}
	}
	public String getValueAfterTaxForTable() {
		return valueAfterTaxForTable;
	}

	public void setValueAfterTaxForTable(double value) {
		DecimalFormat df = new DecimalFormat("#.##");
		this.valueAfterTaxForTable = df.format(value);
	}

	public String getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}

	public String getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getValueOldForTable() {
		return valueOldForTable;
	}

	public void setValueOldForTable(double valueOldForTable) {
		DecimalFormat df = new DecimalFormat("#.##");
		this.valueOldForTable = df.format(valueOldForTable);
	}
}
