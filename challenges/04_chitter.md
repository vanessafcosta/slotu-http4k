# Chitter (without a database)

_**This is a Makers Wheel.** Wheels are designed to develop your skills
efficiently. They are organised around a central exercise, supported by a range
of resources including text, video, and external documentation. [Read more about
how to use Makers
Wheels.](https://github.com/makersacademy/course/blob/main/labels/wheels.md)_

In this challenge, you'll build the Chitter challenge with Kotlin and http4k.

- [Exercise](#exercise)
- [Supporting Materials](#supporting-materials)

## Exercise

_This is the exercise. You may or may not be able to do this yet. Use the
supporting materials below this exercise to help you._

[Using the starter project](../resources/starter_project/), build the Chitter web app challenge.

First, build a simpler version which doesn't use a database to store the peeps.

### Features

These features are listed in increasing level of complexity, so start with the one at the top. Make sure that you test-drive each feature.

```
As a Maker
So that I can let people know what I am doing  
I want to post a message (peep) to chitter

As a maker
So that I can see what others are saying  
I want to see all peeps in reverse chronological order

As a Maker
So that I can better appreciate the context of a peep
I want to see the time at which it was made

As a Maker
So that I can post messages on Chitter as me
I want to sign up for Chitter

As a Maker
So that only I can post messages on Chitter as me
I want to log in to Chitter

As a Maker
So that I can avoid others posting messages on Chitter as me
I want to log out of Chitter
```

## Supporting materials

### Where to store the data?

You don't need to use a database in this exercise ([this will come next](./05_chitter2.md)). You can use an in-memory `MutableList` to store the peeps and user accounts (this means your data will be lost when the program stops running).

### Authenticate users in session

Http4k has no built-in mechanism to store values in session. We need to implement a mechanism to "remember" users who logged in session, using cookies. This is definitely a bit more challenging so it's OK if you don't go that far.

It happens with the following steps:
1. A user signs in with the correct credentials and is authenticated. The server generates a unique session ID, remembers it, and sends it back to the client as a cookie in the response (a cookie is a HTTP header set in the response).
2. Every future request sent by the client will now contain that cookie (as a HTTP request header). Our server code will be able to read the cookie, and verify if a session ID is stored inside it.
3. The server tries to match the session ID with a user ID, to know which user is sending the request.

Here's a detailed breakdown on how to do this:

1. Implement a session "registry". This can be a `Map` associating a session ID to a specific user ID. This should be declared globally, probably in your main file.

```kotlin
// Main.kt

// sessionRegistry is a map containing pairs of 
// session IDs (String) and user IDs (Int)
val sessionRegistry = mutableMapOf<String, Int>
```

2. When your user has successfully logged in, create a session ID and save it in the `sessionRegistry`. It also needs to be sent back in the response as a cookie, so the client (usually a web browser) will remember it.

```kotlin
import java.util.*
import org.http4k.core.cookie.cookie
import org.http4k.core.cookie.Cookie

routes(
    "/login" bind POST to { request: Request -> 
        // Check credentials are valid
        // and authenticate user.

        val sessionId = UUID.randomUUID().toString()

        sessionRegistry.put(sessionId, user.id)

        return Response(OK).cookie(Cookie("session_id", sessionId))
    },
    // ...
)
```

3. The browser will now send the session ID in a cookie HTTP header for every subsequent request. We can try to retrieve the cookie and the user ID to check the user has previously logged in.

```kotlin
import org.http4k.core.cookie.cookie

routes(
    "/" bind GET to { request: Request -> 
        // Retrieve the cookie from the request
        // and the sessionID stored inside it.
        val cookie = request.cookie("session_id")
        val sessionId = cookie?.value

        // Verify the sessionID is present.
        if (sessionId != null) {
            val userId = sessionRegistry.get(sessionId)
            
            // Do something with user ID.
        }
    },
    // ...
)
```


[Next Challenge](05_chitter2.md)

<!-- BEGIN GENERATED SECTION DO NOT EDIT -->

---

**How was this resource?**  
[😫](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F04_chitter.md&prefill_Sentiment=😫) [😕](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F04_chitter.md&prefill_Sentiment=😕) [😐](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F04_chitter.md&prefill_Sentiment=😐) [🙂](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F04_chitter.md&prefill_Sentiment=🙂) [😀](https://airtable.com/shrUJ3t7KLMqVRFKR?prefill_Repository=makersacademy%2Fkotlin-http4k-applications&prefill_File=challenges%2F04_chitter.md&prefill_Sentiment=😀)  
Click an emoji to tell us.

<!-- END GENERATED SECTION DO NOT EDIT -->
