import dialog.Dialog;
import dialog.DialogSelector;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

import databasemanager.DatabaseManagerGateway;
import models.Model;

public class GradeManagerGUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private final JTextField searchField;
    private JComboBox<String> tableCombo;
    private final JComboBox<String> searchCombo;
    private JScrollPane scrollPane;
    private DatabaseManagerGateway gatewayManager;
    
    public GradeManagerGUI() {
        setTitle("Grade Manager");
        setSize(900, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton newStudentButton = new JButton("New record");
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            dispose();
        });
        newStudentButton.addActionListener(e -> {
            Dialog dialog = DialogSelector.openDialog(gatewayManager.getSelected(), GradeManagerGUI.this, null, gatewayManager);
            dialog.setVisible(true);
            refresh();
        });
        searchField = new JTextField(20);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                Search();
            }
        
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                Search();
            }
        
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                Search();
            }
        });
        JLabel searchLabel= new JLabel("Search");
        JLabel searchCriteriaLabel= new JLabel("Search criteria");
        JLabel tableLabel= new JLabel("Tables");
        tableCombo = new JComboBox<>(new String[]{"Grades", "Exams", "Students", "Course"});
        searchCombo = new JComboBox<>(new String[]{});
        tableCombo.addActionListener(e -> {
            int selected = tableCombo.getSelectedIndex();
            tableSelect(selected);

        });


        topPanel.add(newStudentButton);
        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(searchCriteriaLabel);
        topPanel.add(searchCombo);
        topPanel.add(tableLabel);
        topPanel.add(tableCombo);
        topPanel.add(exitButton);

        add(topPanel, BorderLayout.NORTH);

        // Bottom table
        gatewayManager = new DatabaseManagerGateway();
        refresh();
        
    }

    // Refreshing table
    private void refresh() {
        gatewayManager.reload();
        tableSelect(gatewayManager.getSelected());

    }

    // Searching through the records
    private void Search() {
        String text = searchField.getText();
        if (text.trim().length() == 0) {
            ((TableRowSorter<?>) table.getRowSorter()).setRowFilter(null);
        } else {
            int colIndex = searchCombo.getSelectedIndex();
            ((TableRowSorter<?>) table.getRowSorter()).setRowFilter(RowFilter.regexFilter("(?i)" + text, colIndex+1));
        }
    }
    
    // Format table
    private void tableFormat() {
        ArrayList<String> columnNames = gatewayManager.getSelectedTable().columnNames();
        updateSearchCombo(columnNames);
        columnNames.add(0,"this");
        columnNames.add("Actions");
        model = new DefaultTableModel(null, arrayToString(columnNames)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == columnNames.size() - 1;
            }
        };
    
        table = new JTable(model);
        table.setRowHeight(35);
        table.removeColumn(table.getColumnModel().getColumn(0));
        table.setRowSorter(new TableRowSorter<>(model));
    
        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));
    
        if (scrollPane != null) {
            remove(scrollPane);
        }
    
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    
        revalidate();
        repaint();
    
        loadData();
    }

    private void updateSearchCombo(ArrayList<String> columns) {
        searchCombo.setModel(new DefaultComboBoxModel<>(arrayToString(columns)));
    }

    private void tableSelect(int selected) {
        gatewayManager.selectTable(selected);
        tableFormat();
    }

    private void loadData() {
        ArrayList<Model> models = gatewayManager.getSelectedTable().getAll();
        for(int i = 0; i < models.size(); i++) {
            model.addRow(models.get(i).toObject());
        }

    }

    // Button placement in the records
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            add(new JButton("Edit"));
            add(new JButton("Delete"));
        }
    
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }
    

    // Init buttons next to the records
    class ButtonEditor extends DefaultCellEditor {
        private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            JButton editButton = new JButton("Edit");
            JButton deleteButton = new JButton("Delete");
            panel.add(editButton);
            panel.add(deleteButton);

            deleteButton.addActionListener(e -> {
                int viewRow = table.getSelectedRow();
                int modelRow = table.convertRowIndexToModel(viewRow);
                Model selectedModel = (Model) model.getValueAt(modelRow, 0);
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?");
                if (confirm == JOptionPane.YES_OPTION) {
                    gatewayManager.getSelectedTable().delete(selectedModel.getId());
                    refresh();
                }
            });

            editButton.addActionListener(e -> {
                int viewRow = table.getSelectedRow();
                int modelRow = table.convertRowIndexToModel(viewRow);
                Model selectedModel = (Model) model.getValueAt(modelRow, 0);
                Dialog dialog = DialogSelector.openDialog(gatewayManager.getSelected(), GradeManagerGUI.this, selectedModel, gatewayManager);
                dialog.setVisible(true);
                refresh();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            return panel;
        }
    }
    
    public String[] arrayToString(ArrayList<String> array) {
        String[] list = array.toArray(String[]::new);
        return list;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GradeManagerGUI().setVisible(true));
    }

}
