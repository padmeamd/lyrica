package com.songwritingapp.lyrica.song.service;

import com.songwritingapp.lyrica.song.Song;
import com.songwritingapp.lyrica.song.SongRepository;
import com.songwritingapp.lyrica.song.SongVersion;
import com.songwritingapp.lyrica.song.SongVersionRepository;
import com.songwritingapp.lyrica.song.dto.CreateSongRequestDTO;
import com.songwritingapp.lyrica.song.dto.SongResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final SongVersionRepository songVersionRepository;

    @Transactional
    public SongResponseDTO createSong(UUID ownerId, CreateSongRequestDTO request) {

        Song song = new Song(ownerId, request.title());
        songRepository.save(song);
        SongVersion v1 = SongVersion.initial(song.getId());
        songVersionRepository.save(v1);
        song.setCurrentVersion(v1.getId());
        songRepository.save(song);// to persist currentVersionId
        songRepository.flush(); // force Hibernate to execute INSERT/UPDATE so @CreationTimestamp/@UpdateTimestamp get populated
        Song reloaded = songRepository.findById(song.getId())
                .orElseThrow();
        return new SongResponseDTO(
                reloaded.getId(),
                reloaded.getOwnerId(),
                reloaded.getTitle(),
                reloaded.getStatus(),
                reloaded.getCurrentVersionId(),
                reloaded.getCreatedAt(),
                reloaded.getUpdatedAt()
        );
    }
}
