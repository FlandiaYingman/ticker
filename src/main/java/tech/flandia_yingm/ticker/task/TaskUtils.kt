package tech.flandia_yingm.ticker.task

import javafx.application.Platform
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import org.apache.commons.lang3.time.DurationFormatUtils
import org.joda.time.DateTimeConstants.MILLIS_PER_DAY
import java.lang.Thread.currentThread
import java.lang.Thread.sleep
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.concurrent.thread

object TaskUtils {

    private val nowLocalDateTimeProperty: Property<LocalDateTime>

    init {
        nowLocalDateTimeProperty = SimpleObjectProperty(LocalDateTime.now())
        thread(isDaemon = true) {
            while (!currentThread().isInterrupted) {
                Platform.runLater { nowLocalDateTimeProperty.value = LocalDateTime.now() }
                sleep(100)
            }
        }
    }

    internal fun Property<LocalDateTime>.asLocalDateProperty(): Property<LocalDate> {
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

    internal fun Property<LocalDateTime>.asNowRelativePeriod(): Property<Duration> {
        val dateTimeProperty = this
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

    internal fun <T> Property<T>.asStringProperty(converter: (T) -> String): StringProperty {
        val stringProp = SimpleStringProperty(converter(this.value))
        this.addListener { _, _, _ ->
            stringProp.value = converter(this.value)
        }
        return stringProp
    }

    fun Duration.format(): String {
        val millis = kotlin.math.abs(toMillis())
        return if (millis > MILLIS_PER_DAY) {
            DurationFormatUtils.formatDuration(millis, "d 'Days' HH':'mm':'ss")
        } else {
            DurationFormatUtils.formatDuration(millis, "HH':'mm':'ss")
        }
    }

}