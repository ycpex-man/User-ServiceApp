package org.example.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "name")
    private String name;
    @Column (name = "email")
    private String email;
    @Column (name = "age")
    private int age;
    @Column (name = "created_at")
    private LocalDate createdAt;

    public User(String name, String email, int age){
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = LocalDate.now();
    }

    public User(){
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return  "{id = " + id +
                ", name = '" + name + '\'' +
                ", email = '" + email + '\'' +
                ", age = " + age +
                ", createdAt = " + createdAt + "}";
    }
}
