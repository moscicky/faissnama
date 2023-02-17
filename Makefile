build:
	docker buildx build --platform linux/amd64 -t faissnama:latest .

run:
	docker container run \
 	--mount type=bind,source="$(shell pwd)"/src,target=/faiss/jextract-19/bin/src \
	-it --entrypoint /bin/bash \
	faissnama:latest