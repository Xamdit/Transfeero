#!/bin/bash
# push.sh - Install the appropriate APK from output/ to a connected device

OUTPUT_DIR="output"

# Check if adb is available, with a fallback for common Mac path
if ! command -v adb &> /dev/null; then
    if [ -f "$HOME/Library/Android/sdk/platform-tools/adb" ]; then
        export PATH="$PATH:$HOME/Library/Android/sdk/platform-tools"
    else
        echo "Error: adb command not found and not found in default Mac path. Please install Android Platform Tools."
        exit 1
    fi
fi

# Get list of connected devices
DEVICES=$(adb devices | grep -v "List" | grep "device$" | cut -f1)
DEVICE_COUNT=$(echo "$DEVICES" | grep -v "^$" | wc -l)

if [ "$DEVICE_COUNT" -eq 0 ]; then
    echo "Error: No devices connected via adb."
    exit 1
fi

# Use the first device
DEVICE=$(echo "$DEVICES" | head -n 1)
echo "Target device: $DEVICE"

# Detect device ABI
ABI=$(adb -s "$DEVICE" shell getprop ro.product.cpu.abi | tr -d '\r')
echo "Device ABI: $ABI"

# Map common ABIs to suffix
CASE_ABI=""
case "$ABI" in
    "arm64-v8a") CASE_ABI="arm64-v8a" ;;
    "armeabi-v7a") CASE_ABI="armeabi-v7a" ;;
    "x86") CASE_ABI="x86" ;;
    "x86_64") CASE_ABI="x86_64" ;;
    *) 
        echo "Unknown ABI: $ABI. Looking for any APK..."
        CASE_ABI=""
        ;;
esac

# Find matching APK in output directory
echo "Searching for APK in $OUTPUT_DIR..."
APK_FILE=""

if [ -n "$CASE_ABI" ]; then
    # Look for variant specific ABI APK
    APK_FILE=$(find "$OUTPUT_DIR" -name "*$CASE_ABI*.apk" -type f | head -n 1)
fi

# Fallback to any APK if none found for specific ABI
if [ -z "$APK_FILE" ]; then
    APK_FILE=$(find "$OUTPUT_DIR" -name "*.apk" -type f | head -n 1)
fi

if [ -z "$APK_FILE" ]; then
    echo "Error: No APK found in $OUTPUT_DIR. Please run publish.sh first."
    exit 1
fi

echo "Installing $(basename "$APK_FILE")..."
adb -s "$DEVICE" install -r "$APK_FILE"

if [ $? -eq 0 ]; then
    echo "Success! Launching application..."
    # The applicationId is defined as 'org.mozilla' in build.gradle
    adb -s "$DEVICE" shell am start -n org.mozilla/org.mozilla.fenix.IntentReceiverActivity
else
    echo "Error: Installation failed."
    exit 1
fi
