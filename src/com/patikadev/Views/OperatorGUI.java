package com.patikadev.Views;

import com.patikadev.Helpers.Configs;
import com.patikadev.Helpers.Functions;
import com.patikadev.Models.Operator;
import com.patikadev.Models.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private DefaultTableModel tblUsersModel;
    private DefaultComboBoxModel cmbUserTypeModel;

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

        // ModelUserType
        cmbUserTypeModel = new DefaultComboBoxModel();
        Object[] rowUserType = {"operator", "educator", "student"};

        for (Object obj : rowUserType)
            cmbUserTypeModel.addElement(obj);

        cmbUserType.setModel(cmbUserTypeModel);

        Functions.getUsers(tblUsers);

        tblUsers.getSelectionModel().addListSelectionListener(e -> {
            try {
                txtUserId.setText(tblUsers.getValueAt(tblUsers.getSelectedRow(), 0).toString());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        btnAddUser.addActionListener(e -> {
            if (Functions.isFieldEmpty(txtName) || Functions.isFieldEmpty(txtUsername) || Functions.isFieldEmpty(txtPassword)) {
                Functions.showMessage("fill", "err", JOptionPane.ERROR_MESSAGE);
            } else {
                User user = new User();
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
    }
}
