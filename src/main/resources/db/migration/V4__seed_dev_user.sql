INSERT INTO app_user (id, email, password_hash)
VALUES (
           '00000000-0000-0000-0000-000000000001',
           'dev@lyrica.local',
           'DEV_ONLY__NO_AUTH_YET'
       )
    ON CONFLICT (id) DO NOTHING;
