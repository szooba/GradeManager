package dialog;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.*;

import databasemanager.DatabaseManagerGateway;
import models.Model;
import models.Exam;

class ExamDialog extends Dialog {
    private JComboBox<Model> courseCombo;
    private Exam convertedModel;
    private JTextField nameField;
    Model[] courses;
    private JTextField dateField;

    public ExamDialog(Frame owner, Model model, DatabaseManagerGateway gatewayManager) {
        super(owner, model, gatewayManager);
        initiateComponents();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        dateField.setText(dtf.format(localDate));
        if(model != null) {
            convertedModel = (Exam) model;
            nameField.setText(convertedModel.getName());
            courseCombo.setSelectedIndex(selectedComboIndex(courses, convertedModel.getCourseId()));
            dateField.setText(convertedModel.getDate().toString());
        }
        ablak();
    }

    private void ablak() {
        add(new JLabel("Name"));
        add(nameField);
        
        add(new JLabel("Course"));
        add(courseCombo);

        add(new JLabel("Date"));
        add(dateField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> save());
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void save() {
        if(checkDate() == true) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formatter = formatter.withLocale( Locale.ENGLISH );
            LocalDate date = LocalDate.parse(dateField.getText(), formatter);
            if(model != null) {
                gatewayManager.getExamManager().update(convertedModel.getId(), nameField.getText(), courses[courseCombo.getSelectedIndex()].getId(), date);
            }else {
                gatewayManager.getExamManager().add(nameField.getText(), courses[courseCombo.getSelectedIndex()].getId(), date);
            }
            dispose();
        }
    }

    private void initiateComponents() {
        courses = arrayToModel(gatewayManager.getCourseManager().getAll());
        courseCombo = new JComboBox<>(courses);
        nameField = new JTextField();
        dateField = new JTextField();

    }

    private boolean checkDate() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formatter = formatter.withLocale( Locale.ENGLISH );
            LocalDate.parse(dateField.getText(), formatter);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Type in a valid date");
            return false;
        }
    }
}