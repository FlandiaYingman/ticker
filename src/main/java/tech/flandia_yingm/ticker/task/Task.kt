package tech.flandia_yingm.ticker.task

import javafx.beans.Observable
import javafx.beans.property.ReadOnlyStringProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tech.flandia_yingm.ticker.task.TaskUtils.asNowRelativePeriod
import tech.flandia_yingm.ticker.task.TaskUtils.format
import tech.flandia_yingm.ticker.util.PropertyUtils.asStringProperty
import tornadofx.getValue
import tornadofx.setValue
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class Task(
        name: String = "",
        comment: String = "",
        deadline: LocalDateTime = LocalDateTime.now(),
        completion: TaskCompletion = TaskCompletion.INCOMPLETE
) : Comparable<Task> {

    val nameProperty = SimpleStringProperty(this, "name", name)
    var name: String by nameProperty

    val commentProperty = SimpleStringProperty(this, "comment", comment)
    var comment: String by commentProperty

    val deadlineProperty = SimpleObjectProperty(this, "deadline", deadline)
    var deadline: LocalDateTime by deadlineProperty

    val completionProperty = SimpleObjectProperty(this, "completion", completion)
    var completion: TaskCompletion by completionProperty

    val nameStringProperty: ReadOnlyStringProperty
        get() = nameProperty.asStringProperty { if (it.isNullOrEmpty()) "<No Name>" else it }
    val commentStringProperty: ReadOnlyStringProperty
        get() = commentProperty.asStringProperty { if (it.isNullOrEmpty()) "<No Comment>" else it }
    val deadlineDurationStringProperty: ReadOnlyStringProperty
        get() = deadlineProperty.asNowRelativePeriod().asStringProperty { if (it.isNegative) "${it.format()} To" else "${it.format()} Past" }
    val deadlineStringProperty: ReadOnlyStringProperty
        get() = deadlineProperty.asStringProperty { it.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) }
    val completionStringProperty: ReadOnlyStringProperty
        get() = completionProperty.asStringProperty {
            when (it) {
                TaskCompletion.INCOMPLETE -> "Incomplete"
                TaskCompletion.COMPLETE -> "Complete"
                else -> "<NULL_ERROR>"
            }
        }

    override fun compareTo(other: Task): Int =
            Comparator.comparing(Task::completion)
                    .thenComparing(Task::deadline)
                    .thenComparing(Task::name)
                    .thenComparing(Task::comment)
                    .compare(this, other)

    fun extractObservable(): Array<Observable> = arrayOf(nameProperty, commentProperty, deadlineProperty, completionProperty)

}