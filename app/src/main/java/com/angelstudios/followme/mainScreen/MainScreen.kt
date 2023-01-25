import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.hilt.navigation.compose.hiltViewModel
import com.angelstudios.followme.R
import com.angelstudios.followme.components.SimpleDialog
import com.angelstudios.followme.global.extention.getActivity
import com.angelstudios.followme.global.utils.DENIED
import com.angelstudios.followme.global.utils.EXPLAINED
import com.angelstudios.followme.global.utils.checkLocationSetting
import com.angelstudios.followme.mainScreen.MainViewModel
import com.angelstudios.framework.service.LocationService
import com.google.android.gms.location.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel(), onNavigate: (String) -> Unit) {


    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    var deniedList: List<String>

    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.startLocationServices.collect { startLocationServices ->
            if (startLocationServices) {
                Intent(context, LocationService::class.java)
                    .let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            context.startForegroundService(it)
                        } else {
                            context.startService(it)
                        }
                    }
            }
        }
    }

    val requestEnableLocationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_OK)
            viewModel.startStopUpdatingLocation()
        else {
            viewModel.showSnackBar(context.getString(R.string.GPS_should_be_enabled))
        }
    }

    val requestLocationPermissionsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            deniedList = result.filter {
                !it.value
            }.map {
                it.key
            }

            if (deniedList.isEmpty()) {
                // all permissions are accepted ,verify GPS is enabled
                checkLocationSetting(
                    context = context,
                    onDisabled = { intentSenderRequest ->
                        // launch gps enabling dialog
                        requestEnableLocationLauncher.launch(intentSenderRequest)
                    },
                    onEnabled = {
                        // start updating Geolocation
                        viewModel.startStopUpdatingLocation()
                    }
                )
            } else {
                val map = deniedList.groupBy { permission ->
                    context.getActivity()?.let { activity ->
                        if (shouldShowRequestPermissionRationale(
                                activity,
                                permission
                            )
                        ) DENIED else EXPLAINED
                    }
                }
                map[DENIED]?.let {
                    // request location permission Denied
                }
                map[EXPLAINED]?.let {
                    viewModel.showGoToSettingsDialog()
                }

            }

        }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    requestLocationPermissionsLauncher.launch(requiredPermissions)
                }
            ) {

                // gotta be changed to reactive
                if (LocationService.ijij.collectAsState(initial = false).value) {
                    Icon(
                        Icons.Filled.Star,
                        stringResource(R.string.start_update_location)
                    )
                } else {
                    Icon(
                        Icons.Filled.PlayArrow,
                        stringResource(R.string.start_update_location)
                    )
                }

            }
        },
        content = {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    Modifier
                        .padding(it)
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LaunchedEffect(key1 = context) {
                        viewModel.showSnackBar.collect { msg ->
                            scope.launch {
                                snackBarHostState.showSnackbar(msg)
                            }
                        }
                    }
                    if (viewModel.showLocationsSettingsDialog.collectAsState(initial = false).value) {
                        SimpleDialog(
                            title = R.string.gps_settings,
                            successButtonText = R.string.ok,
                            cancelButtonText = R.string.cancel,
                            body = R.string.gps_dialog_body,
                            onDismiss = {
                                viewModel.hideGoToSettingsDialog()
                            },
                            onAction = {
                                gotoSettings(context)
                                viewModel.hideGoToSettingsDialog()
                            }
                        )
                    }
                }
            }
        }
    )
}

private fun gotoSettings(context: Context) {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri: Uri =
        Uri.fromParts("package", context.getActivity()?.packageName, null)
    intent.data = uri
    context.startActivity(intent)
}




