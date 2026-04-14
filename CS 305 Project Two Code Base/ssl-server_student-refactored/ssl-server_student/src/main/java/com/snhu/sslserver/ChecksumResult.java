package com.snhu.sslserver;

public record ChecksumResult(String algorithm, String data, String checksum, boolean verified) {
}
