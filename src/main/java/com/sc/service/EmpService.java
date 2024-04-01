package com.sc.service;

import com.sc.entity.Emp;

import java.util.List;

public interface EmpService {
    public int saveEmp(Emp emp);
    public Emp getEmpById(int id);

    public List<Emp> getAllEmp();

    public void update(Emp emp);

    public void deleteEmp(int id);
}
