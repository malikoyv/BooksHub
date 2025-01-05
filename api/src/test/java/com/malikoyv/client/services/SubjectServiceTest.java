package com.malikoyv.client.services;

import com.malikoyv.api.services.SubjectService;
import com.malikoyv.core.model.Subject;
import com.malikoyv.core.repositories.ICatalogData;
import com.malikoyv.core.repositories.ISubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectServiceTest {

    @Mock
    private ICatalogData catalogData;

    @Mock
    private ISubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService subjectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(catalogData.getSubjects()).thenReturn(subjectRepository);
    }

    @Test
    void saveSubject_WhenNameIsValid_ShouldSaveSubjectAndReturnId() {
        String subjectName = "Science";

        when(subjectRepository.findByName(subjectName)).thenReturn(Optional.empty());

        when(subjectRepository.save(any(Subject.class))).thenAnswer(invocation -> {
            Subject subject = invocation.getArgument(0);
            subject.setId(1L);
            return subject;
        });

        long savedId = subjectService.saveSubject(subjectName);

        ArgumentCaptor<Subject> captor = ArgumentCaptor.forClass(Subject.class);
        verify(subjectRepository, times(1)).save(captor.capture());
        Subject savedSubject = captor.getValue();
        assertEquals(subjectName, savedSubject.getName());
        assertEquals(1L, savedId);
    }


    @Test
    void saveSubject_WhenNameIsNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> subjectService.saveSubject(null));
    }

    @Test
    void saveSubject_WhenNameIsBlank_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> subjectService.saveSubject(" "));
    }

    @Test
    void saveSubject_WhenSubjectAlreadyExists_ShouldThrowException() {
        String subjectName = "Science";
        when(subjectRepository.findByName(subjectName)).thenReturn(Optional.of(new Subject(subjectName)));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> subjectService.saveSubject(subjectName)
        );

        assertEquals("Subject already exists with name: " + subjectName, exception.getMessage());
        verify(subjectRepository, never()).save(any(Subject.class));
    }

    @Test
    void getAllSubjects_ShouldReturnListOfSubjects() {
        List<Subject> mockSubjects = List.of(new Subject("Math"), new Subject("History"));
        when(subjectRepository.findAll()).thenReturn(mockSubjects);

        List<Subject> subjects = subjectService.getAllSubjects();

        assertNotNull(subjects);
        assertEquals(2, subjects.size());
        assertEquals("Math", subjects.get(0).getName());
        verify(subjectRepository, times(1)).findAll();
    }

    @Test
    void deleteSubject_WhenCalled_ShouldDeleteSubjectById() {
        Long subjectId = 1L;

        subjectService.deleteSubject(subjectId);

        verify(subjectRepository, times(1)).deleteById(subjectId);
    }
}
