package com.epam.training.toto.service.parser;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoundParser implements Parser<Round> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
    @Autowired
    private Parser<Hit> hitParser;
    @Autowired
    private Parser<Outcome> outcomeParser;

    @Override
    public Round parse(final String... input) {
        return Round.builder()
                .year(Integer.parseInt(input[0]))
                .week(Integer.parseInt(input[1]))
                .roundOfTheWeek(parseWeekNumber(input[2]))
                .date(retrieveDateOfRound(input))
                .hits(getHits(input))
                .outcomes(getOutcomes(input))
                .build();
    }

    private int parseWeekNumber(final String input) {
        if (input.equals("-")) {
            return 1;
        } else {
            return Integer.parseInt(input);
        }
    }

    private LocalDate retrieveDateOfRound(final String[] input) {
        if (input[3].isEmpty()) {
            return LocalDate.ofYearDay(Integer.parseInt(input[0]), (Integer.parseInt(input[1]) - 1) * 7 + 1);
        } else {
            return LocalDate.parse(input[3], formatter);
        }
    }

    private List<Hit> getHits(final String[] input) {
        List<Hit> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Hit hit = hitParser.parse(input[4 + 2 * i], input[5 + 2 * i]);
            hit.setHitsCount(14 - i);
            result.add(hit);
        }
        return result;
    }

    private List<Outcome> getOutcomes(final String[] input) {
        List<Outcome> result = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            result.add(outcomeParser.parse(input[14 + i]));
        }
        return result;
    }

}
