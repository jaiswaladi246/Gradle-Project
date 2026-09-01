@echo off
setlocal

set "APP_HOME=%~dp0"
set "WRAPPER_JAR=%APP_HOME%gradle\wrapper\gradle-wrapper.jar"
set "WRAPPER_URL=https://raw.githubusercontent.com/gradle/gradle/v9.7.1/gradle/wrapper/gradle-wrapper.jar"
set "EXPECTED_SHA=7a9ce74cff467ca1bf60a4fcd9f05185acceda4d0f382434d393e17864262c5d"

if not exist "%WRAPPER_JAR%" (
  echo Gradle wrapper JAR not found. Downloading Gradle 9.7.1 wrapper...
  if not exist "%APP_HOME%gradle\wrapper" mkdir "%APP_HOME%gradle\wrapper"
  where curl.exe >nul 2>&1
  if errorlevel 1 (
    echo ERROR: curl.exe is required for the first wrapper bootstrap.
    echo Alternatively install Gradle and run: gradle wrapper --gradle-version 9.7.1
    exit /b 1
  )
  curl.exe -fL "%WRAPPER_URL%" -o "%WRAPPER_JAR%"
  if errorlevel 1 exit /b 1
)

for /f "tokens=*" %%H in ('certutil -hashfile "%WRAPPER_JAR%" SHA256 ^| findstr /v /i "hash certutil"') do set "ACTUAL_SHA=%%H"
set "ACTUAL_SHA=%ACTUAL_SHA: =%"
if /i not "%ACTUAL_SHA%"=="%EXPECTED_SHA%" (
  echo ERROR: gradle-wrapper.jar checksum mismatch.
  del /q "%WRAPPER_JAR%" >nul 2>&1
  exit /b 1
)

if defined JAVA_HOME (
  set "JAVACMD=%JAVA_HOME%\bin\java.exe"
) else (
  set "JAVACMD=java.exe"
)

"%JAVACMD%" %JAVA_OPTS% %GRADLE_OPTS% -Dorg.gradle.appname=gradlew -classpath "%WRAPPER_JAR%" org.gradle.wrapper.GradleWrapperMain %*
endlocal
