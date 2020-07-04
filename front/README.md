# Development guide #

1. Install dependencies
$ npm install

2. Start webpack dev server
$ npm start

3. Use (f.ex.) Apache to proxy requests:

<VirtualHost *:80>

	ServerName www.rpylkkanen.com
	ServerAlias rpylkkanen.com

    ...

	ProxyPass / http://127.0.0.1:8080/
	ProxyPassReverse / http://127.0.0.1:8080/

</VirtualHost>


# Other notes #

Static content is bundled from /src to /static.
Bundle files: $ npm run build
