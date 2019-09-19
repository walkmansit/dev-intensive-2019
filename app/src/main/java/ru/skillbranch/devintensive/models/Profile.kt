package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates

data class Profile (

   val firstName : String,
   val lastName : String,
   val about : String,
   var repository : String,
   val rating : Int = 0,
   val respect : Int = 0
) {


    private val repoExcludeWords = listOf(
        "enterprise",
        "features",
        "topics",
        "collections",
        "trending",
        "events",
        "marketplace",
        "pricing",
        "nonprofit",
        "customer-stories",
        "security",
        "login",
        "join"
    )

    public var repositoryValid:Boolean = true
    //public val validateRepositoryMessage = "Невалидный адрес репозитория"

    val rank : String = "Junior Android Developer"
    val nickName : String = Utils.transliteration(Utils.transliteration("$firstName $lastName"),"_")

    fun toMap(): Map<String, Any> = mapOf(
        "nickName" to nickName,
        "rank" to rank,
        "firstName" to firstName,
        "lastName" to lastName,
        "about" to about,
        "repository" to repository,
        "rating" to rating,
        "respect" to respect
    )

    fun toValidateMap(): Map<String, Boolean> = mapOf(
        "repositoryValid" to repositoryValid
    )

    fun toValidateErrors(): Map<String, String> = mapOf(
        "repositoryValid" to "Невалидный адрес репозитория"
    )

    fun repositoryValid() : Boolean{
        if (repository.isEmpty()) return true
        else{
            val pattern = "(https://)?(www.)?github.com/(\\w+)".toRegex()
            val result = pattern.matchEntire(repository)
            if (result != null)
                return !(repoExcludeWords.contains(result?.groupValues?.last()))
            else return  false
        }
    }
}