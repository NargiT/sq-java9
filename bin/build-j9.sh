#!/bin/bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-9.jdk/Contents/Home

rm -r target/main/exploded/*
$JAVA_HOME/bin/javac -d target/main/exploded               \
      --module-path target \
      --module-source-path main            \
      $(find main/*/ -name "*.java")

rm -r target/main/artifact/*
for dir in target/main/exploded/*; do
  if [ -d $dir ]; then
    $JAVA_HOME/bin/jar --create --file target/main/artifact/$(basename $dir).jar -C $dir .
  fi
done
