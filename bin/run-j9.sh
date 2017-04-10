#!/bin/bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-9.jdk/Contents/Home

$JAVA_HOME/bin/java \
    --module-path dependencies:target/main/artifact \
    -m cart.service/com.swissquote.cart.Main