# Week 12 Reflection — Bonus Feature Sprint (Week 2 of 2, Final)


**Name:** Ilyas Ibrahim
**Date:** 08/06/26
**My assigned bonus feature:** *(Write Review / Quotes / Priorities)*

---

## Commits This Week

**Link:** https://github.com/Ilyas9805/media-tracker-android/pull/12

---

## Code Review

**Reviewed:** *(Nicholas Chyrklund )*
**Link to my review:** https://github.com/NChyrklund/media-tracker-android/pull/12/commits

### What I Looked At

reviewed the changes to the review feature, including the interactive star rating, the 500-character limit for reviews, updates to the review sorting logic, and the ViewModel changes that connect the UI with the repository.

### What I Noticed

I noticed the code cleanly connects the UI to the ViewModel so the selected star rating is actually saved when a review is submitted. I also liked that reviews are sorted with the current user's review first, making it easier for users to find their own review. One thing I noticed is that getReviews() now returns a nullable list, which required additional null checks in the UI.

### Comments I Left

I left a comment saying, Nice improvement overall, I really like that the star rating is now interactive and that the user's own review is displayed first. I had one question, was there a specific reason for changing getReviews() to return List<Review>? instead of always returning an empty list when there are no reviews? Returning an empty list might reduce the need for null checks throughout the UI.

## Bonus Feature — Final Status



**What works end-to-end, right now:**

Tapping "My Priorities" from the Library tab navigates to the Priorities screen
The Priorities screen loads real data from GET /priorities, sorted by orderIndex
Items show the correct cover image (or icon fallback), title, creator credit, priority badge (High/Medium/Low), estimated hours, and notes
Tapping the three dot menu on any "Want To" library item shows an "Add to Priorities" option
The Add to Priorities dialog lets you select a priority level (High/Medium/Low), enter estimated hours, and optionally add notes
Confirming the dialog calls PUT /priorities and the item appears in the Priorities screen on next load

**Tests written for this feature:**

Two unit tests written in PrioritiesViewModelTest.kt:

removePriority_removesCorrectItemFromLocalState, given a list of two priority items, calling filter { it.mediaId != 1 } (the logic used in removePriority()) removes the correct item and leaves only the remaining one. Verifies the remove logic works correctly on local state.

priorityList_enforcesMaximumOf5Items, given a full list of 5 priorities, verifies that priorities.size < 5 returns false, confirming the 5-item cap check correctly blocks adding a sixth item.

**Known gaps or rough edges going into demos:**

Remove only works locally. Since PUT /priorities is a single-item upsert and there is no DELETE /priorities/{mediaId} endpoint, removed items reappear when you navigate away and come back. This is a server-side API limitation, not a bug in the client code.
The 5-item limit is not yet enforced in the UI — the "Add to Priorities" option stays visible even when the list is full.
Drag-to-reorder is not implemented yet.
Loading, empty, and error states on the Priorities screen are basic — the empty state message is there but loading and error states need polish.



## One Thing I Understood More Deeply

Building the priorities feature from scratch — designing the data model, wiring the API, building the ViewModel, and connecting the UI — made me understand how much a missing API endpoint affects what you can build on the client. I spent time trying to implement remove in different ways before realizing the limitation wasn't in my code at all



## One Thing I'm Still Confused About

I'm still confused about the right way to handle the orderIndex field. Right now when you add a new item it gets assigned orderIndex = current list size, which puts it at the end. But if you remove an item locally and then add a new one, the orderIndex values in the local list are out of sync with what the server has. I'm not sure whether I should re-index the entire list every time an item is added or removed, or whether the server handles that automatically and I should just trust GET /priorities to give me the correct order on next load.

## Anything Else *(optional)*



## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Honest final-status report — what works end-to-end, what's rough, what's tested — plus a specific, genuine "Understood More Deeply" that reflects on the sprint as a whole, not just this week. | Present but vague, or only reports on this week rather than the feature's overall state. | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** same as every other week — I check the link before grading.
