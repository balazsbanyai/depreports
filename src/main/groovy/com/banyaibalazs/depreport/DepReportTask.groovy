package com.banyaibalazs.depreport

import org.gradle.api.DefaultTask
import org.gradle.api.reporting.Reporting
import org.gradle.api.tasks.TaskAction

public class DepReportTask extends DefaultTask implements Reporting<DepReportContainer> {

    Set<LicenseResolverStrategy> resolversStrategies;

    public DepReportTask() {
        resolversStrategies = new HashSet<>()
        resolversStrategies.add(new ManifestResolverStrategy());
        resolversStrategies.add(new LicenseFileResolverStrategy(project));
    }

    @TaskAction
    public void action() {
        def sizeBytes = 0;
        def formatStr = "%,10.2f"

        project.configurations.default.collect { it.length() / (1024 * 1024) }.each { sizeBytes += it }

        def out = new StringBuffer()

        out << "***********************\n"
        out << "Size report for $project.name\n\n"

        out << "Total artifact size:".padRight(55)
        project.configurations.default.allArtifacts.files.each { artifact ->
            out << "${String.format(formatStr, artifact.length() / (1024 * 1024))} Mb\n\n"
        }

        out << 'Total dependencies size:'.padRight(55)
        out << "${String.format(formatStr, sizeBytes)} Mb\n\n"

        project.configurations
                .default
                .sort { -it.length() }
                .each {
            LicenseData license = resolveLicense(it)
            out << "${it.name}".padRight(45)
            out << "${license.name}".padRight(10)
            out << "${String.format(formatStr, (it.length() / 1024))} kb\n"


            //explainMe(it)
        }
        println(out)
    }

    LicenseData resolveLicense(File file) {


//        def hash = java.security.MessageDigest.getInstance("sha1").
//                digest(delegate.readBytes()).
//                collect{ String.format("%02x", it) }.
//                join()
//
//        def searchApiEndpoint = "http://search.maven.org/solrsearch/select?q=1:\"$hash\"&rows=20&wt=json"
//
//        URL apiUrl = new URL(searchApiEndpoint)
//        def result = new groovy.json.JsonSlurper().parseText(apiUrl.text)
//        if (result.response.numFound > 0) {
//            def id = result.response.docs.g.first()
//            def version = result.response.docs.v.first()
//            def name = result.response.docs.a.first()
//
//            def pomUrlEndpoint = "http://search.maven.org/remotecontent?filepath=${id.replace(".", "/")}/${name}/${version}/${name}-${version}.pom"
//            def pomUrlEndpointUrl = new URL(pomUrlEndpoint);
//
//            println pomUrlEndpointUrl.text
//            pomUrlEndpoint
//
//        } else {
//            "UNKNOWN"
//        }


        def result = new LicenseData()
        result.name = "UNKNOWN"

        resolversStrategies.each { resolver ->

            Optional<LicenseData> resolvedResult = resolver.resolve(file)

            try {

                if (resolvedResult.isPresent()) {
                    result = resolvedResult.get()
                }

            } catch (Exception e) {}
        }

        if (result.name == "UNKNOWN") {
            println file.path
        }

        return result
    }

    @Override
    DepReportContainer getReports() {
        return null
    }

    @Override
    DepReportContainer reports(Closure closure) {
        return null
    }
}