set PGPASSWORD=123
cls
@echo off
C:/Progra~1/PostgreSQL/9.3/bin/psql -h 192.168.99.100 -p 32768 -U postgres -d postgres -c "CREATE TABLE estator(ID SERIAL PRIMARY KEY,TITLE TEXT,URL TEXT,ROOMS TEXT,FLOOR TEXT,AREA TEXT,PRICE TEXT)WITH (OIDS=FALSE);"


