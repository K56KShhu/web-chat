package com.zkyyo.www.bean;

import java.util.List;

/**
 * 用于封装分页对象, 包括分页所需的下标数据和待显示的数据列表
 *
 * @param <T> 待显示的数据类型
 */
public class PageBean<T> {
    private int currentPage; //当前页数
    private int totalPage; //总页数
    private int totalRow; //总行数
    private int rowsOnePage = 10; //每一页显示的行数, 默认为10行
    private List<T> list; //待显示的类型列表

    public PageBean() {

    }

    /**
     * 获取计算分页下标所需的数据, 同时计算出总页数
     *
     * @param currentPage 当前页数
     * @param totalRow 总行数
     * @param rowsOnePage 每一页显示的行数
     */
    public PageBean(int currentPage, int totalRow, int rowsOnePage) {
        this.currentPage = currentPage;
        this.totalRow = totalRow;
        this.rowsOnePage = rowsOnePage;
        makePage();
    }

    /**
     * 计算总页数
     */
    private void makePage() {
        //获取数据的最大页数, 不足一页按一页计算
        totalPage = totalRow / rowsOnePage + ((totalRow % rowsOnePage == 0) ? 0 : 1);

        if (totalPage != 0) {
            //检查页数是否合法
            if (currentPage < 1) {
                currentPage = 1;
            } else if (currentPage > totalPage) {
                currentPage = totalPage;
            }
        } else {
            currentPage = 1;
        }
    }

    public int getRowsOnePage() {
        return rowsOnePage;
    }

    public void setRowsOnePage(int rowsOnePage) {
        this.rowsOnePage = rowsOnePage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 设置总页数, 总页数应当有计算得出
     * @param totalPage 总页数
     */
    @Deprecated
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRow() {
        return totalRow;
    }

    /**
     * 设置总行数, 总行数应该由数据库计算得出
     * @param totalRow 总行数
     */
    @Deprecated
    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "rowsOnePage=" + rowsOnePage +
                ", currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", totalRow=" + totalRow +
                ", list=" + list +
                '}';
    }
}
