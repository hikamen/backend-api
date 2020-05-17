package com.base.backend.core.web.multipart;

import org.apache.commons.fileupload.ProgressListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 文件上传进度监听器
 */
public class FileUploadProgressListener implements ProgressListener {

    private HttpSession session;

    private String progressId = null;

    public FileUploadProgressListener(HttpServletRequest request) {
        session = request.getSession();

        String progressId;
        if (request.getParameter(FileUploadUtils.UPLOAD_PROGRESS_ID_PARAM_KEY) != null) {
            progressId = request.getParameter(FileUploadUtils.UPLOAD_PROGRESS_ID_PARAM_KEY);
        } else {
            progressId = FileUploadUtils.FILE_UPLOAD_PROGRESS_SESSION_KEY;
        }

        FileUploadUtils.getProgressMap(this.session).put(progressId, new FileUploadProgress());

        this.progressId = progressId;
    }

    /**
     * @param pBytesRead     已上传大小
     * @param pContentLength 文件总大小
     * @param pItems         文件数量
     */
    @Override
    public void update(final long pBytesRead, final long pContentLength, final int pItems) {
        FileUploadProgress progress = FileUploadUtils.getProgress(progressId, this.session);
        progress.setBytesRead(pBytesRead);
        progress.setContentLength(pContentLength);
        progress.setItems(pItems);

        // 更新Session里面的进度信息
        FileUploadUtils.updateProgress(this.progressId, progress, session);
    }
}