package ru.hogwarts.school.component;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.record.AvatarRecord;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;

@Component
public class RecordMapper {
    public StudentRecord toRecord(Student student) {
        StudentRecord studentRecord = new StudentRecord();
        studentRecord.setId(student.getId());
        studentRecord.setName(student.getName());
        studentRecord.setAge(student.getAge());
        if (student.getFaculty() != null) {
            studentRecord.setFaculty(toRecord(student.getFaculty()));
        }
        return studentRecord;
    }

    public FacultyRecord toRecord(Faculty faculty) {
        FacultyRecord facultyRecord = new FacultyRecord();
        facultyRecord.setId(faculty.getId());
        facultyRecord.setName(faculty.getName());
        facultyRecord.setColor(faculty.getColor());
        return facultyRecord;
    }

    public AvatarRecord toRecord(Avatar avatar) {
        AvatarRecord avatarRecord = new AvatarRecord();
        avatarRecord.setId(avatarRecord.getId());
        avatarRecord.setMediaType(avatarRecord.getMediaType());
        avatarRecord.setUrl(avatarRecord.getUrl());
        return avatarRecord;
    }

    public Student toEntity(StudentRecord studentRecord) {
        Student student = new Student();
        student.setName(studentRecord.getName());
        student.setAge(studentRecord.getAge());
        if (studentRecord.getFaculty() != null) {
            student.setFaculty(toEntity(studentRecord.getFaculty()));
            return student;
        }

        public Faculty toEntity (FacultyRecord facultyRecord){
            Faculty faculty = new Faculty();
            faculty.setName(facultyRecord.getName());
            faculty.setColor(facultyRecord.getColor());
            return faculty;
        }
    }
}