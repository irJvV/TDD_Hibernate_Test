package gpa.hibernate.main;

import java.util.HashSet;
import java.util.List;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManageEmployee {
	
	public ManageEmployee() {
		System.out.println("*********** Creating ManageEmployee ***********");
	}
	
	private static SessionFactory factory;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
				
		System.out.println("*********** Welcome Hibernate Test ***********");
		
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		ManageEmployee ME = new ManageEmployee();

//		/* Add few employee records in database */
//		Integer empID1 = ME.addEmployee("Zara", "Ali", 1000);
//		Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
//		Integer empID3 = ME.addEmployee("John", "Paul", 10000);

		
		/* Let us have a set of certificates for the first employee */ 
		HashSet set1 = new HashSet(); set1.add(new Certificate("MCA")); 
		set1.add(new Certificate("MBA")); set1.add(new Certificate("PMP")); 
		
		/* Add employee records in the database */ 
		Integer empID1 = ME.addEmployee("Manoj", "Kumar", 4000, set1); 
		
		/* Another set of certificates for the second employee */ 
		HashSet set2 = new HashSet(); set2.add(new Certificate("BCA")); 
		set2.add(new Certificate("BA")); 
		
		/* Add another employee record in the database */ 
		Integer empID2 = ME.addEmployee("Dilip", "Kumar", 3000, set2);
		
		/* List down all the employees */
		ME.listEmployees();

		/* Update employee's records */
		ME.updateEmployee(empID1, 5000);
		
		/* List down all the employees */
		ME.listEmployees();
		
		/* Delete an employee from the database */
		ME.deleteEmployee(empID1);
		ME.deleteEmployee(empID2);
		//ME.deleteEmployee(empID3);

		/* List down new list of the employees */
		ME.listEmployees();
	}

	/* Method to CREATE an employee in the database */
	public Integer addEmployee(String fname, String lname, int salary, Set cert) {

		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;
		try {
			tx = session.beginTransaction();
			Employee employee = new Employee(fname, lname, salary);
			//certificate entry
			employee.setCertificates(cert);
			//certificate entry
			employeeID = (Integer) session.save(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println("*********** CREATE an employee"+employeeID+" in the database ***********");
		return employeeID;
	}
	
	/* Method to READ all the employees */
	public void listEmployees() {
		
		System.out.println("*********** READ all the employees ***********");
		
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
//			@SuppressWarnings("unchecked")
//			List<Object> employees = session.createQuery("FROM Employee").list();
//			for (Iterator<Object> iterator = employees.iterator(); iterator.hasNext();) {
//				Employee employee = (Employee) iterator.next();
//				System.out.print("First Name: " + employee.getFirstName());
//				System.out.print(" Last Name: " + employee.getLastName());
//				System.out.println(" Salary: " + employee.getSalary());
//			}
			List employees = session.createQuery("FROM Employee").list(); 
			for (Iterator iterator1 = employees.iterator(); iterator1.hasNext();){ 
				Employee employee = (Employee) iterator1.next(); 
				System.out.print("First Name: " + employee.getFirstName()); 
				System.out.print(" Last Name: " + employee.getLastName()); 
				System.out.println(" Salary: " + employee.getSalary());
				Set certificates = employee.getCertificates(); 
				System.out.println("********** "+certificates+" **********");
				for (Iterator iterator2 = certificates.iterator(); iterator2.hasNext();){ 
					Certificate certName = (Certificate) iterator2.next(); 
					System.out.println("Certificate: " + certName.getName()); 
				} 
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* Method to UPDATE salary for an employee */
	public void updateEmployee(Integer EmployeeID, int salary) {
		
		System.out.println("*********** UPDATE salary for an employee ***********");
		
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class,
					EmployeeID);
			employee.setSalary(salary);
			session.update(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* Method to DELETE an employee from the records */
	public void deleteEmployee(Integer EmployeeID) {
		
		System.out.println("*********** DELETE an employee from the records ***********");
		
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class,
					EmployeeID);
			session.delete(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
