# CS-305-11357

Artemis Financial was the client for this project. They needed stronger software security for a financial web application that handles sensitive customer data. The main goal was to identify vulnerabilities in the software and improve the security of the application and its communications.

I think I did well at finding both coding issues and dependency-related risks in the application. I identified problems like hardcoded credentials, missing access control, weak input handling, information leakage through stack traces, and outdated libraries with known vulnerabilities. Coding securely matters because it protects sensitive data, lowers risk, and helps a company maintain trust and stability.

One of the more challenging parts of the vulnerability assessment was seeing how multiple security issues could connect across the application. At the same time, that was also one of the most useful parts because it showed me that software security is about layers, not just one fix.

I increased security by upgrading outdated dependencies, enabling HTTPS, generating a certificate, adding SHA-256 checksum verification, escaping user-controlled values, and running OWASP Dependency-Check. In the future, I would keep using code review, dependency scanning, and security testing tools to assess vulnerabilities and choose the right mitigation steps.

To make sure the application was still functional and secure, I tested the refactored code, verified the secure HTTPS connection, checked the checksum behavior, and reviewed the dependency-check results. After refactoring, I used both functional testing and security testing to make sure I did not introduce new problems.

Some of the main tools and practices I used were Spring Boot, HTTPS configuration, self-signed certificates, SHA-256 hashing, OWASP Dependency-Check, dependency upgrades, input escaping, and secure coding practices. These are all things I can carry into future projects.

This is something I could show future employers as an example of vulnerability assessment, secure coding, refactoring, and testing. It shows that I can identify risks, improve software security, and document the work clearly.
