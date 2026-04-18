#!/bin/bash
# ============================================
# test.sh - WiFi ADB Debug Setup & Push APK
# Target device: 192.168.1.99
# ============================================

DEVICE_IP="192.168.1.99"
DEVICE_PORT="5555"
TARGET="${DEVICE_IP}:${DEVICE_PORT}"

echo "============================================"
echo " Transfeero WiFi Debug Script"
echo " Target: ${TARGET}"
echo "============================================"

# Step 1: Enable TCP/IP mode on currently connected (USB) device
echo ""
echo "[1/4] Enabling TCP/IP mode on USB device..."
adb tcpip ${DEVICE_PORT}
if [ $? -ne 0 ]; then
    echo "Warning: tcpip command failed. Device may already be in TCP mode."
fi

# Small delay to let the device switch modes
sleep 2

# Step 2: Connect over WiFi
echo ""
echo "[2/4] Connecting to ${TARGET} over WiFi..."
echo "      (You can now disconnect the USB cable)"
echo ""

MAX_RETRIES=5
RETRY=0
CONNECTED=false

while [ $RETRY -lt $MAX_RETRIES ]; do
    RESULT=$(adb connect ${TARGET} 2>&1)
    echo "      Attempt $((RETRY + 1)): ${RESULT}"
    if echo "$RESULT" | grep -q "connected"; then
        CONNECTED=true
        break
    fi
    RETRY=$((RETRY + 1))
    sleep 2
done

if [ "$CONNECTED" = false ]; then
    echo ""
    echo "Error: Could not connect to ${TARGET} after ${MAX_RETRIES} attempts."
    echo "Please make sure:"
    echo "  - The device and Mac are on the same WiFi network."
    echo "  - The device IP is ${DEVICE_IP}."
    echo "  - No firewall is blocking port ${DEVICE_PORT}."
    exit 1
fi

echo ""
echo "[3/4] Verifying connection..."
adb -s ${TARGET} get-serialno 2>/dev/null
if [ $? -ne 0 ]; then
    echo "Error: Device responds but cannot verify serial. Please check ADB connection."
    exit 1
fi
echo "      Connected! Device is ready."

# Step 4: Find and install the latest debug APK
echo ""
echo "[4/4] Looking for debug APK in output/ directory..."

OUTPUT_DIR="./output"
if [ ! -d "$OUTPUT_DIR" ]; then
    echo "      No output/ directory found. Skipping APK install."
    echo ""
    echo "============================================"
    echo " WiFi debug connected to ${TARGET}"
    echo " Run ./publish.sh debug first to build APK"
    echo "============================================"
    exit 0
fi

# Detect device ABI
DEVICE_ABI=$(adb -s ${TARGET} shell getprop ro.product.cpu.abi 2>/dev/null | tr -d '\r')
echo "      Device ABI: ${DEVICE_ABI}"

# Find matching APK
APK_FILE=""
if [[ "$DEVICE_ABI" == "arm64-v8a" ]]; then
    APK_FILE=$(find "$OUTPUT_DIR" -name "*arm64*debug*.apk" -o -name "*debug*arm64*.apk" 2>/dev/null | head -1)
elif [[ "$DEVICE_ABI" == "armeabi-v7a" ]]; then
    APK_FILE=$(find "$OUTPUT_DIR" -name "*armeabi*debug*.apk" -o -name "*debug*armeabi*.apk" 2>/dev/null | head -1)
elif [[ "$DEVICE_ABI" == "x86_64" ]]; then
    APK_FILE=$(find "$OUTPUT_DIR" -name "*x86_64*debug*.apk" -o -name "*debug*x86_64*.apk" 2>/dev/null | head -1)
elif [[ "$DEVICE_ABI" == "x86" ]]; then
    APK_FILE=$(find "$OUTPUT_DIR" -name "*x86*debug*.apk" -o -name "*debug*x86*.apk" 2>/dev/null | head -1)
fi

# Fallback: pick any debug APK
if [ -z "$APK_FILE" ]; then
    APK_FILE=$(find "$OUTPUT_DIR" -name "*debug*.apk" 2>/dev/null | head -1)
fi

if [ -z "$APK_FILE" ]; then
    echo "      No APK found in ${OUTPUT_DIR}"
    echo ""
    echo "============================================"
    echo " WiFi debug connected: ${TARGET}"
    echo " Build first: ./publish.sh debug"
    echo "============================================"
    exit 0
fi

echo "      Installing: $(basename ${APK_FILE})"
adb -s ${TARGET} install -r "${APK_FILE}"

if [ $? -eq 0 ]; then
    echo ""
    echo "      Launching application..."
    adb -s ${TARGET} shell am start -n org.mozilla.fenix.debug/org.mozilla.fenix.IntentReceiverActivity 2>/dev/null || \
    adb -s ${TARGET} shell monkey -p org.mozilla.fenix.debug -c android.intent.category.LAUNCHER 1 2>/dev/null
    echo ""
    echo "============================================"
    echo " SUCCESS! App installed & launched"
    echo " WiFi device: ${TARGET}"
    echo "============================================"
else
    echo ""
    echo "Error: Installation failed."
    exit 1
fi
