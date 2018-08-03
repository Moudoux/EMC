@echo off
echo Building EMC for Forge and vanilla Minecraft...
:: Forge build
:: gradle build -Pforgebuild="true"
:: Normal build
gradle build -Pforgebuild="false"
