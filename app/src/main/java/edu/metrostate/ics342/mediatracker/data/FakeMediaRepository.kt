package edu.metrostate.ics342.mediatracker.data

import edu.metrostate.ics342.mediatracker.data.model.ActivityEvent
import edu.metrostate.ics342.mediatracker.data.model.LibraryItem
import edu.metrostate.ics342.mediatracker.data.model.LibraryStatus
import edu.metrostate.ics342.mediatracker.data.model.Media
import edu.metrostate.ics342.mediatracker.data.model.UserProfile

object FakeMediaRepository {

    val mediaList: List<Media> = listOf(
        Media(
            id            = 1,
            mediaType     = "book",
            title         = "Dune",
            author        = "Frank Herbert",
            publishedYear = 1965,
            averageRating = 4.8f,
            ratingCount   = 1847,
            genres        = listOf("Science Fiction", "Epic"),
            description   = "Set in the distant future amidst a feudal interstellar society, Dune tells the story of young Paul Atreides as his family accepts stewardship of the desert planet Arrakis, the only source of the most precious substance in the universe.",
            pageCount     = 688,
            reviewCount   = 42
        ),
        Media(
            id             = 2,
            mediaType      = "movie",
            title          = "Arrival",
            director       = "Denis Villeneuve",
            publishedYear  = 2016,
            averageRating  = 4.5f,
            ratingCount    = 1534,
            genres         = listOf("Science Fiction", "Drama"),
            description    = "When mysterious spacecraft touch down across the globe, an elite team is put together to investigate, including linguistics professor Louise Banks.",
            runtimeMinutes = 116,
            reviewCount    = 18
        ),
        Media(
            id            = 3,
            mediaType     = "show",
            title         = "Severance",
            creator       = "Dan Erickson",
            network       = "Apple TV+",
            publishedYear = 2022,
            averageRating = 4.9f,
            ratingCount   = 1432,
            genres        = listOf("Thriller", "Science Fiction", "Drama"),
            description   = "Mark leads a team of office workers whose memories have been surgically divided between their work and personal lives.",
            seasonCount   = 2,
            episodeCount  = 19,
            reviewCount   = 31
        ),
        Media(
            id            = 4,
            mediaType     = "book",
            title         = "Foundation",
            author        = "Isaac Asimov",
            publishedYear = 1951,
            averageRating = 4.7f,
            ratingCount   = 1623,
            genres        = listOf("Science Fiction"),
            description   = "The Foundation series chronicles the fall and rise of a galactic empire over thousands of years.",
            pageCount     = 255,
            reviewCount   = 28
        ),
        Media(
            id             = 5,
            mediaType      = "movie",
            title          = "Interstellar",
            director       = "Christopher Nolan",
            publishedYear  = 2014,
            averageRating  = 4.6f,
            ratingCount    = 2341,
            genres         = listOf("Science Fiction", "Adventure"),
            description    = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
            runtimeMinutes = 169,
            reviewCount    = 35
        ),
        Media(
            id            = 6,
            mediaType     = "show",
            title         = "Dark",
            creator       = "Baran bo Odar",
            network       = "Netflix",
            publishedYear = 2017,
            averageRating = 4.8f,
            ratingCount   = 1563,
            genres        = listOf("Science Fiction", "Thriller", "Mystery"),
            description   = "A missing child sets four families on a frantic hunt for answers as they unearth a mind-bending mystery that spans three generations.",
            seasonCount   = 3,
            episodeCount  = 26,
            reviewCount   = 22
        )
    )

    fun getMediaById(id: Int): Media? = mediaList.find { it.id == id }

    var currentUser = UserProfile(
        id          = "user-001",
        email       = "ilyas.ibrahim@my.metrostate.edu",
        username    = "ics342-ilyas-ibrahim",
        displayName = "Ilyas Ibrahim"
    )

    val followers: List<UserProfile> = listOf(
        UserProfile(
            id          = "user-002",
            email       = "nicholas@example.com",
            username    = "nicholas_c",
            displayName = "Nicholas Chyrklund"
        ),
        UserProfile(
            id          = "user-003",
            email       = "ahmed@example.com",
            username    = "ahmed_s",
            displayName = "Ahmed Sadiq"
        )
    )

    val following: List<UserProfile> = listOf(
        UserProfile(
            id          = "user-002",
            email       = "nicholas@example.com",
            username    = "nicholas_c",
            displayName = "Nicholas Chyrklund"
        )
    )

    val libraryItems: List<LibraryItem> = listOf(
        LibraryItem(
            mediaId   = 1,
            userId    = "user-001",
            media     = mediaList[0],
            status    = LibraryStatus.WANT_TO,
            addedAt   = "2024-01-01",
            updatedAt = "2024-01-01"
        ),
        LibraryItem(
            mediaId   = 2,
            userId    = "user-001",
            media     = mediaList[1],
            status    = LibraryStatus.IN_PROGRESS,
            addedAt   = "2024-01-05",
            updatedAt = "2024-01-05"
        ),
        LibraryItem(
            mediaId   = 3,
            userId    = "user-001",
            media     = mediaList[2],
            status    = LibraryStatus.FINISHED,
            addedAt   = "2024-01-10",
            updatedAt = "2024-01-10"
        )
    )

    val activityFeed: List<ActivityEvent> = listOf(
        ActivityEvent(
            id           = 1,
            userId       = "user-002",
            activityType = "finished",
            mediaId      = 1,
            createdAt    = "2024-01-22",
            user         = followers[0],
            media        = mediaList[0]
        ),
        ActivityEvent(
            id           = 2,
            userId       = "user-003",
            activityType = "started",
            mediaId      = 2,
            createdAt    = "2024-01-21",
            user         = followers[1],
            media        = mediaList[1]
        ),
        ActivityEvent(
            id           = 3,
            userId       = "user-002",
            activityType = "review",
            mediaId      = 3,
            rating       = 5,
            reviewText   = "One of the best shows I have ever seen.",
            createdAt    = "2024-01-20",
            user         = followers[0],
            media        = mediaList[2]
        ),
        ActivityEvent(
            id           = 4,
            userId       = "user-003",
            activityType = "added",
            mediaId      = 4,
            createdAt    = "2024-01-19",
            user         = followers[1],
            media        = mediaList[3]
        ),
        ActivityEvent(
            id           = 5,
            userId       = "user-002",
            activityType = "finished",
            mediaId      = 5,
            createdAt    = "2024-01-18",
            user         = followers[0],
            media        = mediaList[4]
        )
    )
}