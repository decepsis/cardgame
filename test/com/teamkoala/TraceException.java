package com.teamkoala;

/**
 * Simple unchecked exception for usage during testing.
 *
 * Prevents accidentally detecting errors from elsewhere as intentional tracing.
 *
 * @see TitleControllerTest#withParams(int, boolean)
 */
public class TraceException extends RuntimeException {}
