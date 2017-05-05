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
        return fileDao.selectFiles(topicId, apply);
    }
}
