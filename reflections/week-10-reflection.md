# Week 10 Reflection

**Name:** Ilyas Ibrahim
**Date:** 07/23/26

---

## Commits This Week

**Link:** https://github.com/Ilyas9805/media-tracker-android/pull/10/commits




**Reviewed:** *(Sadiq Ahmed)*
**Link to my review:** https://github.com/benjamincassidymetro/media-tracker-android/commit/1c8800662669af8d8d00f21bc9aa4437aabd5a1b

My pod mate accidentally pushed his PR to main so that is why the link looks different



### What I Looked At

Ahmed's PR wired up the library and favorites functionality for Week 10. I focused on MediaDetailScreen.kt, MediaDetailViewModel.kt, 
DefaultMediaRepository.kt, and the new model files he created. The main goal was connecting the "+ Want To" and "Save" buttons to the
real API and replacing the fake library data with GET /library.

### What I Noticed

The most important thing I noticed is how Ahmed handles the library status check on load in MediaDetailViewModel. He calls 
both GET /library/{mediaId} and GET /favorites/{mediaId} inside the same loadMedia() coroutine, one after the other. This means 
the two network calls run sequentially the favorites check doesn't start until the library check finishes.

### Comments I Left

I left a comment on removeItem() pointing out the optimistic removal pattern and suggesting it should wait 
for API confirmation before updating the local list, consistent with how changeStatus() handles it.

## One Thing I Understood More Deeply

This week I understood why LibraryEntry and LibraryItem are two separate classes even though they represent the same concept. 
LibraryItem was the old fake data model — it used a LibraryStatus enum and was designed around what was convenient for the app to work with locally.

## One Thing I'm Still Confused About

I'm still confused about when to use optimistic updates versus waiting for API confirmation. Ahmed removes the item from the local list before 
the API confirms the delete worked. I understand this makes the UI feel faster  the item disappears immediately instead of after a network round trip.

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
