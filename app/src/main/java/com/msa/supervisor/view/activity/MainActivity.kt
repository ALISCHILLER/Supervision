package com.msa.supervisor.view.activity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.msa.supervisor.R
import com.msa.supervisor.databinding.ActivityMainBinding
import com.msa.supervisor.model.firebase.MyFirebaseMessagingService
import com.msa.supervisor.tool.CompanionValues
import com.msa.supervisor.tool.customtoast.MsaToast
import com.msa.supervisor.tool.customtoast.MsaToastStyle
import com.msa.supervisor.view.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * create by Ali Soleymani.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    //---------------------------------------------------------------------------------------------- onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()

    }
    //---------------------------------------------------------------------------------------------- onCreate


    //---------------------------------------------------------------------------------------------- initView
    private fun initView() {
        setListener()
        checkLocationPermission()
        // Create channel to show notifications.
        val channelId = getString(R.string.default_notification_channel_id)
        val channelName = getString(R.string.default_notification_channel_name)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager?.createNotificationChannel(
            NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW,
            ),
        )
        val oldtoken = sharedPreferences.getString(CompanionValues.tokenFirebase, "")
        var myFirebaseMessaging = MyFirebaseMessagingService()
        oldtoken?.let {
            myFirebaseMessaging.refreshToken(it, object : MyFirebaseMessagingService.Callback {
                override fun onSuccess() {
                }

                override fun onSuccess(token: String) {
                    sharedPreferences.edit().putString(CompanionValues.tokenFirebase, token)
                }

                override fun onError(error: String?) {
                    TODO("Not yet implemented")
                }

            })
        }


    }
    //---------------------------------------------------------------------------------------------- initView

