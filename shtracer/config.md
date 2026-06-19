# Traceability configuration

## Requirement
* **PATH**: "../docs/requirements.md"
  * **BRIEF**: "Hierarchical, labelled requirements."
  * **TAG FORMAT**: `@REQ[0-9\.]+@`
  * **TAG LINE FORMAT**: `<!--.*-->`
  * **TAG-TITLE OFFSET**: 1

## Architecture
* **PATH**: "../docs/architecture.md"
  * **BRIEF**: "Architecture, traced to requirements."
  * **TAG FORMAT**: `@ARC[0-9\.]+@`
  * **TAG LINE FORMAT**: `<!--.*-->`
  * **TAG-TITLE OFFSET**: 1

## Implementation
* **PATH**: "../src/main/java/"
  * **EXTENSION FILTER**: "*.java"
  * **TAG FORMAT**: `@IMP[0-9\.]+@`
  * **TAG LINE FORMAT**: `//.*`
  * **BRIEF**: "Production Java code, traced to architecture."

## Unit test
* **PATH**: "../src/test/java/"
  * **EXTENSION FILTER**: "*.java"
  * **IGNORE FILTER**: "*Integration*|*IT*"
  * **TAG FORMAT**: `@UT[0-9\.]+@`
  * **TAG LINE FORMAT**: `//.*`
  * **BRIEF**: "Unit tests, traced to requirements."

## Integration test
* **PATH**: "../src/test/java/"
  * **EXTENSION FILTER**: "*.java"
  * **TAG FORMAT**: `@IT[0-9\.]+@`
  * **TAG LINE FORMAT**: `//.*`
  * **BRIEF**: "Integration / end-to-end tests, traced to requirements."
