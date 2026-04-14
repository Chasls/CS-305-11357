package com.snhu.sslserver;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HashController {

	private static final String DEFAULT_NAME = "Artemis Financial";
	private static final String DEFAULT_DATA = "Retirement-Plan-Transfer-2026";

	private final ChecksumService checksumService;

	public HashController(ChecksumService checksumService) {
		this.checksumService = checksumService;
	}

	@GetMapping(value = "/hash", produces = MediaType.TEXT_HTML_VALUE)
	public String hash(
			@RequestParam(defaultValue = DEFAULT_NAME) String name,
			@RequestParam(defaultValue = DEFAULT_DATA) String data) {

		String verificationSubject = name + " | " + data;
		ChecksumResult result = checksumService.createVerifiedChecksum(verificationSubject);

		return """
				<!DOCTYPE html>
				<html lang="en">
				<head>
					<meta charset="UTF-8">
					<meta name="viewport" content="width=device-width, initial-scale=1.0">
					<title>Artemis Financial Checksum Verification</title>
					<style>
						body { font-family: "Segoe UI", Arial, sans-serif; margin: 2rem; background: #f3f7fb; color: #1d2a38; }
						main { max-width: 56rem; margin: 0 auto; background: #ffffff; padding: 2rem; border-radius: 16px; box-shadow: 0 16px 40px rgba(14, 37, 66, 0.12); }
						h1 { margin-top: 0; color: #0d3b66; }
						p { line-height: 1.6; }
						.label { font-weight: 700; display: inline-block; min-width: 10rem; }
						.code { font-family: Consolas, "Courier New", monospace; overflow-wrap: anywhere; }
						.status-ok { color: #0a7a2f; font-weight: 700; }
					</style>
				</head>
				<body>
					<main>
						<h1>Checksum Verification</h1>
						<p>This page is served over HTTPS and verifies that Artemis Financial transfer data remains unchanged.</p>
						<p><span class="label">Client Name:</span> %s</p>
						<p><span class="label">Unique Data:</span> <span class="code">%s</span></p>
						<p><span class="label">Combined Input:</span> <span class="code">%s</span></p>
						<p><span class="label">Algorithm:</span> %s</p>
						<p><span class="label">Checksum:</span> <span class="code">%s</span></p>
						<p><span class="label">Verification Result:</span> <span class="status-ok">%s</span></p>
					</main>
				</body>
				</html>
				"""
				.formatted(
						htmlEscape(name),
						htmlEscape(data),
						htmlEscape(result.data()),
						htmlEscape(result.algorithm()),
						htmlEscape(result.checksum()),
						result.verified() ? "PASS" : "FAIL");
	}

	private String htmlEscape(String value) {
		return value
				.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"", "&quot;")
				.replace("'", "&#39;");
	}
}
