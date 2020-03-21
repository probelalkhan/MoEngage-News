package net.simplifiedcoding.moengagenews.data.network;

import java.io.IOException;

public class ApiException extends IOException {

    ApiException(String message) {
        super(message);
    }

}
