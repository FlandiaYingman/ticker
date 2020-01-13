package tech.flandia_yingm.ticker.taskeditor

import tech.flandia_yingm.ticker.task.Task
import tornadofx.*


class TaskModel(task: Task) : ItemViewModel<Task>(task) {
    val name = bind(Task::nameProperty)
    val comment = bind(Task::commentProperty)
    val deadline = bind(Task::deadlineProperty)
    val completed = bind(Task::completedProperty)
}


