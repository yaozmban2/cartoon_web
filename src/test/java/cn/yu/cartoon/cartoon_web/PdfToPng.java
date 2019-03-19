package cn.yu.cartoon.cartoon_web;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/18 12:23
 **/
public class PdfToPng {

    public static void main(String[] args) {
        //将pdf装图片 并且自定义图片得格式大小
        File file = new File("C:\\Users\\Yu\\Desktop\\简历\\实习简历.pdf");
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                // Windows native DPI
                BufferedImage image = renderer.renderImageWithDPI(i, 192);
                //产生缩略图
                //BufferedImage srcImage = resize(image, 240, 240);
                ImageIO.write(image, "PNG", new File("C:\\Users\\Yu\\Desktop\\简历\\实习简历.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage resize(BufferedImage source, int targetW,  int targetH) {
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
          } else {
           target = new BufferedImage(targetW, targetH, type);  
        }
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;

    }

}
