build:
	docker buildx build --platform linux/amd64 -t faissnama:latest .

run:
	docker container run faissnama:latest