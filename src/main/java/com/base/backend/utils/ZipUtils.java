package com.base.backend.utils;

import com.base.backend.utils.helper.ExtensionFilter;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 文件压缩解压工具类
 */
public class ZipUtils {
    /**
     * 压缩ZIP文件，将baseDirName目录下的所有文件压缩到targetFileName.ZIP文件中
     *
     * @param baseDirName    需要压缩的文件的根目录
     * @param targetFileName 压缩有生成的文件名
     * @param ext            后缀名数组
     */
    public static void zipFile(String baseDirName, String targetFileName, String[] ext) {
        if (baseDirName == null) {
            return;
        }
        File file = new File(baseDirName);
        if (!file.exists()) {
            return;
        }

        try {
            File[] files = file.listFiles(new ExtensionFilter(ext));
            compressFiles2Zip(files, targetFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压缩ZIP文件，将ZIP文件里的内容解压到targetBaseDirName目录下(默认允许所有的文件类型)
     *
     * @param zipFileName       待解压缩的ZIP文件名
     * @param targetBaseDirName 目标目录
     */
    public static void unzipFile(String zipFileName, String targetBaseDirName) {
        unzipFile(zipFileName, targetBaseDirName, null);
    }

    /**
     * 解压缩ZIP文件，将ZIP文件里的内容解压到targetBaseDirName目录下
     *
     * @param zipFileName       待解压缩的ZIP文件名
     * @param targetBaseDirName 目标目录
     * @param fileFilterRegex   (压缩包中允许含有的文件类型)
     */
    @SuppressWarnings("unchecked")
    public static void unzipFile(String zipFileName, String targetBaseDirName, String fileFilterRegex) {
        if (!targetBaseDirName.endsWith(File.separator)) {
            targetBaseDirName += File.separator;
        }
        InputStream is = null;
        FileOutputStream os = null;
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zipFileName);
            ZipEntry entry = null;
            String entryName = null;
            String targetFileName = null;
            byte[] buffer = new byte[4096];
            int bytes_read;
            Enumeration entrys = zipFile.entries();//.entries();
            while (entrys.hasMoreElements()) {
                entry = (ZipEntry) entrys.nextElement();
                entryName = entry.getName();
                targetFileName = targetBaseDirName + entryName;
                targetFileName = StringUtils.isNotEmpty(fileFilterRegex) ?
                        FileUtils.filterPath(targetFileName, fileFilterRegex) : FileUtils.filterPath(targetFileName);
                if (entry.isDirectory()) {
                    new File(targetFileName).mkdirs();
                    continue;
                } else {
                    new File(targetFileName).getParentFile().mkdirs();
                }
                File targetFile = new File(targetFileName);
                try {
                    os = new FileOutputStream(targetFile);
                    is = zipFile.getInputStream(entry);
                    if (is != null) {
                        while ((bytes_read = is.read(buffer)) != -1) {
                            if (os != null) {
                                os.write(buffer, 0, bytes_read);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (os != null) {
                        try {
                            os.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
                if (zipFile != null) {
                    try {
                        zipFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void dirToZip(String baseDirPath, File dir, ZipOutputStream out) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files.length == 0) {
                ZipEntry entry = new ZipEntry(getEntryName(baseDirPath, dir));
                try {
                    out.putNextEntry(entry);
                    out.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    fileToZip(baseDirPath, files[i], out);
                } else {
                    dirToZip(baseDirPath, files[i], out);
                }
            }
        }
    }

    private static void fileToZip(String baseDirPath, File file, ZipOutputStream out) {
        FileInputStream in = null;
        ZipEntry entry = null;
        byte[] buffer = new byte[4096];
        int bytes_read;
        if (file.isFile()) {
            try {
                in = new FileInputStream(file);
                entry = new ZipEntry(getEntryName(baseDirPath, file));
                out.putNextEntry(entry);
                while ((bytes_read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytes_read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static String getEntryName(String baseDirPath, File file) {
        if (!baseDirPath.endsWith(FileUtils.SEPARATOR)) {
            baseDirPath += FileUtils.SEPARATOR;
        }
        String filePath = file.getAbsolutePath();
        if (file.isDirectory()) {
            filePath += FileUtils.SEPARATOR;
        }
        int index = filePath.indexOf(baseDirPath);
        return filePath.substring(index + baseDirPath.length());
    }

    /**
     * 把文件压缩成zip格式
     *
     * @param files       需要压缩的文件
     * @param zipFilePath 压缩后的zip文件路径   ,如"D:/test/aa.zip";
     */
    public static void compressFiles2Zip(File[] files, String zipFilePath) {
        if (files != null && files.length > 0) {
            if (isEndsWithZip(zipFilePath)) {
                ZipArchiveOutputStream zaos = null;
                try {
                    File zipFile = new File(zipFilePath);
                    zaos = new ZipArchiveOutputStream(zipFile);
                    //Use Zip64 extensions for all entries where they are required
                    zaos.setUseZip64(Zip64Mode.AsNeeded);

                    //将每个文件用ZipArchiveEntry封装
                    //再用ZipArchiveOutputStream写到压缩文件中
                    for (File file : files) {
                        if (file != null) {
                            ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, file.getName());
                            zaos.putArchiveEntry(zipArchiveEntry);
                            InputStream is = null;
                            try {
                                is = new BufferedInputStream(new FileInputStream(file));
                                byte[] buffer = new byte[1024 * 5];
                                int len = -1;
                                while ((len = is.read(buffer)) != -1) {
                                    //把缓冲区的字节写入到ZipArchiveEntry
                                    zaos.write(buffer, 0, len);
                                }
                                //Writes all necessary data for this entry.
                                zaos.closeArchiveEntry();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            } finally {
                                if (is != null)
                                    is.close();
                            }

                        }
                    }
                    zaos.finish();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        if (zaos != null) {
                            zaos.close();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }
    }

    /**
     * 判断文件名是否以.zip为后缀
     *
     * @param fileName 需要判断的文件名
     * @return 是zip文件返回true, 否则返回false
     */
    public static boolean isEndsWithZip(String fileName) {
        boolean flag = false;
        if (fileName != null && !"".equals(fileName.trim())) {
            if (fileName.endsWith(".ZIP") || fileName.endsWith(".zip")) {
                flag = true;
            }
        }
        return flag;
    }
}
