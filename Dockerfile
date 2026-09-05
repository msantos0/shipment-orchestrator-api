FROM ubuntu:latest
LABEL authors="Marcio"

ENTRYPOINT ["top", "-b"]