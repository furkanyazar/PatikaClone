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

public class Path {

    private int id;
    private String name;

    public Path() {
    }

    public Path(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Path> getAll() {
        List<Path> paths = new ArrayList<>();
        Path path;

        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paths");

            while (resultSet.next()) {
                path = new Path();
                path.setId(resultSet.getInt("id"));
                path.setName(resultSet.getString("name"));

                paths.add(path);
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paths;
    }

    public static boolean add(Path path) {
        Path findPath = Path.get(-1, path.getName());

        if (findPath != null) {
            Functions.showMessage("Patika adı zaten mevcut", "err", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            PreparedStatement statement = DbConnector.getInstance().prepareStatement("INSERT INTO paths (name) VALUES (?);");
            statement.setString(1, path.getName());

            statement.executeUpdate();
            Functions.showMessage("success", "info", JOptionPane.INFORMATION_MESSAGE);
            statement.close();

            return true;
        } catch (SQLException e) {
            Functions.showMessage("err", "err", JOptionPane.ERROR_MESSAGE);

            return false;
        }
    }

    public static Path get(int id, String name) {
        Path path = null;

        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paths WHERE name = '" + name + "' AND id != " + id);

            if (resultSet.next()) {
                path = new Path();
                path.setId(resultSet.getInt("id"));
                path.setName(resultSet.getString("name"));
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return path;
    }

    public static Path get(int id) {
        Path path = null;

        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paths WHERE id = " + id);

            if (resultSet.next()) {
                path = new Path();
                path.setId(resultSet.getInt("id"));
                path.setName(resultSet.getString("name"));
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return path;
    }

    public static boolean delete(int id) {
        try {
            Statement statement = DbConnector.getInstance().createStatement();
            statement.executeUpdate("DELETE FROM paths WHERE id = " + id);
            statement.executeUpdate("DELETE FROM courses WHERE path_id = " + id);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean update(Path path) {
        Path findPath = Path.get(path.getId(), path.getName());

        if (findPath != null) {
            Functions.showMessage("Patika adı zaten mevcut", "err", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            PreparedStatement statement = DbConnector.getInstance().prepareStatement("UPDATE paths SET name = ? WHERE id = ?");
            statement.setString(1, path.getName());
            statement.setInt(2, path.getId());

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
