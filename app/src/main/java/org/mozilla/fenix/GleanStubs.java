package org.mozilla.fenix;
import java.util.UUID; import java.util.List; import java.util.Calendar; import mozilla.components.service.glean.BuildInfo;
public class GleanStubs {
    public static class MetricStub {
        public void record(Object e) {} public void record() {} public void add(int a) {} public void add(long a) {} public void add() {} public void set(Object v) {} public void set() {} public void submit(Object v) {} public void submit() {} public void accumulate(long s) {} public void accumulateSamples(List<Long> s) {} public void accumulateSamples() {} public Object testGetValue() { return null; } public UUID generateAndSet() { return UUID.randomUUID(); } public void cancel(Object s) {} public long start() { return 0; } public void stopAndAccumulate(Object s) {} public MetricStub get(Object k) { return this; }
    }
    public static class MapStub { public MetricStub get(Object k) { return new MetricStub(); } }
}
