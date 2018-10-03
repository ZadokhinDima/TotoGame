package com.epam.training.toto.service;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.epam.training.toto.domain.Round;
import com.epam.training.toto.service.parser.Parser;
import com.opencsv.CSVReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoundsLoader {

    private static final String FILE_WITH_ROUNDS = "/toto.csv";

    @Autowired
    private Parser<Round> roundParser;

    public Map<LocalDate, Round> loadTaskInfo() {
        try (CSVReader reader =
                     new CSVReader(new FileReader(RoundsLoader.class.getResource(FILE_WITH_ROUNDS).getFile()), ';')) {
            Map<LocalDate, Round> info = new HashMap<>();
            String[] line;
            while ((line = reader.readNext()) != null) {
                final Round round = roundParser.parse(line);
                info.put(round.getDate(), round);
            }
            return info;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
