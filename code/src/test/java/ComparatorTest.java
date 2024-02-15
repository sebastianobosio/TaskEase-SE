import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.mycompany.taskmanager.model.ComponentComparators.CombinedComparator;
import com.mycompany.taskmanager.model.*;
import com.mycompany.taskmanager.view.EventView;
import com.mycompany.taskmanager.view.TaskView;

public class ComparatorTest {
	
	@Test
	public void testCombinedComparator() {
        // Create a list of tasks and events in mixed order
        List<Component> combinedList = new ArrayList<>();
        TaskView task1 = new TaskView(new Task("Task 1", "some description", LocalDate.of(2024, 2, 15), LocalTime.of(10, 0), TaskStatus.COMPLETED));
        TaskView task2 = new TaskView(new Task("Task 2", "some description", LocalDate.of(2024, 2, 16), LocalTime.of(8, 0), TaskStatus.COMPLETED));
        EventView event1 = new EventView(new Event("Event 1", "some description", "some location", LocalDate.of(2024, 2, 15), LocalTime.of(10, 0), LocalDate.of(2024, 2, 15), LocalTime.of(10, 0)));
        EventView event2 = new EventView(new Event("Event 3", "some other description", "some other location", LocalDate.of(2024, 2, 16), LocalTime.of(8, 0), LocalDate.of(2024, 2, 17), LocalTime.of(10, 0)));

        combinedList.add(event2);
        combinedList.add(task1);
        combinedList.add(task2);
        combinedList.add(event1);

        // Sort the combined list using the combinedComparator
        Collections.sort(combinedList, new CombinedComparator());

        // Ensure the list is sorted correctly
        assertEquals(task1, combinedList.get(0)); // task1 should come first
        assertEquals(event1, combinedList.get(1)); // event1 should come second
        assertEquals(event2, combinedList.get(2)); // event2 should come third
        assertEquals(task2, combinedList.get(3)); // task2 should come last
    }
	
	@Test
	public void testCombinedComparatorWithEmptyList() {
	    List<Component> combinedList = new ArrayList<>();

	    // Sort the empty combined list using the combinedComparator
	    Collections.sort(combinedList, new CombinedComparator());

	    // Ensure the empty list remains unchanged after sorting
	    assertTrue(combinedList.isEmpty());
	}
	
	@Test
	public void testCombinedComparatorSameDateTime() {

		
		List<Component> combinedList = new ArrayList<>();
        TaskView task1 = new TaskView(new Task("Task 1", "some description", LocalDate.of(2024, 2, 15), LocalTime.of(10, 0), TaskStatus.COMPLETED));
        TaskView task2 = new TaskView(new Task("Task 2", "some description", LocalDate.of(2024, 2, 15), LocalTime.of(10, 0), TaskStatus.COMPLETED));
        EventView event1 = new EventView(new Event("Event 1", "some description", "some location", LocalDate.of(2024, 2, 15), LocalTime.of(10, 0), LocalDate.of(2024, 2, 15), LocalTime.of(10, 0)));
        EventView event2 = new EventView(new Event("Event 3", "some other description", "some other location", LocalDate.of(2024, 2, 15), LocalTime.of(10, 0), LocalDate.of(2024, 2, 17), LocalTime.of(10, 0)));

        combinedList.add(event1);
        combinedList.add(task1);
        combinedList.add(task2);
        combinedList.add(event2);

        // Sort the combined list using the combinedComparator
        Collections.sort(combinedList, new CombinedComparator());

        // Ensure the list is sorted correctly. It should be in order of insert
        assertEquals(event1, combinedList.get(0)); // event1 should come first
        assertEquals(task1, combinedList.get(1)); // task1 should come second
        assertEquals(task2, combinedList.get(2)); // task2 should come third
        assertEquals(event2, combinedList.get(3)); // event2 should come last
	}
}
