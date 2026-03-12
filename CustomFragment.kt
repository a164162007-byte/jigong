package com.jigong.helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_custom.*

class CustomFragment : Fragment() {
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View? =
        i.inflate(R.layout.fragment_custom, c, false)

    override fun onViewCreated(v: View, b: Bundle?) {
        super.onViewCreated(v, b)

        et_normal_hours.setText(SpUtil.normalHours.toString())
        et_overtime_convert.setText(SpUtil.overtimeConvertHours.toString())
        et_meal_price.setText(SpUtil.mealPrice.toString())
        et_backup_days.setText(SpUtil.backupDays.toString())

        btn_manual_backup.setOnClickListener {
            BackupUtils.manualBackup(requireContext())
            Toast.makeText(context, "备份成功", Toast.LENGTH_SHORT).show()
        }

        btn_save_config.setOnClickListener {
            val nh = et_normal_hours.text.toString().toDoubleOrNull()
            val oc = et_overtime_convert.text.toString().toDoubleOrNull()
            val mp = et_meal_price.text.toString().toIntOrNull()
            val bd = et_backup_days.text.toString().toIntOrNull()

            if (nh == null || oc == null || mp == null || bd == null) {
                Toast.makeText(context, "输入不合法", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            SpUtil.normalHours = nh
            SpUtil.overtimeConvertHours = oc
            SpUtil.mealPrice = mp
            SpUtil.backupDays = bd

            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show()
        }
    }
}
