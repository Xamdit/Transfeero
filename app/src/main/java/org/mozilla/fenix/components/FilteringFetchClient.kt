/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.components

import mozilla.components.concept.fetch.Client
import mozilla.components.concept.fetch.MutableHeaders
import mozilla.components.concept.fetch.Request
import mozilla.components.concept.fetch.Response
import java.io.ByteArrayInputStream

/**
 * A wrapper for the HTTP [Client] that blocks specific domains (like Mozilla Remotesettings)
 * to ensure that no background sync noise or signature errors occur in PKGO builds.
 */
class FilteringFetchClient(private val base: Client) : Client() {
    override fun fetch(request: Request): Response {
        val url = request.url
        if (url.contains("mozilla.com") || url.contains("mozilla.org")) {
            android.util.Log.d("FilteringFetchClient", "BLOCKED: $url")
            // Return a dummy 404 response to satisfy the client without triggering a sync
            return Response(
                url = url,
                status = 404,
                headers = MutableHeaders(),
                body = Response.Body(ByteArrayInputStream("".toByteArray())),
            )
        }
        return base.fetch(request)
    }
}
