package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils
import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView

fun User.toUserView() : UserView
{

    val initials = Utils.toInitials(firstName,lastName)
    val nickName = Utils.transliteration("$firstName $lastName")
    val status =
        when {
            lastVisit == null -> "not appeared"
            isOnline -> "online"
            else -> lastVisit.humanizeDiff()
        }

    return UserView(
        id = id,
        avatar = avatar,
        fullName = "$firstName $lastName",
        initials = initials,
        nickName = nickName,
        status = status


    )
}