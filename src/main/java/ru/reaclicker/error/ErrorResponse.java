package ru.reaclicker.error;

import lombok.Getter;

/**
 * Created by Artur Belogur on 09.03.17.
 */
public class ErrorResponse {

    @Getter
    private String massage;

    @Getter
    private int status;

    public ErrorResponse(String massage, int status) {
        this.massage = massage;
        this.status = status;
    }
}
