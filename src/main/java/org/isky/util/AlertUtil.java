package org.isky.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.text.TextFlow;

public class AlertUtil {


    public static Alert showWarningAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("提示");
        alert.setHeaderText("");
        // 将TextFlow或Label设置为Alert的内容
        alert.setContentText(content);
        // 或者使用Label:
        // alert.getDialogPane().setContent(label);
        alert.showAndWait();
        return alert;
    }



    public static Alert showSuccessAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText("");
        // 将TextFlow或Label设置为Alert的内容
        alert.setContentText(content);
        // 或者使用Label:
        // alert.getDialogPane().setContent(label);
        alert.showAndWait();
        return alert;
    }


    public static Alert showAreaAlert(String content) {
        // 创建一个TextFlow或Label来包装文本内容
        TextFlow textFlow = new TextFlow();
        // 设置TextFlow的宽度限制，以便自动换行
        textFlow.setMaxWidth(300);
        // 在TextFlow或Label中添加包含换行的文本
        textFlow.getChildren().add(new Label(content));
        // 或者使用Label:
        // label.setText(message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText("");
        // 将TextFlow或Label设置为Alert的内容
        alert.getDialogPane().setContent(textFlow);
        // 或者使用Label:
        // alert.getDialogPane().setContent(label);
        alert.showAndWait();
        return alert;
    }
}
