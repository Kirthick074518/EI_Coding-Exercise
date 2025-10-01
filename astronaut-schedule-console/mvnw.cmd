@ECHO OFF
SETLOCAL EnableExtensions EnableDelayedExpansion

REM Apache Maven Wrapper - Windows
REM Resolve BASE_DIR without trailing backslash to avoid quoting issues
FOR %%i IN ("%~dp0.") DO SET "BASE_DIR=%%~fi"
SET "WRAPPER_JAR=%BASE_DIR%\.mvn\wrapper\maven-wrapper.jar"
SET "WRAPPER_PROPS=%BASE_DIR%\.mvn\wrapper\maven-wrapper.properties"

REM Locate Java
SET "JAVA_EXE=java"
IF NOT "%JAVA_HOME%"=="" (
  SET "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
)

REM Verify Java
"%JAVA_EXE%" -version >NUL 2>&1
IF ERRORLEVEL 1 (
  ECHO Java not found. Please install JDK 17+ or set JAVA_HOME.
  EXIT /B 1
)

REM Download wrapper jar if missing
IF NOT EXIST "!WRAPPER_JAR!" (
  SET "WRAPPER_URL="
  IF EXIST "!WRAPPER_PROPS!" (
    FOR /F "usebackq tokens=1,* delims==" %%A IN ("!WRAPPER_PROPS!") DO (
      IF /I "%%~A"=="wrapperUrl" SET "WRAPPER_URL=%%~B"
    )
  )
  IF "!WRAPPER_URL!"=="" SET "WRAPPER_URL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
  ECHO Downloading Maven Wrapper from !WRAPPER_URL!
  powershell -NoLogo -NoProfile -Command "New-Item -Force -ItemType Directory (Split-Path -Parent '!WRAPPER_JAR!') ^| Out-Null; Invoke-WebRequest -Uri '!WRAPPER_URL!' -OutFile '!WRAPPER_JAR!' -UseBasicParsing" || (
    ECHO Failed to download Maven Wrapper JAR.
    EXIT /B 1
  )
)

SET MAVEN_WRAPPER_MAIN=org.apache.maven.wrapper.MavenWrapperMain
"%JAVA_EXE%" -Dmaven.multiModuleProjectDirectory="!BASE_DIR!" -cp "!WRAPPER_JAR!" %MAVEN_WRAPPER_MAIN% %*
EXIT /B %ERRORLEVEL%
