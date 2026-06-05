# Week 03 Reflection

**Name:** Ilyas Ibrahim
**Date:** 06/04/26

---

## Commits This Week

<!-- Paste a link to your commits for this week. The easiest way: go to your repo on GitHub,
     click "commits", and copy the URL after filtering by your name or branch. -->

**Link:**
https://github.com/Ilyas9805/media-tracker-android/pull/3## Code Review

<!-- Every week you leave a review on a pod mate's pull request. Fill in both parts below.
     Part 1 is the link — I will verify the review exists on GitHub.
     Part 2 is your written assessment — what you actually looked at and what you found. -->

**Reviewed:** *(Ahmed Sadiq)*
**Link to my review:**
https://github.com/ahmedsadiq04/media-tracker-android/pull/5

### What I Looked At

<!-- Walk through the code you reviewed. What was the PR trying to do? Which files or
     functions did you focus on? -->

Today I reviewed Ahmed's PR where Ahmed worked on the register screen, Ahmed added three new files, 
AuthRequests.kt for the request data classes, 
APIService.kt for the Retrofit interface, and UserRepository.kt for the network layer and then updated AuthViewModel and RegisterScreen to use them.

### What I Noticed

<!-- Be specific. Did you spot a potential bug? A pattern that could cause problems? Something
     done well that you want to call out? "I looked at the ViewModel and everything seemed fine"
     is not specific enough. Name the thing you noticed and explain why it matters. -->

One other thing I noticed is in RegisterScreen.kt the last field, Confirm Password, has ImeAction.Done set 
but the keyboardActions still uses onNext instead of onDone.

### Comments I Left

<!-- Briefly summarize the comments you left on the PR. If you left a positive comment,
     say what it was. If you left a suggestion, say what you suggested and why. -->

I left a comment for ahmed discussing that the Confirm Password field has ImeAction.
Done set but the keyboardActions is still using onNext instead of onDone, I added that with this the keyboard does not do anything at the moment.---

## One Thing I Understood More Deeply

<!-- Be specific. Don't write "I learned about ViewModels." Write what specifically clicked —
     what was confusing before, what made it make sense, and how you'd explain it to someone else.
     There are no wrong answers here. -->
---

This week I understood what suspend means on a function. I kept seeing it on the Retrofit interface methods
like suspend fun createUser() and suspend fun login() but didn't really know why it was there. Looking at how Ahmed calls 
them inside viewModelScope.launch, it clicked that suspend just means the function can be paused and resumed without blocking 
the thread so while it's waiting for the network response the app doesn't freeze.

## One Thing I'm Still Confused About

<!-- Be honest. This is the most useful part of the reflection for me — it tells me where to
     spend more time in class. You will not lose points for being confused. -->
---



I'm still confused about how viewModelScope knows when to cancel a coroutine. I understand 
that it cancels when the ViewModel is cleared, but I'm not sure exactly when that happens.

## Anything Else *(optional)*

<!-- Did you help a pod mate work through something? Did you discover something cool or frustrating?
     Did something from a previous week finally click? This is a good place to put it. -->

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
