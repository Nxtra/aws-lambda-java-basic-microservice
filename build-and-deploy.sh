#!/bin/bash

set -e
./gradlew clean build
echo y | sam deploy