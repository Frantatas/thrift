@echo off
REM Thrift App - Build Script
REM Builds the debug APK ready for testing

echo Thrift App - Build Script
echo =========================
echo.
echo Building Thrift App...
echo.

cd /d "C:\Users\david\OneDrive\Documents\codes\thrift"

REM Clean previous build
echo Cleaning previous builds...
call gradlew clean

REM Build the app
echo Building app...
call gradlew assembleDebug

REM Check if build was successful
if %ERRORLEVEL% equ 0 (
    echo.
    echo ✓ BUILD SUCCESSFUL!
    echo.
    echo APK location:
    echo app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo Next step: Run run_app.bat to install and launch
) else (
    echo.
    echo ✗ BUILD FAILED
    echo Check the output above for errors
)

pause

