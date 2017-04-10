#!/bin/bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-9.jdk/Contents/Home

$JAVA_HOME/bin/java \
    --module-path target/main/artifact \
    --add-modules ALL-DEFAULT \
    -m cart/com.swissquote.cart.Main