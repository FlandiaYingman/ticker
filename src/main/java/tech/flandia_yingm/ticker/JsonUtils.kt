package tech.flandia_yingm.ticker

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import tech.flandia_yingm.ticker.task.Task
import java.io.File
import java.lang.reflect.Type

/**
 * @author Flandia Yingman
 * @version 1.0
 */
object JsonUtils {

    private inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    private val gson: Gson = GsonBuilder()
            .registerTypeAdapter(object : TypeToken<List<Task>>() {}.type, TaskListAdapter)
            .setPrettyPrinting()
            .create()


    private val tasksFile = File("./tasks.json")

    fun loadTasks(): List<Task> {
        if (!tasksFile.exists()) {
            saveTasks(listOf())
        }
        return gson.fromJson<List<Task>>(tasksFile.readText())
    }

    fun saveTasks(tasks: List<Task>) {
        tasksFile.writeText(gson.toJson(tasks, object : TypeToken<List<Task>>() {}.type))
    }


    object TaskListAdapter : JsonSerializer<List<Task>>, JsonDeserializer<List<Task>> {
        class TaskData(task: Task) {
            private val name = task.name
            private val comment = task.comment
            private val deadline = task.deadline
            private val completion = task.completion
            fun toTask(): Task = Task(name, comment, deadline, completion)
        }

        override fun serialize(src: List<Task>?, type: Type?, context: JsonSerializationContext?): JsonElement? {
            val taskDataList = src.orEmpty().map { TaskData(it) }
            return context?.serialize(taskDataList, object : TypeToken<List<TaskData>>() {}.type)
        }

        override fun deserialize(json: JsonElement?, type: Type?, context: JsonDeserializationContext?): List<Task> {
            val taskDataList = context?.deserialize<List<TaskData>>(json, object : TypeToken<List<TaskData>>() {}.type)
            return taskDataList.orEmpty().map { it.toTask() }
        }
    }

}