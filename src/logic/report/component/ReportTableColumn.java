package logic.report.component;

import java.lang.reflect.Field;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import logic.report.base.ReportHAlignment;
import logic.report.component.base.ReportComponent;

public class ReportTableColumn<S, T> extends ReportComponent {
	
	private String headerString = " ";
	private ReportHAlignment alignment = ReportHAlignment.CENTER;
	private String itemField;
	private String formatter = null;

	public ReportTableColumn(String name, float width, String itemField) {
		super(name, 0, 0, width, 0f);
		this.setHeaderString(name);
		this.setItemField(itemField);
	}

	@Override
	public void addOnReport(PDDocument document, PDPageContentStream cs) throws Exception {
		
	}
	
	@SuppressWarnings("unchecked")
	public T getValue(S item) {
		
		T s = null;

		try {
			
			String k = getItemField();
			Object object = item;
			
			while(k.contains("/")) {
				
				String m = "";
				
				for(int i = 0;i<k.length();i++) {
					
					if(k.charAt(i) == '/') {
						
						m = k.substring(0, i);
						k = k.substring(i + 1);
						break;
						
					}
			
				}
				
				Field field = object.getClass().getDeclaredField(m);
				field.setAccessible(true);
				object = field.get(object);
				
			}
			
			Field field = object.getClass().getDeclaredField(k);
			field.setAccessible(true);
			s = (T) field.get(object);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return s;

	}
	
	public String getStringValue(S item) {
		
		String s = "";
		
		if(formatter == null) {
			s = getValue(item).toString();
		}else {
			s = String.format(formatter, getValue(item));
		}
		
		return s;
		
	}

	public String getHeaderString() {
		return headerString;
	}

	public void setHeaderString(String headerString) {
		this.headerString = headerString;
	}

	public ReportHAlignment getAlignment() {
		return alignment;
	}

	public void setAlignment(ReportHAlignment alignment) {
		this.alignment = alignment;
	}

	public String getItemField() {
		return itemField;
	}

	public void setItemField(String itemField) {
		this.itemField = itemField;
	}
	
	public String getFormatter() {
		return formatter;
	}
	
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

}
