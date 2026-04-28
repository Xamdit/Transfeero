#!/bin/bash
# ============================================
# test.sh - Emulator Debug Setup & Push APK
# Target device: emulator
# ============================================

./publish.sh debug

# Find first running emulator
TARGET=$(adb devices | grep emulator | grep -v offline | head -1 | awk '{print $1}')

if [ -z "$TARGET" ]; then
    echo "Error: No running emulator found."
    echo "Please start an emulator first (e.g., using 'asimulator' command)."
    exit 1
fi

echo "============================================"
echo " Transfeero Emulator Debug Script"
echo " Target: ${TARGET}"
echo "============================================"

# Step 1: Find and install the latest debug APK
echo ""
echo "[1/3] Looking for debug APK in output/ directory..."

OUTPUT_DIR="./output"
if [ ! -d "$OUTPUT_DIR" ]; then
    echo "      No output/ directory found. Run ./publish.sh debug first."
    exit 1
fi

# Detect device ABI
DEVICE_ABI=$(adb -s ${TARGET} shell getprop ro.product.cpu.abi 2>/dev/null | tr -d '\r')
echo "      Device ABI: ${DEVICE_ABI}"

# Find matching APK
APK_FILE=""
if [[ "$DEVICE_ABI" == "arm64-v8a" ]]; then
    APK_FILE=$(find "$OUTPUT_DIR" -name "*arm64*debug*.apk" -o -name "*debug*arm64*.apk" 2>/dev/null | head -1)
elif [[ "$DEVICE_ABI" == "x86_64" ]]; then
    APK_FILE=$(find "$OUTPUT_DIR" -name "*x86_64*debug*.apk" -o -name "*debug*x86_64*.apk" 2>/dev/null | head -1)
elif [[ "$DEVICE_ABI" == "x86" ]]; then
    APK_FILE=$(find "$OUTPUT_DIR" -name "*x86*debug*.apk" -o -name "*debug*x86*.apk" 2>/dev/null | head -1)
elif [[ "$DEVICE_ABI" == "armeabi-v7a" ]]; then
    APK_FILE=$(find "$OUTPUT_DIR" -name "*armeabi*debug*.apk" -o -name "*debug*armeabi*.apk" 2>/dev/null | head -1)
fi

# Fallback: pick any debug APK
if [ -z "$APK_FILE" ]; then
    APK_FILE=$(find "$OUTPUT_DIR" -name "*debug*.apk" 2>/dev/null | head -1)
fi

if [ -z "$APK_FILE" ]; then
    echo "      No APK found in ${OUTPUT_DIR}"
    exit 1
fi

echo "[2/3] Installing: $(basename ${APK_FILE})"
adb -s ${TARGET} install -r "${APK_FILE}"

if [ $? -ne 0 ]; then
    echo "Error: Installation failed."
    exit 1
fi

echo ""
echo "[3/3] Setting up Logcat and Launching App..."

# Create logs directory if it doesn't exist
mkdir -p logs

# Clear old logcat
adb -s ${TARGET} logcat -c
rm -f logs/logcat.log

# Start logcat in background and save to logs/logcat.log
# Filter only info level and above to reduce noise
adb -s ${TARGET} logcat "*:I" > logs/logcat.log &
LOGCAT_PID=$!

# Force stop the app before launching to ensure fresh configuration loading
adb -s ${TARGET} shell am force-stop org.mozilla.fenix.debug

# Launch the app
adb -s ${TARGET} shell am start -a android.intent.action.VIEW \
    -d "https://control.transfeero.com/new_rides" \
    -n org.mozilla.fenix.debug/org.mozilla.fenix.IntentReceiverActivity 2>/dev/null || \
adb -s ${TARGET} shell monkey -p org.mozilla.fenix.debug -c android.intent.category.LAUNCHER 1 2>/dev/null

echo ""
echo "============================================"
echo " SUCCESS! App installed & launched on ${TARGET}"
echo " Logcat is being recorded in background (PID $LOGCAT_PID)"
echo " -> To view logs in real-time, run: tail -f logs/logcat.log"
echo " -> To search logs later, use: cat logs/logcat.log | grep -i <keyword>"
echo " -> To stop recording logs, run: kill $LOGCAT_PID"
echo "============================================"
