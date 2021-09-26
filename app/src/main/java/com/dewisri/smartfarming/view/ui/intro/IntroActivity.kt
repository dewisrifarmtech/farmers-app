package com.dewisri.smartfarming.view.ui.intro


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dewisri.smartfarming.databinding.ActivityIntroBinding
import com.dewisri.smartfarming.view.ui.intro.adapter.IntroViewPagerAdapter
import com.dewisri.smartfarming.view.ui.login.MainLogin
import com.dewisri.smartfarming.utils.IntroPreferences
import com.google.android.material.tabs.TabLayout


class IntroActivity : AppCompatActivity() {
    var introViewPagerAdapter: IntroViewPagerAdapter? = null
    var position = 0
    var btnAnim: Animation? = null
    private lateinit var viewModel: IntroViewModel
    private lateinit var introBinding: ActivityIntroBinding

    private lateinit var introPreferences: IntroPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        introPreferences = IntroPreferences(this)

        if (!introPreferences.restorePrefData()) {
          launchHomeScreen()
        }
        introBinding= ActivityIntroBinding.inflate(layoutInflater)
        setContentView(introBinding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(
            this@IntroActivity,
            ViewModelProvider.NewInstanceFactory()
        )[IntroViewModel::class.java]

        introViewPagerAdapter = IntroViewPagerAdapter(this, viewModel.getIntro())
        introBinding.screenViewpager.adapter = introViewPagerAdapter
        introBinding.tabIndicator.setupWithViewPager(introBinding.screenViewpager)



        introBinding.btnNext.setOnClickListener {
            position = introBinding.screenViewpager.currentItem
            if (position < viewModel.getIntro().size) {
                introBinding.screenViewpager.currentItem = position + 1
            }
            else  {
                loadLastScreen()
            }
        }

        introBinding.tabIndicator.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == viewModel.getIntro().size - 1) {
                    loadLastScreen()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        introBinding.btnGetStarted.setOnClickListener {
            launchHomeScreen()
        }


        introBinding.tvSkip.setOnClickListener {
            launchHomeScreen()
        }
    }

    private fun launchHomeScreen() {
        introPreferences.savePrefsData(false);
        startActivity(Intent(this, MainLogin::class.java))
        finish()
    }


    private fun loadLastScreen() {
        introBinding.btnNext.visibility = View.INVISIBLE
        introBinding.btnGetStarted.visibility = View.VISIBLE
        introBinding.tvSkip.visibility = View.INVISIBLE
        introBinding.tabIndicator.visibility = View.INVISIBLE
        introBinding.btnGetStarted.animation = btnAnim
    }
}