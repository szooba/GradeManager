package dialog;

import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import databasemanager.DatabaseManagerGateway;
import models.Model;

public class Dialog extends JDialog{
    protected Model model;
    protected DatabaseManagerGateway gatewayManager;
    protected Frame owner;

    public Dialog(Frame owner, Model model, DatabaseManagerGateway gatewayManager) {
        super(owner, "Edit record",true);
        if(model == null) {
            this.setTitle("Create record");
        }
        this.model = model;
        this.gatewayManager = gatewayManager;
        this.owner = owner;
        initializeUI();
    }

    private void initializeUI() {
        JPanel contentPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPanel);
        setSize(400, 300);
        setLocationRelativeTo(owner);
    }

    public Model[] arrayToModel(ArrayList<Model> array) {
        Model[] list = array.toArray(Model[]::new);
        return list;
    }

    public int selectedComboIndex(Model[] combomodel, Model model) {
        int found = 0;
        for(int i = 0; i < combomodel.length; i++) {
            if(combomodel[i].getId() == model.getId()) {
                found = i;
                i = combomodel.length;
            }
        }
        return found;
    }
}