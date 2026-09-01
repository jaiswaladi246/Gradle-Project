# GradleBoard — Gradle DevOps Dashboard

A polished Gradle project built for a complete Gradle tutorial. It combines a real Java/Spring Boot application, responsive UI, REST endpoints, tests, custom Gradle tasks, packaging, and CI in one repository.

> **UI preview:** open `docs/dashboard-preview.html` directly in a browser if you want to inspect the layout without starting Spring Boot.

## What this project demonstrates

- Gradle project structure
- `settings.gradle` and `build.gradle`
- Gradle plugins
- Maven Central repositories
- Dependency configurations
- Java toolchains
- Gradle task lifecycle
- Testing with JUnit
- Spring Boot executable JAR packaging
- Custom Gradle tasks
- Incremental builds and build cache
- Dependency reports and dependency insight
- GitHub Actions CI with Gradle

## Tech stack

| Technology | Version / Purpose |
| --- | --- |
| Java | 21 |
| Gradle | 9.7.1 |
| Spring Boot | 4.1.1 |
| Thymeleaf | Server-side UI templates |
| HTML/CSS/JavaScript | Responsive dashboard |
| JUnit 5 | Testing |

## UI features

- Build metrics dashboard
- Gradle lifecycle visualization
- Build trigger form
- DEV / QA / PPD / PROD environments
- Build history table
- Search builds by service/branch
- Filter by SUCCESS / FAILED / RUNNING
- REST APIs
- Responsive desktop/mobile layout

## Project structure

```text
gradle-devops-dashboard/
├── .github/
│   └── workflows/
│       └── gradle-ci.yml
├── docs/
│   └── dashboard-preview.png
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/com/devopsshack/gradleboard/
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── service/
│   │   │   └── GradleBoardApplication.java
│   │   └── resources/
│   │       ├── static/css/styles.css
│   │       ├── static/js/app.js
│   │       ├── templates/index.html
│   │       └── application.yml
│   └── test/
│       └── java/com/devopsshack/gradleboard/
├── .dockerignore
├── .gitignore
├── Dockerfile
├── build.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
├── DEMO-GUIDE.md
├── LICENSE
└── README.md
```

## Prerequisites

- Java 21
- Internet access on the first Gradle run so dependencies can be downloaded

Check Java:

```bash
java -version
```

## Run the application

Linux/macOS:

```bash
chmod +x gradlew
./gradlew bootRun
```

Windows:

```powershell
.\gradlew.bat bootRun
```

Then open:

```text
http://localhost:8080
```

> The included launch scripts bootstrap the official Gradle 9.7.1 wrapper JAR on first use and validate its SHA-256 checksum.

## Core Gradle commands

```bash
./gradlew tasks
./gradlew clean
./gradlew compileJava
./gradlew test
./gradlew check
./gradlew build
./gradlew bootRun
./gradlew bootJar
```

Windows: replace `./gradlew` with `.\gradlew.bat`.

## Build the executable JAR

```bash
./gradlew clean build
```

Artifact:

```text
build/libs/gradleboard.jar
```

Run the packaged application:

```bash
java -jar build/libs/gradleboard.jar
```

This is useful in a tutorial because it shows that Gradle is needed to **build** the artifact, while Java is enough to **run** the packaged JAR.

## Dependency commands

View all dependency configurations:

```bash
./gradlew dependencies
```

Runtime dependency tree:

```bash
./gradlew dependencies --configuration runtimeClasspath
```

Find why a dependency exists:

```bash
./gradlew dependencyInsight --dependency spring-core --configuration runtimeClasspath
```

## Custom Gradle tasks

```bash
./gradlew helloGradle
./gradlew projectInfo
./gradlew qualityCheck
./gradlew packageApp
```

Run:

```bash
./gradlew tasks --group tutorial
```

## Run with Docker

Build the container image:

```bash
docker build -t gradleboard .
```

Run it:

```bash
docker run --rm -p 8080:8080 gradleboard
```

The Dockerfile uses a Gradle 9.7.1 + JDK 21 build stage and a smaller Java 21 runtime stage.

## REST endpoints

### Build list

```http
GET /api/builds
```

### Build summary

```http
GET /api/summary
```

Example:

```bash
curl http://localhost:8080/api/summary
```

## Run tests

```bash
./gradlew test
```

HTML test report:

```text
build/reports/tests/test/index.html
```

## GitHub Actions

The repository includes:

```text
.github/workflows/gradle-ci.yml
```

For every push/PR to `main`, CI:

1. Checks out the repository.
2. Installs Java 21.
3. Sets up Gradle 9.7.1.
4. Runs tests and the Gradle build.
5. Uploads `gradleboard.jar` as a workflow artifact.

## Useful tutorial commands

```bash
./gradlew build --info
./gradlew build --stacktrace
./gradlew build --scan
./gradlew properties
./gradlew projects
./gradlew tasks --all
```

For a complete recording sequence, see [DEMO-GUIDE.md](DEMO-GUIDE.md).

## License

MIT
