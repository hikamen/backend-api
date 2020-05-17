package com.base.backend.core.web.multipart;

import com.google.common.collect.Maps;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class FileUploadUtils {
    public final static String FILE_UPLOAD_PROGRESS_MAP_KEY = "FILE-UPLOAD-PROGRESS-MAP-KEY";
    public final static String FILE_UPLOAD_PROGRESS_SESSION_KEY = "FILE-UPLOAD-PROGRESS-ID";
    public final static String UPLOAD_PROGRESS_ID_PARAM_KEY = "progressId";

    @SuppressWarnings("unchecked")
    public static Map<String, FileUploadProgress> getProgressMap(HttpSession session) {
        if (session.getAttribute(FILE_UPLOAD_PROGRESS_MAP_KEY) == null) {
            session.setAttribute(FILE_UPLOAD_PROGRESS_MAP_KEY, Maps.newHashMap());
        }
        return (Map<String, FileUploadProgress>) session.getAttribute(FILE_UPLOAD_PROGRESS_MAP_KEY);
    }

    public static FileUploadProgress getProgress(String progressId, HttpSession session) {
        Map<String, FileUploadProgress> map = getProgressMap(session);
        if (map.get(progressId) == null) {
            map.put(progressId, new FileUploadProgress());
        }
        session.setAttribute(FILE_UPLOAD_PROGRESS_MAP_KEY, map);
        return map.get(progressId);
    }

    public static void updateProgress(String progressId, FileUploadProgress progress, HttpSession session) {
        Map<String, FileUploadProgress> map = getProgressMap(session);
        map.put(progressId, progress);
        session.setAttribute(FILE_UPLOAD_PROGRESS_MAP_KEY, map);
    }
}
