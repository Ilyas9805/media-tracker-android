# Week 02 Reflection

**Name:** Ilyas Ibrahim
**Date:** 05/28/26

---

## Commits This Week

<!-- Paste a link to your commits for this week. The easiest way: go to your repo on GitHub,
     click "commits", and copy the URL after filtering by your name or branch. -->

**Link:**
https://github.com/Ilyas9805/media-tracker-android/pull/2
## Code Review

<!-- Every week you leave a review on a pod mate's pull request. Fill in both parts below.
     Part 1 is the link — I will verify the review exists on GitHub.
     Part 2 is your written assessment — what you actually looked at and what you found. -->

**Reviewed:** *(Nicholas Chyrklund)*
**Link to my review:**
https://github.com/NChyrklund/media-tracker-android/pull/2/changes/c96f10489126aa089e171f68079c6730d7395190


### What I Looked At

<!-- Walk through the code you reviewed. What was the PR trying to do? Which files or
     functions did you focus on? -->

Nicholas's PR touched five files, the bottom navigation bar, the route constants, 
the Library screen and its ViewModel, and MyProfileScreen. The main theme of the PR was bug fixes

### What I Noticed

<!-- Be specific. Did you spot a potential bug? A pattern that could cause problems? Something
     done well that you want to call out? "I looked at the ViewModel and everything seemed fine"
     is not specific enough. Name the thing you noticed and explain why it matters. -->

I noticed that the thread.sleep(800) could be deleted and it won't make drastic changes to the
code.

### Comments I Left

<!-- Briefly summarize the comments you left on the PR. If you left a positive comment,
     say what it was. If you left a suggestion, say what you suggested and why. -->

I left a comment for a nick saying that it was a nice catch seeing and fixing the hierarchy.
---

## One Thing I Understood More Deeply

<!-- Be specific. Don't write "I learned about ViewModels." Write what specifically clicked —
     what was confusing before, what made it make sense, and how you'd explain it to someone else.
     There are no wrong answers here. -->
---

Before this week I thought GlobalScope and viewModelScope were basically the same thing
they both launch coroutines. What clicked for me is that a coroutine needs an owner that controls when it 
gets cancelled. GlobalScope has no owner, so it just runs until it finishes regardless of what happens to the 
screen or ViewModel.

## One Thing I'm Still Confused About

<!-- Be honest. This is the most useful part of the reflection for me — it tells me where to
     spend more time in class. You will not lose points for being confused. -->
---

I understand that collectAsStateWithLifecycle() is preferred over collectAsState() for 
flows in Compose, but I'm not fully clear on what the practical difference is in most situations.

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
