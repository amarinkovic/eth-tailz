package io.mankea.eth.streamer.service

import io.mankea.eth.streamer.config.AppConfig
import org.web3j.abi.datatypes.generated.{Bytes32, Uint256}
import org.web3j.abi.datatypes.{Address, Event, Utf8String}
import org.web3j.abi.{EventEncoder, TypeReference}
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

import java.math.BigInteger
import scala.jdk.CollectionConverters.*

class EventResolver {

  // event InitializeDiamond(address sender)
  // event RoleCanAssignUpdated(string role, string group)
  // event OwnershipTransferred(address indexed previousOwner, address indexed newOwner)

  private val events: Map[String, Event] = List(
    new Event("InitializeDiamond", List(
      new TypeReference[Address](false) {}
    ).asJava),

    new Event("RoleCanAssignUpdated", List(
      new TypeReference[Utf8String](false) {},
      new TypeReference[Utf8String](false) {}
    ).asJava),

    new Event("OwnershipTransferred", List(
      new TypeReference[Address](true) {},
      new TypeReference[Address](true) {}
    ).asJava)
  ).map { event =>
    (EventEncoder.encode(event), event)
  }.toMap

  def getName(topic: String): String = events.get(topic).map(_.getName).getOrElse(topic)
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