package utilities;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryProvider {
    public static SessionFactory provideSessionFactory() {
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml"); // No need to specify the full path
        return config.buildSessionFactory();
    }
}