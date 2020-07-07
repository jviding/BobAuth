# Development #

1. Run with docker-compose:
$ docker-compose -f docker-compose.dev.yml up

2. The service is run inside a Docker container with command:
$ python3 -u manage.py runserver 0.0.0.0:8000

3. The service listens in port 8000:
-> http://localhost:8000
