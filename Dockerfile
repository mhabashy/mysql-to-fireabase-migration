# Derived from official mysql image (our base image)
FROM mysql:5.7
# Add a database
ENV MYSQL_DATABASE tempdb
# Add the content of the sql-scripts/ directory to your image
# All scripts in docker-entrypoint-initdb.d/ are automatically
# executed during container startup
COPY ./db/ /docker-entrypoint-initdb.d/

# docker run -d -p 3306:3306 --name db -e MYSQL_ROOT_PASSWORD=password db
