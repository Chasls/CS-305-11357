package com.snhu.sslserver;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import org.springframework.stereotype.Service;

@Service
public class ChecksumService {

	private static final String HASH_ALGORITHM = "SHA-256";

	public ChecksumResult createVerifiedChecksum(String data) {
		String checksum = calculateChecksum(data);
		boolean verified = verifyChecksum(data, checksum);
		return new ChecksumResult(HASH_ALGORITHM, data, checksum, verified);
	}

	public String calculateChecksum(String data) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
			byte[] hashBytes = messageDigest.digest(data.getBytes(StandardCharsets.UTF_8));
			return HexFormat.of().formatHex(hashBytes);
		} catch (NoSuchAlgorithmException exception) {
			throw new IllegalStateException("Required hash algorithm is unavailable.", exception);
		}
	}

	public boolean verifyChecksum(String data, String expectedChecksum) {
		byte[] calculatedChecksum = hexToBytes(calculateChecksum(data));
		byte[] providedChecksum = hexToBytes(expectedChecksum);
		return MessageDigest.isEqual(calculatedChecksum, providedChecksum);
	}

	private byte[] hexToBytes(String checksum) {
		return HexFormat.of().parseHex(checksum);
	}
}
