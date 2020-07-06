package logic.report.base;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class ReportUtil {
	
	public static final float DPI = 72.0f;
	public static final float MMPI = 25.4f;

	public static float cpx(float x) {
		return (x / MMPI * DPI);
	}

	public static float cpy(float y) {
		return 842.0f - (y / MMPI * DPI);
	}
	
	public static PDFont getFont(PDDocument document, String fontName, ReportFontType fontType) throws Exception {
		
		PDFont font;
		switch (fontType) {
		case BOLD:
			font = PDType0Font.load(document, new File("./src/font/" + fontName + " Bold.ttf"));
			break;
		case BOLD_ITALIC:
			font = PDType0Font.load(document, new File("./src/font/" + fontName + " BoldItalic.ttf"));
			break;
		case ITALIC:
			font = PDType0Font.load(document, new File("./src/font/" + fontName + " Italic.ttf"));
			break;
		case REGULAR:
			font = PDType0Font.load(document, new File("./src/font/" + fontName + ".ttf"));
			break;
		default:
			font = PDType0Font.load(document, new File("./src/font/" + fontName + ".ttf"));
			break;
		}
		
		return font;
		
	}
	
	public static float getFontWidth(PDDocument document, PDFont font, float fontSize, String str) throws Exception{
		return (font.getStringWidth(str) / 1000.0f * fontSize) * MMPI / DPI;
	}
	
	public static float getFontHeight(PDDocument document, PDFont font, float fontSize) throws Exception{
		return (font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000.0f * fontSize) * MMPI / DPI;
	}
	
	public static ArrayList<String> getMutilineStringList(PDDocument document, PDFont font, float fontSize, String str, float width) throws Exception{
		
		ArrayList<String> strList = new ArrayList<>();
		
		int j = 0;
		for(int i = 0;i < str.length();i++) {
			
			char k = str.charAt(i);
			if(k == '\n') {
				
				strList.add(str.substring(j, i));
				j = i + 1;
				
			}
			
		}
		strList.add(str.substring(j));
		
		ArrayList<String> strList2 = new ArrayList<>();
		
		for(int m = 0;m < strList.size();m++) {
			
			String s = strList.get(m);
			
			while(getFontWidth(document, font, fontSize, s) > width) {
				
				j = 0;
				for(int i = 0;i < s.length();i++) {
					
					char k = s.charAt(i);
					if(k == ' ') {
						
						if(getFontWidth(document, font, fontSize, s.substring(0, i)) <= width) {
							j = i;
						}else {
							break;
						}
						
					}
					
				}
				strList2.add(s.substring(0, j));
				s = s.substring(j + 1);
				
			}
			
			strList2.add(s);
			
		}
		
		return strList2;
		
	}
		
	
	public static void addParagraph(PDDocument document, PDPageContentStream cs, String str, float fontSize, float x,
			float y, float width, float height, ReportHAlignment hAlignment, ReportVAlignment vAlignment, ReportFontType fontType)
			throws Exception {

		cs.beginText();

		PDFont font = getFont(document, "THSarabunNew", fontType);
		cs.setFont(font, fontSize);
		cs.setNonStrokingColor(Color.BLACK);

		float strWidth = getFontWidth(document, font, fontSize, str);
		float strHeight = getFontHeight(document, font, fontSize);
		float pivotX = cpx(x);
		float pivotY = cpy(y) - strHeight;
		float pixelWidth = cpx(width);
		float pixelHeight = cpx(height);

		float pointX = 0.0f, pointY = 0.0f;

		switch (hAlignment) {
		case CENTER:
			pointX = pivotX + ((pixelWidth - strWidth) / 2.0f);
			break;
		case LEFT:
			pointX = pivotX;
			break;
		case RIGHT:
			pointX = pivotX + ((pixelWidth - strWidth));
			break;
		default:
			pointX = pivotX;
			break;
		}

		switch (vAlignment) {
		case BOTTOM:
			pointY = pivotY - (pixelHeight) + (strHeight);
			break;
		case CENTER:
			pointY = pivotY - (pixelHeight / 2.0f) + (strHeight / 2.0f);
			break;
		case TOP:
			pointY = pivotY;
			break;
		default:
			pointY = pivotY;
			break;

		}

		cs.newLineAtOffset(pointX, pointY);

		cs.showText(str);

		cs.endText();

	}
	
	public static void addMultiLineParagraph(PDDocument document, PDPageContentStream cs, String str, float fontSize,
			float x, float y, float width, float height, ReportHAlignment hAlignment, ReportVAlignment vAlignment,
			ReportFontType fontType) throws Exception {
		
		float lineSpacing = 1f;
		PDFont font = getFont(document, "THSarabunNew", fontType);
		ArrayList<String> mutilineList = getMutilineStringList(document, font, fontSize, str, width);
		int row = mutilineList.size();
		float fontHeight = getFontHeight(document, font, fontSize);
		float paragraphSpace = (row * fontHeight) + ((row - 1) * lineSpacing);
		
		switch(vAlignment) {
		case BOTTOM:
			x = x + (height - paragraphSpace);
			break;
		case CENTER:
			x = x + ((height - paragraphSpace) / 2.0f);
			break;
		case TOP:
			break;
		default:
			break;
		
		}
		
		for(int i = 0;i<row;i++) {
			
			String s = mutilineList.get(i);
			
			addParagraph(document, cs, s, fontSize, x, y, width, fontHeight, hAlignment, ReportVAlignment.CENTER, fontType);
			
			x = x + fontHeight + lineSpacing;
			
		}
		
		

	}
	
	public static void addFillRectBox(PDDocument document, PDPageContentStream cs, float x, float y, float width,
			float height, Color colorFill) throws Exception {
		
		cs.addRect(cpx(x), cpy(y) - cpx(height), cpx(width), cpx(height));
		cs.setNonStrokingColor(colorFill);
		cs.fill();

	}
	
	public static void addStrokeRectBox(PDDocument document, PDPageContentStream cs, float x, float y, float width,
			float height, Color colorStroke) throws Exception {
		
		cs.addRect(cpx(x), cpy(y) - cpx(height), cpx(width), cpx(height));
		cs.setNonStrokingColor(colorStroke);
		cs.stroke();
		
	}
	
	public static void main(String[] args) {
		
		
		
	}

}
