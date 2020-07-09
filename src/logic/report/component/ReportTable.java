package logic.report.component;

import java.awt.Color;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import logic.report.base.ReportFontType;
import logic.report.base.ReportHAlignment;
import logic.report.base.ReportVAlignment;
import logic.report.component.base.ReportComponent;

public class ReportTable<S> extends ReportComponent {
	
	private ArrayList<S> contentList = new ArrayList<>();
	private ArrayList<ReportTableColumn<S, ?>> columnList = new ArrayList<>();
	
	private float headerRowHeight = 8.7f;
	private Color headerBackgroundColor = Color.WHITE;
	private boolean isShowHeader = true;
	private float headerFontSize = 12f;
	private ReportFontType headerFontType = ReportFontType.BOLD;
	
	private float rowHeight = 6f;
	private Color tableBackgroundColor = Color.WHITE;
	private float fontSize = 12f;
	private ReportFontType fontType = ReportFontType.REGULAR;

	public ReportTable(String name, float x, float y, float width, float height) {
		super(name, x, y, width, height);
	}

	@Override
	public void addOnReport(PDDocument document, PDPageContentStream cs) throws Exception {
		
		float x = getX();
		float y = getY();
		
		if(isShowHeader) {
			
			for (int i = 0; i < columnList.size(); i++) {
				
				ReportTableColumn<S, ?> column = columnList.get(i);
				float columnWidth = column.getWidth();
				
				ReportTextBox textBox = new ReportTextBox("", x, y, columnWidth, headerRowHeight,
						column.getHeaderString(), headerFontSize, ReportHAlignment.CENTER, ReportVAlignment.CENTER,
						headerFontType, headerBackgroundColor);
				textBox.addOnReport(document, cs);

				x = x + columnWidth;

			}
			
			y = y + headerRowHeight;
			
		}
		
		for (int i = 0; i < contentList.size(); i++) {
			
			x = getX();
			S item = contentList.get(i);
			
			for (int j = 0; j < columnList.size(); j++) {
				
				ReportTableColumn<S, ?> column = columnList.get(j);
				float columnWidth = column.getWidth();
				String k;
				
				if(column.getItemField().equals("AUTO_NUMBER")) {
					k = Integer.toString(i + 1);
				}else {
					k = column.getStringValue(item);
				}
				
				
				ReportTextBox textBox = new ReportTextBox("", x, y, columnWidth, rowHeight,
						k, fontSize, column.getAlignment(), ReportVAlignment.CENTER,
						fontType, tableBackgroundColor);
				textBox.addOnReport(document, cs);

				x = x + columnWidth;

			}
			
			y = y + rowHeight;

		}
		
		
		
	}

	public boolean isShowHeader() {
		return isShowHeader;
	}

	public void setShowHeader(boolean isShowHeader) {
		this.isShowHeader = isShowHeader;
	}

	public ArrayList<S> getContentList() {
		return contentList;
	}

	@SuppressWarnings("unchecked")
	public void addContent(S... content) {

		for(int i = 0;i<content.length;i++) {
			
			contentList.add(content[i]);
			
		}
		
	}

	public float getRowHeight() {
		return rowHeight;
	}

	public void setRowHeight(float rowHeight) {
		this.rowHeight = rowHeight;
	}

	public Color getHeaderBackgroundColor() {
		return headerBackgroundColor;
	}

	public void setHeaderBackgroundColor(Color headerBackgroundColor) {
		this.headerBackgroundColor = headerBackgroundColor;
	}

	public Color getTableBackgroundColor() {
		return tableBackgroundColor;
	}

	public void setTableBackgroundColor(Color tableBackgroundColor) {
		this.tableBackgroundColor = tableBackgroundColor;
	}

	public float getHeaderRowHeight() {
		return headerRowHeight;
	}

	public void setHeaderRowHeight(float headerRowHeight) {
		this.headerRowHeight = headerRowHeight;
	}

	public ArrayList<ReportTableColumn<S, ?>> getColumnList() {
		return columnList;
	}

	@SuppressWarnings("unchecked")
	public void addColumn(ReportTableColumn<S, ?>... column) {
		for (int i = 0; i < column.length; i++) {

			this.columnList.add(column[i]);

		}
	}

	public float getHeaderFontSize() {
		return headerFontSize;
	}

	public void setHeaderFontSize(float headerFontSize) {
		this.headerFontSize = headerFontSize;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public ReportFontType getHeaderFontType() {
		return headerFontType;
	}

	public void setHeaderFontType(ReportFontType headerFontType) {
		this.headerFontType = headerFontType;
	}

	public ReportFontType getFontType() {
		return fontType;
	}

	public void setFontType(ReportFontType fontType) {
		this.fontType = fontType;
	}

}
