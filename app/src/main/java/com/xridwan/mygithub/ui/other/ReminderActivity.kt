package com.xridwan.mygithub.ui.other

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xridwan.mygithub.R
import com.xridwan.mygithub.data.local.entity.Reminder
import com.xridwan.mygithub.data.local.preference.ReminderPreference
import com.xridwan.mygithub.databinding.ActivityReminderBinding
import com.xridwan.mygithub.receiver.AlarmReceiver

class ReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderBinding
    private lateinit var reminderModel: Reminder
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.label_reminder)
        }

        val reminderPreference = ReminderPreference(this)
        binding.switchReminder.isChecked = reminderPreference.getReminder().isReminded
        alarmReceiver = AlarmReceiver()

        binding.switchReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveReminder(true)
                alarmReceiver.setRepeatingAlarm(
                    this,
                    AlarmReceiver.TYPE_REPEATING,
                    "09:00",
                    "RepeatingAlarm"
                )

            } else {
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    private fun saveReminder(state: Boolean) {
        val reminderPreference = ReminderPreference(this)
        reminderModel = Reminder()
        reminderModel.isReminded = state
        reminderPreference.setReminder(reminderModel)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}