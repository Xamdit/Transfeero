package org.mozilla.fenix
import java.util.UUID

class MetricStub {
    fun record(extras: Any? = null) {}
    fun record() {}
    fun add(amount: Int = 1) {}
    fun add(amount: Long) {}
    fun add() {}
    fun set(value: Any? = null) {}
    fun set() {}
    fun submit(value: Any? = null) {}
    fun submit() {}
    fun accumulate(samples: Long) {}
    fun accumulateSamples(samples: List<Long>? = null) {}
    fun testGetValue(): Any? = null
    fun generateAndSet(): UUID = UUID.randomUUID()
    fun cancel(samples: Any? = null) {}
    fun start(): Long = 0L
    fun stopAndAccumulate(samples: Any? = null) {}
    operator fun get(key: Any?): MetricStub = this
}

class MapStub {
    operator fun get(key: Any?): MetricStub = MetricStub()
}
