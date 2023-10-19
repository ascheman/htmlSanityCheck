package org.aim42.htmlsanitycheck.html

import spock.lang.Specification
import spock.lang.Unroll

class URLUtilSpec extends Specification {

    //@Unroll
    def "invalid chars in link"(boolean containsInvalidChars, String link) {
        expect:
        URLUtil.containsInvalidChars( link ) == containsInvalidChars

        where:

        containsInvalidChars | link
        false | "#Context-Analysis"
        false | "#Context_Analysis"
        false | "#Context--Analysis"
        false | "/forum/#!forum/randoop-discuss" // correct, as reported in #271

        true  | "#Context Analysis" // regression test, contains blank
        true  | "*Context-Analysis" // * is not allowed

    }



    @Unroll
    def "identify invalid links"(boolean isValid, String link) {
        expect:
        URLUtil.isValidURL( link ) == isValid

        where:

        isValid | link
        true    | "http://arc42.de/index.html"
        true    | "#localRef"
        true    | "file://images/image.jpg"

        false   | "#Context Analysis"
        false   | "//10.0.0.1/index.html"

    }

    @Unroll
    def "identify local resource links"(boolean isLocal, String link) {

        expect:
        URLUtil.isLocalResource( link ) == isLocal

        where:

        isLocal | link
        true    | "test.html"
        true    | "test.htm"
        true    | "TEST.HTM"
        true    | "test.docx"
        true    | "test.pdf"
        true    | "test.csv"
        true    | "jquery.js"
        true    | "./test.html"
        true    | "../test.html"
        true    | "file://test.html"
        true    | "file://test.html#anchor"
        true    | "dira/file.html"
        true    | "dira/dirb/file.html"
        true    | "dira/dirb/file.html#anchor"

        true    | "//index.html"   // browser assumes "file:" here
        true    | "//10.0.0.1/index.html" // invalid syntax, scheme missing, but browser defaults to "file"

        false   | "#Context Analysis" // regression test for  issue #70, contains blank-character

        false   | "http://index.html"
        false   | "mailto:alan.turing@acm.org"
        false   | ""
        false   | null
        false   | "ftp://acm.org"
        false   | "http://10.0.0.1/index.html"

        false   | "10.0.0.1/index.html"  // this is a valid REMOTE address, defaults to http or https

    }


    @Unroll
    def "check for valid ip address"(boolean isValidIP, String ipa) {
        expect:
        URLUtil.isValidIP(ipa) == isValidIP

        where:

        isValidIP | ipa
        true      | "0.0.0.0"
        true      | "255.255.255.255"
        true      | "127.0.0.1"

        false     | "a.b.c.d"
        false     | "192.102.100"
        false     | "1.2.3.400"
        false     | "1.2.3"
        false     | "1.2"
        false     | "110"


    }
}


/************************************************************************
 * This is free software - without ANY guarantee!
 *
 *
 * Copyright 2013-2015, Dr. Gernot Starke, arc42.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *********************************************************************** */
