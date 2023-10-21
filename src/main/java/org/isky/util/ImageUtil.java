package org.isky.util;

import javafx.scene.image.Image;
import org.isky.domain.ImageInfo;

/**
 * 功能描述
 *
 * @author: sunlei
 * @date: 2023年10月20日 20:53
 */
public class ImageUtil {

    public static ImageInfo getImageInfo(String filePath) {
        ImageInfo imageInfo = new ImageInfo();
        Image image = new Image(filePath);
        double width = image.getWidth();
        double height = image.getHeight();
        imageInfo.setWidth(width);
        imageInfo.setHeight(height);


        return imageInfo;

    }

}
