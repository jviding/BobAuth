package configurations

import javax.inject.Inject
import akka.stream.Materializer
import play.api.mvc._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import java.text.SimpleDateFormat
import java.util.Date


class ResponseFilter @Inject() (implicit val mat: Materializer, ec: ExecutionContext) extends Filter {
    
    def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
        nextFilter(requestHeader).map { result =>
            val timeStamp: String = getTimeStamp()
            val method: String = requestHeader.method
            val path: String = requestHeader.uri
            val httpProtocol: String = requestHeader.version
            val status: Int = result.header.status

            println(s"""[${timeStamp}] "${method} ${path} ${httpProtocol}" ${status}""")

            result
        }
    }

    private def getTimeStamp(): String = {
        (new SimpleDateFormat("dd/LLL/yyyy HH:mm:ss")).format(new Date()).toString()
    }
}