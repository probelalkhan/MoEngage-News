package net.simplifiedcoding.moengagenews.data.network;

import java.io.IOException;

public class ApiException extends IOException {
    public ApiException(String message) {
        super(message);
    }
}
