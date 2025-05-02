package com.lingkesh.employeeAPI.repo;

import com.lingkesh.employeeAPI.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
}
