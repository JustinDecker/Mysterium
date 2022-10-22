#!/bin/bash
export PGPASSWORD='postgres1'
BASEDIR=$(dirname $0)
DATABASE=mysterium
"C:\Program Files\PostgreSQL\14\bin\psql.exe" -U postgres -f "$BASEDIR/dropdb.sql" &&
"C:\Program Files\PostgreSQL\14\bin\createdb.exe" -U postgres $DATABASE &&
"C:\Program Files\PostgreSQL\14\bin\psql.exe" -U postgres -d $DATABASE -f "$BASEDIR/schema.sql" &&
"C:\Program Files\PostgreSQL\14\bin\psql.exe" -U postgres -d $DATABASE -f "$BASEDIR/user.sql"
