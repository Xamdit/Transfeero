package org.mozilla.fenix

class MetricStub {
    fun record(vararg args: Any?) {}
    fun set(value: Any? = null) {}
    fun add(value: Any? = null) {}
    fun accumulateSamples(value: Any? = null) {}
    fun accumulate(value: Any? = null) {}
    fun generateAndSet(): Any = "stub-uuid"
    fun stop(value: Any? = null) {}
    fun cancel(value: Any? = null) {}
    fun <T> start(value: Any? = null): T? = null
    fun stopAndAccumulate(value: Any? = null) {}
    fun submit(value: Any? = null) {}

    inline fun <T> measure(block: () -> T): T {
        return block()
    }

    operator fun get(key: String): MetricStub = this
}

class MapStub {
    fun set(key: String, value: String) {}
    fun record(vararg args: Any?) {}
    fun all(context: Any?): Any = this
    operator fun get(key: String): MetricStub = MetricStub()
    val isDefaultBrowser: Boolean = false
}
