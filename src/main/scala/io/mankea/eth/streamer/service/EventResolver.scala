package io.mankea.eth.streamer.service

import org.web3j.abi.datatypes.generated.{Bytes32, Uint256, Uint8}
import org.web3j.abi.datatypes.{Address, Bool, Event, Type, Utf8String}
import org.web3j.abi.{EventEncoder, FunctionReturnDecoder, TypeReference}
import org.web3j.protocol.core.methods.response.EthLog.LogObject
import org.web3j.utils.Numeric
import zio.{Task, ZIO, ZLayer}

import java.math.BigInteger
import scala.jdk.CollectionConverters.*

import io.mankea.eth.streamer.service.EventTypes
import scala.Conversion

type Web3jEventType = Event

given Conversion[BigInteger, BigInt] = BigInt(_)
given Conversion[BigInteger, Int] = BigInt(_).intValue
given Conversion[Bytes32, Bytes32String] = b => Numeric.toHexString(b.getValue)
given Conversion[Utf8String, String] = _.getValue();
given Conversion[Uint256, BigInt] = _.getValue()
given Conversion[Uint256, Int] = _.getValue().intValueExact()
given Conversion[Uint256, Long] = _.getValue().longValueExact()
given Conversion[Bool, Boolean] = _.getValue()

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

case class CollateralRatioUpdated(entityId: Bytes32String, collateralRatio: BigInt, utilizedCapacity: BigInt) extends TypedEvent
case class CreateUpgrade(id: Bytes32String, who: AddressString) extends TypedEvent
//event DiamondCut(FacetCut[] _diamondCut, address _init, bytes _calldata);
//event DiamondCut(IDiamondCut.FacetCut[] diamondCut, address init, bytes _calldata);
case class DividendDistribution(guid: Bytes32String, from: Bytes32String, to: Bytes32String, dividendTokenId: Bytes32String, amount: BigInt) extends TypedEvent
case class DividendWithdrawn(accountId: Bytes32String, tokenId: Bytes32String, amountOwned: BigInt, dividendTokenId: Bytes32String, dividendAmountWithdrawn: BigInt) extends TypedEvent
case class EntityCreated(entityId: Bytes32String, entityAdmin: Bytes32String) extends TypedEvent
case class EntityUpdated(entityId: Bytes32String) extends TypedEvent
case class FeePaid(fromId: Bytes32String, toId: Bytes32String, tokenId: Bytes32String, amount: BigInt, feeType: Int) extends TypedEvent
//case class FeeScheduleAdded(entityId: Bytes32String, feeType: Int, FeeSchedule feeSchedule) extends TypedEvent
//case class FunctionsLocked(selectors: List[String]) extends TypedEvent
//case class FunctionsUnlocked(selectors: List[String]) extends TypedEvent
case class InitializeDiamond(sender: AddressString) extends TypedEvent
case class InternalTokenBalanceUpdate(ownerId: Bytes32String, tokenId: Bytes32String, newAmount: BigInt, funcName: String, sender: AddressString) extends TypedEvent
case class InternalTokenSupplyUpdate(tokenId: Bytes32String, newTokenSupply: BigInt, funcName: String, sender: AddressString) extends TypedEvent
case class MakerBasisPointsUpdated(tradingCommissionMakerBP: Int) extends TypedEvent
case class MaxDividendDenominationsUpdated(oldMax: Int, newMax: Int) extends TypedEvent
case class ObjectCreated(objectId: Bytes32String, parentId: Bytes32String, hash: Bytes32String) extends TypedEvent
case class ObjectUpdated(objectId: Bytes32String, parentId :Bytes32String, hash: Bytes32String) extends TypedEvent
case class OrderAdded(orderId: Long, maker: Bytes32String, sellToken: Bytes32String, sellAmount: BigInt, sellAmountInitial: BigInt, buyToken: Bytes32String, buyAmount: BigInt, buyAmountInitial: BigInt, state: Int) extends TypedEvent
case class OrderCancelled(orderId: Long, taker: Bytes32String, sellToken: Bytes32String) extends TypedEvent
case class OrderExecuted(orderId: Long, taker: Bytes32String, sellToken: Bytes32String, sellAmount: BigInt, buyToken: Bytes32String, buyAmount: BigInt, state: Int) extends TypedEvent
case class OwnershipTransferred(previousOwner: AddressString, newOwner: AddressString) extends TypedEvent
case class RoleCanAssignUpdated(role: String, group: String) extends TypedEvent
case class RoleGroupUpdated(role: String, group: String, roleInGroup: Boolean) extends TypedEvent
case class RoleUpdated(objectId: Bytes32String, contextId: Bytes32String, roleId: Bytes32String, funcName: String) extends TypedEvent
case class SimplePolicyCancelled(id: Bytes32String) extends TypedEvent
case class SimplePolicyClaimPaid(claimId: Bytes32String, policyId: Bytes32String, insuredId: Bytes32String, amount: BigInt) extends TypedEvent
case class SimplePolicyCreated(id: Bytes32String, entityId: Bytes32String) extends TypedEvent
case class SimplePolicyMatured(id: Bytes32String) extends TypedEvent
case class SimplePolicyPremiumPaid(id: Bytes32String, amount: BigInt) extends TypedEvent
case class SupportedTokenAdded(tokenAddress: AddressString) extends TypedEvent
case class TokenInfoUpdated(objectId: Bytes32String, symbol: String, name: String) extends TypedEvent
case class TokenizationEnabled(objectId: Bytes32String, symbol: String, name: String) extends TypedEvent
case class TokenSaleStarted(entityId: Bytes32String, offerId: Long, tokenSymbol: String, tokenName: String) extends TypedEvent
case class TokenWrapped(id: Bytes32String, wrapper: AddressString) extends TypedEvent
case class Unsupported(topic: String) extends TypedEvent
case class UpgradeExpiration(duration: BigInt) extends TypedEvent
case class UpgradeCancelled(id: Bytes32String, who: AddressString) extends TypedEvent


