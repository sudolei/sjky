package org.isky.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfMerger {
    /**
     * pdf合成
     *
     * @param files
     * @param result
     */
    public static void mergePdf(String[] files, String result) {
        Document document = new Document();
        try {
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(result));
            document.open();

            for (String file : files) {
                PdfReader reader = new PdfReader(file);
                int numPages = reader.getNumberOfPages();

                for (int i = 1; i <= numPages; i++) {
                    copy.addPage(copy.getImportedPage(reader, i));
                }

                reader.close();
            }
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * PDF插入
     *
     * @param files
     * @param insertPdf
     * @param result
     */
    public static void insertPdf(String[] files, String insertPdf, String result) {
        try {
            // 创建一个新的PDF文档
            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(result));
            document.open();

            for (String file : files) {
                // 读取原始PDF文件
                PdfReader reader = new PdfReader(file);
                int totalPages = reader.getNumberOfPages();
                for (int i = 1; i <= totalPages; i++) {
                    // 复制原始PDF文件的每一页到新的PDF文档中
                    copy.addPage(copy.getImportedPage(reader, i));
                    PdfReader insertReader = new PdfReader(insertPdf);
                    // 复制插入PDF文件的每一页到新的PDF文档中
                    copy.addPage(copy.getImportedPage(insertReader, 1));
                    insertReader.close();
                }
                reader.close();

            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移动到其他文件夹
     *
     * @param observableList
     */
    public static void reToOtherFolder(ObservableList<String> observableList) {
        for (String str : observableList) {
            File f = new File(str);
            String folder = f.getParent();
            String reToFolder = folder + File.separator + "123111" + File.separator;
            File folderFile = new File(reToFolder);
            if (!folderFile.exists()) {
                folderFile.mkdir();
            }
            f.deleteOnExit();
            System.out.println(f.getName());
            if (f.renameTo(new File(reToFolder, f.getName()))) {
                System.out.println("移动成功!");
            } else {
                System.out.println("移动失败!");
            }
        }
    }


    public static void reToOther(ObservableList<String> observableList) throws IOException {
        for (String str : observableList) {
            File f = new File(str);
            String folder = f.getParent();
            String reToFolder = folder + File.separator + "123111" + File.separator;
            FileUtils.copyFileToDirectory(f, new File(reToFolder));
            if (f.exists() && f.isFile()) {
                System.gc();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(f.getName());
                System.out.println(f.getAbsolutePath());
                boolean b = f.delete();
                System.out.println(b);
            }
        }
    }
}