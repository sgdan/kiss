graalvm=@docker-compose run --rm graalvm

.build: graalvm/Dockerfile
	@docker-compose build graalvm
	touch .build

shell: .build
	$(graalvm) bash

build:
	$(graalvm) gradle build installDist

run:
	$(graalvm) ./build/install/kiss/bin/kiss

.PHONY: build
