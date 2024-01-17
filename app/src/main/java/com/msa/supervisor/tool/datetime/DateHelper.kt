package com.msa.supervisor.tool.datetime

import java.text.SimpleDateFormat
import java.util.*
/**
 * create by Ali Soleymani.
 */

// var dateString = toString(date, DateFormat.MicrosoftDateTime, Locale.US)

object DateHelper {
    val Min = Date(1, 1, 1)

    fun toString(calendar: Calendar?, dateFormat: DateFormat?): String {
        if (calendar == null)
            return ""
        return when (dateFormat) {
            DateFormat.Complete -> "${toString(calendar.get(Calendar.YEAR))}/${
                toString(
                    calendar.get(
                        Calendar.MONTH
                    ) + 1
                )
            }/${toString(calendar.get(Calendar.DAY_OF_MONTH))} ${toString(calendar.get(Calendar.HOUR_OF_DAY))}:${
                toString(
                    calendar.get(Calendar.MINUTE)
                )
            }:${toString(calendar.get(Calendar.SECOND))}"

            DateFormat.Date -> "${toString(calendar.get(Calendar.YEAR))}/${
                toString(
                    calendar.get(
                        Calendar.MONTH
                    ) + 1
                )
            }/${toString(calendar.get(Calendar.DAY_OF_MONTH))}"

            DateFormat.Time -> "${toString(calendar.get(Calendar.HOUR_OF_DAY))}:${
                toString(
                    calendar.get(
                        Calendar.MINUTE
                    )
                )
            }:${toString(calendar.get(Calendar.SECOND))}"

            DateFormat.Simple -> "${toString(calendar.get(Calendar.YEAR))}${
                toString(
                    calendar.get(
                        Calendar.MONTH
                    ) + 1
                )
            }${toString(calendar.get(Calendar.DAY_OF_MONTH))}"

            DateFormat.MicrosoftDateTime -> "${toString(calendar.get(Calendar.YEAR))}-${
                toString(
                    calendar.get(Calendar.MONTH) + 1
                )
            }-${toString(calendar.get(Calendar.DAY_OF_MONTH))}T${toString(calendar.get(Calendar.HOUR_OF_DAY))}:${
                toString(
                    calendar.get(Calendar.MINUTE)
                )
            }:${toString(calendar.get(Calendar.SECOND))}.${toString(calendar.get(Calendar.MILLISECOND))}"

            DateFormat.FileName -> "${toString(calendar.get(Calendar.YEAR))}${
                toString(
                    calendar.get(
                        Calendar.MONTH
                    ) + 1
                )
            }${toString(calendar.get(Calendar.DAY_OF_MONTH))}T${toString(calendar.get(Calendar.HOUR_OF_DAY))}${
                toString(
                    calendar.get(Calendar.MINUTE)
                )
            }${toString(calendar.get(Calendar.SECOND))}_${toString(calendar.get(Calendar.MILLISECOND))}"

            else -> calendar.toString()
        }
    }

    private fun toString(number: Int): String {
        return if (number < 10) "0$number" else number.toString()
    }

    fun toString(date: Date?, dateFormat: DateFormat?, locale: Locale?): String {
        if (date == null)
            return ""
        val language = locale?.language ?: Locale.getDefault().language
        return if (language == "fa") {
            val calendar = com.msa.supervisor.tool.datetime.JalaliCalendar.getCalendar(date)
            toString(calendar, dateFormat)
        } else {
            val calendar = GregorianCalendar()
            calendar.time = date
            toString(calendar, dateFormat)
        }
    }


    /***
     *
     * @param timeOffset in seconds
     * @return
     */
    fun getTimeSpanString(timeOffset: Long): String {
        val hours = (timeOffset / 3600).toInt()
        val m = (timeOffset % 3600).toInt()
        val minutes = m / 60
        val seconds = m % 60
        return "${toString(hours)}:${toString(minutes)}:${toString(seconds)}"
    }

    fun compareDates(date1: String, date2: String): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dateObj1 = sdf.parse(date1)
        val dateObj2 = sdf.parse(date2)

        return dateObj1.compareTo(dateObj2)
    }
    fun getTimeHMString(timeOffset: Long): String {
        val hours = (timeOffset / 3600).toInt()
        val m = (timeOffset % 3600).toInt()
        val minutes = m / 60
        return "${toString(hours)}:${toString(minutes)}"
    }

    interface OnDateSelected {
        fun run(calendar: Calendar)
    }

    val fa = Locale("fa", "Iran")
}
