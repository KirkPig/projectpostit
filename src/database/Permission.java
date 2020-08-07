package database;

public class Permission {
	
	public boolean QY = true ;
	public boolean YN = true;
	public boolean PO = true;
	public boolean CR = true;
	public boolean DE = true;
	public boolean RB = true;
	public boolean BL = true;
	public boolean database = true;
	
	
	public Permission (boolean QY ,boolean YN ,boolean PO ,boolean CR ,boolean DE ,boolean RB ,boolean BL ,boolean database) {
		this.QY = QY;
		this.YN = YN;
		this.PO=PO;
		this.CR=CR;
		this.DE=DE;
		this.RB=RB;
		this.BL=BL;
		this.database=database;
	}


	
}
