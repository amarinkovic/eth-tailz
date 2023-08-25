package io.mankea.eth.streamer.service

import org.web3j.abi.datatypes.generated.{Bytes32, Uint256}
import org.web3j.abi.datatypes.{Address, Bool, Event, Type, Utf8String}
import org.web3j.abi.{EventEncoder, FunctionReturnDecoder, TypeReference}
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.EthLog.LogObject
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Numeric
import zio.{Task, ZIO, ZLayer}

import java.nio.charset.StandardCharsets
import scala.jdk.CollectionConverters._

type Web3jEventType = Event

opaque type Bytes32String <: String = String
object Bytes32String {
  def apply(value: String) = new Bytes32String(value)
  given Conversion[String, Bytes32String] = s => Bytes32String(s)
  given Conversion[Bytes32String, String] = s => s
}

opaque type AddressString <: String = String
object AddressString {
  def apply(value: String) = new AddressString(value)
  given Conversion[String, AddressString] = s => AddressString(s)
  given Conversion[AddressString, String] = s => s
}

sealed trait TypedEvent

case class InitializeDiamond(sender: AddressString) extends TypedEvent
case class EntityCreated(entityId: Bytes32String, entityAdmin: Bytes32String) extends TypedEvent
case class EntityUpdated(entityId: Bytes32String) extends TypedEvent
case class RoleUpdated(objectId: Bytes32String, contextId: Bytes32String, roleId: Bytes32String, funcName: String) extends TypedEvent
case class RoleGroupUpdated(role: String, group: String, roleInGroup: Boolean) extends TypedEvent
case class RoleCanAssignUpdated(role: String, group: String) extends TypedEvent
case class OrderAdded(orderId: Long, maker: Bytes32String, sellToken: Bytes32String, sellAmount: BigInt, sellAmountInitial: BigInt, buyToken: Bytes32String, buyAmount: BigInt, buyAmountInitial: BigInt, state: Int) extends TypedEvent
case class OrderExecuted(orderId: Long, taker: Bytes32String, sellToken: Bytes32String, sellAmount: BigInt, buyToken: Bytes32String, buyAmount: BigInt, state: Int) extends TypedEvent
case class OrderCancelled(orderId: Long, taker: Bytes32String, sellToken: Bytes32String) extends TypedEvent
case class SimplePolicyCreated(id: Bytes32String, entityId: Bytes32String) extends TypedEvent
case class SimplePolicyPremiumPaid(id: Bytes32String, amount: BigInt) extends TypedEvent
case class SimplePolicyClaimPaid(claimId: Bytes32String, policyId: Bytes32String, insuredId: Bytes32String, amount: BigInt) extends TypedEvent
case class SimplePolicyMatured(id: Bytes32String) extends TypedEvent
case class SimplePolicyCancelled(id: Bytes32String) extends TypedEvent
case class InternalTokenBalanceUpdate(ownerId: Bytes32String, tokenId: Bytes32String, newAmount: BigInt, funcName: String, sender: AddressString) extends TypedEvent
case class InternalTokenSupplyUpdate(tokenId: Bytes32String, newTokenSupply: BigInt, funcName: String, sender: AddressString) extends TypedEvent
case class OwnershipTransferred(previousOwner: AddressString, newOwner: AddressString) extends TypedEvent
case class TokenSaleStarted(entityId: Bytes32String, offerId: Long, tokenSymbol: String, tokenName: String) extends TypedEvent
case class SupportedTokenAdded(tokenAddress: AddressString) extends TypedEvent
case class DividendDistribution(guid: Bytes32String, from: Bytes32String, to: Bytes32String, dividendTokenId: Bytes32String, amount: BigInt) extends TypedEvent
case class Unsupported(topic: String) extends TypedEvent

trait EventResolver {
  def getTypedEvent(obj: LogObject): Task[TypedEvent]
}

