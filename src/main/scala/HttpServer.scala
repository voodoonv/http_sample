import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, StatusCode, StatusCodes}
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

object HttpServer extends App{

  val host = "0.0.0.0"
  val port = 9000

  implicit val system: ActorSystem = ActorSystem("httpServer")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  var devInfo = scala.collection.mutable.Map[String, String]()

  devInfo += ("Demo" -> "Software developer")

  def route = path("users") {
    concat(
      get{
        complete(Json.stringify(Json.toJson(devInfo)))
      },
      post{
        entity(as[HttpEntity]){ entity =>{
          val responseString: Future[String] = Unmarshal(entity).to[String]
          responseString onSuccess {
            case msg =>
              val json = Json.parse(msg)
              val devName = json("devName").validate[String].get
              val description = json("description").validate[String].get

              devInfo += (devName -> description)
        }
        complete(StatusCodes.Created)
      }
      }
    },
    delete{
      complete("")
    }
    )
  }


  Http().bindAndHandle(route, host, port)
  println(s"Your server is running here: $host:$port")

}
