#!/usr/bin/env pwsh
# Thrift App - PowerShell Launcher
# This script builds, installs, and launches the Thrift app

Write-Host ""
Write-Host "╔════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║     THRIFT APP - LAUNCHER              ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""

# Set location
Set-Location "C:\Users\david\OneDrive\Documents\codes\thrift"

# Check connected devices
Write-Host "📱 Checking for connected devices..." -ForegroundColor Yellow
$devices = & adb devices | Select-Object -Skip 1 | Where-Object {$_ -ne "" -and $_ -notlike "*offline*"}

if ($null -eq $devices -or $devices.Count -eq 0) {
    Write-Host "❌ No devices found!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Please:" -ForegroundColor Yellow
    Write-Host "  1. Start Android emulator in Android Studio" -ForegroundColor Yellow
    Write-Host "  2. Or connect physical device via USB" -ForegroundColor Yellow
    Write-Host "  3. Enable USB debugging" -ForegroundColor Yellow
    exit 1
}

Write-Host "✅ Found device(s):" -ForegroundColor Green
foreach ($device in $devices) {
    Write-Host "   - $device" -ForegroundColor Green
}
Write-Host ""

# Install APK
Write-Host "📦 Installing app..." -ForegroundColor Yellow
$apkPath = "app\build\outputs\apk\debug\app-debug.apk"

if (-not (Test-Path $apkPath)) {
    Write-Host "❌ APK not found at: $apkPath" -ForegroundColor Red
    Write-Host "Please build the app first using: .\gradlew assembleDebug" -ForegroundColor Yellow
    exit 1
}

& adb install -r $apkPath

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Installation failed!" -ForegroundColor Red
    exit 1
}

Write-Host "✅ App installed successfully!" -ForegroundColor Green
Write-Host ""

# Launch app
Write-Host "🚀 Launching app..." -ForegroundColor Yellow
& adb shell am start -n "com.example.thrift/com.example.thrift.MainActivity"

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Thrift app launched!" -ForegroundColor Green
    Write-Host ""
    Write-Host "📊 App Details:" -ForegroundColor Cyan
    Write-Host "   Package: com.example.thrift" -ForegroundColor Cyan
    Write-Host "   Activity: MainActivity" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "📋 To view logs, run:" -ForegroundColor Yellow
    Write-Host "   adb logcat | findstr thrift" -ForegroundColor Gray
} else {
    Write-Host "❌ Failed to launch app!" -ForegroundColor Red
    exit 1
}

