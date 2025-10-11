package com.threego.algo.common.pagination;

public class PaginationUtils {

    public static int calcOffset(int page, int size) {
        return (page - 1) * size;
    }
}