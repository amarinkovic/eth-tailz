package io.mankea.eth.streamer.service

import io.mankea.eth.streamer.config.AppConfig
import org.web3j.abi.datatypes.generated.{Bytes32, Uint256}
import org.web3j.abi.datatypes.{Address, Event, Type, Utf8String}
import org.web3j.abi.{EventEncoder, FunctionReturnDecoder, TypeReference}
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.EthLog.LogObject
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Numeric

import java.nio.charset.StandardCharsets
import scala.jdk.CollectionConverters._

type Web3jEventType = Event

sealed trait TypedEvent

case class InitializeDiamond(sender: String) extends TypedEvent
case class OwnershipTransferred(previousOwner: String, newOwner: String) extends TypedEvent
case class RoleCanAssignUpdated(role: String, group: String) extends TypedEvent
case class RoleUpdated(objectId: String, contextId: String, roleId: String, funcName: String) extends TypedEvent
case class EntityCreated(entityId: String, entityAdmin: String) extends TypedEvent
case class EntityUpdated(entityId: String) extends TypedEvent
case class SimplePolicyCreated(id: String, entityId: String) extends TypedEvent
case class TokenSaleStarted(entityId: String, offerId: Long, tokenSymbol: String, tokenName: String) extends TypedEvent
case class Unsupported(topic: String) extends TypedEvent

object EventResolver {

  private val evenTypes: Map[String, Web3jEventType] = List(
    new Web3jEventType("TokenSaleStarted", List(
      new TypeReference[Bytes32](true) {},      //  entityId
      new TypeReference[Uint256](false) {},     //  offerId
      new TypeReference[Utf8String](false) {},  //  tokenSymbol
      new TypeReference[Utf8String](false) {}   //  tokenName
    ).asJava),

    new Web3jEventType("SimplePolicyCreated", List(
      new TypeReference[Bytes32](true) {}, //  policyId
      new TypeReference[Bytes32](false) {} //  entityId
    ).asJava),

    new Web3jEventType("EntityUpdated", List(
      new TypeReference[Bytes32](true) {}     //  entityId
    ).asJava),

    new Web3jEventType("EntityCreated", List(
      new TypeReference[Bytes32](true) {},    //  entityId
      new TypeReference[Bytes32](false) {}    //  entityAdmin
    ).asJava),

    new Web3jEventType("RoleUpdated", List(
      new TypeReference[Bytes32](true) {},    //  objectId
      new TypeReference[Bytes32](false) {},   //  contextId
      new TypeReference[Bytes32](false) {},   //  roleId
      new TypeReference[Utf8String](false) {} //  functionName
    ).asJava),

    new Web3jEventType("RoleCanAssignUpdated", List(
      new TypeReference[Utf8String](false) {},  // role
      new TypeReference[Utf8String](false) {}   // group
    ).asJava),

    new Web3jEventType("InitializeDiamond", List(
      new TypeReference[Address](false) {}    // sender
    ).asJava),

    new Web3jEventType("OwnershipTransferred", List(
      new TypeReference[Address](true) {},    // previous owner
      new TypeReference[Address](true) {}     // new owner
    ).asJava),

    new Web3jEventType("InitializeDiamond", List(
      new TypeReference[Address](false) {}    // sender
    ).asJava)

  ).map { event =>
    (EventEncoder.encode(event), event)
  }.toMap

  private def getName(topic: String): String = evenTypes.get(topic).map(_.getName).getOrElse(topic)

  def getTypedEvent(obj: LogObject): TypedEvent = {
    val topic = obj.getTopics.get(0)
    val eventName = getName(topic)

    evenTypes.get(topic).map(t => decode(obj, t)) match {
      case Some(decodedAttrs) => {
        eventName match
          case "TokenSaleStarted" =>
            TokenSaleStarted(
              entityId = Numeric.toHexString(decodedAttrs.asJava.get(0).asInstanceOf[Bytes32].getValue),
              offerId = decodedAttrs.asJava.get(1).asInstanceOf[Uint256].getValue.longValueExact(),
              tokenSymbol = decodedAttrs.asJava.get(2).asInstanceOf[Utf8String].getValue,
              tokenName = decodedAttrs.asJava.get(3).asInstanceOf[Utf8String].getValue
            )
          case "SimplePolicyCreated" =>
            SimplePolicyCreated(
              id = Numeric.toHexString(decodedAttrs.asJava.get(0).asInstanceOf[Bytes32].getValue),
              entityId = Numeric.toHexString(decodedAttrs.asJava.get(1).asInstanceOf[Bytes32].getValue)
            )
          case "EntityCreated" =>
            EntityCreated(
              entityId = Numeric.toHexString(decodedAttrs.asJava.get(0).asInstanceOf[Bytes32].getValue),
              entityAdmin = Numeric.toHexString(decodedAttrs.asJava.get(1).asInstanceOf[Bytes32].getValue)
            )
          case "EntityUpdated" =>
            EntityUpdated(
              entityId = Numeric.toHexString(decodedAttrs.asJava.get(0).asInstanceOf[Bytes32].getValue)
            )
          case "RoleUpdated" =>
            RoleUpdated(
              objectId = Numeric.toHexString(decodedAttrs.asJava.get(0).asInstanceOf[Bytes32].getValue),
              contextId = Numeric.toHexString(decodedAttrs.asJava.get(1).asInstanceOf[Bytes32].getValue),
              roleId = Numeric.toHexString(decodedAttrs.asJava.get(2).asInstanceOf[Bytes32].getValue),
              funcName = decodedAttrs.asJava.get(3).asInstanceOf[Utf8String].getValue
            )
          case "RoleCanAssignUpdated" =>
            RoleCanAssignUpdated(
              role = decodedAttrs.asJava.get(0).getValue.toString,
              group = decodedAttrs.asJava.get(1).getValue.toString
            )
          case "OwnershipTransferred" =>
            OwnershipTransferred(
              previousOwner = decodedAttrs.asJava.get(0).getValue.toString,
              newOwner = decodedAttrs.asJava.get(1).getValue.toString
            )
          case "InitializeDiamond" =>
            InitializeDiamond(sender = decodedAttrs.asJava.get(0).getValue.toString)
          case _ => Unsupported(eventName)
      }
      case None => Unsupported(topic)
    }
  }

  private def decode(logObj: LogObject, eventType: Web3jEventType): List[Type[_]] = {
    val nonIndexedDecoded = FunctionReturnDecoder.decode(logObj.getData, eventType.getNonIndexedParameters).asScala.toList
    val indexedDecoded = (0 until eventType.getIndexedParameters.size())
      .map(x => FunctionReturnDecoder.decodeIndexedValue(logObj.getTopics.get(x + 1), eventType.getIndexedParameters.get(x))).toList

    (indexedDecoded ++ nonIndexedDecoded)
  }
}
