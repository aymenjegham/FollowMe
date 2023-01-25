package com.angelstudios.framework.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.angelstudios.core.usecase.point.startLocationUpdates.StartLocationUpdatesUseCase
import com.angelstudios.framework.R
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.util.concurrent.ExecutionException
import java.util.concurrent.Flow
import javax.inject.Inject


const val SYNC_INTERVAL = 2 * 60000L
const val GEOFENCE_DWELLING: Long = 20000


@AndroidEntryPoint
class LocationService : Service() {


    private val serviceScope = CoroutineScope(Dispatchers.IO)


    override fun onBind(p0: Intent?): IBinder? = null


    @Inject
    lateinit var starLocationUpdateUseCase: StartLocationUpdatesUseCase
//
//    @Inject
//    lateinit var stopLocationUpdatesUseCase: StopLocationUpdatesUseCase
//
//    @Inject
//    lateinit var getScheduledSitesUseCase: GetScheduledSitesUseCase
//
//    @Inject
//    private lateinit var startGeofencingUseCase: StartGeofencingUseCase
//
//    @Inject
//    private lateinit var stopGeofencingUseCase: StopGeofencingUseCase
//
//    @Inject
//    private lateinit var updateGeofencesUseCase: UpdateGeofencesUseCase


    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManagerCompat


    //private var geofences: Map<String, Geofence>? = null

    private var isGeoFenceListeningStarted: Boolean = false

    var workManagerInstance = WorkManager.getInstance(this@LocationService)


    override fun onCreate() {
        super.onCreate()
        initService()
        syncData()
        startLocationUpdates()
        serviceIsStarted(true)

        //  listenForScheduledSites()


    }

    private fun serviceIsStarted(isRunning: Boolean) {
        ijij = flow {
            emit(isRunning)
        }
    }


//    private fun listenForScheduledSites() {
//        serviceScope.launch(Dispatchers.IO) {
//            getScheduledSitesUseCase().mapNotNull {
//
//                it?.filterNot {
//                    (it.state == InterventionStates.ACHIEVED && it.isPrecise == false)
//                }?.distinctBy {
//                    it.siteName
//                }?.mapNotNull { site ->
//                    site.run {
//                        if (this.latitude != null && this.longitude != null) {
//                            Geofence(
//                                interventionId,
//                                this.siteName!!,
//                                this.latitude!!,
//                                this.longitude!!,
//                                radius!!.toDouble(),
//                                scheduledStartAt ?: Date()
//                            )
//                        } else null
//                    }
//                }
//            }.filterNotNull()
//                .collect {
//                    geofences = it.map { geofence -> geofence.id to geofence }.toMap()
//                    updateGeofencesUseCase(it)
//                }
//        }
//    }

    private fun initService() {
        serviceScope.launch {

            val notification =
                notificationBuilder
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(getString(R.string.notif_content))
                    .build()

            startForeground(1, notification)
        }
    }

