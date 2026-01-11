package com.songwritingapp.lyrica.song.dto;

import com.songwritingapp.lyrica.song.SongStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record SongListItemDTO(
        UUID id,
        String title,
        SongStatus status,
        OffsetDateTime updatedAt
) {}