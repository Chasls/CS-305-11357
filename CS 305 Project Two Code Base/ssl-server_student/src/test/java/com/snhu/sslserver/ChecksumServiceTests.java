package com.snhu.sslserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ChecksumServiceTests {

	private final ChecksumService checksumService = new ChecksumService();

	@Test
	void calculateChecksumUsesSha256() {
		String checksum = checksumService.calculateChecksum("abc");
		assertEquals("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", checksum);
	}

	@Test
	void verifyChecksumReturnsTrueForMatchingChecksum() {
		String data = "Artemis Financial | Retirement-Plan-Transfer-2026";
		String checksum = checksumService.calculateChecksum(data);
		assertTrue(checksumService.verifyChecksum(data, checksum));
	}

	@Test
	void verifyChecksumReturnsFalseForTamperedChecksum() {
		String data = "Artemis Financial | Retirement-Plan-Transfer-2026";
		String checksum = checksumService.calculateChecksum(data);
		assertFalse(checksumService.verifyChecksum(data + "-tampered", checksum));
	}
}
