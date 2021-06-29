package net.michal.helpers

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.io.File

private val client = HttpClient()

suspend fun get(url: String) = client.get<HttpResponse>(url) {
    headers {
        append(HttpHeaders.UserAgent, "Java/1.8.0_51")
        append(HttpHeaders.Host, "s.optifine.net")
        append(HttpHeaders.Accept, "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2")
        append(HttpHeaders.Connection, "keep-alive")
        append(HttpHeaders.CacheControl, "no-cache")
    }
}

suspend fun downloadText(url: String) = get(url).readText()

suspend fun downloadFile(url: String, file: File) {
    file.writeBytes(get(url).receive())
}
