package com.banyaibalazs.depreport

import com.google.common.base.Optional

public interface LicenseResolverStrategy {
    Optional<LicenseData> resolve(File dependency)
}