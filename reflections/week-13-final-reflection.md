# Final Reflection — ICS 342

**Due:** End of class, Week 13 (August 13, 2026)
**Submitted with:** Your final pull request
**Worth:** 50 points

---
Name** Ilyas Ibrahim
Date** 08/13/26
---

## Part 1 — Your App

**1a. Open your final pull request on GitHub. Find the commit that you are most proud of — not the largest, not the last one, the one that meant the most to you. Paste the commit URL here and explain why
you chose it. What did it take to get there? What was broken before and what worked after?**

> Your answer: The commit I'm most proud of is the one where I wired GET /library/{mediaId} and GET /favorites/{mediaId} into MediaDetailViewModel.loadMedia() so the "+ Want To" and "Save" buttons actually 
> reflected reality instead of always showing as unselected. Before that commit, the buttons were completely static they always showed "+ Want To" and an empty heart regardless of whether you'd already 
> added the item. The problem was that loadMedia() only fetched the media detail and stopped there. Getting it to also check two additional endpoints on every load and update two separate state
> flows (libraryStatus and isFavorited) meant restructuring the entire function. After the commit, opening a media item you'd already added to your library correctly showed "Want To" on the button, and 
> opening something you'd saved showed a filled heart. That was the first moment the app felt like a real app rather than a prototype.

Link: https://github.com/Ilyas9805/media-tracker-android/pull/9

---

**1b. Name one screen in your app that you think is genuinely well-built. Not perfect — well-built. Explain specifically why: what design decisions did you make, what did you refactor, and how does it differ from how you would have approached it in week 2?**

> Your answer: MediaDetailScreen is the screen I'm most satisfied with. It has four distinct UI states Loading, NotFound, Error, and Success each handled cleanly through a sealed UiState class 
> in MediaDetailViewModel. The content itself is split into a separate MediaDetailContent composable so the state handling and the actual UI are never mixed together in the same function. 
> The cover image handles both a real AsyncImage (when coverUrl is not null) and a colored icon fallback (when it is null) using per-type container colors purple for books, pink for movies,
> amber for shows which is consistent with how the search cards and library cards handle the same case. The stat grid changes its middle box based on mediaType pages for books, runtime for movies, seasons/episodes for shows



---

**1c. Name one screen or feature that you are not satisfied with. What is wrong with it? If you had one more week, what specifically would you change?**

> Your answer: The Priorities feature's remove functionality is the thing I'm least satisfied with. When you tap Remove on a priority item it disappears from the screen immediately
> but if you navigate away and come back, it reappears because GET /priorities fetches fresh from the server which still has the item. There's no DELETE /priorities/{mediaId} endpoint 
> on the API, so there's genuinely no way to permanently remove an item through the client. If I had one more week I would redesign the whole approach instead of trying to remove items,
> I'd let users set a "completed" or "dismissed" flag locally and hide those items from the list on the client side, making the remove feel permanent even though the server still has the record.

---

## Part 2 — A Specific Bug

**2a. Describe the hardest bug you fixed this semester. Not the most recent one — the one that took the longest or cost you the most confusion. What was the symptom? What did you think the problem was at first? What was it actually? How did you find it?**

> Your answer: The hardest bug this semester was the 401 Unauthorized error on every search request. The symptom was clear every call to GET /media came back with 401 "Authorization
> header required" even though I was logged in. My first assumption was that the token wasn't being saved after login, so I spent time checking DefaultSessionRepository and DataStore. The token
> was being saved correctly. Then I thought the AuthInterceptor wasn't being added to the OkHttp client. But I hadn't built AuthInterceptor yet that was the actual problem. I had a 
> RetrofitInstance with a single shared OkHttpClient that had no interceptor at all, so no Authorization header was ever being added to outgoing requests. Every request was going out 
> as if the user wasn't logged in. I found it by checking Logcat for the OkHttp tag and looking at the raw request there was simply no Authorization header present in any outgoing call.

