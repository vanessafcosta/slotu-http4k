package com.example

import com.example.models.HandlebarsViewModel
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.Query
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer
import org.http4k.template.HandlebarsTemplates

val templateRenderer = HandlebarsTemplates().HotReload("src/main/resources")

val app: HttpHandler = routes(
    "/" bind GET to { request: Request ->
        Response(OK).body("Hello")
    },
    "/bob" bind Method.POST to { request: Request ->
        val viewModel = HandlebarsViewModel("John", "Doe")
        println("Attempting to render template for: $viewModel")
        val renderedTemplate = templateRenderer(viewModel)
        println("Rendered template: $renderedTemplate")
        Response(OK).body(renderedTemplate)
    }
)

fun main() {
    val server = app.asServer(Undertow(9000)).start()

    println("Server started on " + server.port())
}
