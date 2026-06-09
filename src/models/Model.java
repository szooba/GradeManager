package models;

public abstract class Model {
    public int id;

    public Model(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public Object[] toObject() {return null;}
    public String getName() {return null;}
}