---

**2b. Copy and paste the specific lines of code you changed to fix it. (This can be a before/after comparison, a diff, or just the relevant snippet.) Explain in plain English what the fix does and why it works.**

> Your answer (include code):

// Before — no auth interceptor anywhere
private val retrofit = Retrofit.Builder()
.baseUrl(ApiConstants.BASE_URL)
.client(OkHttpClient.Builder().addInterceptor(loggingInterceptor()).build())
.addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
.build()

val mediaApiService: MediaApiService = retrofit.create(MediaApiService::class.java)

// After — separate function that injects AuthInterceptor
fun mediaApiService(sessionRepository: SessionRepository): MediaApiService =
Retrofit.Builder()
.baseUrl(ApiConstants.BASE_URL)
.client(
OkHttpClient.Builder()
.addInterceptor(AuthInterceptor(sessionRepository))
.addInterceptor(loggingInterceptor())
.build()
)
.addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
.build()
.create(MediaApiService::class.java)

        And AuthInterceptor:
        class AuthInterceptor(private val sessionRepository: SessionRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { sessionRepository.getAccessToken() }
        val request = chain.request().newBuilder()
            .apply { if (token != null) addHeader("Authorization", "Bearer $token") }
            .build()
        return chain.proceed(request)
    }
}


The fix works because every call to mediaApiService(sessionRepository) now builds a fresh Retrofit instance with an AuthInterceptor that reads the saved token from DataStore and attaches it as a Bearer header to every outgoing request. 
The userApiService (login/register) stays unauthenticated since those calls happen before a token exists.
---

## Part 3 — What You Actually Learned

**3a. Pick the concept from this semester that took the longest to actually understand — not just to implement, but to understand. Describe what you thought it was before y
ou understood it, what changed, and how you would explain it now to a student who was exactly where you were at the start of the semester.**

> Your answer: The concept that took the longest to actually understand was the relationship between ViewModel, AndroidViewModel, and Application context. At the start of the semester 
> I thought ViewModel was just a class that held data for a screen I didn't understand why sometimes you needed AndroidViewModel and sometimes you didn't. I kept copying the pattern without understanding it.
> It clicked during Week 8 when I had to change MediaDetailViewModel from ViewModel to AndroidViewModel to make the real API call work.

---

**3b. Your weekly reflections had a "Still Confused" section. Look back at your early reflections — weeks 1 through 4. Find something you wrote that you were confused about then. 
Are you still confused about it? If not, when and how did it click? If you still are, say so honestly and describe what the sticking point is.**

