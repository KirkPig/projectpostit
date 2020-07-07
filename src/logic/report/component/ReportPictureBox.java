package logic.report.component;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import logic.report.base.ReportUtil;
import logic.report.component.base.ReportComponent;

public class ReportPictureBox extends ReportComponent {
	
	private String picPath;

	public ReportPictureBox(String name, float x, float y, float width, float height, String picPath) {
		super(name, x, y, width, height);
		this.setPicPath(picPath);
	}

	@Override
	public void addOnReport(PDDocument document, PDPageContentStream cs) throws Exception {

		ReportUtil.addPicture(document, cs, picPath, getX(), getY(), getWidth(), getHeight());
		
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

}
