package ipvc.estg.easyparking

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.directions.route.*
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import ipvc.estg.easyparking.databinding.ActivityMapsBinding


class MapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener, OnMapReadyCallback, RoutingListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var  lastLocation : Location

    //added to implement distance between two locations
    var firstlocLat: Double = 0.0
    var firstlocLong: Double = 0.0

    //current maker
    var end = LatLng(0.0,0.0)
    private var polylines: List<Polyline>? = null
    var me = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        createLocationRequest()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation

                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 17.0f))
                val address = getAddress(lastLocation.latitude, lastLocation.longitude)
                if(me == 0){
                    me++
                    firstlocLat = lastLocation.latitude
                    firstlocLong = lastLocation.longitude
                    mMap.addCircle(CircleOptions().center(LatLng(firstlocLat,firstlocLong)).radius(5.0).strokeColor(Color.BLACK).fillColor(Color.BLUE))
                }
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
// interval specifies the rate at which your app will like to receive updates.
        locationRequest.interval = 100000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        addMarker(googleMap)
    }

    private fun  addMarker(googleMap: GoogleMap){
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val p1 = LatLng(41.6946, -8.83016)
        mMap.addMarker(MarkerOptions().position(p1).title("Parque Estacionamento Gil Eanes"))

        val p2 = LatLng(41.691002, -8.828173)
        mMap.addMarker(MarkerOptions().position(p2).title("Parque de Estacionamento Avenida"))

        val p3 = LatLng(41.695546, -8.828924)
        mMap.addMarker(MarkerOptions().position(p3).title("Parque de estacionamento 1º de Maio"))

        val p4 = LatLng(41.691765, -8.838676)
        mMap.addMarker(MarkerOptions().position(p4).title("Parque de Estacionamento Campo da Agonia"))

        val p5 = LatLng(41.696037, -8.822682)
        mMap.addMarker(MarkerOptions().position(p5).title("Parque Estacionamento Afonso III"))

        val p6 = LatLng(41.696763, -8.831194)
        mMap.addMarker(MarkerOptions().position(p6).title("Parque Magma"))

        val p7 = LatLng(41.695049, -8.825733)
        mMap.addMarker(MarkerOptions().position(p7).title("Parque do Mercado"))

        val p8 = LatLng(41.698397, -8.831994)
        mMap.addMarker(MarkerOptions().position(p8).title("Parque do Ulsam"))


        /*
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getUsers()
        var position: LatLng

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()!!
                    for (user in users) {
                        position = LatLng(
                            user.address.geo.lat.toString().toDouble(),
                            user.address.geo.lng.toString().toDouble()
                        )
                        mMap.addMarker(
                            MarkerOptions().position(position).title(
                                user.address.suite + " - " + user.address.city))
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                Log.d("****", "${t.message}")
            }
        })
        */

        // Set a listener for marker click.
        // adding on click listener to marker of google maps.
        mMap.setOnMarkerClickListener(this)
    }

    /** Called when the user clicks a marker.  */
     override fun onMarkerClick(marker: Marker): Boolean {

        // on marker click we are getting the title of our marker
        // which is clicked and displaying it in a toast message.
        var markerName = marker.title


        end= LatLng(marker.position.latitude, marker.position.longitude)
        //Toast.makeText(this, "Clicked location is " + markerName, Toast.LENGTH_SHORT).show()

        val cc : com.google.android.material.card.MaterialCardView = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardLocalonMap)
        cc.setVisibility(com.google.android.material.card.MaterialCardView.VISIBLE)

        val c2 : com.google.android.material.card.MaterialCardView = findViewById<com.google.android.material.card.MaterialCardView>(R.id.filterButton)
        c2.setVisibility(com.google.android.material.card.MaterialCardView.GONE)

        var parque : TextView = findViewById<TextView>(R.id.descrDestinoOnMap)
        parque.setText(markerName)

        return false
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("****", "onPause - removeLocationUpdates")
    }
    public override fun onResume() {
        super.onResume()
        startLocationUpdates()
        Log.d("****", "onResume - startLocationUpdates")
    }

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

    }

    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(lat, lng, 1)
        return list[0].getAddressLine(0)
    }

    fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lng1, lat2, lng2, results)
