package com.employeeManagement.employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByDepartment(String department);

    List<Employee> findByOrderBySalaryDesc();

    List<Employee> findByOrderBySalary();

    List<Employee> findByDepartmentOrderBySalary(String department);

    List<Employee> findByDepartmentOrderBySalaryDesc(String department);

}
