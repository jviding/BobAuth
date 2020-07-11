# Development #

See: https://www.playframework.com/documentation/2.8.x/PlayConsole

1. Run docker-compose to launch project
    $ docker-compose -f docker-compose up

2. Open shell in container
    $ docker exec -it bobauth_api-games_1 /bin/bash

3. Start sbt in interactive mode (to enable live-reloading)
    [container] $ sbt

4. Run
    [container] [games] $ run


# Security #

No validation on userID, gameName, gameStatus.
Validation should be performed on a higher level.
Thus, the API/games endpoints should be private and not exposed to public.

# Secrets #

Secrets are set to environment variables when container is started.
Current secrets: MONGO_CONNECTION_STRING.

Source code contains no secrets, and should never do so!
