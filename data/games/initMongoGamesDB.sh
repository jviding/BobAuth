#!/bin/bash
set -e;

echo "*******************************"
echo "* Running initMongoGamesDB.sh *"
echo "*******************************"

[ ! -f "${MONGO_NON_ROOT_USERNAME_FILE}" ] && echo "File not found MONGO_NON_ROOT_USERNAME_FILE!" && exit 1
[ ! -f "${MONGO_NON_ROOT_PASSWORD_FILE}" ] && echo "File not found MONGO_NON_ROOT_PASSWORD_FILE!" && exit 1

MONGO_NON_ROOT_USERNAME=$(cat "${MONGO_NON_ROOT_USERNAME_FILE}")
MONGO_NON_ROOT_PASSWORD=$(cat "${MONGO_NON_ROOT_PASSWORD_FILE}")

[ -z "$MONGO_NON_ROOT_USERNAME" ] && echo "Empty file MONGO_NON_ROOT_USERNAME_FILE!" && exit 1
[ -z "$MONGO_NON_ROOT_PASSWORD" ] && echo "Empty file MONGO_NON_ROOT_PASSWORD_FILE!" && exit 1

[ -z "${MONGO_NON_ROOT_ROLE:-}" ] && echo "MONGO_NON_ROOT_ROLE not set, using 'readWrite'."
MONGO_NON_ROOT_ROLE="${MONGO_NON_ROOT_ROLE:-readWrite}"

[ -z "${MONGO_INITDB_DATABASE}" ] && echo "Value missing MONGO_INITDB_DATABASE!" && exit 1

"${mongo[@]}" "${MONGO_INITDB_DATABASE}" <<-EOJS
	db.createUser({
		user: "$MONGO_NON_ROOT_USERNAME",
		pwd: "$MONGO_NON_ROOT_PASSWORD",
		roles: [ {
            role: "$MONGO_NON_ROOT_ROLE",
            db: "$MONGO_INITDB_DATABASE"
        } ]
	})
EOJS
