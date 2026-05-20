package io.mankea.eth.streamer.service

import io.nayms.contracts.generated.NaymsDiamond
import io.nayms.contracts.generated.NaymsDiamond.{
  COLLATERALRATIOUPDATED_EVENT,
  CREATEUPGRADE_EVENT,
  DIVIDENDDISTRIBUTION_EVENT,
  DIVIDENDWITHDRAWN_EVENT,
  ENTITYCREATED_EVENT,
  ENTITYUPDATED_EVENT,
  EXTERNALDEPOSIT_EVENT,
  EXTERNALWITHDRAW_EVENT,
  FEEPAID_EVENT,
  FEESCHEDULEREMOVED_EVENT,
  INITIALIZEDIAMOND_EVENT,
  INTERNALTOKENBALANCEUPDATE_EVENT,
  INTERNALTOKENSUPPLYUPDATE_EVENT,
  MAKERBASISPOINTSUPDATED_EVENT,
  MAXDIVIDENDDENOMINATIONSUPDATED_EVENT,
  MINIMUMSELLUPDATED_EVENT,
  OBJECTCREATED_EVENT,
  OBJECTUPDATED_EVENT,
  ORDERADDED_EVENT,
  ORDERCANCELLED_EVENT,
  ORDEREXECUTED_EVENT,
  ORDERMATCHED_EVENT,
  OWNERSHIPTRANSFERRED_EVENT,
  ROLECANASSIGNUPDATED_EVENT,
  ROLEGROUPUPDATED_EVENT,
  ROLEUPDATED_EVENT,
  SELFONBOARDINGCOMPLETED_EVENT,
  SIMPLEPOLICYCANCELLED_EVENT,
  SIMPLEPOLICYCLAIMPAID_EVENT,
  SIMPLEPOLICYCREATED_EVENT,
  SIMPLEPOLICYMATURED_EVENT,
  SIMPLEPOLICYPREMIUMPAID_EVENT,
  SUPPORTEDTOKENADDED_EVENT,
  TOKENINFOUPDATED_EVENT,
  TOKENIZATIONENABLED_EVENT,
  TOKENREWARDCOLLECTED_EVENT,
  TOKENREWARDPAID_EVENT,
  TOKENSALESTARTED_EVENT,
  TOKENSTAKED_EVENT,
  TOKENSTAKINGSTARTED_EVENT,
  TOKENUNSTAKED_EVENT,
  UPDATEUPGRADEEXPIRATION_EVENT,
  TOKENWRAPPED_EVENT,
  UPGRADECANCELLED_EVENT
}
import org.web3j.abi.EventEncoder
import org.web3j.abi.datatypes.generated.{Bytes32, Uint256, Uint64, Uint16, Uint8}
import org.web3j.abi.datatypes.{Bool, Event, Utf8String}
import org.web3j.protocol.core.methods.response.EthLog.LogObject
import org.web3j.utils.Numeric
import zio.{Task, ZIO, ZLayer}

import java.math.BigInteger
import scala.Conversion
import scala.jdk.CollectionConverters.*
import java.time.Instant
import org.web3j.abi.datatypes.Address

type Web3jEventType = Event

given Conversion[BigInteger, BigInt] = BigInt(_)
given Conversion[BigInteger, Int] = BigInt(_).intValue
given Conversion[Bytes32, Bytes32String] = b => Numeric.toHexString(b.getValue)
given Conversion[Utf8String, String] = _.getValue;
given Conversion[Uint256, BigInt] = _.getValue
given Conversion[Uint256, Int] = _.getValue.intValueExact
given Conversion[Uint64, Int] = _.getValue.intValueExact
given Conversion[Uint16, Int] = _.getValue.intValueExact
given Conversion[Uint8, Int] = _.getValue.intValueExact
given Conversion[Uint64, Long] = _.getValue.longValueExact
given Conversion[Uint256, Long] = _.getValue.longValueExact
given Conversion[Bool, Boolean] = _.getValue
given Conversion[Uint256, Instant] = b => Instant.ofEpochSecond(b.getValue.longValueExact)

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
  given Conversion[Address, AddressString] = s => AddressString(s.getValue)
}

sealed trait TypedEvent

case class FeeReceiver(receiverId: Bytes32String, basisPoints: Int)

