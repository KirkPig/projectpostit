package logic.report.component;

import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import logic.report.component.base.ReportComponent;

public class ReportTableCustomRow extends ReportComponent{
	
	private float lastX = 0.0f;
	private float maxHeight = 0.0f;
	private ArrayList<ReportComponent> component = new ArrayList<>();

	public ReportTableCustomRow(String name) {
		super(name, 0f, 0f, 0f, 0f);
	}

	@Override
	public void addOnReport(PDDocument document, PDPageContentStream cs) throws Exception {
		
		
	}

	public ArrayList<ReportComponent> getComponent() {
		return component;
	}

	public void addComponent(ReportComponent... components) {

		float y = getY();
		float x = getX();
		for(ReportComponent k: components) {
			
			k.setY(0f);
			System.out.println(k.getX());
			System.out.println(k.getY());
			component.add(k);
			lastX += k.getWidth();
			System.out.println(lastX);
			maxHeight = Math.max(maxHeight, k.getHeight());
			
		}
		setWidth(lastX - x);
		setHeight(maxHeight);
	}

}
