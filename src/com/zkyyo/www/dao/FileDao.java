package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.FilePo;

import java.util.List;

public interface FileDao {
    void addFile(FilePo filePo);

    void deleteFile(int fileId);

    FilePo selectFile(int fileId);

    List<FilePo> selectFilesByTopicId(int currentPage, int rowsOnePage, int order, boolean isReverse, int topicId, int apply);

    int getTotalRow(int topicId);
}
