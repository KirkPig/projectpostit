package logic.report.layout;

import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import logic.report.component.base.ReportComponent;

public abstract class ReportLayout {
	
	private String name;
	private ArrayList<ReportComponent> componentList = new ArrayList<>();
	
	public ReportLayout(String name) {
		
		this.setName(name);
		
	}
	
	public void addComponent(ReportComponent component) {
		
		componentList.add(component);
		
	}
	
	public void makeReport(PDDocument document, PDPageContentStream cs) throws Exception {
		
		for(ReportComponent rc: componentList) {
			
			rc.addOnReport(document, cs);
			
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}