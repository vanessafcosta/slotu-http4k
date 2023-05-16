# A better Greeter

_**This is a Makers Wheel.** Wheels are designed to develop your skills
efficiently. They are organised around a central exercise, supported by a range
of resources including text, video, and external documentation. [Read more about
how to use Makers
Wheels.](https://github.com/makersacademy/course/blob/main/labels/wheels.md)_

In this challenge, you'll build a greeter web app which prints a personalised "Hello" message to its user.

- [Exercise](#exercise)
- [Supporting Materials](#supporting-materials)
    - [Video](#video)
    - [Installing Handlebars](#installing-handlebars-in-the-project)
    - [How do I write HTML pages?](#how-do-i-write-html-pages)
    - [More on http4k lenses](#more-on-http4k-lenses)
    - [How do I handle data sent with forms?](#how-do-i-handle-data-sent-with-forms)
    - [Writing tests for forms](#writing-tests)
    - [More on the Handlebars syntax](#more-on-the-handlebars-templating-syntax)
    - [Exercises to go further](#exercises-to-go-further)

## Exercise

_This is the exercise. You may or may not be able to do this yet. Use the
supporting materials below this exercise to help you._

[Building on the previous exercise](./01_greeter.md), we want to make improvements to make the greeter app more user-friendly.

The user should get a form on the home page where they can input their name. Once they submit the form, they should get a new page with the greeting message displayed. Test-drive this new feature.

## Supporting materials

The following content can be read top to bottom. You might need to research a few things by yourself in the provided documentation links.

### Video

[A video walkthrough of the following sections and exercises is available here](https://www.youtube.com/watch?v=BF0mrNVw0Gw)

### Installing Handlebars in the project

Using the Handlebars templating engine, we can return HTML web pages from the routes.

To install the dependencies required to make it work, make the following changes to the file `build.gradle`:

```groovy
dependencies {
    // ...

    implementation(platform("org.http4k:http4k-bom:4.43.1.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-template-handlebars")
}
```

Then run a "Gradle sync" when prompted.

### How do I write HTML pages?

To use a template to send back an HTML response, we need:
 * create a `ViewModel` data class
 * create a "renderer"
 * pass data to the template to get the final HTML result

```kotlin
import org.http4k.template.ViewModel
import org.http4k.template.HandlebarsTemplates

// 1.
data class PersonViewModel(
    val firstName: String,
    val lastName: String
) : ViewModel

// 2.
val templateRenderer = HandlebarsTemplates().HotReload("src/main/resources")

val app: HttpHandler = routes(
    "/" bind POST to { request: Request ->

        // 3.
        val viewModel = PersonViewModel("John", "Doe")

        Response(Status.OK)
            .body(templateRenderer(viewModel))
    }
)
```

```hbs
<!-- file: src/main/resources/PersonViewModel.hbs -->

<h1>Hello {{firstName}} {{lastName}}</h1>
```

1. We create a data class which inherits from the `ViewModel` class (from http4k). This class is used as a "container" for the data we need to pass to the view layer.
2. We create a "renderer" using `HandlebarsTemplates().HotReload("src/main/resources")`, giving the path to the Handlebars templates in the project.
3. We create an instance from the `ViewModel` class and give it to the `templateRenderer`. The template will then be transformed to the final HTML body, using the actual values provided in the `ViewModel`. This returns a string (the HTML body) we can send back in the response.

Note that the `.hbs` file needs to have the exact same name than the `ViewModel` class name.

[The documentation can be found here.](https://www.http4k.org/guide/howto/use_a_templating_engine/)

### More on http4k lenses

As seen in the previous section, lenses allow us to access values in the request, such as query parameters in the URL.

We can use them to access other things too, such as values sent by form (in `POST` requests), HTTP headers, etc.

How to know when using a lens can be useful? Well:

 * If you need to access (or validate) a query parameter from the URL, such as `/greet?name=Leo`, you can use a lens for this.
 * If you need to access (or validate) a value sent in a form, you can use a lens for this.
 * If you need to access a HTTP header value (or validate) sent in the request, you can use a lens for this.

[More examples can be found in the documentation](https://www.http4k.org/guide/concepts/lens/)

### How do I handle data sent with forms?

We can use lenses again to handle parameters sent with POST forms:

```kotlin
import org.http4k.lens.FormField
// Below is a shortcut to include everything
// under org.http4k.lens.
import org.http4k.lens.*

// 1. Create lenses for individual fields
val requiredFirstNameField = FormField.required("first_name")
val requiredLastNameField = FormField.required("last_name")

// 2. Create a lens for the form
//   (which contains the fields above, and 
//   some validation strategy)
val requiredForm = Body.webForm(
    Validator.Strict,
    requiredFirstNameField,
    requiredLastNameField
).toLens()


val app: HttpHandler = routes(
    "/signup" bind POST to {request: Request ->

        // 3. Unwrap the form from the request, then individual values
        val form = requiredForm(request)
        val firstName = requiredFirstNameField(form)
        val lastName = requiredLastNameField(form)

        // ...
    }
)
```

[A more complex example can be found in the official documentation.](https://www.http4k.org/guide/howto/use_html_forms/#lens_typesafe_validating_api)

### Writing tests

We can re-use lenses in our tests as well, to build test requests sent to the app. Below is an example of using the previous declared lens `requiredForm` to build a test request.

```kotlin
// In the "Main" program file:

import org.http4k.lens.*

val requiredFirstNameField = FormField.required("first_name")
val requiredLastNameField = FormField.required("last_name")
val requiredForm = Body.webForm(
    Validator.Strict,
    requiredFirstNameField,
    requiredLastNameField
).toLens()
```

```kotlin
// In a test file:

@Test fun testSignup() {
    // 1. Build the form
    val form = WebForm(mapOf(
        "firstName" to listOf("John"),
        "lastName" to listOf("Doe"),
    ))

    // 2. Send the request
    val response = app(
        Request(Method.POST, "http://localhost:9000/users").with(
            requiredForm of form
        )
    )

    // ...
}
```

### More on the Handlebars templating syntax

Let's assume we have the following `ViewModel` class. It contains a mix of string and boolean properties, and a list of objects.

```kotlin
data class Hobbie(
    val name: String
)

data class PersonViewModel(
    val firstName: String,
    val lastName: String,
    val hobbies: List<Hobbie>,
    val isAuthorized: Boolean
) : ViewModel
```

In handlebars template files, we can use the following syntax to access these properties, have conditionals on them, or loop through them:

```hbs
<h1>{{firstName}} {{lastName}}</h1>
```

```hbs
<p>
    {{#if isAuthorized}}
        You are authorized
    {{/if}}
</p>
```

```hbs
<ul>
    {{#each people.hobbies}}
        <li>{{name}}</li>
    {{/each}}
</ul>
```

#### Exercises to go further

1. Create an HTML template for the route `/secret` implemented in the previous exercises.

2. Update the route built previously `/secret` so it now accepts the password from an HTML form, rather than a query parameter.

    This means you'll need two routes: a `GET` route for a first page containing the form, and a `POST` route to receive the form data and display the result.

[Next Challenge](03_rock_paper_scissors.md)

<!-- BEGIN GENERATED SECTION DO NOT EDIT -->

---

**How was this resource?**  
[😫](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=😫) [😕](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=😕) [😐](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=😐) [🙂](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=🙂) [😀](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=😀)  
Click an emoji to tell us.

<!-- END GENERATED SECTION DO NOT EDIT -->
