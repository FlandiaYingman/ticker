package tech.flandia_yingm.ticker.util

import javafx.beans.property.Property
import javafx.beans.property.ReadOnlyStringProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty

/**
 * @author Flandia Yingman
 * @version 1.0
 */
object PropertyUtils {

    fun <T> Property<T>.asStringProperty(converter: (T) -> String): ReadOnlyStringProperty {
        val stringProp = SimpleStringProperty(converter(this.value))
        this.addListener { _, _, _ ->
            stringProp.value = converter(this.value)
        }
        return stringProp
    }

    fun <T> Property<T>.asBiStringProperty(toString: (T) -> String, toT: (String) -> T?): StringProperty {
        val stringProp = SimpleStringProperty(toString(this.value))
        this.addListener { _ ->
            stringProp.value = toString(this.value)
        }
        stringProp.addListener { _ ->
            this.value = toT(stringProp.value) ?: this.value
        }
        return stringProp

    }

}