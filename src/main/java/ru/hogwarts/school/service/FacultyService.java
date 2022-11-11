package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    public FacultyService(FacultyRepository facultyRepository, RecordMapper recordMapper) {
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public FacultyRecord create(FacultyRecord facultyRecord) {
        return recordMapper.toRecord(facultyRepository.save(recordMapper.toEntity(facultyRecord)));
    }

    public FacultyRecord read(long id) {
        return recordMapper.toRecord(facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id)));
    }

    public FacultyRecord update(long id, FacultyRecord facultyRecord) {
        Faculty oldFaculty = facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
        oldFaculty.setName(facultyRecord.getName());
        oldFaculty.setColor(facultyRecord.getColor());
        return recordMapper.toRecord(facultyRepository.save(oldFaculty));
    }

    public FacultyRecord delete(long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
        facultyRepository.delete(faculty);
        return recordMapper.toRecord(faculty);
    }

    public Collection<FacultyRecord> findByColor(String color) {
        return facultyRepository.findAllByColor(color).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<FacultyRecord> findByNameOrColor(String nameOrColor) {
        return facultyRepository.findAllByColorIgnoreCaseOrNameIgnoreCase(nameOrColor, nameOrColor).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentController> findStudentsByFaculty(long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id)).setStudents().stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }
}
