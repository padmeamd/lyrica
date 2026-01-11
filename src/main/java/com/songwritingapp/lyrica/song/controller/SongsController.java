package com.songwritingapp.lyrica.song.controller;


import com.songwritingapp.lyrica.security.CurrentUser;
import com.songwritingapp.lyrica.song.dto.CreateSongRequestDTO;
import com.songwritingapp.lyrica.song.dto.SongDetailDTO;
import com.songwritingapp.lyrica.song.dto.SongListItemDTO;
import com.songwritingapp.lyrica.song.dto.SongResponseDTO;
import com.songwritingapp.lyrica.song.service.SongQueryService;
import com.songwritingapp.lyrica.song.service.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongsController {
    private final SongService songService;
    private final SongQueryService songQueryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SongResponseDTO create(@Valid @RequestBody CreateSongRequestDTO request) {
        // DEV: пока нет auth, использую фиксированный ownerId
        UUID ownerId = CurrentUser.id();
        return songService.createSong(ownerId, request);
    }

    @GetMapping
    public List<SongListItemDTO> listSongs() {
        return songQueryService.listSongs(CurrentUser.id());
    }

    @GetMapping("/{id}")
    public SongDetailDTO getSong(@PathVariable UUID id) {
        return songQueryService.getSong(CurrentUser.id(), id);
    }
}