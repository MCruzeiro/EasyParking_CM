package ipvc.estg.easyparking

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.estg.easyparking.api.EndPoints
import ipvc.estg.easyparking.api.ServiceBuilder
import ipvc.estg.easyparking.api.User
import ipvc.estg.easyparking.databinding.ActivityMapsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var  lastLocation : Location

    //added to implement distance between two locations
    private var continenteLat: Double = 0.0
    private var continenteLong: Double = 0.0

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
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.0f))
                val address = getAddress(lastLocation.latitude, lastLocation.longitude)
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
// interval specifies the rate at which your app will like to receive updates.
        locationRequest.interval = 10000
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
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val p1 = LatLng(41.6946, -8.83016)
        mMap.addMarker(MarkerOptions().position(p1).title("Parque Estacionamento Gil Eanes"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p1))

        val p2 = LatLng(41.691002, -8.828173)
        mMap.addMarker(MarkerOptions().position(p2).title("Parque de Estacionamento Avenida"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p2))

        val p3 = LatLng(41.695546, -8.828924)
        mMap.addMarker(MarkerOptions().position(p3).title("Parque de estacionamento 1º de Maio"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p3))

        val p4 = LatLng(41.691765, -8.838676)
        mMap.addMarker(MarkerOptions().position(p4).title("Parque de Estacionamento Campo da Agonia"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p4))

        val p5 = LatLng(41.696037, -8.822682)
        mMap.addMarker(MarkerOptions().position(p5).title("Parque Estacionamento Afonso III"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p5))

        val p6 = LatLng(41.696763, -8.831194)
        mMap.addMarker(MarkerOptions().position(p6).title("Parque Magma"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p6))

        val p7 = LatLng(41.695049, -8.825733)
        mMap.addMarker(MarkerOptions().position(p7).title("Parque do Mercado"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p7))

        val p8 = LatLng(41.698397, -8.831994)
        mMap.addMarker(MarkerOptions().position(p8).title("Parque do Ulsam"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p8))

       /* val request = ServiceBuilder.buildService(EndPoints::class.java)
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
                                user.address.suite + " - " + user . address . city))
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {

            }
        }) */
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
}