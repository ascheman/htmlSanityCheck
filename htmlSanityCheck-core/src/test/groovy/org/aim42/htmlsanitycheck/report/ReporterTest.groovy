package org.aim42.htmlsanitycheck.report

import org.aim42.htmlsanitycheck.collect.PerRunResults
import org.aim42.htmlsanitycheck.collect.SinglePageResults
import org.junit.Test

// see end-of-file for license information


class ReporterTest extends GroovyTestCase {

    @Test
    public void testNothingReportedWithEmptyResults() {
        SinglePageResults spr = new SinglePageResults()
        PerRunResults runResults = new PerRunResults(  )
        runResults.addPageResults(spr)

        Reporter reporter = new ConsoleReporter( runResults )

        assertEquals( "Empty ConsoleReporter has no check", 0, reporter.totalNrOfChecks())

        assertEquals( "Empty Reporter shall have no findings", 0, reporter.totalNrOfFindings())
    }
}
