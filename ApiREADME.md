# Enterprise JMeter API Testing Framework
[![Version](https://img.shields.io/badge/version-2.0.0-blue)]()
[![JMeter](https://img.shields.io/badge/JMeter-5.6.3-red)]()
[![License](https://img.shields.io/badge/license-MIT-green)]()

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Technical Specifications](#technical-specifications)
- [Framework Components](#framework-components)
- [Test Plan Structure](#test-plan-structure)
- [Installation & Setup](#installation--setup)
- [Execution Guide](#execution-guide)
- [Performance Metrics](#performance-metrics)
- [Monitoring & Reporting](#monitoring--reporting)
- [Best Practices](#best-practices)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## Overview

The Enterprise JMeter API Testing Framework is a robust performance testing solution designed for scalable API validation and load testing. This framework implements industry-standard practices for continuous performance monitoring and validation of RESTful services.

## Architecture

```
┌─────────────────────────────────────┐
│           Test Controller           │
├─────────────────────────────────────┤
│         Thread Management           │
├─────────────────────────────────────┤
│    Request Configuration Layer      │
├─────────────────────────────────────┤
│      Data Extraction & Logic        │
├─────────────────────────────────────┤
│      Assertion & Validation         │
├─────────────────────────────────────┤
│     Reporting & Visualization       │
└─────────────────────────────────────┘
```

## Technical Specifications

### Core Components
- **JMeter Version:** 5.6.3+
- **Java Runtime:** JRE 8+
- **Plugins:** Standard Set + Custom Plugins

### Supporting Tools
- **JMeter Plugins Manager:** 1.7+
- **Custom Developed Utilities**
- **Results Analysis Tools**

## Framework Components

### 1. Test Elements
- **Thread Groups**
  - Configurable user load simulation
  - Parameterized ramp-up periods
  - Custom thread scheduling

- **Samplers**
  ```
  ├── HTTP Requests
  │   ├── GET Operations
  │   ├── POST Operations
  │   ├── PUT Operations
  │   └── DELETE Operations
  ├── Debug Samplers
  └── JSR223 Samplers
  ```

- **Configuration Elements**
  ```
  ├── HTTP Header Manager
  ├── HTTP Cookie Manager
  ├── HTTP Cache Manager
  └── User Defined Variables
  ```

### 2. Logic Controllers
- **Flow Controllers**
  - If Controller
  - While Controller
  - ForEach Controller
  - Transaction Controller

### 3. Listeners
- **Primary Listeners**
  - Aggregate Report
  - Summary Report
  - View Results Tree
  - Response Time Graph

## Test Plan Structure

```
Root
├── Thread Group
│   ├── HTTP Request Defaults
│   ├── User Authentication
│   │   ├── Login Request
│   │   └── Token Extraction
│   ├── API Tests
│   │   ├── GET Requests
│   │   ├── POST Requests
│   │   └── DELETE Requests
│   ├── Assertions
│   │   ├── Response Assertions
│   │   └── JSON Assertions
│   └── Reporting Listeners
└── Test Data
```

## Installation & Setup

### Prerequisites
```bash
# Java 8+
java -version

# JMeter 5.6.3+
jmeter -v

# Available Memory
free -m  # Linux
Get-CimInstance Win32_OperatingSystem | Select FreePhysicalMemory  # Windows
```

### Installation Steps

1. **JMeter Installation**
```bash
# Download JMeter
wget https://downloads.apache.org/jmeter/binaries/apache-jmeter-5.6.3.tgz

# Extract
tar -xzf apache-jmeter-5.6.3.tgz

# Set JMETER_HOME
export JMETER_HOME=/path/to/apache-jmeter-5.6.3
export PATH=$JMETER_HOME/bin:$PATH
```

2. **Plugin Installation**
   - Launch JMeter Plugin Manager
   - Install required plugins:
     - Custom Thread Groups
     - 3 Basic Graphs
     - JSON Plugins
     - HTTP/2 Sampler

## Execution Guide

### Command Line Execution
```bash
# Basic Execution
jmeter -n -t TestPlan.jmx -l results.jtl

# With Custom Properties
jmeter -n -t TestPlan.jmx -l results.jtl -Jthreads=50 -Jrampup=30

# Distributed Testing
jmeter -n -t TestPlan.jmx -R remote-host1,remote-host2 -l results.jtl
```

### Configuration Parameters
```properties
# user.properties
thread.count=100
ramp.up=30
hold.time=300
target.rpm=1000
```

## Performance Metrics

### Key Performance Indicators (KPIs)
- Response Time (90th percentile)
- Throughput (requests/second)
- Error Rate
- Concurrent Users

### Acceptance Criteria
```
Response Time: P90 < 1000ms
Error Rate: < 1%
Throughput: > 100 rps
```

## Monitoring & Reporting

### Real-time Monitoring
- Active Threads Graph
- Response Times Over Time
- Transactions per Second

### Report Generation
```bash
# Generate HTML Report
jmeter -g results.jtl -o /path/to/report/directory

# Generate Dashboard
jmeter -g results.jtl -o /path/to/dashboard -e
```

## Best Practices

### 1. Test Design
- Use appropriate thread groups
- Implement proper think times
- Include reasonable assertions
- Handle dynamic data properly

### 2. Performance Optimization
```
# JMeter Properties Optimization
server.rmi.ssl.disable=true
CookieManager.save.cookies=false
ViewResultsTree.max_size=0
```

### 3. Resource Management
- Clear test data between iterations
- Implement proper cleanup
- Monitor resource usage

## Troubleshooting

### Common Issues
1. **Memory Issues**
   ```bash
   # Increase Heap Size
   export JVM_ARGS="-Xms1g -Xmx2g"
   ```

2. **Connection Issues**
   - Verify network connectivity
   - Check firewall settings
   - Validate SSL certificates

### Debug Procedures
1. Enable Debug Sampler
2. Check jmeter.log
3. Analyze thread dumps if needed

## Contributing

### Development Workflow
1. Fork repository
2. Create feature branch
```bash
git checkout -b feature/PERF-123-description
```
3. Commit changes
```bash
git commit -m "feat(api): add new performance metrics"
```
4. Submit pull request

### Code Review Process
- Technical review
- Performance impact assessment
- Documentation update verification

---

## License
MIT License - See [LICENSE.md](LICENSE.md) for details

## Support
For technical support:
- Create an issue in the repository
- Email: performance-testing@yourcompany.com

---

*Last Updated: November 17, 2024*  
*Author: Gupta Mohit*  
*Version: 2.0.0*
