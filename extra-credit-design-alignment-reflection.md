# Extra Credit Reflection — Design Alignment


**Name:** Ilyas Ibrahim
**Date:** 07/01/2026

---

## The Audit

*Before touching any code, compare your running app to the wireframes screen by screen. List what you found — be specific about which screen, which component, and what was different. "The colors were off" is not specific. "The active chip on the Search screen was using amber instead of primary container (#E0E0FF)" is specific.*

*List at least five concrete differences you found:*

1. Login and Register screens: Primary color was #4F46E5 instead of the
   spec's #6366F1 — buttons, active nav icons, and links were all the
   wrong shade of indigo.
2. Register screen: SmartDisplay icon tint was set to primaryContainer
   (light purple) instead of onPrimaryContainer
3. Bottom nav bar: Selected tab icons and labels were amber/yellow
   instead of the spec's primary purple
4. Search result cards: All media type icons had the same grey
   surfaceVariant background instead of distinct colors per type
   (purple for books, pink for movies, amber for shows).
5. Media Tracker title on Login screen was purple instead of the
   spec's near-black onBackground color.

---

## What You Changed

*Walk through the changes you made. For each area of the design system, describe 
what the code looked like before and what you changed it to. Reference specific files and Composables.*

### Color System

Before: Color.kt had Primary = 0xFF4F46E5, Secondary = 0xFFD97706 (amber), no status 
colors at all, and Tertiary = 0xFF0D9488 (teal). After: rewrote all token values to match 
the spec — Primary = #6366F1, Secondary = #DB2777 (pink for movies), Tertiary = #D97706 (amber, moved from Secondary to Tertiary for ratings). 
Added six new status tokens (WantTo, WantToContainer, InProgress, InProgressContainer, Finished, FinishedContainer) and their on-container pairs.

### Typography

Type.kt previously only defined 5 styles (bodyLarge, bodyMedium, titleLarge, titleMedium, labelSmall) 
and left titleSmall, labelMedium, headlineLarge, bodySmall, labelLarge undefined, meaning they fell back to 
Material 3 defaults with potentially wrong weights. Updated to define all 11 styles explicitly, applying FontWeight.Bold (700) 
to headline styles, FontWeight.SemiBold (600) to title and label styles, and FontWeight.Normal (400) to body styles per spec.

### Buttons

Added shape = RoundedCornerShape(20.dp) and ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, 
contentColor = MaterialTheme.colorScheme.onPrimary) to every Button in LoginScreen.kt and RegisterScreen.kt.

### Text Fields

Added shape = RoundedCornerShape(8.dp) and colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary) 
to every OutlinedTextField in LoginScreen.kt and RegisterScreen.kt. Updated search bar inputs in SearchScreen.kt and SearchResultsScreen.kt to use RoundedCornerShape(28.dp) (pill shape) per spec.

### Other Components

Filter chips: Added shape = RoundedCornerShape(8.dp), FilterChipDefaults.filterChipColors(...) with explicit active (primaryContainer/onPrimaryContainer) 
and inactive (surface/onSurfaceVariant) states, and an outline border for unselected state. Applied consistently in LibraryScreen.kt and SearchComponents.kt.

Cards: Updated MediaResultCard in SearchComponents.kt and LibraryItemCard in LibraryScreen.kt — elevation 1.dp → 2.dp, explicit containerColor = surface to 
prevent M3 tonal elevation tinting.

Media type icon backgrounds: Applied per-type container colors to the icon box in MediaResultCard — books use 
primaryContainer, movies use secondaryContainer, shows use tertiaryContainer.

Bottom nav: Already had the correct colors from an earlier implementation — selectedIconColor = primary, indicatorColor = primaryContainer, unselectedIconColor = onSurfaceVariant.

---

## What Was Hard

*The most challenging part was applying the status badge colors (WantTo, InProgress, Finished). I expected to access them 
through MaterialTheme.colorScheme like every other color in the app, but colorScheme only has fixed slots for primary, secondary, 
tertiary, and error — there's no way to add custom slots to it. So instead of writing MaterialTheme.colorScheme.wantToContainer, 
I had to import the raw color values directly from Color.kt and pass them in manually. It was the one place in the whole assignment 
where I couldn't follow the same pattern I used everywhere else.

---

## What You Understand Now

Before this assignment I thought you could put any color you wanted into MaterialTheme and access it anywhere. What I 
understand now is that MaterialTheme.colorScheme has a fixed set of named slots — primary, secondary, tertiary, error, and their 
containers — and you can't add new ones. You can change what color goes in each slot, but the slots themselves are set by Material 3. 
That's why the status badge colors had to be referenced directly from Color.kt instead of going through the theme — there simply 
wasn't a slot for them. Every standard M3 component like Button or FilterChip works through those fixed slots, 
which is why theming them is so clean. Custom semantic colors that don't fit those slots have to live outside the system.

---

## Self-Assessment


| Section | Possible | My Estimate |
|:---|:---:|:---:|
| Color System | 13 |11 |
| Typography | 5 | 5|
| Component Styling | 15 | 15|
| Navigation & Cards | 5 | 5|
| Reflection | 12 | 10|
| **Total** | **50** | 46|

*One thing I think I did well:*

Component styling, buttons, text fields, filter chips, and cards all match the spec exactly with no hardcoded color literals.

*One thing I know I left incomplete or could have done better:*

One thing I could have done better is checking every screen in the app for hardcoded colors and font weights, 
not just the ones I actively worked on
