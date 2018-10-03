package com.epam.training.toto.service.parser;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import com.epam.training.toto.domain.Hit;

import org.springframework.stereotype.Service;

@Service
public class HitParser implements Parser<Hit> {

    private DecimalFormat format;

    @Override
    public Hit parse(final String... input) {
        return Hit.builder()
                .games(Integer.parseInt(input[0]))
                .prize((format.parse(input[1], new ParsePosition(0))).intValue())
                .build();
    }

    @PostConstruct
    private void initFormatter() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        format = new DecimalFormat("###,### UAH", symbols);
    }
}
