package com.jigong.helper

import android.app.Activity

object AdManager {
    enum class AdUnion {
        TEST, CSJ, YLH, BQT, KS, HW, XM, OTHER
    }

    private val CURRENT_UNION = AdUnion.TEST

    fun loadAndShowSplashAd(
        activity: Activity,
        onAdDismiss: () -> Unit,
        onAdError: () -> Unit = {}
    ) {
        when (CURRENT_UNION) {
            AdUnion.TEST -> {
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    onAdDismiss()
                }, 2000)
            }
            AdUnion.CSJ -> { onAdDismiss() }
            AdUnion.YLH -> { onAdDismiss() }
            AdUnion.BQT -> { onAdDismiss() }
            AdUnion.KS -> { onAdDismiss() }
            AdUnion.HW -> { onAdDismiss() }
            AdUnion.XM -> { onAdDismiss() }
            else -> { onAdDismiss() }
        }
    }
}
