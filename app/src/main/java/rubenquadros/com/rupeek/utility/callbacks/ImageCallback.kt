package rubenquadros.com.rupeek.utility.callbacks

import rubenquadros.com.rupeek.model.Data

interface ImageCallback {

    fun onResponseReceived(response: Data?)
}