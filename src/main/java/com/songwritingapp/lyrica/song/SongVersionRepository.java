package com.songwritingapp.lyrica.song;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface SongVersionRepository extends JpaRepository<SongVersion, UUID> {
    Optional<SongVersion> findByIdAndSongId(UUID id, UUID songId);
}
