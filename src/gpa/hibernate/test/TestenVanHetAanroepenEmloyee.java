package gpa.hibernate.test;

import static org.junit.Assert.assertEquals;
import gpa.hibernate.main.Employee;
import gpa.hibernate.main.Hibernate_Entry;

import org.junit.Test;

public class TestenVanHetAanroepenEmloyee {

	String firstname1 = "Gijs";
	int salary1 = 2500;
	Employee employee1 = new Employee();
	
	@Test
	public void testContactWithMainMethod(){
		assertEquals(firstname1, employee1.getFirstName());
		assertEquals(salary1, employee1.getSalary());
	}

}
