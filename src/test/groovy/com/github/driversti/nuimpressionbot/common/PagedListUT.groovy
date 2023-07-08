package com.github.driversti.nuimpressionbot.common

import com.github.driversti.nuimpressionbot.UnitTest
import java.util.stream.IntStream

class PagedListUT extends UnitTest {

    def "should return the expected elements of the list based on the provided page number"() {
        given:
        def pagedList = PagedList.ofSize(givenListOfIntegers, pageSize)

        expect:
        pagedList.getPage(pageNumber) == expectedListOfIntegers

        where:
        givenListOfIntegers | pageSize | pageNumber || expectedListOfIntegers
        listOfSize(1)       | 3        | 1          || listOfSize(1)
        listOfSize(3)       | 3        | 1          || listOfSize(3)
        listOfSize(10)      | 3        | 2          || rangeClosed(4, 6)
    }

    def "should return number of pages"() {
        given:
        def pagedList = PagedList.ofSize(givenContent, pageSize)

        expect:
        pagedList.pages() == expectedNumberOfPages

        where:
        givenContent   | pageSize || expectedNumberOfPages
        listOfSize(0)  | 5        || 0
        listOfSize(1)  | 5        || 1
        listOfSize(6)  | 5        || 2
        listOfSize(20) | 5        || 4
    }

    private static List<Integer> listOfSize(int size) {
        IntStream.iterate(1, i -> ++i).limit(size).boxed().collect()
    }

    private static List<Integer> rangeClosed(int start, int end) {
        IntStream.rangeClosed(start, end).boxed().collect()
    }
}