// distance in meter
        return results[0]
    }

    fun Back(view: android.view.View) {}
    fun Perfil(view: android.view.View) {
        val intent = Intent(this, PerfilUtilizador::class.java).apply {
        }
        startActivity(intent)
    }

    fun direcoesClick(view: android.view.View) {
        val c1 : com.google.android.material.card.MaterialCardView = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardLocalonMap)
        c1.setVisibility(com.google.android.material.card.MaterialCardView.GONE)

        val c2 : com.google.android.material.card.MaterialCardView = findViewById<com.google.android.material.card.MaterialCardView>(R.id.filterButton)
        c2.setVisibility(com.google.android.material.card.MaterialCardView.VISIBLE)


        //Direçoes
        var start = LatLng(lastLocation.latitude, lastLocation.longitude)

        val routing = Routing.Builder()
            .travelMode(AbstractRouting.TravelMode.DRIVING)
            .withListener(this)
            .alternativeRoutes(true)
            .waypoints(start, end)
            .key("AIzaSyBptSXcDUVMIfdP8PLuFxhJuWnVnYEySgU") //also define your api key here.
            .build()
        routing.execute()
    }

    //Routing call back functions.
    override fun onRoutingFailure(e: RouteException) {
        val parentLayout: View = findViewById(android.R.id.content)
        val snackbar: Snackbar = Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    override fun onRoutingStart() {
        Toast.makeText(this,"Calculando rota...", Toast.LENGTH_LONG).show()
    }

    fun fecharClick(view: android.view.View) {
        val c1 : com.google.android.material.card.MaterialCardView = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardLocalonMap)
        c1.setVisibility(com.google.android.material.card.MaterialCardView.GONE)

        val c2 : com.google.android.material.card.MaterialCardView = findViewById<com.google.android.material.card.MaterialCardView>(R.id.filterButton)
        c2.setVisibility(com.google.android.material.card.MaterialCardView.VISIBLE)
    }

    //If Route finding success..
    override fun onRoutingSuccess(route: ArrayList<Route>, shortestRouteIndex: Int) {
        var start = LatLng(lastLocation.latitude, lastLocation.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 17.0f))
        if (polylines != null) {
            mMap.clear()
            addMarker(mMap)
            mMap.addCircle(CircleOptions().center(LatLng(firstlocLat,firstlocLong)).radius(5.0).strokeColor(Color.BLACK).fillColor(Color.BLUE))
            var emptypolylines: List<Polyline>? = null
            polylines = emptypolylines
        }
        val polyOptions = PolylineOptions()
        var polylineStartLatLng: LatLng? = null
        var polylineEndLatLng: LatLng? = null
        polylines = ArrayList()
        //add route(s) to the map using polyline
        for (i in 0 until route.size) {
            if (i == shortestRouteIndex) {
                polyOptions.color(resources.getColor(android.R.color.holo_blue_dark))
                polyOptions.width(7f)
                polyOptions.addAll(route[shortestRouteIndex].getPoints())
                val polyline = mMap.addPolyline(polyOptions)
                polylineStartLatLng = polyline.points[0]
                val k = polyline.points.size
                polylineEndLatLng = polyline.points[k - 1]
                (polylines as ArrayList<Polyline>).add(polyline)
            } else {
            }
        }

        /*
        //Add Marker on route starting position
        val startMarker = MarkerOptions()
        startMarker.position(polylineStartLatLng!!)
        startMarker.title("My Location")
        mMap.addMarker(startMarker)

        //Add Marker on route ending position
        val endMarker = MarkerOptions()
        endMarker.position(polylineEndLatLng!!)
        endMarker.title("Destination")
        mMap.addMarker(endMarker)
        */
    }

    override fun onRoutingCancelled() {
        TODO("Not yet implemented")
    }

    fun filtrosClick(view: android.view.View) {
        val c1 : com.google.android.material.card.MaterialCardView = findViewById<com.google.android.material.card.MaterialCardView>(R.id.filterCard)
        c1.setVisibility(com.google.android.material.card.MaterialCardView.VISIBLE)
    }
}