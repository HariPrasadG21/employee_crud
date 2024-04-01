package com.sc.service;

import com.sc.dao.EmpDao;
import com.sc.entity.Emp;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class EmpServiceImpl implements EmpService
{
    @Autowired
    private EmpDao empDao;
    @Override
    @Transactional
    public int saveEmp(Emp emp) {
        int i = empDao.saveEmp(emp);
        return i;
    }
    public Emp getEmpById(int id) {
        Emp emp = empDao.getEmpById(id);
        return emp;
    }
    public List<Emp> getAllEmp() {

        List<Emp> emps = empDao.getAllEmp();
        return emps;
    }
    public void update(Emp emp) {
        empDao.update(emp);

    }
    public void deleteEmp(int id) {
        empDao.deleteEmp(id);
    }
}
