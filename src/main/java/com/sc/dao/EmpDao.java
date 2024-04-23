package com.sc.dao;

import com.sc.entity.Emp;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class EmpDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Transactional(readOnly = false)
    public int saveEmp(Emp emp) {

        int i = (Integer) hibernateTemplate.save(emp);
        return i;
    }

    public Emp getEmpById(int id) {
        Emp emp = hibernateTemplate.get(Emp.class, id);
        return emp;
    }

    public List<Emp> getAllEmp() {
        List<Emp> list = hibernateTemplate.loadAll(Emp.class);
        return list;
    }

    @Transactional
    public void update(Emp emp) {
        hibernateTemplate.update(emp);

    }

    @Transactional
    public void deleteEmp(int id) {
        Emp emp = hibernateTemplate.get(Emp.class, id);
        hibernateTemplate.delete(emp);
    }
    public Emp findByUsername(String email)
    {
        String sql = "from emp where email =:nm";
        Emp emp =(Emp) hibernateTemplate.execute(session -> {
            Query query = session.createQuery(sql);
            query.setParameter("nm",email);
            return query.uniqueResult();
        });
        return emp;
    }
}
