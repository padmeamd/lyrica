package com.songwritingapp.lyrica.song.dto;

import com.songwritingapp.lyrica.song.SongStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record SongResponseDTO(
        UUID id,
        UUID ownerId,
        String title,
        SongStatus status,
        UUID currentVersionId,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}