package com.zkyyo.www.service;

import com.zkyyo.www.dao.FileDao;
import com.zkyyo.www.po.FilePo;

import java.util.List;

public class FileService {
    public static final int APPLY_IMAGE = 1;
    public static final int APPLY_FILE = 2;
    private FileDao fileDao;

    public FileService(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    public void addFile(FilePo filePo) {
        fileDao.addFile(filePo);
    }

    public List<FilePo> findFiles(int topicId, int apply) {
        return fileDao.selectFilesByTopicId(topicId, apply);
    }

    public boolean isValidId(String fileId) {
        return true;
    }

    public boolean isExisted(int fileId) {
        return true;
    }

    public FilePo findFile(int fileId) {
        return fileDao.selectFileByFileId(fileId);
    }

    public void deleteFile(int fileId) {
        fileDao.deleteFile(fileId);
    }
}
