package com.zkyyo.www.bean;

import java.util.List;

public class PageBean<T> {
    private int currentPage;
    private int totalPage;
    private int totalRow;
    private int rowsOnePage = 10;
    private List<T> list;

    public PageBean() {

    }

    public PageBean(int currentPage, int totalRow, int rowsOnePage) {
        this.currentPage = currentPage;
        this.totalRow = totalRow;
        this.rowsOnePage = rowsOnePage;
        makePage();
    }

    private void makePage() {
        totalPage = totalRow / rowsOnePage + ((totalRow % rowsOnePage == 0) ? 0 : 1);
        if (currentPage < 1) {
            currentPage = 1;
        } else if (currentPage > totalPage) {
            currentPage = totalPage;
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

    @Deprecated
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRow() {
        return totalRow;
    }

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
