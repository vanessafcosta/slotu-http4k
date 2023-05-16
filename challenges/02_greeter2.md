# A better Greeter

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

[Building on the previous exercise](./01_greeter.md), we want to make improvements to make the greeter app more user-friendly.

The user should get a form on the home page where they can input their name. Once they submit the form, they should get a new page with the greeting message displayed.

## Supporting materials

The following content can be read top to bottom. You might need to research a few things by yourself in the provided documentation links.

### How do I write HTML pages?

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

```kotlin
import org.http4k.template.ViewModel

// 1.
data class PersonViewModel(
    val firstName: String,
    val lastName: String
) : ViewModel

// 2.
val templateRenderer = HandlebarsTemplates().HotReload("src/main/resources")

// 3.
val viewModel = PersonViewModel("John", "Doe")

Response(Status.OK)
    .body(templateRenderer(viewModel))
```

```hbs
<!-- file: src/main/resources/PersonViewModel.hbs -->
<h1>Hello {{firstName}} {{lastName}}</h1>
```

1. We create a data class which inherits from the `ViewModel` class (from http4k). This class is used as a "container" for the data we need to pass to the view layer.
2. We create a "renderer" using `HandlebarsTemplates().HotReload("src/main/resources")`, giving the path to the Handlebars templates in the project.
3. We create an instance from the `ViewModel` class and give it to the `templateRenderer`. The template will then be transformed to the final HTML body, using the actual values provided in the `ViewModel`. This returns a string (the HTML body) we can send back in the response.

### How do I handle data sent with forms?

We can use lenses again to handle parameters sent with POST forms:

```kotlin
import org.http4k.lens.FormField

// 1.
val requiredFirstNameField = FormField.required("first_name")
val requiredLastNameField = FormField.required("last_name")

// 2.

val firstName = requiredFirstNameField(request)
val lastName = requiredLastNameField(request)
```

### Writing tests

@TODO


### More on the Handlebars templating syntax

```hbs

```

[Next Challenge](03_rock_paper_scissors.md)

<!-- BEGIN GENERATED SECTION DO NOT EDIT -->

---

**How was this resource?**  
[😫](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=😫) [😕](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=😕) [😐](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=😐) [🙂](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=🙂) [😀](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=😀)  
Click an emoji to tell us.

<!-- END GENERATED SECTION DO NOT EDIT -->
