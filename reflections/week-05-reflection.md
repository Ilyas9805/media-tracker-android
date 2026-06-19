# Week 05 Reflection

**Name:** Ilyas Ibrahim
**Date:** 06/18/26

---

## Commits This Week

**Link:** https://github.com/Ilyas9805/media-tracker-android/pull/5



**Reviewed:** *(Sadiq Ahmed)*
**Link to my review:** https://github.com/ahmedsadiq04/media-tracker-android/pull/7



### What I Looked At

Ahmed's PR implements the Register User functionality end to end. He added CreateUserResponse
as a real @Serializable data class replacing the old placeholder annotation class, Ahmed also updated ApiService
so createUser now returns Response<Unit> instead of a fake response type, and rewrote 
UserRepository.createAccount() to return a new APIResult<T> sealed interface instead of nothing

### What I Noticed
noticed that UserRepository.createAccount() no longer has a try/catch around the API call. f the network fails 
completely instead of just returning an error response, this could throw an exception 
that crashes the app other than returning APIResult.Error. It is better wrapping the call in a try/catch so 
network failures are handled the same clean way as a 409 response.

### Comments I Left

I left a comment pointing out the error_api_auth string mismatch — 409 is a Conflict status, not Unauthorized, 
so the wording might cause confusion when someone is debugging duplicate accounts.

## One Thing I Understood More Deeply

This week I understood why Response<Unit> is used in ApiService instead of Response<CreateUserResponse>. 
I expected the return type to match the actual data the API sends back. But looking at createAccount(), 
it only checks response.isSuccessful and response.code() it never reads the response body.


## One Thing I'm Still Confused About

I'm still confused about why AuthViewModel now takes userRepository: UserRepository = UserRepository() as a 
constructor parameter with a default value, instead of needing a ViewModelFactory like we used in earlier weeks. 
I thought any ViewModel with a constructor parameter required a factory to be instantiated with viewModel().

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
