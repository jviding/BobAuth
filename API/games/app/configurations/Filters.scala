package configurations

import javax.inject.Inject
import play.api.http.DefaultHttpFilters
import play.api.http.EnabledFilters
import play.filters.gzip.GzipFilter

class Filters @Inject() (
    defaultFilters: EnabledFilters,
    gzip: GzipFilter,
    response: ResponseFilter
) extends DefaultHttpFilters(defaultFilters.filters :+ gzip :+ response: _*)