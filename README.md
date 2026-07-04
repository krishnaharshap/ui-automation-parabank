# ParaBank UI Automation

[![Playwright UI Automation - Parabank](https://github.com/krishnaharshap/ui-automation-parabank/actions/workflows/playwright-ci.yml/badge.svg)](https://github.com/krishnaharshap/ui-automation-parabank/actions/workflows/playwright-ci.yml)

Java UI automation smoke tests for the public ParaBank demo application using Playwright, TestNG, Maven, and Extent Reports.

## Current Purpose

This repository validates that the ParaBank login page is reachable and that missing-login validation is displayed. The suite is intentionally small and CI-focused so the GitHub Actions workflow runs reliably on a headless Linux runner.

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
- Extent HTML output is written under `reports/` (generated locally, not committed — see [Generated Output](#generated-output)).

## Self-Healing: Retry on Transient Failure

Both tests point at a public third-party demo site (`parabank.parasoft.com`) that occasionally has transient
network/load hiccups unrelated to the code under test. `RetryAnalyzer`
(`src/test/java/.../listeners/RetryAnalyzer.java`) automatically retries a failed test up to 2 times
before letting it fail for real, so CI failures represent actual regressions rather than one-off flake.
Tune the retry count with `-Dtestng.retry.max=N`.

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

> **PowerShell users:** quote `-D` system properties, e.g. `mvn -B test "-Dplaywright.headless=true"`.
> See [Troubleshooting](#troubleshooting) for why.

## CI

The workflow in `.github/workflows/playwright-ci.yml`:

1. Checks out the repository.
2. Sets up Temurin Java 17 with Maven caching.
3. Compiles the project.
4. Installs Chromium through the Playwright Java CLI.
5. Runs the TestNG suite in headless mode (with automatic retry on transient failures).
6. Uploads screenshots, Surefire reports, and Extent reports as artifacts.

**CI triggers are currently paused.** The workflow only runs on manual dispatch
(`workflow_dispatch`) while the suite is being stabilized, so no run fires automatically on every
push. See [`.github/GITHUB-README.md`](.github/GITHUB-README.md) for how to re-enable automatic
triggers and how to run the workflow manually in the meantime.

## Generated Output

`reports/` (Extent HTML) and `.vscode/` (local editor settings) are gitignored and are not tracked
in this repository — they're regenerated locally every time you run the suite or open the project
in VS Code. If you see them as untracked/modified locally, that's expected; there's nothing to commit.

## Troubleshooting

- **`mvn -B test -Dplaywright.headless=true` fails with `Unknown lifecycle phase ".headless=true"`
  in PowerShell.** This is a PowerShell argument-parsing quirk, not a project bug — PowerShell can
  mis-tokenize an unquoted `-Dkey=value` argument passed to a native executable. Quote the property:
  `mvn -B test "-Dplaywright.headless=true"`, or run the command from Git Bash / a POSIX shell instead.
- **A test fails once but the retry passes.** Check the console output for a `Retrying <test> (attempt
  N of 2)` line — this means the site had a transient issue and the retry absorbed it. If a test fails
  on every retry, treat it as a real regression, not flake.
- **Chromium install step times out in CI.** Re-run the workflow; the `install --with-deps chromium`
  step downloads a large binary and can occasionally hit runner network variance.

## Project Structure

```text
src/main/java/io/github/krishnaharshap/qa/automation/
  pages/      Page objects (LoginPage)

src/test/java/io/github/krishnaharshap/qa/automation/
  base/       BaseTest — shared Playwright browser/context lifecycle
  listeners/  TestListener (logging + Extent flush), RetryAnalyzer (self-healing retries)
  tests/      ParaBank UI tests

.github/workflows/
  playwright-ci.yml
```
