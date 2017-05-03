package com.zkyyo.www.dao;

import javax.sql.DataSource;

public class ReplyDaoJdbcImpl implements ReplyDao{
    private DataSource dataSource;

    public ReplyDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
