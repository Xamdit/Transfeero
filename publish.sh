#!/bin/bash
# publish.sh - Wrapper for build.sh to support 'debug' argument
# Matches the expectation in test.sh

VARIANT=${1:-debug}

if [[ "$VARIANT" == "debug" ]]; then
    ./build.sh
else
    echo "Unsupported variant: $VARIANT"
    echo "Currently only 'debug' is supported via build.sh"
    exit 1
fi
