package com.songwritingapp.lyrica.song.service;


import com.songwritingapp.lyrica.song.Song;
import com.songwritingapp.lyrica.song.SongRepository;
import com.songwritingapp.lyrica.song.SongVersion;
import com.songwritingapp.lyrica.song.SongVersionRepository;
import com.songwritingapp.lyrica.song.dto.SongDetailDTO;
import com.songwritingapp.lyrica.song.dto.SongListItemDTO;
import com.songwritingapp.lyrica.song.dto.SongVersionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SongQueryService {

    private final SongRepository songRepository;
    private final SongVersionRepository songVersionRepository;

    @Transactional(readOnly = true)
    public List<SongListItemDTO> listSongs(UUID ownerId) {
        return songRepository.findAllByOwnerIdAndDeletedAtIsNullOrderByCreatedAtDesc(ownerId)
                .stream()
                .map(s -> new SongListItemDTO(s.getId(), s.getTitle(), s.getStatus(), s.getUpdatedAt()))
                .toList();
    }

    @Transactional(readOnly = true)
    public SongDetailDTO getSong(UUID ownerId, UUID songId) {
        Song song = songRepository.findByIdAndOwnerIdAndDeletedAtIsNull(songId, ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));
        SongVersionDTO current = null;
        if (song.getCurrentVersionId() != null) {
            SongVersion v = songVersionRepository.findByIdAndSongId(song.getCurrentVersionId(), song.getId())
                    .orElseThrow(() -> new IllegalStateException("Current version not found"));
            current = new SongVersionDTO(
                    v.getId(),
                    v.getSongId(),
                    v.getLabel(),
                    v.getLyrics(),
                    v.getNotes(),
                    v.getCreatedAt()
            );
        }

        return new SongDetailDTO(
                song.getId(),
                song.getOwnerId(),
                song.getTitle(),
                song.getStatus(),
                song.getCurrentVersionId(),
                song.getCreatedAt(),
                song.getUpdatedAt(),
                current
        );
    }
}