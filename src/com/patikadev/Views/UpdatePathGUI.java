package com.patikadev.Views;

import com.patikadev.Helpers.Configs;
import com.patikadev.Helpers.Functions;
import com.patikadev.Models.Path;

import javax.swing.*;

public class UpdatePathGUI extends JFrame {

    private JPanel wrapper;
    private JTextField txtPathName;
    private JButton btnUpdate;

    private Path path;
    private JTable tblPaths;

    public UpdatePathGUI(Path path, JTable tblPaths) {
        this.path = path;
        this.tblPaths = tblPaths;

        add(wrapper);
        setSize(400, 110);
        setLocation(Functions.screenCenterLocation("x", getSize()), Functions.screenCenterLocation("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Configs.PROJECT_TITLE);
        setVisible(true);

        txtPathName.setText(path.getName());

        btnUpdate.addActionListener(e -> {
            if (Functions.isFieldEmpty(txtPathName))
                Functions.showMessage("fill", "err", JOptionPane.ERROR_MESSAGE);
            else {
                path.setName(txtPathName.getText());
                Path.update(path);

                Functions.getPaths(tblPaths);
                dispose();
            }
        });
    }
}
