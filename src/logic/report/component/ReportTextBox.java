package logic.report.component;

import java.awt.Color;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import logic.report.base.ReportFontType;
import logic.report.base.ReportHAlignment;
import logic.report.base.ReportUtil;
import logic.report.base.ReportVAlignment;
import logic.report.component.base.ReportComponent;

public class ReportTextBox extends ReportComponent {

	private String text;
	private float fontSize;
	private ReportHAlignment hAlignment;
	private ReportVAlignment vAlignment;
	private ReportFontType fontType;
	private Color colorFill = null;
	private Color colorStroke = null;

	public ReportTextBox(String name, float x, float y, float width, float height, String text, float fontSize,
			ReportHAlignment hAlignment, ReportVAlignment vAlignment, ReportFontType fontType) {
		super(name, x, y, width, height);
		this.setText(text);
		this.setFontSize(fontSize);
		this.sethAlignment(hAlignment);
		this.setvAlignment(vAlignment);
		this.setFontType(fontType);
	}
	
	public ReportTextBox(String name, float x, float y, float width, float height, String text, float fontSize,
			ReportHAlignment hAlignment, ReportVAlignment vAlignment, ReportFontType fontType, Color colorFill) {
		super(name, x, y, width, height);
		this.setText(text);
		this.setFontSize(fontSize);
		this.sethAlignment(hAlignment);
		this.setvAlignment(vAlignment);
		this.setFontType(fontType);
		this.setColorFill(colorFill);
	}
	
	public ReportTextBox(String name, float x, float y, float width, float height, String text, float fontSize,
			ReportHAlignment hAlignment, ReportVAlignment vAlignment, ReportFontType fontType, Color colorFill, Color colorStroke) {
		super(name, x, y, width, height);
		this.setText(text);
		this.setFontSize(fontSize);
		this.sethAlignment(hAlignment);
		this.setvAlignment(vAlignment);
		this.setFontType(fontType);
		this.setColorFill(colorFill);
		this.setColorStroke(colorStroke);
	}

	@Override
	public void addOnReport(PDDocument document, PDPageContentStream cs) throws Exception {

		if (colorFill != null) {

			ReportUtil.addFillRectBox(document, cs, getX(), getY(), getWidth(), getHeight(), getColorFill());

		}

		if (colorStroke != null) {

			ReportUtil.addStrokeRectBox(document, cs, getX(), getY(), getWidth(), getHeight(), getColorStroke());

		}

		ReportUtil.addParagraph(document, cs, getText(), getFontSize(), getX(), getY(), getWidth(), getHeight(),
				gethAlignment(), getvAlignment(), getFontType());

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public ReportHAlignment gethAlignment() {
		return hAlignment;
	}

	public void sethAlignment(ReportHAlignment hAlignment) {
		this.hAlignment = hAlignment;
	}

	public ReportVAlignment getvAlignment() {
		return vAlignment;
	}

	public void setvAlignment(ReportVAlignment vAlignment) {
		this.vAlignment = vAlignment;
	}

	public ReportFontType getFontType() {
		return fontType;
	}

	public void setFontType(ReportFontType fontType) {
		this.fontType = fontType;
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
