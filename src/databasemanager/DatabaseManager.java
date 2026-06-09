package databasemanager;

import java.sql.Connection;
import java.util.ArrayList;

import models.Model;

public abstract class DatabaseManager {
    
    public Connection conn;
    public ArrayList<Model> getAll(){return null;}
    public boolean add(){return false;}
    public boolean update(){return false;}
    public boolean delete(int id){return false;}
    public ArrayList<String> columnNames(){return null;}

    public DatabaseManager() {
        conn = Database.connect();
    }

    public Model searchById(int id, DatabaseManager searchManager) {
        for (int i = 0; i < searchManager.getAll().size(); i++) {
            if(searchManager.getAll().get(i).getId() == id) {
                return searchManager.getAll().get(i);
            }          
        }
        return null;
    }
}

