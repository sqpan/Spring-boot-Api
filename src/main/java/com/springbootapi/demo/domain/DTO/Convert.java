package com.springbootapi.demo.domain.DTO;

public interface Convert<S, T> {

    T convert(S s, T t);

    T convert(S s);
}
