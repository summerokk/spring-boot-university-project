FROM postgres:11.1
MAINTAINER summerokk@gmail.com
COPY 01-schema.sql /docker-entrypoint-initdb.d/
COPY 02-data.sql /docker-entrypoint-initdb.d/
