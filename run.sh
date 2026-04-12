#!/bin/bash

# Exit on error
set -e
# --clear Flag: You can now wipe app data before testing by running ./test.sh --clear
# Configuration
ADB="/Users/parinkanthakamala/Library/Android/sdk/platform-tools/adb"
export JAVA_HOME="/Users/parinkanthakamala/Library/Java/JavaVirtualMachines/corretto-17.0.14/Contents/Home"
PACKAGE_NAME="org.mozilla.fenix.debug"
LAUNCH_ACTIVITY="org.mozilla.fenix.HomeActivity"
OUTPUT_DIR="output"
CLEAR_DATA=false

# Argument parsing
for arg in "$@"; do
    if [ "$arg" == "--clear" ]; then
        CLEAR_DATA=true
    fi
done

echo "==========================================="
echo "Fenix (Firefox for Android) Debug Test Script"
echo "==========================================="

# Check for connected devices
ALL_DEVICES=$($ADB devices | grep -v "List" | grep -v "^$" || true)
DEVICE_COUNT=$(echo "$ALL_DEVICES" | grep -w "device" | wc -l)

if [ "$DEVICE_COUNT" -eq 0 ]; then
    if [ ! -z "$ALL_DEVICES" ]; then
        echo "==========================================="
        echo "ERROR: Device(s) found but they are not ready."
        echo "-------------------------------------------"
        echo "$ALL_DEVICES"
        echo "-------------------------------------------"
        echo "Suggestions:"
        echo "1. Check your device for a 'Allow USB debugging?' prompt."
        echo "2. Try reconnecting the USB cable."
        echo "3. Run: $ADB kill-server && $ADB devices"
        echo "==========================================="
    else
        echo "ERROR: No devices connected. Please connect a device via USB or start an emulator."
    fi
    exit 1
fi

# Detect device ABI
ABI=$($ADB shell getprop ro.product.cpu.abi)
echo "Detected device ABI: $ABI"

# Select correct APK
APK_PATH="$OUTPUT_DIR/app-$ABI-debug.apk"
if [ ! -f "$APK_PATH" ]; then
    echo "WARNING: APK for $ABI not found in $OUTPUT_DIR."
    echo "Falling back to searching for any debug APK..."
    APK_PATH=$(find "$OUTPUT_DIR" -name "*-debug.apk" | head -n 1)
fi

if [ -z "$APK_PATH" ] || [ ! -f "$APK_PATH" ]; then
    echo "ERROR: No APK found in $OUTPUT_DIR. Please run ./publish.sh first."
    exit 1
fi

echo "Installing $APK_PATH..."
$ADB install -r -t "$APK_PATH"

if [ "$CLEAR_DATA" = true ]; then
    echo "Clearing application data (--clear)..."
    $ADB shell pm clear "$PACKAGE_NAME"
fi

echo "Launching application..."
$ADB shell am start -n "$PACKAGE_NAME/$LAUNCH_ACTIVITY"

echo "==========================================="
echo "Application launched! Streaming logs (logcat)..."
echo "Monitoring: $PACKAGE_NAME, StrictMode, Gecko, and App tags"
echo "Press Ctrl+C to stop."
echo "==========================================="

# Clear previous logs and start streaming with enhanced filters
$ADB logcat -c
$ADB logcat "$PACKAGE_NAME:V" "StrictMode:D" "Gecko:V" "App:V" "*:S"
