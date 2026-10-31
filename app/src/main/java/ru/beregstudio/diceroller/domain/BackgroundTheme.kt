package ru.beregstudio.diceroller.domain

import androidx.annotation.DrawableRes
import ru.beregstudio.diceroller.R

/**
 * Тематический фон экрана приложения.
 *
 * [AUTO] — псевдо-тема: фон выбирается по текущей дате через [HolidayCalendar].
 * У остальных элементов [resId] — идентификатор векторного фона в `res/drawable`.
 *
 * Ключ хранения в SharedPreferences — [name] (а не `ordinal`), чтобы перестановка
 * констант не ломала сохранённые настройки пользователя.
 */
enum class BackgroundTheme(@param:DrawableRes val resId: Int) {
    AUTO(0),
    NONE(0),
    HALLOWEEN(R.drawable.bg_theme_halloween),
    NEW_YEAR(R.drawable.bg_theme_new_year),
    VALENTINE(R.drawable.bg_theme_valentine);

    companion object {
        /** Значение по умолчанию: фон подбирается автоматически по дате. */
        val DEFAULT: BackgroundTheme = AUTO

        /** Безопасный разбор сохранённого ключа; при мусоре возвращает [DEFAULT]. */
        fun fromKey(key: String?): BackgroundTheme =
            entries.firstOrNull { it.name == key } ?: DEFAULT
    }
}
