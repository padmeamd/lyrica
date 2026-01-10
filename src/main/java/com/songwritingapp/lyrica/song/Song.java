package com.songwritingapp.lyrica.song;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "song")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Song {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(nullable = false, length = 200)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SongStatus status = SongStatus.DRAFT;

    @Column(name = "current_version_id")
    private UUID currentVersionId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    public Song(UUID ownerId, String title) {
        this.id = UUID.randomUUID();
        this.ownerId = ownerId;
        this.title = title;
        this.status = SongStatus.DRAFT;
    }

    // --- domain methods (NOT generic setters) ---

    public void rename(String newTitle) {
        this.title = newTitle;
    }

    public void archive() {
        this.status = SongStatus.ARCHIVED;
    }

    public void activate() {
        this.status = SongStatus.ACTIVE;
    }

    public void setCurrentVersion(UUID versionId) {
        this.currentVersionId = versionId;
    }

    public void softDelete() {
        this.deletedAt = OffsetDateTime.now();
    }
}
