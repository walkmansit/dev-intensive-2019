package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import ru.skillbranch.devintensive.R

/*fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}*/

fun Activity.hideKeyboard() {
    val view: View = if (currentFocus == null) View(this) else currentFocus
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardOpen() : Boolean {
    val display = windowManager.defaultDisplay
    var point = Point()
    display.getSize(point) // point.x - full height

    val rootView = window.decorView.rootView

    var rect = Rect()
    rootView.getWindowVisibleDisplayFrame(rect)

    return rect.height() != point.x
}
fun Activity.isKeyboardClosed() : Boolean {
    return !isKeyboardOpen()
}

/*fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}*/
