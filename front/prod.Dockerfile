FROM httpd:2.4.43-alpine

COPY ./httpd.conf /usr/local/apache2/conf/httpd.conf
