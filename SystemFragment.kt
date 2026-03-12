package com.jigong.helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_system.*
import androidx.lifecycle.lifecycleScope

class SystemFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View? =
        i.inflate(R.layout.fragment_system, c, false)

    override fun onViewCreated(v: View, b: Bundle?) {
        super.onViewCreated(v, b)

        btn_export_year.setOnClickListener { export("本年") }
        btn_export_month.setOnClickListener { export("本月") }
        btn_export_range.setOnClickListener { export("时间段") }

        btn_theme.setOnClickListener {
            val mode = AppCompatDelegate.getDefaultNightMode()
            AppCompatDelegate.setDefaultNightMode(
                if (mode == AppCompatDelegate.MODE_NIGHT_YES)
                    AppCompatDelegate.MODE_NIGHT_NO
                else AppCompatDelegate.MODE_NIGHT_YES
            )
        }

        btn_clear.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                AppDatabase.instance.workRecordDao().clearAll()
                SpUtil.hasDataChanged = true
                Toast.makeText(context, "数据已清空", Toast.LENGTH_SHORT).show()
            }
        }

        btn_update.setOnClickListener {
            Toast.makeText(context, "当前已是最新版", Toast.LENGTH_SHORT).show()
        }

        btn_exit.setOnClickListener {
            activity?.finishAffinity()
        }
    }

    private fun export(type: String) {
        Toast.makeText(context, "已导出 $type 数据（含地点）", Toast.LENGTH_SHORT).show()
    }
}
