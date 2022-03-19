package com.patikadev.Views;

import com.patikadev.Helpers.Configs;
import com.patikadev.Helpers.Functions;
import com.patikadev.Models.Operator;
import com.patikadev.Models.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OperatorGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane tabOperator;
    private JLabel lblWelcome;
    private JButton btnSignOut;
    private JScrollPane scrlUsers;
    private JTable tblUsers;
    private JPanel pnlUserForm;
    private JTextField txtName;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JComboBox cmbUserType;
    private JButton btnAddUser;
    private JTextField txtUserId;
    private JButton btnDelete;
    private JTextField txtNameForSearch;
    private JTextField txtUsernameForSearch;
    private JComboBox cmbUserTypeForSearch;
    private JButton btnSearch;
    private DefaultTableModel tblUsersModel;
    private DefaultComboBoxModel cmbUserTypeModel;
    private DefaultComboBoxModel cmbUserTypeForSearchModel;

    private final Operator operator;

    public OperatorGUI(Operator operator) {
        this.operator = operator;

        add(wrapper);
        setSize(800, 600);
        setLocation(Functions.screenCenterLocation("x", getSize()), Functions.screenCenterLocation("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Configs.PROJECT_TITLE);
        setVisible(true);

        lblWelcome.setText(Configs.WELCOME_TEXT + this.operator.getName());

        // ModelUserType
        cmbUserTypeModel = new DefaultComboBoxModel();
        Object[] rowUserType = {"operator", "educator", "student"};

        for (Object obj : rowUserType)
            cmbUserTypeModel.addElement(obj);

        cmbUserType.setModel(cmbUserTypeModel);

        // ModelUserTypeForSearch
        cmbUserTypeForSearchModel = new DefaultComboBoxModel();
        Object[] rowUserTypeForSearch = {"", "operator", "educator", "student"};

        for (Object obj : rowUserTypeForSearch)
            cmbUserTypeForSearchModel.addElement(obj);

        cmbUserTypeForSearch.setModel(cmbUserTypeForSearchModel);

        Functions.getUsers(tblUsers);

        tblUsers.getSelectionModel().addListSelectionListener(e -> {
            try {
                txtUserId.setText(tblUsers.getValueAt(tblUsers.getSelectedRow(), 0).toString());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        tblUsers.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                User user = new User();
                user.setId(Integer.parseInt(tblUsers.getValueAt(tblUsers.getSelectedRow(), 0).toString()));
                user.setName(tblUsers.getValueAt(tblUsers.getSelectedRow(), 1).toString());
                user.setUsername(tblUsers.getValueAt(tblUsers.getSelectedRow(), 2).toString());
                user.setPassword(tblUsers.getValueAt(tblUsers.getSelectedRow(), 3).toString());
                user.setType(tblUsers.getValueAt(tblUsers.getSelectedRow(), 4).toString());

                User.update(user);

                Functions.getUsers(tblUsers);
            }
        });

        btnAddUser.addActionListener(e -> {
            User user;

            if (Functions.isFieldEmpty(txtName) || Functions.isFieldEmpty(txtUsername) || Functions.isFieldEmpty(txtPassword)) {
                Functions.showMessage("fill", "err", JOptionPane.ERROR_MESSAGE);
            } else {
                user = new User();
                user.setName(txtName.getText());
                user.setUsername(txtUsername.getText());
                user.setPassword(txtPassword.getText());
                user.setType(cmbUserType.getSelectedItem().toString());

                if (User.add(user)) {
                    txtName.setText(null);
                    txtUsername.setText(null);
                    txtPassword.setText(null);
                }

                Functions.getUsers(tblUsers);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Functions.isFieldEmpty(txtUserId))
                    Functions.showMessage("fill", "err", JOptionPane.ERROR_MESSAGE);
                else {
                    if (User.delete(Integer.parseInt(txtUserId.getText()))) {
                        Functions.showMessage("success", "info", JOptionPane.INFORMATION_MESSAGE);
                        txtUserId.setText(null);
                    } else
                        Functions.showMessage("err", "err", JOptionPane.ERROR_MESSAGE);

                    Functions.getUsers(tblUsers);
                }
            }
        });
        btnSearch.addActionListener(e -> {
            String name = txtNameForSearch.getText();
            String username = txtUsernameForSearch.getText();
            String type = cmbUserTypeForSearch.getSelectedItem().toString();

            List<User> users = User.search(User.searchQuery(name, username, type));
            Functions.getUsers(tblUsers, users);
        });

        btnSignOut.addActionListener(e -> {
            dispose();
        });
    }
}
