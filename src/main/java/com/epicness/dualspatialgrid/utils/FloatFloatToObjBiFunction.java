package com.epicness.dualspatialgrid.utils;

/**
 * Represents an operation on a {@code float}-valued operand and a {@code float}-valued
 * operand that produces a {@code R}-valued result.
 * <br>
 * This is a functional interface whose functional method is {@link #apply(float, float)}.
 */
@FunctionalInterface
public interface FloatFloatToObjBiFunction<R> {
  /**
   * Applies this function to the given arguments.
   *
   * @param first the first function argument
   * @param second the second function argument
   * @return the function result
   */
  R apply(float first, float second);
}
