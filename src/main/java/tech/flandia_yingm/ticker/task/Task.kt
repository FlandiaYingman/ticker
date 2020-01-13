package tech.flandia_yingm.ticker.task

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import tech.flandia_yingm.ticker.task.TaskUtils.asNowRelativePeriod
import tech.flandia_yingm.ticker.task.TaskUtils.asStringProperty
import tech.flandia_yingm.ticker.task.TaskUtils.format
import tornadofx.getValue
import tornadofx.setValue
import java.time.LocalDateTime

class Task(
        name: String = "",
        comment: String = "",
        deadline: LocalDateTime = LocalDateTime.now(),
        completed: TaskCompletion = TaskCompletion.INCOMPLETE
) : Comparable<Task> {

    val nameProperty = SimpleStringProperty(this, "name", name)
    var name: String by nameProperty

    val commentProperty = SimpleStringProperty(this, "comment", comment)
    var comment: String by commentProperty

    val deadlineProperty = SimpleObjectProperty(this, "deadline", deadline)
    var deadline: LocalDateTime by deadlineProperty

    val completedProperty = SimpleObjectProperty(this, "completed", completed)
    var completed: TaskCompletion by completedProperty


    override fun compareTo(other: Task): Int =
            Comparator.comparing(Task::completed)
                    .thenComparing(Task::deadline)
                    .thenComparing(Task::name)
                    .thenComparing(Task::comment)
                    .compare(this, other)

    val nameStringProperty: StringProperty
        get() = nameProperty.asStringProperty { if (it.isNullOrEmpty()) "<No Name>" else it }
    val commentStringProperty: StringProperty
        get() = commentProperty.asStringProperty { if (it.isNullOrEmpty()) "<No Comment>" else it }
    val deadlineDurationStringProperty: StringProperty
        get() = deadlineProperty.asNowRelativePeriod().asStringProperty { if (it.isNegative) "${it.format()} To" else "${it.format()} Past" }
    val deadlineStringProperty: StringProperty
        get() = deadlineProperty.asStringProperty { it.toString() }
    val completedStringProperty: StringProperty
        get() = completedProperty.asStringProperty {
            when (it) {
                TaskCompletion.INCOMPLETE -> "Incomplete"
                TaskCompletion.COMPLETE -> "Complete"
                TaskCompletion.MULTIPLE_VALUE -> "<Multiple Value>"
                else -> "<NULL_ERROR>"
            }
        }

}