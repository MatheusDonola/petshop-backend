# Repository Guidelines

## Project Structure & Module Organization
- Source code lives in `src/main/java` (base package `com.petshop`).
- Configuration and web assets are in `src/main/resources` (e.g., `application.properties`, `static/`, `templates/`).
- Tests live in `src/test/java` and mirror the main package structure.
- Build output is written to `target/`.

## Build, Test, and Development Commands
- `./mvnw spring-boot:run` (or `mvnw.cmd spring-boot:run` on Windows): run the app locally with Spring Boot.
- `./mvnw test`: run the full test suite.
- `./mvnw package`: build the JAR into `target/`.

## Coding Style & Naming Conventions
- Java 21 is the target runtime (see `pom.xml`).
- Follow standard Java conventions: 4-space indentation, PascalCase for classes, camelCase for methods/variables.
- Keep package names lowercase (e.g., `com.petshop.service`).
- No formatter/linter is configured yet; keep diffs tidy and consistent with existing style.

## Testing Guidelines
- Tests use Spring Boot’s test support (JUnit via `spring-boot-starter-*-test`).
- Name tests with the `*Tests` suffix and place them in the matching package under `src/test/java`.
- Run tests with `./mvnw test` before opening a PR.

## Commit & Pull Request Guidelines
- Current history uses Conventional Commit-style prefixes (e.g., `feat: initial commit`).
- Use `type: short description` (examples: `feat:`, `fix:`, `chore:`, `refactor:`).
- PRs should include a concise summary, test evidence (command output or notes), and screenshots for UI changes.

## Configuration Tips
- Application settings live in `src/main/resources/application.properties`.
- Keep secrets out of git; use environment variables or local overrides when needed.