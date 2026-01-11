package com.songwritingapp.lyrica.security;

import lombok.NoArgsConstructor;
import java.util.UUID;

@NoArgsConstructor
public final class CurrentUser {

    private static final UUID DEV_USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    public static UUID id() {
        return DEV_USER_ID;
    }
}