package org.example.console;

import org.example.entity.User;
import org.example.DAO.UserDAO;

import java.util.List;
import java.util.Scanner;

public class ConsoleManager {
    private final String COOL = "\uD83D\uDC4D";
    private final String WARNING = "⚠";
    private final Scanner sc = new Scanner(System.in);
    private final UserDAO userDAO = new UserDAO();

    protected void add(){
        System.out.println("_________________________________________");

        System.out.println("Введите имя пользователя: ");
        String name = sc.nextLine();
        System.out.println("Введите email пользователя: ");
        String email = sc.nextLine();
        System.out.println("Введите возраст пользователя: ");
        int age = inputValidAge(sc);
        sc.nextLine();
        userDAO.add(new User(name, email, age));

        System.out.println("____________________________________________________________________________________________");
    }

    protected void delete(){
        System.out.println("_________________________________________");

        System.out.println("Введите Id пользователя: ");
        int userId = inputValidId(sc);
        sc.nextLine();
        userDAO.delete(userId);

        System.out.println("_________________________________________");
        }

    protected void get(){
        System.out.println("____________________________________________________________________________________________");

        System.out.println("Введите Id пользователя: ");
        int userId = inputValidId(sc);
        sc.nextLine();
        User findedUser = userDAO.get(userId);
        if (findedUser != null) System.out.println(findedUser);

        System.out.println("____________________________________________________________________________________________");
    }

    protected void getAll(){
        System.out.println("____________________________________________________________________________________________");

        List<User> users = userDAO.getAllUsers();
        for (User user : users){
            System.out.println(user);
        }

        System.out.println("____________________________________________________________________________________________");
    }

    protected void update(){
        System.out.println("_________________________________________");

        System.out.println("Введите Id пользователя: ");
        int userId = inputValidId(sc);
        sc.nextLine();
        User findedUser = userDAO.get(userId);
        if (findedUser != null){
            System.out.println("Введите новый email пользователя: ");
            String newEmail = sc.nextLine();
            userDAO.update(userId, newEmail);
        }

        System.out.println("_________________________________________");
    }

    private int inputValidAge(Scanner sc){
        int age = -1;
        boolean validInput = false;
        while (!validInput){
            if (sc.hasNextInt()){
                age = sc.nextInt();
                if (age > 0) validInput = true;
                else System.out.println("Возраст не может быть меньше 0 " + WARNING + "\n" +
                        "Попробуйте ещё раз.");
            } else {
                System.out.println("Возраст должен быть числом " + WARNING + "\n" +
                        "Попробуйте ещё раз.");
                sc.next();
            }
        }
        return age;
    }

    private int inputValidId(Scanner sc){
        int id = -1;
        boolean validInput = false;
        while (!validInput){
            if (sc.hasNextInt()){
                id = sc.nextInt();
                if (id > 0) validInput = true;
                else System.out.println("Id не может быть меньше 0 " + WARNING + "\n" +
                        "Попробуйте ещё раз.");
            } else {
                System.out.println("Id должен быть числом " + WARNING + "\n" +
                        "Попробуйте ещё раз.");
                sc.next();
            }
        }
        return id;
    }
}
