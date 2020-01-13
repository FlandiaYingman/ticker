package tech.flandia_yingm.ticker.taskeditor

import tech.flandia_yingm.ticker.task.MultipleTask
import tech.flandia_yingm.ticker.task.TaskUtils.asLocalDateProperty
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

