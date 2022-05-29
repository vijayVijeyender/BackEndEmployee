package com.employeeManagement;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.employeeManagement.employee.Employee;
import com.employeeManagement.employee.EmployeeRepository;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@TestMethodOrder(OrderAnnotation.class)
class EmployeeManagementApplicationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	EmployeeRepository employeeRepository;

	@BeforeEach
	void init() throws Exception {
		insertIntoDB();
	}

	public void insertIntoDB() throws Exception {
		final Employee employeeOne = new Employee(1, "Emp", "One", "Front-end", 20000);
		final Employee employeeTwo = new Employee(2, "Emp", "Two", "Back-end", 17000);
		final Employee employeeThree = new Employee(3, "Emp", "Three", "Full-stack", 35000);
		final Employee employeeFour = new Employee(4, "Emp", "Four", "Front-end", 25000);
		final Employee employeeFive = new Employee(5, "Emp", "Five", "Full-stack", 45000);
		final Employee employeeSix = new Employee(6, "Emp", "Six", "Full-stack", 150000);
		final Employee employeeSeven = new Employee(7, "Emp", "Seven", "Back-end", 40000);
		final Employee employeeEight = new Employee(8, "Emp", "Eight", "Full-stack", 85000);
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeOne))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeTwo))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeThree))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeFour))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeFive))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeSix))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeSeven))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeEight))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}

	private byte[] toJson(final Object r) throws Exception {
		final ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(r).getBytes();
	}

	// insert new employee into the db
	@Test
	@Order(1)
	public void createEmployee() throws Exception {
		final Employee employeeOne = new Employee(1, "Employee", "NumberOne", "Front-end", 20000);
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeOne))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	// not creating employee when firstName contains special characters fails
	@Test
	@Order(2)
	public void notCreateEmployee1() throws Exception {
		final Employee employeeOne = new Employee(1, "em@", "NumberOne", "Front-end", 20000);
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeOne))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	// not creating employee when firstName does not contain specified length
	@Test
	@Order(3)
	public void notCreateEmployee2() throws Exception {
		final Employee employeeOne = new Employee(1, "em", "NumberOne", "Front-end", 20000);
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeOne))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	// not creating employee when lastName contains special characters fails
	@Test
	@Order(4)
	public void notCreateEmployee3() throws Exception {
		final Employee employeeOne = new Employee(1, "emp", "Number1One", "Front-end", 20000);
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeOne))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	// not creating employee when lastName does not contain specified length
	@Test
	@Order(5)
	public void notCreateEmployee4() throws Exception {
		final Employee employeeOne = new Employee(1, "em", "N", "Front-end", 20000);
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeOne))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	// not creating employee when salary is lesser than 15000
	@Test
	@Order(6)
	public void notCreateEmployee5() throws Exception {
		final Employee employeeOne = new Employee(1, "emp", "Number1One", "Front-end", 14000);
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeOne))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	// not creating employee when salary is greater than 200000
	@Test
	@Order(7)
	public void notCreateEmployee6() throws Exception {
		final Employee employeeOne = new Employee(1, "em", "N", "Front-end", 300000);
		mvc.perform(MockMvcRequestBuilders.post("/employees").content(toJson(employeeOne))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	// get all the employees from the DB
	@Test
	@Order(8)
	public void getEmployees() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/employees")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(8)));
	}

	// get all the employees by department from the DB
	@Test
	@Order(9)
	public void getEmployeesByDept1() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/employees?dept=Back-end")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
	}

	// get all the employees by department from the DB
	@Test
	@Order(10)
	public void getEmployeesByDept2() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/employees?dept=Full-stack")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)));
	}

	// get the salary of the employess in ascending order
	@Test
	@Order(11)
	public void getEmployeesSortedBySalaryAsc() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/employees?salary=asc")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].salary", is(17000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].salary", is(20000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].salary", is(25000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[3].salary", is(35000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[4].salary", is(40000))) 
				.andExpect(MockMvcResultMatchers.jsonPath("$[5].salary", is(45000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[6].salary", is(85000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[7].salary", is(150000)));
	}

	// get the salary of the employess in descending order
	@Test
	@Order(12)
	public void getEmployeesSortedBySalaryDesc() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/employees?salary=desc")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[7].salary", is(17000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[6].salary", is(20000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[5].salary", is(25000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[4].salary", is(35000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[3].salary", is(40000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].salary", is(45000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].salary", is(85000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].salary", is(150000)));
	}

	// get the employees based on dept sorted by salary ascending order
	@Test
	@Order(13)
	public void getSortedEmployessByDeptAndSalaryAsc() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/employees?dept=Full-stack&salary=asc")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].salary", is(35000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].salary", is(45000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].salary", is(85000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[3].salary", is(150000)));
	}

	// get the employees based on dept sorted by salary descending order
	@Test
	@Order(14)
	public void getSortedEmployessByDeptAndSalaryDesc() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/employees?dept=Back-end&salary=desc")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].salary", is(40000)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].salary", is(17000)));
	}

	// updating a employee details
	@Test
	@Order(15)
	public void updateEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put("/employees/4")
				.content(toJson(new Employee(4, null, null, "Machine_Learning", 190000)))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is("Emp")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName", is("Four")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.department", is("Machine_Learning")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary", is(190000)));
	}

	// deleting a employee from DB
	@Test
	@Order(16)
	public void deleteEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/employees/5").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		mvc.perform(MockMvcRequestBuilders.get("/employees"))
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(7)));
	}
}
