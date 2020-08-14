package jal.dev.common.utils;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: <FileUtil><br>
 * Author:      mxdl<br>
 * Date:        2018/7/13<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class FileUtil {
    public static boolean isImageFile(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        String reg = ".+(\\.jpeg|\\.jpg|\\.gif|\\.bmp|\\.png).*";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(url.toLowerCase());
        return matcher.find();
    }

    public static boolean isVideoFile(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        String reg = ".+(\\.avi|\\.wmv|\\.mpeg|\\.mp4|\\.mov|\\.mkv|\\.flv|\\.f4v|\\.m4v|\\.rmvb|\\.rm|\\.rmvb|\\.3gp|\\.dat|\\.ts|\\.mts|\\.vob).*";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(url.toLowerCase());
        return matcher.find();
    }

    public static boolean isUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        String reg = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        return url.matches(reg);
    }

    public static byte[] getFileByte(String filename) {
        File f = new File(filename);
        if (!f.exists()) {
            return null;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            in.close();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getFileFormat(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        int point = fileName.lastIndexOf('.');
        if (point == -1) {
            return "";
        }
        return fileName.substring(point + 1);
    }

    public static final long KB = 1024;
    public static final long MB = 1024 * KB;
    public static final long GB = 1024 * MB;
    public static final long TB = 1024 * GB;

    public static String getFileSizeText(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (size >= TB) {
            return df.format((float) size / TB) + " TB";
        } else if (size >= GB) {
            return df.format((float) size / GB) + " GB";
        } else if (size >= MB) {
            return df.format((float) size / MB) + " MB";
        } else if (size >= KB) {
            return df.format((float) size / KB) + " KB";
        } else {
            return size + " B";
        }
    }
}
