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

## How to I write HTML pages?

Using the Handlebars templating engine, we can return HTML web pages from the routes:

## How do I handle data sent with forms?

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

[Next Challenge](03_rock_paper_scissors.md)

<!-- BEGIN GENERATED SECTION DO NOT EDIT -->

---

**How was this resource?**  
[😫](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=😫) [😕](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=😕) [😐](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=😐) [🙂](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=🙂) [😀](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F02_greeter2.md&prefill_Sentiment=😀)  
Click an emoji to tell us.

<!-- END GENERATED SECTION DO NOT EDIT -->
