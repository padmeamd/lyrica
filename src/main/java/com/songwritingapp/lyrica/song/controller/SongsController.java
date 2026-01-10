package com.songwritingapp.lyrica.song.controller;


import com.songwritingapp.lyrica.song.dto.CreateSongRequestDTO;
import com.songwritingapp.lyrica.song.dto.SongResponseDTO;
import com.songwritingapp.lyrica.song.service.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor

public class SongsController {
    private final SongService songService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SongResponseDTO create(@Valid @RequestBody CreateSongRequestDTO request) {
        // DEV: пока нет auth, использую фиксированный ownerId
        UUID ownerId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        return songService.createSong(ownerId, request);
    }
}