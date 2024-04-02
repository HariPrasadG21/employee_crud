package com.sc.dao;

import com.sc.entity.Users;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsersDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;
    public Users loginUser(String email, String password)
    {
        String sql = "from Users where username =:nm and password =:pwd";
        Users user =(Users) hibernateTemplate.execute(session -> {
            Query query = session.createQuery(sql);
            query.setParameter("nm",email);
            query.setParameter("pwd",password);
            return query.uniqueResult();
        });
        return user;
    }
    public Users getByUsername(String username)
    {
        String sql = "from Users where username =:nm ";
        Users user =(Users) hibernateTemplate.execute(session -> {
            Query query = session.createQuery(sql);
            query.setParameter("nm",username);
            return query.uniqueResult();
        });
        return user;
    }
    @Transactional(readOnly = false)
    public int saveUser(Users user)
    {
        String username = (String) hibernateTemplate.save(user);
        return 1;
    }
}
