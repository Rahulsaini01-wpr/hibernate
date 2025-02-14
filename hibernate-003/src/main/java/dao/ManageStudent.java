package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import entity.Student;

import java.util.List;
import java.util.Iterator;

public class ManageStudent {
    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object: " + ex);
            throw new ExceptionInInitializerError(ex);
        }

        ManageStudent MS = new ManageStudent();

        Integer studentID1 = MS.addStudent(101, "Alice Brown", 20, "Female", "123 Main St");
        Integer studentID2 = MS.addStudent(102, "Bob Smith", 22, "Male", "456 Oak St");
        Integer studentID3 = MS.addStudent(103, "Charlie Johnson", 21, "Male", "789 Pine St");

        MS.listStudents();

        MS.updateStudent(studentID1, "321 Maple St");

        MS.deleteStudent(studentID2);

        MS.listStudents();
    }

    public Integer addStudent(int rollNumber, String name, int age, String gender, String address) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer studentID = null;
        
        try {
            tx = session.beginTransaction();
            Student student = new Student(rollNumber, name, age, gender, address);
            studentID = (Integer) session.save(student);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return studentID;
    }

    public void listStudents() {
        Session session = factory.openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            List students = session.createQuery("FROM Student").list();
            for (Iterator iterator = students.iterator(); iterator.hasNext();) {
                Student student = (Student) iterator.next();
                System.out.print("Roll Number: " + student.getRoll());
                System.out.print("  Name: " + student.getName());
                System.out.print("  Age: " + student.getAge());
                System.out.print("  Gender: " + student.getGender());
                System.out.println("  Address: " + student.getAddress());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateStudent(Integer studentID, String newAddress) {
        Session session = factory.openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            Student student = (Student) session.get(Student.class, studentID);
            student.setAddress(newAddress);
            session.update(student);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteStudent(Integer studentID) {
        Session session = factory.openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            Student student = (Student) session.get(Student.class, studentID);
            session.delete(student);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}