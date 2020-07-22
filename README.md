# Battleship Java

A simple game of Battleship, written in Java.

# Getting started

This project requires a Java JDK 8 or higher. To prepare to work with it, pick one of these
options:

## Run locally

Run battleship with Gradle

```bash
./gradlew run
```

Run battleship with Gradle in testing mode 1 (randomly generate user fleet)

```bash
./gradlew run --args="testing"
```


Run battleship with Gradle in testing mode 2 (randomly generate user fleet and show enemy fleet)

```bash
./gradlew run --args="testing2"
```

Run battleship with Gradle in testing mode 3 (randomly generate user fleet and enemy fleet and shoot all but last position: A5)

```bash
./gradlew run --args="testing3"
```

Execute tests with Gradle

```bash
./gradlew test
```

## Docker

If you don't want to install anything Java-related on your system, you can
run the game inside Docker instead.

### Run a Docker Container from the Image

```bash
docker run -it -v ${PWD}:/battleship -w /battleship java
```

# Launching the game

```bash
./gradlew run
```

# Running the Tests

```
./gradlew test
```
