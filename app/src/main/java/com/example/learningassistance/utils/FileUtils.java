package com.example.learningassistance.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.example.learningassistance.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class FileUtils {

    public static void openFile(Context context, File file, String ext){
        if (!file.exists()){
            Toast.makeText(context, "No Found", Toast.LENGTH_SHORT).show();
            return;
        }
        String type = getType(ext);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context,context.getApplicationContext().getPackageName() + ".FileProvider",file);
            intent.setDataAndType(contentUri,type);
        } else {
            Uri uri = Uri.fromFile(file);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri,type);
        }
        context.startActivity(intent);
    }

    /**
     * 获取文件扩展名
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getFileExt(String fileName) {
        String ext = "";
        int pos = fileName.lastIndexOf(".");
        ext = fileName.substring(pos + 1, fileName.length());
        return ext.toLowerCase();
    }

    /**
     * 根据网络地址获得文件名
     * @param url 网络地址
     * @return 文件名
     */
    public static String getFileName(String url){
        String name = "";
        int pos = url.lastIndexOf('/');
        name = url.substring(pos + 1, url.length());
        return name;
    }

    /**
     * 根据扩展名适配图标
     * @param ext 扩展名
     * @return 图标资源ID
     */
    public static int getImgId(String ext){
        switch (ext){
            case "apk":
                return R.drawable.ic_file_apk;
            //压缩包
            case "zip":
            case "rar":
            case "tar":
            case "7z":
                return R.drawable.ic_file_archive;
            //视频
            case "avi":
            case "mp4":
            case "mpeg":
            case "mov":
            case "wmv":
                return R.drawable.ic_file_video;
            //音频
            case "mp3":
            case "wav":
            case "wma":
                return R.drawable.ic_file_audio;
            //图片
            case "bmp":
            case "png":
            case "jpg":
            case "jpeg":
            case "ico":
            case "gif":
                return R.drawable.ic_file_pic;
            //文本
            case "txt":
            case "log":
                return R.drawable.ic_file_txt;
            //文档
            case "doc":
            case "docx":
                return R.drawable.ic_file_word;
            case "xls":
            case "xlsx":
                return R.drawable.ic_file_excel;
            case "ppt":
            case "pptx":
                return R.drawable.ic_file_ppt;
            case "pdf":
                return R.drawable.ic_file_pdf;
            //代码
            case "html":
            case "xml":
            case "java":
            case "json":
            case "c":
            case "cpp":
            case "h":
            case "py":
                return R.drawable.ic_file_code;
            //默认
            case "":
            default:
                return R.drawable.ic_file_default;
        }
    }


    /**
     * 根据扩展名适配打开类型
     * @param ext 文件扩展名
     * @return 打开类型
     */
    private static String getType(String ext) {
        switch (ext){
            case "3gp":return "video/3gpp";
            case "apk":return "application/vnd.android.package-archive";
            case "asf":return "video/x-ms-asf";
            case "avi":return "video/x-msvideo";
            case "bin":return "application/octet-stream";
            case "bmp":return "image/bmp";
            case "c":return "text/plain";
            case "class":return "application/octet-stream";
            case "conf":return "text/plain";
            case "cpp":return "text/plain";
            case "doc":return "application/msword";
            case "docx":return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls":return "application/vnd.ms-excel";
            case "xlsx":return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "exe":return "application/octet-stream";
            case "gif":return "image/gif";
            case "gtar":return "application/x-gtar";
            case "gz":return "application/x-gzip";
            case "h":return "text/plain";
            case "htm":return "text/html";
            case "html":return "text/html";
            case "jar":return "application/java-archive";
            case "java":return "text/plain";
            case "jpeg":return "image/jpeg";
            case "jpg":return "image/jpeg";
            case "js":return "application/x-javascript";
            case "log":return "text/plain";
            case "m3u":return "audio/x-mpegurl";
            case "m4a":return "audio/mp4a-latm";
            case "m4b":return "audio/mp4a-latm";
            case "m4p":return "audio/mp4a-latm";
            case "m4u":return "video/vnd.mpegurl";
            case "m4v":return "video/x-m4v";
            case "mov":return "video/quicktime";
            case "mp2":return "audio/x-mpeg";
            case "mp3":return "audio/x-mpeg";
            case "mp4":return "video/mp4";
            case "mpc":return "application/vnd.mpohun.certificate";
            case "mpe":return "video/mpeg";
            case "mpeg":return "video/mpeg";
            case "mpg":return "video/mpeg";
            case "mpg4":return "video/mp4";
            case "mpga":return "audio/mpeg";
            case "msg":return "application/vnd.ms-outlook";
            case "ogg":return "audio/ogg";
            case "pdf":return "application/pdf";
            case "png":return "image/png";
            case "pps":return "application/vnd.ms-powerpoint";
            case "ppt":return "application/vnd.ms-powerpoint";
            case "pptx":return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "prop":return "text/plain";
            case "rc":return "text/plain";
            case "rmvb":return "audio/x-pn-realaudio";
            case "rtf":return "application/rtf";
            case "sh":return "text/plain";
            case "tar":return "application/x-tar";
            case "tgz":return "application/x-compressed";
            case "txt":return "text/plain";
            case "wav":return "audio/x-wav";
            case "wma":return "audio/x-ms-wma";
            case "wmv":return "audio/x-ms-wmv";
            case "wps":return "application/vnd.ms-works";
            case "xml":return "text/plain";
            case "z":return "application/x-compress";
            case "zip":return "application/x-zip-compressed";
            case "":
            default:return "*/*";
        }
    }

    public static void getDownLoad(String url, String name) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            HttpURLConnection connection = getConnection(url);
            int contentLength = connection.getContentLength();
            System.out.println("文件的大小是:" + contentLength);
            if (contentLength > 32) {
                InputStream is = getConnection(url).getInputStream();
                bis = new BufferedInputStream(is);
                FileOutputStream fos = new FileOutputStream(name);
                bos = new BufferedOutputStream(fos);
                int b = 0;
                byte[] byArr = new byte[1024];
                while ((b = bis.read(byArr)) != -1) {
                    bos.write(byArr, 0, b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*Message message = new Message();
        message.arg1 = 1;
        handler.sendMessage(message);*/
    }

    public static HttpURLConnection getConnection(String httpUrl) throws Exception {
        URL url = new URL(httpUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
        connection.setRequestProperty("Accept","*/*");
        connection.connect();
        return connection;
    }

}
