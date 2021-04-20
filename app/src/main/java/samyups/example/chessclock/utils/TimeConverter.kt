package samyups.example.chessclock.utils

fun timeToMilliSec(hours: Long, minutes: Long, seconds: Long): Long {
    val hr = hours * 3_600_000
    val min = minutes * 60000
    val sec = seconds * 1000
    return hr + min + sec
}

fun milliToMinSec(startTime : Long): String {
    val hours : Long = startTime / 3_600_000
    val timeMinusHours : Long = startTime - (hours * 3_600_000)
    val minute : Long = (timeMinusHours / 1000) / 60
    val seconds  = ((timeMinusHours / 1000) % 60).toInt()
    val convertedTime : String

    if (seconds < 10 && hours < 1) {
        convertedTime = "$minute:0$seconds" // ex: 0:08 instead of 0:8 for 8 seconds
    } else if (seconds < 10 && minute >= 10 && hours >= 1) {
        convertedTime = "$hours:$minute:0$seconds"
    } else if (seconds < 10 && minute <= 10 && hours >= 1) {
        convertedTime = "$hours:0$minute:0$seconds"
    } else if (seconds >= 10 && minute <= 10 && hours >= 1) {
        convertedTime = "$hours:0$minute:$seconds"
    }else if (seconds >= 10 && minute >= 10 && hours >= 1) {
        convertedTime = "$hours:$minute:$seconds"
    }else {
        convertedTime = "$minute:$seconds"
    }
    return convertedTime
}