# Lyrica 🎶 - private songwriting workspace (local-first)

Lyrica is a personal web app for songwriters: a private workspace where you can store songs and drafts, organise them into collections, track lyric versions, and use AI as a creative assistant (not a replacement).

The focus is **human-first songwriting**:
- inspiration
- structure
- alternatives
- flow
- safe experimentation via version history

---

## Why this project

I’m building Lyrica to combine:
- backend engineering (Java / Spring Boot / clean API)
- music and songwriting (creative process, not “auto-generated songs”)
- privacy-first AI (local models via Ollama)

This is a learning-driven pet project, but also something I genuinely want to use.

---

## Features (planned)

### Core
- Store songs and drafts
- Collections (mood-based, album ideas, concepts)
- Lyric version history (snapshots)
- Notes per version

### Writing tools
- Rhyme finder (word + line)
- Smart rewrite / continue suggestions (AI-assisted)

### Product / UX (later)
- Full-text search
- Tags, moods, metadata
- Export (txt/markdown)
- Minimal UI (web)

---

## Tech stack

### Backend
- **Java 21**
- **Spring Boot** (REST API)
- **Spring Data JPA**
- **Flyway** (DB migrations)
- **Spring Security** (JWT later)

### Database
- **PostgreSQL 16**

### AI (local-first)
- **Ollama** (local LLM server)
- Local models (e.g. `llama3.1`) downloaded on demand

### Dev setup
- Docker Compose (Postgres + Ollama)
- Local-first development (no paid APIs)

---

## Architecture overview

### High-level components
- **API**: Spring Boot REST application
- **DB**: PostgreSQL
- **AI layer**: isolated integration with Ollama via HTTP (swappable later)

### Design principles
- Clean boundaries (controllers → services → repositories)
- “Local-first”: everything runs locally
- Privacy-first: lyrics never leave the machine
- AI is a **tool**, not the source of truth
- Versions are first-class (safe experimentation)

---

## Database model (Option B: versions-first)

Lyrica treats a song as a **container + history**, not a single text field.

### Tables
- `app_user` — account/owner of data
- `song` — song metadata (title/status/owner + pointer to current version)
- `song_version` — actual lyrics + notes (snapshots over time)
- `collection` — mood/album/concept grouping
- `collection_song` — many-to-many link between collections and songs
- `flyway_schema_history` — Flyway internal history table (do not edit)

### Why versions-first?
Because songwriting is iterative:
- you want to experiment safely
- compare drafts
- roll back
- preserve “in-between” ideas

**Lyrics live in `song_version`, not in `song`.**

`Song.current_version_id` points to the currently active version for fast reads.

---

## How the app works (conceptually)

### Creating a song
1. Create a `song` (metadata + owner)
2. Create the first `song_version` (empty lyrics + notes by default)
3. Set `song.current_version_id` to that version

### Editing lyrics
Instead of overwriting:
1. Create a new `song_version` with updated lyrics/notes
2. Switch `song.current_version_id` to the new version

This makes editing safe, trackable, and AI-friendly.

### Collections
Collections are flexible:
- a song can be in 0, 1, or many collections
- collections can represent mood, album, concept, era, etc.

---

## Local development

### Prerequisites
- Java 21
- Docker + Docker Compose
- Maven (or use the included Maven wrapper `./mvnw`)

### 1) Start infrastructure (Postgres + Ollama)
From the project root (where `docker-compose.yml` lives):

```bash
docker compose up -d
docker compose ps
```

### 2) (Optional) Download a local model for AI

If you plan to build/test AI endpoints later:

docker exec -it lyrica-ollama ollama pull llama3.1

### 3) Run the Spring Boot API

From the Spring Boot project folder:

./mvnw spring-boot:run

### 4) Health check
curl http://localhost:8080/api/health


### Expected:
```
{"status":"ok"}
```

### Migrations (Flyway)

Migrations live here:
```
src/main/resources/db/migration
```

Naming convention:
```
V<version>__<description>.sql
```

### Example:

```
V1__baseline.sql

V2__init_schema.sql
```

Flyway runs migrations automatically on application startup and tracks them in:

```
flyway_schema_history
```

Important rule: once a migration was applied, don’t edit it.
Create a new versioned migration instead.

## API (planned endpoints)
### Songs
```
POST /api/songs — create a song (creates first version)

GET /api/songs — list my songs

GET /api/songs/{id} — get song + current version

POST /api/songs/{id}/versions — create a new version

GET /api/songs/{id}/versions — list versions

PUT /api/songs/{id}/current-version — switch current version

DELETE /api/songs/{id} — soft delete
```

### Collections

```
POST /api/collections

GET /api/collections

POST /api/collections/{id}/songs/{songId}

DELETE /api/collections/{id}/songs/{songId}
```

### AI (later)
```
POST /api/ai/rewrite — rewrite selected lines

POST /api/ai/continue — continue from a line

POST /api/ai/rhyme — rhyme suggestions (local + curated)

```
### Security (planned)


Current stage: development-friendly configuration.

Next steps:

- JWT auth

- app_user integration

- ownership checks (cannot access other users’ songs)

- AI endpoints protected by auth

## Roadmap
## Phase 1 - foundation ✅

- Spring Boot project skeleton

- Docker Compose infra

- Flyway migrations

- Basic health endpoint

## Phase 2 - songs & versions (next)

- JPA entities + repositories

 - SongService (transactional create version)

- CRUD endpoints for songs/versions

- Soft delete

## Phase 3 - collections

- Many-to-many linking

 - List/filter by collection

- Phase 4 - AI layer

- Ollama client module

- prompts for rewrite/continue

- guardrails: always create a new version from AI output

## Phase 5 - UI

- minimal web UI for writing

- version timeline

- collections browsing