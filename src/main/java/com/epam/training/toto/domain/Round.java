package com.epam.training.toto.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Round {

    private int year;
    private int week;
    private int roundOfTheWeek;
    private LocalDate date;
    private List<Hit> hits;
    private List<Outcome> outcomes;

}
