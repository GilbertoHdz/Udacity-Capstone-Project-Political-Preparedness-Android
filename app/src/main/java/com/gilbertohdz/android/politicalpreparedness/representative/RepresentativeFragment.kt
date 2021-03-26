package com.gilbertohdz.android.politicalpreparedness.representative

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gilbertohdz.android.politicalpreparedness.R
import com.gilbertohdz.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.gilbertohdz.android.politicalpreparedness.network.models.Address
import com.gilbertohdz.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_representative.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class DetailFragment : Fragment() {

    companion object {
        // DONE: Add Constant for Location request
        private const val REQUEST_LOCATION_PERMISSION = 717
    }

    // DONE: Declare ViewModel
    private val viewModel: RepresentativeViewModel by viewModel()

    private lateinit var binding: FragmentRepresentativeBinding
    private lateinit var representativeAdapter: RepresentativeListAdapter

    lateinit var states: Array<out String>

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // DONE: Establish bindings
        binding = FragmentRepresentativeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // DONE: Define and assign Representative adapter
        states = resources.getStringArray(R.array.states)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, states)
        binding.state.adapter = adapter

        // DONE: Populate Representative adapter
        representativeAdapter = RepresentativeListAdapter()
        binding.representativeTitle
        binding.representativeRecyclerView.adapter = representativeAdapter

        // DONE: Establish button listeners for field and location search
        binding.buttonSearch.setOnClickListener {
            hideKeyboard()

            val line2 = binding.addressLine2.text.toString()
            val address = Address(
                    line1 = binding.addressLine1.text.toString(),
                    line2 = if (line2 == "") null else line2,
                    city = binding.city.text.toString(),
                    state = binding.state.selectedItem.toString(),
                    zip = binding.zip.text.toString()
            )

            viewModel.getRepresentatives(address)
        }

        binding.buttonLocation.setOnClickListener {
            hideKeyboard()
            checkLocationPermissions()
        }

        // Dummy data
        // val address = Address("1886  Bridge Street", "", "Fort Smith", "Oklahoma", "72901")
        // viewModel.getRepresentatives(address)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.representatives.observe(viewLifecycleOwner, Observer { representatives ->
            representativeAdapter.submitList(representatives)
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // DONE: Handle location permission result to get location on permission granted
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (requireContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }
        }
    }

    private fun checkLocationPermissions(): Boolean {
        return if (isPermissionGranted()) {
            getLocation()
            true
        } else {
            // DONE: Request Location permissions
            requestPermissions(
                    arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
            )
            false
        }
    }

    private fun isPermissionGranted() : Boolean {
        // DONE: Check if permission is already granted and return (true = granted, false = denied/other)
        return requireContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    /**
     * Source for ForeGround Locations with LocationServices
     * https://developer.android.com/training/location/permissions
     *
     * However, location was always null, and adapted to suggested code from
     * https://stackoverflow.com/questions/29441384/fusedlocationapi-getlastlocation-always-null
     */
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        // DONE: Get location from LocationServices
        // DONE: The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    location?.let{
                        // create Address object from the received Location object
                        val address = geoCodeLocation(location)

                        // populate the address fields with the retrieved address data
                        binding.addressLine1.setText(address.line1)
                        binding.addressLine2.setText(address.line2)
                        binding.city.setText(address.city)
                        binding.zip.setText(address.zip)

                        // find the spinner position of the state by the retrieved location
                        val spinerPos = states.indexOf(address.state)
                        if (spinerPos >= 0){
                            // update the spinner to the retrieved state
                            binding.state.setSelection(spinerPos)
                        }

                        // fetch representatives for that address from the network
                        viewModel.getRepresentatives(address)
                    }
                }
            }
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null)
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
                .map { address ->
                    Address(address.thoroughfare ?: "", address.subThoroughfare ?: "", address.locality ?: "", address.adminArea ?: "", address.postalCode ?: "")
                }
                .first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }
}