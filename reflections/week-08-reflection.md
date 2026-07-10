# Week 08 Reflection

**Name:** Ilyas Ibrahim
**Date:** 07/09/26

---

## Commits This Week

**Link:** https://github.com/Ilyas9805/media-tracker-android/pull/9




**Reviewed:** *(Nicholas Chyrklund)*
**Link to my review:** https://github.com/NChyrklund/media-tracker-android/pull/9





### What I Looked At

Nicholas's PR built out the MediaDetailScreen and WriteReviewScreen for Week 8. I focused on 
MediaDetailViewModel.kt, DefaultMediaRepository.kt, and WriteReviewScreen.kt. The main goals 
were wiring GET /media/{id} to the detail screen and building the review form with star rating, 
text input, and a post button.

### What I Noticed

The most interesting thing I noticed is how Nicholas handles the UiState in MediaDetailViewModel 
he uses a sealed class with Loading, Success, Error, and NotFound as separate states rather than 
combining them into one. This means the screen can react differently to a network error versus a missing item

### Comments I Left

left a positive comment on the UiState sealed class pattern in MediaDetailViewModel, noting that separating
NotFound from Error is the right call since they're genuinely different failure modes that deserve different 
UI responses.

## One Thing I Understood More Deeply

This week I understood what @Path does in a Retrofit interface. Before I only knew @Query which adds parameters
to the end of a URL like ?query=dune. But GET /media/{id} is different — the id is part of the URL path itself, not 
a query parameter. @Path("id") tells Retrofit to substitute the {id} placeholder in the route with the actual value you pass in.

## One Thing I'm Still Confused About

I'm still confused about why library items show "Media not found" when tapped even though they're valid items. I know it's because 
the fake IDs (1, 2, 3) don't exist in the real Supabase database, but I'm not sure what the right long-term fix is. Should the library 
eventually store the real database IDs when items are added via POST /library? Or is there some other way to link local library items 
to real media records without hardcoding IDs?

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
