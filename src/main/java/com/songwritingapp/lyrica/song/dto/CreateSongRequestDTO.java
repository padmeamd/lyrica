package com.songwritingapp.lyrica.song.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSongRequestDTO(
        @NotBlank @Size(max = 200)
        String title
) {}