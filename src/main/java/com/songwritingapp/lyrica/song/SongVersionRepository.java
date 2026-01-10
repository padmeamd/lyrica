package com.songwritingapp.lyrica.song;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SongVersionRepository extends JpaRepository<SongVersion, UUID> {
    List<SongVersion> findAllBySongId(UUID songId);
}
