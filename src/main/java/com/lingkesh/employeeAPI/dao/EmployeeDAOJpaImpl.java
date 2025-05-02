package com.lingkesh.employeeAPI.dao;

import com.lingkesh.employeeAPI.entity.Employee;
import com.lingkesh.employeeAPI.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//import jakarta.persistence.EntityManager;
//import jakarta.persistence.TypedQuery;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO{

    private EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeDAOJpaImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee findById(int theId) {
        Optional<Employee> result = employeeRepo.findById(theId);

        Employee theEmployee;

        if(result.isPresent()){
            theEmployee = result.get();
        }else{
            throw new RuntimeException("Did not find the employee id: " + theId);
        }

        return theEmployee;
    }

    @Override
    public Employee save(Employee theEmployee) {
        return employeeRepo.save(theEmployee);
    }

    @Override
    public void deleteById(int theId) {
        Optional<Employee> result = employeeRepo.findById(theId);

        if(result.isPresent()){
            employeeRepo.deleteById(theId);
        }else{
            throw new RuntimeException("Did not find the employee id: " + theId);
        }
    }

    // define field for entity manager
//    private EntityManager entityManager;

    //set up constructor injection
//    @Autowired
//    public EmployeeDAOJpaImpl(EntityManager entityManager){
//        this.entityManager = entityManager;
//    }

//    @Override
//    public List<Employee> findAll() {
//        // create a query
//        TypedQuery<Employee> theQuery = entityManager.createQuery("from Employee", Employee.class);
//
//        //return the results
//        return theQuery.getResultList();
//    }
//
//    @Override
//    public Employee findById(int theId) {
//        //return employee by id
//        return entityManager.find(Employee.class, theId);
//    }
//
//    //Note: We don't use @Transactional at DAO Layer. It will be handled at Service layer
//    @Override
//    public Employee save(Employee theEmployee) {
//        //if id==0, then insert/save else update
//        return entityManager.merge(theEmployee);
//    }
//
//    @Override
//    public void deleteById(int theId) {
//        Employee theEmployee = entityManager.find(Employee.class, theId);
//
//        entityManager.remove(theEmployee);
//    }
}