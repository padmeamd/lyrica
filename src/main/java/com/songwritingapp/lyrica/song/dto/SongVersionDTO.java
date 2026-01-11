package com.songwritingapp.lyrica.song.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record SongVersionDTO(
        UUID id,
        UUID songId,
        String label,
        String lyrics,
        String notes,
        OffsetDateTime createdAt
) {}
