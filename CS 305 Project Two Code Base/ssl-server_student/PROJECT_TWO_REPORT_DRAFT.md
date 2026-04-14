# Practices for Secure Software Report

## Developer

Chas Stevens

## 1. Algorithm Cipher

I recommend using AES-256 as the primary encryption cipher for Artemis Financial's web application traffic, delivered through TLS 1.3. AES is the current industry standard symmetric cipher for protecting data in transit and at rest because it is fast, well studied, broadly supported, and approved for high-value business and government environments. In this project, I used HTTPS to protect the connection channel and SHA-256 to generate a checksum that verifies whether transferred data has been modified.

AES-256 uses a 256-bit symmetric key, which provides a strong security margin against brute-force attacks. TLS 1.3 uses strong random numbers during the handshake to create short-lived session keys, and it combines symmetric encryption with asymmetric cryptography. The asymmetric portion is used during certificate-based identity verification and key exchange, while the faster symmetric session key is used for the actual encrypted communication. For integrity verification, SHA-256 produces a 256-bit digest, which makes accidental collisions extremely unlikely for this assignment's use case and is appropriate for checksum validation.

Historically, older algorithms such as DES and 3DES became too weak or too slow for modern systems, and older protocol versions such as SSL and early TLS releases introduced avoidable security risk. AES replaced those older block ciphers as the accepted standard, while TLS 1.3 removed weaker negotiation options and legacy cryptographic behavior. Today, the recommended approach is to use modern TLS with strong certificates for communication confidentiality and authenticity, combined with secure hashing such as SHA-256 for integrity checks. That approach aligns with Artemis Financial's requirement to protect client financial data and verify transferred information.

## 2. Certificate Generation

I generated a self-signed PKCS12 certificate for local HTTPS testing by using the Java `keytool` utility. The certificate uses the alias `artemis-localhost`, includes `localhost` and `127.0.0.1` as subject alternative names, and is stored in the project as `src/main/resources/artemis-keystore.p12`. I also exported the certificate as `artemis-localhost.cer` so it can be reviewed and used as the required screenshot artifact in the report.

For the report screenshot, use the `artemis-localhost.cer` file in the project root. This is the exported certificate file requested in the rubric.

## 3. Deploy Cipher

I refactored the application to add a dedicated checksum workflow instead of leaving the original placeholder comment in the main application class. The new `ChecksumService` computes a SHA-256 digest and verifies it with a constant-time comparison, while `HashController` exposes a `/hash` endpoint that displays the client name, unique data string, algorithm, resulting checksum, and verification result.

To demonstrate functionality, I verified the endpoint with the secure URL `https://localhost:8443/hash?name=Chas%20Stevens&data=CS305-Unique-2026-04-13`. The application returned the SHA-256 checksum `9ddb3b0d46ab69ae1cc87ca9d093f1d69142a1870300b0a27e759ab9860eec39` and a verification result of `PASS`. That confirms the refactored code performs checksum generation and verification successfully.

## 4. Secure Communications

I converted the application from insecure HTTP placeholders to a working HTTPS configuration in `application.properties`. The server now runs on port `8443`, loads the PKCS12 keystore from the classpath, and uses the generated certificate alias to establish a secure TLS connection. This satisfies Artemis Financial's requirement to use secure communications for client financial data.

I compiled and ran the refactored application, then verified secure communication with `https://localhost:8443/hash`. A request to the HTTPS endpoint returned a successful `HTTP/1.1 200` response and the checksum verification page over TLS, which confirms the secure channel is active and functioning.

## 5. Secondary Testing

After refactoring, I ran OWASP Dependency-Check against the updated project to verify that the new code and upgraded dependencies did not introduce known dependency vulnerabilities. The report generated successfully at `target/dependency-check-report.html`.

The final Dependency-Check summary reported:

- Dependencies Scanned: 39
- Unique Dependencies: 18
- Vulnerable Dependencies: 0
- Vulnerabilities Found: 0
- Vulnerabilities Suppressed: 0

This result shows the refactored project did not introduce known third-party dependency vulnerabilities during the security enhancement work.

## 6. Functional Testing

I manually reviewed the original starter project and identified several issues:

- The application had placeholder SSL properties instead of a valid HTTPS configuration.
- The checksum route required by the assignment had not been implemented.
- The project depended on an outdated Spring Boot parent and an outdated Dependency-Check plugin version.
- The original structure placed security behavior in a single class with no separation between application startup and checksum logic.

I addressed those problems by upgrading the framework, generating a keystore and certificate, implementing checksum generation and verification, and adding automated tests. I then ran `mvn clean test`, which completed successfully with five passing tests and no errors. That verifies the refactored code is syntactically correct, logically consistent, and operational.

## 7. Summary

I refactored the Artemis Financial application by upgrading the outdated framework dependencies, enabling HTTPS, generating a self-signed certificate, adding SHA-256 checksum verification, and running both functional and secondary security testing. These changes directly addressed the key security areas identified in the vulnerability assessment workflow: secure communications, software composition risk, integrity verification, and code-level secure design.

My process for adding layers of security started with removing the insecure placeholders and obsolete dependencies. I then created a certificate-backed HTTPS configuration, separated checksum behavior into focused classes, escaped user-controlled values before returning HTML, and verified the results through unit tests, endpoint checks, packaging, and OWASP Dependency-Check scanning. The result is a more maintainable application with stronger transport security, better integrity verification, and cleaner security testing evidence.

## 8. Industry Standard Best Practices

I applied several industry standard secure coding practices during the refactor. I upgraded the project to a modern supported Spring Boot release, removed an unnecessary starter dependency to reduce attack surface, used HTTPS with a PKCS12 keystore, selected SHA-256 for integrity checking, escaped reflected user input before rendering HTML, and separated responsibilities into dedicated classes for easier maintenance and testing. I also added automated tests and ran Dependency-Check to verify that dependency security remained intact.

These practices protect the application's existing security posture and improve Artemis Financial's overall well-being. Secure defaults, supported dependencies, transport encryption, integrity validation, and repeatable testing reduce the chance of data exposure, tampering, and avoidable production incidents. For a financial services client, those controls help preserve customer trust, support regulatory expectations, and reduce the business impact of security failures.
