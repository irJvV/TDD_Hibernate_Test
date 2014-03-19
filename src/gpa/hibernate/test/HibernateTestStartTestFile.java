package gpa.hibernate.test;

import static org.junit.Assert.assertEquals;
import gpa.hibernate.main.Hibernate_Entry;

import org.junit.Test;

public class HibernateTestStartTestFile {

	String string1 = "Entry";
	
	@Test
	public void testContactWithMainMethod(){
		assertEquals(string1, Hibernate_Entry.Welcome());
	}

}
