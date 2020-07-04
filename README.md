# Design #

API/admin:
    - Java Spring Boot
    - admin.rpylkkanen.com
    - public

API/games:
    - Scala
    - games.rpylkkanen.com
    - public

API/iam:
    - Python (Django)
    - www.rpylkkanen.com
    - private

data/games:
    - MongoDB

data/iam:
    - PostgreSQL

front:
    - React & Sass

# Quick start #

$ docker-compose -f docker-compose.dev.yml build
$ docker-compose -f docker-compose.dev.yml up

Postgre has unresolved issues with permissions.
Quick workaround before build:
$ chmod 777 -R data/iam/db/

Set up forwarding with f.ex. Apache on the host.
This will make the cluster available.

1. Enable required Apache modules:
$ sudo a2enmod proxy
$ sudo a2enmod proxy_http
$ sudo a2enmod proxy_balancer
$ sudo a2enmod lbmethod_byrequests

2. Restart Apache to activate modules:
$ sudo systemctl restart apache2

3. Set up forwarding:

<VirtualHost *:80>

	ServerName www.rpylkkanen.com
	ServerAlias rpylkkanen.com

	ProxyPass /api http://127.0.0.1:8000/api
	ProxyPassReverse /api http://127.0.0.1:8000/api

	ProxyPass / http://127.0.0.1:8080/
	ProxyPassReverse / http://127.0.0.1:8080/

</VirtualHost>
