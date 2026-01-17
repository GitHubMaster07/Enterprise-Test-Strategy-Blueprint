# Functional Testing Layers

This document defines the functional testing layers used across the system.  
Each layer has a clear purpose, scope, coverage expectations, best practices, and anti‑patterns.  
The goal is to ensure deterministic, maintainable, scalable, and audit‑ready test coverage.

---

## 1. Unit Testing

### 1.1 Purpose
Validate individual functions, classes, and components in isolation without external dependencies.

### 1.2 Scope
- Business logic
- Utility functions
- Data transformations
- Validation rules
- Error handling

### 1.3 Coverage Expectations
- ≥ 80% line coverage
- 100% coverage for critical logic
- Zero external calls (no DB, no API, no network)

### 1.4 Best Practices
- Use mocks/stubs for all dependencies
- Fast execution (< 1 second per test)
- Deterministic behavior
- No shared state

### 1.5 Anti‑Patterns
- Calling real APIs or DB
- Using sleeps or time‑dependent logic
- Testing multiple components in one test

---

## 2. API Testing

### 2.1 Purpose
Validate service behavior, business rules, and data contracts at the API layer.

### 2.2 Scope
- Request/response validation
- Business logic validation
- Authentication & authorization
- Error handling
- Schema validation
- Contract enforcement

### 2.3 Coverage Expectations
- All critical endpoints covered
- Positive + negative + edge cases
- JSON schema validation for all responses
- Contract tests for all consumer‑facing APIs

### 2.4 Best Practices
- Use RestAssured with reusable request specs
- Validate status codes, headers, payloads
- Validate schemas for all responses
- Use payload builders for request bodies
- Use WireMock for unstable dependencies

### 2.5 Anti‑Patterns
- UI‑driven API tests
- Hardcoded URLs or tokens
- Mixing API and DB logic in the same test

---

## 3. UI Testing

### 3.1 Purpose
Validate user workflows, visual behavior, and UI‑level business rules.

### 3.2 Scope
- Critical user journeys
- Form validation
- Navigation
- Visual confirmation (optional)
- Cross‑browser compatibility (if required)

### 3.3 Coverage Expectations
- Minimal UI coverage (≤ 10% of total tests)
- Only critical flows automated
- All non‑critical flows tested at API level

### 3.4 Best Practices
- Page Object Model (POM)
- Stable locators (data‑test‑id)
- Explicit waits, no sleeps
- Screenshots on failure
- Parallel execution with ThreadLocal WebDriver

### 3.5 Anti‑Patterns
- Over‑automating UI
- Using XPath for everything
- Embedding assertions inside Page Objects
- Flaky waits or sleeps

---

## 4. Database Testing

### 4.1 Purpose
Validate data integrity, persistence, and transactional behavior.

### 4.2 Scope
- CRUD operations
- Referential integrity
- Data transformations
- Transaction boundaries
- Schema validation

### 4.3 Coverage Expectations
- Validate DB state after API/UI actions
- Use read‑only queries in tests
- Use parameterized queries

### 4.4 Best Practices
- ThreadLocal DB connections
- SQL in separate query files
- DB validators for reusable checks
- No destructive operations in tests

### 4.5 Anti‑Patterns
- Writing data directly to DB to simulate flows
- Hardcoding SQL inside tests
- Using DB tests as integration tests

---

## 5. Integration Testing

### 5.1 Purpose
Validate interactions between multiple services, components, or layers.

### 5.2 Scope
- API → DB
- UI → API → DB
- Service → service interactions
- External dependency flows (via WireMock)

### 5.3 Coverage Expectations
- All critical cross‑service flows
- All integrations with external systems
- All error propagation paths

### 5.4 Best Practices
- Use WireMock for unstable dependencies
- Validate DB state after API calls
- Validate event emission (if applicable)
- Use contract tests to reduce integration failures

### 5.5 Anti‑Patterns
- Using real external systems in CI
- Over‑testing UI in integration flows
- Mixing integration and E2E logic

---

## 6. End-to-End Testing

### 6.1 Purpose
Validate real user journeys across the entire system, including UI, API, DB, and events.

### 6.2 Scope
- Full business workflows
- Multi‑step user scenarios
- Cross‑service orchestration
- Realistic data flows

### 6.3 Coverage Expectations
- Only the most critical flows (≤ 5% of total tests)
- One E2E test per major business capability
- All other coverage handled at lower layers

### 6.4 Best Practices
- Use API calls for setup/teardown
- Minimize UI interactions
- Validate DB and event outputs
- Capture full evidence (screenshots, logs, payloads)

### 6.5 Anti‑Patterns
- Using E2E tests as regression suite
- Heavy UI usage
- Long, brittle workflows
- Environment‑dependent logic

---

## 7. Contract Testing

### 7.1 Purpose
Ensure backward‑compatible communication between services using consumer‑driven contracts.

### 7.2 Scope
- Request/response structure
- Required fields
- Data types
- Backward compatibility rules

### 7.3 Coverage Expectations
- All consumer → provider interactions covered
- All breaking changes detected before integration

### 7.4 Best Practices
- Use Pact for consumer tests
- Publish contracts to Pact Broker
- Verify provider in CI
- Enforce versioning rules

### 7.5 Anti‑Patterns
- Using contract tests as API tests
- Ignoring backward compatibility
- Hardcoding provider URLs

---

## 8. Event-Driven Testing

### 8.1 Purpose
Validate event production, consumption, schema compliance, and processing logic.

### 8.2 Scope
- Kafka/SQS event emission
- Consumer processing logic
- Schema registry validation
- DLQ handling
- Ordering and idempotency

### 8.3 Coverage Expectations
- All critical event flows covered
- Schema validation for all events
- DLQ validation for failure scenarios

### 8.4 Best Practices
- Use embedded Kafka or test containers
- Validate event payloads against schemas
- Validate offsets and consumer groups
- Validate idempotency and retries

### 8.5 Anti‑Patterns
- Using production Kafka in CI
- Hardcoding offsets
- Ignoring DLQ behavior

---
