package com.example

import org.http4k.client.OkHttp
import org.http4k.core.Method
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AppTest {

    @Test
    fun testReturnsHello() {
        val client = OkHttp()

        val response = app(
            Request(GET, "http://localhost:9000/")
        )

        assert(response.bodyString() == "Hello")
    }

    @Test
    fun testRendersHandlebarsTemplate() {
        val response = app(Request(Method.POST, "http://localhost:9000/bob"))
        assertEquals("<h1>Hello John Doe</h1>", response.bodyString())
    }
}
