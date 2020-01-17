package tech.flandia_yingm.ticker.task

import javafx.scene.paint.Color
import javafx.scene.text.FontPosture
import tornadofx.*

/**
 * @author Flandia Yingman
 * @version 1.0
 */
class TaskCell(task: Task) : Form() {

    init {
        fieldset {
            label(task.nameStringProperty) {
                style {
                    fontSize = 16.pt
                }
            }
            label(task.completionStringProperty) {
                style {
                    fontSize = 8.pt
                }
            }
            label(task.commentStringProperty) {
                style {
                    fontSize = 12.pt
                    fontStyle = FontPosture.ITALIC
                    textFill = Color.DIMGRAY
                }
            }
            label(task.deadlineDurationStringProperty) {
                style {
                    fontStyle = FontPosture.ITALIC
                    fontSize = 10.pt
                    textFill = Color.DARKBLUE
                }
            }
            label(task.deadlineStringProperty) {
                style {
                    fontSize = 10.pt
                    fontStyle = FontPosture.ITALIC
                    textFill = Color.DARKBLUE
                }
            }
        }
    }

}



