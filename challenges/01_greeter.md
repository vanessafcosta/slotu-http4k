# Greeter

_**This is a Makers Wheel.** Wheels are designed to develop your skills
efficiently. They are organised around a central exercise, supported by a range
of resources including text, video, and external documentation. [Read more about
how to use Makers
Wheels.](https://github.com/makersacademy/course/blob/main/labels/wheels.md)_

In this challenge, you'll build a greeter web app which prints a personalised "Hello" message to its user.

- [Exercise](#exercise)
- [Supporting Materials](#supporting-materials)

## Exercise

_This is the exercise. You may or may not be able to do this yet. Use the
supporting materials below this exercise to help you._

[Using the starter project](../resources/starter_project/), build a web app which prints a personalised greeting message to its user.

If the user browses the URL http://localhost/hello?name=Leo, it should print "Hello Leo". If they browse the URL http://localhost/hello?name=Jay, it should print "Hello Jay", etc. 

## Supporting materials

The following content should be read in sequence. You might need to research a few things by yourself in the provided documentation links.

### Video

[A video walkthrough of the following sections and exercises is available here](https://www.youtube.com/watch?v=TXeFAqHJ7Hw)

### Where do I start? 

[Use the starter project](../resources/starter_project/) provided with these materials. You should be able to run the `main` function to start a web server at http://localhost:9000

### What does the code do?

The code in the starter project does a few things:

```kotlin
// ...

// 1
val app: HttpHandler = routes(
    "/" bind GET to { request: Request ->

        // 3
        Response(OK).body("Hello")
    }
)

fun main() {

    // 2
    val server = app.asServer(Undertow(9000)).start()

    println("Server started on " + server.port())
}
```

Let's break it down:

1. The built-in `routes` function is used to map a path and HTTP method to a [Kotlin lambda](https://kotlinlang.org/docs/lambdas.html#lambda-expression-syntax) (the equivalent of an "anonymous" function in Kotlin). It returns a value of the type `HttpHandler`.
2. Calling the member function `asServer` on `app` is what starts the server for real (on the given port 9000), and keep it running in the background until we manually stop the program. Once the server is started, nothing much happens. 
3. As soon as an incoming HTTP request is received (for example, when you browse http://localhost:9000 in your web browser), http4k will try to find a matching route, and will run the lambda associated with it. This lambda needs to return a `Response` object. Here, we return an OK response with a body.

#### Exercises to go further

1. Modify the code to implement an extra route `GET` to `/goodbye`. This should return a OK response with the contents "Good bye"

### Writing tests

```kotlin
// file: src/test/kotlin/com.example/AppTest.kt

package com.example

import org.http4k.client.OkHttp
import org.http4k.core.Method.GET
import org.http4k.core.Request
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
}
```

#### Exercises to go further

Write a test for the endpoint `/goodbye` implemented in the previous exercise

### Handling request parameters

We need to use [the concept of Lens](https://www.http4k.org/guide/concepts/lens/) from the http4k library to handle request parameters.

Below is an annotated example:

```kotlin
// ...
import org.http4k.lens.Query

// 1.
val optionalCityQuery = Query.optional("city")

val app: HttpHandler = routes(
    "/" bind GET to { request: Request ->

        // 2.
        val city: String? = optionalCityQuery(request)

        Response(OK).body("Hello, you've chosen $city")
    }
)

fun main() {
    
    // ...
}
```

1. We declare a "Lens" object by using `Query.optional`. There are a lot of different ways of declaring lenses depending what we need to access. Essentially here we're saying "I want to accept an optional query parameter named 'city' in my requests".

2. Within the route lambda, we can access the actual value of the parameter present in the request by using the lens like a function, passing the request in argument. Note that because we used `optional`, the parameter might be present or not — which is why we get a `String?` for the value.

```
$ curl 'http://localhost:9000/?city=London'
Hello, you've chosen London

$ curl 'http://localhost:9000/?city=Paris'
Hello, you've chosen Paris
```

#### Exercises to go further

Test-drive a new route `GET /secret` which returns either a text response "The cookies are in the desk drawer", either "Wrong password!", depending on the query parameter `password`:

```
$ curl 'http://localhost:9000/secret?password=something'
Wrong password!

$ curl 'http://localhost:9000/secret?password=somethingelse'
Wrong password!

$ curl 'http://localhost:9000/secret?password=HUNGRY'
The cookies are in the desk drawer
```

[Next Challenge](02_greeter2.md)

<!-- BEGIN GENERATED SECTION DO NOT EDIT -->

---

**How was this resource?**  
[😫](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F01_greeter.md&prefill_Sentiment=😫) [😕](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F01_greeter.md&prefill_Sentiment=😕) [😐](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F01_greeter.md&prefill_Sentiment=😐) [🙂](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F01_greeter.md&prefill_Sentiment=🙂) [😀](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F01_greeter.md&prefill_Sentiment=😀)  
Click an emoji to tell us.

<!-- END GENERATED SECTION DO NOT EDIT -->
