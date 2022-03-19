package com.patikadev.Helpers;

import com.patikadev.Models.Course;
import com.patikadev.Models.Path;
import com.patikadev.Models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Functions {

    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().equals("Nimbus")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int screenCenterLocation(String axis, Dimension size) {
        int point = 0;

        switch (axis) {
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
        }

        return point;
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static void showMessage(String message, String title, int type) {
        optionPaneTR();

        switch (message) {
            case "fill":
                message = Configs.FILL_ALL_FILEDS_MESSAGE;
                break;
            case "success":
                message = Configs.OPERATION_SUCCESSFUL_MESSAGE;
                break;
            case "err":
                message = Configs.ERROR_MESSAGE;
        }

        switch (title) {
            case "err":
                title = Configs.ERROR_MESSAGE_TITLE;
                break;
            case "info":
                title = Configs.INFORMATION_MESSAGE_TITLE;
                break;
            case "warn":
                title = Configs.WARNING_MESSAGE_TITLE;
        }

        JOptionPane.showMessageDialog(null, message, title, type);
    }

    public static void optionPaneTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }

    public static void getPaths(JTable tblPaths) {
        DefaultTableModel tblPathsModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;

                return super.isCellEditable(row, column);
            }
        };

        Object[] colPaths = {"ID", "Patika Adı"};
        tblPathsModel.setColumnIdentifiers(colPaths);

        for (Path path : Path.getAll()) {
            Object[] rowPaths = new Object[colPaths.length];

            rowPaths[0] = path.getId();
            rowPaths[1] = path.getName();

            tblPathsModel.addRow(rowPaths);
        }

        tblPaths.setModel(tblPathsModel);
        tblPaths.getTableHeader().setReorderingAllowed(false);
        tblPaths.getColumnModel().getColumn(0).setMaxWidth(100);
        tblPaths.getColumnModel().getColumn(0).setMinWidth(50);
    }

    public static void getCourses(JTable tblCourses) {
        DefaultTableModel tblCoursesModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;

                return super.isCellEditable(row, column);
            }
        };

        Object[] colCourses = {"ID", "Eğitmen", "Patika", "Kurs Adı", "Dil"};
        tblCoursesModel.setColumnIdentifiers(colCourses);

        for (Course course : Course.getAll()) {
            Object[] rowCourses = new Object[colCourses.length];

            rowCourses[0] = course.getId();
            rowCourses[1] = course.getUser().getName();
            rowCourses[2] = course.getPath().getName();
            rowCourses[3] = course.getName();
            rowCourses[4] = course.getLanguage();

            tblCoursesModel.addRow(rowCourses);
        }

        tblCourses.setModel(tblCoursesModel);
        tblCourses.getTableHeader().setReorderingAllowed(false);
        tblCourses.getColumnModel().getColumn(0).setMaxWidth(100);
        tblCourses.getColumnModel().getColumn(0).setMinWidth(50);
    }

    public static void getPathsForAddCourse(JComboBox cmbPaths) {
        cmbPaths.removeAllItems();

        List<Path> paths = Path.getAll();
        for (Path path : paths)
            cmbPaths.addItem(new Item(path.getId(), path.getName()));
    }

    public static void getUsersForAddCourse(JComboBox cmbTeachers) {
        cmbTeachers.removeAllItems();

        List<User> users = User.getAll("educator");
        for (User user : users)
            cmbTeachers.addItem(new Item(user.getId(), user.getName()));
    }

    public static void getUsers(JTable tblUsers) {
        DefaultTableModel tblUsersModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;

                return super.isCellEditable(row, column);
            }
        };

        Object[] colUsers = {"ID", "İsim", "Kullanıcı adı", "Şifre", "Kullanıcı Tipi"};
        tblUsersModel.setColumnIdentifiers(colUsers);

        for (User user : User.getAll()) {
            Object[] rowUsers = new Object[colUsers.length];

            rowUsers[0] = user.getId();
            rowUsers[1] = user.getName();
            rowUsers[2] = user.getUsername();
            rowUsers[3] = user.getPassword();
            rowUsers[4] = user.getType();

            tblUsersModel.addRow(rowUsers);
        }

        tblUsers.setModel(tblUsersModel);
        tblUsers.getTableHeader().setReorderingAllowed(false);
        tblUsers.getColumnModel().getColumn(0).setMaxWidth(100);
        tblUsers.getColumnModel().getColumn(0).setMinWidth(50);
    }

    public static void getUsers(JTable tblUsers, List<User> users) {
        DefaultTableModel tblUsersModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;

                return super.isCellEditable(row, column);
            }
        };

        Object[] colUsers = {"ID", "İsim", "Kullanıcı adı", "Şifre", "Kullanıcı Tipi"};
        tblUsersModel.setColumnIdentifiers(colUsers);

        for (User user : users) {
            Object[] rowUsers = new Object[colUsers.length];

            rowUsers[0] = user.getId();
            rowUsers[1] = user.getName();
            rowUsers[2] = user.getUsername();
            rowUsers[3] = user.getPassword();
            rowUsers[4] = user.getType();

            tblUsersModel.addRow(rowUsers);
        }

        tblUsers.setModel(tblUsersModel);
        tblUsers.getTableHeader().setReorderingAllowed(false);
        tblUsers.getColumnModel().getColumn(0).setMaxWidth(100);
        tblUsers.getColumnModel().getColumn(0).setMinWidth(50);
    }

    public static boolean confirm() {
        optionPaneTR();
        return JOptionPane.showConfirmDialog(null, "İşlem gerçekleştirilsin mi?", "Uyarı", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0;
    }
}
