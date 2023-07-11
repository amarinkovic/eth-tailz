package io.mankea.eth.streamer.service

import io.mankea.eth.streamer.config.AppConfig
import org.web3j.abi.datatypes.generated.{Bytes32, Uint256}
import org.web3j.abi.datatypes.{Address, Event, Type, Utf8String}
import org.web3j.abi.{EventEncoder, FunctionReturnDecoder, TypeReference}
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.EthLog.LogObject
import org.web3j.protocol.http.HttpService

import java.math.BigInteger
import scala.jdk.CollectionConverters.*

type EventType = Event

trait TypedEvent
case class Unsupported(topic: String) extends TypedEvent
case class RoleUpdated(objectId: String, contextId: String, roleId: String, funcName: String) extends TypedEvent
case class RoleCanAssignUpdated(role: String, group: String) extends TypedEvent
case class OwnershipTransferred(previousOwner: String, newOwner: String) extends TypedEvent

object EventResolver {

  // event InitializeDiamond(address sender)
  // event RoleCanAssignUpdated(string role, string group)
  // event OwnershipTransferred(address indexed previousOwner, address indexed newOwner)

  private val evenTypes: Map[String, EventType] = List(
    new EventType("RoleUpdated", List(
      new TypeReference[Bytes32](true) {},    //  objectId
      new TypeReference[Bytes32](false) {},   //  contextId
      new TypeReference[Bytes32](false) {},   //  roleId
      new TypeReference[Utf8String](false) {} //  functionName
    ).asJava),

    new EventType("RoleCanAssignUpdated", List(
      new TypeReference[Utf8String](false) {},  // role
      new TypeReference[Utf8String](false) {}   // group
    ).asJava),

    new EventType("InitializeDiamond", List(
      new TypeReference[Address](false) {}    // sender
    ).asJava),

    new EventType("OwnershipTransferred", List(
      new TypeReference[Address](true) {},    // previous owner
      new TypeReference[Address](true) {}     // new owner
    ).asJava)

  ).map { event =>
    (EventEncoder.encode(event), event)
  }.toMap

  def getName(topic: String): String = evenTypes.get(topic).map(_.getName).getOrElse(topic)

  private def getType(topic: String): Option[EventType] = evenTypes.get(topic)

  def getTypedEvent(obj: LogObject): TypedEvent = {
    val topic = obj.getTopics.get(0)
    val eventName = getName(topic)

    getType(topic).map(t => decode(obj, t)) match {
      case Some(allDecoded) => {
        eventName match
          case "RoleUpdated" =>
            RoleUpdated(
              objectId = allDecoded.asJava.get(0).getValue.asInstanceOf[Bytes32].getValue.toString,
              contextId = allDecoded.asJava.get(1).getValue.asInstanceOf[Bytes32].getValue.toString,
              roleId = allDecoded.asJava.get(2).getValue.asInstanceOf[Bytes32].getValue.toString,
              funcName = allDecoded.asJava.get(3).getValue.asInstanceOf[Utf8String].getValue
            )
          case "RoleCanAssignUpdated" =>
            RoleCanAssignUpdated(
              role = allDecoded.asJava.get(0).getValue.toString,
              group = allDecoded.asJava.get(1).getValue.toString
            )
          case "OwnershipTransferred" =>
            OwnershipTransferred(
              previousOwner = allDecoded.asJava.get(0).getValue.toString,
              newOwner = allDecoded.asJava.get(1).getValue.toString
            )

          case _ => Unsupported(eventName)
      }
      case None => Unsupported(topic)
    }
  }

  private def decode(logObj: LogObject, eventType: EventType): List[Type[_]] = {
    val nonIndexedDecoded = FunctionReturnDecoder.decode(logObj.getData, eventType.getNonIndexedParameters).asScala.toList
    val indexedDecoded = (0 until eventType.getIndexedParameters.size())
      .map(x => {
        FunctionReturnDecoder.decodeIndexedValue(logObj.getTopics.get(x + 1), eventType.getIndexedParameters.get(x))
      }).toList
    (indexedDecoded ++ nonIndexedDecoded)
  }
}

//  TODO
// "Approval(address,address,uint256)"
// "OwnershipTransferred(address,address)"
// "DiamondCut(IDiamondCut.FacetCut[],address,bytes)"
// "DiamondCut(FacetCut[],address,bytes)"
// "OwnershipTransferred(address,address)"
// "CreateUpgrade(bytes32,address)"
// "UpdateUpgradeExpiration(uint256)"
// "UpgradeCancelled,address)"
// "InitializeDiamond(address,bytes32)"
// "TokenWrapped(bytes32,address)"
// "TokenInfoUpdated(bytes32,string,string)"
// "OrderAdded(uint256,bytes32,bytes32,uint256,uint256,bytes32uint256,uint256,uint256)"
// "OrderExecuted(uint256,bytes32,bytes32,uint256,bytes32,uint256,uint256)"
// "OrderCancelled(uint256,bytes32,bytes32)"
// "InternalTokenBalanceUpdate(bytes32,bytes32,uint256,string,address)"
// "InternalTokenSupplyUpdate(bytes32,uint256,string,address)"
// "DividendDistribution(bytes32,bytes32,bytes32,bytes32,uint256)"
// "EntityCreated(bytes32,bytes32)"
// "EntityUpdated(bytes32)"
// "SimplePolicyCreated,bytes32)"
// "TokenSaleStarted(bytes32,uint256,string,string)"
// "CollateralRatioUpdated(bytes32,uint256,uint256)"
// "TradingCommissionsPaid(bytes32,bytes32,uint256)"
// "PremiumCommissionsPaid(bytes32,bytes32,uint256)"
// "SimplePolicyMatured(bytes32)"
// "(SimplePolicyCancelled(bytes32)"
// "SimplePolicyPremiumPaid(bytes32,uint256)"
// "SimplePolicyClaimPaid(bytes32,bytes32,bytes32,uint256)"
// "MaxDividendDenominationsUpdated(uint8,uint8)"
// "SupportedTokenAdded(address)"
// "RoleGroupUpdated(string,string,bool)"
// "RoleCanAssignUpdated(string,string)"