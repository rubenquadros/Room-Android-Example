package rubenquadros.com.rupeek.utility.application

import android.support.design.widget.Snackbar
import android.view.View

class ApplicationUtility {

    companion object {
        fun showSnack(msg: String, view: View, action: String){
            val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction(action) {
                snackBar.dismiss()
            }
            snackBar.show()
        }
    }
}