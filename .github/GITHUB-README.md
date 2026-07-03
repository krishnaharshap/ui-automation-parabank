# GitHub Automation

[![Playwright UI Automation - Parabank](https://github.com/krishnaharshap/ui-automation-parabank/actions/workflows/playwright-ci.yml/badge.svg)](https://github.com/krishnaharshap/ui-automation-parabank/actions/workflows/playwright-ci.yml)

This directory contains the GitHub Actions workflow for the ParaBank Playwright Java smoke suite.

## Workflow

`workflows/playwright-ci.yml`

- Uses Java 17 because the Maven compiler is configured with `<release>17</release>`.
- Installs Chromium with the Playwright Java CLI.
- Runs `mvn -B test -Dplaywright.headless=true` (tests self-retry on transient failure via `RetryAnalyzer`).
- Uploads screenshots, Surefire reports, and Extent reports.

## Trigger status: paused

Automatic triggers (`push` / `pull_request` on `main`) are **commented out** in the workflow file
while the suite is being actively stabilized. This avoids firing a CI run on every commit during
iteration. The workflow currently only runs via manual dispatch:

```yaml
on:
  workflow_dispatch:
  # push:
  #   branches: ["main"]
  # pull_request:
  #   branches: ["main"]
```

**To run it manually:** GitHub UI → Actions → "Playwright UI Automation - Parabank" → Run workflow.

**To re-enable automatic runs** once the suite has stayed green across several manual runs,
uncomment the `push`/`pull_request` block in `.github/workflows/playwright-ci.yml` and remove (or
keep alongside) `workflow_dispatch`.

Note: this repository is public, so GitHub-hosted runner minutes are free regardless — pausing
triggers here is about not spamming runs during active debugging, not about avoiding cost on this
specific repo. If this workflow is ever reused in a private repo, keep this pause pattern in mind
before wiring up new automatic triggers.

## Notes

The workflow does not require Node.js because the project uses Playwright for Java, not Playwright Test for Node.
