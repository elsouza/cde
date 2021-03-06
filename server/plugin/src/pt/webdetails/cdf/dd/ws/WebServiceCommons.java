/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */
package pt.webdetails.cdf.dd.ws;


public final class WebServiceCommons {

    public static final String JSON_FILE_DOES_NOT_EXIST = "{\"error\": \"file does not exist\"}";

	static boolean isUnitTest() {
		return System.getProperty("mock.path") != null;
	}
}
