# Rock-Paper-Scissors

_**This is a Makers Wheel.** Wheels are designed to develop your skills
efficiently. They are organised around a central exercise, supported by a range
of resources including text, video, and external documentation. [Read more about
how to use Makers
Wheels.](https://github.com/makersacademy/course/blob/main/labels/wheels.md)_

In this challenge, you'll build a game of Rock-Paper-Scissors.

- [Exercise](#exercise)
- [Supporting Materials](#supporting-materials)
    - [I'm not sure where to start](#im-not-sure-where-to-start)
    - [How to generate a random move?](#how-to-generate-a-random-move)

## Exercise

_This is the exercise. You may or may not be able to do this yet. Use the
supporting materials below this exercise to help you._

[Using the starter project](../resources/starter_project/), build a web app which is a game of Rock-Paper-Scissors.

On the home page, the user should be able to select one of the three moves, then submit. The next page should indicate the result (won or lost) to the user.

## Supporting materials

### I'm not sure where to start

Start by test-driving a first route which returns a list of possible choices to the user. No need for HTML for now, just return a plain text response (for example, `"Rock - Paper - Scissors"`).

Once this is done, do the work of [moving the response body to a Handlebars template](./02_greeter2.md#how-do-i-write-html-pages). This will become an interactive HTML page where the user can select their move using buttons (or links). Whether you're using query parameters or a form, [make sure you use the http4k lens mechanism.](./01_greeter.md#handling-request-parameters)

The rest of the challenge will be the result page, where the player move is compared to a randomly generated move.

### Custom Handlebars helpers

There are times where we need to call some Kotlin code in our templates. For example, let's say we want to transform to uppercase a value printed in a template.

```hbs
<h1>{{ name }}</h1>
```

It would be good if we could just write...

```hbs
<h1>{{ name.uppercase() }}</h1>
```

This doesn't work. But we can register a custom Handlebars helper to get the same result:

```kotlin
import com.github.jknack.handlebars.Handlebars

// ...

fun configureHandlebars (config: Handlebars): Handlebars {
    config.registerHelper<String>("uppercase") { string, _options ->
        string.uppercase()
    }

    return config
}

val renderer = HandlebarsTemplates(::configureHandlebars).HotReload("src/main/resources")
```

```hbs
<h1>{{ uppercase name }}</h1>
```

### How to generate a random move?

[You can use the `Random.Default` class](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.random/-random/-default/) from Kotlin to help you generate the random move from the game.



[Next Challenge](04_chitter.md)

<!-- BEGIN GENERATED SECTION DO NOT EDIT -->

---

**How was this resource?**  
[😫](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F03_rock_paper_scissors.md&prefill_Sentiment=😫) [😕](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F03_rock_paper_scissors.md&prefill_Sentiment=😕) [😐](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F03_rock_paper_scissors.md&prefill_Sentiment=😐) [🙂](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F03_rock_paper_scissors.md&prefill_Sentiment=🙂) [😀](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F03_rock_paper_scissors.md&prefill_Sentiment=😀)  
Click an emoji to tell us.

<!-- END GENERATED SECTION DO NOT EDIT -->
