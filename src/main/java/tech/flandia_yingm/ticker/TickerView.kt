package tech.flandia_yingm.ticker

import javafx.scene.control.SelectionMode
import tech.flandia_yingm.ticker.task.TaskCell
import tornadofx.*
import java.util.*


class TickerView : View() {

    private val controller: TickerController by inject()

    override val root = borderpane {
        top = hbox {
            button(messages["toolbar.new"]) {
                action(controller::newTask)
            }
            button(messages["toolbar.remove"]) {
                action(controller::removeSelectedTasks)
            }
            button(messages["toolbar.edit"]) {
                action(controller::editSelectedTasks)
            }
            button(messages["toolbar.complete"]) {
                action(controller::completeSelectedTasks)   
            }
            button(messages["toolbar.incomplete"]) {
                action(controller::incompleteSelectedTasks)
            }
        }
        center = listview(controller.sortedTasks) {
            controller.selectionModelProperty.bind(selectionModelProperty())
            selectionModel.selectionMode = SelectionMode.MULTIPLE
            cellFormat {
                graphic = TaskCell(it)
            }
        }
    }

}



