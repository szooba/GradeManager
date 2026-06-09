package models;

import java.time.LocalDate;

public class Exam extends Model {
    public Model courseId;
    public LocalDate date;
    private final String name;

    public Exam(int id, String name, Model courseId, LocalDate date) {
        super(id);
        this.name = name;
        this.courseId = courseId;
        this.date = date;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Model getCourseId() {
        return this.courseId;
    }
    
    @Override
    public String toString() {
        return getName();
    }

    @Override
    public Object[] toObject() {
        Object[] obj = new Object[]{this, name, courseId.getName(), date};
        return obj;
    }

}