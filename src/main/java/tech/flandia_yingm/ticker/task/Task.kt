package tech.flandia_yingm.ticker.task

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDateTime

class Task(
        name: String = "",
        comment: String = "",
        deadline: LocalDateTime = LocalDateTime.now(),
        completed: Boolean = false
) : Comparable<Task> {

    val nameProperty = SimpleStringProperty(this, "name", name)
    var name by nameProperty

    val commentProperty = SimpleStringProperty(this, "comment", comment)
    var comment by commentProperty

    val deadlineProperty = SimpleObjectProperty(this, "deadline", deadline)
    var deadline by deadlineProperty

    val completedProperty = SimpleBooleanProperty(this, "completed", completed)
    var completed by completedProperty


    override fun compareTo(other: Task): Int =
            Comparator.comparing(Task::completed)
                    .thenComparing(Task::deadline)
                    .thenComparing(Task::name)
                    .thenComparing(Task::comment)
                    .compare(this, other)

}