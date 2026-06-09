package databasemanager;

import java.sql.*;
import java.util.*;

import models.Grade;
import models.Model;

public class GradeDatabaseManager extends DatabaseManager{

    private ArrayList<Model> list;
    
    @SuppressWarnings("CallToPrintStackTrace")
    public GradeDatabaseManager(DatabaseManager studentManager, DatabaseManager examManager) {
        list = new ArrayList<>();
        try (
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Grades")) {

            while (rs.next()) {
                list.add(new Grade(
                    rs.getInt("GradeID"),
                    searchById(rs.getInt("StudentID"), studentManager),
                    searchById(rs.getInt("ExamID"), examManager),
                    rs.getInt("GradeValue")
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

    @SuppressWarnings("CallToPrintStackTrace")
    public boolean add(int studentId, int examId, int grade) {
        try (
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Grades (StudentID, ExamID, GradeValue) VALUES (?, ?, ?)")) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, examId);
            stmt.setInt(3, grade);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public boolean update(int id, int studentId, int examId, int grade) {
        try (
            PreparedStatement stmt = conn.prepareStatement("UPDATE Grades SET StudentID=?, ExamID=?, GradeValue=? WHERE GradeID=?")) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, examId);
            stmt.setInt(3, grade);
            stmt.setInt(4, id);
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
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Grades WHERE GradeID=?")) {
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
        columnName.add("Students");
        columnName.add("Exams");
        columnName.add("Grades");
        return columnName;
    }
}
