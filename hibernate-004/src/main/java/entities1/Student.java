package entities1;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Student {
    
    @Id
    private int roll;
    private String name;

    // Default constructor (Hibernate requires it)
    public Student() { }

    // Parameterized constructor
    public Student(int roll, String name) {
        this.roll = roll;
        this.name = name;
    }

    // Getters and Setters
    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}