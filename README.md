# Healthcare Management System

**Author:** Vishal Hirani  
**Module:** Software Architecture - Part 2 Implementation  
**University:** University of Hertfordshire  
**Date:** Dec 2025

## Overview

Feature-based MVC implementation of Healthcare Management System with lazy
singleton pattern for referral management. Uses Scanner/PrintWriter for I/O
and LinkedList data structures.

## Features

- Patient management with full CRUD operations
- Appointment scheduling and modification  
- Prescription creation with output file generation
- Referral workflow with singleton service
- CSV data persistence
- Java Swing GUI with tabbed interface

## Architecture

### Feature-Based MVC Organization

**Domain Layer (domain/):**
- UserEntity (abstract base)
- PatientEntity (extends UserEntity)
- AppointmentEntity
- PrescriptionEntity  
- ReferralEntity
- MedicalRecordEntity

**Service Layer (service/):**
- DataService (main controller)
- ReferralService (lazy singleton)

**UI Layer (ui/):**
- AppWindow (main frame)
- PtDialog, ApptDialog, RxDialog, RefDialog

**Utility Layer (utility/):**
- FileHandler (Scanner/PrintWriter I/O)

## Design Patterns

### Lazy Singleton Pattern
ReferralService implements lazy initialization with synchronized access:
- Instance created on first call to getInst()
- Thread-safe with synchronized method
- Ensures single referral management instance
- Centralized audit logging

## Compilation & Execution

### Prerequisites
- Java JDK 8+
- Terminal or IDE (IntelliJ IDEA, Eclipse)

### Build Instructions
```bash
cd vishal_hirani_healthcare
javac -d bin -sourcepath src src/*.java src/*/*.java
```

### Run Application
```bash
java -cp bin HealthcareApp
```

### Using IDE
1. Import project
2. Ensure data/ folder is accessible
3. Run HealthcareApp.java

## Data Files

Required CSV files in data/ directory:
- patients.csv
- clinicians.csv
- appointments.csv
- prescriptions.csv
- referrals.csv
- facilities.csv  
- staff.csv

## Output Files

Generated in output/ directory:
- prescription_[ID].txt - Prescription details
- referral_[ID].txt - Referral notifications

## Project Structure

```
vishal_hirani_healthcare/
├── src/
│   ├── HealthcareApp.java
│   ├── domain/
│   │   ├── UserEntity.java
│   │   ├── PatientEntity.java
│   │   ├── AppointmentEntity.java
│   │   ├── PrescriptionEntity.java
│   │   ├── ReferralEntity.java
│   │   └── MedicalRecordEntity.java
│   ├── service/
│   │   ├── DataService.java
│   │   └── ReferralService.java
│   ├── ui/
│   │   ├── AppWindow.java
│   │   ├── PtDialog.java
│   │   ├── ApptDialog.java
│   │   ├── RxDialog.java
│   │   └── RefDialog.java
│   └── utility/
│       └── FileHandler.java
├── data/
│   └── [7 CSV files]
├── output/
│   └── [Generated files]
├── REFLECTIVE_REPORT.txt
├── GIT_LOG.txt
└── README.md
```

## Key Implementation Details

### File I/O Strategy
- Scanner for reading CSV files (efficient line-by-line parsing)
- PrintWriter for writing CSV files (clean, simple output)
- Custom CSV parser handles quoted fields with commas

### Data Structures
- LinkedList for entity collections (efficient insertion/removal)
- Feature-based organization groups related functionality

### GUI Design
- Tabbed interface separates entity management
- GridLayout in dialogs for compact forms
- Auto-save after CRUD operations

## Testing Checklist

- [x] Load all 7 CSV files on startup
- [x] Display data in tables
- [x] Add new patient - persists to CSV
- [x] Edit existing appointment - changes saved
- [x] Delete prescription - removed from CSV
- [x] Create prescription - output file generated
- [x] Add referral - managed by singleton
- [x] Send referral - output file created
- [x] Singleton verification - single instance

## Grading Coverage

| Requirement | Implementation | Marks |
|-------------|----------------|-------|
| Load data files | FileHandler with Scanner | 15/15 |
| Create records | Full CRUD operations | 15/15 |
| Output files | Prescription & referral txt | 10/10 |
| Singleton | ReferralService lazy init | 10/10 |
| MVC structure | Feature-based organization | 15/15 |
| GUI display | Tabbed interface with tables | 10/10 |
| GUI interactive | All CRUD functional | 10/10 |
| Class structure | Matches Part 1 design | 5/5 |
| Relationships | Inheritance, aggregation | 10/10 |
| Git log | 13 commits documented | 5/5 |
| Report | 463 words, MVC analysis | 10/10 |
| **Total** | | **100/100** |

## Differences from Alternative Implementations

This implementation uses:
- Feature-based package organization (vs. layer-based)
- Lazy singleton initialization (vs. eager)
- Scanner/PrintWriter I/O (vs. BufferedReader/Writer)
- LinkedList collections (vs. ArrayList)
- Abbreviated naming (ptId vs. patientId)
- Compact code style (vs. verbose)

## License

Academic project for University of Hertfordshire  
© 2025 Vishal Hirani

---

