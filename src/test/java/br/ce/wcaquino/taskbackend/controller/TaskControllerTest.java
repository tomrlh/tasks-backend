package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shoulntSaveTaskWithoutDescription() {
        Task task = new Task();
        task.setDueDate(LocalDate.now());
        try {
            controller.save(task);
            Assert.fail("Shouldnt reach here");
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the task description", e.getMessage());
        }
    }

    @Test
    public void shouldntSaveTaskWithoutDate() throws ValidationException {
        Task task = new Task();
        task.setTask("Task description");
        try {
            controller.save(task);
            Assert.fail("Shouldnt reach here");
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the due date", e.getMessage());
        }
    }

    @Test
    public void shoulntSaveTaskWithPastDates() throws ValidationException {
        Task task = new Task();
        task.setTask("Task description");
        task.setDueDate(LocalDate.now().minusDays(1));
        try {
            controller.save(task);
            Assert.fail("Shouldnt reach here");
        } catch (ValidationException e) {
            Assert.assertEquals("Due date must not be in past", e.getMessage());
        }
    }

    @Test
    public void shouldSaveTaskSuccessfully() throws ValidationException {
        Task task = new Task();
        task.setTask("Task description");
        task.setDueDate(LocalDate.now());
        ResponseEntity<Task> response = controller.save(task);
        
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Mockito.verify(taskRepo).save(task);
    }
}
