package com.epam.training.toto.service.parser;

public interface Parser<T> {
    T parse(String... input);
}
