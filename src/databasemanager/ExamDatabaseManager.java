package databasemanager;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import models.Model;
import models.Exam;

public class ExamDatabaseManager extends DatabaseManager {

    private ArrayList<Model> list;

    @SuppressWarnings("CallToPrintStackTrace")
    public ExamDatabaseManager(DatabaseManager courseManager) {
        list = new ArrayList<>();
        try (
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Exams")) {

            while (rs.next()) {
                list.add(new Exam(
                    rs.getInt("ExamID"),
                    rs.getString("Name"),
                    searchById(rs.getInt("CourseID"), courseManager),
                    LocalDate.parse(rs.getString("Date"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public ArrayList<Model> getAll() {
        return list;
    }

    public boolean add(String name, int courseId, LocalDate date) {
        try (
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Exams (Name, CourseID, Date) VALUES (?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setInt(2, courseId);
            stmt.setString(3, date.toString());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(int id, String name, int courseId, LocalDate date) {
        try (
            PreparedStatement stmt = conn.prepareStatement("UPDATE Exams SET Name=?, CourseID=?, Date=? WHERE ExamID=?")) {
            stmt.setString(1, name);
            stmt.setInt(2, courseId);
            stmt.setString(3, date.toString());
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try (
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Exams WHERE ExamID=?")) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public ArrayList<String> columnNames(){
        ArrayList<String> columnName = new ArrayList<>();
        columnName.add("Name");
        columnName.add("Courses");
        columnName.add("Date");
        return columnName;
    }

}