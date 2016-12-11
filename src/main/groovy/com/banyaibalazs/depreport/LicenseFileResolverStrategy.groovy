package com.banyaibalazs.depreport

import org.gradle.api.Project

class LicenseFileResolverStrategy implements LicenseResolverStrategy {

    Project project;

    LicenseFileResolverStrategy(Project project) {
        this.project = project
    }

    @Override
    public Optional<LicenseData> resolve(File dependency) {

        def result = null;

        project.zipTree(dependency).each { file ->
            if (file.name.endsWith("LICENSE")) {
                result = file.text
                result = result.findAll('(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?')
                result = result.last()
                result = new LicenseData(result)
            }
        }


        return Optional.ofNullable(result)
    }

}
