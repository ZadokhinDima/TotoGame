package com.epam.training.toto.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Hit {

    private int hitsCount;
    private int games;
    private int prize;
}
