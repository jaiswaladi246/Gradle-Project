# GradleBoard — Video Demo Flow

Use this order when recording the Gradle tutorial.

## 1. Start with the finished application

```bash
./gradlew bootRun
```

Windows:

```powershell
.\gradlew.bat bootRun
```

Open `http://localhost:8080`.

## 2. Show the project structure

Explain:

- `settings.gradle`
- `build.gradle`
- `gradle.properties`
- `src/main/java`
- `src/main/resources`
- `src/test/java`
- `build/`

## 3. Basic Gradle commands

```bash
./gradlew --version
./gradlew tasks
./gradlew projects
./gradlew properties
```

## 4. Gradle lifecycle

```bash
./gradlew clean
./gradlew compileJava
./gradlew processResources
./gradlew test
./gradlew check
./gradlew build
```

## 5. Dependency management

```bash
./gradlew dependencies
./gradlew dependencies --configuration runtimeClasspath
./gradlew dependencyInsight --dependency spring-core --configuration runtimeClasspath
```

## 6. Custom tasks

```bash
./gradlew helloGradle
./gradlew projectInfo
./gradlew qualityCheck
./gradlew packageApp
```

## 7. Package and run without Gradle

```bash
./gradlew bootJar
java -jar build/libs/gradleboard.jar
```

## 8. Gradle debugging commands

```bash
./gradlew build --info
./gradlew build --debug
./gradlew build --stacktrace
./gradlew build --scan
```

## 9. Incremental build / up-to-date demo

Run twice:

```bash
./gradlew build
./gradlew build
```

On the second run, point out `UP-TO-DATE` tasks.

Then modify one Java file and rerun the build to show selective task execution.
