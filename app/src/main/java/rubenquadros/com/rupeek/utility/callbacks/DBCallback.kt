package rubenquadros.com.rupeek.utility.callbacks

import rubenquadros.com.rupeek.localDatabase.ImageData

interface DBCallback {

    fun onQueryExecuted(localImageData: List<ImageData>?)
}