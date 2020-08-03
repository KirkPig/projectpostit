package database;

public class Permission {
	
	private boolean QY = true ;
	private boolean OR = true;
	private boolean PO = true;
	private boolean CR = true;
	private boolean DE = true;
	private boolean IV = true;
	private boolean BL = true;
	private boolean database = true;
	
	
	public Permission (boolean QY ,boolean OR ,boolean PO ,boolean CR ,boolean DE ,boolean IV ,boolean BL ,boolean database) {
		this.setQY(QY);
		this.setOR(OR);
		this.setPO(PO);
		this.setCR(CR);
		this.setDE(DE);
		this.setIV(IV);
		this.setBL(BL);
		this.setDatabase(database);
	}


	public boolean isQY() {
		return QY;
	}


	public void setQY(boolean qY) {
		QY = qY;
	}


	public boolean isOR() {
		return OR;
	}


	public void setOR(boolean oR) {
		OR = oR;
	}


	public boolean isPO() {
		return PO;
	}


	public void setPO(boolean pO) {
		PO = pO;
	}


	public boolean isCR() {
		return CR;
	}


	public void setCR(boolean cR) {
		CR = cR;
	}


	public boolean isDE() {
		return DE;
	}


	public void setDE(boolean dE) {
		DE = dE;
	}


	public boolean isIV() {
		return IV;
	}


	public void setIV(boolean iV) {
		IV = iV;
	}


	public boolean isBL() {
		return BL;
	}


	public void setBL(boolean bL) {
		BL = bL;
	}


	public boolean isDatabase() {
		return database;
	}


	public void setDatabase(boolean database) {
		this.database = database;
	}
}
