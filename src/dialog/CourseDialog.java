package dialog;

import java.awt.*;
import javax.swing.*;

import databasemanager.DatabaseManagerGateway;
import models.Course;
import models.Model;

final class CourseDialog extends Dialog {
    private JTextField courseNameField;
    private Course convertedModel;

    public CourseDialog(Frame owner, Model model, DatabaseManagerGateway gatewayManager) {
        super(owner, model, gatewayManager);
        initiateComponents();
        if(model != null) {
            convertedModel = (Course) model;
            courseNameField.setText(convertedModel.getName());
        }
        ablak();
    }

    private void ablak() {
        add(new JLabel("Name"));
        add(courseNameField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> save());
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void save() {
        if(model != null) {
            gatewayManager.getCourseManager().update(convertedModel.getId(),courseNameField.getText());
        }else {
            gatewayManager.getCourseManager().add(courseNameField.getText());
        }
        dispose();
    }

    public void initiateComponents() {
        courseNameField = new JTextField();
    }
}
