package tech.flandia_yingm.ticker

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.transformation.SortedList
import tech.flandia_yingm.ticker.task.MultipleTask
import tech.flandia_yingm.ticker.task.Task
import tech.flandia_yingm.ticker.task.TaskCompletion
import tech.flandia_yingm.ticker.taskeditor.TaskEditorView
import tornadofx.*
import java.time.LocalDateTime

class TickerController : Controller() {

    private val tasks: ObservableList<Task> = FXCollections.observableList(
            mutableListOf(
                    Task("Completed Task 1", "Nothing", LocalDateTime.of(1949, 10, 1, 0, 0, 0), TaskCompletion.COMPLETE),
                    Task("Completed Task 2", "", LocalDateTime.of(1949, 10, 1, 0, 0, 0), TaskCompletion.COMPLETE),
                    Task("Completed Task 3", "Nothing", LocalDateTime.of(2049, 10, 1, 0, 0, 0), TaskCompletion.COMPLETE),
                    Task("Completed Task 4", "", LocalDateTime.now(), TaskCompletion.COMPLETE),
                    Task("Uncompleted Task 1", "Nothing", LocalDateTime.of(1949, 10, 1, 0, 0, 0), TaskCompletion.INCOMPLETE),
                    Task("Uncompleted Task 2", "", LocalDateTime.of(1949, 10, 1, 0, 0, 0), TaskCompletion.INCOMPLETE),
                    Task("Uncompleted Task 3", "Nothing", LocalDateTime.of(2049, 10, 1, 0, 0, 0), TaskCompletion.INCOMPLETE),
                    Task("Uncompleted Task 4", "", LocalDateTime.now(), TaskCompletion.INCOMPLETE)
            )
    ) { arrayOf(it.nameProperty, it.commentProperty, it.deadlineProperty, it.completedProperty) }

    val sortedTasks: SortedList<Task> = tasks.sorted()

    val selectedTasks: MutableList<Task> = mutableListOf()


    fun completeSelectedTasks() {
        selectedTasks.toList().forEach { it.completed = TaskCompletion.COMPLETE }
    }

    fun incompleteSelectedTasks() {
        selectedTasks.toList().forEach { it.completed = TaskCompletion.INCOMPLETE }
    }

    fun editSelectedTasks() {
        TaskEditorView(MultipleTask(selectedTasks.toList())).openModal()
    }

}
