package databasemanager;

import java.sql.*;
import java.util.*;

import models.Course;
import models.Model;

public class CourseDatabaseManager extends DatabaseManager{

    private ArrayList<Model> list;
    
    @SuppressWarnings("CallToPrintStackTrace")
    public CourseDatabaseManager() {
        list = new ArrayList<>();
        try (
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Courses")) {

            while (rs.next()) {
                list.add(new Course(rs.getInt("CourseID"), rs.getString("CourseName")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Model> getAll() {
        return list;
    }

    public boolean add(String name) {
        try (
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Courses (CourseName) VALUES (?)")) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(int id, String name) {
        try (
            PreparedStatement stmt = conn.prepareStatement("UPDATE Course SET CourseName=? WHERE CourseID=?")) {
            stmt.setString(1, name);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try (
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Courses WHERE CourseID=?")) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    @Override
    public ArrayList<String> columnNames(){
        ArrayList<String> columnName = new ArrayList<>();
        columnName.add("Név");
        return columnName;
    }
}