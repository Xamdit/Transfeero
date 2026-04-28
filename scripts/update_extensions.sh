#!/bin/bash
# scripts/update_extensions.sh

set -e

EXT_SRC="./extensions"
EXT_DEST="./app/src/main/assets/extensions"

echo "Fenix: Updating extensions from $EXT_SRC to $EXT_DEST..."

mkdir -p "$EXT_DEST"

# NEW: Copy app-config.json directly from root to assets for native access
if [ -f "./app-config.json" ]; then
    echo "Processing app-config.json from root..."
    cp "./app-config.json" "app/src/main/assets/app-config.json"
fi

# Remove only our custom extension directories (identified by .js source)
for js_file in "$EXT_SRC"/*.js; do
    if [ -f "$js_file" ]; then
        filename=$(basename "$js_file")
        ext_name="${filename%.*}"
        rm -rf "$EXT_DEST/$ext_name"
    fi
done

# Process each .js file in ./extensions
for js_file in "$EXT_SRC"/*.js; do
    if [ -f "$js_file" ]; then
        filename=$(basename "$js_file")
        ext_name="${filename%.*}"
        target_dir="$EXT_DEST/$ext_name"
        ext_id="${ext_name}@transfeero.com"

        echo "Processing $ext_name..."
        mkdir -p "$target_dir"

        # Copy script
        cp "$js_file" "$target_dir/content_script.js"

        # Determine permissions
        if grep -q "browser.storage" "$js_file"; then
            PERMISSIONS='"geckoViewAddons", "<all_urls>", "storage"'
        else
            PERMISSIONS='"geckoViewAddons", "<all_urls>"'
        fi

        # Determine run_at
        if [[ "$ext_name" == *"login"* ]] || [[ "$ext_name" == *"auto-login"* ]]; then
            RUN_AT="document_end"
        else
            RUN_AT="document_start"
        fi

        # Generate manifest.json with proper Gecko ID
        cat <<EOF > "$target_dir/manifest.json"
{
  "manifest_version": 2,
  "applications": {
    "gecko": {
      "id": "${ext_id}"
    }
  },
  "name": "Fenix $ext_name",
  "version": "1.0",
  "description": "Fenix built-in extension: $ext_name",
  "content_scripts": [
    {
      "matches": ["<all_urls>"],
      "js": ["content_script.js"],
      "run_at": "$RUN_AT"
    }
  ],
  "permissions": [$PERMISSIONS]
}
EOF
        echo "  -> id: ${ext_id}, run_at: $RUN_AT"
    fi
done

# Process directories in ./extensions that have manifest.json
for ext_dir in "$EXT_SRC"/*/; do
    if [ -d "$ext_dir" ] && [ -f "${ext_dir}manifest.json" ]; then
        ext_name=$(basename "$ext_dir")
        target_dir="$EXT_DEST/$ext_name"
        ext_id="${ext_name}@transfeero.com"

        echo "Processing directory extension: $ext_name..."
        rm -rf "$target_dir"
        mkdir -p "$target_dir"

        # Copy all files from the extension directory
        cp -R "$ext_dir"* "$target_dir/"

        # Update manifest.json with Gecko ID if missing
        if ! grep -q "gecko" "${target_dir}/manifest.json"; then
            # Use python to safely update JSON
            python3 -c "
import json, sys
with open('${target_dir}/manifest.json', 'r') as f:
    data = json.load(f)
if 'applications' not in data:
    data['applications'] = {}
if 'gecko' not in data['applications']:
    data['applications']['gecko'] = {'id': '${ext_id}'}
# Ensure it's manifest version 2 for better compatibility if it was 3
# data['manifest_version'] = 2 
with open('${target_dir}/manifest.json', 'w') as f:
    json.dump(data, f, indent=2)
"
        fi
        echo "  -> id: ${ext_id} (Directory base)"
    fi
done

echo ""
echo "Fenix: Extensions updated successfully."
echo "Custom extensions installed:"
for js_file in "$EXT_SRC"/*.js; do
    [ -f "$js_file" ] && echo "  - $(basename "${js_file%.*}")"
done
for ext_dir in "$EXT_SRC"/*/; do
    if [ -d "$ext_dir" ] && [ -f "${ext_dir}manifest.json" ]; then
        echo "  - $(basename "$ext_dir") (Directory)"
    fi
done
