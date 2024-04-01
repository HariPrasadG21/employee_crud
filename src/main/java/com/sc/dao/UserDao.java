package com.sc.dao;

import com.sc.entity.Emp;
import com.sc.entity.User;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class UserDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Transactional(readOnly = false)
    public int saveUser(User user)
    {
        int i = (Integer) hibernateTemplate.save(user);
        return i;
    }
    public User getUserById(int id)
    {
        User user = hibernateTemplate.get(User.class,id);
        return user;
    }
    public List<User> getAllUser()
    {
        List<User> users = hibernateTemplate.loadAll(User.class);
        return users;
    }
    @Transactional
    public void update(User user) {
        hibernateTemplate.update(user);
    }

    @Transactional
    public void deleteUser(int id) {
        User user = hibernateTemplate.get(User.class, id);
        hibernateTemplate.delete(user);
    }

    public User loginUser(String email,String password)
    {
        String sql = "from User where email =:nm and password =:pwd";
        User user =(User) hibernateTemplate.execute(session -> {
            Query query = session.createQuery(sql);
            query.setParameter("nm",email);
            query.setParameter("pwd",password);
            return query.uniqueResult();
        });
                return user;
    }
}
