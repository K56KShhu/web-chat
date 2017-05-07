package com.zkyyo.www.dao;

import com.zkyyo.www.po.FilePo;

import java.util.List;

public interface FileDao {
    void addFile(FilePo filePo);
    List<FilePo> selectFiles(int fileId, int apply);
    FilePo selectFileByFileId(int fileId);
    void deleteFile(int fileId);
}
