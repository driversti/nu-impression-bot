package com.github.driversti.nuimpressionbot.common;

import java.util.ArrayList;
import java.util.List;

public class PagedList<T> {

  private final List<T> content;
  private final int pageSize;

  public PagedList(List<T> content, int pageSize) {
    this.content = content;
    this.pageSize = pageSize;
  }

  public List<T> getPage(int page) {
    if (page < 1) {
      throw new IllegalArgumentException("Page cannot be less then 1");
    }
    if (page > pages()) {
      throw new IllegalArgumentException(
          "Given page number \"" + page + "\" is bigger then total number of pages: " + pages());
    }
    if (content.isEmpty()) {
      return List.of();
    }
    int start = (page - 1) * pageSize;
    int end = Math.min(start + pageSize, content.size());

    return new ArrayList<>(content.subList(start, end));
  }

  public int pages() {
    int fullPages = content.size() / pageSize;
    int partialPage = content.size() % pageSize > 0 ? 1 : 0;
    return fullPages + partialPage;
  }

  public static <T> PagedList<T> ofSize(List<T> content, int pageSize) {
    return new PagedList<>(content, pageSize);
  }
}
