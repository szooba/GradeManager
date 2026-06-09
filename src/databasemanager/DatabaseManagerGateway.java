package databasemanager;

public final class DatabaseManagerGateway extends DatabaseManager{
    private DatabaseManager studentManager;
    private DatabaseManager courseManager;
    private DatabaseManager examManager;
    private DatabaseManager gradeManager;
    private int selected;

    public DatabaseManagerGateway() {
        selected = 0;
        reload();
    }

    public void reload() {
        studentManager = new StudentDatabaseManager();
        courseManager = new CourseDatabaseManager();
        examManager = new ExamDatabaseManager(courseManager);
        gradeManager = new GradeDatabaseManager(studentManager, examManager);
    }

    public StudentDatabaseManager getStudentManager(){
        return (StudentDatabaseManager)studentManager;
    }
    public CourseDatabaseManager getCourseManager(){
        return (CourseDatabaseManager)courseManager;
    }
    public ExamDatabaseManager getExamManager(){
        return (ExamDatabaseManager)examManager;
    }
    public GradeDatabaseManager getGradeManager(){
        return (GradeDatabaseManager)gradeManager;
    }

    public int getSelected() {
        return this.selected;
    }

    public void selectTable(int selected) {
        this.selected = selected;
    }

    public DatabaseManager getSelectedTable() {
        return switch (selected) {
            case 0 -> gradeManager;
            case 1 -> examManager;
            case 2 -> studentManager;
            case 3 -> courseManager;
            default -> studentManager;
        };     
    }
}
