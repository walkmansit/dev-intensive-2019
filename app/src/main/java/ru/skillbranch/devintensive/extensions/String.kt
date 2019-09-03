package ru.skillbranch.devintensive.extensions

fun String.truncate(trunLength:Int = 16) = if (this.trimEnd().length > trunLength) "${substring(0,trunLength).trimEnd()}..." else this.trimEnd()


const val OPEN_TAG = '<'
const val CLOSE_TAG = '>'
const val ESCAPE_SYMBOL = '&'
const val EMPTY_SYMBOL = ' '

fun String.stripHtml():String{
    val sb = StringBuilder()


    var skipByHtml = false
    var prevChar = '1'

    for (c in this){

        var skip = false

        when (c){
            OPEN_TAG -> skipByHtml = true
            EMPTY_SYMBOL -> if (prevChar == EMPTY_SYMBOL) skip = true
            ESCAPE_SYMBOL -> skip = true
            else -> if (prevChar == CLOSE_TAG) skipByHtml = false
        }

        if (!(skip || skipByHtml)) sb.append(c)

        prevChar = c
    }



    return sb.toString()
}