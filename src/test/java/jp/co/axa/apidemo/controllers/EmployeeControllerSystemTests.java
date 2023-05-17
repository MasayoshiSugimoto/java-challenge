package jp.co.axa.apidemo.controllers;

import com.google.common.base.Strings;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerSystemTests {


	@Autowired
	private MockMvc mvc;
	@Autowired
	private EmployeeRepository employeeRepo;


	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenEmployees_whenGetEmployees_thenStatus200() throws Exception {
		mvc.perform(get("/api/v1/employees"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[" +
						"{\"id\":1,\"name\":\"John Carmack\",\"salary\":1000000,\"department\":\"R&D\"}," +
						"{\"id\":2,\"name\":\"Brian Kernighan\",\"salary\":500000,\"department\":\"R&D\"}," +
						"{\"id\":3,\"name\":\"Bob Martin\",\"salary\":100000,\"department\":\"HR\"}" +
						"]"
				));
	}


	@Test
	@Sql(scripts = "classpath:/sql/controllers/employee-reset.sql")
	public void givenNoEmployee_whenGetEmployees_thenStatus200() throws Exception {
		mvc.perform(get("/api/v1/employees"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[]"));
	}


	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenJohn_whenGetEmployee_thenStatus200() throws Exception {
		mvc.perform(get("/api/v1/employees/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json("{\"id\":1,\"name\":\"John Carmack\",\"salary\":1000000,\"department\":\"R&D\"}"));
	}


	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenBrian_whenGetEmployee_thenStatus200() throws Exception {
		mvc.perform(get("/api/v1/employees/2"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json("{\"id\":2,\"name\":\"Brian Kernighan\",\"salary\":500000,\"department\":\"R&D\"}"));
	}

	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenBob_whenGetEmployee_thenStatus200() throws Exception {
		mvc.perform(get("/api/v1/employees/3"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json("{\"id\":3,\"name\":\"Bob Martin\",\"salary\":100000,\"department\":\"HR\"}"));
	}


	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenUnknown_whenGetEmployee_thenStatus200() throws Exception {
		mvc.perform(get("/api/v1/employees/4"))
				.andExpect(status().isOk())
				.andExpect(content().string(Matchers.isEmptyOrNullString()));
	}


	@Test
	@Sql(scripts = "classpath:/sql/controllers/employee-reset.sql")
	public void givenJohnAndEmptyDB_whenSaveEmployee_thenStatus200() throws Exception {
		Employee employee = new Employee(){{
			setName("John Carmack");
			setSalary(1000000);
			setDepartment("R&D");
		}};
		mvc.perform(
				post("/api/v1/employees")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJSON(employee))
		)
				.andExpect(status().isOk())
				.andExpect(content().string(Matchers.isEmptyOrNullString()));
		assertEmployees(new Employee[]{
				new Employee(){{
					setName("John Carmack");
					setSalary(1000000);
					setDepartment("R&D");
				}}
		});
	}


	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenCaseyAndNonEmptyDB_whenSaveEmployee_thenStatus200() throws Exception {
		Employee employee = new Employee(){{
			setName("Casey Muratori");
			setSalary(750000);
			setDepartment("R&D");
		}};
		mvc.perform(
				post("/api/v1/employees")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJSON(employee))
		)
				.andExpect(status().isOk())
				.andExpect(content().string(Matchers.isEmptyOrNullString()));
		assertEmployees(new Employee[]{
				new Employee(){{
					setName("John Carmack");
					setSalary(1000000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Brian Kernighan");
					setSalary(500000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Bob Martin");
					setSalary(100000);
					setDepartment("HR");
				}},
				new Employee(){{
					setName("Casey Muratori");
					setSalary(750000);
					setDepartment("R&D");
				}}
		});
	}


	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenJohn_whenDeleteEmployee_thenStatus200() throws Exception {
		mvc.perform(delete("/api/v1/employees/1"))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
		assertEmployees(new Employee[]{
				new Employee(){{
					setName("Brian Kernighan");
					setSalary(500000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Bob Martin");
					setSalary(100000);
					setDepartment("HR");
				}}
		});
	}


	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenBrian_whenDeleteEmployee_thenStatus200() throws Exception {
		mvc.perform(delete("/api/v1/employees/2"))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
		assertEmployees(new Employee[]{
				new Employee(){{
					setName("John Carmack");
					setSalary(1000000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Bob Martin");
					setSalary(100000);
					setDepartment("HR");
				}}
		});
	}


	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenBob_whenDeleteEmployee_thenStatus200() throws Exception {
		mvc.perform(delete("/api/v1/employees/3"))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
		assertEmployees(new Employee[]{
				new Employee(){{
					setName("John Carmack");
					setSalary(1000000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Brian Kernighan");
					setSalary(500000);
					setDepartment("R&D");
				}}
		});
	}


	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenUnknown_whenDeleteEmployee_thenStatus200() throws Exception {
		mvc.perform(delete("/api/v1/employees/4"))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
		assertEmployees(new Employee[]{
				new Employee(){{
					setName("John Carmack");
					setSalary(1000000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Brian Kernighan");
					setSalary(500000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Bob Martin");
					setSalary(100000);
					setDepartment("HR");
				}}
		});
	}


	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenJohn_whenPutEmployee_thenStatus200() throws Exception {
		Employee employee = new Employee(){{
			setId(1L);
			setName("Mr John Carmack");
			setSalary(2000000);
			setDepartment("CTO");
		}};
		mvc.perform(
				put("/api/v1/employees/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJSON(employee))
		)
				.andExpect(status().isOk())
				.andExpect(content().string(""));
		assertEmployees(new Employee[]{
				new Employee(){{
					setName("Mr John Carmack");
					setSalary(2000000);
					setDepartment("CTO");
				}},
				new Employee(){{
					setName("Brian Kernighan");
					setSalary(500000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Bob Martin");
					setSalary(100000);
					setDepartment("HR");
				}}
		});
	}


	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenBrian_whenPutEmployee_thenStatus200() throws Exception {
		Employee employee = new Employee(){{
			setId(2L);
			setName("Mr Brian Kernighan");
			setSalary(2000000);
			setDepartment("CTO");
		}};
		mvc.perform(
				put("/api/v1/employees/2")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJSON(employee))
		)
				.andExpect(status().isOk())
				.andExpect(content().string(""));
		assertEmployees(new Employee[]{
				new Employee(){{
					setName("John Carmack");
					setSalary(1000000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Mr Brian Kernighan");
					setSalary(2000000);
					setDepartment("CTO");
				}},
				new Employee(){{
					setName("Bob Martin");
					setSalary(100000);
					setDepartment("HR");
				}}
		});
	}


	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenBob_whenPutEmployee_thenStatus200() throws Exception {
		Employee employee = new Employee(){{
			setId(3L);
			setName("Mr Bob Martin");
			setSalary(2000000);
			setDepartment("CTO");
		}};
		mvc.perform(
				put("/api/v1/employees/3")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJSON(employee))
		)
				.andExpect(status().isOk())
				.andExpect(content().string(""));
		assertEmployees(new Employee[]{
				new Employee(){{
					setName("John Carmack");
					setSalary(1000000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Brian Kernighan");
					setSalary(500000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Mr Bob Martin");
					setSalary(2000000);
					setDepartment("CTO");
				}}
		});
	}


	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenUnknown_whenPutEmployee_thenStatus200() throws Exception {
		Employee employee = new Employee(){{
			setId(4L);
			setName("Casey Muratori");
			setSalary(750000);
			setDepartment("R&D");
		}};
		mvc.perform(
				put("/api/v1/employees/4")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJSON(employee))
		)
				.andExpect(status().isOk())
				.andExpect(content().string(""));
		assertEmployees(new Employee[]{
				new Employee(){{
					setName("John Carmack");
					setSalary(1000000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Brian Kernighan");
					setSalary(500000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Bob Martin");
					setSalary(100000);
					setDepartment("HR");
				}}
		});
	}


	@Test
	@Sql(scripts = {
			"classpath:/sql/controllers/employee-reset.sql",
			"classpath:/sql/controllers/employee-controller.default.sql",
	})
	public void givenInconsistentData_whenPutEmployee_thenStatus400() throws Exception {
		Employee employee = new Employee(){{
			setId(1L);
			setName("Mr Bob Martin");
			setSalary(2000000);
			setDepartment("CTO");
		}};
		mvc.perform(
				put("/api/v1/employees/3")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJSON(employee))
		)
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("{\"error\":\"`employeeId` and `employee.id` must be same.\"}"));
		assertEmployees(new Employee[]{
				new Employee(){{
					setName("John Carmack");
					setSalary(1000000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Brian Kernighan");
					setSalary(500000);
					setDepartment("R&D");
				}},
				new Employee(){{
					setName("Bob Martin");
					setSalary(100000);
					setDepartment("HR");
				}}
		});
	}


	private static void assertEqualsEmployees(List<Employee> employees, List<Employee> expectedEmployees) {
		assertEquals("Number of employees is not as expected.", employees.size(), expectedEmployees.size());
		for (int i = 0; i < employees.size(); i++) {
			assertEquals(
					"Employee not as expected.",
					asString(employees.get(i)),
					asString(expectedEmployees.get(i))
			);
		}
	}


	private static String asString(Employee employee) {
		if (employee == null) return "{class: Employee}";
		return String.format(
				"{class: Employee, name: %s, salary: %d, department: %s}",
				Strings.nullToEmpty(employee.getName()),
				employee.getSalary(),
				Strings.nullToEmpty(employee.getDepartment())
		);
	}


	private static String toJSON(Employee employee) {
		if (employee == null) return "{}";
		return String.format(
				"{\"id\": %d, \"name\": \"%s\", \"salary\": %d, \"department\": \"%s\"}",
				employee.getId(),
				Strings.nullToEmpty(employee.getName()),
				employee.getSalary(),
				Strings.nullToEmpty(employee.getDepartment())
		);
	}


	private void assertEmployees(Employee[] expectedEmployees) {
		assertEqualsEmployees(employeeRepo.findAll(), Arrays.asList(expectedEmployees));
	}


}
