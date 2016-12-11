package com.banyaibalazs.depreport


import org.gradle.api.Project
import org.gradle.api.Plugin


class DepReportPlugin implements Plugin<Project> {

   void apply(Project project) {


   }

   
task size
size.doLast {
    def sizeBytes = 0;
    def formatStr = "%,10.2f"


    File.metaClass.getLicense = {

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

        def result = "UNKNOWN"
        zipTree(delegate).files.each { file ->
            if (file.name.endsWith("LICENSE")) {
                result = file.text
                result = result.findAll('(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?').last()
            }
        }


        result

    }

    configurations.default.collect { it.length() / (1024 * 1024) }.each { sizeBytes += it }

    def out = new StringBuffer()

    out << "***********************\n"
    out << "Size report for $project.name\n\n"

    out << "Total artifact size:".padRight(45)
    configurations.default.allArtifacts.files.each { artifact ->
        out << "${String.format(formatStr, artifact.length() / (1024 * 1024))} Mb\n\n"
    }

    out << 'Total dependencies size:'.padRight(45)
    out << "${String.format(formatStr, sizeBytes)} Mb\n\n"

    configurations
            .default
            .sort { -it.length() }
            .each {
        out << "${it.name}".padRight(45)
        out << "${it.license}".padRight(10)
        out << "${String.format(formatStr, (it.length() / 1024))} kb\n"




        //explainMe(it)
    }
    println(out)
}

def void explainMe(it){
    //println "Examining $it.name:"
    println "Meta:"
    println it.metaClass.metaMethods*.name.sort().unique()
    println "Methods:"
    println it.metaClass.methods*.name.sort().unique()
    println "Depends On:"
    //println it.dependsOn.collect({it*.getName()})
    println "Properties:"
    println it.properties.entrySet()*.toString()
            .sort().toString().replaceAll(", ","\n")

}

}