package com.xridwan.mygithub.presenter.other

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.xridwan.mygithub.R
import com.xridwan.mygithub.data.local.entity.Reminder
import com.xridwan.mygithub.databinding.ActivityReminderBinding
import com.xridwan.mygithub.receiver.AlarmReceiver
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReminderActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    private lateinit var binding: ActivityReminderBinding
    private lateinit var reminderModel: Reminder
    private val viewModel: ReminderViewModel by viewModel()
    private var alarmReceiver: AlarmReceiver = AlarmReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.label_reminder)
        }

        binding.switchReminder.isChecked = viewModel.getReminder().isReminded
        binding.switchReminder.setOnCheckedChangeListener(this)
    }

    private fun saveReminder(state: Boolean) {
        reminderModel = Reminder()
        reminderModel.isReminded = state
        viewModel.setReminder(reminderModel)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
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