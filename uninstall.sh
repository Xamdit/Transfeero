#!/bin/bash

# uninstall.sh - Force stop and uninstall Fenix build variants from device

# Check if adb is available, with a fallback for common Mac path
if ! command -v adb &> /dev/null; then
    if [ -f "$HOME/Library/Android/sdk/platform-tools/adb" ]; then
        export PATH="$PATH:$HOME/Library/Android/sdk/platform-tools"
    else
        echo "Error: adb command not found and not found in default Mac path."
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

echo "Force stopping and uninstalling Transfeero/Fenix apps..."

# List of potential package names for different build variants
PACKAGES=(
    "org.mozilla.fenix.debug"
    "org.mozilla.fenix"
    "org.mozilla.firefox"
    "org.mozilla.firefox_beta"
)

for pkg in "${PACKAGES[@]}"; do
    echo "-> Checking $pkg..."
    
    # Force stop
    adb -s "$DEVICE" shell am force-stop "$pkg" 2>/dev/null
    
    # Uninstall
    OUTPUT=$(adb -s "$DEVICE" uninstall "$pkg" 2>&1)
    
    if [[ $OUTPUT == *"Success"* ]]; then
        echo "   [✓] Successfully uninstalled $pkg"
    elif [[ $OUTPUT == *"DELETE_FAILED_INTERNAL_ERROR"* ]] || [[ $OUTPUT == *"Unknown package"* ]]; then
        echo "   [-] $pkg is not installed"
    else
        echo "   [!] $OUTPUT"
    fi
done

echo "Done."
