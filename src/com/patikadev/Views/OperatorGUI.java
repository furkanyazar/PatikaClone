package com.patikadev.Views;

import com.patikadev.Helpers.Configs;
import com.patikadev.Helpers.Functions;
import com.patikadev.Models.Operator;
import com.patikadev.Models.Path;
import com.patikadev.Models.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JTable tblPaths;
    private JTextField txtPathName;
    private JButton btnAddPath;
    private DefaultComboBoxModel cmbUserTypeModel;
    private DefaultComboBoxModel cmbUserTypeForSearchModel;
    private JPopupMenu pathsMenu;

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
        Functions.getPaths(tblPaths);

        pathsMenu = new JPopupMenu();
        JMenuItem updateMenu = new JCheckBoxMenuItem("Düzenle");
        JMenuItem deleteMenu = new JCheckBoxMenuItem("Sil");
        pathsMenu.add(updateMenu);
        pathsMenu.add(deleteMenu);
        tblPaths.setComponentPopupMenu(pathsMenu);

        updateMenu.addActionListener(e -> {
            Path path = new Path();
            path.setId(Integer.parseInt(tblPaths.getValueAt(tblPaths.getSelectedRow(), 0).toString()));
            path.setName(tblPaths.getValueAt(tblPaths.getSelectedRow(), 1).toString());
            UpdatePathGUI updatePathGUI = new UpdatePathGUI(path, tblPaths);
        });

        deleteMenu.addActionListener(e -> {
            if (Functions.confirm()) {
                int selectedId = Integer.parseInt(tblPaths.getValueAt(tblPaths.getSelectedRow(), 0).toString());

                if (Path.delete(selectedId))
                    Functions.showMessage("success", "info", JOptionPane.INFORMATION_MESSAGE);
                else
                    Functions.showMessage("err", "err", JOptionPane.ERROR_MESSAGE);

                Functions.getPaths(tblPaths);
            }
        });

        tblPaths.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedRow = tblPaths.rowAtPoint(point);
                tblPaths.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

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

        btnDelete.addActionListener(e -> {
            if (Functions.isFieldEmpty(txtUserId))
                Functions.showMessage("fill", "err", JOptionPane.ERROR_MESSAGE);
            else {
                if (Functions.confirm()) {
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

        btnAddPath.addActionListener(e -> {
            Path path;

            if (Functions.isFieldEmpty(txtPathName))
                Functions.showMessage("fill", "err", JOptionPane.ERROR_MESSAGE);
            else {
                path = new Path();
                path.setName(txtPathName.getText());

                if (Path.add(path)) {
                    txtPathName.setText(null);
                }

                Functions.getPaths(tblPaths);
            }
        });
    }
}
