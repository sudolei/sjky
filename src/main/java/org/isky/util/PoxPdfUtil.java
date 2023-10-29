package org.isky.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 功能描述
 *
 * @author: sunlei
 * @date: 2023年10月28日 21:27
 */
public class PoxPdfUtil {

    public static void magerPdf(List<String> images, String resultPath, float width, float height) {
        System.out.println(width);
        System.out.println(height);
        try {
            PDDocument document = new PDDocument();
            for (String str : images) {
                PDPage pdPage = new PDPage(new PDRectangle(width, height));
                document.addPage(pdPage);
                PDImageXObject pdImage = PDImageXObject.createFromFile(str, document);
                PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
                contentStream.drawImage(pdImage, 0, 0, width, height);
                contentStream.close();
            }
            document.save(resultPath);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
