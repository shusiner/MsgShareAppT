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
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.DocumentReference
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    var db = FirebaseFirestore.getInstance()

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        auth = FirebaseAuth.getInstance()
        auth.signInAnonymously().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInAnonymously:success")
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInAnonymously:failure", task.exception)
                Toast.makeText(
                    baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnShowToast.setOnClickListener {
            Log.i(TAG, "Button was clicked !")
            showToast(resources.getString(R.string.btn_was_clicked),Toast.LENGTH_LONG)

            //Toast.makeText(this,"Button was clicked!",Toast.LENGTH_SHORT).show()

            db.collection("users")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            Log.d(TAG, document.id + " => " + document.data)
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.exception)
                    }
                }

        }

        btnContact.setOnClickListener {
            val user = HashMap<String, Any>()
            user.put("first", "Alan")
            user.put("middle", "Mathison")
            user.put("last", "Turing")
            user.put("born", 1912)

// Add a new document with a generated ID
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG,"DocumentSnapshot added with ID: " + documentReference.id)
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
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