case class CollateralRatioUpdated(entityId: Bytes32String, collateralRatio: BigInt, utilizedCapacity: BigInt) extends TypedEvent
case class CreateUpgrade(id: Bytes32String, who: AddressString) extends TypedEvent
//event DiamondCut(FacetCut[] _diamondCut, address _init, bytes _calldata);
//event DiamondCut(IDiamondCut.FacetCut[] diamondCut, address init, bytes _calldata);
case class DividendDistribution(guid: Bytes32String, from: Bytes32String, to: Bytes32String, dividendTokenId: Bytes32String, amount: BigInt) extends TypedEvent
case class DividendWithdrawn(accountId: Bytes32String, tokenId: Bytes32String, amountOwned: BigInt, dividendTokenId: Bytes32String, dividendAmountWithdrawn: BigInt) extends TypedEvent
case class EntityCreated(entityId: Bytes32String, entityAdmin: Bytes32String) extends TypedEvent
case class EntityUpdated(entityId: Bytes32String) extends TypedEvent
case class ExternalDeposit(receiverId: Bytes32String, externalTokenAddress: AddressString, amount: BigInt) extends TypedEvent
case class ExternalWithdraw(entityId: Bytes32String, receiver: AddressString, externalTokenAddress: AddressString, amount: BigInt) extends TypedEvent
case class FeePaid(fromId: Bytes32String, toId: Bytes32String, tokenId: Bytes32String, amount: BigInt, feeType: Int) extends TypedEvent
case class FeeScheduleRemoved(entityId: Bytes32String, feeType: BigInt) extends TypedEvent
case class FeeScheduleAdded(entityId: Bytes32String, feeType: Int, feeReceivers: List[FeeReceiver]) extends TypedEvent
// case class FunctionsLocked(selectors: List[String]) extends TypedEvent
// case class FunctionsUnlocked(selectors: List[String]) extends TypedEvent
case class InitializeDiamond(sender: AddressString) extends TypedEvent
case class InternalTokenBalanceUpdate(ownerId: Bytes32String, tokenId: Bytes32String, newAmount: BigInt, funcName: String, sender: AddressString) extends TypedEvent
case class InternalTokenSupplyUpdate(tokenId: Bytes32String, newTokenSupply: BigInt, funcName: String, sender: AddressString) extends TypedEvent
case class MakerBasisPointsUpdated(tradingCommissionMakerBP: Int) extends TypedEvent
case class MaxDividendDenominationsUpdated(oldMax: Int, newMax: Int) extends TypedEvent
case class MinimumSellUpdated(objectId: Bytes32String, minimumSell: BigInt) extends TypedEvent
case class ObjectCreated(objectId: Bytes32String, parentId: Bytes32String, hash: Bytes32String) extends TypedEvent
case class ObjectUpdated(objectId: Bytes32String, parentId: Bytes32String, hash: Bytes32String) extends TypedEvent
case class OrderAdded(orderId: Long, maker: Bytes32String, sellToken: Bytes32String, sellAmount: BigInt, sellAmountInitial: BigInt, buyToken: Bytes32String, buyAmount: BigInt, buyAmountInitial: BigInt, state: Int) extends TypedEvent
case class OrderCancelled(orderId: Long, taker: Bytes32String, sellToken: Bytes32String) extends TypedEvent
case class OrderExecuted(orderId: Long, taker: Bytes32String, sellToken: Bytes32String, sellAmount: BigInt, buyToken: Bytes32String, buyAmount: BigInt, state: Int) extends TypedEvent
case class OrderMatched(orderId: Long, matchedWithId: Long, sellAmountMatched: BigInt, buyAmountMatched: BigInt) extends TypedEvent
case class OwnershipTransferred(previousOwner: AddressString, newOwner: AddressString) extends TypedEvent
case class RoleCanAssignUpdated(role: String, group: String) extends TypedEvent
case class RoleGroupUpdated(role: String, group: String, roleInGroup: Boolean) extends TypedEvent
case class RoleUpdated(objectId: Bytes32String, contextId: Bytes32String, roleId: Bytes32String, funcName: String) extends TypedEvent
case class SelfOnboardingCompleted(userAddress: AddressString) extends TypedEvent
case class SimplePolicyCancelled(id: Bytes32String) extends TypedEvent
case class SimplePolicyClaimPaid(claimId: Bytes32String, policyId: Bytes32String, insuredId: Bytes32String, amount: BigInt) extends TypedEvent
case class SimplePolicyCreated(id: Bytes32String, entityId: Bytes32String) extends TypedEvent
case class SimplePolicyMatured(id: Bytes32String) extends TypedEvent
case class SimplePolicyPremiumPaid(id: Bytes32String, amount: BigInt) extends TypedEvent
case class SupportedTokenAdded(tokenAddress: AddressString) extends TypedEvent
case class TokenInfoUpdated(objectId: Bytes32String, symbol: String, name: String) extends TypedEvent
case class TokenizationEnabled(objectId: Bytes32String, symbol: String, name: String) extends TypedEvent
case class TokenRewardCollected(stakerId: Bytes32String, entityId: Bytes32String, tokenId: Bytes32String, interval: Int, rewardCurrency: Bytes32String, rewardAmount: BigInt) extends TypedEvent
case class TokenRewardPaid(guid: Bytes32String, entityId: Bytes32String, tokenId: Bytes32String, rewardTokenId: Bytes32String, rewardAmount: BigInt) extends TypedEvent
case class TokenSaleStarted(entityId: Bytes32String, offerId: Long, tokenSymbol: String, tokenName: String) extends TypedEvent
case class TokenStaked(stakerId: Bytes32String, entityId: Bytes32String, tokenId: Bytes32String, amount: BigInt) extends TypedEvent
case class TokenStakingStarted(entityId: Bytes32String, tokenId: Bytes32String, initDate: Instant, a: Long, r: Long, divider: Long, interval: Int) extends TypedEvent
case class TokenUnstaked(stakerId: Bytes32String, entityId: Bytes32String, tokenId: Bytes32String, amount: BigInt) extends TypedEvent
case class UpdateUpgradeExpiration(duration: BigInt) extends TypedEvent // TODO
case class TokenWrapped(id: Bytes32String, wrapper: AddressString) extends TypedEvent
case class UpgradeExpiration(duration: BigInt) extends TypedEvent
case class UpgradeCancelled(id: Bytes32String, who: AddressString) extends TypedEvent
case class Unsupported(topic: String) extends TypedEvent

