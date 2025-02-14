package entities1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class App {
    public static void main(String[] args) {
        // Creating Student object
        Student stu = new Student(101, "Preeti");

        // hibernate Configuration (Without XML)
        Configuration config = new Configuration();
        config.addAnnotatedClass(Student.class); // Register entity class

        // Database Connection Properties
        config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/preetidb?createDatabaseIfNotExist=true");
        config.setProperty("hibernate.connection.username", "root");
        config.setProperty("hibernate.connection.password", "system");
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        config.setProperty("hibernate.hbm2ddl.auto", "update"); // Automatically creates tables
        config.setProperty("hibernate.show_sql", "true");

        // Building ServiceRegistry
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties()).build();

        // Creating SessionFactory
        try (SessionFactory factory = config.buildSessionFactory(serviceRegistry);
             Session session = factory.openSession()) {

            // Beginning transaction
            Transaction tx = session.beginTransaction();

            // Saving student object
            session.save(stu);

            // Committing transaction
            tx.commit();

            System.out.println("Record saved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}