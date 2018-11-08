package com.quadible.app

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.quadible.activityjobexecutor.ActivityJobExecutor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityJobExecutor.execute(Logger("onCreate()"))
    }

    override fun onPause() {
        super.onPause()
        ActivityJobExecutor.execute(Logger("onPause() even if the activity is paused! WOW"))
    }

    private class Logger(val text : String) : ActivityJobExecutor.ActivityJob {

        override fun run(activity: Activity) {
            AlertDialog.Builder(activity)
                    .setMessage(text)
                    .setPositiveButton("Ok", null)
                    .show()
        }

    }

}