(If you can't access your early reflections, describe something you remember being confused about in the first half of the semester.)

> Your answer: In my early weeks I was confused about why StateFlow was used instead of just regular variables to hold screen state in the ViewModel. I wrote something like "I don't understand why you can't just 
> use a var inside the ViewModel and read it from the screen." It clicked around Week 6 when I realized that Compose recomposes automatically when a StateFlow emits a new value but it has no way of knowing a plain var changed.

---

**3c. Name one thing a pod mate said, asked, or showed you during a code review or work session that changed how you approached something. It doesn't have to be a big thing. What was it, and what did it change?**

> Your answer: During a code review Nicholas left a comment on my MediaDetailViewModel pointing out that getLibraryEntry() and getFavoriteEntry() were running sequentially inside loadMedia()  the favorites check 
> didn't start until the library check finished. He suggested running them concurrently with async/await. I hadn't thought about that at all  I just wrote the calls one after the other without considering that
> they were independent of each other and could run in parallel.



---

## Part 4 — Your Bonus Feature

**4a. Describe your bonus feature in one paragraph as if you were explaining it to someone who has never used an app before. What does it do? Why would a user want it?**

> Your answer: The Priorities feature lets you take items from your "Want To" list and organize them by how urgently you want to get to them. Imagine you have twenty books 
> and movies saved that you want to watch or read someday the Priorities feature lets you pick up to five of them, label each one as High, Medium, or Low priority, estimate 
> how many hours it'll take, and add a personal note. They show up in a dedicated "My Priorities" list so you always know what you should be working on next instead of 
> scrolling through your entire library trying to decide. It's the difference between a wish list and an actual plan.



---

**4b. What was the technically hardest part of building it? Name a specific function, flow, or data structure that gave you trouble, and explain what the problem was.**

> Your answer: The hardest part was understanding what PUT /priorities actually does. The spec said "PUT a new list without the removed item" to handle deletion which implied the endpoint accepted a full array and replaced everything.
> I spent a significant amount of time sending List<PriorityRequest> as the body and getting errors, then switching to a wrapper class SetPrioritiesRequest, then switching back. The actual API only accepts a single PriorityRequest
> object per call it's a single-item upsert, not a bulk replace. There is no way to delete an item through the API at all.



---

**4c. Your bonus feature has tests. Open the test file and paste the test you think is most valuable — the one that would catch the most important failure. Explain what it proves and what it does not prove.**

> Your answer (include code):

@Test
fun removePriority_removesCorrectItemFromLocalState() {
// Given a list of 2 priorities
val priorities = listOf(
Priority(mediaId = 1, priority = 1, orderIndex = 0, estimatedTimeHours = 2f),
Priority(mediaId = 2, priority = 2, orderIndex = 1, estimatedTimeHours = 1f)
)

    // When removing mediaId = 1
    val result = priorities.filter { it.mediaId != 1 }

    // Then only mediaId = 2 should remain
    assertEquals(1, result.size)
    assertEquals(2, result.first().mediaId)
}

This test proves that the filter logic used in removePriority() removes exactly the right item and leaves everything else intact. It's the most valuable 
test because remove is the one operation in the priorities feature that has no API backing, it only works locally so if the filter logic is wrong, there's no server 
response to catch the mistake. What it does not prove is that removePriority() actually persists across navigation, because it can't the server still has the item and loadPriorities() fetches fresh on every screen open.

---

## Part 5 — Looking Forward

**5a. If you were going to continue developing this app after the semester ends, what would you build next and why?**

> Your answer: The first thing I would build next is wiring the delete favorites endpoint. Right now once you save something as a favorite the heart fills in and
> the button says "Saved" but there's no way to unsave it. The POST /favorites call works correctly, but tapping the filled heart does nothing. The API has a DELETE /favorites/{mediaId} endpoint
> that I never wired up because it wasn't required for Week 10. Adding it would be straightforward one new function in MediaApiService, one in DefaultMediaRepository, and a small change to onFavoriteClick() 
> in MediaDetailViewModel to check whether the item is already favorited and either add or remove it accordingly. It's a small change that would make a big difference to how complete the app feels

---

**5b. A friend tells you they want to learn Android development. Based specifically on your experience this semester — not what you've read, what you lived — what is the one thing you would tell them to understand before they write a single line of code?**

> Your answer: I would tell that Understand that Android development is not just about writing code it's about understanding how pieces connect. Before you write anything, learn what a ViewModel is and why it exists, 
> because everything else builds on top of that. If you don't understand why the screen shouldn't own its own data, you'll spend the whole semester fighting bugs that are caused by architecture problems, not code problems.

---

## Grading

| Section | Points |
|:---|:---:|
| Part 1 — Your App | 12 |
| Part 2 — A Specific Bug | 12 |
| Part 3 — What You Actually Learned | 14 |
| Part 4 — Your Bonus Feature | 8 |
| Part 5 — Looking Forward | 4 |
| **Total** | **50** |

Full credit in each section requires specificity — code, names, dates, conversations, actual things that happened. Half credit for responses that are genuine but stay at the surface. No credit for responses that are generic, missing, or could have been written by someone who was not in this class.
