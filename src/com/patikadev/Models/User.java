package com.patikadev.Models;

import com.patikadev.Helpers.DbConnector;
import com.patikadev.Helpers.Functions;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class User {

    private int id;
    private String name;
    private String username;
    private String password;
    private String type;

    public User() {
    }

    public User(int id, String name, String username, String password, String type) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public static List<User> getAll() {
        List<User> users = new ArrayList<>();
        User user;

        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));

                users.add(user);
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static List<User> getAll(String type) {
        List<User> users = new ArrayList<>();
        User user;

        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE type = '" + type + "'");

            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));

                users.add(user);
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static boolean add(User user) {
        User findUser = User.get(-1, user.getUsername());

        if (findUser != null) {
            Functions.showMessage("Kullanıcı adı zaten mevcut", "err", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            PreparedStatement statement = DbConnector.getInstance().prepareStatement("INSERT INTO users (name, username, password, type) VALUES (?, ?, ?, ?);");
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getType());

            statement.executeUpdate();
            Functions.showMessage("success", "info", JOptionPane.INFORMATION_MESSAGE);
            statement.close();

            return true;
        } catch (SQLException e) {
            Functions.showMessage("err", "err", JOptionPane.ERROR_MESSAGE);

            return false;
        }
    }

    public static User get(int id, String username) {
        User user = null;

        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "' AND id != " + id);

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public static User get(int id) {
        User user = null;

        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE id = " + id);

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public static boolean delete(int id) {
        try {
            Statement statement = DbConnector.getInstance().createStatement();
            statement.executeUpdate("DELETE FROM users WHERE id = " + id);
            statement.executeUpdate("DELETE FROM courses WHERE user_id = " + id);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean update(User user) {
        User findUser = User.get(user.getId(), user.getUsername());

        if (findUser != null) {
            Functions.showMessage("Kullanıcı adı zaten mevcut", "err", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            PreparedStatement statement = DbConnector.getInstance().prepareStatement("UPDATE users SET name = ?, username = ?, password = ?, type = ? WHERE id = ?");
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getType());
            statement.setInt(5, user.getId());

            statement.executeUpdate();
            Functions.showMessage("success", "info", JOptionPane.INFORMATION_MESSAGE);
            statement.close();

            return true;
        } catch (SQLException e) {
            Functions.showMessage("err", "err", JOptionPane.ERROR_MESSAGE);

            return false;
        }
    }

    public static List<User> search(String query) {
        List<User> users = new ArrayList<>();
        User user;

        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));

                users.add(user);
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static String searchQuery(String name, String username, String type) {
        name = name.toLowerCase();
        username = username.toLowerCase();
        type = type.toLowerCase();

        String query = "SELECT * FROM users WHERE LOWER(name) LIKE '%{{name}}%' AND LOWER(username) LIKE '%{{username}}%'";
        query = query.replace("{{name}}", name);
        query = query.replace("{{username}}", username);

        if (!type.isEmpty()) {
            query += " AND LOWER(type) = '{{type}}'";
            query = query.replace("{{type}}", type);
        }

        return query;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
