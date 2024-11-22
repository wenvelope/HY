package com.hys.hy.task.entities


enum class TaskImportance {
    UNIMPORTANT,
    ORDINARY,
    IMPORTANT
}

class TaskImportanceName(
    val names: List<String>
) {
    init {
        require(names.size == 3) { "TaskImportance names must contain exactly 3 elements" }
        names.indices.forEach { ix ->
            require(names[ix].isNotEmpty()) { "A TaskImportance name can not be empty" }
            for (ix2 in 0 until ix) {
                require(names[ix] != names[ix2]) {
                    "TaskImportance names must be unique, but '${names[ix]}' was repeated"
                }
            }
        }
    }


    public constructor(
        unImportance: String,
        ordinary: String,
        importance: String,
    ) : this(listOf(unImportance, ordinary, importance))


    public companion object {
        val CHINESE_TASK_IMPORTANCE_NAMES = TaskImportanceName(
            listOf("不重要", "一般", "重要")
        )
    }

    override fun equals(other: Any?): Boolean {
        return other is TaskImportanceName && other.names == names
    }

    override fun hashCode(): Int {
        return names.hashCode()
    }

    override fun toString(): String {
        return names.joinToString(
            separator = ",",
            prefix = "TaskImportanceName(",
            postfix = ")",
            transform = String::toString
        )
    }

}


