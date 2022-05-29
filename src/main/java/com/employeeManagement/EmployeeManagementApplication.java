package com.employeeManagement;

import java.util.*;


import javax.annotation.PostConstruct;

import com.employeeManagement.employee.Employee;
import com.employeeManagement.employee.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}
@Autowired
EmployeeRepository employeeRepository;

	@PostConstruct
	public void init(){
		final Employee employeeOne = new Employee(1, "Emp", "One", "Front-end", 20000);
		final Employee employeeTwo = new Employee(2, "Emp", "Two", "Back-end", 17000);
		final Employee employeeThree = new Employee(3, "Emp", "Three", "Full-stack", 35000);
		final Employee employeeFour = new Employee(4, "Emp", "Four", "Front-end", 25000);
		final Employee employeeFive = new Employee(5, "Emp", "Five", "Full-stack", 45000);
		final Employee employeeSix = new Employee(6, "Emp", "Six", "Full-stack", 150000);
		final Employee employeeSeven = new Employee(7, "Emp", "Seven", "Back-end", 40000);
		final Employee employeeEight = new Employee(8, "Emp", "Eight", "Full-stack", 85000);
		List<Employee> e= Arrays.asList(employeeOne,employeeTwo,employeeThree,employeeFour,employeeFive,employeeSix,employeeSeven,employeeEight);

		employeeRepository.saveAll(e);
	}
}
