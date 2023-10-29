package org.isky.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @author: sunlei
 * @date: 2023年10月21日 15:41
 */
public class PdfUtils {


    public static Map<String, Object> boxHc(List<String> images, String createFolder) {
        Map<String, Object> result = new HashMap<>();

        String firstFile = images.get(0);
        Image image = new Image(firstFile);
        double width = image.getWidth();
        double height = image.getHeight();

        double w = new BigDecimal(width).doubleValue() * 72 / 300;
        double h = new BigDecimal(height).doubleValue() * 72 / 300;
        String lsFileName = createFolder + File.separator + "no_water_" + System.currentTimeMillis() + ".pdf";
        PoxPdfUtil.magerPdf(images, lsFileName, (float) w, (float) h);

        result.put("fileName", lsFileName);
        result.put("width", width);
        result.put("height", height);
        return result;
    }

    public static Map<String, Object> hc(List<String> images, String createFolder) {
        Map<String, Object> result = new HashMap<>();

        String firstFile = images.get(0);
        Image image = new Image(firstFile);
        double width = image.getWidth();
        double height = image.getHeight();

        double w = new BigDecimal(width).doubleValue() * 72 / 300;
        double h = new BigDecimal(height).doubleValue() * 72 / 300;
        Document document = null;
        String lsFileName = createFolder + File.separator + "no_water_" + System.currentTimeMillis() + ".pdf";

        result.put("fileName", lsFileName);
        result.put("width", width);
        result.put("height", height);
        try {
            document = new Document();

            // 设置页面大小和边距
            Rectangle rect = new Rectangle(0, 0, (float) w, (float) h);
            document.setPageSize(rect);
            document.setMargins(0, 0, 0, 0);

            // 使用PdfWriter将文档对象写入PDF文件
            PdfWriter.getInstance(document, new FileOutputStream(lsFileName));
            document.open();

            // 使用Image类读入jpg文件，并添加到PDF文档中
            for (String str : images) {
                com.itextpdf.text.Image i = com.itextpdf.text.Image.getInstance(str);
                document.add(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭文档对象，生成PDF文件
            document.close();
        }
        return result;
    }
}
