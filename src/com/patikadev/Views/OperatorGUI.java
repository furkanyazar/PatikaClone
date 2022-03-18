package com.patikadev.Views;

import com.patikadev.Helpers.Configs;
import com.patikadev.Helpers.Functions;
import com.patikadev.Models.Operator;
import com.patikadev.Models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OperatorGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane tabOperator;
    private JLabel lblWelcome;
    private JButton btnSignOut;
    private JScrollPane scrlUsers;
    private JTable tblUsers;
    private DefaultTableModel tblUsersModel;
    private Object[] rowUsers;

    private final Operator operator;

    public OperatorGUI(Operator operator) {
        this.operator = operator;

        add(wrapper);
        setSize(1000, 500);
        setLocation(Functions.screenCenterLocation("x", getSize()), Functions.screenCenterLocation("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Configs.PROJECT_TITLE);
        setVisible(true);

        lblWelcome.setText(Configs.WELCOME_TEXT + this.operator.getName());

        // ModelUsers
        tblUsersModel = new DefaultTableModel();
        Object[] colUsers = {"ID", "İsim", "Kullanıcı adı", "Şifre", "Kullanıcı Tipi"};
        tblUsersModel.setColumnIdentifiers(colUsers);

        Object[] row;
        for (User user : User.getUsers()) {
            row = new Object[colUsers.length];

            row[0] = user.getId();
            row[1] = user.getName();
            row[2] = user.getUsername();
            row[3] = user.getPassword();
            row[4] = user.getType();

            tblUsersModel.addRow(row);
        }

        tblUsers.setModel(tblUsersModel);
        tblUsers.getTableHeader().setReorderingAllowed(false);
    }
}
