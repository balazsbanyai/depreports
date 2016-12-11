package com.banyaibalazs.depreport

import org.gradle.api.Plugin
import org.gradle.api.Project

class DepReportPlugin implements Plugin<Project> {

   void apply(Project project) {

      project.tasks.create("depreport", DepReportTask)

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