//    private fun askNotificationPermission() {
//        // This is only necessary for API Level > 33 (TIRAMISU)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
//                PackageManager.PERMISSION_GRANTED
//            ) {
//                // FCM SDK (and your app) can post notifications.
//            } else {
//                // Directly ask for the permission
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//            }
//        }
//    }
//
//    private val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission(),
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//            Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT)
//                .show()
//        } else {
//            Toast.makeText(
//                this,
//                "FCM can't post notifications without POST_NOTIFICATIONS permission",
//                Toast.LENGTH_LONG,
//            ).show()
//        }
//    }

    //---------------------------------------------------------------------------------------------- setListener
    private fun setListener() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment?.navController
        navController?.addOnDestinationChangedListener { _, destination, _ ->
            showAndHideBottomNavigationMenu(destination.id)
        }

        binding.customMenuSetting.setOnClickListener {
            resetMenuColor()
            binding.customMenuSetting.selected()

        }

        binding.customMenuReport.setOnClickListener {
            resetMenuColor()
            binding.customMenuReport.selected()
            gotoFragment(R.id.action_goto_reportChooseFragment)
        }

        binding.customMenuHome.setOnClickListener {
            resetMenuColor()
            binding.customMenuHome.selected()
            gotoFragment(R.id.action_goto_HomeFragment)
        }

        binding.customMenuProfile.setOnClickListener {
            resetMenuColor()
            binding.customMenuProfile.selected()
            gotoFragment(R.id.action_goto_approvalsFragment)
        }

        binding.customMenuTracing.setOnClickListener {
            resetMenuColor()
            binding.customMenuTracing.selected()
            gotoFragment(R.id.action_goto_MapFragment)

        }

        binding.customMenuSetting.setOnClickListener {
            resetMenuColor()
            binding.customMenuSetting.selected()
            gotoFragment(R.id.action_goto_ProfileFragment)
        }

    }
    //---------------------------------------------------------------------------------------------- setListener


    //---------------------------------------------------------------------------------------------- showAndHideBottomNavigationMenu
    private fun showAndHideBottomNavigationMenu(fragmentId: Int) {
        resetMenuColor()
        when (fragmentId) {
            R.id.splashFragment,
            R.id.loginFragment,
            R.id.questionnaireFormFragment
            -> {
                binding.constraintLayoutFooterMenu.visibility = View.GONE
            }

            R.id.homeFragment -> {
                binding.constraintLayoutFooterMenu.visibility = View.VISIBLE
                binding.customMenuHome.selected()
            }

            R.id.reportChooseFragment -> {
                binding.constraintLayoutFooterMenu.visibility = View.VISIBLE
                binding.customMenuReport.selected()
            }

            R.id.mapFragment -> {
                binding.constraintLayoutFooterMenu.visibility = View.VISIBLE
                binding.customMenuTracing.selected()
            }

            R.id.approvalsFragment -> {
                binding.constraintLayoutFooterMenu.visibility = View.VISIBLE
                binding.customMenuProfile.selected()
            }

            R.id.profileSettingFragment -> {
                binding.constraintLayoutFooterMenu.visibility = View.VISIBLE
                binding.customMenuSetting.selected()
            }
        }
    }
    //---------------------------------------------------------------------------------------------- showAndHideBottomNavigationMenu


    //---------------------------------------------------------------------------------------------- resetMenuColor
    private fun resetMenuColor() {
        binding.customMenuSetting.clearSelected()
        binding.customMenuHome.clearSelected()
        binding.customMenuProfile.clearSelected()
        binding.customMenuReport.clearSelected()
        binding.customMenuTracing.clearSelected()
    }
    //---------------------------------------------------------------------------------------------- resetMenuColor


    //---------------------------------------------------------------------------------------------- showMessage
    fun showMessage(message: String) {
        Log.e("Error", "showMessage: ${message}")
//        val snack = Snackbar.make(binding.constraintLayoutParent, message, 5 * 1000)
//        val view = snack.view
//        val textView = (view).findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
//        val font = Typeface.createFromAsset(assets, "font/mitra_light.ttf")
//        textView.typeface = font
//        val params = view.layoutParams as FrameLayout.LayoutParams
//        params.gravity = Gravity.TOP
//        snack.setBackgroundTint(resources.getColor(R.color.snackBack, theme))
//        snack.setTextColor(resources.getColor(R.color.textColor, theme))
//        snack.setAction(getString(R.string.dismiss)) { snack.dismiss() }
//        snack.setActionTextColor(resources.getColor(R.color.disableText, theme))
//        snack.show()
        MsaToast.darkColorToast(
            this,
            "خطا ☹️",
            "$message !",
            MsaToastStyle.ERROR,
            MsaToast.GRAVITY_BOTTOM,
            MsaToast.LONG_DURATION,
            ResourcesCompat.getFont(this, R.font.mitra_bold),
            getColor(R.color.white)
        )
    }
    //---------------------------------------------------------------------------------------------- showMessage


    //---------------------------------------------------------------------------------------------- gotoFirstFragment
    fun gotoFirstFragment() {
        /*        deleteAllData()
                CoroutineScope(IO).launch {
                    delay(500)
                    withContext(Main) {
                        gotoFragment(R.id.action_goto_SplashFragment)
                    }
                }*/
    }
    //---------------------------------------------------------------------------------------------- gotoFirstFragment


    //---------------------------------------------------------------------------------------------- gotoFragment
    private fun gotoFragment(fragment: Int) {
        try {
            navController?.navigate(fragment, null)
        } catch (e: java.lang.Exception) {
            showMessage(getString(R.string.notFoundPage))
        }
    }
    //---------------------------------------------------------------------------------------------- gotoFragment

    private fun checkLocationPermission() {

        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableListOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            mutableListOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }

        Dexter.withContext(this)
            .withPermissions(permission)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {

                }

            })
            .check()
    }

    //---------------------------------------------------------------------------------------------- deleteAllData
    fun deleteAllData() {
//        mainViewModel.deleteAllData()
    }
    //---------------------------------------------------------------------------------------------- deleteAllData



    fun popFragment() {
        supportFragmentManager.popBackStack()
    }

    override fun onBackPressed() {
        if (getCurrentFragment() is BaseFragment<*>)
            (getCurrentFragment() as BaseFragment<*>?)?.onBackPressed()
        else
            popFragment()
    }

    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
    }

    private fun fragmentCount(): Int {
        return supportFragmentManager.backStackEntryCount
    }

}