package com.banyaibalazs.depreport

import org.gradle.api.reporting.Report
import org.gradle.api.reporting.ReportContainer

interface DepReportContainer extends ReportContainer<Report> {
    Report getHtml();
}
