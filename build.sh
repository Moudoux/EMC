#!/bin/bash

# Some path variables
MCP_DIR="/mnt/d/MCP/MCP 1.12/"
MINECRAFT_JAR="/mnt/c/Users/deftware/AppData/Roaming/.minecraft/versions/1.12/1.12.jar"

# EMC installer jar
DIFF_TOOL="/mnt/d/MCP/Resources/Installer.jar"

if [ -d "build/" ]; then
	rm -R build
fi

mkdir build && cd build

cp $MINECRAFT_JAR mc.jar

# Add resources


rm mc.jar