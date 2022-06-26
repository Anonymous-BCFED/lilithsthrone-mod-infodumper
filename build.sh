#!/bin/bash

# I like to combine name and project, so each mod is atomic.
MODAUTHORID=anonymousbcfed_infodumper

rm -rf dist/
mvn clean package
mkdir -p dist/res/mods/${MODAUTHORID}/plugins
cp -v target/infodumper-*-SNAPSHOT-jar-with-dependencies.jar dist/res/mods/${MODAUTHORID}/plugins/infodumper-${VERSION}-SNAPSHOT.jar
cp -v README-dist.md dist/res/mods/${MODAUTHORID}/README.md
cp -v LICENSE dist/res/mods/${MODAUTHORID}/LICENSE
