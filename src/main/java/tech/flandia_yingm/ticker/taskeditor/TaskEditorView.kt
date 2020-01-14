package tech.flandia_yingm.ticker.taskeditor

import tech.flandia_yingm.ticker.task.MultipleTask
import tech.flandia_yingm.ticker.task.TaskCompletion
import tech.flandia_yingm.ticker.task.TaskUtils.asLocalDateProperty
import tech.flandia_yingm.ticker.task.TaskUtils.asLocalTimeProperty
import tech.flandia_yingm.ticker.task.TaskUtils.timeToStringProp
import tech.flandia_yingm.ticker.task.TaskUtils.dateToStringProp
import tornadofx.*

class TaskEditorView(editing: MultipleTask) : View() {

    private val model = TaskModel(editing)

    override val root = form {
        title = "Task Editor"
        paddingAll = 25.0
        fieldset {
            field("Name") {
                textfield(model.name)
            }
            field("Comment") {
                textfield(model.comment)
            }
            field("Deadline") {
                datepicker(model.deadline.asLocalDateProperty())
                textfield(model.deadline.asLocalTimeProperty().timeToStringProp()) { }
            }
            field("Completion") {
                combobox<TaskCompletion>(model.completion, TaskCompletion.values().toList().observable())
            }
        }
        hbox {
            spacer()
            button("Save") {
                action {
                    model.commit()
                    close()
                }
            }
            spacer()
            button("Cancel") {
                action {
                    model.rollback()
                    close()
                }
            }
            spacer()
        }
    }

}

