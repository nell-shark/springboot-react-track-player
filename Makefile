up:
	docker-compose --env-file=.env up --build
down:
	docker-compose down --volumes
mysql:
	docker run --rm --name db -p 3306:3306 \
  -e MYSQL_USER=nellshark \
  -e MYSQL_PASSWORD=root \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=player \
  mysql:8.0.32