    private fun listenForGeofencesEvents() {
        serviceScope.launch {
//            startGeofencingUseCase(
//                GEOFENCE_DWELLING,
//                geofences?.values?.toList()
//            ).collect(::handleGeoFencingEvent)

        }
    }

//    private suspend fun handleGeoFencingEvent(events: List<GeofencingEvent>) {
//        events.map { geoFencingEvent ->
//            getActivityTypeBySlug(ACTIVITY_TYPE_TABLE_SLUG_WORK).run {
//
//                if (geoFencingEvent.type == GeofencingEventType.ENTER && this.isAutoStart) {
//                    //startActivity(geoFencingEvent.geofence.id,geoFencingEvent.timestamp)
//                    showGeoFencingEventNotification(listOf(geoFencingEvent))
//                } else if ((geoFencingEvent.type == GeofencingEventType.EXIT && this.isAutoStop)) {
//                    stopCurrentActivity(geoFencingEvent.timestamp)
//                    showGeoFencingEventNotification(listOf(geoFencingEvent))
//                }
//            }
//        }
//
//
//    }

//    private fun showGeoFencingEventNotification(events: List<GeofencingEvent>) {
//        events.forEach { event ->
//            geofences?.get(event.geofence.id)?.also { workLocation ->
//                showGeoFencingNotification(event, workLocation)
//            }
//        }
//    }

//    private fun showGeoFencingNotification(
//        event: GeofencingEvent,
//        geoFence: Geofence
//    ) {
//        val title =
//            if (event.type == GeofencingEventType.ENTER)
//                getString(R.string.entrance_detected)
//            else
//                getString(R.string.exit_detected)
//
//        val body =
//            "${
//                if (event.type == GeofencingEventType.ENTER) getString(R.string.entrance_to) else getString(
//                    R.string.exit_from
//                )
//            } ${geoFence.name} ${getString(R.string.has_been_detected_at)} ${
//                Date(event.timestamp).format(TIME_FORMAT)
//            }"
//
//        val pendingIntent = Intent(this, HomeActivity::class.java).let {
//            PendingIntent.getActivity(this, 0, it, PendingIntent.FLAG_UPDATE_CURRENT)
//        }
//
//        val actionIntentAction = Intent(this, ActionReceiver::class.java)
//        actionIntentAction.putExtra(GEOFENCE_EXTRA, geoFence.copy(geofencingEvent = event))
//
//        val cancelIntentAction = Intent(this, CancelReceiver::class.java)
//        cancelIntentAction.putExtra(GEOFENCE_EXTRA, geoFence.copy(geofencingEvent = event))
//
//
//        val actionPendingIntent =
//            PendingIntent.getBroadcast(
//                this,
//                0,
//                actionIntentAction,
//                PendingIntent.FLAG_UPDATE_CURRENT
//            )
//
//        val cancelPendingIntent =
//            PendingIntent.getBroadcast(
//                this,
//                geoFence.hashCode(),
//                cancelIntentAction,
//                PendingIntent.FLAG_UPDATE_CURRENT
//            )
//
//        with(NotificationManagerCompat.from(this)) {
//            if (event.type == GeofencingEventType.ENTER) {
//
//
//                if (getStateOfWork() != WorkInfo.State.ENQUEUED && getStateOfWork() != WorkInfo.State.RUNNING) {
//                    scheduleStartActivityJob(geoFence.copy(geofencingEvent = event))
//                } else {
//                    workManagerInstance.cancelUniqueWork("xxxx")
//                }
//
//                notify(
//                    geoFence.scheduledStartAt.time.toInt(),
//                    buildNotification(
//                        title,
//                        body,
//                        pendingIntent,
//                        actionPendingIntent,
//                        cancelPendingIntent,
//                        true
//                    )
//                )
//                app.setGeofencingEvent(geoFence.copy(geofencingEvent = event))
//            } else {
//                notify(
//                    geoFence.scheduledStartAt.time.toInt(),
//                    buildNotification(
//                        title,
//                        body,
//                        pendingIntent,
//                        actionPendingIntent,
//                        cancelPendingIntent,
//                        false
//                    )
//                )
//            }
//
//        }
//    }

    private fun getStateOfWork(): WorkInfo.State? {
        return try {
            if (workManagerInstance.getWorkInfosForUniqueWork("xxxx")
                    .get().size > 0
            ) {
                workManagerInstance.getWorkInfosForUniqueWork("xxxx")
                    .get()[0].state
            } else {
                WorkInfo.State.CANCELLED
            }
        } catch (e: ExecutionException) {
            e.printStackTrace()
            WorkInfo.State.CANCELLED
        } catch (e: InterruptedException) {
            e.printStackTrace()
            WorkInfo.State.CANCELLED
        }
    }

//    private fun scheduleStartActivityJob(geofence: Geofence) {
//        val data = Data.Builder()
//
//        DebugLog.v("checkingResult", gson.toJson(geofence))
//        data.putString("GEOFENCE_EXTRA_WORKER", gson.toJson(geofence))
//
//        val activityStarterRequest =
//            OneTimeWorkRequestBuilder<ActivityStarterWorker>()
//                .setInitialDelay(1, TimeUnit.MINUTES)
//                .setInputData(data.build())
//                .addTag(geofence.id)
//                .build()
//
//        workManagerInstance
//            .enqueueUniqueWork(
//                "xxxx",
//                ExistingWorkPolicy.KEEP,
//                activityStarterRequest
//            )
//    }


