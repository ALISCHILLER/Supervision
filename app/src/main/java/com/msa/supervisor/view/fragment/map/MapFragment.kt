package com.msa.supervisor.view.fragment.map

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.View
import androidx.fragment.app.viewModels
import com.zar.core.tools.extensions.solarDateToGregorianDate
import com.zar.core.tools.manager.ThemeManager
import com.zar.core.view.picker.date.customviews.DateRangeCalendarView
import com.zar.core.view.picker.date.dialog.DatePickerDialog
import com.msa.supervisor.R
import com.msa.supervisor.databinding.FragmentMapBinding
import com.msa.supervisor.model.data.request.GpsTrackingRequestModel
import com.msa.supervisor.model.data.response.map.GpsTrackingJsonModel
import com.msa.supervisor.model.data.response.map.MarkersVisitor
import com.msa.supervisor.tool.CompanionValues
import com.msa.supervisor.tool.OsmManager
import com.msa.supervisor.tool.datetime.DateFormat
import com.msa.supervisor.tool.datetime.DateHelper
import com.msa.supervisor.tool.signalr.RemoteSignalREmitter
import com.msa.supervisor.view.activity.MainActivity
import com.msa.supervisor.view.dialog.multiplechoice.MultiItem
import com.msa.supervisor.view.dialog.multiplechoice.SelectionCompleteListener
import com.msa.supervisor.view.dialog.multiplechoice.MultipleChoiceDialog
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * create by Ali Soleymani.
 */
