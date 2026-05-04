# ParaBank UI Automation

Java UI automation smoke tests for the public ParaBank demo application using Playwright, TestNG, Maven, Extent Reports, and GitHub Actions.

## Current Purpose

This repository validates that the ParaBank login page is reachable and that missing-login validation is displayed. The suite is intentionally small and CI-focused so the first GitHub Actions workflow can run reliably in a headless Linux runner.

## Tech Stack

- Java 17
- Maven
- Playwright for Java
- TestNG
- Extent Reports
- GitHub Actions

## Test Coverage

- ParaBank login form loads successfully.
- Empty username/password submission displays the expected validation error.
- Screenshots are captured to `target/screenshots/` when a test fails.
- TestNG and Surefire reports are written under `target/surefire-reports/`.
- Extent HTML output is written under `reports/`.

## Run Locally

Install browser binaries for Playwright Java:

```bash
mvn -B exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"
```

Run the test suite:

```bash
mvn -B test -Dplaywright.headless=true
```

Use a different ParaBank URL when needed:

```bash
mvn -B test -Dplaywright.headless=true -Dparabank.baseUrl=https://parabank.parasoft.com/parabank/index.htm
```

## CI

The workflow in `.github/workflows/playwright-ci.yml` runs on pushes and pull requests to `main`.

CI steps:

1. Check out the repository.
2. Set up Temurin Java 17 with Maven caching.
3. Compile the project.
4. Install Chromium through the Playwright Java CLI.
5. Run the TestNG suite in headless mode.
6. Upload screenshots, Surefire reports, and Extent reports as artifacts.

## Project Structure

```text
src/main/java/io/github/krishnaharshap/qa/automation/
  core/       Shared Playwright setup helpers
  pages/      Page objects

src/test/java/io/github/krishnaharshap/qa/automation/
  base/       Base test setup
  listeners/  TestNG listeners
  tests/      ParaBank UI tests
  utils/      Extent Reports setup

.github/workflows/
  playwright-ci.yml
```
