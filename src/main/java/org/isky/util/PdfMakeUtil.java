package org.isky.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @BelongsProject: lwq
 * @BelongsPackage: com.lwq.lwq.comm.util
 * @Author: sunlei
 * @CreateTime: 2022-11-01  08:59
 * @Description: TODO
 * @Version: 1.0
 */
public class PdfMakeUtil {

    /**
     * @description:
     * @author: sunlei
     * @date: 2022/11/1 9:17
     * @param: [pdfFile, fontFile, pageNum, text, x, y]
     * @return: void
     **/
    public static void makeText(String pdfFile, String resultPdf, String fontFile, int pageNum, String text, int x, int y) {
        //设置编码
        try {
            PdfReader pdfReader = new PdfReader(pdfFile);
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(resultPdf));
            PdfContentByte cb = pdfStamper.getOverContent(pageNum);
            try {
                float[] colorArr = {0, 0, 0, 60};
                ExtendedColor baseColor = new CMYKColor(colorArr[0], colorArr[1], colorArr[2], colorArr[3]);
                cb.setColorFill(baseColor);
                BaseFont bf = BaseFont.createFont(fontFile, BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED);
                cb.beginText();
                cb.setFontAndSize(bf, 24);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text, x, y, 0);
                cb.endText();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //设置不可编辑
            pdfStamper.setFormFlattening(true);
            pdfStamper.close();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
