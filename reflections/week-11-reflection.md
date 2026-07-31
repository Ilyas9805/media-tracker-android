# Week 11 Reflection — Bonus Feature Sprint (Week 1 of 2)

**Name:** Ilyas Ibrahim
**Date:** 07/30/26
**My assigned bonus feature:** *(Write Review / Quotes / Priorities)*

---

## Commits This Week


**Link:** https://github.com/Ilyas9805/media-tracker-android/pull/11


---

## Code Review



**Reviewed:** *(Sadiq Ahmed)*
**Link to my review:** https://github.com/ahmedsadiq04/media-tracker-android/pull/12

### What I Looked At

I looked at Sadiq's Quotes feature implementation, including the new Quotes screen, the QuotesViewModel, the repository and API changes, and the navigation updates. I also reviewed the changes to the quote input modal and the quote data model.

### What I Noticed

I noticed that the feature was wired through multiple layers of the app instead of only creating the UI. He added the API endpoint to retrieve quotes, created a ViewModel to load the user's quotes, added a new Quotes screen to display them, and connected the screen through the navigation and bottom navigation bar.

### Comments I Left

I like how you connected the Quotes feature through the API, repository, ViewModel, navigation, and UI instead of only building the screen. The code is organized well and follows the project's MVVM structure.

---

## Bonus Feature Progress



**What's working:**

My Priorities feature is mostly working. I created the Priority data model and connected it to the repository and API layer. I added functions to load priorities, add/update priorities, and remove priorities. The Priorities screen displays real data from the API and shows the media title, cover image, creator information, priority level, estimated time, and notes.


**What's still stubbed, fake, or not started:**

The feature still needs some improvements, including better error handling, enforcing the maximum number of priorities, and improving some UI interactions. Drag-and-drop reordering is not implemented yet.

**What I'm blocked on, if anything:**

I am not blocked right now, but I need to continue testing the API responses and make sure priority updates and removals work correctly in all cases.

## One Thing I Understood More Deeply

I understood more deeply how the different parts of an Android app connect together. Building this feature helped me understand how the UI, ViewModel, repository, and API work together instead of only working on one file at a time. I also learned how StateFlow helps update the UI automatically when data changes.



---

## One Thing I'm Still Confused About

I am still learning the best way to handle errors between the repository and ViewModel layers. I understand how to make API calls work, but I want to improve how to show useful error messages to users when something goes wrong.



---

## Anything Else *(optional)*

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Concrete progress report (what's wired, what's not) plus specific, honest "Understood More Deeply" and "Still Confused" sections. | Present but vague — "I worked on my feature" with no specifics on what's actually working. | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match.
