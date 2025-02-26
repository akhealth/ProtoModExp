FROM  docker.io/library/postgres:15

#TODO, generate test data. This file needed to init db tables.
COPY *.sql /docker-entrypoint-initdb.d/

EXPOSE 5432