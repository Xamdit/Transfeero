#!/bin/bash

# Exit on error
set -e

VARIANT=${1:-"debug"}
OUTPUT_DIR="output"

# Try to find a compatible JAVA_HOME (11 or 17) if current one is too new
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [[ "$JAVA_VERSION" -gt "20" ]]; then
    echo "Current Java version ($JAVA_VERSION) is likely too new for this project's Gradle version."
    if [ -x "/usr/libexec/java_home" ]; then
        for v in 17 11; do
            TARGET_JAVA_HOME=$(/usr/libexec/java_home -v "$v" 2>/dev/null || true)
            if [ -n "$TARGET_JAVA_HOME" ]; then
                export JAVA_HOME="$TARGET_JAVA_HOME"
                echo "Switched to JAVA_HOME: $JAVA_HOME (Java $v)"
                break
            fi
        done
    fi
fi

echo "==========================================="
echo "Fenix (Firefox for Android) Publish Script"
echo "Variant: $VARIANT"
echo "==========================================="

# Clean previous builds
echo "Cleaning up..."
# Remove any leftover temporary scripts
rm -f *.py
./gradlew clean

# Build the project
echo "Building $VARIANT..."
export GRADLE_OPTS="-Xss16m"
GRADLE_ARGS="-x lint -x test"

if [[ "$VARIANT" == "debug" ]]; then
    ./gradlew app:assembleDebug $GRADLE_ARGS
elif [[ "$VARIANT" == "nightly" ]]; then
    ./gradlew app:assembleNightly $GRADLE_ARGS
elif [[ "$VARIANT" == "beta" ]]; then
    ./gradlew app:assembleBeta $GRADLE_ARGS
elif [[ "$VARIANT" == "release" ]]; then
    ./gradlew app:assembleRelease $GRADLE_ARGS
else
    echo "Unknown variant: $VARIANT. Attempting to run assemble$VARIANT..."
    ./gradlew "app:assemble$VARIANT" $GRADLE_ARGS
fi

# Create output directory
mkdir -p "$OUTPUT_DIR"
rm -f "$OUTPUT_DIR"/*.apk

# Find and collect APKs
echo "Collecting APKs..."
APK_FOUND=false
# Search in common gradle output locations
find app/build/outputs/apk -name "*.apk" -type f | while read -r apk; do
    filename=$(basename "$apk")
    cp "$apk" "$OUTPUT_DIR/$filename"
    echo "  Copied: $filename"
    APK_FOUND=true
done

if [ "$(ls -A $OUTPUT_DIR/*.apk 2>/dev/null)" ]; then
    echo "==========================================="
    echo "SUCCESS: APKs are ready in the '$OUTPUT_DIR' directory."
    ls -lh "$OUTPUT_DIR"/*.apk
    echo "==========================================="
else
    echo "==========================================="
    echo "ERROR: No APKs were found after the build."
    echo "==========================================="
    exit 1
fi
