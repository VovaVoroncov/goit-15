package ua.goit.hw15;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    private NoteService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        noteService = new NoteService(noteRepository);
    }

    @Test
    void testListAll() {
        // Arrange
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1L, "Note 1"));
        notes.add(new Note(2L, "Note 2"));
        when(noteRepository.findAll()).thenReturn(notes);

        // Act
        List<Note> result = noteService.listAll();

        // Assert
        assertEquals(notes.size(), result.size());
        assertEquals(notes, result);
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void testAdd() {
        // Arrange
        Note note = new Note(1L, "New Note");
        when(noteRepository.save(note)).thenReturn(note);

        // Act
        Note result = noteService.add(note);

        // Assert
        assertEquals(note, result);
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    void testDeleteById() {
        // Arrange
        long id = 1L;

        // Act
        noteService.deleteById(id);

        // Assert
        verify(noteRepository, times(1)).deleteById(id);
    }

    @Test
    void testUpdate_ExistingNote() {
        // Arrange
        Note note = new Note(1L, "Updated Note");
        when(noteRepository.existsById(note.getId())).thenReturn(true);
        when(noteRepository.save(note)).thenReturn(note);

        // Act
        noteService.update(note);

        // Assert
        verify(noteRepository, times(1)).existsById(note.getId());
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    void testUpdate_NonexistentNote() {
        // Arrange
        Note note = new Note(1L, "Updated Note");
        when(noteRepository.existsById(note.getId())).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> noteService.update(note));
        verify(noteRepository, times(1)).existsById(note.getId());
        verify(noteRepository, never()).save(note);
    }

    @Test
    void testGetById_ExistingNote() {
        // Arrange
        long id = 1L;
        Note note = new Note(id, "Note");
        when(noteRepository.findById(id)).thenReturn(Optional.of(note));

        // Act
        Note result = noteService.getById(id);

        // Assert
        assertEquals(note, result);
        verify(noteRepository, times(1)).findById(id);
    }

    @Test
    void testGetById_NonexistentNote() {
        // Arrange
        long id = 1L;
        when(noteRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> noteService.getById(id));
        verify(noteRepository, times(1)).findById(id);
    }
}
