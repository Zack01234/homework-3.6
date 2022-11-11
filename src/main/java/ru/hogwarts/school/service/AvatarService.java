package ru.hogwarts.school.service;

import lombok.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.record.AvatarRecord;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final RecordMapper recordMapper;
    @Value("${application.avatar.store}")
    private String avatarDir;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository, RecordMapper recordMapper) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.recordMapper = recordMapper
    }

    public AvatarRecord create(MultipartFile multipartFile, long studentId) throws IOException {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
        byte[] data = multipartFile.getBytes();
        String extension = Optional.ofNullable(multipartFile.getOriginalFilename())
                .map(fileName -> fileName.substring(multipartFile.getOriginalFilename().lastIndexOf('.')))
                .orElse("");
        Path path = Paths.get(avatarDir).resolve(studentId + extension);
        Files.write(path, data);
        Avatar avatar = new Avatar();
        avatar.setData(data);
        avatar.setFileSize(data.length);
        avatar.setMediaType(multipartFile.getContentType());
        avatar.setStusent(student);
        avatar.setFilePath(path.toString());
        return recordMapper.toRecord(avatarRepository.save(avatar));
    }

    public Pair<byte[], String> readFromFs(long id) throws IOException {
        Avatar avatar = avatarRepository.findById(id).orElseThrow(() -> new AvatarNotFoundException(id));
        return Pair.of(Files.readAllBytes(Paths.get(avatar.getFilePath())), avatar.getMediaType());
    }

    public Pair<byte[], String> readFromDb(long id) {
        Avatar avatar = avatarRepository.findById(id).orElseThrow(() -> new AvatarNotFoundException(id));
        return Pair.of(avatar.getData(), avatar.getMediaType());
    }
}
