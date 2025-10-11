#Project Repo Structure

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
