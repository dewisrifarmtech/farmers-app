package com.dewisri.smartfarming.view.ui.home


import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dewisri.smartfarming.R
import com.dewisri.smartfarming.view.ui.home.content.commodities.KomoditasActivity
import com.dewisri.smartfarming.view.ui.home.content.weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var hariIni: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
     //  val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //   textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBtn()
        getToday()
    }

    private fun getToday() {
        getDate()
        val date = Calendar.getInstance().time
        val tanggal = DateFormat.format("d MMM yyyy", date) as String
        val formatDate = "$hariIni, $tanggal"
        tvDate.text = formatDate
    }

    private fun getDate() {
        val sdf = SimpleDateFormat("EEEE")
        val d = Date()
        hariIni = sdf.format(d)
    }

    private fun initBtn() {
        btnMonitor.setOnClickListener {
            val monitor = Intent(context, KomoditasActivity::class.java)
            startActivity(monitor)
        }

        btnWeather.setOnClickListener {

        }
    }


}