package com.home.androidkotlinmapac

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.home.androidkotlinmapac.entities.Result
import kotlinx.android.synthetic.main.bottom_sheet.*
import java.lang.RuntimeException

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private val LOG_TAG = javaClass.simpleName
    private var mMap: GoogleMap? = null
    lateinit var viewModel: MainViewModel
    private var results: List<Result>? = null
    private var lastClickedMarker: Marker? = null
    private var mapFragment: SupportMapFragment? = null
    lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java);

        setUpObservers()
        setupViews();
    }

    private fun setupViews() {
        bottomSheetBehavior = BottomSheetBehavior.from<LinearLayout>(bottom_sheet)

        setZoomControlPosition()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap?.uiSettings?.isZoomControlsEnabled = true
        mMap?.setOnMarkerClickListener(this)
        viewModel.getPlaces()
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        val index = marker?.tag as Int
        if (lastClickedMarker != null)
            lastClickedMarker?.setIcon(
                BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)
            )
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        val result = results?.get(index)
        txv_place_name.text = result?.name
        txv_place_detail.text = result?.formattedAddress
        lastClickedMarker = marker
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        return false
    }

    private fun setUpObservers() {

        viewModel.getDataResult()?.observe(this,
            Observer<List<Result>> { results ->
                Log.d(LOG_TAG, results.toString())
                fillMap(results)
            })
    }

    private fun fillMap(results: List<Result>?) {

        this.results = results

        results?.indices?.forEach { i ->
            val latLng =
                LatLng(
                    results[i].geometry?.location?.lat ?: throw RuntimeException("Invalid result"),
                    results[i].geometry?.location?.lng ?: throw RuntimeException("Invalid result")
                )
            val marker = mMap?.addMarker(
                MarkerOptions().position(latLng).icon(
                    BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)
                )
            )
            marker?.tag = i
        }

        mMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    results?.get(0)?.geometry?.location?.lat ?: throw RuntimeException("Invalid result"),
                    results.get(0).geometry?.location?.lng ?: throw RuntimeException("Invalid result")
                ), 12.0f
            )
        )
    }

    private fun setZoomControlPosition() {

        mapFragment?.view?.findViewById<View>(0x1).let {
            if (it?.layoutParams is RelativeLayout.LayoutParams) {

                val params = it.layoutParams as RelativeLayout.LayoutParams

                params.addRule(RelativeLayout.ALIGN_PARENT_TOP)
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.addRule(RelativeLayout.ALIGN_PARENT_END)
                }

                val margin = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10f,
                    resources.displayMetrics
                ).toInt()
                params.setMargins(margin, margin, margin, margin)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.marginEnd = margin
                }
            }
        }
    }
}
