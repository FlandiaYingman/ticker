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

class TickerController : Controller() {

    private val tasks: ObservableList<Task> = FXCollections.observableList(mutableListOf(), Task::extractObservable)

    val sortedTasks: SortedList<Task> = tasks.sorted()

    val selectionModelProperty: ObjectProperty<MultipleSelectionModel<Task>> = SimpleObjectProperty<MultipleSelectionModel<Task>>()

    private val selectedTasks: ObservableList<Task>
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

    fun editSelectedTasks() {
        if (!selectedTasks.isEmpty()) {
            TaskEditorView(MultipleTask(selectedTasks.toList())).openModal()
        }
    }

    fun completeSelectedTasks() {
        selectedTasks.toList().forEach { it.completion = TaskCompletion.COMPLETE }
    }

    fun incompleteSelectedTasks() {
        selectedTasks.toList().forEach { it.completion = TaskCompletion.INCOMPLETE }
    }

}
