package com.example.demo

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Pattern


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var maindata: EditText? = null; var skipcount: EditText? = null
    private var submit: Button? = null
    private var substring: String = ""; var result: String = ""; var main: String = ""; var finalres: String = ""
    private var cancel: Boolean = false
    private var focusView: View? = null
    private var n: Int = 0
    private var builder: AlertDialog.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        maindata = findViewById(R.id.maindata)
        skipcount = findViewById(R.id.skipcount)
        submit = findViewById(R.id.submit)

        submit?.setOnClickListener(this)
    }

    override fun onClick(v: View?)
    {
        try
        {
            if(v == submit)
            {
                substring =""
                result = ""
                main = ""
                finalres = ""
                if(TextUtils.isEmpty(maindata?.text.toString()))
                {
                    maindata?.setError(getString(R.string.error_field_required));
                    focusView = maindata;
                    cancel = true;
                }
                else if(TextUtils.isEmpty(skipcount?.text.toString()))
                {
                    skipcount?.setError(getString(R.string.error_field_required));
                    focusView = skipcount;
                    cancel = true;
                }
                else
                {
                    main = maindata?.text.toString().trim()
                    var tempstr = skipcount?.text.toString().trim()
                    n = tempstr.toInt()

                    reversingData()
                }
            }
        }
        catch(ex: Exception)
        {
            Log.e("error",ex.localizedMessage)
            ex.printStackTrace()
        }
    }

    private fun reversingData() {

        val split = main.split(".").toTypedArray()
        for (i in 0 until split.size-1) {
            substring = split[i]
            var count = 0
            var ch = CharArray(substring.length)
            for (k in 0 until substring.length) {
                ch[k] = substring[k]

                if (k > 0 && ch[k] != ' ' && ch[k - 1] == ' ' || ch[0] != ' ' && k == 0) {
                    count++
                }
            }

            if (count > n) {
                val pattern: Pattern = Pattern.compile("\\s")
                val temp: Array<String> = pattern.split(substring)
                for (m in temp.size - (n + 1) downTo 0) {
                    finalres = finalres + temp[m] + " "
                }
                for (x in count - n until count) {
                    finalres = finalres + temp[x] + " "
                }
                finalres = finalres.substring(0, finalres.length - 1) + "."
            } else {
                finalres = finalres + substring + "."
            }
        }
       alertConfirm(finalres)
    }

    private fun alertConfirm(txt: String) {
        builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder?.setCancelable(true)
        builder?.setTitle(R.string.app_name)

        builder?.setMessage(txt)

        builder?.setIcon(R.drawable.yellow_alert_icon)
        builder?.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })

        val dialog: AlertDialog = builder!!.show()
        val messageText = dialog.findViewById<TextView>(R.id.message) as TextView
        messageText.gravity = Gravity.CENTER
        dialog.show()
    }
}
