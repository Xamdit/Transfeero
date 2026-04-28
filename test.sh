#!/bin/bash
# ============================================
# test.sh - Debug Setup & Push APK to Connected Device
# ============================================

set -e

# Run build first
./publish.sh debug

# Step 1: Find connected device
echo "Searching for connected devices..."
DEVICES=$(adb devices | grep -v "List of devices" | grep "device$" | awk '{print $1}')
COUNT=$(echo "$DEVICES" | grep -v '^$' | wc -l | tr -d ' ')

if [ "$COUNT" -eq 0 ]; then
    echo "Error: No device connected via USB or WiFi."
    echo "Please connect a device or start an emulator first."
    exit 1
fi

# Use first device by default, or the one provided as argument
if [ ! -z "$1" ]; then
    TARGET="$1"
else
    TARGET=$(echo "$DEVICES" | head -1)
fi

echo "============================================"
echo " Transfeero Debug Script"
echo " Target Device: ${TARGET}"
echo "============================================"

# Step 2: Detect device ABI to pick the right APK
echo ""
echo "[1/3] Detecting device architecture..."
DEVICE_ABI=$(adb -s ${TARGET} shell getprop ro.product.cpu.abi | tr -d '\r')
echo "      Device ABI: ${DEVICE_ABI}"

OUTPUT_DIR="./output"
APK_FILE=""

# Map ABI to APK filename pattern
case $DEVICE_ABI in
    "arm64-v8a") APK_PATTERN="*arm64*debug*.apk" ;;
    "armeabi-v7a") APK_PATTERN="*armeabi*debug*.apk" ;;
    "x86_64") APK_PATTERN="*x86_64*debug*.apk" ;;
    "x86") APK_PATTERN="*x86*debug*.apk" ;;
    *) APK_PATTERN="*debug*.apk" ;;
esac

APK_FILE=$(find "$OUTPUT_DIR" -name "$APK_PATTERN" | head -1)

if [ -z "$APK_FILE" ]; then
    echo "      Warning: No exact match for ABI ${DEVICE_ABI}. Picking any debug APK..."
    APK_FILE=$(find "$OUTPUT_DIR" -name "*debug*.apk" | head -1)
fi

if [ -z "$APK_FILE" ]; then
    echo "Error: No APK found in ${OUTPUT_DIR}. Please check the build output."
    exit 1
fi

echo "      Using APK: $(basename ${APK_FILE})"

# Step 3: Install APK
echo ""
echo "[2/3] Installing APK to ${TARGET}..."
adb -s ${TARGET} install -r "${APK_FILE}"

# Step 4: Clear state and Launch
echo ""
echo "[3/3] Launching Transfeero..."

# Force stop to ensure fresh config load
adb -s ${TARGET} shell am force-stop org.mozilla.fenix.debug

# Launch with the main Transfeero URL
adb -s ${TARGET} shell am start -n org.mozilla.fenix.debug/org.mozilla.fenix.IntentReceiverActivity -d "https://control.transfeero.com"

# Setup Logcat
mkdir -p logs
adb -s ${TARGET} logcat -c
rm -f logs/logcat.log
echo "Recording logs to logs/logcat.log..."
adb -s ${TARGET} logcat "*:I" > logs/logcat.log &
LOG_PID=$!

echo ""
echo "============================================"
echo " SUCCESS! App launched on ${TARGET}"
echo " Logcat PID: $LOG_PID"
echo " View logs: tail -f logs/logcat.log"
echo "============================================"
