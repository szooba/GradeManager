package dialog;

import java.awt.*;
import javax.swing.*;

import databasemanager.DatabaseManagerGateway;
import models.Student;
import models.Model;

class StudentDialog extends Dialog {
    private JTextField nameField, emailField;
    private Student convertedModel;

    public StudentDialog(Frame owner, Model model, DatabaseManagerGateway gatewayManager) {
        super(owner, model, gatewayManager);
        initiateComponents();
        if(model != null) {
            convertedModel = (Student) model;
            nameField.setText(convertedModel.getName());
            emailField.setText(convertedModel.getEmail());
        }
        window();
    }

    private void window() {
        add(new JLabel("Name"));
        add(nameField);
        
        add(new JLabel("Email"));
        add(emailField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> save());
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void save() {
       if(model != null) {
        gatewayManager.getStudentManager().update(convertedModel.getId(),nameField.getText(),emailField.getText());
       }else {
        gatewayManager.getStudentManager().add(nameField.getText(),emailField.getText());
       }
       dispose();
    }

    private void initiateComponents() {
        nameField = new JTextField();
        emailField = new JTextField();
    }
}
