package org.isky;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.isky.util.*;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private Button selFolderBtn;

    @FXML
    private Button subBtn;

    @FXML
    private Label twoFolderLabel;

    @FXML
    private ListView<String> folderListView;


    @FXML
    private TextField xLocal;

    @FXML
    private TextField yLocal;

    @FXML
    private TextField xLocation;

    @FXML
    private TextField yLocation;

    @FXML
    private Button folderBtn;

    @FXML
    private Label pdfHbLabel;

    @FXML
    private Button endHcBtn;

    @FXML
    private Label defFolder;

    @FXML
    protected void onHelloButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件夹");
        String sysVal = FileUtil.getSysVal();
        if (!StringUtils.isEmpty(sysVal)) {
            defFolder.setText(sysVal);
        }
        if (!StringUtils.isEmpty(defFolder.getText())) {
            directoryChooser.setInitialDirectory(new File(defFolder.getText()));
        }
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
        int x = Integer.parseInt(xLocation.getText());
        int y = Integer.parseInt(yLocation.getText());
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
        // 如果图片是300dpi,需要把宽高改成72DPI的宽高
        double w = new BigDecimal(width).doubleValue() * 72 / 300;
        double h = new BigDecimal(height).doubleValue() * 72 / 300;
        System.out.println(width);
        System.out.println(height);
        File fmFile = new File(firstFile);
        String createFolder = fmFile.getParent();
        String lsFileName = createFolder + File.separator + "no_water_" + System.currentTimeMillis() + ".pdf";
        PoxPdfUtil.magerPdf(images, lsFileName, (float) w, (float) h);
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
//                    PdfMakeUtil.makeText(lsFileName, createFolder + File.separator + orderNo + ".pdf", "C:\\Windows\\Fonts\\simhei.ttf", 1, orderNo, (int) (width - x - 100), y);
                    PdfMakeUtil.makeText(lsFileName, createFolder + File.separator + orderNo + ".pdf", "C:\\Windows\\Fonts\\simhei.ttf", 1, orderNo, (int) (width * 72 / 300 - x - 80), y);
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
        String sysVal = FileUtil.getSysVal();
        if (!StringUtils.isEmpty(sysVal)) {
            defFolder.setText(sysVal);
        }
        if (!StringUtils.isEmpty(defFolder.getText())) {
            directoryChooser.setInitialDirectory(new File(defFolder.getText()));
        }
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
        int x = 100;
        int y = 100;
        if (StringUtils.isNotEmpty(xLocal.getText())) {
            x = Integer.parseInt(xLocal.getText());
        }
        if (StringUtils.isNotEmpty(yLocal.getText())) {
            y = Integer.parseInt(yLocal.getText());
        }
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
            Map<String, Object> hcPath = PdfUtils.boxHc(images, thisFolder);
            noWaterList.add(hcPath);
        }


        try {
            Thread.sleep(2000);
            for (Map<String, Object> s : noWaterList) {
                String fileName = s.get("fileName").toString();
                double width = (double) s.get("width");
                Path path = Paths.get(fileName);
                String createPath = path.getRoot() + "世纪开元PDF";
                File createFolder = new File(createPath);
                if (createFolder.exists()) {
                    if (!createFolder.isDirectory()) {
                        createFolder.mkdir();
                    }
                } else {
                    createFolder.mkdir();
                }

                System.out.println(path.getNameCount());
                String firstLevelDirectory = path.getName(path.getNameCount()-3).toString();
                System.out.println(firstLevelDirectory);
                //订单号
                String pdfName = firstLevelDirectory.split(";")[0];
                String orderNo = firstLevelDirectory.split("-")[0];
//                PdfMakeUtil.makeText(fileName, createPath + File.separator + pdfName + ".pdf", "C:\\Windows\\Fonts\\simhei.ttf", 1, orderNo, (int) (width - x - 100), y);
                PdfMakeUtil.makeText(fileName, createPath + File.separator + pdfName + ".pdf", "C:\\Windows\\Fonts\\simhei.ttf", 1, orderNo, (int) (width * 72 / 300 - x - 80), y);
            }

            Thread.sleep(2000);
            AlertUtil.showSuccessAlert("合成成功，PDF在本磁盘的 世纪开元PDF 文件夹");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void folderBtnClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件夹");
        Stage stage = (Stage) folderBtn.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            String folderPath = selectedDirectory.getAbsolutePath();
            // 可以在这里处理选择的文件夹
            pdfHbLabel.setText(folderPath);


            List<String> list = FileUtil.readFolders(folderPath);
            ObservableList<String> folderList = FXCollections.observableArrayList(list);
            endFolderListView.setItems(folderList);
        }
    }

    @FXML
    private ListView<String> endFolderListView;

    @FXML
    protected void endHcBtnClick() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        ObservableList<String> observableList = endFolderListView.getItems();
        for (String folder : observableList) {
            List<String> l = FileUtil.readPdfFiles(folder);
            if (l != null && l.size() > 0) {
                String[] newFiles = new String[l.size()];
                l.toArray(newFiles);
                String resultPdf = folder + File.separator + sdf.format(new Date()) + ".pdf";
                PdfMerger.mergePdf(newFiles, resultPdf);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                String reToFolder = folder + File.separator + "111";
                File f = new File(reToFolder);
                if (!f.exists()) {
                    f.mkdir();
                }


            }
        }
    }

    /**
     * 设置默认路径
     */
    @FXML
    protected void setFolderPath() {
        Stage stage = new Stage();
        // 创建新的Stage对象
        // 设置窗口标题
        stage.setTitle("设置默认路径");
        stage.setWidth(300);
        stage.setHeight(200);

        // 创建文本标签
        TextField field = new TextField(defFolder.getText());

        // 创建按钮
        Button button = new Button("保存");
        button.setStyle("-fx-background-color: green;");
        button.setTextFill(Paint.valueOf("#fcf8f8"));
        // 创建垂直布局容器
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(field, button);

        // 创建场景并将容器添加到场景中
        Scene scene = new Scene(vbox, 300, 200);

        // 将场景设置到舞台
        stage.setScene(scene);
        // 显示窗口
        stage.show();

        button.setOnAction(e -> {
            String path = field.getText();
            File file = new File(path);
            if (!file.exists()) {
                AlertUtil.showWarningAlert("保存失败,路径不存在！");
                return;
            }
            defFolder.setText(field.getText());
            AlertUtil.showSuccessAlert("保存成功!");
            FileUtil.setSysVal(field.getText());
            stage.close();
        });
    }

    /**
     * 程序退出
     */
    @FXML
    protected void exitSjky() {
        Platform.exit();
    }
}