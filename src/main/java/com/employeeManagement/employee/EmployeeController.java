package com.employeeManagement.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping()
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	// Write your endpoints here
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getEmployee(@RequestParam(name = "dept", required = false) String depart,
			@RequestParam(name = "salary", required = false) String SalaryOrder) {
		 System.out.println("department" + depart);
		// System.out.println(depart!=null);
		 System.out.println("salaryorder" + SalaryOrder);
		if (depart != null && SalaryOrder == null) { // department only
			System.out.println("inside department only");
			List<Employee> dept = employeeRepository.findByDepartment(depart);
			return new ResponseEntity<>(dept, HttpStatus.OK);

		} else if (depart == null && SalaryOrder!=null) { // salary only
			System.out.println("inside Salary only");
			if (SalaryOrder.equals("asc")) {

				List<Employee> dept = employeeRepository.findByOrderBySalary();
				System.out.println(dept);
				return new ResponseEntity<>(dept, HttpStatus.OK);

			} else {

				List<Employee> dept = employeeRepository.findByOrderBySalaryDesc();
				System.out.println(dept);
				return new ResponseEntity<>(dept, HttpStatus.OK);
			}

		} else if (depart != null && SalaryOrder != null) { // department and salary
			System.out.println("inside department and salary");
			if (SalaryOrder.equals("asc")) {
				List<Employee> dept = employeeRepository.findByDepartmentOrderBySalary(depart);
				return new ResponseEntity<>(dept, HttpStatus.OK);
			} else {
				List<Employee> dept = employeeRepository.findByDepartmentOrderBySalaryDesc(depart);
				return new ResponseEntity<>(dept, HttpStatus.OK);
			}

		} else {

			List<Employee> dept = employeeRepository.findAll();
			return new ResponseEntity<>(dept, HttpStatus.OK);
		}

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/employees")
	public ResponseEntity<String> createEmployee(@RequestBody Employee emp) {
		System.out.println(emp);
		if (emp.getFirstName().length() > 3 && emp.getLastName().length() > 3 && emp.getSalary() > 15000
				&& emp.getSalary() < 200000) {
			employeeRepository.save(emp);
			return new ResponseEntity<>("Employee created", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("name should have more than 3 char", HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<String> DeleteEmployee(@PathVariable Integer id) {
		employeeRepository.deleteById(id);
		return new ResponseEntity<>("deleted sucessfully", HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> UpdateEmployee(@PathVariable Integer id, @RequestBody Employee emp) {
		Employee e = new Employee();
		e = employeeRepository.findById(id).get();
		if(emp.getFirstName()!=null && emp.getLastName() !=null){
		e.setFirstName(emp.getFirstName());
		e.setLastName(emp.getLastName());
		}
		
		e.setDepartment(emp.getDepartment());
		e.setSalary(emp.getSalary());
		employeeRepository.save(e);
		return new ResponseEntity<>(e, HttpStatus.OK);
	}

}
