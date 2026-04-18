#!/bin/bash
# cd ./output
# rm *.apk
# cd ..
./scripts/update_extensions.sh
./gradlew :app:clean
sh ./publish.sh
sh ./run.sh --clear