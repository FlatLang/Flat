# Event-Stream Agent Guidance

Follow the repository-root `AGENTS.md`. This file adds rules for dataflow infrastructure.

## Responsibility

This package provides foundational event/dataflow behavior used by the compiler. Preserve compatibility with existing `EventStream` while introducing stronger primitives deliberately.

## Required design properties

- New compiler-facing APIs must be typed and bounded.
- Distinguish work channels, where one consumer owns an item, from broadcast topics, where multiple observers receive an event.
- Producers must be able to observe and await downstream backpressure.
- Completion, failure, cancellation, detachment, and graceful draining are first-class lifecycle states.
- Define ordering, replay, delivery, consumer, and ownership contracts in the API—not only in implementation comments.
- Do not use implicit unbounded event history. Replay must be explicit and bounded.
- Avoid stringly typed lifecycle protocols in new APIs.
- Concurrent operators must declare maximum in-flight work and whether output ordering is preserved.
- Ordered operators must bound or spill their reorder buffers.
- Cancellation must release pending futures, listeners, queues, and upstream resources.

## Testing

Use controlled gates/executors rather than sleeps. Cover:

- No lost or duplicate accepted items.
- Subscription/startup boundaries.
- Capacity enforcement and producer backpressure.
- Slow and failed consumers.
- Completion and failure propagation.
- Cancellation and resource cleanup.
- Ordered and unordered concurrent mapping.
- Bounded in-flight work and bounded reorder storage.
- Determinism under reversed and randomized schedules.

Benchmark allocation, throughput, and queue behavior for hot-path changes. Do not optimize by weakening lifecycle or delivery guarantees.
