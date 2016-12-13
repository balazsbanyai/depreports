package com.banyaibalazs.depreport;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bbanyai on 13/12/16.
 */
public class DepReportPluginTest {

    @Test
    public void greeterPluginAddsGreetingTaskToProject() {
        Project project = ProjectBuilder.builder().build();
        project.pluginManager.apply 'com.banyaibalazs.depreport'

        assertTrue(project.tasks.depreport instanceof DepReportTask)
    }


}