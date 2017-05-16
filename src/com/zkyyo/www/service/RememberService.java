package com.zkyyo.www.service;

import com.zkyyo.www.dao.RememberDao;

public class RememberService {
    private RememberDao rememberDao;

    public RememberService(RememberDao rememberDao) {
        this.rememberDao = rememberDao;
    }

    public void save(String uuid, String username) {
        rememberDao.addRemember(uuid, username);
    }

    public String find(String uuid) {
        return rememberDao.selectUsername(uuid);
    }

    public void delete(String username) {
        rememberDao.delete(username);
    }
}
