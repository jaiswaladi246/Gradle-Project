#!/bin/sh

# Lightweight bootstrap for the official Gradle Wrapper JAR.
# Once downloaded, execution is delegated to Gradle's WrapperMain.

APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd -P) || exit 1
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
WRAPPER_URL="https://raw.githubusercontent.com/gradle/gradle/v9.7.1/gradle/wrapper/gradle-wrapper.jar"
EXPECTED_SHA="7a9ce74cff467ca1bf60a4fcd9f05185acceda4d0f382434d393e17864262c5d"

if [ ! -f "$WRAPPER_JAR" ]; then
    echo "Gradle wrapper JAR not found. Downloading Gradle 9.7.1 wrapper..."
    mkdir -p "$APP_HOME/gradle/wrapper"
    if command -v curl >/dev/null 2>&1; then
        curl -fL "$WRAPPER_URL" -o "$WRAPPER_JAR" || exit 1
    elif command -v wget >/dev/null 2>&1; then
        wget -q "$WRAPPER_URL" -O "$WRAPPER_JAR" || exit 1
    else
        echo "ERROR: Install curl or wget, or run 'gradle wrapper --gradle-version 9.7.1'." >&2
        exit 1
    fi
fi

if command -v sha256sum >/dev/null 2>&1; then
    ACTUAL_SHA=$(sha256sum "$WRAPPER_JAR" | awk '{print $1}')
    if [ "$ACTUAL_SHA" != "$EXPECTED_SHA" ]; then
        echo "ERROR: gradle-wrapper.jar checksum mismatch." >&2
        rm -f "$WRAPPER_JAR"
        exit 1
    fi
fi

if [ -n "$JAVA_HOME" ]; then
    JAVACMD="$JAVA_HOME/bin/java"
else
    JAVACMD=java
fi

exec "$JAVACMD" ${JAVA_OPTS:-} ${GRADLE_OPTS:-} -Dorg.gradle.appname=gradlew -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
