package tech.flandia_yingm.ticker

import javafx.beans.binding.Bindings
import javafx.scene.control.SelectionMode
import javafx.scene.paint.Color
import javafx.scene.text.FontPosture
import tornadofx.*


class TickerView : View() {

    private val controller: TickerController by inject()

    override val root = borderpane {
        top = hbox {
            button("Complete") {
                action(controller::completeSelectedTasks)
            }
            button("Incomplete") {
                action(controller::incompleteSelectedTasks)
            }
            button("Edit") {
                action(controller::editSelectedTasks)
            }
        }
        center = listview(controller.sortedTasks) {
            Bindings.bindContent(controller.selectedTasks, selectionModel.selectedItems)
            selectionModel.selectionMode = SelectionMode.MULTIPLE

            cellFormat {
                graphic = form {
                    fieldset {
                        label(it.nameStringProperty) {
                            style {
                                fontSize = 16.pt
                            }
                        }
                        label(it.completedStringProperty) {
                            style {
                                fontSize = 8.pt
                            }
                        }
                        label(it.commentStringProperty) {
                            style {
                                fontSize = 12.pt
                                fontStyle = FontPosture.ITALIC
                                textFill = Color.DIMGRAY
                            }
                        }
                        label(it.deadlineDurationStringProperty) {
                            style {
                                fontStyle = FontPosture.ITALIC
                                fontSize = 10.pt
                                textFill = Color.DARKBLUE
                            }
                        }
                        label(it.deadlineStringProperty) {
                            style {
                                fontSize = 10.pt
                                fontStyle = FontPosture.ITALIC
                                textFill = Color.DARKBLUE
                            }
                        }
                    }
                }
            }
        }
    }

}



