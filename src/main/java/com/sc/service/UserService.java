package com.sc.service;

import com.sc.entity.User;

import java.util.List;

public interface UserService
{
    public int saveUser(User user);
    public User getUserById(int id);

    public List<User> getAllUser();

    public void update(User user);

    public void deleteUser(int id);
    public User loginUser(String email,String password);
}
