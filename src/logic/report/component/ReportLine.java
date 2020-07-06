package logic.report.component;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import logic.report.base.ReportUtil;
import logic.report.component.base.ReportComponent;

public class ReportLine extends ReportComponent {
	
	private float x2, y2;

	public ReportLine(String name, float x1, float y1, float x2, float y2) {
		super(name, x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
		// TODO Auto-generated constructor stub
		this.setX2(x2);
		this.setY2(y2);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addOnReport(PDDocument document, PDPageContentStream cs) throws Exception {
		// TODO Auto-generated method stub
		
		cs.drawLine(ReportUtil.cpx(getX()), ReportUtil.cpy(getY()), ReportUtil.cpx(getX2()), ReportUtil.cpy(getY2()));
		
	}

	public float getX2() {
		return x2;
	}

	public void setX2(float x2) {
		this.x2 = x2;
		setWidth(Math.abs(x2 - getX()));
	}

	public float getY2() {
		return y2;
	}

	public void setY2(float y2) {
		this.y2 = y2;
		setHeight(Math.abs(y2 - getY()));
	}
	
	@Override
	public void setX(float x) {
		// TODO Auto-generated method stub
		super.setX(x);
		setWidth(Math.abs(x2 - x));
	}
	
	@Override
	public void setY(float y) {
		// TODO Auto-generated method stub
		super.setY(y);
		setHeight(Math.abs(y2 - y));
	}

}
