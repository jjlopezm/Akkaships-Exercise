package es.codemotion.akkaships.server

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.routing.{BroadcastGroup, RoundRobinPool}
import es.codemotion.akkaships.common.domain._
import es.codemotion.akkaships.server.actors.{BoardActor, ShipActor, StatisticsActor}
import es.codemotion.akkaships.server.config.ServerConfig
import org.apache.commons.daemon.{Daemon, DaemonContext}
import org.apache.log4j.Logger

class Server extends Daemon with ServerConfig {

  override lazy val logger = Logger.getLogger(classOf[Server])

  var system = ActorSystem(clusterName, config)


  override def init(p1: DaemonContext): Unit = ()

  override def start(): Unit = {

    val statisticsActor = ???
    val boardActor = ???

    /* Round Robin Router*/
    val statisticsRouter: ActorRef = ???

    /* Ship Actors  */
    system.actorOf(Props(new ShipActor(Ship(Position(1, 2), Vertical, 4), boardActor, statisticsRouter)), "Portaviones1")
    /* ... */

    /* Ships Broadcast Router */
    val routees = Vector[String]()
    system.actorOf(BroadcastGroup(routees).props(), "server")

    logger.info("Akka Ship Server Started")
  }

  override def stop(): Unit = {
    system.shutdown()
    logger.info("Akka Ship Server Stopped")
  }

  override def destroy(): Unit = ()
}
