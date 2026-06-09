package dialog;

import java.awt.*;
import javax.swing.*;

import databasemanager.DatabaseManagerGateway;
import models.Grade;
import models.Model;

final class GradeDialog extends Dialog {
    private JComboBox<Model> studentCombo, examCombo;
    private Grade convertedModel;
    private JTextField gradeField;
    Model[] students;
    Model[] exams;
    public GradeDialog(Frame owner, Model model, DatabaseManagerGateway gatewayManager) {
        super(owner, model, gatewayManager);
        initiateComponents();
        if(model != null) {
            convertedModel = (Grade) model;
            studentCombo.setSelectedIndex(selectedComboIndex(students, convertedModel.getStudentID()));
            examCombo.setSelectedIndex(selectedComboIndex(exams, convertedModel.getExamID()));
            gradeField.setText(Integer.toString(convertedModel.getGrade()));
        }
        ablak();
    }

    private void ablak() {
        add(new JLabel("Student"));
        add(studentCombo);
        
        add(new JLabel("Exams"));
        add(examCombo);

        add(new JLabel("Grade"));
        add(gradeField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> save());
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void save() {
       if(model != null && gradeCheckerDialog(gradeField.getText()) && uniqueCheckerDialog()) {
        gatewayManager.getGradeManager().update(convertedModel.getId(), students[studentCombo.getSelectedIndex()].getId(), exams[examCombo.getSelectedIndex()].getId(),Integer.parseInt(gradeField.getText()));
       }else if(model == null && gradeCheckerDialog(gradeField.getText()) && uniqueCheckerDialog()){
        gatewayManager.getGradeManager().add(students[studentCombo.getSelectedIndex()].getId(), exams[examCombo.getSelectedIndex()].getId(),Integer.parseInt(gradeField.getText()));
       }
       if(gradeChecker(gradeField.getText()) && uniqueChecker()) {
        dispose();
       }
    }
    private boolean uniqueCheckerDialog() {
        boolean check = uniqueChecker();
        if(check == false) {
            JOptionPane.showMessageDialog(null, "This student already has a grade for that exam");
        }
        return check;
    }
    
    private boolean uniqueChecker() {
        int selectedExamId = exams[examCombo.getSelectedIndex()].getId();
        int selectedStudentId = students[studentCombo.getSelectedIndex()].getId();
        int totalGradesCount = gatewayManager.getGradeManager().getAll().size();
        if(model != null) {
            totalGradesCount = 0;
        }
        for (int i = 0; i < totalGradesCount; i++) {
            Grade existingGrade = (Grade) gatewayManager.getGradeManager().getAll().get(i);
            int existingExamId = existingGrade.getExamID().getId();
            int existingStudentId = existingGrade.getStudentID().getId();
            
            if(selectedExamId == existingExamId && selectedStudentId == existingStudentId) {
                return false;
            }
        }
        return true;
    }

    public void initiateComponents() {
        students = arrayToModel(gatewayManager.getStudentManager().getAll());
        exams = arrayToModel(gatewayManager.getExamManager().getAll());
        gradeField = new JTextField();
        studentCombo = new JComboBox<>(students);
        examCombo = new JComboBox<>(exams);
    }

    private boolean gradeCheckerDialog(String grade) {
        boolean check = gradeChecker(grade);
        if(check == false) {
            JOptionPane.showMessageDialog(null, "Type in a valid grade");
        }
        return check;
    }
    
    public boolean gradeChecker(String grade) {
        try {
           int boolean_to_int = Integer.parseInt(grade);
            return !(boolean_to_int > 5 || boolean_to_int < 1);
        } catch (HeadlessException | NumberFormatException e) {
            return false;
        }
    }
}
