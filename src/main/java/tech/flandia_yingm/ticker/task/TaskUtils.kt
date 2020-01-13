package tech.flandia_yingm.ticker.task

import javafx.application.Platform
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import org.joda.time.Period
import org.joda.time.format.PeriodFormatterBuilder
import java.lang.Thread.currentThread
import java.lang.Thread.sleep
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.concurrent.thread

object TaskUtils {

    fun Property<LocalDateTime>.asLocalDateProperty(): Property<LocalDate> {
        val dateTimeProperty = this
        val dateProperty = SimpleObjectProperty(dateTimeProperty.value.toLocalDate())
        dateTimeProperty.addListener { _, _, newVal ->
            dateProperty.value = newVal.toLocalDate()
        }
        dateProperty.addListener { _, _, newVal ->
            dateTimeProperty.value = LocalDateTime.of(newVal, dateTimeProperty.value.toLocalTime())
        }
        return dateProperty
    }

    fun Property<LocalDateTime>.asNowRelativePeriod(): Property<Duration> {
        val dateTimeProperty = this;
        val nowProperty = nowLocalDateTimeProperty
        val nowRelativeProperty = SimpleObjectProperty(Duration.between(dateTimeProperty.value, nowProperty.value))

        fun updateNowRelativeProperty() {
            nowRelativeProperty.value = Duration.between(dateTimeProperty.value, nowProperty.value)
        }

        dateTimeProperty.addListener { _, _, _ ->
            updateNowRelativeProperty()
        }
        nowProperty.addListener { _, _, _ ->
            updateNowRelativeProperty()
        }

        return nowRelativeProperty
    }


    private val nowLocalDateTimeProperty: Property<LocalDateTime>

    init {
        nowLocalDateTimeProperty = SimpleObjectProperty(LocalDateTime.now())
        thread {
            while (!currentThread().isInterrupted) {
                Platform.runLater { nowLocalDateTimeProperty.value = LocalDateTime.now() }
                sleep(100)
            }
        }
    }


    fun Duration.format(): String {
        val formatter = PeriodFormatterBuilder()
                .appendDays()
                .appendSuffix(" Days ")
                .appendHours()
                .appendSuffix("Hours")
                .appendMinutes()
                .appendSuffix("Minutes")
                .appendSeconds()
                .appendSuffix(" Seconds ")
                .toFormatter()
        return formatter.print(Period.millis(this.toMillis().toInt()))
    }

    fun <T> Property<T>.asStringProperty(converter: (T) -> String): StringProperty {
        val stringProp = SimpleStringProperty(converter(this.value))
        st
    }

}