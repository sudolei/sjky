package org.isky;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import org.isky.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class JpgToPdfConverter {
    public static void main(String[] args) {
        try {
            String folderPath = "D:\\iskylei\\pdf\\台历_2591963193_榉木\\";
            List<String> images = FileUtil.readImgFiles(folderPath);
            String firstFile = folderPath + images.get(0);
            // 创建PDF文档对象
            Document document = new Document();

            // 设置页面大小和边距
            Rectangle rect = new Rectangle(0, 0, 595, 842);
            document.setPageSize(rect);
            document.setMargins(0, 0, 0, 0);

            // 使用PdfWriter将文档对象写入PDF文件
            PdfWriter.getInstance(document, new FileOutputStream("D:\\iskylei\\pdf\\output.pdf"));
            document.open();

            // 使用Image类读入jpg文件，并添加到PDF文档中
            for (String str : images) {
                System.out.println(folderPath + str);
                Image image = Image.getInstance(folderPath + str);
                document.add(image);
            }
            // 关闭文档对象，生成PDF文件
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}