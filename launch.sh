#!/bin/bash
# Thrift App - Quick Launch Script
# This script installs and launches the Thrift app on connected device/emulator

echo "╔════════════════════════════════════════╗"
echo "║     THRIFT APP - QUICK LAUNCHER        ║"
echo "╚════════════════════════════════════════╝"
echo ""

# Navigate to project directory
cd "C:/Users/david/OneDrive/Documents/codes/thrift" || exit

# Check for connected devices
echo "📱 Checking for connected devices..."
adb devices

echo ""
echo "📦 Installing app..."
adb install -r app/build/outputs/apk/debug/app-debug.apk

if [ $? -ne 0 ]; then
    echo ""
    echo "❌ Installation failed. Please:"
    echo "   1. Start Android emulator in Android Studio"
    echo "   2. Or connect physical device via USB"
    echo "   3. Enable USB debugging"
    exit 1
fi

echo ""
echo "🚀 Launching app..."
adb shell am start -n com.example.thrift/com.example.thrift.MainActivity

echo ""
echo "✅ Thrift app launched!"
echo ""
echo "📊 Viewing logs..."
adb logcat | grep -i thrift

