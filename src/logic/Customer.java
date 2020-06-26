package logic;

public class Customer {
	
	private String code;
	private String name;
	private String taxID;
	private String address;
	private String tel;
	private String fax;
	private String email;
	
	public Customer(String code, String name, String taxID, String address, String tel, String fax, String email) {
		this.setCode(code);
		this.setName(name);
		this.setTaxID(taxID);
		this.setAddress(address);
		this.setTel(tel);
		this.setFax(fax);
		this.setEmail(email);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTaxID() {
		return taxID;
	}

	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
