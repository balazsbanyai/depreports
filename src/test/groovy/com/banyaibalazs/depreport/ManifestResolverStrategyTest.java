package com.banyaibalazs.depreport;

import com.google.common.base.Optional;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ManifestResolverStrategyTest {

    ManifestResolverStrategy strategy;

    @Before
    public void setUp() throws Exception {
        strategy = new ManifestResolverStrategy();
    }

    @Test
    public void null_returnsOptional() throws Exception {
        Object opt = strategy.resolve(null);

        assertTrue(opt instanceof Optional);
    }

    @Test
    public void null_returnsOptionalThatAbsent() throws Exception {
        Optional<LicenseData> opt = strategy.resolve(null);

        assertFalse(opt.isPresent());
    }
}