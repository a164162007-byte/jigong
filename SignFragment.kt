package com.jigong.helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_sign.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class SignFragment : Fragment() {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val today = dateFormat.format(Date())

    override fun onCreateView(infl: LayoutInflater, c: ViewGroup?, b: Bundle?): View? =
        infl.inflate(R.layout.fragment_sign, c, false)

    override fun onViewCreated(v: View, b: Bundle?) {
        super.onViewCreated(v, b)
        tv_date.text = today

        btn_one_day.setOnClickListener {
            val loc = et_location.text.toString().trim()
            if (loc.isEmpty()) {
                Toast.makeText(context, "请填写工作地点", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launchWhenCreated {
                AppDatabase.instance.workRecordDao().insert(
                    WorkRecord(
                        type = 0,
                        date = today,
                        location = loc,
                        workHours = SpUtil.normalHours
                    )
                )
                SpUtil.hasDataChanged = true
                Toast.makeText(context, "记录成功", Toast.LENGTH_SHORT).show()
                et_location.setText("")
                refreshOvertimeTotal()
            }
        }

        btn_manual.setOnClickListener {
            val loc = et_location_manual.text.toString().trim()
            val start = et_start_time.text.toString().trim()
            val end = et_end_time.text.toString().trim()
            if (loc.isEmpty() || start.isEmpty() || end.isEmpty()) {
                Toast.makeText(context, "请填写完整", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            try {
                val s = timeFormat.parse(start)
                val e = timeFormat.parse(end)
                val hours = abs(s.time - e.time) / (1000.0 * 60 * 60)
                val dayHours = SpUtil.normalHours
                val day = if (dayHours == 0.0) 0.0 else hours / dayHours

                lifecycleScope.launchWhenCreated {
                    AppDatabase.instance.workRecordDao().insert(
                        WorkRecord(
                            type = 1,
                            date = today,
                            startTime = start,
                            endTime = end,
                            workHours = hours,
                            location = loc
                        )
                    )
                    SpUtil.hasDataChanged = true
                    Toast.makeText(context, "记录成功，折算天数：%.2f".format(day), Toast.LENGTH_SHORT).show()
                    et_start_time.setText("")
                    et_end_time.setText("")
                    et_location_manual.setText("")
                    refreshOvertimeTotal()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "时间格式错误，请用 HH:mm", Toast.LENGTH_SHORT).show()
            }
        }

        btn_overtime.setOnClickListener {
            val loc = et_location_overtime.text.toString().trim()
            val hourStr = et_overtime_hours.text.toString().trim()
            if (loc.isEmpty() || hourStr.isEmpty()) {
                Toast.makeText(context, "请填写完整", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val hours = hourStr.toDoubleOrNull()
            if (hours == null || hours < 0) {
                Toast.makeText(context, "加班时长输入错误", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launchWhenCreated {
                AppDatabase.instance.workRecordDao().insert(
                    WorkRecord(
                        type = 2,
                        date = today,
                        workHours = hours,
                        location = loc
                    )
                )
                SpUtil.hasDataChanged = true
                Toast.makeText(context, "加班记录成功", Toast.LENGTH_SHORT).show()
                et_overtime_hours.setText("")
                et_location_overtime.setText("")
                refreshOvertimeTotal()
            }
        }
        refreshOvertimeTotal()
    }

    private fun refreshOvertimeTotal() {
        lifecycleScope.launchWhenCreated {
            val list = AppDatabase.instance.workRecordDao().getAllRecords()
            var total = 0.0
            list.forEach { if (it.type == 2) total += it.workHours }
            tv_overtime_total.text = "加班总时长：%.2f 小时".format(total)
        }
    }
}
