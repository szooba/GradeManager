package databasemanager;

import java.sql.*;
import java.util.ArrayList;

import models.Student;
import models.Model;

public class StudentDatabaseManager extends DatabaseManager{

    private ArrayList<Model> list;

    @SuppressWarnings("CallToPrintStackTrace")
    public StudentDatabaseManager() {
        list = new ArrayList<>();
        try (
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Students");
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Student(rs.getInt("StudentID"), rs.getString("Name"), rs.getString("Email")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Model> getAll() {
        return list;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public boolean add(String name, String email) {
        try (
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Students (Name, Email) VALUES (?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public boolean update(int id, String name, String email) {
        try (
            PreparedStatement stmt = conn.prepareStatement("UPDATE Students SET Name=?, Email=? WHERE StudentID=?")) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setInt(3, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public boolean delete(int id) {
        try (
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Students WHERE StudentID=?")) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<String> columnNames(){
        ArrayList<String> columnName = new ArrayList<>();
        columnName.add("Name");
        columnName.add("Email");
        return columnName;
    }

}