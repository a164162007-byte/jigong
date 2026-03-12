package com.jigong.helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_stats.*
import java.text.SimpleDateFormat
import java.util.*

class StatsFragment : Fragment() {
    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val cal = Calendar.getInstance()

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View? =
        i.inflate(R.layout.fragment_stats, c, false)

    override fun onViewCreated(v: View, b: Bundle?) {
        super.onViewCreated(v, b)
        radio_group.setOnCheckedChangeListener { _, _ -> refreshStats() }
        refreshStats()
    }

    private fun refreshStats() {
        lifecycleScope.launchWhenCreated {
            val list = AppDatabase.instance.workRecordDao().getAllRecords()
            val filtered = when (radio_group.checkedRadioButtonId) {
                R.id.rb_year -> list.filter {
                    val d = sdf.parse(it.date)
                    val c = Calendar.getInstance()
                    c.time = d
                    c.get(Calendar.YEAR) == cal.get(Calendar.YEAR)
                }
                R.id.rb_month -> list.filter {
                    val d = sdf.parse(it.date)
                    val c = Calendar.getInstance()
                    c.time = d
                    c.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                    c.get(Calendar.MONTH) == cal.get(Calendar.MONTH)
                }
                else -> list
            }

            val normalDays = filtered.count { it.type == 0 }
            val manualDays = filtered.filter { it.type == 1 }.sumOf {
                it.workHours / SpUtil.normalHours
            }
            val totalNormal = normalDays + manualDays
            val overtimeHours = filtered.filter { it.type == 2 }.sumOf { it.workHours }
            val convert = overtimeHours / SpUtil.overtimeConvertHours
            val totalAll = totalNormal + convert
            val meal = totalNormal.toInt() * SpUtil.mealPrice

            tv_normal_days.text = "正常标准工：%.2f".format(totalNormal)
            tv_overtime_convert.text = "加班折算工：+%.2f".format(convert)
            tv_total_all.text = "总标准工：%.2f".format(totalAll)
            tv_meal.text = "饭补：%.0f × %d = %d 元".format(totalNormal, SpUtil.mealPrice, meal)
            tv_meal_tip.text = "（加班工时不计饭补）"

            val group = filtered.groupBy { it.location }
            val sb = StringBuilder()
            group.forEach { (loc, items) ->
                val cnt = items.count { it.type == 0 || it.type == 1 }
                sb.append("$loc：$cnt 天\n")
            }
            tv_location_stats.text = "按地点统计：\n${sb.trim()}"
        }
    }
}
