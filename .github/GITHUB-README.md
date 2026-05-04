# GitHub Automation

[![Playwright UI Automation - Parabank](https://github.com/krishnaharshap/ui-automation-parabank/actions/workflows/playwright-ci.yml/badge.svg)](https://github.com/krishnaharshap/ui-automation-parabank/actions/workflows/playwright-ci.yml)

This directory contains the GitHub Actions workflow for the ParaBank Playwright Java smoke suite.

## Workflow

`workflows/playwright-ci.yml`

- Triggers on push and pull request events targeting `main`.
- Uses Java 17 because the Maven compiler is configured with `<release>17</release>`.
- Installs Chromium with the Playwright Java CLI.
- Runs `mvn -B test -Dplaywright.headless=true`.
- Uploads screenshots, Surefire reports, and Extent reports.

## Notes

The workflow does not require Node.js because the project uses Playwright for Java, not Playwright Test for Node.
