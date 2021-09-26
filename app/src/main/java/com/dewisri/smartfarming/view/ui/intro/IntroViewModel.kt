package com.dewisri.smartfarming.view.ui.intro

import androidx.lifecycle.ViewModel
import com.dewisri.smartfarming.data.model.ScreenItem
import com.dewisri.smartfarming.utils.DataDummy

class IntroViewModel: ViewModel() {

   fun getIntro(): List<ScreenItem> = DataDummy.generateDataIntroDummy()
}