package org.example.console;

import java.util.Scanner;

public class Console {
    private boolean isClose;
    private final Scanner sc = new Scanner(System.in);
    private final ConsoleManager consoleManager = new ConsoleManager();

    public Console(){
        init();
    }

    private void init(){
        while (!isClose){
            printMenu();
                int action = sc.nextInt();
                sc.nextLine();
                if (action < 1 || action > 6){
                    System.out.println("____________________________");
                    System.out.println("Введите число от 1 до 6!");
                    System.out.println("____________________________");
                }
                switch (action){
                    case 1:
                        consoleManager.add();
                        break;
                    case 2:
                        consoleManager.delete();
                        break;
                    case 3:
                        consoleManager.getAll();
                        break;
                    case 4:
                        consoleManager.get();
                        break;
                    case 5:
                        consoleManager.update();
                        break;
                    case 6:
                        System.out.println("До свидания!");
                        isClose = true;
                        break;
                }
        }
    }
    private void printMenu(){
        System.out.println("""
                    Выберите действие:\s
                    1. Добавление пользователя.
                    2. Удаление пользователя.
                    3. Просмотр всех пользователей.
                    4. Поиск пользователя.
                    5. Изменение email пользователя.
                    6. Закрыть программу.""");
    }
}
