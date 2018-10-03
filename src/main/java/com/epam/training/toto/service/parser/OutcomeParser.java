package com.epam.training.toto.service.parser;

import com.epam.training.toto.domain.Outcome;

import org.springframework.stereotype.Service;

@Service
public class OutcomeParser implements Parser<Outcome> {

    @Override
    public Outcome parse(final String... input) {
        String value = input[0];
        if (value.endsWith("1")) {
            return Outcome.FIRST;
        } else if (value.endsWith("2")) {
            return Outcome.SECOND;
        } else {
            return Outcome.DRAW;
        }
    }

}
