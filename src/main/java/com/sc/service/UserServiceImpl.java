package com.sc.service;

import com.sc.dao.UserDao;
import com.sc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public int saveUser(User user) {
        int i = userDao.saveUser(user);
        return i;
    }

    @Override
    public User getUserById(int id) {
        User user = userDao.getUserById(id);
        return user;
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = userDao.getAllUser();
        return users;
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public User loginUser(String email, String password) {
        User user = userDao.loginUser(email, password);
        return user;
    }
}
