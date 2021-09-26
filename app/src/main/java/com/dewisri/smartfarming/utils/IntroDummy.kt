package com.dewisri.smartfarming.utils

import com.dewisri.smartfarming.R
import com.dewisri.smartfarming.data.model.ScreenItem

object DataDummy {
    fun generateDataIntroDummy(): List<ScreenItem> {
        val listIntro = ArrayList<ScreenItem>()

        listIntro.add(
            ScreenItem(
                "Monitoring",
                "Dengan system monitoring dapat memudahkan anda dalam bekerja",
                R.drawable.monitoring,
            )
        )
        listIntro.add(
            ScreenItem(
                "Weather",
                "Perkiraan cuaca yang selalu update secara realtime",
                R.drawable.weather,
            )
        )
        listIntro.add(
            ScreenItem(
                "Good App",
                "Aplikasi ini sangat bagus dan user friendly untuk digunakan",
                R.drawable.goodapp,
            )
        )

        return listIntro
    }

}