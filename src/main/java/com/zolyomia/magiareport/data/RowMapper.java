package com.zolyomia.magiareport.data;

public interface RowMapper<T, S> {
    T mapRow(S value);
}
