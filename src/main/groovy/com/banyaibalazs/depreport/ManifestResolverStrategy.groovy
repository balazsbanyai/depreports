package com.banyaibalazs.depreport

import java.util.jar.JarFile

public class ManifestResolverStrategy implements LicenseResolverStrategy {

    @Override
    public Optional<LicenseData> resolve(File dependency) {

        def result = null;

        try {
            new JarFile(dependency).manifest.mainAttributes.each { entry ->


                def candidate = entry.key.toString()
                if (candidate.equalsIgnoreCase("Bundle-License")) {
                    result = new LicenseData(entry.value)
                }
            }
        } catch (Exception e) {

        }

        return Optional.ofNullable(result)
    }

}
