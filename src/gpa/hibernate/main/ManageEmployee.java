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

		/* Toevoegen certificaten voor de eerste employee */
		HashSet set1 = new HashSet();
		set1.add(new Certificate("MCA"));
		set1.add(new Certificate("MBA"));
		set1.add(new Certificate("PMP"));

		/* toevoegen employee in database */
		Integer empID1 = ME.addEmployee("Manoj", "Kumar", 4000, set1);

		/* Toevoegen certificaten voor de tweede employee */
		HashSet set2 = new HashSet();
		set2.add(new Certificate("BCA"));
		set2.add(new Certificate("BA"));

		/* toevoegen tweede employee in database */
		Integer empID2 = ME.addEmployee("Dilip", "Kumar", 3000, set2);

		/* List down all the employees */
		ME.listEmployees();

		/* Update employee's records */
		ME.updateEmployee(empID1, 5000);

		/* Delete an employee from the database */
		ME.deleteEmployee(empID1);
		ME.deleteEmployee(empID2);

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
			employee.setCertificates(cert); // toevoeging voor certificaten
			employeeID = (Integer) session.save(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println("*********** CREATE an employee" + employeeID
				+ " in the database ***********");
		return employeeID;
	}

	/* Method to READ all the employees */
	public void listEmployees() {

		System.out.println("*********** READ all the employees ***********");

		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Object> employees = session.createQuery("FROM Employee")
					.list();
			for (Iterator<Object> iterator = employees.iterator(); iterator
					.hasNext();) {
				Employee employee = (Employee) iterator.next();
				System.out.print("First Name: " + employee.getFirstName());
				System.out.print(" Last Name: " + employee.getLastName());
				System.out.println(" Salary: " + employee.getSalary());
				Set certificates = employee.getCertificates(); 
				// toevoeging voor certificaten (boven en onder)
				for (Iterator iterator2 = certificates.iterator(); iterator2
						.hasNext();) {
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

		System.out
				.println("*********** UPDATE salary for an employee ***********");

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

		System.out
				.println("*********** DELETE an employee from the records ***********");

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
