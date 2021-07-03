package com.ua.alevel.jsonCrud.service;

import com.ua.alevel.jsonCrud.controller.CmdMenage;
import com.ua.alevel.jsonCrud.dao.User;
import com.ua.alevel.jsonCrud.dao.UserDao;
import com.ua.alevel.jsonCrud.exception.UserNotExist;
import lombok.SneakyThrows;

import java.util.List;

public class CrudService {
    private final UserDao userDao = new UserDao();

    @SneakyThrows
    public void create(User user) {
        CmdMenage cmdMenage = new CmdMenage();
        if (!userDao.existByEmail(user.getEmail())) {
            userDao.create(user);
        } else {
            System.out.println("Пользователь с таким Email уже существует");
            cmdMenage.inputCmd();
        }

    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(String id){
        userDao.delete(id);
    }

    public User findById(String id) {
        return userDao.find(id);
    }

    public List<User> showAll() {
        return userDao.showAll();
    }


}
