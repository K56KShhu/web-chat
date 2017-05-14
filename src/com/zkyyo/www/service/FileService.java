package com.zkyyo.www.service;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.dao.FileDao;
import com.zkyyo.www.bean.po.FilePo;
import com.zkyyo.www.dao.impl.FileDaoJdbcImpl;
import com.zkyyo.www.util.CheckUtil;

import java.util.List;

public class FileService {
    public static final int APPLY_IMAGE = 1;
    public static final int APPLY_FILE = 2;

    public static final int ORDER_BY_CREATED = 0;

    private static final int ROWS_ONE_PAGE = 15;

    private static final int MAX_ID_LENGTH = 10;

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
        return CheckUtil.isValidId(fileId, MAX_ID_LENGTH);
    }

    public boolean isExisted(int fileId) {
        return findFile(fileId) != null;
    }

    public FilePo findFile(int fileId) {
        return fileDao.selectFileByFileId(fileId);
    }

    public void deleteFile(int fileId) {
        fileDao.deleteFile(fileId);
    }

    public PageBean<FilePo> queryFiles(int currentPage, int order, boolean isReverse, int topicId, int apply) {
        PageBean<FilePo> pageBean = new PageBean<>(currentPage, fileDao.getTotalRow(topicId), ROWS_ONE_PAGE);
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;

        List<FilePo> files;
        if (APPLY_IMAGE == apply) {
            if (ORDER_BY_CREATED == order) {
                files = fileDao.selectFilesByTopicId(startIndex, ROWS_ONE_PAGE, FileDaoJdbcImpl.ORDER_BY_CREATED, isReverse, topicId, FileDaoJdbcImpl.APPLY_IMAGE);
            } else {
                return null;
            }
        } else if (APPLY_FILE == apply) {
            files = fileDao.selectFilesByTopicId(startIndex, ROWS_ONE_PAGE, FileDaoJdbcImpl.ORDER_BY_CREATED, isReverse, topicId, FileDaoJdbcImpl.APPLY_FILE);
        } else {
            return null;
        }
        pageBean.setList(files);
        return pageBean;
    }
}
