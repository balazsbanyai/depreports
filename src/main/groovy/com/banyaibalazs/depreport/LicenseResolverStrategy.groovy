package com.banyaibalazs.depreport

public interface LicenseResolverStrategy {
    Optional<LicenseData> resolve(File dependency)
}