FROM python:3.8.3-alpine3.12 as base

FROM base as builder

RUN mkdir /install && \
    apk update && \
    apk add postgresql-dev gcc python3-dev musl-dev
WORKDIR /install
COPY requirements.txt /requirements.txt
RUN pip install --install-option="--prefix=/install" -r /requirements.txt

FROM base

COPY --from=builder /install /usr/local
RUN apk --no-cache add libpq

WORKDIR /iam
