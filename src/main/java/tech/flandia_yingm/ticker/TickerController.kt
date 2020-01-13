package tech.flandia_yingm.ticker

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.collections.transformation.SortedList
import javafx.scene.control.MultipleSelectionModel
import tech.flandia_yingm.ticker.task.MultipleTask
import tech.flandia_yingm.ticker.task.Task
import tech.flandia_yingm.ticker.task.TaskCompletion
import tech.flandia_yingm.ticker.taskeditor.TaskEditorView
import tornadofx.Controller
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

    val selectionModelProperty: ObjectProperty<MultipleSelectionModel<Task>> = SimpleObjectProperty<MultipleSelectionModel<Task>>()

    val selectedTasks: ObservableList<Task>
        get() = selectionModelProperty.value.selectedItems

    init {
        tasks.setAll(JsonUtils.loadTasks())
        tasks.addListener(ListChangeListener<Task> {
            JsonUtils.saveTasks(tasks)
        })
    }

    fun newTask() {
        val newTask = Task()
        tasks.add(newTask)
        selectionModelProperty.value.clearSelection()
        selectionModelProperty.value.select(newTask)
    }

    fun removeSelectedTasks() {
        tasks.removeAll(selectedTasks.toList())
        selectionModelProperty.value.clearSelection()
    }

    fun completeSelectedTasks() {
        selectedTasks.toList().forEach { it.completed = TaskCompletion.COMPLETE }
        selectionModelProperty.value.clearSelection()
    }

    fun incompleteSelectedTasks() {
        selectedTasks.toList().forEach { it.completed = TaskCompletion.INCOMPLETE }
        selectionModelProperty.value.clearSelection()
    }

    fun editSelectedTasks() {
        TaskEditorView(MultipleTask(selectedTasks.toList())).openModal()
    }

}
