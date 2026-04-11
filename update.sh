#!/bin/bash
cd ./output
rm *.apk
cd ..
./gradlew :app:clean
sh ./publish.sh
sh ./run.sh --clear