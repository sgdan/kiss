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

build-native:
	$(graalvm) native-image \
		-cp build/install/kiss/lib/kotlin-stdlib-1.3.60.jar:build/install/kiss/lib/kiss.jar:build/install/kiss/lib/antlr4-runtime-4.7.2.jar \
		-J"-XX:-UseJVMCIClassLoader" \
		--initialize-at-build-time --macro:truffle --allow-incomplete-classpath \
		com.github.sgdan.kiss.MainKt \
		build/kissnative

run-native:
	$(graalvm) build/kissnative

.PHONY: build
