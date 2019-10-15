package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.humanizeDiff
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User (
    val id : String,
    var firstName : String?,
    var lastName : String?,
    var avatar : String?,
    var rating : Int = 0,
    var respect : Int = 0,
    val lastVisit : Date? = Date(),
    val isOnline : Boolean = false
) {
    constructor(id: String,firstName:String?,lastName:String?):this(id,firstName,lastName,null)

    constructor(id:String):this(id, "Leroy", "Jenkins $id")

    init {
        println("User $firstName $lastName created")
    }

    fun print()
    {
        println("""
            id : $id
            firstName : $firstName
            lastName : $lastName
            avatar : $avatar
            rating : $rating
            respect : $respect
            lastVisit : $lastVisit
            isOnline : $isOnline
        """.trimIndent())
    }

    fun toUserItem(): UserItem {
        val lastActivity = when {
            lastVisit == null -> "Еще ни разу не заходил"
            isOnline -> "online"
            else -> "Последний раз был ${lastVisit.humanizeDiff()}"
        }

        return UserItem(
            id,
            "${firstName.orEmpty()} ${lastName.orEmpty()}",
            Utils.toInitials(firstName,lastName),
            avatar,
            lastActivity,
            false,
            isOnline
         )
    }

    class Builder {
        lateinit var id : String
        var firstName : String? = null
        var lastName : String? = null
        var avatar : String? = null
        var rating : Int = 0
        var respect : Int = 0
        var lastVisit : Date? = Date()
        var isOnline : Boolean = false

        fun id(s:String) = apply { this.id = s }
        fun firstName(s:String?) = apply { firstName = s }
        fun lastName(s:String?) = apply { lastName = s }
        fun avatar(s:String?) = apply { avatar = s }
        fun rating(n:Int) = apply { rating = n }
        fun respect(n:Int) = apply { respect = n  }
        fun lastVisit(d:Date?) = apply { lastVisit = d }
        fun isOnline(b:Boolean) = apply { isOnline = b }
        fun build() = User(
            id ?: error("field id required"),
            firstName,
            lastName,
            avatar,
            rating,
            respect,
            lastVisit,
            isOnline
        )
    }

    companion object Factory{

        private var lastId:Int = -1

        fun makeUser(fullName:String?) : User
        {
            lastId++

            val (firstName,lastName) = Utils.parseFullName(fullName)

            return User(
                "$lastId",
                firstName,
                lastName
            )
        }
    }
}