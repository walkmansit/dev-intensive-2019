package ru.skillbranch.devintensive.extensions

fun String.truncate(trunLength:Int = 16) = if (this.trimEnd().length > trunLength) "${substring(0,trunLength).trimEnd()}..." else this.trimEnd()


const val OPEN_TAG = '<'
const val CLOSE_TAG = '>'
const val AMPERSAND = '&'
const val KAVICHKA1 = '\''
const val KAVICHKA2 = '"'
const val EMPTY_SYMBOL = ' '

const val amp = "&amp;"
const val lt = "&lt;"
const val qt = "&gt;"
const val quot = "&quot;"
const val apos = "&apos;"

fun String.stripHtml():String{
    val removed = replace("<[^>]*>".toRegex(),"")
    val sb = StringBuilder()

    var prev = '1'
    var append = false

    with(sb){
        for (ch in removed) {

            when (ch) {
                OPEN_TAG, CLOSE_TAG, AMPERSAND, KAVICHKA1,KAVICHKA2 -> append = false
                //KAVICHKA2 -> if (prev == KAVICHKA2) append = false else { append = true; append(KAVICHKA2)}
                EMPTY_SYMBOL -> append = prev != EMPTY_SYMBOL
                else -> append = true
            }
            if (append) append(ch)
            prev = ch
        }

    }
    return  sb.toString()
}
fun String.removeExtraWhiteSpaces(): String {

    var result = ""

    var prevChar = ""

    for ( char in this ) {
        if ( (prevChar == " " && char == ' ').not() ) {

            result += char
        }

        prevChar = char.toString()
    }

    return result
}