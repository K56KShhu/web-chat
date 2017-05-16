package com.zkyyo.www.dao;

public interface RememberDao {
    String selectUsername(String uuid);
    void addRemember(String uuid, String username);
    void delete(String username);
}
