package models;


public class Course extends Model {
    public String name;

    public Course(int id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public Object[] toObject() {
        Object[] obj = new Object[]{this, name};
        return obj;
    }
}