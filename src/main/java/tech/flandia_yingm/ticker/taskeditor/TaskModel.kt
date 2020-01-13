package tech.flandia_yingm.ticker.taskeditor

import tech.flandia_yingm.ticker.task.MultipleTask
import tornadofx.ItemViewModel


class TaskModel(task: MultipleTask) : ItemViewModel<MultipleTask>(task) {

    val name = bind(MultipleTask::nameProperty)
    val comment = bind(MultipleTask::commentProperty)
    val deadline = bind(MultipleTask::deadlineProperty)
    val completion = bind(MultipleTask::completionProperty)

}


