package pl.agh.edu.erasmus_system.controllers.qualification.response_bodies;

import lombok.Getter;
import pl.agh.edu.erasmus_system.model.Student;

@Getter
public class QualificationStudentResponseBody {
    private Long id;
    private String name;
    private String surname;
    private String year;
    private String email;
    private String department;
    private String field;

    public QualificationStudentResponseBody(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.surname = student.getSurname();
        this.year = student.getYear();
        this.email = student.getEmail();
        this.department = student.getDepartment();
        this.field = student.getField();
    }
}