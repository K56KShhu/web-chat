package com.zkyyo.www.dao;

public interface RememberDao {
    void addRemember(String uuid, String username);

    void delete(String username);

    String selectUsername(String uuid);

}
