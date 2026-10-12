package com.pt.PerformanceTracker.common.sequence;

public interface SequenceGenerator {
   Long getNextValue(SequenceType sequenceType);
}
