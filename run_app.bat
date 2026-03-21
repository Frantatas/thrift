@echo off
REM Run Thrift App on Android Emulator
REM This script builds and launches the app on the first connected emulator/device

echo Thrift App - Build and Run Script
echo ==================================
echo.

REM Check for connected devices
echo Checking for connected devices...
adb devices

echo.
echo Starting app installation...
adb install -r app\build\outputs\apk\debug\app-debug.apk

if %ERRORLEVEL% neq 0 (
    echo.
    echo Error: No device found. Please:
    echo 1. Start an Android emulator in Android Studio
    echo 2. Or connect a physical device via USB
    echo 3. Then run this script again
    pause
    exit /b 1
)

echo.
echo Launching app...
adb shell am start -n com.example.thrift/com.example.thrift.MainActivity

echo.
echo Thrift app launched successfully!
echo Monitor logs with: adb logcat | findstr thrift
pause

