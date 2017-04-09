#!/usr/bin/env bash

items=("Clean Architecture" "Pragmatic Programmer" "Clean Code")

for item in "${items[@]}"; do

    echo "Sending item: $item"
    curl -d "item=$item" \
        localhost:8080/cart
    echo -e  "\n"

done