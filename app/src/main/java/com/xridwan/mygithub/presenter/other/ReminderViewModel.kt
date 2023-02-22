package com.xridwan.mygithub.presenter.other

import androidx.lifecycle.ViewModel
import com.xridwan.mygithub.data.local.entity.Reminder
import com.xridwan.mygithub.data.preference.ReminderPreference

class ReminderViewModel(
    private val preference: ReminderPreference,
) : ViewModel() {

    fun setReminder(value: Reminder) {
        preference.setReminder(value)
    }

    fun getReminder(): Reminder {
        return preference.getReminder()
    }
}