package dialog;

import java.awt.Frame;

import databasemanager.DatabaseManagerGateway;
import models.Model;

public class DialogSelector {
    
    public static Dialog openDialog(int selected, Frame owner, Model model, DatabaseManagerGateway gatewayManager) {
        return switch (selected) {
            case 0 -> new GradeDialog(owner, model, gatewayManager);
            case 1 -> new ExamDialog(owner, model, gatewayManager);
            case 2 -> new StudentDialog(owner, model, gatewayManager);
            case 3 -> new CourseDialog(owner, model, gatewayManager);
            default -> new GradeDialog(owner, model, gatewayManager);
        }; 
    }

}
