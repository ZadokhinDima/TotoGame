package com.epam.training.toto.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;
import com.epam.training.toto.service.parser.Parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TotoService {

    @Autowired
    private Parser<Outcome> outcomeParser;

    private static final int GAMES_COUNT = 14;

    private final DecimalFormat prizeFormat = new DecimalFormat("###,### UAH");
    private final DecimalFormat distributionFormat = new DecimalFormat("#.##%");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");

    private final Scanner scanner = new Scanner(System.in);

    public void printMaxPrize(Collection<Round> allRounds) {
        int maxPrize = allRounds.stream()
                .flatMap(round -> round.getHits().stream())
                .mapToInt(Hit::getPrize).max().getAsInt();
        System.out.println("Largest prize: " + prizeFormat.format(maxPrize));
    }

    public void printDistributions(Collection<Round> allRounds) {
        allRounds.forEach(this::printRoundDistribution);
    }

    public void takeUserBet(Map<LocalDate, Round> info) {
        System.out.print("Enter date: ");
        final LocalDate date = LocalDate.parse(scanner.next(), dateFormatter);
        final Round round = info.get(date);
        System.out.print("Enter outcomes (1/2/X): ");
        final String outcomes = scanner.next();
        final List<Outcome> userBet = readUserBet(outcomes);
        final Hit userResult = calculatePrize(round, userBet);
        System.out.println("Result: hits: " + userResult.getHitsCount() + ", amount: " + prizeFormat.format(userResult.getPrize() ));
    }

    private void printRoundDistribution(Round round) {
        final Map<Outcome, Integer> outcomes = groupOutcomes(round);
        outcomes.forEach((outcome, count) ->
                System.out.print(outcome + ": " + distributionFormat.format(count / (double) GAMES_COUNT) + " "));
        System.out.println();
    }

    private Map<Outcome, Integer> groupOutcomes(Round round) {
        Map<Outcome, Integer> result = new HashMap<>();
        for (Outcome outcome : round.getOutcomes()) {
            final Integer count = result.getOrDefault(outcome, 0);
            result.put(outcome, count + 1);
        }
        return result;
    }

    private List<Outcome> readUserBet(String input) {
        List<Outcome> result = new ArrayList<>();
        for (char c : input.toCharArray()) {
            char arr[] = new char[1];
            arr[0] = c;
            result.add(outcomeParser.parse(new String(arr)));
        }
        return result;
    }

    private Hit calculatePrize(Round round, List<Outcome> userBet) {
        int correctAnswers = 0;
        for (int i = 0; i < GAMES_COUNT; i++) {
            if (round.getOutcomes().get(i) == userBet.get(i)) {
                correctAnswers++;
            }
        }
        final int finalCorrectAnswers = correctAnswers;
        return round.getHits().stream().filter(hit -> hit.getHitsCount() == finalCorrectAnswers).findFirst()
                .orElse(Hit.builder().games(finalCorrectAnswers).prize(0).build());
    }
}
