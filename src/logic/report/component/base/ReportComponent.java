package logic.report.component.base;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public abstract class ReportComponent {
	
	private String name;
	private float x;
	private float y;
	private float width;
	private float height;
	
	public ReportComponent(String name, float x, float y, float width, float height) {

		this.setName(name);
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		
	}
	
	public abstract void addOnReport(PDDocument document, PDPageContentStream cs) throws Exception;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

}
