package com.base.backend.utils.helper;

import com.google.common.collect.Lists;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

public class ExtensionFilter implements FilenameFilter {

    private List<String> exts = Lists.newArrayList();

    public ExtensionFilter(String ext) {
        if (StringUtils.isNoneBlank(ext)) {
            exts.add(ext);
        }
    }

    public ExtensionFilter(String[] ext) {
        if (ext != null) {
            exts.addAll(Lists.newArrayList(ext));
        }
    }

    @Override
    public boolean accept(File dir, String name) {
        // 获取后缀名
        String extension = FilenameUtils.getExtension(name);
        for (String ext : exts) {
            if (ext.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
