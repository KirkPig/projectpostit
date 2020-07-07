package logic.report.component;

import java.awt.Color;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import logic.report.base.ReportUtil;
import logic.report.component.base.ReportComponent;

public class ReportRectBox extends ReportComponent{
	
	private Color colorFill;
	private Color colorStroke;

	public ReportRectBox(String name, float x, float y, float width, float height, Color colorFill, Color colorStroke) {
		
		super(name, x, y, width, height);
		this.setColorFill(colorFill);
		this.setColorStroke(colorStroke);
		
	}

	@Override
	public void addOnReport(PDDocument document, PDPageContentStream cs) throws Exception {

		if(colorFill != null) {
			
			ReportUtil.addFillRectBox(document, cs, getX(), getY(), getWidth(), getHeight(), getColorFill());
			
		}
		
		if(colorStroke != null) {
			
			ReportUtil.addStrokeRectBox(document, cs, getX(), getY(), getWidth(), getHeight(), getColorStroke());
			
		}
		
	}

	public Color getColorFill() {
		return colorFill;
	}

	public void setColorFill(Color colorFill) {
		this.colorFill = colorFill;
	}

	public Color getColorStroke() {
		return colorStroke;
	}

	public void setColorStroke(Color colorStroke) {
		this.colorStroke = colorStroke;
	}

}
