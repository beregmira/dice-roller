package ru.beregstudio.diceroller.domain

import java.util.Calendar

/**
 * Определяет тематический фон по текущей дате.
 *
 * Использует [Calendar], а не `java.time`, чтобы не включать desugaring при `minSdk 23`.
 * Метод чистый относительно переданного [Calendar], поэтому легко тестируется.
 */
object HolidayCalendar {

    /**
     * Праздничные окна в формате `[месяц, день]`.
     *
     * Месяцы — как в [Calendar]: 0 = январь … 11 = декабрь.
     * Если начало окна «больше» конца по календарю (декабрь → январь),
     * окно считается переходящим через Новый год.
     *
     * Чтобы добавить праздник — достаточно одной строки в этой таблице.
     */
    private val windows: List<HolidayWindow> = listOf(
        HolidayWindow(BackgroundTheme.HALLOWEEN, from = MonthDay(9, 24), to = MonthDay(10, 4)),
        HolidayWindow(BackgroundTheme.NEW_YEAR, from = MonthDay(11, 15), to = MonthDay(0, 10)),
        HolidayWindow(BackgroundTheme.VALENTINE, from = MonthDay(1, 10), to = MonthDay(1, 15)),
    )

    /**
     * Возвращает тему для указанной даты (по умолчанию — «сегодня»).
     *
     * @return [BackgroundTheme.NONE], если дата не попадает ни в одно праздничное окно.
     */
    fun currentTheme(now: Calendar = Calendar.getInstance()): BackgroundTheme {
        val today = MonthDay(now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
        return windows.firstOrNull { it.contains(today) }?.theme ?: BackgroundTheme.NONE
    }
}

/** День года без года: месяц (0-11) + день месяца. Сравним лексикографически. */
private data class MonthDay(val month: Int, val day: Int) : Comparable<MonthDay> {
    override fun compareTo(other: MonthDay): Int =
        if (month != other.month) month - other.month else day - other.day
}

/** Праздничное окно дат, возможно переходящее через Новый год. */
private data class HolidayWindow(
    val theme: BackgroundTheme,
    val from: MonthDay,
    val to: MonthDay,
) {
    private val wrapsYear: Boolean get() = from > to

    fun contains(day: MonthDay): Boolean =
        if (wrapsYear) day >= from || day <= to else day in from..to
}
