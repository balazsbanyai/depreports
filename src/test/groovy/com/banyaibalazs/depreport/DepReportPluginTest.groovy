package com.banyaibalazs.depreport;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepReportPluginTest {

    @Test
    public void depreportPluginAddsDepreportTaskToProject() {
        Project project = ProjectBuilder.builder().build();
        project.pluginManager.apply 'com.banyaibalazs.depreport'

        assertTrue(project.tasks.depreport instanceof DepReportTask)
    }


}