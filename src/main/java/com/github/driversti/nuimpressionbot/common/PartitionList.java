package com.github.driversti.nuimpressionbot.common;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

// credits https://github.com/wololock/java-performance-benchmarks/blob/master/src/main/java/com/github/wololock/Partition.java
public final class PartitionList<T> extends AbstractList<List<T>> {

  private final List<T> list;
  private final int chunkSize;

  public PartitionList(List<T> list, int chunkSize) {
    this.list = new ArrayList<>(list);
    this.chunkSize = chunkSize;
  }

  @Override
  public List<T> get(int index) {
    int start = index * chunkSize;
    int end = Math.min(start + chunkSize, list.size());

    if (start > end) {
      throw new IndexOutOfBoundsException(
          "Index " + index + " is out of the list range <0," + (size() - 1) + ">");
    }

    return new ArrayList<>(list.subList(start, end));
  }

  @Override
  public int size() {
    return (int) Math.ceil((double) list.size() / (double) chunkSize);
  }

  public static <T> PartitionList<T> ofSize(List<T> list, int chunkSize) {
    return new PartitionList<>(list, chunkSize);
  }
}
