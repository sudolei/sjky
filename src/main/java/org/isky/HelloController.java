package org.isky;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.isky.util.*;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HelloController {
    @FXML
    private Label jpgFolderLabel;

    @FXML
    private Button selJpgFolderBtn;

    @FXML
    private Button hcPdtBtn;

    @FXML
    private ListView<String> imgListView;

    @FXML
    private Button clearBtn;

    @FXML
    private Button selCreateFolderBtn;

    @FXML
    private Label createFolderLabel;

    @FXML
    private Button selFolderBtn;

    @FXML
    private Button subBtn;

    @FXML
    private Label twoFolderLabel;

    @FXML
    private ListView<String> folderListView;

    @FXML
    protected void onHelloButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件夹");
        Stage stage = (Stage) selJpgFolderBtn.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            String folderPath = selectedDirectory.getAbsolutePath();
            // 可以在这里处理选择的文件夹
            jpgFolderLabel.setText(folderPath);

            List<String> list = FileUtil.readImgFiles(folderPath);
            ObservableList<String> imgList = FXCollections.observableArrayList(list);
            imgListView.setItems(imgList);
        }

    }


    @FXML
    protected void hcPdtClick() {

        String createFolder = createFolderLabel.getText();
        if (StringUtils.isBlank(createFolder)) {
            AlertUtil.showWarningAlert("请先选择生成文件目录");
            return;
        }

        String jpgFolder = jpgFolderLabel.getText();
        Path path = Paths.get(jpgFolder);
        String firstLevelDirectory = path.getName(1).toString();
        //订单号
        String orderNo = firstLevelDirectory.split("-")[0];


        ObservableList<String> images = imgListView.getItems();
        String firstFile = images.get(0);
        Image image = new Image(firstFile);
        double width = image.getWidth();
        double height = image.getHeight();
        Document document = null;
        String lsFileName = createFolder + File.separator + "no_water_" + System.currentTimeMillis() + ".pdf";
        try {
            document = new Document();

            // 设置页面大小和边距
            Rectangle rect = new Rectangle(0, 0, (float) width, (float) height);
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

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        AlertUtil.showSuccessAlert("已合成,请查看对应文件夹");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.gc();
                    PdfMakeUtil.makeText(lsFileName, createFolder + File.separator + orderNo + ".pdf", "C:\\Windows\\Fonts\\simhei.ttf", 1, orderNo, 100, 100);
                    Thread.sleep(4000);
                    System.gc();
                    File delFile = new File(lsFileName);
                    if (delFile.delete()) {
                        System.out.println("文件删除成功");
                    } else {
                        System.out.println("文件删除失败");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @FXML
    protected void selCreateFolderClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件夹");
        Stage stage = (Stage) selCreateFolderBtn.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            String folderPath = selectedDirectory.getAbsolutePath();
            // 可以在这里处理选择的文件夹
            createFolderLabel.setText(folderPath);
        }
    }

    @FXML
    protected void clearBtnClick() {
        imgListView.getItems().clear();
    }


    /**
     * 多文件
     */

    @FXML
    protected void selFolderClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件夹");
        Stage stage = (Stage) selFolderBtn.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            String folderPath = selectedDirectory.getAbsolutePath();
            // 可以在这里处理选择的文件夹
            twoFolderLabel.setText(folderPath);

            List<String> list = FileUtil.readFolders(folderPath);
            ObservableList<String> folderList = FXCollections.observableArrayList(list);
            folderListView.setItems(folderList);
        }
    }

    @FXML
    protected void subBtnClick() {
        ObservableList<String> folderList = folderListView.getItems();
        if (folderList == null || folderList.size() <= 0) {
            AlertUtil.showWarningAlert("没有找到源文件");
            return;
        }
        List<Map<String, Object>> noWaterList = new ArrayList<>();
        for (String folder : folderList) {
            List<String> images = FileUtil.getJpgFiles(folder);
            File imgFile = new File(images.get(0));
            String thisFolder = imgFile.getParent();
            Map<String, Object> hcPath = PdfUtils.hc(images, thisFolder);
            noWaterList.add(hcPath);
        }


        try {
            Thread.sleep(2000);
            for (Map<String, Object> s : noWaterList) {
                String fileName = s.get("fileName").toString();
                double width = (double) s.get("width");
                double height = (double) s.get("height");

                Path path = Paths.get(fileName);
                String firstLevelDirectory = path.getName(1).toString();
                //订单号
                String orderNo = firstLevelDirectory.split("-")[0];
                PdfMakeUtil.makeText(fileName, path.getParent() + File.separator + orderNo + ".pdf", "C:\\Windows\\Fonts\\simhei.ttf", 1, orderNo, (int) (width - 100 - 100), 100);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}