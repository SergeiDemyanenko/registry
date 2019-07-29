#!/bin/bash

sudo isql -input ddl.sql
mkdir -p ../../target/db
mv db.fdb ../../target/db/db.fdb
sudo chown -R firebird ../../target/db/db.fdb