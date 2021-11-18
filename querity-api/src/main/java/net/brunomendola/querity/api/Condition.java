package net.brunomendola.querity.api;

public interface Condition {
  default boolean isEmpty() {
    return false;
  }
}
