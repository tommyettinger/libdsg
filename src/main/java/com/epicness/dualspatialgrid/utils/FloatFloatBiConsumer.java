package com.epicness.dualspatialgrid.utils;

/**
 * Represents an operation that accepts
 * two input arguments and returns no result.
 * <br>
 * This is a functional interface
 * whose functional method is {@link #accept(float, float)}.
 */
@FunctionalInterface
public interface FloatFloatBiConsumer {
  /**
   * Performs this operation on the given arguments.
   *
   * @param first the first input argument
   * @param second the second input argument
   */
  void accept(float first, float second);
}
