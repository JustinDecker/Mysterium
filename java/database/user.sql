-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER mysterium_owner
WITH PASSWORD 'finalcapstone';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO mysterium_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO mysterium_owner;

CREATE USER mysterium_appuser
WITH PASSWORD 'finalcapstone';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO mysterium_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO mysterium_appuser;