@AndroidEntryPoint
class MapFragment(override var layout: Int = R.layout.fragment_map) :
    BaseFragment<FragmentMapBinding>() {
    private lateinit var osmManager: OsmManager
    private var signalRListener: com.msa.supervisor.tool.signalr.SignalRListener? = null
    private val mapViewModel: MapViewModel by viewModels()
    private var markerVistors: List<MarkersVisitor>? = null
    private val visitorItems: MutableList<MultiItem> = ArrayList()
    private var selectVisitor: MutableList<MultiItem> = ArrayList()
    val dealerIds: MutableList<String> = mutableListOf()
    var createdDate_str :String = ""
    var dealerId:String = ""
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var themeManagers: ThemeManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = mapViewModel
        initView()
        getVisitor()
        observeLiveDate()
//        mapViewModel.getCustomer()

    }


    fun initView() {
        val click = object : OsmManager.Click {
            override fun selectItem(item: Marker) {
                markerVistors?.forEachIndexed { index, markersVisitor ->
                    if (item == markersVisitor.marker) {
                        showMarkerDetails(markersVisitor)
                    }
                }
            }
        }
        osmManager = OsmManager(binding.mapview, click)
        osmManager.mapInitialize(themeManagers.applicationTheme())
        setListener()
    }

    private fun observeLiveDate() {
        mapViewModel.errorLiveDate.observe(viewLifecycleOwner) {
            showMessage(it.message)
            lottieisAnimating(true)
        }
        mapViewModel.gpsTrackingJson.observe(viewLifecycleOwner) {
            setpolyline(it)
        }
        mapViewModel.getVisitor()
        mapViewModel.markersVisitor.observe(viewLifecycleOwner) {
            it.forEachIndexed { index, markersVisitor ->
                val visitor = MultiItem(
                    markersVisitor.nameVisitor.toString(),
                    markersVisitor.visitorId.toString(),
                    ""
                )
                visitorItems.add(index, visitor)
            }
            markerVistors = it
        }

        mapViewModel.customer.observe(viewLifecycleOwner) {
           CoroutineScope(IO).launch{
               it.forEach {
                   it.let { it1 ->
                       it.address?.let { it2 ->
                           it1.customerName?.let { it3 ->
                               createMarkerCustomer(
                                   GeoPoint(it.latitude, it.longitude),
                                   R.drawable.ic_customer_location, it3, it2
                               )
                           }
                       }
                   }
               }
               withContext(Main){
                   lottieisLocation(true)
               }

           }

        }

        mapViewModel.gpsTrackingView.observe(viewLifecycleOwner) {
            lottieisAnimating(true)
        }
    }

    private fun setListener() {
        binding.switchLive.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.switchRouting.isChecked = false
                setexpandRounting(false)
                osmManager.clearOverlays()
                startSignalR()
            }
        }
        binding.switchRouting.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.switchLive.isChecked = false

                setexpandRounting(true)
            } else {
                setexpandRounting(false)

            }
        }
        binding.switchCustomer.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mapViewModel.getCustomer()
                lottieisLocation(false)
            }
        }
        binding.selectVisitor.setOnClickListener {
            setSelectVisitor()
        }
        binding.selectDate.setOnClickListener {
            showDatePickerDialog()
        }
        binding.btnConfirmData.setOnClickListener {

            if (createdDate_str.isNotEmpty() && dealerId.isNotEmpty()) {
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                val date: Date = formatter.parse(createdDate_str)
                val fields = dealerId.split(",")
                val userId = sharedPreferences.getString(CompanionValues.userId, "")
                fields.forEachIndexed { index, s ->
                    dealerIds.add(index, s)
                }
                val createdDate = date
                mapViewModel.getGpsTracking(
                    GpsTrackingRequestModel(
                        dealerId, dealerIds,
                        userId.toString(), "",
                        createdDate,
                        createdDate_str
                    )
                )
                lottieisAnimating(false)
            }
        }
    }


    private fun setpolyline(gpsTrackingJsonModels: List<GpsTrackingJsonModel>) {
        osmManager.clearOverlays()
        mapViewModel.drawTripRoute(gpsTrackingJsonModels, selectVisitor, osmManager)


    }

    private fun setexpandRounting(cheak: Boolean) {
        if (cheak) {
            binding.expandableMore.collapse()
            binding.expandRouting.expand()
        } else {
            binding.expandRouting.collapse()

        }
    }


    private fun getVisitor() {


    }


    private fun startSignalR() {

        signalRListener?.stopConnection()
        val remote = object : RemoteSignalREmitter {
            override fun onConnectToSignalR() {
                Log.e("onConnectToSignalR", "onConnectToSignalR: ")
                signalRListener?.DistJoinGroup()
            }

            override fun onGetPoint(lat: String, lng: String, visitorId: String) {
                CoroutineScope(IO).launch {
                    withContext(Dispatchers.Main) {
                        if (isVisible() && isResumed()) {
                            createMarkerLive(GeoPoint(lat.toDouble(), lng.toDouble()), visitorId)
                        }
                    }
                }
            }

            override fun onPreviousStationReached(message: String) {

            }
        }
        CoroutineScope(IO).launch {
            val token = sharedPreferences.getString(CompanionValues.tokenSignalR, "")
            delay(2000)
            signalRListener = com.msa.supervisor.tool.signalr.SignalRListener(remote, token)
            signalRListener?.startConnection()
        }
    }

    private fun setSelectVisitor() {
        MultipleChoiceDialog.show(requireContext(),
            "لیست ویزیتورها",
            "تایید",
            visitorItems,
            object : SelectionCompleteListener {
                override fun onCompleteSelection(selectedItems: ArrayList<MultiItem>) {
                    Log.e("data", selectedItems.toString())
                    binding.linearVisitorView.visibility = view!!.visibility
                    var sb = ""
                    var id = ""
                    selectedItems.let {

                        selectedItems.forEachIndexed { index, multiItem ->
                            sb += "${multiItem.name},"
                            id += "${multiItem.id},"
                        }
                        dealerId = id
                        selectVisitor=selectedItems
                        binding.txtViewVisitor.text = sb
                        binding.txtViewVisitor.isSelected = true
                    }
                }
            })


    }

    private fun createMarkerLive(position: GeoPoint, vistourId: String) {

        markerVistors?.forEachIndexed { index, markersVisitor ->
            markersVisitor.takeIf { it.visitorId == vistourId }?.let {
                it.marker?.let { it ->
                    val measure = osmManager.measureDistance(position, it.position)
                    if (measure > 10) {
                        it.position = position
                        val date = Date()
                        val persianLocale = Locale("fa", "IR")
                        var dateString = DateHelper.toString(date, DateFormat.Time, persianLocale)
                        markersVisitor.time=dateString
                    }

                } ?: run {
                    val iconBus = osmManager
                        .createMarkerIconDrawable(Size(70, 100), R.drawable.live_location)
                    markerVistors?.get(index)
                        ?.marker = osmManager.addMarkerOnMarkerClick(iconBus, position, null)
                    val date = Date()
                    val persianLocale = Locale("fa", "IR")
                    var dateString = DateHelper.toString(date, DateFormat.Time, persianLocale)
                    markersVisitor.time=dateString
                }
            }
        }
    }

    private fun createMarker(position: GeoPoint, icon: Int, name: String, time: String) {
        val iconBus = osmManager
            .createMarkerIconDrawable(Size(50, 50), icon)
        val infoWindows = osmManager.getInfoWindows(
            name,
            requireActivity().resources.getString(R.string.attendanceTime, time)
        )
        osmManager.addMarker(iconBus, position, infoWindows)
    }

    private fun createMarkerCustomer(position: GeoPoint, icon: Int, name: String, address: String) {
        val iconBus = osmManager
            .createMarkerIconDrawable(Size(50, 50), icon)
        val infoWindows = osmManager.getInfoWindows(
            name,
            requireActivity().resources.getString(R.string.addressmarker, address)
        )
        osmManager.addMarker(iconBus, position, infoWindows)
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(requireContext())
        datePickerDialog.selectionMode = DateRangeCalendarView.SelectionMode.Single
        datePickerDialog.isDisableDaysAgo = false
        datePickerDialog.textSizeWeek = 12.0f
        datePickerDialog.textSizeDate = 14.0f
        datePickerDialog.textSizeTitle = 18.0f
        datePickerDialog.setCanceledOnTouchOutside(true)

        datePickerDialog.onSingleDateSelectedListener =
            DatePickerDialog.OnSingleDateSelectedListener { startDate ->
                binding.txtDate.text = startDate.persianShortDate
                binding.linearDateView.visibility = View.VISIBLE
              //  createdDate_str = startDate.persianShortDate.solarDateToGregorianDate().toString()+":00.743"
                val temp = startDate.persianShortDate.solarDateToGregorianDate()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                createdDate_str = temp!!.format(formatter)


            }
        datePickerDialog.showDialog()
    }


    private fun showMarkerDetails(marker: MarkersVisitor) {
        // Display marker details, e.g., show a dialog or a custom view
        binding.expandableMore.expand()
        binding.txtNameVisitor.text = marker.nameVisitor
        binding.txtlasttimelocation.text = marker.time
        // You can replace the toast with your custom implementation to show marker details
    }

    private fun showMessage(message: String) {
        activity?.let {
            (it as MainActivity).showMessage(message)
        }
    }

    private fun lottieisAnimating(runAn: Boolean) {
        if (binding.loadinglocation.isAnimating || runAn) {
            binding.loadinglocation.pauseAnimation()
            binding.loadinglocation.cancelAnimation()
            binding.loadinglocation.visibility = View.GONE
        } else {
            binding.loadinglocation?.setAnimation("86513-location-forked.json")
            binding.loadinglocation.setBackgroundColor(Color.TRANSPARENT)
            binding.loadinglocation?.loop(true)
            binding.loadinglocation?.playAnimation()
            binding.loadinglocation.visibility = View.VISIBLE
        }
    }

    private fun lottieisLocation(runAn: Boolean) {
        if (binding.loadinglocation.isAnimating || runAn) {
            binding.loadinglocation.pauseAnimation()
            binding.loadinglocation.cancelAnimation()
            binding.loadinglocation.visibility = View.GONE
        } else {
            binding.loadinglocation?.setAnimation("location-customer.json")
            binding.loadinglocation.setBackgroundColor(Color.TRANSPARENT)
            binding.loadinglocation?.loop(true)
            binding.loadinglocation?.playAnimation()
            binding.loadinglocation.visibility = View.VISIBLE
        }
    }
}