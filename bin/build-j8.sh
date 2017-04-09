#!/bin/bash

rm -r target/main/exploded/*

javac -d target/main/exploded               \
      -sourcepath main            \
      -XDignore.symbol.file                 \
      $(find main -name "*.java")

jar cf target/main/artifact/cart.jar \
    -C target/main/exploded .
