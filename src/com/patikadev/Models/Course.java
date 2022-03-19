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

public class Course {

    private int id;
    private int userId;
    private int pathId;
    private String name;
    private String language;

    private Path path;
    private User user;

    public Course() {
    }

    public Course(int id, int userId, int pathId, String name, String language) {
        this.id = id;
        this.userId = userId;
        this.pathId = pathId;
        this.name = name;
        this.language = language;

        this.path = Path.get(pathId);
        this.user = User.get(userId);
    }

    public static List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        Course course;

        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM courses");

            while (resultSet.next()) {
                course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setPath(Path.get(resultSet.getInt("path_id")));
                course.setUser(User.get(resultSet.getInt("user_id")));
                course.setName(resultSet.getString("name"));
                course.setLanguage(resultSet.getString("language"));

                courses.add(course);
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public static boolean add(Course course) {
        Course findCourse = Course.get(-1, course.getName());

        if (findCourse != null) {
            Functions.showMessage("Kurs adı zaten mevcut", "err", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            PreparedStatement statement = DbConnector.getInstance().prepareStatement("INSERT INTO courses (user_id, path_id, name, language) VALUES (?, ?, ?, ?);");
            statement.setInt(1, course.getUserId());
            statement.setInt(2, course.getPathId());
            statement.setString(3, course.getName());
            statement.setString(4, course.getLanguage());

            statement.executeUpdate();
            Functions.showMessage("success", "info", JOptionPane.INFORMATION_MESSAGE);
            statement.close();

            return true;
        } catch (SQLException e) {
            Functions.showMessage("err", "err", JOptionPane.ERROR_MESSAGE);

            return false;
        }
    }

    public static Course get(int id, String name) {
        Course course = null;

        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM courses WHERE name = '" + name + "' AND id != " + id);

            if (resultSet.next()) {
                course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setPath(Path.get(resultSet.getInt("path_id")));
                course.setUser(User.get(resultSet.getInt("user_id")));
                course.setName(resultSet.getString("name"));
                course.setLanguage(resultSet.getString("language"));
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return course;
    }

    public static Course get(int id) {
        Course course = null;

        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM courses WHERE id = " + id);

            if (resultSet.next()) {
                course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setPath(Path.get(resultSet.getInt("path_id")));
                course.setUser(User.get(resultSet.getInt("user_id")));
                course.setName(resultSet.getString("name"));
                course.setLanguage(resultSet.getString("language"));
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return course;
    }

    public static boolean delete(int id) {
        try {
            Statement statement = DbConnector.getInstance().createStatement();
            statement.executeUpdate("DELETE FROM courses WHERE id = " + id);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean update(Course course) {
        Course findCourse = Course.get(course.getId(), course.getName());

        if (findCourse != null) {
            Functions.showMessage("Kurs adı zaten mevcut", "err", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            PreparedStatement statement = DbConnector.getInstance().prepareStatement("UPDATE courses SET user_id = ?, path_id = ?, name = ?, language = ? WHERE id = ?");
            statement.setInt(1, course.getUserId());
            statement.setInt(2, course.getPathId());
            statement.setString(3, course.getName());
            statement.setString(4, course.getLanguage());
            statement.setInt(5, course.getId());

            statement.executeUpdate();
            Functions.showMessage("success", "info", JOptionPane.INFORMATION_MESSAGE);
            statement.close();

            return true;
        } catch (SQLException e) {
            Functions.showMessage("err", "err", JOptionPane.ERROR_MESSAGE);

            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPathId() {
        return pathId;
    }

    public void setPathId(int pathId) {
        this.pathId = pathId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
