package com.smartherd.msgshareapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.smartherd.msgshareapp.Constants
import com.smartherd.msgshareapp.R
import com.smartherd.msgshareapp.showToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShowToast.setOnClickListener {
            Log.i(TAG, "Button was clicked !")
            showToast(resources.getString(R.string.btn_was_clicked),Toast.LENGTH_LONG)

            //Toast.makeText(this,"Button was clicked!",Toast.LENGTH_SHORT).show()
        }

        btnContact.setOnClickListener {

        }

        btnSendMsgToNextActivity.setOnClickListener {


            val message: String = etUserMessage.text.toString()

            //Toast.makeText(this,message,Toast.LENGTH_SHORT).show()

            val intent = Intent(this, SecondActivity::class.java)

            intent.putExtra(Constants.USER_MSG_KEY,message)

            startActivity(intent)
        }

        btnShareToOtherApps.setOnClickListener {

            val message: String = etUserMessage.text.toString()

            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Please select app: "))

        }

        btnRecycleViewDemo.setOnClickListener {
            val intent = Intent(this, HobbiesActivity::class.java)
            startActivity(intent)
        }
    }

}
