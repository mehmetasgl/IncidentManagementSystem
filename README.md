# SE322-Section1-3

Java Incident Management System with automated tests for validating incident creation, tracking, and management operations.

# System Requirements Document

## Functional Requirements

| REQ. # | Description | Priority | Status |
|--------|-------------|----------|--------|
| 1 | Users should be able to log in to the system with predefined ID information. | High | [x] |
| 2 | Users should be able to create a new incident report. | High | [x] |
| 3 | Users should be able to type the incident description/definition. | High | [x] |
| 4 | Users should be able to select the status of the incident (Started, In Progress, Done, Delayed). | High | [x] |
| 5 | Users should be able to select the priority of the incident (Low, Medium, Important, Urgent). | High | [x] |
| 6 | Users should be able to select the start and end date of the incident if necessary. | Moderate | [ ] |
| 7 | Users can edit/delete the existing incident report if necessary. | Moderate | [ ] |
| 8 | Users should be able to upload documents to the incident report if necessary. | Moderate | [ ] |
| 9 | Users can list incidents on that date by typing a date. | Low | [ ] |
| 10 | Users can see/review previous incident posts. | Low | [] |

## Non-Functional Requirements

| REQ. # | Description | Priority/Version | Status |
|--------|-------------|------------------|--------|
| 1 | The system must be able to support at least 500 concurrent users. | High | [ ] |
| 2 | Response time must be less than 1 second. | High | [ ] |
| 3 | The system must be available 24/7. | High | [ ] |
| 4 | The system should have an easy-to-understand interface for all age groups (young, middle, old). | Low | [ ] |
| 5 | The system should provide data security with end-to-end encryption. | Moderate | [ ] |
| 6 | The system have PostgreSQL database for hiding user information. | Moderate | [ ] |
