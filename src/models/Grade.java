package models;

public class Grade extends Model {
    public Model studentId;
    public Model examId;
    public int grade;

    public Grade(int id, Model studentId, Model examId, int grade) {
        super(id);
        this.studentId = studentId;
        this.examId = examId;
        this.grade = grade;
    }

    public Model getStudentID() {
        return studentId;
    }

    public Model getExamID() {
        return examId;
    }

    public int getGrade() {
        return grade;
    }

    @Override
    public Object[] toObject() {
        Object[] obj = new Object[]{this, studentId.getName(), examId.getName(), grade};
        return obj;
    }
}