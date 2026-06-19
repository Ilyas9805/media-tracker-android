# Week 04 Reflection

**Name:** Ilyas Ibrahim
**Date:** 06/18/26

---

## Commits This Week

**Link:** https://github.com/Ilyas9805/media-tracker-android/pull/4


**Reviewed:** *(Nicholas Chyrklund)*
**Link to my review:** https://github.com/NChyrklund/media-tracker-android/pull/5


### What I Looked At

Nicholas's PR touched several files but the main focus was RegisterScreen.kt and 
RegisterViewModel.kt. The goal was to move validation logic out of the screen and into 
the ViewModel, and wire the screen up to use the ViewModel's state instead of local remember variables.

### What I Noticed

I noticed that the validation logic is now cleanly inside onRegisterClick() in the ViewModel using 
a when block, which is a clear improvement over having it inside the screen's onClick lambda.

### Comments I Left

I left a positive comment on moving the validation into the ViewModel the screen 
shouldn't be making decisions about whether the input is valid, that's the ViewModel's job.

## One Thing I Understood More Deeply

This week I understood why validation belongs in the ViewModel and not the screen. 
Before I thought it didn't matter where it lived. But if validation is in the screen and 
you ever build a second version of that screen, you'd have to copy all the validation logic over. 
If it's in the ViewModel, any screen that uses it gets the same validation for free.



## One Thing I'm Still Confused About

I'm still confused about the sealed class for UI state. I understand that it limits 
the possible states to only the ones you define, but the syntax is still tripping me up.
I know that the teacher said not to worry about this topic as we will cover it in the coming
weeks but I tried listening to the quick explanation of the teacher and it was confusing.




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
