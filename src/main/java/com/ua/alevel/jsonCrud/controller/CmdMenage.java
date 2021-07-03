package com.ua.alevel.jsonCrud.controller;

import com.ua.alevel.jsonCrud.dao.User;
import com.ua.alevel.jsonCrud.service.CrudService;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class CmdMenage {
    private final CrudService crudService = new CrudService();
    BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

    public void cmdList() {
        System.out.println("Список доступных команд: ");
        System.out.println("\"create\" - создать нового пользователя");
        System.out.println("\"update\" - изменить существующего пользователя");
        System.out.println("\"find\" - полная информация по пользователю");
        System.out.println("\"show all\" - информация по всем пользователям");
        System.out.println("\"delete\" - удалить пользователя");
        System.out.println("\"exit\" - выход из приложения");
        inputCmd();


    }

    @SneakyThrows
    public void inputCmd() {

        String cmd = read.readLine();
        switch (cmd) {
            case ("create") -> create();
            case ("update") -> update();
            case ("find") -> find();
            case ("show all") -> showAll();
            case ("delete") -> delete();
            case ("exit") -> exit();
            default -> unknownCmd(cmd);
        }

    }

    //_________________________________________________
    @SneakyThrows
    private void create() {

        System.out.println("Введите имя нового пользователя");
        String firstName = read.readLine();
        System.out.println("Введите фамилию нового пользователя");
        String lastName = read.readLine();
        System.out.println("Введите актуальную почту нового пользователя");
        String email = read.readLine();
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        crudService.create(user);

    }

    @SneakyThrows
    private void update() {
        System.out.println("Введите Id пользователя для внесения изменений");
        String id = read.readLine();
        System.out.println("Новое имя: ");
        String firstName = read.readLine();
        System.out.println("Новая фамилия ");
        String lastName = read.readLine();
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        crudService.update(user);
        inputCmd();
    }

    @SneakyThrows
    private void find() {
        System.out.println("Введите Id пользователя для поиска");
        String id = read.readLine();
        if (crudService.findById(id) != null) {
            System.out.println(crudService.findById(id));
            inputCmd();
        }else{
            userNotExist();
        }
    }

    private void showAll() {
        if (!crudService.showAll().isEmpty()) {
            crudService.showAll().forEach(System.out::println);
        }else{
            System.out.println("Список пока что пуст");
        }
        inputCmd();
    }

    @SneakyThrows
    private void delete() {
        System.out.println("Введите Id для удаления пользователя");
        String id = read.readLine();
        crudService.delete(id);
        inputCmd();
    }

    private void exit() {
        System.exit(0);
    }


    private void unknownCmd(String cmd) {
        System.out.println("\nНеизвестная команда " + cmd + "\n");
        cmdList();
    }

    public void userNotExist(){
        System.out.println("Пользователя не существует");
        inputCmd();
    }
}
