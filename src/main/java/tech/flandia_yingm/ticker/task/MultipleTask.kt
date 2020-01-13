package tech.flandia_yingm.ticker.task

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import java.time.LocalDateTime

/**
 * @author Flandia Yingman
 * @version 1.0
 */
class MultipleTask(
        private val tasks: List<Task>
) {

    private val names: List<String>
    val nameProperty: StringProperty = SimpleStringProperty()

    private val comments: List<String>
    val commentProperty: StringProperty = SimpleStringProperty()

    private val deadlines: List<LocalDateTime>
    val deadlineProperty: ObjectProperty<LocalDateTime> = SimpleObjectProperty()

    private val completion: List<TaskCompletion>
    val completionProperty: ObjectProperty<TaskCompletion> = SimpleObjectProperty()

    init {
        if (tasks.isEmpty()) {
            throw IllegalArgumentException("Tasks list ($tasks) is empty.")
        }

        names = tasks.map { it.name }
        comments = tasks.map { it.comment }
        deadlines = tasks.map { it.deadline }
        completion = tasks.map { it.completion }

        nameProperty.value = if (names.distinct().size == 1) names[0] else "<Multiple Names>"
        commentProperty.value = if (comments.distinct().size == 1) comments[0] else "<Multiple Comments>"
        deadlineProperty.value = if (deadlines.distinct().size == 1) deadlines[0] else deadlines[0]
        completionProperty.value = if (completion.distinct().size == 1) completion[0] else completion[0]

        nameProperty.addListener { _, _, _ -> tasks.forEach { it.nameProperty.value = nameProperty.value } }
        commentProperty.addListener { _, _, _ -> tasks.forEach { it.commentProperty.value = commentProperty.value } }
        deadlineProperty.addListener { _, _, _ -> tasks.forEach { it.deadlineProperty.value = deadlineProperty.value } }
        completionProperty.addListener { _, _, _ -> tasks.forEach { it.completionProperty.value = completionProperty.value } }
    }

}