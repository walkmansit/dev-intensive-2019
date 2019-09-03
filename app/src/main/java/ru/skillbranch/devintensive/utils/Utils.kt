package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?):Pair<String?,String?> {
        val parts:List<String>? = fullName?.split(" ")
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return firstName to lastName
    }

    fun toInitials(firstName:String?, lastName:String?) : String? {
        var result = "${firstName?.trim()?.getOrNull(0)?.toUpperCase() ?: ""}" +
                "${lastName?.trim()?.getOrNull(0)?.toUpperCase() ?: ""}"

        return if (result.isNullOrEmpty()) null else result
    }

    fun transliteration(payload:String, divider:String = " "):String {

        fun translitWord(word:String?):String{

            if (word.isNullOrEmpty()) return ""
            else {
                val sb = StringBuilder()

                for (letter in word) {

                    if (transliteralMap.containsKey(letter.toLowerCase())) {
                        sb.append(transliteralMap[letter.toLowerCase()])
                    }
                    else
                        sb.append(letter)

                }

                var result = sb.toString()

                return result.replaceFirst(result[0],result[0].toUpperCase())
            }
        }

        val parts:List<String> = payload.split(" ")

        var first = translitWord(parts.getOrNull(0))
        val second = translitWord(parts.getOrNull(1))

        return if(second.isNullOrEmpty()) first else "$first$divider$second"

    }

    private val transliteralMap : Map<Char,String> = mapOf(
        'а' to "a",
        'б' to "b",
        'в' to "v",
        'г' to "g",
        'д' to "d",
        'е' to "e",
        'ё' to "e",
        'ж' to "zh",
        'з' to "z",
        'и' to "i",
        'й' to "i",
        'к' to "k",
        'л' to "l",
        'м' to "m",
        'н' to "n",
        'о' to "o",
        'п' to "p",
        'р' to "r",
        'с' to "s",
        'т' to "t",
        'у' to "u",
        'ф' to "f",
        'х' to "h",
        'ц' to "c",
        'ч' to "ch",
        'ш' to "sh",
        'щ' to "sh'",
        'ъ' to "",
        'ы' to "i",
        'ь' to "",
        'э' to "e",
        'ю' to "yu",
        'я' to "ya"
    )
}