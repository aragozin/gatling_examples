package wordstress

import io.gatling.core.Predef._

import scala.concurrent.duration._

class LinearRequestRateGrowRun extends CommonSimulation {

	  val USER_CAP = Math.ceil(50 * LOAD_FACTOR).asInstanceOf[Int];
	  val TARGET_RPS = Math.ceil(2.5 * LOAD_FACTOR).asInstanceOf[Int];

	  println("Max target RPS: " + TARGET_RPS);
          val TIME = 10 minutes;
          println("Duration: " + TIME);

		setUp(
				scenario("Linear RPS")
					.exec(Scripts.compositeWordpressLoad())

					.inject(rampConcurrentUsers(2).to(USER_CAP).during (TIME))

					.throttle(jumpToRps(1), reachRps(2) in (15 seconds), reachRps(TARGET_RPS) in (TIME - 5.seconds))

					.protocols(httpProtocol)
		).maxDuration(TIME + 1.minutes).disablePauses

}