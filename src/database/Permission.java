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
		this.QY = QY;
		this.OR = OR;
		this.PO = PO;
		this.CR = CR;
		this.DE = DE;
		this.IV = IV;
		this.BL = BL;
		this.database = database;
		
	}
}
