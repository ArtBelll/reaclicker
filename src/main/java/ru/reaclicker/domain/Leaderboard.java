package ru.reaclicker.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Artur Belogur on 19.03.17.
 */
public class Leaderboard {

    @Getter
    @Setter
    private double score;

    @Getter
    @Setter
    private long id;
}
