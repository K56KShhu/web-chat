package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.FilePo;

import java.util.List;

/**
 * 定义与上传文件相关, 涉及数据库操作的接口
 */
public interface FileDao {
    /**
     * 向数据库中插入文件信息
     *
     * @param filePo 待插入的文件对象
     */
    void addFile(FilePo filePo);

    /**
     * 删除数据库中的文件信息
     *
     * @param fileId 待删除的文件ID
     */
    void deleteFile(int fileId);

    /**
     * 获取数据库中指定文件ID的文件信息
     *
     * @param fileId 待获取的文件ID
     * @return 存在则返回该文件对象, 不存在则返回null
     */
    FilePo selectFile(int fileId);

    /**
     * 获取数据库中一个讨论区中指定文件类型的多个文件信息, 同时进行分页和排序
     *
     * @param startIndex  起始下标
     * @param rowsOnePage 获取的文件数量
     * @param order       排序依据
     * @param isReverse   是否降序
     * @param topicId     讨论区ID
     * @param apply       文件类型
     * @return 返回一个关于文件的列表, 不包含任何认为则为size为0的空列表
     */
    List<FilePo> selectFilesByTopicId(int startIndex, int rowsOnePage, int order, boolean isReverse, int topicId, int apply);

    /**
     * 获取数据库中指定讨论区ID和文件类型的所有文件数量
     *
     * @param topicId 讨论区ID
     * @param apply   文件类型
     * @return 文件数量
     */
    int getTotalRow(int topicId, int apply);
}
