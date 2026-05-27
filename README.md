# QA Automation Test Suite

## Prerequisites
- Java 21
- Maven 3.6.3
- Chrome browser

## How to Run
### Run all tests
mvn clean test

### Run only API tests
mvn test -Dgroups="api"

### Run only UI tests
mvn test -Dgroups="ui"

## Test Strategy

The suite is structured around a layered architecture that separates concerns from infrastructure to assertion:
**Utilities → Config → Components → Page Objects → Services → AOP → Validator → API Clients → DTOs → Scenarios → Tests**.

**API layer (REST Assured + JUnit 5)**
Restful Booker is covered with a full CRUD lifecycle: authentication, create, read, update, and delete. Authentication is handled by a thread-safe `AuthService` backed by `ThreadLocal`, ensuring each parallel worker fetches and reuses its own token without contention. A custom JUnit 5 extension (`BookingTestExtension`) initialises the token lazily before each test and cleans up after the class completes. GraphQL (Hygraph) is tested at both the happy-path level (pagination, field selection, nested types) and the error boundary (malformed queries, non-existent fields, missing IDs), since GraphQL returns HTTP 200 even for schema violations — explicit error-body assertions are essential here.

**UI layer (Playwright + JUnit 5)**
Page objects expose atomic actions only (`clickAdd`, `fillForm`, `search`); multi-step business flows live in a separate Scenarios layer. This keeps page objects stable under UI churn while scenarios remain readable. DataFaker generates realistic, varied test data per run. Parameterised tests with `@MethodSource` and named `record` arguments cover junior, mid-level, and senior data profiles in a single test method, avoiding copy-paste test duplication.

**Parallelism**
Test classes run concurrently via `junit-platform.properties`; methods within each UI class run sequentially (`@Execution(SAME_THREAD)`) to avoid shared `Page` field races. The browser instance is shared per class (`@TestInstance(PER_CLASS)`) and a fresh `BrowserContext` is created per test, giving full isolation without the cost of launching a new browser for every method.

**Prioritisation**
Correctness and isolation were prioritised over coverage breadth: each test is independent, deterministic, and leaves no side effects. Infrastructure concerns (auth, reporting, browser lifecycle) are handled in base classes and extensions so test methods express intent only.

## Challenges & Solutions

**Thread-safe authentication across parallel tests**
Running `BookingCrudTest` in parallel required each thread to hold its own token without re-authenticating on every test method. The initial approach stored the token in a static field, which is a shared mutable state anti-pattern. The solution was a `ThreadLocal<String>` inside `AuthService` with lazy initialisation: the token is fetched once per thread on first use and reused for all subsequent tests on that thread. A JUnit 5 `AfterAllCallback` in `BookingTestExtension` cleans it up after the class finishes, avoiding leaks without interfering with the null-check guard.

**Screenshot capture after context is already closed**
JUnit 5 calls `TestWatcher` callbacks (`testFailed`, `testSuccessful`) after `@AfterEach` has run. Closing the `BrowserContext` in `@AfterEach` meant the page was already gone by the time the watcher tried to capture a screenshot. The fix was to remove `closePage()` entirely and let `ScreenshotWatcher` own the full context lifecycle — saving the screenshot and trace on failure, then closing; or discarding the trace and closing on success.

**GraphQL errors hidden behind HTTP 200**
GraphQL always returns `200 OK`, even for malformed queries or schema violations. Asserting on the HTTP status code alone would have made the negative tests pass incorrectly. The `ApiValidator` explicitly inspects the `errors` array in the response body, which is the only reliable signal for GraphQL failures.

**UI parallelism vs. shared page field**
Enabling class-level parallel execution with `@TestInstance(PER_CLASS)` introduced a race condition: concurrent test methods within the same class competed over the shared `page` and `webTablesPage` fields. Adding `@Execution(SAME_THREAD)` to `BaseUiTest` enforces sequential method execution per class while keeping class-level concurrency intact.

## What I Would Add With More Time

1. **Centralised REST Assured client** — introduce a single `RestAssuredClient` as the global HTTP entry point, then build per-service clients on top of it (e.g. `BookingApiClient`, `GraphQLApiClient`) each mapping directly to the endpoints described in the service documentation. This eliminates scattered `RestAssured.given()` calls and makes base-URL or auth changes a one-line fix.

2. **Replace Allure with a maintained reporter** — Allure is governed by Yandex, which introduces supply chain risk in enterprise environments. The current Playwright trace + Surefire HTML setup is a lightweight alternative; for richer reporting, other open-source or paid tools can be chosen.

3. **Replace placeholder-based locators** — locators such as `page.getByPlaceholder("Last Name")` are tied to visible UI text and break under internationalisation or copy changes. Stable `data-testid` attributes or ARIA roles should be negotiated with the development team and used instead.

4. **Dedicated steps / data-provider layer** — helper methods like `fetchMovies()`, inline record construction, and data manipulation currently live inside test classes. Extracting these into a separate `steps` or `data-provider` layer would improve reuse, readability, and make test methods express intent rather than mechanics.

5. **Service registry class in the test package** — a dedicated configuration class that wires and exposes all test-scoped services (e.g. `BookingService`, `GraphQLService`) would remove `@Autowired` scatter across individual test classes and provide a single place to manage test dependencies.

6. **Context-based data injection** — test data (names, emails, dates) is currently generated inline inside test classes. Moving generation into a `TestContextProvider` or data-builder that populates a context object before each test would decouple data creation from assertion logic and simplify parameterisation.

7. **Data storage for dependent dropdowns** — the State → City mapping in `FormTest` is hardcoded in a `switch` block. Externalising it to a JSON fixture or enum-backed data store would make it easier to extend and less prone to silent drift when the UI adds new options.

8. **Environment switching via Maven profiles** — base URLs, timeouts, and credentials are currently fixed to one environment. Defining Maven profiles (`-Pstaging`, `-Pprod`) that override `application.properties` values would allow the same suite to target any environment without code changes.

9. **Test suite strategy (Smoke, Regression, Feature)** — currently all tests share a single `api` or `ui` tag. A richer tagging model would introduce named suites: `@Tag("smoke")` for a fast, high-confidence subset run on every deployment to catch critical regressions within minutes; `@Tag("regression")` for the full suite scheduled nightly or pre-release; and `@Tag("feature")` scoped to the area under active development and run on every feature branch PR. Combined with Maven profiles or CI matrix jobs, each suite could target the appropriate environment, parallelism level, and failure threshold independently.