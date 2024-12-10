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
import org.web3j.abi.{EventEncoder}
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

  def getTypedEvent(obj: LogObject): Task[TypedEvent] = {

    val topic = obj.getTopics.get(0)

    ZIO.attempt {
      getName(topic) match
        case "CollateralRatioUpdated" =>
          val event = NaymsDiamond.getCollateralRatioUpdatedEventFromLog(obj)
          CollateralRatioUpdated(
            entityId = event.entityId,
            collateralRatio = event.collateralRatio,
            utilizedCapacity = event.utilizedCapacity
          )
        case "CreateUpgrade" =>
          val event = NaymsDiamond.getCreateUpgradeEventFromLog(obj)
          CreateUpgrade(
            id = event.id,
            who = event.who
          )
        case "DividendDistribution" =>
          val event = NaymsDiamond.getDividendDistributionEventFromLog(obj)
          DividendDistribution(
            guid = event .guid,
            from = event .from,
            to = event .to,
            dividendTokenId = event .dividendTokenId,
            amount = event .amount
          )
        case "DividendWithdrawn" =>
          val event = NaymsDiamond.getDividendWithdrawnEventFromLog(obj)
          DividendWithdrawn(
            accountId = event.accountId,
            tokenId = event.tokenId,
            amountOwned = event.amountOwned,
            dividendTokenId = event.dividendTokenId,
            dividendAmountWithdrawn = event.dividendAmountWithdrawn
          )
        case "TokenSaleStarted" =>
          TokenSaleStarted(
            entityId = NaymsDiamond.getTokenSaleStartedEventFromLog(obj).entityId,
            offerId = NaymsDiamond.getTokenSaleStartedEventFromLog(obj).offerId,
            tokenSymbol = NaymsDiamond.getTokenSaleStartedEventFromLog(obj).tokenSymbol,
            tokenName = NaymsDiamond.getTokenSaleStartedEventFromLog(obj).tokenName
          )
        case "TokenWrapped" =>
          TokenWrapped(
            id = NaymsDiamond.getTokenWrappedEventFromLog(obj).entityId,
            wrapper = NaymsDiamond.getTokenWrappedEventFromLog(obj).tokenWrapper // TODO: rename
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
            claimId = NaymsDiamond.getSimplePolicyClaimPaidEventFromLog(obj).claimId,
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
        case "ExternalDeposit" =>
          ExternalDeposit(
            receiverId = NaymsDiamond.getExternalDepositEventFromLog(obj).receiverId,
            externalTokenAddress = NaymsDiamond.getExternalDepositEventFromLog(obj).externalTokenAddress,
            amount = NaymsDiamond.getExternalDepositEventFromLog(obj).amount
          )
        case "ExternalWithdraw" =>
          ExternalWithdraw(
            entityId = NaymsDiamond.getExternalWithdrawEventFromLog(obj).entityId,
            receiver = NaymsDiamond.getExternalWithdrawEventFromLog(obj).receiver,
            externalTokenAddress = NaymsDiamond.getExternalWithdrawEventFromLog(obj).externalTokenAddress,
            amount = NaymsDiamond.getExternalWithdrawEventFromLog(obj).amount
          )
        case "FeePaid" =>
          FeePaid(
            fromId = NaymsDiamond.getFeePaidEventFromLog(obj).fromId,
            toId = NaymsDiamond.getFeePaidEventFromLog(obj).toId,
            tokenId = NaymsDiamond.getFeePaidEventFromLog(obj).tokenId,
            amount = NaymsDiamond.getFeePaidEventFromLog(obj).amount,
            feeType = NaymsDiamond.getFeePaidEventFromLog(obj).feeType
          )
        case "FeeScheduleRemoved" =>
          FeeScheduleRemoved(
            entityId = NaymsDiamond.getFeeScheduleRemovedEventFromLog(obj).entityId,
            feeType = NaymsDiamond.getFeeScheduleRemovedEventFromLog(obj).feeType
          )
        case "FeeScheduleAdded" =>
          val event = NaymsDiamond.getFeeScheduleAddedEventFromLog(obj)
          val feeReceivers = for {
              r <- event.feeSchedule.receiver.getValue.asScala
              bp <- event.feeSchedule.basisPoints.getValue.asScala
            } yield FeeReceiver(r, bp)

          FeeScheduleAdded(
            entityId = event.entityId,
            feeType = event.feeType,
            feeReceivers = feeReceivers.toList
          )
        case "RoleUpdated" =>
          RoleUpdated(
            objectId = NaymsDiamond.getRoleUpdatedEventFromLog(obj).objectId,
            contextId = NaymsDiamond.getRoleUpdatedEventFromLog(obj).contextId,
            roleId = NaymsDiamond.getRoleUpdatedEventFromLog(obj).assignedRoleId, // TODO: rename
            funcName = NaymsDiamond.getRoleUpdatedEventFromLog(obj).functionName // TODO: rename
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
        case "SelfOnboardingCompleted" =>
          SelfOnboardingCompleted(
            userAddress = NaymsDiamond.getSelfOnboardingCompletedEventFromLog(obj).userAddress
          )
        case "SupportedTokenAdded" =>
          SupportedTokenAdded(
            tokenAddress = NaymsDiamond.getSupportedTokenAddedEventFromLog(obj).tokenAddress
          )
        case "TokenInfoUpdated" =>
          TokenInfoUpdated(
            objectId = NaymsDiamond.getTokenInfoUpdatedEventFromLog(obj).objectId,
            symbol = NaymsDiamond.getTokenInfoUpdatedEventFromLog(obj).symbol,
            name = NaymsDiamond.getTokenInfoUpdatedEventFromLog(obj).name
          )
        case "TokenizationEnabled" =>
          TokenizationEnabled(
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
        case "OrderMatched" =>
          OrderMatched(
            orderId = NaymsDiamond.getOrderMatchedEventFromLog(obj).orderId,
            matchedWithId = NaymsDiamond.getOrderMatchedEventFromLog(obj).matchedWithId,
            sellAmountMatched = NaymsDiamond.getOrderMatchedEventFromLog(obj).sellAmountMatched,
            buyAmountMatched = NaymsDiamond.getOrderMatchedEventFromLog(obj).buyAmountMatched
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
            newAmount = NaymsDiamond.getInternalTokenBalanceUpdateEventFromLog(obj).newAmountOwned, // TODO: rename
            funcName = NaymsDiamond.getInternalTokenBalanceUpdateEventFromLog(obj).functionName, // TODO: rename
            sender = NaymsDiamond.getInternalTokenBalanceUpdateEventFromLog(obj).msgSender // TODO: rename
          )
        case "InternalTokenSupplyUpdate" =>
          InternalTokenSupplyUpdate(
            tokenId = NaymsDiamond.getInternalTokenSupplyUpdateEventFromLog(obj).tokenId,
            newTokenSupply = NaymsDiamond.getInternalTokenSupplyUpdateEventFromLog(obj).newTokenSupply,
            funcName = NaymsDiamond.getInternalTokenSupplyUpdateEventFromLog(obj).functionName, // TODO: rename
            sender = NaymsDiamond.getInternalTokenSupplyUpdateEventFromLog(obj).msgSender
          )
        case "MakerBasisPointsUpdated" =>
          MakerBasisPointsUpdated(
            tradingCommissionMakerBP = NaymsDiamond.getMakerBasisPointsUpdatedEventFromLog(obj).tradingCommissionMakerBP
          )
        case "MaxDividendDenominationsUpdated" =>
          MaxDividendDenominationsUpdated(
            oldMax = NaymsDiamond.getMaxDividendDenominationsUpdatedEventFromLog(obj).oldMax,
            newMax = NaymsDiamond.getMaxDividendDenominationsUpdatedEventFromLog(obj).newMax
          )
        case "MinimumSellUpdated" =>
          MinimumSellUpdated(
            objectId = NaymsDiamond.getMinimumSellUpdatedEventFromLog(obj).objectId,
            minimumSell = NaymsDiamond.getMinimumSellUpdatedEventFromLog(obj).minimumSell
          )
        case "ObjectCreated" =>
          ObjectCreated(
            objectId = NaymsDiamond.getObjectCreatedEventFromLog(obj).objectId,
            parentId = NaymsDiamond.getObjectCreatedEventFromLog(obj).parentId,
            hash = NaymsDiamond.getObjectCreatedEventFromLog(obj).dataHash // TODO: rename
          )
        case "ObjectUpdated" =>
          ObjectUpdated(
            objectId = NaymsDiamond.getObjectUpdatedEventFromLog(obj).objectId,
            parentId = NaymsDiamond.getObjectUpdatedEventFromLog(obj).parentId,
            hash = NaymsDiamond.getObjectUpdatedEventFromLog(obj).dataHash // TODO: rename
          )
        case "OwnershipTransferred" =>
          OwnershipTransferred(
            previousOwner = NaymsDiamond.getOwnershipTransferredEventFromLog(obj).previousOwner,
            newOwner = NaymsDiamond.getOwnershipTransferredEventFromLog(obj).newOwner
          )
        case "InitializeDiamond" =>
          InitializeDiamond(
            sender = NaymsDiamond.getInitializeDiamondEventFromLog(obj).sender
          )
        case "UpgradeExpiration" =>
          UpgradeExpiration(
            duration = NaymsDiamond.getUpdateUpgradeExpirationEventFromLog(obj).duration
          )
        case "UpgradeCancelled" =>
          UpgradeCancelled(
            id = NaymsDiamond.getUpgradeCancelledEventFromLog(obj).id,
            who = NaymsDiamond.getUpgradeCancelledEventFromLog(obj).who
          )
        case "TokenRewardCollected" =>
          TokenRewardCollected(
            stakerId = NaymsDiamond.getTokenRewardCollectedEventFromLog(obj).stakerId,
            entityId = NaymsDiamond.getTokenRewardCollectedEventFromLog(obj).entityId,
            tokenId = NaymsDiamond.getTokenRewardCollectedEventFromLog(obj).tokenId,
            interval = NaymsDiamond.getTokenRewardCollectedEventFromLog(obj).interval,
            rewardCurrency = NaymsDiamond.getTokenRewardCollectedEventFromLog(obj).rewardCurrency,
            rewardAmount = NaymsDiamond.getTokenRewardCollectedEventFromLog(obj).rewardAmount
          )
        case "TokenRewardPaid" =>
          TokenRewardPaid(
            guid = NaymsDiamond.getTokenRewardPaidEventFromLog(obj).guid,
            entityId = NaymsDiamond.getTokenRewardPaidEventFromLog(obj).entityId,
            tokenId = NaymsDiamond.getTokenRewardPaidEventFromLog(obj).tokenId,
            rewardTokenId = NaymsDiamond.getTokenRewardPaidEventFromLog(obj).rewardTokenId,
            rewardAmount = NaymsDiamond.getTokenRewardPaidEventFromLog(obj).rewardAmount
          )
        case "TokenStaked" =>
          TokenStaked(
            stakerId = NaymsDiamond.getTokenStakedEventFromLog(obj).stakerId,
            entityId = NaymsDiamond.getTokenStakedEventFromLog(obj).entityId,
            tokenId = NaymsDiamond.getTokenStakedEventFromLog(obj).tokenId,
            amount = NaymsDiamond.getTokenStakedEventFromLog(obj).amount
          )
        case "TokenStakingStarted" =>
          TokenStakingStarted(
            entityId = NaymsDiamond.getTokenStakingStartedEventFromLog(obj).entityId,
            tokenId = NaymsDiamond.getTokenStakingStartedEventFromLog(obj).tokenId,
            initDate = NaymsDiamond.getTokenStakingStartedEventFromLog(obj).initDate,
            a = NaymsDiamond.getTokenStakingStartedEventFromLog(obj).a,
            r = NaymsDiamond.getTokenStakingStartedEventFromLog(obj).r,
            divider = NaymsDiamond.getTokenStakingStartedEventFromLog(obj).divider,
            interval = NaymsDiamond.getTokenStakingStartedEventFromLog(obj).interval
          )
        case "TokenUnstaked" =>
          TokenUnstaked(
            stakerId = NaymsDiamond.getTokenUnstakedEventFromLog(obj).stakerId,
            entityId = NaymsDiamond.getTokenUnstakedEventFromLog(obj).entityId,
            tokenId = NaymsDiamond.getTokenUnstakedEventFromLog(obj).tokenId,
            amount = NaymsDiamond.getTokenUnstakedEventFromLog(obj).amount
          )
        case _ => Unsupported(getName(topic))
    }
  }
}

object EventResolver {

  def getTypedEvent(obj: LogObject): ZIO[EventResolver, Throwable, TypedEvent] =
    ZIO.serviceWithZIO[EventResolver](_.getTypedEvent(obj))

  val live: ZLayer[Any, Nothing, EventResolver] =
    ZLayer.succeed(new EventResolverImpl)

}
