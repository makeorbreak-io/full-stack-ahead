package xyz.fullstackahead.where2go.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.MenuItem


fun showDialog(title: String, text: String, context: Context): AlertDialog? {
    val alertDialog = AlertDialog.Builder(context).create()
    alertDialog.setTitle(title)
    alertDialog.setMessage(text)
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, _ -> dialog.dismiss() }
    alertDialog.show()
    return alertDialog
}


fun tintMenuItem(item: MenuItem?) {
    val icon = item?.icon
    icon?.mutate()?.setColorFilter(Color.rgb(255, 255, 255), PorterDuff.Mode.SRC_IN)
    item?.icon = icon
}
