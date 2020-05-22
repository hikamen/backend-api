package com.base.backend.utils;

import com.alibaba.simpleimage.ImageFormat;
import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.ImageWrapper;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.*;
import com.alibaba.simpleimage.util.ImageCropHelper;
import com.alibaba.simpleimage.util.ImageDrawHelper;
import com.alibaba.simpleimage.util.ImageReadHelper;
import com.alibaba.simpleimage.util.ImageScaleHelper;
import org.apache.commons.io.IOUtils;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 阿里巴巴 simpleimage 图片处理工具类 功能 等比例缩放 裁切 加水印 复合操作
 *
 *
 */
public class ImageUtils {
    /**
     * 水印文字
     */
    private final static String WATERMARK_TEXT = "样例水印";
    /**
     * 水印
     */
    private final static String WATERMARK_URL = "D:\\resources\\Image\\scale\\watermark.png";
    /**
     * 默认输出文件名
     */
    private final static String OUTPUT_FILENAME = "output.jpg";
    /**
     * 默认输出格式
     */
    private final static ImageFormat OUTPUT_FORMAT = ImageFormat.JPEG;
    /**
     * 默认尺寸，宽和高公用
     */
    private final static int DEFAULT_SIZE = 1024;

    /**
     * 等比例缩放
     * 注意！缩放操作是将图片的像素等比例缩放到 width * height 之内：width=1000,height=1000, 2000*1500 -> 1000*750
     *
     * @param source 原图片
     * @param target 目的图片
     * @param width  目标宽度
     * @param height 目标高度
     */
    public static void scale(String source, String target, int width, int height) {
        File in = new File(source);
        File out = new File(target);

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        ImageRender wr = null;
        try {
            inStream = new FileInputStream(in);
            outStream = new FileOutputStream(out);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);
            int originalWidth = imageWrapper.getWidth();
            int originalHeight = imageWrapper.getHeight();

            // 处理宽高
            //noinspection Duplicates
            if (width <= 0 && height <= 0) {
                width = DEFAULT_SIZE;
                height = DEFAULT_SIZE;
            } else if (width <= 0) {
                width = getWidth(originalWidth, originalHeight, height);
            } else if (height <= 0) {
                height = getHeight(originalWidth, originalHeight, width);
            }

            // 将图像缩略到 width * height 以内，宽高均不足则不做任何处理
            ScaleParameter scaleParam = new ScaleParameter(width, height);
            ImageRender scaleRender = new ScaleRender(imageWrapper, scaleParam);
            wr = new WriteRender(scaleRender, outStream, OUTPUT_FORMAT);

            //触发图像处理
            wr.render();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // noinspection deprecation
            IOUtils.closeQuietly(inStream);
            // noinspection deprecation
            IOUtils.closeQuietly(outStream);
            if (wr != null) {
                try {
                    //释放simpleImage的内部资源
                    wr.dispose();
                } catch (SimpleImageException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 缩放到指定宽度 高度自适应
     * 注意！缩放操作是将图片的像素等比例缩放到 width * height 之内：width=1000,height=1000, 2000*1500 -> 1000*750
     *
     * @param source 原图片
     * @param target 目的图片
     * @param width  目标宽度
     */
    public static void scaleByWidth(String source, String target, int width) {
        File in = new File(source);
        File out = new File(target);
        if (width <= 0) {
            width = DEFAULT_SIZE;
        }

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        ImageRender wr = null;
        try {
            inStream = new FileInputStream(in);
            outStream = new FileOutputStream(out);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);

            // 使用 getHeight() 计算缩略后的高度，然后构造缩略参数
            ScaleParameter scaleParam = new ScaleParameter(width, getHeight(imageWrapper.getWidth(),
                    imageWrapper.getHeight(), width));
            ImageRender scaleRender = new ScaleRender(imageWrapper, scaleParam);
            wr = new WriteRender(scaleRender, outStream, OUTPUT_FORMAT);

            //触发图像处理
            wr.render();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // noinspection deprecation
            IOUtils.closeQuietly(inStream);
            // noinspection deprecation
            IOUtils.closeQuietly(outStream);
            if (wr != null) {
                try {
                    //释放simpleImage的内部资源
                    wr.dispose();
                } catch (SimpleImageException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 缩放到指定高度，宽度自适应
     * 注意！缩放操作是将图片的像素等比例缩放到 width * height 之内：width=1000,height=1000, 2000*1500 -> 1000*750
     *
     * @param source 原图片
     * @param target 目的图片
     * @param height 目标高度
     */
    public static void scaleByHeight(String source, String target, int height) {
        File in = new File(source);
        File out = new File(target);
        if (height <= 0) {
            height = DEFAULT_SIZE;
        }

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        ImageRender wr = null;
        try {
            inStream = new FileInputStream(in);
            outStream = new FileOutputStream(out);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);
            // 使用 getWidth() 计算缩略后的宽度，然后构造缩略参数
            ScaleParameter scaleParam = new ScaleParameter(getWidth(imageWrapper.getWidth(), imageWrapper.getHeight(),
                    height), height);
            ImageRender scaleRender = new ScaleRender(imageWrapper, scaleParam);
            wr = new WriteRender(scaleRender, outStream, OUTPUT_FORMAT);

            //触发图像处理
            wr.render();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // noinspection deprecation
            IOUtils.closeQuietly(inStream);
            // noinspection deprecation
            IOUtils.closeQuietly(outStream);
            if (wr != null) {
                try {
                    //释放simpleImage的内部资源
                    wr.dispose();
                } catch (SimpleImageException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 打文字水印
     *
     * @param source 原图片
     * @param target 目的图片
     */
    public static void drawWaterMark(String source, String target) {
        File in = new File(source);
        File out = new File(target);

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        ImageRender wr = null;
        try {
            inStream = new FileInputStream(in);
            outStream = new FileOutputStream(out);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);
            // 打水印
            imageWrapper = drawWatermark(imageWrapper, WATERMARK_TEXT);
            // 输出
            wr = new WriteRender(imageWrapper, outStream, OUTPUT_FORMAT);
            wr.render();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // noinspection deprecation
            IOUtils.closeQuietly(inStream);
            // noinspection deprecation
            IOUtils.closeQuietly(outStream);
            if (wr != null) {
                try {
                    //释放simpleImage的内部资源
                    wr.dispose();
                } catch (SimpleImageException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 等比例缩放，有水印
     * 注意！缩放操作是将图片的像素等比例缩放到 width * height 之内：width=1000,height=1000, 2000*1500 -> 1000*750
     *
     * @param source 原图片
     * @param target 目的图片
     * @param width  目标宽度
     * @param height 目标高度
     */
    public static void scaleWithWaterMark(String source, String target, int width, int height) {
        File in = new File(source);
        File out = new File(target);

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        ImageRender wr = null;
        try {
            inStream = new FileInputStream(in);
            outStream = new FileOutputStream(out);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);
            int originalWidth = imageWrapper.getWidth();
            int originalHeight = imageWrapper.getHeight();

            // 处理宽高
            // noinspection Duplicates
            if (width <= 0 && height <= 0) {
                width = DEFAULT_SIZE;
                height = DEFAULT_SIZE;
            } else if (width <= 0) {
                width = getWidth(originalWidth, originalHeight, height);
            } else if (height <= 0) {
                height = getHeight(originalWidth, originalHeight, width);
            }

            // 需要对原图进行缩略，再给缩略后的图片打水印
            // 缩略
            ScaleParameter scaleParam = new ScaleParameter(width, height);
            PlanarImage planrImage = ImageScaleHelper.scale(imageWrapper.getAsPlanarImage(), scaleParam);
            // 打水印
            imageWrapper = drawWatermark(new ImageWrapper(planrImage));
            // 输出
            wr = new WriteRender(imageWrapper, outStream, OUTPUT_FORMAT);
            wr.render();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // noinspection deprecation
            IOUtils.closeQuietly(inStream);
            // noinspection deprecation
            IOUtils.closeQuietly(outStream);
            if (wr != null) {
                try {
                    //释放simpleImage的内部资源
                    wr.dispose();
                } catch (SimpleImageException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 先等比例缩放，然后以指定坐标为圆心，裁剪成圆形
     * 注意！缩放操作是将图片的像素等比例缩放到 width * height 之内：width=1000,height=1000, 2000*1500 -> 1000*750
     *
     * @param source      原图片
     * @param target      目标图片
     * @param scaleWidth  缩放的目标宽度
     * @param scaleHeight 缩放的目标高度
     * @param center      裁剪的目标圆心坐标
     * @param radius      裁剪的目标半径
     */
    public static void scaleAndCropToCircular(String source, String target, int scaleWidth, int scaleHeight,
                                              Point center, int radius) {
        File in = new File(source);
        File out = new File(target);
        int diameter = radius * 2;

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        Graphics2D graphic = null;
        ImageRender wr = null;
        try {
            inStream = new FileInputStream(in);
            outStream = new FileOutputStream(out);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);
            int originalWidth = imageWrapper.getWidth();
            int originalHeight = imageWrapper.getHeight();

            // 处理宽高
            //noinspection Duplicates
            if (scaleWidth <= 0 && scaleHeight <= 0) {
                scaleWidth = DEFAULT_SIZE;
                scaleHeight = DEFAULT_SIZE;
            } else if (scaleWidth <= 0) {
                scaleWidth = getWidth(originalWidth, originalHeight, scaleHeight);
            } else if (scaleHeight <= 0) {
                scaleHeight = getHeight(originalWidth, originalHeight, scaleWidth);
            }

            // 需要对原图进行缩略，然后再做裁剪
            // 缩略
            ScaleParameter scaleParam = new ScaleParameter(scaleWidth, scaleHeight);
            PlanarImage planarImage = ImageScaleHelper.scale(imageWrapper.getAsPlanarImage(), scaleParam);

            // 裁剪
            BufferedImage scaleImage = new ImageWrapper(planarImage).getAsBufferedImage();
            BufferedImage outputImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            graphic = outputImage.createGraphics();
            graphic.setComposite(AlphaComposite.Src);
            graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphic.setColor(Color.WHITE);
            graphic.fill(new RoundRectangle2D.Float(0, 0, diameter, diameter, diameter, diameter));
            graphic.setComposite(AlphaComposite.SrcAtop);
            // 这个方法的参数 x 和 y 是：圆在用户空间中呈现该图像的左上角位置的坐标，貌似要用负的值
            graphic.drawImage(scaleImage, (int) -(center.getX() - radius), (int) -(center.getY() - radius), null);

            // 输出
            wr = new WriteRender(new ImageWrapper(outputImage), outStream, OUTPUT_FORMAT);
            wr.render();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // noinspection deprecation
            IOUtils.closeQuietly(inStream);
            // noinspection deprecation
            IOUtils.closeQuietly(outStream);
            if (wr != null) {
                try {
                    //释放simpleImage的内部资源
                    wr.dispose();
                } catch (SimpleImageException e) {
                    e.printStackTrace();
                }
            }
            if (graphic != null) {
                //释放Graphics2D的资源
                graphic.dispose();
            }
        }
    }

    /**
     * 打水印
     *
     * @param originalWrapper 原图片，ImageWrapper
     * @return ImageWrapper
     */
    private static ImageWrapper drawWatermark(ImageWrapper originalWrapper) {
        FileInputStream waterStream;
        try {
            waterStream = new FileInputStream(WATERMARK_URL);
            ImageWrapper waterWrapper = ImageReadHelper.read(waterStream);
            Point point = calWaterPoint(originalWrapper.getWidth(), originalWrapper.getHeight(),
                    waterWrapper.getWidth(), waterWrapper.getHeight());
            WatermarkParameter param = new WatermarkParameter(waterWrapper, 1f, (int) point.getX(),
                    (int) point.getY());
            BufferedImage bufferedImage = ImageDrawHelper.drawWatermark(originalWrapper.getAsBufferedImage(), param);
            return new ImageWrapper(bufferedImage);
        } catch (Exception e) {
            e.printStackTrace();
            return originalWrapper;
        }
    }

    /**
     * 裁剪，矩形
     *
     * @param source      原图片
     * @param target      目的图片
     * @param x           裁剪区域左上角顶点的 x 坐标
     * @param y           裁剪区域左上角顶点的 y 坐标
     * @param width       裁剪宽度
     * @param height      裁剪高度
     */
    public static void crop(String source, String target, int x, int y, int width, int height) {
        File in = new File(source);
        File out = new File(target);

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        ImageRender wr = null;
        try {
            inStream = new FileInputStream(in);
            outStream = new FileOutputStream(out);
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);

            // 裁剪
            CropParameter cropParam = new CropParameter(x, y, width, height);
            PlanarImage planarImage = ImageCropHelper.crop(imageWrapper.getAsPlanarImage(), cropParam);
            wr = new WriteRender(new ImageWrapper(planarImage), outStream, OUTPUT_FORMAT);

            //触发图像处理
            wr.render();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // noinspection deprecation
            IOUtils.closeQuietly(inStream);
            // noinspection deprecation
            IOUtils.closeQuietly(outStream);
            if (wr != null) {
                try {
                    //释放simpleImage的内部资源
                    wr.dispose();
                } catch (SimpleImageException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 打文字水印
     *
     * @param imageWrapper 原图片，ImageWrapper
     * @param text         水印文字
     * @return ImageWrapper
     */
    private static ImageWrapper drawWatermark(ImageWrapper imageWrapper, String text) {
        DrawTextItem waterText = new FixDrawTextItem(text, FixDrawTextItem.Position.BOTTOM_RIGHT, 0.5f);
        // simpleimage 很坑地没有实现默认字体，这里直接指定一种字体
        waterText.setFont(new Font("方正黑体", Font.PLAIN, 14));
        DrawTextParameter param = new DrawTextParameter();
        param.addTextInfo(waterText);
        BufferedImage bufferedImage = imageWrapper.getAsBufferedImage();
        ImageDrawHelper.drawText(bufferedImage, param);
        return new ImageWrapper(bufferedImage);
    }

    /**
     * 计算水印位置
     *
     * @param enclosingWidth  外围宽度（图片宽度）
     * @param enclosingHeight 外围高度（图片高度）
     * @param width           水印宽度
     * @param height          水印高度
     * @return Point
     */
    private static Point calWaterPoint(int enclosingWidth, int enclosingHeight,
                                       int width, int height) {
        int x = (enclosingWidth / 2) - (width / 2);
        int y = (enclosingHeight / 2) - (height / 2);
        return new Point(x, y);
    }

    /**
     * 根据宽、高和目标宽度 等比例求高度
     *
     * @param w     原宽度
     * @param h     原高度
     * @param width 目标宽度
     * @return int
     */
    private static int getHeight(int w, int h, int width) {
        return h * width / w;
    }

    /**
     * 根据宽、高和目标高度 等比例求宽度
     *
     * @param w      原宽度
     * @param h      原高度
     * @param height 目标高度
     * @return int
     */
    private static int getWidth(int w, int h, int height) {
        return w * height / h;
    }

    /**
     * 构造输出文件路径
     *
     * @param path 文件上级目录
     * @return 文件路径
     */
    public static String generateTargetPath(String path) {
        return path + "/" + OUTPUT_FILENAME;
    }
}
