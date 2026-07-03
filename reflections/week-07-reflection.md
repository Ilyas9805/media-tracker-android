# Week 07 Reflection

**Name:** Ilyas Ibrahim
**Date:** 07/02/26

---

## Commits This Week

**Link:** https://github.com/Ilyas9805/media-tracker-android/pull/8




**Reviewed:** *(Nicholas Chyrklund)*
**Link to my review:** https://github.com/NChyrklund/media-tracker-android/pull/8/commits




### What I Looked At

Nicholas's PR built out the MediaDetailScreen with hardcoded fake data, matching the Week 7 handout 
requirements. I focused on MediaDetailScreen.kt and MediaDetailViewModel.kt. The PR adds a scrollable 
detail layout with a cover image area, title and creator credit, a star rating display, a stat grid 
(year, pages/runtime/seasons, genre), a description section, and a list of fake review cards. 
He also updated NavGraph.kt to properly pass the mediaId argument to the screen.

### What I Noticed

The most interesting thing I noticed is how Nicholas handles the star rating display. 
Instead of showing a single star icon next to the number like a lot of apps do, 
he uses repeat(rating.toInt()) to render individual filled star icons side by side, 
then calculates the remainder to decide whether to show a half star.

### Comments I Left

I left a positive comment on the star rating row, noting that using repeat() to 
render individual star icons is the right approach for matching the wireframe 
rather than just slapping a number next to a single icon.

## One Thing I Understood More Deeply

This week I understood why the stat grid uses three separate Card composables side by 
side instead of just three Text composables in a Row. At first it seemed like extra work 
for the same visual result. But the cards give each stat its own background, padding, and 
rounded corners — which makes the year, page count, and genre visually distinct and easier to read at a glance.

## One Thing I'm Still Confused About

I'm still confused about when to pass data to a screen as a navigation argument versus loading it 
inside the ViewModel. Right now mediaId comes in as a nav argument and the screen uses it directly in a when block to pick fake data

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
