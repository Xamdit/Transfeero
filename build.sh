#!/bin/bash
set -e

# Project root directory
PROJECT_ROOT="/Users/parinkanthakamala/Documents/workspace/transfeero"
OUTPUT_DIR="$PROJECT_ROOT/output"

echo "==========================================="
echo "Fenix (Firefox for Android) Build Script"
echo "==========================================="

# Step 1: Clean output directory
echo "Cleaning output directory: $OUTPUT_DIR"
if [ -d "$OUTPUT_DIR" ]; then
    rm -f "$OUTPUT_DIR"/*.apk
else
    mkdir -p "$OUTPUT_DIR"
fi

# Step 2: Set Java 11
export JAVA_HOME=$(/usr/libexec/java_home -v 11)
echo "Using JAVA_HOME: $JAVA_HOME"

# Step 3: Run Build
echo "Starting build (Debug variant)..."
# แนะนำให้ใช้ 4mb (4m) สำหรับโปรเจกต์ใหญ่ และเพิ่ม Heap เป็น 8g ถ้าเครื่องไหว
export GRADLE_OPTS="-Xmx8g -Xss32m"
# ./gradlew assembleDebug -PversionName="1.0.0" -PversionCode=1 --no-daemon
./gradlew assembleDebug -PversionName="1.0.0" -PversionCode=1 --stacktrace --info

# Step 4: Copy results to output
echo "Copying APKs to $OUTPUT_DIR..."
find app/build/outputs/apk -name "*.apk" -exec cp {} "$OUTPUT_DIR/" \;


echo "Build finished."
ls -lh "$OUTPUT_DIR"
