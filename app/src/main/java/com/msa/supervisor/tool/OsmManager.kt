package com.msa.supervisor.tool

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.util.Size
import android.widget.TextView
import com.msa.supervisor.R

import com.msa.supervisor.model.data.response.map.TripPointModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.TilesOverlay
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow
import java.io.IOException
import java.util.Locale
/**
 * create by Ali Soleymani.
 */
class OsmManager(
    private val map: MapView,
     val click: Click,
) :Marker.OnMarkerClickListener{

    interface Click {
        fun selectItem(item: Marker)
    }
    fun mapInitialize(theme: Int) {
        Configuration.getInstance().userAgentValue = map.context.packageName
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        map.setMultiTouchControls(true)
        map.minZoomLevel = 5.0
        map.maxZoomLevel = 21.0
        map.controller.animateTo(
            GeoPoint(32.4279, 53.6880)
            ,6.0,1)
//        if (theme == android.content.res.Configuration.UI_MODE_NIGHT_YES)
//            map.overlayManager.tilesOverlay.setColorFilter(TilesOverlay.INVERT_COLORS) // dark
        map.onResume()
    }

    fun createMarkerIconDrawable(size: Size, icon: Int): Drawable {
        val iconStart = Bitmap
            .createScaledBitmap(
                BitmapFactory.decodeResource(map.context.resources, icon),
                size.width,
                size.height,
                true
            )
        return BitmapDrawable(map.context.resources, iconStart)
    }

    fun addMarker(icon: Drawable, position: GeoPoint, infoWindows: MarkerInfoWindow?): Marker {
        val marker = Marker(map, map.context)
        marker.icon = icon
        marker.position = position
        marker.setInfoWindow(infoWindows)
        map.overlayManager.add(marker)
        map.invalidate()
        return marker
    }
    fun addMarkerOnMarkerClick(icon: Drawable, position: GeoPoint, infoWindows: MarkerInfoWindow?): Marker {
        val marker = Marker(map, map.context)
        marker.icon = icon
        marker.position = position
        marker.setInfoWindow(infoWindows)
        map.overlayManager.add(marker)
        map.invalidate()
        marker.setOnMarkerClickListener(this)
        return marker
    }



    fun measureDistance(Old: GeoPoint, New: GeoPoint): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            Old.latitude, Old.longitude,
            New.latitude, New.longitude, results
        )
        return if (results.isNotEmpty()) results[0] else 0f
    }
    suspend fun drawPolyLine(tripPoints: List<TripPointModel>, color: Int) {
        val roadManager = OSRMRoadManager(map.context, "")
        val road = roadManager.getRoad(getGeoPoints(tripPoints))
        val polyline = Polyline(map, true, false)
        polyline.setPoints(RoadManager.buildRoadOverlay(road).actualPoints)
        polyline.outlinePaint?.color = color
        polyline.outlinePaint?.strokeWidth = 11.0f
        polyline.outlinePaint?.strokeCap = Paint.Cap.ROUND
        polyline.isGeodesic = true
        withContext(Dispatchers.Main) {
            map.overlays.add(polyline)
            map.zoomToBoundingBox(getBoundingBoxFromPoints(getGeoPoints(tripPoints)), true)
        }
    }

    private fun getGeoPoints(tripPoints: List<TripPointModel>): ArrayList<GeoPoint> {
        val geoPoints = ArrayList<GeoPoint>()
        for (item in tripPoints)
            geoPoints.add(GeoPoint(item.lat.toDouble(), item.long.toDouble()))
        return geoPoints
    }



    fun clearOverlays() {
        map.overlays.clear()
        map.invalidate()
    }

    fun onClickMarker(){

    }
    fun getBoundingBoxFromPoints(points: List<GeoPoint>): BoundingBox {
        var north = 0.0
        var south = 0.0
        var west = 0.0
        var east = 0.0
        for (i in points.indices) {
            val lat = points[i].latitude
            val lon = points[i].longitude
            if (i == 0 || lat > north) north = lat
            if (i == 0 || lat < south) south = lat
            if (i == 0 || lon < west) west = lon
            if (i == 0 || lon > east) east = lon
        }
        north += 0.02
        south -= 0.02
        east += 0.02
        west -= 0.02

        return BoundingBox(north, east, south, west)
    }


    fun getInfoWindows(title: String?, content: String?): MarkerInfoWindow {
        val infoWindow: MarkerInfoWindow =
            object : MarkerInfoWindow(R.layout.layout_marker_info, map) {
                override fun onOpen(item: Any) {
                    val titleTextView = mView.findViewById<TextView>(R.id.textViewTitle)
                    titleTextView.text = title
                    val textViewContent = view.findViewById<TextView>(R.id.textViewContent)
                    textViewContent.text = content
                }

                override fun onClose() {}
            }
        return infoWindow
    }
    override fun onMarkerClick(marker: Marker?, mapView: MapView?): Boolean {
        Log.e(TAG, "onMarkerClick: ${marker}", )
        marker?.let {
            click.selectItem(it)
            Log.e(TAG, "onMarkerClick: ${it}")
        }
        return true
    }


}