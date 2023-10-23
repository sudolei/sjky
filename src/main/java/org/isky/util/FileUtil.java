package org.isky.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能描述
 *
 * @author: sunlei
 * @date: 2023年10月05日 14:11
 */
public class FileUtil {


    public static List<String> readImgFiles(String folderPath) {
        List<String> pdfFiles = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
                        String pdfFileName = fileName.toUpperCase();
                        int count = MyUtil.getPdfCount(pdfFileName);
                        if (count > 0) {
                            pdfFiles.add(folderPath + File.separator + fileName);
                        }
                    }
                }
            }
        }
        return pdfFiles;
    }


    public static List<String> readPdfFiles(String folderPath) {
        List<String> pdfFiles = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".pdf")) {
                        String pdfFileName = fileName.toUpperCase();
                        int count = MyUtil.getPdfCount(pdfFileName);
                        if (count > 0) {
                            pdfFiles.add(folderPath + File.separator + fileName);
                        }
                    }
                }
            }
        }
        return pdfFiles;
    }

    public static List<String> getJpgFiles(String folderPath) {
        List<String> jpgFiles = new ArrayList<String>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                jpgFiles.addAll(getJpgFiles(file.getAbsolutePath()));
            } else {
                String fileName = file.getName();
                if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
                    jpgFiles.add(file.getAbsolutePath());
                }
            }
        }
        return jpgFiles;
    }

    public static List<String> readFolders(String folderPath) {

        List<String> folderList = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String fileName = file.getName();
                    folderList.add(folderPath + File.separator + fileName);
                }
            }
        }
        return folderList;
    }


    public static List<String> readBmPDFFiles(String folderPath) {
        List<String> pdfFiles = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".pdf")) {
                        String pdfFileName = fileName.toUpperCase();
                        String[] strArr = pdfFileName.split("--");
                        int count = MyUtil.getPdfCount(pdfFileName);
                        if (count == 0) {
                            pdfFiles.add(fileName);
                        }
                    }
                }
            }
        }
        return pdfFiles;
    }

    /**
     * 文件重命名
     *
     * @param oldFile
     * @param newFile
     */
    public static void fileReName(String oldFile, String newFile) {
        System.out.println(oldFile);
        System.out.println(newFile);
        File o = new File(oldFile);
        if (!o.exists()) {
            System.out.println("文件不存在");
            return;
        }
        File n = new File(filterSpecialChars(newFile));
        if (o.renameTo(n)) {
            System.out.println("文件重命名成功！");
        } else {
            System.out.println("文件重命名失败！");
        }
    }

    private static String filterSpecialChars(String fileName) {
        Pattern pattern = Pattern.compile("[\\\\/:*?\"<>|]");
        Matcher matcher = pattern.matcher(fileName);
        return matcher.replaceAll("");
    }

    public static void main(String[] args) {
        fileReName("F:\\3寸新尺寸 白卡纸 PKZDBM CAI--A--0fen--kekepenpen123--1.pdf", "F:\\333.pdf");
    }


}
