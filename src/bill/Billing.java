package bill;

import java.text.DecimalFormat;
import java.util.ArrayList;

import logic.Customer;

public class Billing implements Comparable<Billing>{

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
	private String valueForTable;
	private double total = 0.00;
	
	public Billing(String id, String date, Customer customer, ArrayList<Invoice> invoiceList, ArrayList<String> psList, String billingBy,
			String billingDate, String ps,String creator) {
		this.setCustomer(customer);
		this.setDate(date);
		this.setId(id);
		this.setBillingBy(billingBy);
		this.setBillingDate(billingDate);
		this.setPs(ps);
		this.setInvoiceList(invoiceList);
		this.setPsList(psList);
		
		for(Invoice i : invoiceList) {
			total = total + i.getValueAfterTax();
			
		}
		this.setValue(onlyTwoDecimalPlaces(Double.toString(total)));
		for (Integer i = 0 ; i<invoiceList.size() ;i++) {
			invoiceList.get(i).setPs(psList.get(i));
		}
		this.setInvoiceIdForTable(invoiceList);
		this.setPsForTable(psList);
		this.setAmountForTable(invoiceList);
		this.setCustomerName(customer);
		this.setCreator(creator);
		this.setValueForTable(total);
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
		DecimalFormat df = new DecimalFormat("#,###.##");

		this.value = Double.parseDouble(df.format(value+0.00).replace(",", ""));
;
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

	@Override
	public int compareTo(Billing o) {
		return this.getDate().compareTo(o.getDate());
	}

	public String getValueForTable() {
		return valueForTable;
	}

	public void setValueForTable(double value) {
		DecimalFormat df = new DecimalFormat("#,###.##");
		this.valueForTable = df.format(value);
	}
	public double onlyTwoDecimalPlaces(String number) {
        StringBuilder sbFloat = new StringBuilder(number);
        int start = sbFloat.indexOf(".");
        if (start < 0) {
            return Double.parseDouble(sbFloat.toString());
        }
        int end = start+3;
        if((end)>(sbFloat.length()-1)) end = sbFloat.length();

        String twoPlaces = sbFloat.substring(start, end);
        sbFloat.replace(start, sbFloat.length(), twoPlaces);
        return Double.parseDouble(sbFloat.toString());
    }
}
