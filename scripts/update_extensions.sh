#!/bin/bash
# scripts/update_extensions.sh

# Exit on error
set -e

EXT_SRC="./extensions"
EXT_DEST="./app/src/main/assets/extensions"

echo "Fenix: Updating extensions from $EXT_SRC to $EXT_DEST..."

# Create destination if not exists
mkdir -p "$EXT_DEST"

# Clear destination (except for internal folders if any)
# We recreate it to ensure it's clean
rm -rf "$EXT_DEST"/*

# Process each .js file in ./extensions
for js_file in "$EXT_SRC"/*.js; do
    if [ -f "$js_file" ]; then
        filename=$(basename "$js_file")
        ext_name="${filename%.*}"
        target_dir="$EXT_DEST/$ext_name"
        
        echo "Processing $ext_name..."
        
        mkdir -p "$target_dir"
        
        # Copy script
        cp "$js_file" "$target_dir/content_script.js"
        
        # Generate manifest.json
        cat <<EOF > "$target_dir/manifest.json"
{
  "manifest_version": 2,
  "name": "Fenix $ext_name",
  "version": "1.0",
  "description": "Fenix built-in extension for $ext_name",
  "content_scripts": [
    {
      "matches": ["<all_urls>"],
      "js": ["content_script.js"],
      "run_at": "document_start"
    }
  ],
  "permissions": ["<all_urls>"]
}
EOF
    fi
done

echo "Fenix: Extensions updated successfully."