case class EventResolverImpl() extends EventResolver {

  private val evenTypes: Map[String, Web3jEventType] = List(
    new Web3jEventType("InitializeDiamond", List(
      new TypeReference[Address](false) {} // sender
    ).asJava),

    new Web3jEventType("EntityCreated", List(
      new TypeReference[Bytes32](true) {}, //  entityId
      new TypeReference[Bytes32](false) {} //  entityAdmin
    ).asJava),

    new Web3jEventType("EntityUpdated", List(
      new TypeReference[Bytes32](true) {} //  entityId
    ).asJava),

    new Web3jEventType("RoleUpdated", List(
      new TypeReference[Bytes32](true) {},    //  objectId
      new TypeReference[Bytes32](false) {},   //  contextId
      new TypeReference[Bytes32](false) {},   //  roleId
      new TypeReference[Utf8String](false) {} //  functionName
    ).asJava),

    new Web3jEventType("RoleGroupUpdated", List(
      new TypeReference[Utf8String](false) {},  //  role
      new TypeReference[Utf8String](false) {},  //  group
      new TypeReference[Bool](false) {}         //  roleInGroup
    ).asJava),

    new Web3jEventType("RoleCanAssignUpdated", List(
      new TypeReference[Utf8String](false) {},  // role
      new TypeReference[Utf8String](false) {}   // group
    ).asJava),

    new Web3jEventType("OrderAdded", List(
      new TypeReference[Uint256](true) {},  //  orderId
      new TypeReference[Bytes32](true) {},  //  maker
      new TypeReference[Bytes32](true) {},  //  sellToken
      new TypeReference[Uint256](false) {}, //  sellAmount
      new TypeReference[Uint256](false) {}, //  sellAmountInitial
      new TypeReference[Bytes32](false) {}, //  buyToken
      new TypeReference[Uint256](false) {}, //  buyAmount
      new TypeReference[Uint256](false) {}, //  buyAmountInitial
      new TypeReference[Uint256](false) {}  //  state
    ).asJava),

    new Web3jEventType("OrderExecuted", List(
      new TypeReference[Uint256](true) {},  //  orderId
      new TypeReference[Bytes32](true) {},  //  taker
      new TypeReference[Bytes32](true) {},  //  sellToken
      new TypeReference[Uint256](false) {}, //  sellAmount
      new TypeReference[Bytes32](false) {}, //  buyToken
      new TypeReference[Uint256](false) {}, //  buyAmount
      new TypeReference[Uint256](false) {}  //  state
    ).asJava),

    new Web3jEventType("OrderCancelled", List(
      new TypeReference[Uint256](true) {}, //  orderId
      new TypeReference[Bytes32](true) {}, //  taker
      new TypeReference[Bytes32](false) {} //  sellToken
    ).asJava),

    new Web3jEventType("SimplePolicyCreated", List(
      new TypeReference[Bytes32](true) {}, //  policyId
      new TypeReference[Bytes32](false) {} //  entityId
    ).asJava),

    new Web3jEventType("SimplePolicyPremiumPaid", List(
      new TypeReference[Bytes32](true) {}, //  policyId
      new TypeReference[Uint256](false) {} //  amount
    ).asJava),

    new Web3jEventType("SimplePolicyClaimPaid", List(
      new TypeReference[Bytes32](true) {}, //  claimId
      new TypeReference[Bytes32](true) {}, //  policyId
      new TypeReference[Bytes32](true) {}, //  insuredId
      new TypeReference[Uint256](false) {} //  amount
    ).asJava),

    new Web3jEventType("SimplePolicyMatured", List(
      new TypeReference[Bytes32](true) {} //  policyId
    ).asJava),

    new Web3jEventType("SimplePolicyCancelled", List(
      new TypeReference[Bytes32](true) {} //  policyId
    ).asJava),

    new Web3jEventType("InternalTokenBalanceUpdate", List(
      new TypeReference[Bytes32](true) {},      //  ownerId
      new TypeReference[Bytes32](false) {},     //  tokenId
      new TypeReference[Uint256](false) {},     //  newAmount
      new TypeReference[Utf8String](false) {},  //  functionName
      new TypeReference[Address](false) {}      //  sender
    ).asJava),

    new Web3jEventType("InternalTokenSupplyUpdate", List(
      new TypeReference[Bytes32](true) {},      //  tokenId
      new TypeReference[Uint256](false) {},     //  newTokenSupply
      new TypeReference[Utf8String](false) {},  //  functionName
      new TypeReference[Address](false) {}      //  sender
    ).asJava),

    new Web3jEventType("TokenSaleStarted", List(
      new TypeReference[Bytes32](true) {},      //  entityId
      new TypeReference[Uint256](false) {},     //  offerId
      new TypeReference[Utf8String](false) {},  //  tokenSymbol
      new TypeReference[Utf8String](false) {}   //  tokenName
    ).asJava),

    new Web3jEventType("SupportedTokenAdded", List(
      new TypeReference[Address](false) {}  //  tokenAddress, current sepolia deploy had this flag set to: false
    ).asJava),

    new Web3jEventType("DividendDistribution", List(
      new TypeReference[Bytes32](true) {},  //  guid
      new TypeReference[Bytes32](false) {}, //  from
      new TypeReference[Bytes32](false) {}, //  to
      new TypeReference[Bytes32](false) {}, //  dividendTokenId
      new TypeReference[Uint256](false) {}  //  amount
    ).asJava),

    new Web3jEventType("OwnershipTransferred", List(
      new TypeReference[Address](true) {},  //  previous owner
      new TypeReference[Address](true) {}   //  new owner
    ).asJava),

    new Web3jEventType("InitializeDiamond", List(
      new TypeReference[Address](false) {} // sender
    ).asJava)

  ).map { event =>
    (EventEncoder.encode(event), event)
  }.toMap

  private def getName(topic: String): String = evenTypes.get(topic).map(_.getName).getOrElse(topic)

  def getTypedEvent(obj: LogObject): Task[TypedEvent] = {
    val topic = obj.getTopics.get(0)
    val eventName = getName(topic)

    ZIO.attempt {
      evenTypes.get(topic).map(t => decode(obj, t)) match {
        case Some(decodedAttrs) =>
          eventName match
            case "DividendDistribution" =>
              DividendDistribution(
                guid = Numeric.toHexString(decodedAttrs.asJava.get(0).asInstanceOf[Bytes32].getValue),
                from = Numeric.toHexString(decodedAttrs.asJava.get(1).asInstanceOf[Bytes32].getValue),
                to = Numeric.toHexString(decodedAttrs.asJava.get(2).asInstanceOf[Bytes32].getValue),
                dividendTokenId = Numeric.toHexString(decodedAttrs.asJava.get(3).asInstanceOf[Bytes32].getValue),
                amount = BigInt(decodedAttrs.asJava.get(4).asInstanceOf[Uint256].getValue)
              )
            case "TokenSaleStarted" =>
              TokenSaleStarted(
                entityId = Numeric.toHexString(decodedAttrs.asJava.get(0).asInstanceOf[Bytes32].getValue),
                offerId = decodedAttrs.asJava.get(1).asInstanceOf[Uint256].getValue.longValueExact,
                tokenSymbol = decodedAttrs.asJava.get(2).asInstanceOf[Utf8String].getValue,
                tokenName = decodedAttrs.asJava.get(3).asInstanceOf[Utf8String].getValue
              )
            case "SimplePolicyCreated" =>
              SimplePolicyCreated(
                id = Numeric.toHexString(decodedAttrs.asJava.get(0).asInstanceOf[Bytes32].getValue),
                entityId = Numeric.toHexString(decodedAttrs.asJava.get(1).asInstanceOf[Bytes32].getValue)
              )
            case "SimplePolicyPremiumPaid" =>
              SimplePolicyPremiumPaid(
                id = Numeric.toHexString(decodedAttrs.asJava.get(0).asInstanceOf[Bytes32].getValue),
                amount = BigInt(decodedAttrs.asJava.get(1).asInstanceOf[Uint256].getValue)
              )
            case "SimplePolicyClaimPaid" =>
              SimplePolicyClaimPaid(
                claimId = Numeric.toHexString(decodedAttrs.asJava.get(0).asInstanceOf[Bytes32].getValue),
                policyId = Numeric.toHexString(decodedAttrs.asJava.get(1).asInstanceOf[Bytes32].getValue),
                insuredId = Numeric.toHexString(decodedAttrs.asJava.get(2).asInstanceOf[Bytes32].getValue),
                amount = BigInt(decodedAttrs.asJava.get(3).asInstanceOf[Uint256].getValue)
              )
            case "SimplePolicyMatured" =>
              SimplePolicyMatured(
                id = Numeric.toHexString(decodedAttrs.asJava.get(0).asInstanceOf[Bytes32].getValue)
              )
            case "SimplePolicyCancelled" =>
              SimplePolicyCancelled(
                id = Numeric.toHexString(decodedAttrs.asJava.get(0).asInstanceOf[Bytes32].getValue)
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
            case "RoleGroupUpdated" =>
              RoleGroupUpdated(
                role = decodedAttrs.asJava.get(0).asInstanceOf[Utf8String].getValue,
                group = decodedAttrs.asJava.get(1).asInstanceOf[Utf8String].getValue,
                roleInGroup = decodedAttrs.asJava.get(2).asInstanceOf[Bool].getValue
              )
            case "RoleCanAssignUpdated" =>
              RoleCanAssignUpdated(
                role = decodedAttrs.asJava.get(0).getValue.toString,
                group = decodedAttrs.asJava.get(1).getValue.toString
              )
            case "SupportedTokenAdded" =>
              SupportedTokenAdded(tokenAddress = decodedAttrs.asJava.get(0).getValue.toString)
            case "OrderAdded" =>
              OrderAdded(
                orderId = decodedAttrs.asJava.get(0).asInstanceOf[Uint256].getValue.longValueExact,
                maker = Numeric.toHexString(decodedAttrs.asJava.get(1).asInstanceOf[Bytes32].getValue),
                sellToken = Numeric.toHexString(decodedAttrs.asJava.get(2).asInstanceOf[Bytes32].getValue),
                sellAmount = BigInt(decodedAttrs.asJava.get(3).asInstanceOf[Uint256].getValue),
                sellAmountInitial = BigInt(decodedAttrs.asJava.get(4).asInstanceOf[Uint256].getValue),
                buyToken = Numeric.toHexString(decodedAttrs.asJava.get(5).asInstanceOf[Bytes32].getValue),
                buyAmount = BigInt(decodedAttrs.asJava.get(6).asInstanceOf[Uint256].getValue),
                buyAmountInitial = BigInt(decodedAttrs.asJava.get(7).asInstanceOf[Uint256].getValue),
                state = decodedAttrs.asJava.get(8).asInstanceOf[Uint256].getValue.longValueExact.toInt
              )
            case "OrderExecuted" =>
              OrderExecuted(
                orderId = decodedAttrs.asJava.get(0).asInstanceOf[Uint256].getValue.longValueExact,
                taker = Numeric.toHexString(decodedAttrs.asJava.get(1).asInstanceOf[Bytes32].getValue),
                sellToken = Numeric.toHexString(decodedAttrs.asJava.get(2).asInstanceOf[Bytes32].getValue),
                sellAmount = BigInt(decodedAttrs.asJava.get(3).asInstanceOf[Uint256].getValue),
                buyToken = Numeric.toHexString(decodedAttrs.asJava.get(4).asInstanceOf[Bytes32].getValue),
                buyAmount = BigInt(decodedAttrs.asJava.get(5).asInstanceOf[Uint256].getValue),
                state = decodedAttrs.asJava.get(6).asInstanceOf[Uint256].getValue.longValueExact.toInt
              )
            case "OrderCancelled" =>
              OrderCancelled(
                orderId = decodedAttrs.asJava.get(0).asInstanceOf[Uint256].getValue.longValueExact,
                taker = Numeric.toHexString(decodedAttrs.asJava.get(1).asInstanceOf[Bytes32].getValue),
                sellToken = Numeric.toHexString(decodedAttrs.asJava.get(2).asInstanceOf[Bytes32].getValue)
              )
            case "OwnershipTransferred" =>
              OwnershipTransferred(
                previousOwner = decodedAttrs.asJava.get(0).getValue.toString,
                newOwner = decodedAttrs.asJava.get(1).getValue.toString
              )
            case "InitializeDiamond" =>
              InitializeDiamond(sender = decodedAttrs.asJava.get(0).getValue.toString)
            case _ => Unsupported(eventName)
        case None => Unsupported(topic)
      }
    }
  }

  private def decode(logObj: LogObject, eventType: Web3jEventType): List[Type[_]] = {
    try {
      val nonIndexedDecoded = FunctionReturnDecoder.decode(logObj.getData, eventType.getNonIndexedParameters).asScala.toList
      val indexedDecoded = (0 until eventType.getIndexedParameters.size())
        .map(x => FunctionReturnDecoder.decodeIndexedValue(logObj.getTopics.get(x + 1), eventType.getIndexedParameters.get(x))).toList

      indexedDecoded ++ nonIndexedDecoded
    } catch {
      case e: IndexOutOfBoundsException =>
        println(s"#${logObj.getBlockNumber} | TX: ${logObj.getTransactionHash} => `'${eventType.getName}` decoding failed due to: ${e.getMessage}`")
        throw e
    }
  }
}

object EventResolver {

  def getTypedEvent(obj: LogObject): ZIO[EventResolver, Throwable, TypedEvent] =
    ZIO.serviceWithZIO[EventResolver](_.getTypedEvent(obj))

  val live: ZLayer[Any, Nothing, EventResolver] =
    ZLayer.succeed(new EventResolverImpl)

}
