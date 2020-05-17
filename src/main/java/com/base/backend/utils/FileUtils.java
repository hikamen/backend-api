package com.base.backend.utils;

import java.util.regex.Pattern;

public class FileUtils {
    //上传文件扩展名
    public final static String fileExt="(asx|asf|mpg|wmv|3gp|mp4|mov|avi|flv|mp3|wma|" +
            "aac|doc|docx|xls|xlsx|ppt|pptx|jpg|jpeg|gif|bmp|png|zip|xml|pdf|smcx|flash|swf|apk|txt)";

    public static final String SEPARATOR = System.getProperty("file.separator");

    /**
     * 路径过滤(默认可上传的文件类型过滤)
     * @param path
     * @return
     */
    public static String filterPath(String path){
        return filterPath(path,fileExt);
    }
    /**
     * 路径过滤
     * @param path
     * @param regex
     * @return
     */
    public static String filterPath(String path,String regex){
        String filetype=path.substring(path.lastIndexOf(".")+1, path.length());
        if(filetype.indexOf("/")==-1&&filetype.indexOf("\\")==-1){
            if(!Pattern.compile(StringUtils.isEmpty(regex)?fileExt:
                    regex).matcher(filetype.toLowerCase()).matches()){
                return "";
            }
        }
        return path.replace("..", "");
//		return path.replace("..","").replaceAll("[^a-z0-9A-Z\\-_\\./:"+(File.separator.equals("\\")?"\\\\":File.separator)+"]", "");
    }
}
