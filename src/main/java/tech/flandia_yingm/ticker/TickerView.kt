package tech.flandia_yingm.ticker

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.ListView
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.text.FontPosture
import tech.flandia_yingm.ticker.task.Task
import tech.flandia_yingm.ticker.taskeditor.TaskEditorView
import tornadofx.*
import tech.flandia_yingm.ticker.task.TaskUtils.asNowRelativePeriod

class TickerView : View() {

    val controller: TickerController by inject()

    val selectedTask: ObjectProperty<Task> = SimpleObjectProperty(Task())

    override val root = borderpane {
        top = hbox {
            button("Complete") {
                action {
                    selectedTask.value.completed = true
                }
            }
            button("Edit") {
                action {
                    TaskEditorView(selectedTask.value).openModal()
                }
            }
        }
        center     = listview(controller.sortedTaskList) {
            taskCellFormat()
            selectedTask.bind(selectionModel.selectedItemProperty())
        }
    }


    private fun ListView<Task>.taskCellFormat() = cellFormat {
        graphic = form {
            fieldset {
                label(it.name) {
                    style {
                        fontSize = 16.pt
                    }
                }
                label(if (it.completed) "Completed" else "Uncompleted") {
                    style {
                        fontSize = 8.pt
                    }
                }
                label(if (it.comment.isNotEmpty()) it.comment else "<No Comment>") {
                    style {
                        fontSize = 12.pt
                        fontStyle = FontPosture.ITALIC
                    }
                }
                label(it.deadlineProperty.asNowRelativePeriod().asString) {
                    style {
                        fontSize = 10.pt
                        fontStyle = FontPosture.ITALIC
                    }
                }
                label(it.deadline.toString()) {
                    style {
                        fontSize = 10.pt
                        fontStyle = FontPosture.ITALIC
                    }
                }
            }
        }
    }

}
