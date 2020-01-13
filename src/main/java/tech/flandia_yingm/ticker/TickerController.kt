package tech.flandia_yingm.ticker

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.transformation.SortedList
import tech.flandia_yingm.ticker.task.Task
import tornadofx.*
import java.time.LocalDateTime

class TickerController : Controller() {

    val taskList: ObservableList<Task> = FXCollections.observableList(
            mutableListOf(
                    Task("Completed Task 1", "Nothing", LocalDateTime.of(1949, 10, 1, 0, 0, 0), true),
                    Task("Completed Task 2", "", LocalDateTime.of(1949, 10, 1, 0, 0, 0), true),
                    Task("Completed Task 3", "Nothing", LocalDateTime.of(2049, 10, 1, 0, 0, 0), true),
                    Task("Completed Task 4", "", LocalDateTime.now(), true),
                    Task("Uncompleted Task 1", "Nothing", LocalDateTime.of(1949, 10, 1, 0, 0, 0), false),
                    Task("Uncompleted Task 2", "", LocalDateTime.of(1949, 10, 1, 0, 0, 0), false),
                    Task("Uncompleted Task 3", "Nothing", LocalDateTime.of(2049, 10, 1, 0, 0, 0), false),
                    Task("Uncompleted Task 4", "", LocalDateTime.now(), false)
            ),
            { arrayOf(it.nameProperty, it.commentProperty, it.deadlineProperty, it.completedProperty) }
    )

    val sortedTaskList: SortedList<Task> = taskList.sorted()

}
