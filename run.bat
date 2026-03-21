@echo off
REM Thrift App - Complete Build & Run
REM Builds the APK and installs it on connected device/emulator

setlocal enabledelayedexpansion

echo.
echo ========================================
echo    THRIFT APP - BUILD AND RUN
echo ========================================
echo.

cd /d "C:\Users\david\OneDrive\Documents\codes\thrift"

REM Step 1: Check for devices
echo [1/4] Checking for connected devices...
adb devices | findstr /v "List" | findstr /v "^$" > nul
if errorlevel 1 (
    echo.
    echo ERROR: No devices found!
    echo.
    echo Please:
    echo - Start Android emulator in Android Studio, OR
    echo - Connect physical device via USB
    echo - Enable USB debugging
    echo.
    pause
    exit /b 1
)

REM Step 2: Build APK
echo [2/4] Building APK...
call gradlew assembleDebug -q
if errorlevel 1 (
    echo.
    echo ERROR: Build failed!
    pause
    exit /b 1
)

REM Step 3: Install APK
echo [3/4] Installing app...
adb install -r app\build\outputs\apk\debug\app-debug.apk > nul 2>&1
if errorlevel 1 (
    echo.
    echo ERROR: Installation failed!
    pause
    exit /b 1
)

REM Step 4: Launch app
echo [4/4] Launching app...
adb shell am start -n com.example.thrift/com.example.thrift.MainActivity

echo.
echo ========================================
echo    ✓ THRIFT APP LAUNCHED!
echo ========================================
echo.
echo Package: com.example.thrift
echo Activity: MainActivity
echo.
echo View logs with: adb logcat | findstr thrift
echo.

pause

