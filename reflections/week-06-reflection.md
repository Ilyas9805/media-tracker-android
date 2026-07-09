# Week 06 Reflection

**Name:** Ilyas Ibrahim
**Date:** 06/25/26

---

## Commits This Week

**Link:** https://github.com/Ilyas9805/media-tracker-android/pull/6




**Reviewed:** *(Nicholas Chyrklund)*
**Link to my review:** https://github.com/NChyrklund/media-tracker-android/pull/7




### What I Looked At

This week I looked at Nicholas PR, I looked closely at AuthInterceptor.kt and how it gets wired into RetrofitInstance.mediaApiService(), 
since that's what finally solves the missing Authorization header problem I'd been stuck on.

### What I Noticed

I noticed that the AuthInterceptor uses runBlocking to call the suspend function getAccessToken() from inside a synchronous OkHttp Interceptor.

### Comments I Left

I left a comment on the AuthInterceptor using a small dedicated Interceptor class instead of bloating RetrofitInstance
directly is a clean separation of concerns, and it makes the auth logic easy to test or swap out independently.

## One Thing I Understood More Deeply

This week I understood why mediaApiService is a function that takes sessionRepository as a parameter, instead of just being 
a val like userApiService. The auth interceptor needs access to the session token, and that token comes from SessionRepository, 
which needs a Context to work.

## One Thing I'm Still Confused About

I'm still confused about why MediaTypeFilterChips uses an empty string "" to represent the "All" filter instead
of something like "all" or a null value.

## Anything Else *(optional)*



---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
