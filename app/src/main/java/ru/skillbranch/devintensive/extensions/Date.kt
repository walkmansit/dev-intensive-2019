package ru.skillbranch.devintensive.extensions

import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = SECOND * 60
const val HOUR = MINUTE * 60
const val DAY = HOUR * 24

fun Date.format(pattern:String = "HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern);
    return dateFormat.format(this)
}


fun Date.add(value:Int, unit:TimeUnit = TimeUnit.SECOND): Date {
    var time = this.time

    time += when(unit){
        TimeUnit.SECOND -> value * SECOND
        TimeUnit.MINUTE -> value * MINUTE
        TimeUnit.HOUR -> value * HOUR
        TimeUnit.DAY -> value * DAY
    }

    this.time = time
    return this
}

fun Date.humanizeDiff():String {
    //TODO implement

}

enum class TimeUnit {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}