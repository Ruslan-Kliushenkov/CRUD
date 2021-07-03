package com.ua.alevel.jsonCrud.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ua.alevel.jsonCrud.cfg.FileType;
import com.ua.alevel.jsonCrud.controller.CmdMenage;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDao {

    private List<User> users = new ArrayList<>();

    public void create(User user) {
        this.users = readFile();
        final CmdMenage cmdMenage = new CmdMenage();
        user.setId(newId());
        users.add(user);
        System.out.println("\nSuccess!\n");
        writeFile();
        cmdMenage.inputCmd();
    }

    public Boolean existByEmail(String newEmail) {
        this.users = readFile();
        return users.stream().anyMatch(user -> user.getEmail().equals(newEmail));
    }

    private String newId() {
        this.users = readFile();
        String id = UUID.randomUUID().toString();
        if (users.stream().anyMatch(u -> u.getId().equals(id))) {
            return newId();
        }
        return id;
    }

    public void update(User user) {
        this.users = readFile();
        CmdMenage cmdMenage = new CmdMenage();
        User newUser = users.stream().filter(u -> u.getId().equals(user.getId())).findFirst().orElse(null);
        if (newUser != null) {
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            writeFile();
            System.out.println("\nSuccess!\n");
        }else{
            cmdMenage.userNotExist();
        }
    }

    public User find(String id){
        this.users = readFile();
        users.forEach(user -> user.setFullName(user.getFullName()));
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public List<User> showAll(){
        this.users = readFile();
        users.forEach(user -> user.setFullName(user.getFullName()));
        return users;
    }

    public void delete(String id){
        this.users = readFile();
        CmdMenage cmdMenage = new CmdMenage();
        if(find(id) != null){
            users.removeIf(user -> user.getId().equals(id));
            writeFile();
            System.out.println("\nSuccess!\n");
        }else{
            cmdMenage.userNotExist();
        }

    }
    @SneakyThrows
    public List<User> readFile(){
        try {
            String usersJson = FileUtils.readFileToString(new File(FileType.JSON_TYPE.getPath()), "UTF-8");
            if (StringUtils.isBlank(usersJson)) {
                return new ArrayList<>();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(usersJson, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("some problem from file");
    }

    @SneakyThrows
    public void writeFile(){
        Gson gson = new Gson();
        String usersOut = gson.toJson(this.users);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileType.JSON_TYPE.getPath()))) {
            writer.append(usersOut);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
