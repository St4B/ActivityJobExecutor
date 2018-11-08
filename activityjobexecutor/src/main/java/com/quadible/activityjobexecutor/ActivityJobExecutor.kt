package com.quadible.activityjobexecutor

import android.app.Activity
import android.app.Application
import io.github.pau1adam.currentactivitysample.CurrentActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

object ActivityJobExecutor : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var jobs : MutableList<ActivityJob> = mutableListOf()

    @JvmStatic fun init(application: Application) {
        CurrentActivity.init(application)
    }

    @JvmStatic fun execute(job: ActivityJob) {
        val activity = CurrentActivity.get()

        if (activity != null) {
            job.run(activity)
        } else {
            jobs.add(job)
            observe()
        }
    }

    private fun executePending() {
        val iterator = jobs.listIterator()
        while(iterator.hasNext()){
            val job = iterator.next()
            iterator.remove()
            execute(job)
        }

        for (job in jobs) {
            jobs.remove(job)
            execute(job)
        }
    }

    private fun observe() {
        launch(coroutineContext){
            while (CurrentActivity.get() == null) {
                //suspend for some time
                delay(10)
            }
            executePending()
        }
    }

    interface ActivityJob {

        fun run(activity: Activity)

    }

}