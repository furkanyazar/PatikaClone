package com.patikadev;

import com.patikadev.Helpers.DbConnector;
import com.patikadev.Helpers.Functions;
import com.patikadev.Models.Operator;
import com.patikadev.Views.OperatorGUI;

public class Main {

    public static void main(String[] args) {
        Operator operator = new Operator();
        operator.setId(1);
        operator.setName("Furkan");
        operator.setUsername("admin");
        operator.setPassword("123456");
        operator.setType("operator");

        Functions.setLayout();
        OperatorGUI operatorGUI = new OperatorGUI(operator);

        DbConnector.getInstance();
    }
}
