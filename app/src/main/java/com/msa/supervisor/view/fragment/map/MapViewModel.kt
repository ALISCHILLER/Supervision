package com.msa.supervisor.view.fragment.map


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.util.Size
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.msa.supervisor.R
import com.msa.supervisor.model.data.database.entity.CustomerEntity
import com.msa.supervisor.model.data.request.GpsTrackingRequestModel
import com.msa.supervisor.model.data.response.map.GpsTrackingJsonModel
import com.msa.supervisor.model.data.response.map.JsonStrModel
import com.msa.supervisor.model.data.response.map.MarkersVisitor
import com.msa.supervisor.model.data.response.map.TripPointModel
import com.msa.supervisor.model.data.response.repository.CustomerRepository
import com.msa.supervisor.model.data.response.repository.MapRepository
import com.msa.supervisor.model.data.response.repository.VisitorRepository
import com.msa.supervisor.model.mapper.MapperMarkersVisitor
import com.msa.supervisor.tool.OsmManager
import com.msa.supervisor.view.dialog.multiplechoice.MultiItem
import com.msa.supervisor.view.fragment.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

/**
 * create by Ali Soleymani.
 */
@HiltViewModel
class MapViewModel @Inject constructor(
    private val visitorRepository: VisitorRepository,
    private val mapRepository: MapRepository,
    private val customerRepository: CustomerRepository,
    @ApplicationContext private val applicationContext: Context
) : BaseViewModel() {

    val markersVisitor: MutableLiveData<List<MarkersVisitor>> by lazy { MutableLiveData<List<MarkersVisitor>>() }
    val gpsTrackingJson: MutableLiveData<List<GpsTrackingJsonModel>> by lazy { MutableLiveData<List<GpsTrackingJsonModel>>() }
    val gpsTrackingView: com.msa.supervisor.tool.SingleLiveEvent<Boolean> by lazy { com.msa.supervisor.tool.SingleLiveEvent<Boolean>() }
    val customer: MutableLiveData<List<CustomerEntity>> by lazy { MutableLiveData<List<CustomerEntity>>() }


    fun getVisitor() {
        viewModelScope.launch(Dispatchers.IO+exceptionHandler()) {
            val visitorsEntity = visitorRepository.getVisitors()
            val mapper = MapperMarkersVisitor()
            mapper.transformToDomain(visitorsEntity).let {
                markersVisitor.postValue(it)
            }
        }
    }

    fun getInvoiveItem(): MutableList<MarkersVisitor> {
        val items: MutableList<MarkersVisitor> = mutableListOf()
        items.add(
            MarkersVisitor(
                "7571D2C6-44CE-4B62-A45E-42EC8B1C670,",
                "رضا", null, null, "12.59"
            )
        )
        return items
    }

    @SuppressLint("SuspiciousIndentation")
    fun getCustomer() {
        viewModelScope.launch(IO+exceptionHandler()) {
            val customerall = customerRepository.getCustomerAll()
            customer.postValue(customerall)
        }
    }


    fun getGpsTracking(gpsTrackingRequestModel: GpsTrackingRequestModel) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler()) {
            val response = callApi2(mapRepository.requestMap(gpsTrackingRequestModel))

            response?.let {
                Log.e("MapViewModel", "requestMapViewModel:${it} ")
                gpsTrackingJson.postValue(it)
            }

        }
    }

    var myList: MutableList<String> = mutableListOf<String>()
    fun getTabItem(): MutableList<GpsTrackingRequestModel> {
        val items: MutableList<GpsTrackingRequestModel> = mutableListOf()

        myList.add("256b9720-da5b-4691-aea3-30b482a57365")
        myList.add("b2e4da8e-5b23-40ce-b061-450d2170cfc3")
        myList.add("bfdfd726-5787-4903-a3e2-6c30e78618ef")
         myList.add("b86f186f-a372-44c0-b6e4-7c95f00cee25")


        val localDateTime = LocalDateTime.parse("2023-04-19T00:00:00.743")
        val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
        val date = Date.from(instant)
        println(date)
        items.add(
            GpsTrackingRequestModel(
                "256b9720-da5b-4691-aea3-30b482a57365,",
                myList,
                "333f70ec-dea8-46e9-9a36-83aebe1c27bb",
                "",
                date,
                "2023/04/19 00:00:00"
            )
        )
        return items
    }

    private fun createMarker(
        position: GeoPoint,
        icon: Int,
        name: String,
        time: String,
        osmManager: OsmManager,
        size: Size
    ) {
        val iconBus = osmManager
            .createMarkerIconDrawable(size, icon)
        val infoWindows = osmManager.getInfoWindows(
            name,
            applicationContext.resources.getString(R.string.attendanceTime, time)
        )
        osmManager.addMarker(iconBus, position, infoWindows)
    }

    fun drawTripRoute(
        gpsTrackingJsonModels: List<GpsTrackingJsonModel>,
        visitorIds: MutableList<MultiItem>,
        osmManager: OsmManager
    ) {
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<Array<JsonStrModel>>() {}.type
        val rainbow: IntArray = applicationContext.getResources()
            .getIntArray(R.array.bg_color)
        var jsonStrModels: Array<JsonStrModel>?
        var c = 0;

        visitorIds.forEach { visitorId ->
            val gpsTrackingJsonModel = gpsTrackingJsonModels.firstOrNull {
                it.dealerId.equals(visitorId.id, ignoreCase = true)
            }

            gpsTrackingJsonModel?.let { gpsTrackingJson ->
                jsonStrModels = emptyArray()
                jsonStrModels?.toMutableList()?.clear()
                jsonStrModels = gson.fromJson(gpsTrackingJsonModel.json_str, arrayTutorialType)
                val tripPoints = jsonStrModels?.mapNotNull { jsonModel ->
                    val latitude = jsonModel.Lat.toDoubleOrNull()
                    val longitude = jsonModel.Long.toDoubleOrNull()
                    if (latitude == null || longitude == null) {
                        Log.e(TAG, "Invalid coordinates for visitor $visitorId")
                        null
                    } else {
                        TripPointModel(
                            dealerId = jsonModel.DealerId,
                            lat = latitude.toFloat(),
                            long = longitude.toFloat(),
                            name =visitorId.name ,
                            time = jsonModel.Time
                        )
                    }
                }

                if (!tripPoints.isNullOrEmpty()) {
                    val color = rainbow[c]
                    drawPolyline(osmManager, tripPoints, color)
                    addMarkers(osmManager, tripPoints)
                    c++
                }
            }
        }
        CoroutineScope(IO).launch {
            delay(2000)
            gpsTrackingView.postValue(true)
        }

    }

    fun drawPolyline(osmManager: OsmManager, triples: List<TripPointModel>, color: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            osmManager.drawPolyLine(triples, color = color)
        }
    }

    fun addMarkers(osmManager: OsmManager, triples: List<TripPointModel>) {
        triples.forEachIndexed { index, tripPointModel ->
            val markerDrawable = when (index) {
                0 -> {
                    R.drawable.ic_start_location
                }

                triples.lastIndex - 1 -> R.drawable.ic_end_location
                else -> R.drawable.ic_stop_location
            }
            var size = when (index) {
                0 -> {
                    Size(90, 90)
                }

                triples.lastIndex - 1 -> Size(80, 80)
                else -> Size(50, 50)
            }
            createMarker(
                GeoPoint(tripPointModel.lat.toDouble(), tripPointModel.long.toDouble()),
                markerDrawable,
                tripPointModel.name,
                tripPointModel.time,
                osmManager,
                size as Size
            )
        }
    }

}