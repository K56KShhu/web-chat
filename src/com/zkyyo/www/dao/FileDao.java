package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.FilePo;

import java.util.List;

public interface FileDao {
    void addFile(FilePo filePo);
    List<FilePo> selectFilesByTopicId(int topicId, int apply);
    List<FilePo> selectFilesByTopicId(int currentPage, int rowsOnePage, int order, boolean isReverse, int topicId, int apply);
    FilePo selectFileByFileId(int fileId);
    void deleteFile(int fileId);
    int getTotalRow(int topicId);
}