trait EventResolver {
  def getTypedEvent(obj: LogObject): Task[TypedEvent]
}

case class EventResolverImpl() extends EventResolver {

  private def getName(topic: String): String = Option(EventTypes.getByHash(topic)).map(_.getName).getOrElse(topic)

  def getTypedEvent(obj: LogObject): Task[TypedEvent] = {

    val topic = obj.getTopics.get(0)

    ZIO.attempt {
        getName(topic) match
          case "CollateralRatioUpdated" =>
            CollateralRatioUpdated(
              entityId = NaymsDiamond.getCollateralRatioUpdatedEventFromLog(obj).entityId,
              collateralRatio = NaymsDiamond.getCollateralRatioUpdatedEventFromLog(obj).collateralRatio,
              utilizedCapacity = NaymsDiamond.getCollateralRatioUpdatedEventFromLog(obj).utilizedCapacity
            )
          case "CreateUpgrade" => CreateUpgrade(
            id = NaymsDiamond.getCreateUpgradeEventFromLog(obj).id,
            who = NaymsDiamond.getCreateUpgradeEventFromLog(obj).who.getValue()
          )
          case "DividendDistribution" =>
            DividendDistribution(
              guid = NaymsDiamond.getDividendDistributionEventFromLog(obj).guid,
              from = NaymsDiamond.getDividendDistributionEventFromLog(obj).from,
              to = NaymsDiamond.getDividendDistributionEventFromLog(obj).to,
              dividendTokenId = NaymsDiamond.getDividendDistributionEventFromLog(obj).dividendTokenId,
              amount = NaymsDiamond.getDividendDistributionEventFromLog(obj).amount
            )
          case "DividendWithdrawn" => DividendWithdrawn(
            accountId = NaymsDiamond.getDividendWithdrawnEventFromLog(obj).accountId,
            tokenId = NaymsDiamond.getDividendWithdrawnEventFromLog(obj).tokenId,
            amountOwned = NaymsDiamond.getDividendWithdrawnEventFromLog(obj).amountOwned,
            dividendTokenId = NaymsDiamond.getDividendWithdrawnEventFromLog(obj).dividendTokenId,
            dividendAmountWithdrawn = NaymsDiamond.getDividendWithdrawnEventFromLog(obj).dividendAmountWithdrawn
          )
          case "TokenSaleStarted" =>
            TokenSaleStarted(
              entityId = NaymsDiamond.getTokenSaleStartedEventFromLog(obj).entityId,
              offerId = NaymsDiamond.getTokenSaleStartedEventFromLog(obj).offerId,
              tokenSymbol = NaymsDiamond.getTokenSaleStartedEventFromLog(obj).tokenSymbol,
              tokenName = NaymsDiamond.getTokenSaleStartedEventFromLog(obj).tokenName
            )
          case "TokenWrapped" => TokenWrapped(
            id = NaymsDiamond.getTokenWrappedEventFromLog(obj).entityId,
            wrapper = NaymsDiamond.getTokenWrappedEventFromLog(obj).tokenWrapper.getValue()
          )
          case "SimplePolicyCreated" =>
            SimplePolicyCreated(
              id = NaymsDiamond.getSimplePolicyCreatedEventFromLog(obj).id,
              entityId = NaymsDiamond.getSimplePolicyCreatedEventFromLog(obj).entityId
            )
          case "SimplePolicyPremiumPaid" =>
            SimplePolicyPremiumPaid(
              id = NaymsDiamond.getSimplePolicyPremiumPaidEventFromLog(obj).id,
              amount = NaymsDiamond.getSimplePolicyPremiumPaidEventFromLog(obj).amount
            )
          case "SimplePolicyClaimPaid" =>
            SimplePolicyClaimPaid(
              claimId = NaymsDiamond.getSimplePolicyClaimPaidEventFromLog(obj)._claimId,
              policyId = NaymsDiamond.getSimplePolicyClaimPaidEventFromLog(obj).policyId,
              insuredId = NaymsDiamond.getSimplePolicyClaimPaidEventFromLog(obj).insuredId,
              amount = NaymsDiamond.getSimplePolicyClaimPaidEventFromLog(obj).amount
            )
          case "SimplePolicyMatured" =>
            SimplePolicyMatured(
              id = NaymsDiamond.getSimplePolicyMaturedEventFromLog(obj).id
            )
          case "SimplePolicyCancelled" =>
            SimplePolicyCancelled(
              id = NaymsDiamond.getSimplePolicyCancelledEventFromLog(obj).id
            )
          case "EntityCreated" =>
            EntityCreated(
              entityId = NaymsDiamond.getEntityCreatedEventFromLog(obj).entityId,
              entityAdmin = NaymsDiamond.getEntityCreatedEventFromLog(obj).entityAdmin
            )
          case "EntityUpdated" =>
            EntityUpdated(
              entityId = NaymsDiamond.getEntityUpdatedEventFromLog(obj).entityId
            )
          case "FeePaid" => FeePaid(
            fromId = NaymsDiamond.getFeePaidEventFromLog(obj).fromId,
            toId = NaymsDiamond.getFeePaidEventFromLog(obj).toId,
            tokenId = NaymsDiamond.getFeePaidEventFromLog(obj).tokenId,
            amount = NaymsDiamond.getFeePaidEventFromLog(obj).amount,
            feeType = NaymsDiamond.getFeePaidEventFromLog(obj).feeType,
          )
          case "RoleUpdated" =>
            RoleUpdated(
              objectId = NaymsDiamond.getRoleUpdatedEventFromLog(obj).objectId,
              contextId = NaymsDiamond.getRoleUpdatedEventFromLog(obj).contextId,
              roleId = NaymsDiamond.getRoleUpdatedEventFromLog(obj).assignedRoleId,
              funcName = NaymsDiamond.getRoleUpdatedEventFromLog(obj).functionName
            )
          case "RoleGroupUpdated" =>
            RoleGroupUpdated(
              role = NaymsDiamond.getRoleGroupUpdatedEventFromLog(obj).role,
              group = NaymsDiamond.getRoleGroupUpdatedEventFromLog(obj).group,
              roleInGroup = NaymsDiamond.getRoleGroupUpdatedEventFromLog(obj).roleInGroup
            )
          case "RoleCanAssignUpdated" =>
            RoleCanAssignUpdated(
              role = NaymsDiamond.getRoleCanAssignUpdatedEventFromLog(obj).role,
              group = NaymsDiamond.getRoleCanAssignUpdatedEventFromLog(obj).group
            )
          case "SupportedTokenAdded" =>
            SupportedTokenAdded(tokenAddress = NaymsDiamond.getSupportedTokenAddedEventFromLog(obj).tokenAddress.getValue)
          case "TokenInfoUpdated" => TokenInfoUpdated(
            objectId = NaymsDiamond.getTokenInfoUpdatedEventFromLog(obj).objectId,
            symbol = NaymsDiamond.getTokenInfoUpdatedEventFromLog(obj).symbol,
            name = NaymsDiamond.getTokenInfoUpdatedEventFromLog(obj).name
          )
          case "TokenizationEnabled" => TokenizationEnabled(
            objectId = NaymsDiamond.getTokenInfoUpdatedEventFromLog(obj).objectId,
            symbol = NaymsDiamond.getTokenInfoUpdatedEventFromLog(obj).symbol,
            name = NaymsDiamond.getTokenInfoUpdatedEventFromLog(obj).name
          )
          case "OrderAdded" =>
            OrderAdded(
              orderId = NaymsDiamond.getOrderAddedEventFromLog(obj).orderId,
              maker = NaymsDiamond.getOrderAddedEventFromLog(obj).maker,
              sellToken = NaymsDiamond.getOrderAddedEventFromLog(obj).sellToken,
              sellAmount = NaymsDiamond.getOrderAddedEventFromLog(obj).sellAmount,
              sellAmountInitial = NaymsDiamond.getOrderAddedEventFromLog(obj).sellAmountInitial,
              buyToken = NaymsDiamond.getOrderAddedEventFromLog(obj).buyToken,
              buyAmount = NaymsDiamond.getOrderAddedEventFromLog(obj).buyAmount,
              buyAmountInitial = NaymsDiamond.getOrderAddedEventFromLog(obj).buyAmountInitial,
              state = NaymsDiamond.getOrderAddedEventFromLog(obj).state
            )
          case "OrderExecuted" =>
            OrderExecuted(
              orderId = NaymsDiamond.getOrderExecutedEventFromLog(obj).orderId,
              taker = NaymsDiamond.getOrderExecutedEventFromLog(obj).taker,
              sellToken = NaymsDiamond.getOrderExecutedEventFromLog(obj).sellToken,
              sellAmount = NaymsDiamond.getOrderExecutedEventFromLog(obj).sellAmount,
              buyToken = NaymsDiamond.getOrderExecutedEventFromLog(obj).buyToken,
              buyAmount = NaymsDiamond.getOrderExecutedEventFromLog(obj).buyAmount,
              state = NaymsDiamond.getOrderExecutedEventFromLog(obj).state
            )
          case "OrderCancelled" =>
            OrderCancelled(
              orderId = NaymsDiamond.getOrderExecutedEventFromLog(obj).orderId,
              taker = NaymsDiamond.getOrderExecutedEventFromLog(obj).taker,
              sellToken = NaymsDiamond.getOrderExecutedEventFromLog(obj).sellToken
            )
          case "InternalTokenBalanceUpdate" =>
            InternalTokenBalanceUpdate(
              ownerId = NaymsDiamond.getInternalTokenBalanceUpdateEventFromLog(obj).ownerId,
              tokenId = NaymsDiamond.getInternalTokenBalanceUpdateEventFromLog(obj).tokenId,
              newAmount = NaymsDiamond.getInternalTokenBalanceUpdateEventFromLog(obj).newAmountOwned,
              funcName = NaymsDiamond.getInternalTokenBalanceUpdateEventFromLog(obj).functionName,
              sender = NaymsDiamond.getInternalTokenBalanceUpdateEventFromLog(obj).msgSender.getValue()
            )
          case "InternalTokenSupplyUpdate" =>
            InternalTokenSupplyUpdate(
              tokenId = NaymsDiamond.getInternalTokenSupplyUpdateEventFromLog(obj).tokenId,
              newTokenSupply = NaymsDiamond.getInternalTokenSupplyUpdateEventFromLog(obj).newTokenSupply,
              funcName = NaymsDiamond.getInternalTokenSupplyUpdateEventFromLog(obj).functionName,
              sender = NaymsDiamond.getInternalTokenSupplyUpdateEventFromLog(obj).msgSender.getValue()
            )
          case "MakerBasisPointsUpdated" => MakerBasisPointsUpdated(
            tradingCommissionMakerBP = NaymsDiamond.getMakerBasisPointsUpdatedEventFromLog(obj).tradingCommissionMakerBP.getValue()
          )
          case "MaxDividendDenominationsUpdated" => MaxDividendDenominationsUpdated(
            oldMax = NaymsDiamond.getMaxDividendDenominationsUpdatedEventFromLog(obj).oldMax.getValue(),
            newMax = NaymsDiamond.getMaxDividendDenominationsUpdatedEventFromLog(obj).newMax.getValue()
          )
          case "ObjectCreated" =>
            ObjectCreated(
              objectId = NaymsDiamond.getObjectCreatedEventFromLog(obj).objectId,
              parentId = NaymsDiamond.getObjectCreatedEventFromLog(obj).parentId,
              hash = NaymsDiamond.getObjectCreatedEventFromLog(obj).dataHash
            )
          case "ObjectUpdated" => ObjectUpdated(
            objectId = NaymsDiamond.getObjectUpdatedEventFromLog(obj).objectId,
            parentId = NaymsDiamond.getObjectUpdatedEventFromLog(obj).parentId,
            hash = NaymsDiamond.getObjectUpdatedEventFromLog(obj).dataHash
          )
          case "OwnershipTransferred" =>
            OwnershipTransferred(
              previousOwner = NaymsDiamond.getOwnershipTransferredEventFromLog(obj).previousOwner.getValue(),
              newOwner = NaymsDiamond.getOwnershipTransferredEventFromLog(obj).newOwner.getValue()
            )
          case "InitializeDiamond" =>
            InitializeDiamond(sender = NaymsDiamond.getInitializeDiamondEventFromLog(obj).sender.getValue())
          case "UpgradeExpiration" => UpgradeExpiration(
            duration =  NaymsDiamond.getUpdateUpgradeExpirationEventFromLog(obj).duration
          )
          case "UpgradeCancelled" => UpgradeCancelled(
            id = NaymsDiamond.getUpgradeCancelledEventFromLog(obj).id,
            who = NaymsDiamond.getUpgradeCancelledEventFromLog(obj).who.getValue()
          )
          case _ => Unsupported(getName(topic))
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
