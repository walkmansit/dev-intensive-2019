package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = SECOND * 60
const val HOUR = MINUTE * 60
const val DAY = HOUR * 24

fun Date.format(pattern:String = "HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern)
    return dateFormat.format(this)
}


fun Date.add(value:Int, unit:TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when(unit){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}

/*
0с - 1с "только что"
1с - 45с "несколько секунд назад"
45с - 75с "минуту назад"
75с - 45мин "N минут назад"
45мин - 75мин "час назад"
75мин 22ч "N часов назад"
22ч - 26ч "день назад"

26ч - 360д "N дней назад"
>360д "более года назад"
*/
fun Date.humanizeDiff():String {

    fun getRoundedUnit(ticks:Long, unit:TimeUnits):Long{
        return when(unit){
            TimeUnits.SECOND -> ticks / SECOND
            TimeUnits.MINUTE -> ticks / MINUTE
            TimeUnits.HOUR -> ticks / HOUR
            TimeUnits.DAY -> ticks / DAY
        }
    }

    fun getBase(ticks:Long): Pair<String,Boolean>
    {
        val abs = abs(ticks)

        return when{
            abs == 0L || abs == SECOND -> "только что" to false
            abs > SECOND && abs <= 45*SECOND -> "несколько секунд" to true
            abs > 45*SECOND && abs <= 75*SECOND -> "минуту" to true
            abs > 75*SECOND && abs <= 45*MINUTE  -> "${TimeUnits.MINUTE.plural(getRoundedUnit(abs,TimeUnits.MINUTE).toInt())}" to true
            abs > 45*MINUTE && abs <= 75*MINUTE -> "час" to true
            abs > 75*MINUTE && abs <= 22*HOUR  -> "${TimeUnits.HOUR.plural(getRoundedUnit(abs,TimeUnits.HOUR).toInt())}" to true
            abs > 22* HOUR && abs <= 26* HOUR -> "день" to true
            abs > 26* HOUR && abs <= 360*DAY -> "${TimeUnits.DAY.plural(getRoundedUnit(abs,TimeUnits.DAY).toInt())}" to true
            else -> if (ticks > 0L) "более года назад" to false else "более чем через год" to false

        }
        return "" to false
    }

    val diffDate = Date().time - time //разница в тиках, >0 - назад, <0 - через

    val (base,appendDrection) = getBase(diffDate)

    return if (appendDrection)  if (diffDate > 0L) "$base назад" else "через $base" else base
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

fun TimeUnits.plural(value:Int):String{
    return when(this)
    {
        TimeUnits.SECOND -> "$value ${pluralForm(value,"секунду","секунды","секунд")}"
        TimeUnits.MINUTE -> "$value ${pluralForm(value,"минуту","минуты","минут")}"
        TimeUnits.HOUR -> "$value ${pluralForm(value,"час","часа","часов")}"
        TimeUnits.DAY -> "$value ${pluralForm(value,"день","дня","дней")}"
    }
}

private fun pluralForm(value:Int,form1:String,form2:String,form5:String):String{
    val n = abs(value) % 100
    val n1 = n % 10

    return when
    {
        n in 11..19 -> form5
        n1 in 2..4 -> form2
        n1 == 1 -> form1
        else -> form5
    }
}