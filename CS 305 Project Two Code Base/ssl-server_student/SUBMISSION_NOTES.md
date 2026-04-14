# Submission Notes

## Primary Artifacts

- Refactored project root: `ssl-server_student`
- Exported certificate: `artemis-localhost.cer`
- HTTPS keystore: `src/main/resources/artemis-keystore.p12`
- Checksum response captured from HTTPS endpoint: `hash-response.html`
- HTTPS response headers and page capture: `hash-response.txt`
- Functional test output: `functional-test-output.txt`
- Dependency-Check report: `target/dependency-check-report.html`
- Report draft text: `PROJECT_TWO_REPORT_DRAFT.md`

## Commands Used

- Tests: `.\mvnw.cmd clean test`
- Package: `.\mvnw.cmd package -DskipTests`
- Dependency-Check: `.\mvnw.cmd org.owasp:dependency-check-maven:12.1.0:check -DautoUpdate=false -DossindexAnalyzerEnabled=false -DknownExploitedEnabled=false -DnodeAnalyzerEnabled=false -DnodeAuditAnalyzerEnabled=false -DretireJsAnalyzerEnabled=false -DversionCheckEnabled=false`
- HTTPS verification URL: `https://localhost:8443/hash?name=Chas%20Stevens&data=CS305-Unique-2026-04-13`

## Screenshot Targets for the Word Template

- `Certificate Generation`: open `artemis-localhost.cer`
- `Deploy Cipher`: open `https://localhost:8443/hash?name=Chas%20Stevens&data=CS305-Unique-2026-04-13`
- `Secure Communications`: open `https://localhost:8443/hash`
- `Secondary Testing`: open `target/dependency-check-report.html` and `functional-test-output.txt`
- `Functional Testing`: open `functional-test-output.txt`

## Dependency-Check Result

- Report generated on April 13, 2026 at 9:50:58 PM CDT
- Vulnerable Dependencies: 0
- Vulnerabilities Found: 0
