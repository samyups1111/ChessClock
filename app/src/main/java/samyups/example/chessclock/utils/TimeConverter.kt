package samyups.example.chessclock.utils

fun timeToMilliSec(minutes: Long, seconds: Long): Long {
    val min = minutes * 60000
    val sec = seconds * 1000
    return min + sec
}

fun milliToMinSec(startTime : Long): String {
    val minute : Long = (startTime / 1000) / 60
    val seconds  = ((startTime / 1000) % 60).toInt()
    val convertedTime : String

    if (seconds < 10) {
        convertedTime = "$minute:0$seconds" // ex: 0:08 instead of 0:8 for 8 seconds
    } else {
        convertedTime = "$minute:$seconds"
    }
    return convertedTime
}