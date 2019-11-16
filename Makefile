graal=@docker-compose run --rm graalvm

build:
	$(graal) ./gradlew clean build

shell:
	docker-compose run graalvm bash

.PHONY: build
