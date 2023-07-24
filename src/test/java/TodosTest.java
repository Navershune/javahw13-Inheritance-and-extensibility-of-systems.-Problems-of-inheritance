import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.Epic;
import ru.netology.Meeting;
import ru.netology.SimpleTask;
import ru.netology.Task;
import ru.netology.Todos;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TodosTest {

    @Test
    public void shouldReturnMatchingTasksForSearchQuery() {
        SimpleTask simpleTask1 = new SimpleTask(1, "Buy groceries");
        SimpleTask simpleTask2 = new SimpleTask(2, "Finish report");
        SimpleTask simpleTask3 = new SimpleTask(3, "Call client");
        Epic epic = new Epic(4, new String[]{"Update UI", "Refactor code", "Write tests"});
        Meeting meeting = new Meeting(5, "Project kickoff", "New project", "2023-05-15");

        Todos todos = new Todos();
        todos.add(simpleTask1);
        todos.add(simpleTask2);
        todos.add(simpleTask3);
        todos.add(epic);
        todos.add(meeting);

        // Тестирование поиска по подзадачам Epic
        Task[] expected1 = {epic};
        Task[] actual1 = todos.search("tests");
        Assertions.assertArrayEquals(expected1, actual1);

        // Тестирование поиска по теме встречи
        Task[] expected2 = {meeting};
        Task[] actual2 = todos.search("kickoff");
        Assertions.assertArrayEquals(expected2, actual2);
    }

    @Test
    public void shouldReturnEmptyArrayForNonMatchingSearchQuery() {
        SimpleTask simpleTask1 = new SimpleTask(1, "Buy groceries");
        SimpleTask simpleTask2 = new SimpleTask(2, "Finish report");
        SimpleTask simpleTask3 = new SimpleTask(3, "Call client");
        Epic epic = new Epic(4, new String[]{"Update UI", "Refactor code", "Write tests"});
        Meeting meeting = new Meeting(5, "Project kickoff", "New project", "2023-05-15");

        Todos todos = new Todos();
        todos.add(simpleTask1);
        todos.add(simpleTask2);
        todos.add(simpleTask3);
        todos.add(epic);
        todos.add(meeting);

        // Тестирование поиска с неподходящим запросом
        Task[] expected = {};
        Task[] actual = todos.search("meeting");
        Assertions.assertArrayEquals(expected, actual);
    }
    @Test
    public void testMatches() {
        Task task1 = new SimpleTask(1, "Finish homework");
        assertTrue(task1.matches("homework"));
        assertFalse(task1.matches("project"));

        String[] subtasks = {"Task 1", "Task 2", "Task 3"};
        Task task2 = new Epic(2, subtasks);
        assertTrue(task2.matches("Task 1"));
        assertTrue(task2.matches("Task"));
        assertFalse(task2.matches("Project"));

        Task task3 = new Meeting(3, "Weekly Meeting", "Project X", "2023-05-16T09:00");
        assertTrue(task3.matches("Meeting"));
        assertTrue(task3.matches("Project"));
        assertFalse(task3.matches("Task"));
    }
    @Test
    public void testMatchesSimpleTask() {
        // Arrange
        Todos todos = new Todos();
        SimpleTask task = new SimpleTask(1, "Task 1");
        todos.add(task);

        // Act
        Task[] result = todos.search("Task");

        // Assert
        Task[] expected = {task};
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void testMatchesEpic() {
        // Arrange
        Todos todos = new Todos();
        Epic epic = new Epic(1, new String[]{"Subtask 1", "Subtask 2"});
        todos.add(epic);

        // Act
        Task[] result = todos.search("Subtask");

        // Assert
        Task[] expected = {epic};
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void testMatchesMeeting() {
        // Arrange
        Todos todos = new Todos();
        Meeting meeting = new Meeting(1, "Discussion", "Project 1", "2023-05-14");
        todos.add(meeting);

        // Act
        Task[] result = todos.search("Project");

        // Assert
        Meeting[] expected = { meeting };
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void testMatchesNoMatch() {
        // Arrange
        Todos todos = new Todos();
        SimpleTask task = new SimpleTask(1, "Task 1");
        todos.add(task);

        // Act
        Task[] result = todos.search("Meeting");

        // Assert
        Task[] expected = new Task[0];
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchMultipleTasksFound() {
        Todos todos = new Todos();

        Task task1 = new SimpleTask(1, "Buy groceries");
        Task task2 = new SimpleTask(2, "Clean the house");
        Task task3 = new SimpleTask(3, "Walk the dog");

        todos.add(task1);
        todos.add(task2);
        todos.add(task3);

        Task[] result = todos.search("the");

        assertArrayEquals(new Task[] { task2, task3 }, result);
    }

    @Test
    public void testSearchOneTaskFound() {
        Todos todos = new Todos();

        Task task1 = new SimpleTask(1, "Buy groceries");
        Task task2 = new SimpleTask(2, "Clean the house");
        Task task3 = new SimpleTask(3, "Walk the dog");

        todos.add(task1);
        todos.add(task2);
        todos.add(task3);

        Task[] result = todos.search("dog");

        assertArrayEquals(new Task[] { task3 }, result);
    }

    @Test
    public void testSearchNoTasksFound() {
        Todos todos = new Todos();

        Task task1 = new SimpleTask(1, "Buy groceries");
        Task task2 = new SimpleTask(2, "Clean the house");
        Task task3 = new SimpleTask(3, "Walk the dog");

        todos.add(task1);
        todos.add(task2);
        todos.add(task3);

        Task[] result = todos.search("work");

        assertArrayEquals(new Task[0], result);
    }
}