package com.songwritingapp.lyrica.song;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, UUID> {

    List<Song> findAllByOwnerIdAndDeletedAtIsNullOrder(UUID ownerId);

    Optional<Song> findByIdAndOwnerIdAndDeletedAtIsNull(UUID id, UUID ownerId);
}
