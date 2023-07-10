package io.mankea.eth.streamer.service

import org.web3j.abi.{EventEncoder, TypeReference}
import org.web3j.abi.datatypes.{Address, Event, Utf8String}
import org.web3j.abi.datatypes.generated.{Bytes32, Uint256}

import scala.collection.convert.ImplicitConversionsToJava.*

trait LoggedEvent

case class Transfer(from: String, to: String, amount: BigInt)
case class RoleUpdated(objectId: String, contextId: String, roleId: String, funcName: String)

object EventResolver {

  private val events: Map[String, List[TypeReference[_]]] = Map(
    "Transfer(address,address,uint256)" -> List(
      new TypeReference[Address](true) {},
      new TypeReference[Address](true) {},
      new TypeReference[Uint256](false) {}
    ),
    "RoleUpdated(bytes32,bytes32,string)" -> List(
      new TypeReference[Bytes32](true) {},        //  objectId
      new TypeReference[Bytes32](false) { },   //  contextId
      new TypeReference[Bytes32](false) {},    //  roleId
      new TypeReference[Utf8String](false) {} //  functionName
    )
  ).map {
    case (key, value) => (EventEncoder.buildEventSignature(key), value)
  }

  def get(topic: String): Event = Event(topic, events(topic))

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