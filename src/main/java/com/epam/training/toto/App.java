package com.epam.training.toto;

import java.time.LocalDate;
import java.util.Map;

import com.epam.training.toto.domain.Round;
import com.epam.training.toto.service.RoundsLoader;
import com.epam.training.toto.service.TotoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private TotoService totoService;

    @Autowired
    private RoundsLoader loader;


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @Override
    public void run(final String... args) throws Exception {
        final Map<LocalDate, Round> info = loader.loadTaskInfo();
        totoService.printMaxPrize(info.values());
        totoService.printDistributions(info.values());
        totoService.takeUserBet(info);
    }
}
