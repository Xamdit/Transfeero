#!/bin/bash
# scripts/update_extensions.sh

set -e

EXT_SRC="./extensions"
EXT_DEST="./app/src/main/assets/extensions"

echo "Fenix: Updating extensions from $EXT_SRC to $EXT_DEST..."

mkdir -p "$EXT_DEST"

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

echo ""
echo "Fenix: Extensions updated successfully."
echo "Custom extensions installed:"
for js_file in "$EXT_SRC"/*.js; do
    [ -f "$js_file" ] && echo "  - $(basename "${js_file%.*}")"
done
