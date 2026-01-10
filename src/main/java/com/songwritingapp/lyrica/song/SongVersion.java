package com.songwritingapp.lyrica.song;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "song_version")
@Getter
@NoArgsConstructor
public class SongVersion {

    @Id
    @Column(nullable = false,updatable = false)
    private UUID id;

    // FK to song(id). Keeping it as UUID for now to avoid bi-directional JPA complexity (add @ManyToOne ltr)
    @Column(name = "song_id", nullable = false,updatable = false)
    private UUID songId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "label", length = 120)
    private String label;

    @Column(name = "lyrics", nullable = false, columnDefinition = "text")
    private String lyrics = "";

    @Column(name = "notes", nullable = false, columnDefinition = "text")
    private String notes = "";


    public SongVersion(UUID songId, String label, String lyrics, String notes) {
        this.id = UUID.randomUUID();
        this.songId = songId;
        this.label = label;
        this.lyrics = (lyrics == null) ? "" : lyrics;
        this.notes = (notes  == null) ? "" : notes;
    }

    public static SongVersion initial( UUID songId){
        return new SongVersion(songId, "Initial", "","");
    }
}