trait EventResolver {
  def getTypedEvent(obj: LogObject): Task[TypedEvent]
}

case class EventResolverImpl() extends EventResolver {

  private val eventMap: Map[String, Event] = List(
    COLLATERALRATIOUPDATED_EVENT,
    CREATEUPGRADE_EVENT,
    DIVIDENDDISTRIBUTION_EVENT,
    DIVIDENDWITHDRAWN_EVENT,
    ENTITYCREATED_EVENT,
    ENTITYUPDATED_EVENT,
    EXTERNALDEPOSIT_EVENT,
    EXTERNALWITHDRAW_EVENT,
    FEEPAID_EVENT,
    FEESCHEDULEREMOVED_EVENT,
    INITIALIZEDIAMOND_EVENT,
    INTERNALTOKENBALANCEUPDATE_EVENT,
    INTERNALTOKENSUPPLYUPDATE_EVENT,
    MAKERBASISPOINTSUPDATED_EVENT,
    MAXDIVIDENDDENOMINATIONSUPDATED_EVENT,
    MINIMUMSELLUPDATED_EVENT,
    OBJECTCREATED_EVENT,
    OBJECTUPDATED_EVENT,
    ORDERADDED_EVENT,
    ORDERCANCELLED_EVENT,
    ORDEREXECUTED_EVENT,
    ORDERMATCHED_EVENT,
    OWNERSHIPTRANSFERRED_EVENT,
    ROLECANASSIGNUPDATED_EVENT,
    ROLEGROUPUPDATED_EVENT,
    ROLEUPDATED_EVENT,
    SELFONBOARDINGCOMPLETED_EVENT,
    SIMPLEPOLICYCANCELLED_EVENT,
    SIMPLEPOLICYCLAIMPAID_EVENT,
    SIMPLEPOLICYCREATED_EVENT,
    SIMPLEPOLICYMATURED_EVENT,
    SIMPLEPOLICYPREMIUMPAID_EVENT,
    SUPPORTEDTOKENADDED_EVENT,
    TOKENINFOUPDATED_EVENT,
    TOKENIZATIONENABLED_EVENT,
    TOKENREWARDCOLLECTED_EVENT,
    TOKENREWARDPAID_EVENT,
    TOKENSALESTARTED_EVENT,
    TOKENSTAKED_EVENT,
    TOKENSTAKINGSTARTED_EVENT,
    TOKENUNSTAKED_EVENT,
    UPDATEUPGRADEEXPIRATION_EVENT,
    TOKENWRAPPED_EVENT,
    UPGRADECANCELLED_EVENT
  ).map(v => EventEncoder.encode(v) -> v).toMap

  private def getName(topic: String): String = eventMap.get(topic).map(_.getName).getOrElse(topic)

  import NaymsDiamond.*
  import scala.deriving.Mirror

  private class AutoBuilder[T <: TypedEvent & Product] {
    inline def apply[E](get: LogObject => E)(using m: Mirror.ProductOf[T]): LogObject => TypedEvent =
      obj => AutoMap.from[E, T](get(obj))
  }
  private inline def auto[T <: TypedEvent & Product]: AutoBuilder[T] = new AutoBuilder[T]

  private def custom[E, T <: TypedEvent](get: LogObject => E)(build: E => T): LogObject => TypedEvent =
    obj => build(get(obj))

  private val resolvers: Map[String, LogObject => TypedEvent] = Map(
    "CollateralRatioUpdated"          -> auto[CollateralRatioUpdated](getCollateralRatioUpdatedEventFromLog),
    "CreateUpgrade"                   -> auto[CreateUpgrade](getCreateUpgradeEventFromLog),
    "DividendDistribution"            -> auto[DividendDistribution](getDividendDistributionEventFromLog),
    "DividendWithdrawn"               -> auto[DividendWithdrawn](getDividendWithdrawnEventFromLog),
    "EntityCreated"                   -> auto[EntityCreated](getEntityCreatedEventFromLog),
    "EntityUpdated"                   -> auto[EntityUpdated](getEntityUpdatedEventFromLog),
    "ExternalDeposit"                 -> auto[ExternalDeposit](getExternalDepositEventFromLog),
    "ExternalWithdraw"                -> auto[ExternalWithdraw](getExternalWithdrawEventFromLog),
    "FeePaid"                         -> auto[FeePaid](getFeePaidEventFromLog),
    "FeeScheduleRemoved"              -> auto[FeeScheduleRemoved](getFeeScheduleRemovedEventFromLog),
    "InitializeDiamond"               -> auto[InitializeDiamond](getInitializeDiamondEventFromLog),
    "MakerBasisPointsUpdated"         -> auto[MakerBasisPointsUpdated](getMakerBasisPointsUpdatedEventFromLog),
    "MaxDividendDenominationsUpdated" -> auto[MaxDividendDenominationsUpdated](getMaxDividendDenominationsUpdatedEventFromLog),
    "MinimumSellUpdated"              -> auto[MinimumSellUpdated](getMinimumSellUpdatedEventFromLog),
    "OrderAdded"                      -> auto[OrderAdded](getOrderAddedEventFromLog),
    "OrderCancelled"                  -> auto[OrderCancelled](getOrderCancelledEventFromLog),
    "OrderExecuted"                   -> auto[OrderExecuted](getOrderExecutedEventFromLog),
    "OrderMatched"                    -> auto[OrderMatched](getOrderMatchedEventFromLog),
    "OwnershipTransferred"            -> auto[OwnershipTransferred](getOwnershipTransferredEventFromLog),
    "RoleCanAssignUpdated"            -> auto[RoleCanAssignUpdated](getRoleCanAssignUpdatedEventFromLog),
    "RoleGroupUpdated"                -> auto[RoleGroupUpdated](getRoleGroupUpdatedEventFromLog),
    "SelfOnboardingCompleted"         -> auto[SelfOnboardingCompleted](getSelfOnboardingCompletedEventFromLog),
    "SimplePolicyCancelled"           -> auto[SimplePolicyCancelled](getSimplePolicyCancelledEventFromLog),
    "SimplePolicyClaimPaid"           -> auto[SimplePolicyClaimPaid](getSimplePolicyClaimPaidEventFromLog),
    "SimplePolicyCreated"             -> auto[SimplePolicyCreated](getSimplePolicyCreatedEventFromLog),
    "SimplePolicyMatured"             -> auto[SimplePolicyMatured](getSimplePolicyMaturedEventFromLog),
    "SimplePolicyPremiumPaid"         -> auto[SimplePolicyPremiumPaid](getSimplePolicyPremiumPaidEventFromLog),
    "SupportedTokenAdded"             -> auto[SupportedTokenAdded](getSupportedTokenAddedEventFromLog),
    "TokenInfoUpdated"                -> auto[TokenInfoUpdated](getTokenInfoUpdatedEventFromLog),
    "TokenRewardCollected"            -> auto[TokenRewardCollected](getTokenRewardCollectedEventFromLog),
    "TokenRewardPaid"                 -> auto[TokenRewardPaid](getTokenRewardPaidEventFromLog),
    "TokenSaleStarted"                -> auto[TokenSaleStarted](getTokenSaleStartedEventFromLog),
    "TokenStaked"                     -> auto[TokenStaked](getTokenStakedEventFromLog),
    "TokenStakingStarted"             -> auto[TokenStakingStarted](getTokenStakingStartedEventFromLog),
    "TokenUnstaked"                   -> auto[TokenUnstaked](getTokenUnstakedEventFromLog),
    "UpgradeCancelled"                -> auto[UpgradeCancelled](getUpgradeCancelledEventFromLog),
    "TokenWrapped"                -> custom(getTokenWrappedEventFromLog)(e => TokenWrapped(id = e.entityId, wrapper = e.tokenWrapper)),
    "TokenizationEnabled"         -> custom(getTokenizationEnabledEventFromLog)(e =>TokenizationEnabled(objectId = e.objectId, symbol = e.tokenSymbol, name = e.tokenName)),
    "RoleUpdated"                 -> custom(getRoleUpdatedEventFromLog)(e => RoleUpdated(objectId = e.objectId, contextId = e.contextId, roleId = e.assignedRoleId, funcName = e.functionName)),
    "InternalTokenBalanceUpdate"  -> custom(getInternalTokenBalanceUpdateEventFromLog)(e => InternalTokenBalanceUpdate(ownerId = e.ownerId, tokenId = e.tokenId, newAmount = e.newAmountOwned, funcName = e.functionName, sender = e.msgSender)),
    "InternalTokenSupplyUpdate"   -> custom(getInternalTokenSupplyUpdateEventFromLog)(e => InternalTokenSupplyUpdate(tokenId = e.tokenId, newTokenSupply = e.newTokenSupply, funcName = e.functionName, sender = e.msgSender)),
    "ObjectCreated"               -> custom(getObjectCreatedEventFromLog)(e => ObjectCreated(objectId = e.objectId, parentId = e.parentId, hash = e.dataHash)),
    "ObjectUpdated"               -> custom(getObjectUpdatedEventFromLog)(e => ObjectUpdated(objectId = e.objectId, parentId = e.parentId, hash = e.dataHash)),
    "UpgradeExpiration"           -> custom(getUpdateUpgradeExpirationEventFromLog)(e => UpgradeExpiration(duration = e.duration)),
    "FeeScheduleAdded"            -> custom(getFeeScheduleAddedEventFromLog) { e =>
      val feeReceivers = e.feeSchedule.receiver.getValue.asScala
        .zip(e.feeSchedule.basisPoints.getValue.asScala)
        .map((r, bp) => FeeReceiver(r, bp))
        .toList
      FeeScheduleAdded(entityId = e.entityId, feeType = e.feeType, feeReceivers = feeReceivers)
    }
  )

  def getTypedEvent(obj: LogObject): Task[TypedEvent] = ZIO.attempt {
    val name = getName(obj.getTopics.get(0))
    resolvers.get(name).fold[TypedEvent](Unsupported(name))(_(obj))
  }
}

object EventResolver {

  def getTypedEvent(obj: LogObject): ZIO[EventResolver, Throwable, TypedEvent] =
    ZIO.serviceWithZIO[EventResolver](_.getTypedEvent(obj))

  val live: ZLayer[Any, Nothing, EventResolver] =
    ZLayer.succeed(new EventResolverImpl)

}
