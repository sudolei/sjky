package org.isky;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.isky.util.AlertUtil;
import org.isky.util.FileUtil;

import java.io.File;
import java.util.List;

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
        AlertUtil.showWarningAlert("111");
    }
}