    override fun onDestroy() {
        // stopLocationUpdatesUseCase()
        stopGeofencing()
        stopForeground(STOP_FOREGROUND_DETACH)
        //  app.setWorkDayState(false)
        serviceScope.cancel()
        serviceIsStarted(false)
        super.onDestroy()
    }

    private fun syncData() {
        serviceScope.launch {
            withContext(serviceScope.coroutineContext + Dispatchers.IO) {
                delay(SYNC_INTERVAL)
                runCatching {
                    //    syncroManger.sync()
                }
            }
            syncData()
        }
    }


    private fun startLocationUpdates() {
        serviceScope.launch(serviceScope.coroutineContext + Dispatchers.Main) {

            val speedLimit = 20

            starLocationUpdateUseCase().collect { point ->

                point?.speed?.let { speed ->
                    if (speed > speedLimit) {

                    }
                }

                if (!isGeoFenceListeningStarted) {
                    listenForGeofencesEvents()
                    isGeoFenceListeningStarted = true
                }
            }
        }
    }

    private fun showOtherActivityExitNotification() {
//        val pendingIntent = Intent(this, HomeActivity::class.java).let {
//            PendingIntent.getActivity(this, 0, it, PendingIntent.FLAG_UPDATE_CURRENT)
//        }
//
//        with(NotificationManagerCompat.from(this)) {
//
//            val title = getString(R.string.exit_detected)
//
//            val body =
//                "${
//                    getString(R.string.exit_from)
//                } site ${getString(R.string.has_been_detected_at)} ${
//                    Date().format(TIME_FORMAT)
//                }"
//
//
//            notify(
//                0,
//                buildNotification(
//                    title,
//                    body,
//                    pendingIntent,
//                    null,
//                    null,
//                    false
//                )
//            )
//        }
    }

    private fun stopGeofencing() {
//        serviceScope.launch {
//            runCatching {
//                stopGeofencingUseCase()
//            }.onFailure {
//                Firebase.crashlytics.recordException(it)
//                DebugLog.e("Altagem_error", "stopWorkLocationGeoFencing", it)
//
//            }
//        }
    }


    private suspend fun startActivity(interventionId: String, activityStartTime: Long) {

//        serviceScope.launch(serviceScope.coroutineContext + Dispatchers.IO) {
//
//            val currentActivity = getLastLoggedInUserUseCase()
//                ?.run { getCurrentWorkdayFlowUseCase(id).first() }
//                ?.run { getOnGoingActivityFlowUseCase(id) }?.first()
//
//            if (currentActivity?.activityType?.slug != "work") {
//                switchActivitiesUseCase(
//                    currentActivity,
//                    interventionId,
//                    ActivityDataType.INTERVENTION,
//                    null,
//                    null,
//                    activityStartTime
//                )
//            }
//        }
    }

    private suspend fun stopCurrentActivity(activityStartTime: Long) {

//        serviceScope.launch(serviceScope.coroutineContext + Dispatchers.IO) {
//            lateinit var onGoingStartedAt: Date
//
//            getLastLoggedInUserUseCase()
//                ?.let { user ->
//                    getCurrentWorkdayFlowUseCase(user.id).first()
//                }?.let { workday ->
//                    getOnGoingActivityFlowUseCase(workday.id)
//                }?.first()?.let {
//
//                    onGoingStartedAt = it.startedAt
//
//                    getSimultaneousActivitiesOfTeamDeclaredUseCase(
//                        getLastLoggedInUserUseCase()!!.id,
//                        it.activityableId,
//                        it.activityableType,
//                        it.declarativeId,
//                        it.activityTypeId,
//                        it.startedAt
//                    )
//                }?.let {
//                    it.filter {
//                        it.startedAt == onGoingStartedAt
//                    }.let {
//                        switchTeamsActivitiesToCommutingUseCase(it, activityStartTime)
//                    }
//
//                }
//
//        }
    }

    companion object {

        var ijij = flow { emit(false) }
    }
}