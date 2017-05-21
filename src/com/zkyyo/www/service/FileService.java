package com.zkyyo.www.service;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.dao.FileDao;
import com.zkyyo.www.bean.po.FilePo;
import com.zkyyo.www.dao.impl.FileDaoJdbcImpl;
import com.zkyyo.www.util.CheckUtil;

import java.util.List;

/**
 * 提供与文件信息相关, 用于逻辑处理的代码
 * 该类包含增删查改方法和校验方法
 */
public class FileService {
    /**
     * 分享类型标识符, 表示图片分享
     */
    public static final int APPLY_IMAGE = 0;
    /**
     * 分享类型标识符, 表示文件分享
     */
    public static final int APPLY_FILE = 1;
    /**
     * 排序标识符, 排序依据为分享时间
     */
    public static final int ORDER_BY_CREATED = 0;
    /**
     * 分页系统中, 表示每一页显示的行数
     */
    private static final int ROWS_ONE_PAGE = 15;
    /**
     * 文件唯一标识ID的最大长度
     */
    private static final int MAX_ID_LENGTH = 10;
    /**
     * 数据操作相关的接口
     */
    private FileDao fileDao;

    /**
     * 构造函数
     *
     * @param fileDao 传入的数据库操作接口
     */
    public FileService(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    /**
     * 校验文件ID是否合法
     *
     * @param fileId 待校验的文件ID
     * @return true合法 false不合法
     */
    public boolean isValidId(String fileId) {
        return CheckUtil.isValidInteger(CheckUtil.NUMBER_POSITIVE, fileId, MAX_ID_LENGTH);
    }

    /**
     * 校验文件信息是否存在
     *
     * @param fileId 待校验的文件ID
     * @return true存在, false不存在
     */
    public boolean isExisted(int fileId) {
        return findFile(fileId) != null;
    }

    /**
     * 添加文件信息
     *
     * @param filePo 待添加的文件对象
     */
    public void addFile(FilePo filePo) {
        fileDao.addFile(filePo);
    }

    /**
     * 删除文件信息
     *
     * @param fileId 待删除的文件ID
     */
    public void deleteFile(int fileId) {
        fileDao.deleteFile(fileId);
    }

    /**
     * 通过文件ID精确查找文件信息
     *
     * @param fileId 待查找的文件ID
     * @return 存在返回文件对象, 不存在返回null
     */
    public FilePo findFile(int fileId) {
        return fileDao.selectFile(fileId);
    }

    /**
     * 通过排序方式查询一个讨论区中指定分享类型的文件信息, 同时进行排序
     *
     * @param currentPage 当前页数
     * @param order       排序依据
     * @param isReverse   是否倒序
     * @param topicId     讨论区ID
     * @param apply       分享类型
     * @return 封装的分页对象, 信息输入不合法时返回null
     */
    public PageBean<FilePo> queryFiles(int currentPage, int order, boolean isReverse, int topicId, int apply) {
        int orderType;
        //判断排序依据
        if (ORDER_BY_CREATED == order) {
            orderType = FileDaoJdbcImpl.ORDER_BY_CREATED;
        } else {
            return null;
        }
        int applyType;
        //判断分享类型
        if (APPLY_IMAGE == apply) {
            applyType = FileDaoJdbcImpl.APPLY_IMAGE;
        } else if (APPLY_FILE == apply) {
            applyType = FileDaoJdbcImpl.APPLY_FILE;
        } else {
            return null;
        }

        //设置分页对象
        PageBean<FilePo> pageBean = new PageBean<>(currentPage, fileDao.getTotalRow(topicId, applyType), ROWS_ONE_PAGE);
        //计算起始下标
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;
        List<FilePo> files = fileDao.selectFilesByTopicId(startIndex, ROWS_ONE_PAGE, orderType, isReverse, topicId, applyType);
        pageBean.setList(files);
        return pageBean;
    }
}
