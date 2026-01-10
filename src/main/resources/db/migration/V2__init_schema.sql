-- Enable UUID generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Users
CREATE TABLE app_user (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          email VARCHAR(320) NOT NULL UNIQUE,
                          password_hash VARCHAR(255) NOT NULL,
                          created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Songs
CREATE TABLE song (
                      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                      owner_id UUID NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
                      title VARCHAR(200) NOT NULL,
                      status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
                      current_version_id UUID NULL,
                      created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                      updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                      deleted_at TIMESTAMPTZ NULL
);

CREATE INDEX idx_song_owner_id ON song(owner_id);
CREATE INDEX idx_song_status_owner ON song(owner_id, status);

-- Song versions (lyrics + notes live here)
CREATE TABLE song_version (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              song_id UUID NOT NULL REFERENCES song(id) ON DELETE CASCADE,
                              created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                              label VARCHAR(120) NULL,
                              lyrics TEXT NOT NULL DEFAULT '',
                              notes TEXT NOT NULL DEFAULT ''
);

CREATE INDEX idx_song_version_song_id ON song_version(song_id);

-- Set current_version_id FK after song_version exists
ALTER TABLE song
    ADD CONSTRAINT fk_song_current_version
        FOREIGN KEY (current_version_id)
            REFERENCES song_version(id)
            ON DELETE SET NULL;

-- Collections
CREATE TABLE collection (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            owner_id UUID NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
                            name VARCHAR(120) NOT NULL,
                            description TEXT NULL,
                            created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_collection_owner_id ON collection(owner_id);

-- Collection ↔ Song (many-to-many)
CREATE TABLE collection_song (
                                 collection_id UUID NOT NULL REFERENCES collection(id) ON DELETE CASCADE,
                                 song_id UUID NOT NULL REFERENCES song(id) ON DELETE CASCADE,
                                 added_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                                 PRIMARY KEY (collection_id, song_id)
);

CREATE INDEX idx_collection_song_song_id ON collection_song(song_id);