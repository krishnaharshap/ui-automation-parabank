# Project Repo Structure

ui-automation-parabank/

├─ src/

│   ├─ main/

│   │    └── java/io/github/krishnaharshap/qa/automation/

│   │           ├── core/PlaywrightManager.java

│   │           ├── pages/HomePage.java

│   │           └── (other pure helpers, no TestNG or Extent imports)

│   └─ test/

│        ├── java/io/github/krishnaharshap/qa/automation/

│        │      ├── base/BaseTest.java

│        │      ├── utils/ExtentManager.java

│        │      ├── listeners/TestListener.java

│        │      └── tests/ParabankLoginTest.java, ParabankTests.java, etc.

│        └── resources/ (testdata, config, etc.)



# UI Automation Framework - Parabank - GitHub Badge: 

[![Playwright UI Automation - Parabank](https://github.com/krishnaharshap/ui-automation-parabank/actions/workflows/playwright-ci.yml/badge.svg)](https://github.com/krishnaharshap/ui-automation-parabank/actions/workflows/playwright-ci.yml)