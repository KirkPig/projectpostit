package logic.report.layout;

import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import logic.report.component.base.ReportComponent;

public class ReportLayout {
	
	private String name;
	private ArrayList<ReportComponent> componentList = new ArrayList<>();
	
	public ReportLayout(String name) {
		
		this.setName(name);
		
	}
	
	public void addComponent(ReportComponent... component) {
		
		for (int i = 0; i < component.length; i++) {
			componentList.add(component[i]);
		}
		
	}
	
	public void makeReport(PDDocument document, PDPageContentStream cs) throws Exception {
		
		for(ReportComponent rc: componentList) {
			
			rc.addOnReport(document, cs);
			
		}
		
	}
	
	public ArrayList<ReportComponent> getComponentList() {
		return componentList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
