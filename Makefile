up:
	docker-compose --env-file=.env up --build
down:
	docker-compose down --volumes
