package tech.flandia_yingm.ticker

import javafx.application.Platform
import tornadofx.App
import tornadofx.FX.Companion.messages
import tornadofx.UIComponent
import tornadofx.get
import java.util.*

class Ticker : App(TickerView::class) {

    init {
        Platform.setImplicitExit(false)
        messages = ResourceBundle.getBundle("Message")
    }

    override fun onBeforeShow(view: UIComponent) {
        view.title = messages["title"]
    }

}
