package bill;

import java.text.DecimalFormat;
import java.util.ArrayList;

import logic.Customer;

public class Invoice implements Comparable<Invoice>{

	private String id;
	private String date;
	private Customer customer;
	private String poNum;
	private String orderBy;
	private String paymentTerm;
	private String dateDue;
	private String sales;
	private ArrayList<Item> itemList;
	private double valueBeforeTax;
	private double valueTax;
	private double valueAfterTax;
	private String creator;
	private String productName;
	private String productQuantity;
	private String productUnit;
	private String valueAfterTaxForTable;
	private String customerName;
	private boolean select;
	private String ps;

	public Invoice(String id, String date, Customer customer, ArrayList<Item> itemList, String poNum, String orderBy,
			String paymentTerm, String dateDue, String sales, String creator) {
		this.setCustomer(customer);
		this.setDate(date);
		this.setId(id);
		this.setItemList(itemList);
		this.setPoNum(poNum);
		this.setOrderBy(orderBy);
		this.setPaymentTerm(paymentTerm);
		this.setDateDue(dateDue);
		this.setSales(sales);
		double total = 0.00;
		for (Item e : itemList) {
			total += e.getAmount();
		}
		this.setValueBeforeTax(total);
		this.setValueTax(total * 7 / 100);
		this.setValueTax(onlyTwoDecimalPlaces(Double.toString(getValueTax())));
		this.setValueAfterTax(getValueBeforeTax() + getValueTax());
		this.setProductName(itemList);
		this.setProductQuantity(itemList);
		this.setProductUnit(itemList);
		this.setValueAfterTaxForTable(getValueAfterTax());
		this.setCustomerName(customer.getName());
		this.setCreator(creator);
		this.select = false;
		this.setPs("");
		
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

	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
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
		DecimalFormat df = new DecimalFormat("#,###.##");
		this.valueTax = Double.parseDouble(df.format(valueTax+0.00).replace(",", ""));
	}

	public double getValueAfterTax() {
		return valueAfterTax;
	}

	public void setValueAfterTax(double valueAfterTax) {
		DecimalFormat df = new DecimalFormat("#,###.##");
		this.valueAfterTax = Double.parseDouble(df.format(valueAfterTax).toString().replace(",", ""));
	}

	public String getPoNum() {
		return poNum;
	}

	public void setPoNum(String poNum) {
		this.poNum = poNum;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getDateDue() {
		return dateDue;
	}

	public void setDateDue(String dateDue) {
		this.dateDue = dateDue;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(ArrayList<Item> itemList) {
		this.productQuantity = "";
		for (Item item : itemList) {
			this.productQuantity += Integer.toString(item.getItemQuantity()) + "\n";
		}
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(ArrayList<Item> itemList) {
		this.productUnit = "";
		for (Item item : itemList) {
			this.productUnit += item.getUnit() + "\n";
		}
	}

	public String getValueAfterTaxForTable() {
		return valueAfterTaxForTable;
	}

	public void setValueAfterTaxForTable(double value) {
		DecimalFormat df = new DecimalFormat("#,###.##");
		this.valueAfterTaxForTable = df.format(value);
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public boolean getSelect() {
		return select;
	}

	public void setSelect(boolean a) {
		this.select = a;
	}
	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	@Override
	public int compareTo(Invoice o) {
		// TODO Auto-generated method stub
		return this.getDate().compareTo(o.getDate());
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
