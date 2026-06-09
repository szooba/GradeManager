package models;

public class Student extends Model {
    public String name;
    public String email;

    public Student(int id, String name, String email) {
        super(id);
        this.name = name;
        this.email = email;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public Object[] toObject() {
        Object[] obj = new Object[]{this, name, email};
        return obj;
    }
}