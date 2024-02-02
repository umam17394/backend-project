package com.project.utils;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.project.entity.PlanCategoryEntity;
import com.project.entity.PlanMasterEntity;

public class PdfUtils {
	// Method to get category name by ID
	private static String getCategoryNameById(Integer categoryId, List<PlanCategoryEntity> categories) {
		for (PlanCategoryEntity category : categories) {
			if (category.getCategoryId() == categoryId) {
				return category.getCategoryName();
			}
		}
		return "";
	}

	public static ByteArrayInputStream createPdf(List<PlanMasterEntity> plans, List<PlanCategoryEntity> categories) {
		String title = "Welcome To Plans Page ";

		String content = "we provide best plans to practice nad get somenthinh";

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Document doc = new Document();

		try {
			PdfWriter.getInstance(doc, out);
			// this is footer code
			HeaderFooter footer = new HeaderFooter(true, new Phrase(" page"));
			footer.setAlignment(Element.ALIGN_CENTER);
			footer.setBorderWidthBottom(0);
			doc.setFooter(footer);

			doc.open();
			// this is titile code
			Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 35, Font.UNDERLINE,
					new Color(0x00, 0x00, 0xFF));
			Paragraph titlePara = new Paragraph(title, titleFont);

			titlePara.setAlignment(Element.ALIGN_CENTER);
			doc.add(titlePara);
			// add new line
			doc.add(Chunk.NEWLINE);
			// add para code
			Font Parafont = FontFactory.getFont(FontFactory.HELVETICA, 18);

			Paragraph para = new Paragraph(content);
			para.add(new Chunk(
					".plan is a place of literary wonder, where the pages of countless stories, knowledge, and adventures come to life. It's a sanctuary for book lovers, a haven for the curious, and a treasure trove of human creativity. Within its shelves, one can find a diverse collection of books spanning various genres, from captivating novels to educational textbooks"));
			// Create a table for book details
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);

			// Add table headers
			Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
			table.addCell(new Phrase("Plan ID", tableHeaderFont));
			table.addCell(new Phrase("plan Name", tableHeaderFont));
			table.addCell(new Phrase("Plan Started Date ", tableHeaderFont));
			table.addCell(new Phrase("Plan End Date", tableHeaderFont));
			table.addCell(new Phrase("Plan Category Name", tableHeaderFont));

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			// ...

			for (PlanMasterEntity plan : plans) {
				table.addCell(String.valueOf(plan.getPlanId()));
				table.addCell(plan.getPlanName());

				// Format LocalDateTime to String for start date
				String createdDateStr = plan.getStartDate().format(formatter);
				table.addCell(createdDateStr);

				// Format LocalDateTime to String for end date
				String updatedDateStr = plan.getEndDate().format(formatter);
				table.addCell(updatedDateStr);

				Integer categoryId = plan.getCategoryId();
				String categoryName = getCategoryNameById(categoryId, categories);
				table.addCell(categoryName);
			}

			doc.add(para);
			doc.add(Chunk.NEWLINE);

			doc.add(table);

			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ByteArrayInputStream(out.toByteArray());

	}
}
