package io.mankea.eth.streamer.service;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes12;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.10.3.
 */
@SuppressWarnings("rawtypes")
public class NaymsDiamond extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ADDFEESCHEDULE = "addFeeSchedule";

    public static final String FUNC_ADDSUPPORTEDEXTERNALTOKEN = "addSupportedExternalToken";

    public static final String FUNC_ASSIGNROLE = "assignRole";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_CALCULATEPREMIUMFEES = "calculatePremiumFees";

    public static final String FUNC_CALCULATETRADINGFEES = "calculateTradingFees";

    public static final String FUNC_CANASSIGN = "canAssign";

    public static final String FUNC_CANGROUPASSIGNROLE = "canGroupAssignRole";

    public static final String FUNC_CANCELOFFER = "cancelOffer";

    public static final String FUNC_CANCELSIMPLEPOLICY = "cancelSimplePolicy";

    public static final String FUNC_CANCELUPGRADE = "cancelUpgrade";

    public static final String FUNC_CHECKANDUPDATESIMPLEPOLICYSTATE = "checkAndUpdateSimplePolicyState";

    public static final String FUNC_CREATEENTITY = "createEntity";

    public static final String FUNC_CREATESIMPLEPOLICY = "createSimplePolicy";

    public static final String FUNC_CREATEUPGRADE = "createUpgrade";

    public static final String FUNC_DOMAINSEPARATORV4 = "domainSeparatorV4";

    public static final String FUNC_ENABLEENTITYTOKENIZATION = "enableEntityTokenization";

    public static final String FUNC_EXECUTELIMITOFFER = "executeLimitOffer";

    public static final String FUNC_EXTERNALDEPOSIT = "externalDeposit";

    public static final String FUNC_EXTERNALWITHDRAWFROMENTITY = "externalWithdrawFromEntity";

    public static final String FUNC_FACETADDRESS = "facetAddress";

    public static final String FUNC_FACETADDRESSES = "facetAddresses";

    public static final String FUNC_FACETFUNCTIONSELECTORS = "facetFunctionSelectors";

    public static final String FUNC_GETADDRESSFROMEXTERNALTOKENID = "getAddressFromExternalTokenId";

    public static final String FUNC_GETBESTOFFERID = "getBestOfferId";

    public static final String FUNC_GETENTITY = "getEntity";

    public static final String FUNC_GETENTITYINFO = "getEntityInfo";

    public static final String FUNC_GETFEESCHEDULE = "getFeeSchedule";

    public static final String FUNC_GETLASTOFFERID = "getLastOfferId";

    public static final String FUNC_GETLOCKEDBALANCE = "getLockedBalance";

    public static final String FUNC_GETMAKERBP = "getMakerBP";

    public static final String FUNC_GETMAXDIVIDENDDENOMINATIONS = "getMaxDividendDenominations";

    public static final String FUNC_GETOBJECTMETA = "getObjectMeta";

    public static final String FUNC_GETOBJECTTOKENSYMBOL = "getObjectTokenSymbol";

    public static final String FUNC_GETOBJECTTYPE = "getObjectType";

    public static final String FUNC_GETOFFER = "getOffer";

    public static final String FUNC_GETPOLICYCOMMISSIONRECEIVERS = "getPolicyCommissionReceivers";

    public static final String FUNC_GETROLEINCONTEXT = "getRoleInContext";

    public static final String FUNC_GETSIGNINGHASH = "getSigningHash";

    public static final String FUNC_GETSIMPLEPOLICYINFO = "getSimplePolicyInfo";

    public static final String FUNC_GETSUPPORTEDEXTERNALTOKENS = "getSupportedExternalTokens";

    public static final String FUNC_GETSYSTEMID = "getSystemId";

    public static final String FUNC_GETUPGRADE = "getUpgrade";

    public static final String FUNC_GETUPGRADEEXPIRATION = "getUpgradeExpiration";

    public static final String FUNC_GETUSERIDFROMADDRESS = "getUserIdFromAddress";

    public static final String FUNC_GETWITHDRAWABLEDIVIDEND = "getWithdrawableDividend";

    public static final String FUNC_HASGROUPPRIVILEGE = "hasGroupPrivilege";

    public static final String FUNC_HASHTYPEDDATAV4 = "hashTypedDataV4";

    public static final String FUNC_INTERNALBALANCEOF = "internalBalanceOf";

    public static final String FUNC_INTERNALBURN = "internalBurn";

    public static final String FUNC_INTERNALTOKENSUPPLY = "internalTokenSupply";

    public static final String FUNC_INTERNALTRANSFERBYSYSTEMADMIN = "internalTransferBySystemAdmin";

    public static final String FUNC_INTERNALTRANSFERFROMENTITY = "internalTransferFromEntity";

    public static final String FUNC_ISACTIVEOFFER = "isActiveOffer";

    public static final String FUNC_ISDIAMONDINITIALIZED = "isDiamondInitialized";

    public static final String FUNC_ISFUNCTIONLOCKED = "isFunctionLocked";

    public static final String FUNC_ISINGROUP = "isInGroup";

    public static final String FUNC_ISOBJECT = "isObject";

    public static final String FUNC_ISOBJECTTOKENIZABLE = "isObjectTokenizable";

    public static final String FUNC_ISOBJECTTYPE = "isObjectType";

    public static final String FUNC_ISPARENTINGROUP = "isParentInGroup";

    public static final String FUNC_ISROLEINGROUP = "isRoleInGroup";

    public static final String FUNC_ISSUPPORTEDEXTERNALTOKEN = "isSupportedExternalToken";

    public static final String FUNC_ISTOKENWRAPPED = "isTokenWrapped";

    public static final String FUNC_LOCKALLFUNDTRANSFERFUNCTIONS = "lockAllFundTransferFunctions";

    public static final String FUNC_LOCKFUNCTION = "lockFunction";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PAYDIVIDENDFROMENTITY = "payDividendFromEntity";

    public static final String FUNC_PAYSIMPLECLAIM = "paySimpleClaim";

    public static final String FUNC_PAYSIMPLEPREMIUM = "paySimplePremium";

    public static final String FUNC_REMOVEFEESCHEDULE = "removeFeeSchedule";

    public static final String FUNC_REPLACEMAKERBP = "replaceMakerBP";

    public static final String FUNC_SETENTITY = "setEntity";

    public static final String FUNC_SETMAXDIVIDENDDENOMINATIONS = "setMaxDividendDenominations";

    public static final String FUNC_STARTTOKENSALE = "startTokenSale";

    public static final String FUNC_STRINGTOBYTES32 = "stringToBytes32";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_TOTALDIVIDENDS = "totalDividends";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UNASSIGNROLE = "unassignRole";

    public static final String FUNC_UNLOCKALLFUNDTRANSFERFUNCTIONS = "unlockAllFundTransferFunctions";

    public static final String FUNC_UNLOCKFUNCTION = "unlockFunction";

    public static final String FUNC_UPDATEENTITY = "updateEntity";

    public static final String FUNC_UPDATEENTITYTOKENINFO = "updateEntityTokenInfo";

    public static final String FUNC_UPDATEROLEASSIGNER = "updateRoleAssigner";

    public static final String FUNC_UPDATEROLEGROUP = "updateRoleGroup";

    public static final String FUNC_UPDATEUPGRADEEXPIRATION = "updateUpgradeExpiration";

    public static final String FUNC_WITHDRAWALLDIVIDENDS = "withdrawAllDividends";

    public static final String FUNC_WITHDRAWDIVIDEND = "withdrawDividend";

    public static final String FUNC_WRAPTOKEN = "wrapToken";

    public static final String FUNC_WRAPPERINTERNALTRANSFERFROM = "wrapperInternalTransferFrom";

    public static final Event LOG_EVENT = new Event("log", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event LOG_ADDRESS_EVENT = new Event("log_address", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event LOG_ARRAY_EVENT = new Event("log_array", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
    ;

    public static final Event LOG_BYTES_EVENT = new Event("log_bytes", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event LOG_BYTES32_EVENT = new Event("log_bytes32", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event LOG_INT_EVENT = new Event("log_int", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
    ;

    public static final Event LOG_NAMED_ADDRESS_EVENT = new Event("log_named_address", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event LOG_NAMED_ARRAY_EVENT = new Event("log_named_array", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<DynamicArray<Address>>() {}));
    ;

    public static final Event LOG_NAMED_BYTES_EVENT = new Event("log_named_bytes", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event LOG_NAMED_BYTES32_EVENT = new Event("log_named_bytes32", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event LOG_NAMED_DECIMAL_INT_EVENT = new Event("log_named_decimal_int", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOG_NAMED_DECIMAL_UINT_EVENT = new Event("log_named_decimal_uint", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOG_NAMED_INT_EVENT = new Event("log_named_int", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {}));
    ;

    public static final Event LOG_NAMED_STRING_EVENT = new Event("log_named_string", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event LOG_NAMED_UINT_EVENT = new Event("log_named_uint", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOG_STRING_EVENT = new Event("log_string", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event LOG_UINT_EVENT = new Event("log_uint", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGS_EVENT = new Event("logs", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event OBJECTUPDATED_EVENT = new Event("ObjectUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event DIVIDENDWITHDRAWN_EVENT = new Event("DividendWithdrawn", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event INTERNALTOKENBALANCEUPDATE_EVENT = new Event("InternalTokenBalanceUpdate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event INTERNALTOKENSUPPLYUPDATE_EVENT = new Event("InternalTokenSupplyUpdate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event DIVIDENDDISTRIBUTION_EVENT = new Event("DividendDistribution", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OBJECTCREATED_EVENT = new Event("ObjectCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event SLOTFOUND_EVENT = new Event("SlotFound", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes4>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WARNING_UNINITEDSLOT_EVENT = new Event("WARNING_UninitedSlot", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ENTITYCREATED_EVENT = new Event("EntityCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event ROLEUPDATED_EVENT = new Event("RoleUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event TOKENWRAPPED_EVENT = new Event("TokenWrapped", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>() {}));
    ;

    public static final Event FEEPAID_EVENT = new Event("FeePaid", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event SIMPLEPOLICYCANCELLED_EVENT = new Event("SimplePolicyCancelled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event SIMPLEPOLICYCLAIMPAID_EVENT = new Event("SimplePolicyClaimPaid", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event SIMPLEPOLICYMATURED_EVENT = new Event("SimplePolicyMatured", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event SIMPLEPOLICYPREMIUMPAID_EVENT = new Event("SimplePolicyPremiumPaid", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DIAMONDCUT_EVENT = new Event("DiamondCut", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<FacetCut>>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event ROLECANASSIGNUPDATED_EVENT = new Event("RoleCanAssignUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event ROLEGROUPUPDATED_EVENT = new Event("RoleGroupUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event ORDERADDED_EVENT = new Event("OrderAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ORDERCANCELLED_EVENT = new Event("OrderCancelled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event ORDEREXECUTED_EVENT = new Event("OrderExecuted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TOKENINFOUPDATED_EVENT = new Event("TokenInfoUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event TOKENIZATIONENABLED_EVENT = new Event("TokenizationEnabled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event FEESCHEDULEADDED_EVENT = new Event("FeeScheduleAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<FeeSchedule>() {}));
    ;

    public static final Event MAKERBASISPOINTSUPDATED_EVENT = new Event("MakerBasisPointsUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint16>() {}));
    ;

    public static final Event COLLATERALRATIOUPDATED_EVENT = new Event("CollateralRatioUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ENTITYUPDATED_EVENT = new Event("EntityUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event SIMPLEPOLICYCREATED_EVENT = new Event("SimplePolicyCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event TOKENSALESTARTED_EVENT = new Event("TokenSaleStarted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event FUNCTIONSLOCKED_EVENT = new Event("FunctionsLocked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes4>>() {}));
    ;

    public static final Event FUNCTIONSUNLOCKED_EVENT = new Event("FunctionsUnlocked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes4>>() {}));
    ;

    public static final Event MAXDIVIDENDDENOMINATIONSUPDATED_EVENT = new Event("MaxDividendDenominationsUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}));
    ;

    public static final Event SUPPORTEDTOKENADDED_EVENT = new Event("SupportedTokenAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event INITIALIZEDIAMOND_EVENT = new Event("InitializeDiamond", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event CREATEUPGRADE_EVENT = new Event("CreateUpgrade", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event UPDATEUPGRADEEXPIRATION_EVENT = new Event("UpdateUpgradeExpiration", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event UPGRADECANCELLED_EVENT = new Event("UpgradeCancelled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected NaymsDiamond(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NaymsDiamond(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NaymsDiamond(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NaymsDiamond(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addFeeSchedule(Bytes32 _entityId, Uint256 _feeScheduleType, DynamicArray<Bytes32> _receiver, DynamicArray<Uint16> _basisPoints) {
        final Function function = new Function(
                FUNC_ADDFEESCHEDULE, 
                Arrays.<Type>asList(_entityId, _feeScheduleType, _receiver, _basisPoints), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addSupportedExternalToken(Address _tokenAddress) {
        final Function function = new Function(
                FUNC_ADDSUPPORTEDEXTERNALTOKEN, 
                Arrays.<Type>asList(_tokenAddress), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> assignRole(Bytes32 _objectId, Bytes32 _contextId, Utf8String _role) {
        final Function function = new Function(
                FUNC_ASSIGNROLE, 
                Arrays.<Type>asList(_objectId, _contextId, _role), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Uint256> balanceOf(Address addr) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(addr), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<CalculatedFees> calculatePremiumFees(Bytes32 _policyId, Uint256 _premiumPaid) {
        final Function function = new Function(FUNC_CALCULATEPREMIUMFEES, 
                Arrays.<Type>asList(_policyId, _premiumPaid), 
                Arrays.<TypeReference<?>>asList(new TypeReference<CalculatedFees>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Tuple2<Uint256, Uint256>> calculateTradingFees(Bytes32 _buyerId, Bytes32 _sellToken, Bytes32 _buyToken, Uint256 _buyAmount) {
        final Function function = new Function(FUNC_CALCULATETRADINGFEES, 
                Arrays.<Type>asList(_buyerId, _sellToken, _buyToken, _buyAmount), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<Uint256, Uint256>>(function,
                new Callable<Tuple2<Uint256, Uint256>>() {
                    @Override
                    public Tuple2<Uint256, Uint256> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Uint256, Uint256>(
                                (Uint256) results.get(0), 
                                (Uint256) results.get(1));
                    }
                });
    }

    public RemoteFunctionCall<Bool> canAssign(Bytes32 _assignerId, Bytes32 _objectId, Bytes32 _contextId, Utf8String _role) {
        final Function function = new Function(FUNC_CANASSIGN, 
                Arrays.<Type>asList(_assignerId, _objectId, _contextId, _role), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> canGroupAssignRole(Utf8String role, Utf8String group) {
        final Function function = new Function(FUNC_CANGROUPASSIGNROLE, 
                Arrays.<Type>asList(role, group), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelOffer(Uint256 _offerId) {
        final Function function = new Function(
                FUNC_CANCELOFFER, 
                Arrays.<Type>asList(_offerId), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelSimplePolicy(Bytes32 _policyId) {
        final Function function = new Function(
                FUNC_CANCELSIMPLEPOLICY, 
                Arrays.<Type>asList(_policyId), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelUpgrade(Bytes32 id) {
        final Function function = new Function(
                FUNC_CANCELUPGRADE, 
                Arrays.<Type>asList(id), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> checkAndUpdateSimplePolicyState(Bytes32 _policyId) {
        final Function function = new Function(
                FUNC_CHECKANDUPDATESIMPLEPOLICYSTATE, 
                Arrays.<Type>asList(_policyId), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createEntity(Bytes32 _entityId, Bytes32 _entityAdmin, Entity _entityData, Bytes32 _dataHash) {
        final Function function = new Function(
                FUNC_CREATEENTITY, 
                Arrays.<Type>asList(_entityId, _entityAdmin, _entityData, _dataHash), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createSimplePolicy(Bytes32 _policyId, Bytes32 _entityId, Stakeholders _stakeholders, SimplePolicy _simplePolicy, Bytes32 _dataHash) {
        final Function function = new Function(
                FUNC_CREATESIMPLEPOLICY, 
                Arrays.<Type>asList(_policyId, _entityId, _stakeholders, _simplePolicy, _dataHash), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createUpgrade(Bytes32 id) {
        final Function function = new Function(
                FUNC_CREATEUPGRADE, 
                Arrays.<Type>asList(id), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Bytes32> domainSeparatorV4() {
        final Function function = new Function(FUNC_DOMAINSEPARATORV4, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> enableEntityTokenization(Bytes32 _objectId, Utf8String _symbol, Utf8String _name) {
        final Function function = new Function(
                FUNC_ENABLEENTITYTOKENIZATION, 
                Arrays.<Type>asList(_objectId, _symbol, _name), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> executeLimitOffer(Bytes32 _sellToken, Uint256 _sellAmount, Bytes32 _buyToken, Uint256 _buyAmount) {
        final Function function = new Function(
                FUNC_EXECUTELIMITOFFER, 
                Arrays.<Type>asList(_sellToken, _sellAmount, _buyToken, _buyAmount), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> externalDeposit(Address _externalTokenAddress, Uint256 _amount) {
        final Function function = new Function(
                FUNC_EXTERNALDEPOSIT, 
                Arrays.<Type>asList(_externalTokenAddress, _amount), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> externalWithdrawFromEntity(Bytes32 _entityId, Address _receiver, Address _externalTokenAddress, Uint256 _amount) {
        final Function function = new Function(
                FUNC_EXTERNALWITHDRAWFROMENTITY, 
                Arrays.<Type>asList(_entityId, _receiver, _externalTokenAddress, _amount), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Address> facetAddress(Bytes4 _functionSelector) {
        final Function function = new Function(FUNC_FACETADDRESS, 
                Arrays.<Type>asList(_functionSelector), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<DynamicArray<Address>> facetAddresses() {
        final Function function = new Function(FUNC_FACETADDRESSES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<DynamicArray<Bytes4>> facetFunctionSelectors(Address _facet) {
        final Function function = new Function(FUNC_FACETFUNCTIONSELECTORS, 
                Arrays.<Type>asList(_facet), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes4>>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Address> getAddressFromExternalTokenId(Bytes32 _externalTokenId) {
        final Function function = new Function(FUNC_GETADDRESSFROMEXTERNALTOKENID, 
                Arrays.<Type>asList(_externalTokenId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> getBestOfferId(Bytes32 _sellToken, Bytes32 _buyToken) {
        final Function function = new Function(FUNC_GETBESTOFFERID, 
                Arrays.<Type>asList(_sellToken, _buyToken), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bytes32> getEntity(Bytes32 _userId) {
        final Function function = new Function(FUNC_GETENTITY, 
                Arrays.<Type>asList(_userId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Entity> getEntityInfo(Bytes32 _entityId) {
        final Function function = new Function(FUNC_GETENTITYINFO, 
                Arrays.<Type>asList(_entityId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Entity>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<FeeSchedule> getFeeSchedule(Bytes32 _entityId, Uint256 _feeScheduleType) {
        final Function function = new Function(FUNC_GETFEESCHEDULE, 
                Arrays.<Type>asList(_entityId, _feeScheduleType), 
                Arrays.<TypeReference<?>>asList(new TypeReference<FeeSchedule>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> getLastOfferId() {
        final Function function = new Function(FUNC_GETLASTOFFERID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> getLockedBalance(Bytes32 _entityId, Bytes32 _tokenId) {
        final Function function = new Function(FUNC_GETLOCKEDBALANCE, 
                Arrays.<Type>asList(_entityId, _tokenId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint16> getMakerBP() {
        final Function function = new Function(FUNC_GETMAKERBP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint16>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint8> getMaxDividendDenominations() {
        final Function function = new Function(FUNC_GETMAXDIVIDENDDENOMINATIONS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Tuple5<Bytes32, Bytes32, Utf8String, Utf8String, Address>> getObjectMeta(Bytes32 _id) {
        final Function function = new Function(FUNC_GETOBJECTMETA, 
                Arrays.<Type>asList(_id), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple5<Bytes32, Bytes32, Utf8String, Utf8String, Address>>(function,
                new Callable<Tuple5<Bytes32, Bytes32, Utf8String, Utf8String, Address>>() {
                    @Override
                    public Tuple5<Bytes32, Bytes32, Utf8String, Utf8String, Address> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<Bytes32, Bytes32, Utf8String, Utf8String, Address>(
                                (Bytes32) results.get(0), 
                                (Bytes32) results.get(1), 
                                (Utf8String) results.get(2), 
                                (Utf8String) results.get(3), 
                                (Address) results.get(4));
                    }
                });
    }

    public RemoteFunctionCall<Utf8String> getObjectTokenSymbol(Bytes32 _objectId) {
        final Function function = new Function(FUNC_GETOBJECTTOKENSYMBOL, 
                Arrays.<Type>asList(_objectId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bytes12> getObjectType(Bytes32 _objectId) {
        final Function function = new Function(FUNC_GETOBJECTTYPE, 
                Arrays.<Type>asList(_objectId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes12>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<MarketInfo> getOffer(Uint256 _offerId) {
        final Function function = new Function(FUNC_GETOFFER, 
                Arrays.<Type>asList(_offerId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<MarketInfo>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<DynamicArray<Bytes32>> getPolicyCommissionReceivers(Bytes32 _id) {
        final Function function = new Function(FUNC_GETPOLICYCOMMISSIONRECEIVERS, 
                Arrays.<Type>asList(_id), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bytes32> getRoleInContext(Bytes32 objectId, Bytes32 contextId) {
        final Function function = new Function(FUNC_GETROLEINCONTEXT, 
                Arrays.<Type>asList(objectId, contextId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bytes32> getSigningHash(Uint256 _startDate, Uint256 _maturationDate, Bytes32 _asset, Uint256 _limit, Bytes32 _offchainDataHash) {
        final Function function = new Function(FUNC_GETSIGNINGHASH, 
                Arrays.<Type>asList(_startDate, _maturationDate, _asset, _limit, _offchainDataHash), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<SimplePolicyInfo> getSimplePolicyInfo(Bytes32 _policyId) {
        final Function function = new Function(FUNC_GETSIMPLEPOLICYINFO, 
                Arrays.<Type>asList(_policyId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<SimplePolicyInfo>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<DynamicArray<Address>> getSupportedExternalTokens() {
        final Function function = new Function(FUNC_GETSUPPORTEDEXTERNALTOKENS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bytes32> getSystemId() {
        final Function function = new Function(FUNC_GETSYSTEMID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> getUpgrade(Bytes32 id) {
        final Function function = new Function(FUNC_GETUPGRADE, 
                Arrays.<Type>asList(id), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> getUpgradeExpiration() {
        final Function function = new Function(FUNC_GETUPGRADEEXPIRATION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bytes32> getUserIdFromAddress(Address addr) {
        final Function function = new Function(FUNC_GETUSERIDFROMADDRESS, 
                Arrays.<Type>asList(addr), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> getWithdrawableDividend(Bytes32 ownerId, Bytes32 tokenId, Bytes32 dividendTokenId) {
        final Function function = new Function(FUNC_GETWITHDRAWABLEDIVIDEND, 
                Arrays.<Type>asList(ownerId, tokenId, dividendTokenId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> hasGroupPrivilege(Bytes32 _userId, Bytes32 _contextId, Bytes32 _groupId) {
        final Function function = new Function(FUNC_HASGROUPPRIVILEGE, 
                Arrays.<Type>asList(_userId, _contextId, _groupId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bytes32> hashTypedDataV4(Bytes32 structHash) {
        final Function function = new Function(FUNC_HASHTYPEDDATAV4, 
                Arrays.<Type>asList(structHash), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> internalBalanceOf(Bytes32 ownerId, Bytes32 tokenId) {
        final Function function = new Function(FUNC_INTERNALBALANCEOF, 
                Arrays.<Type>asList(ownerId, tokenId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> internalBurn(Bytes32 from, Bytes32 tokenId, Uint256 amount) {
        final Function function = new Function(
                FUNC_INTERNALBURN, 
                Arrays.<Type>asList(from, tokenId, amount), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Uint256> internalTokenSupply(Bytes32 tokenId) {
        final Function function = new Function(FUNC_INTERNALTOKENSUPPLY, 
                Arrays.<Type>asList(tokenId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> internalTransferBySystemAdmin(Bytes32 _fromEntityId, Bytes32 _toEntityId, Bytes32 _tokenId, Uint256 _amount) {
        final Function function = new Function(
                FUNC_INTERNALTRANSFERBYSYSTEMADMIN, 
                Arrays.<Type>asList(_fromEntityId, _toEntityId, _tokenId, _amount), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> internalTransferFromEntity(Bytes32 to, Bytes32 tokenId, Uint256 amount) {
        final Function function = new Function(
                FUNC_INTERNALTRANSFERFROMENTITY, 
                Arrays.<Type>asList(to, tokenId, amount), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Bool> isActiveOffer(Uint256 _offerId) {
        final Function function = new Function(FUNC_ISACTIVEOFFER, 
                Arrays.<Type>asList(_offerId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> isDiamondInitialized() {
        final Function function = new Function(FUNC_ISDIAMONDINITIALIZED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> isFunctionLocked(Bytes4 functionSelector) {
        final Function function = new Function(FUNC_ISFUNCTIONLOCKED, 
                Arrays.<Type>asList(functionSelector), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> isInGroup(Bytes32 _objectId, Bytes32 _contextId, Utf8String _group) {
        final Function function = new Function(FUNC_ISINGROUP, 
                Arrays.<Type>asList(_objectId, _contextId, _group), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> isObject(Bytes32 _id) {
        final Function function = new Function(FUNC_ISOBJECT, 
                Arrays.<Type>asList(_id), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> isObjectTokenizable(Bytes32 _objectId) {
        final Function function = new Function(FUNC_ISOBJECTTOKENIZABLE, 
                Arrays.<Type>asList(_objectId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> isObjectType(Bytes32 _objectId, Bytes12 _objectType) {
        final Function function = new Function(FUNC_ISOBJECTTYPE, 
                Arrays.<Type>asList(_objectId, _objectType), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> isParentInGroup(Bytes32 _objectId, Bytes32 _contextId, Utf8String _group) {
        final Function function = new Function(FUNC_ISPARENTINGROUP, 
                Arrays.<Type>asList(_objectId, _contextId, _group), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> isRoleInGroup(Utf8String role, Utf8String group) {
        final Function function = new Function(FUNC_ISROLEINGROUP, 
                Arrays.<Type>asList(role, group), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> isSupportedExternalToken(Bytes32 _tokenId) {
        final Function function = new Function(FUNC_ISSUPPORTEDEXTERNALTOKEN, 
                Arrays.<Type>asList(_tokenId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> isTokenWrapped(Bytes32 _entityId) {
        final Function function = new Function(FUNC_ISTOKENWRAPPED, 
                Arrays.<Type>asList(_entityId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> lockAllFundTransferFunctions() {
        final Function function = new Function(
                FUNC_LOCKALLFUNDTRANSFERFUNCTIONS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> lockFunction(Bytes4 functionSelector) {
        final Function function = new Function(
                FUNC_LOCKFUNCTION, 
                Arrays.<Type>asList(functionSelector), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Address> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> payDividendFromEntity(Bytes32 guid, Uint256 amount) {
        final Function function = new Function(
                FUNC_PAYDIVIDENDFROMENTITY, 
                Arrays.<Type>asList(guid, amount), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> paySimpleClaim(Bytes32 _claimId, Bytes32 _policyId, Bytes32 _insuredId, Uint256 _amount) {
        final Function function = new Function(
                FUNC_PAYSIMPLECLAIM, 
                Arrays.<Type>asList(_claimId, _policyId, _insuredId, _amount), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> paySimplePremium(Bytes32 _policyId, Uint256 _amount) {
        final Function function = new Function(
                FUNC_PAYSIMPLEPREMIUM, 
                Arrays.<Type>asList(_policyId, _amount), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeFeeSchedule(Bytes32 _entityId, Uint256 _feeScheduleType) {
        final Function function = new Function(
                FUNC_REMOVEFEESCHEDULE, 
                Arrays.<Type>asList(_entityId, _feeScheduleType), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> replaceMakerBP(Uint16 _newMakerBP) {
        final Function function = new Function(
                FUNC_REPLACEMAKERBP, 
                Arrays.<Type>asList(_newMakerBP), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setEntity(Bytes32 _userId, Bytes32 _entityId) {
        final Function function = new Function(
                FUNC_SETENTITY, 
                Arrays.<Type>asList(_userId, _entityId), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setMaxDividendDenominations(Uint8 _newMax) {
        final Function function = new Function(
                FUNC_SETMAXDIVIDENDDENOMINATIONS, 
                Arrays.<Type>asList(_newMax), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> startTokenSale(Bytes32 _entityId, Uint256 _amount, Uint256 _totalPrice) {
        final Function function = new Function(
                FUNC_STARTTOKENSALE, 
                Arrays.<Type>asList(_entityId, _amount, _totalPrice), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Bytes32> stringToBytes32(Utf8String _strIn) {
        final Function function = new Function(FUNC_STRINGTOBYTES32, 
                Arrays.<Type>asList(_strIn), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Bool> supportsInterface(Bytes4 interfaceId) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(interfaceId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> totalDividends(Bytes32 _tokenId, Bytes32 _dividendDenominationId) {
        final Function function = new Function(FUNC_TOTALDIVIDENDS, 
                Arrays.<Type>asList(_tokenId, _dividendDenominationId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<Uint256> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(Address _newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(_newOwner), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unassignRole(Bytes32 _objectId, Bytes32 _contextId) {
        final Function function = new Function(
                FUNC_UNASSIGNROLE, 
                Arrays.<Type>asList(_objectId, _contextId), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unlockAllFundTransferFunctions() {
        final Function function = new Function(
                FUNC_UNLOCKALLFUNDTRANSFERFUNCTIONS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unlockFunction(Bytes4 functionSelector) {
        final Function function = new Function(
                FUNC_UNLOCKFUNCTION, 
                Arrays.<Type>asList(functionSelector), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateEntity(Bytes32 _entityId, Entity _updateEntity) {
        final Function function = new Function(
                FUNC_UPDATEENTITY, 
                Arrays.<Type>asList(_entityId, _updateEntity), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateEntityTokenInfo(Bytes32 _entityId, Utf8String _symbol, Utf8String _name) {
        final Function function = new Function(
                FUNC_UPDATEENTITYTOKENINFO, 
                Arrays.<Type>asList(_entityId, _symbol, _name), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateRoleAssigner(Utf8String _role, Utf8String _assignerGroup) {
        final Function function = new Function(
                FUNC_UPDATEROLEASSIGNER, 
                Arrays.<Type>asList(_role, _assignerGroup), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateRoleGroup(Utf8String _role, Utf8String _group, Bool _roleInGroup) {
        final Function function = new Function(
                FUNC_UPDATEROLEGROUP, 
                Arrays.<Type>asList(_role, _group, _roleInGroup), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateUpgradeExpiration(Uint256 duration) {
        final Function function = new Function(
                FUNC_UPDATEUPGRADEEXPIRATION, 
                Arrays.<Type>asList(duration), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawAllDividends(Bytes32 ownerId, Bytes32 tokenId) {
        final Function function = new Function(
                FUNC_WITHDRAWALLDIVIDENDS, 
                Arrays.<Type>asList(ownerId, tokenId), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawDividend(Bytes32 ownerId, Bytes32 tokenId, Bytes32 dividendTokenId) {
        final Function function = new Function(
                FUNC_WITHDRAWDIVIDEND, 
                Arrays.<Type>asList(ownerId, tokenId, dividendTokenId), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> wrapToken(Bytes32 _objectId) {
        final Function function = new Function(
                FUNC_WRAPTOKEN, 
                Arrays.<Type>asList(_objectId), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> wrapperInternalTransferFrom(Bytes32 from, Bytes32 to, Bytes32 tokenId, Uint256 amount) {
        final Function function = new Function(
                FUNC_WRAPPERINTERNALTRANSFERFROM, 
                Arrays.<Type>asList(from, to, tokenId, amount), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static List<LogEventResponse> getLogEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_EVENT, transactionReceipt);
        ArrayList<LogEventResponse> responses = new ArrayList<LogEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogEventResponse typedResponse = new LogEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param0 = (Utf8String) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static LogEventResponse getLogEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_EVENT, log);
        LogEventResponse typedResponse = new LogEventResponse();
        typedResponse.log = log;
        typedResponse.param0 = (Utf8String) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<LogEventResponse> logEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLogEventFromLog(log));
    }

    public Flowable<LogEventResponse> logEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_EVENT));
        return logEventFlowable(filter);
    }

    public static List<Log_addressEventResponse> getLog_addressEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_ADDRESS_EVENT, transactionReceipt);
        ArrayList<Log_addressEventResponse> responses = new ArrayList<Log_addressEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_addressEventResponse typedResponse = new Log_addressEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param0 = (Address) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_addressEventResponse getLog_addressEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_ADDRESS_EVENT, log);
        Log_addressEventResponse typedResponse = new Log_addressEventResponse();
        typedResponse.log = log;
        typedResponse.param0 = (Address) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<Log_addressEventResponse> log_addressEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_addressEventFromLog(log));
    }

    public Flowable<Log_addressEventResponse> log_addressEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_ADDRESS_EVENT));
        return log_addressEventFlowable(filter);
    }

    public static List<Log_arrayEventResponse> getLog_arrayEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_ARRAY_EVENT, transactionReceipt);
        ArrayList<Log_arrayEventResponse> responses = new ArrayList<Log_arrayEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_arrayEventResponse typedResponse = new Log_arrayEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.val = (DynamicArray<Address>) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_arrayEventResponse getLog_arrayEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_ARRAY_EVENT, log);
        Log_arrayEventResponse typedResponse = new Log_arrayEventResponse();
        typedResponse.log = log;
        typedResponse.val = (DynamicArray<Address>) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<Log_arrayEventResponse> log_arrayEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_arrayEventFromLog(log));
    }

    public Flowable<Log_arrayEventResponse> log_arrayEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_ARRAY_EVENT));
        return log_arrayEventFlowable(filter);
    }

    public static List<Log_bytesEventResponse> getLog_bytesEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_BYTES_EVENT, transactionReceipt);
        ArrayList<Log_bytesEventResponse> responses = new ArrayList<Log_bytesEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_bytesEventResponse typedResponse = new Log_bytesEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param0 = (DynamicBytes) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_bytesEventResponse getLog_bytesEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_BYTES_EVENT, log);
        Log_bytesEventResponse typedResponse = new Log_bytesEventResponse();
        typedResponse.log = log;
        typedResponse.param0 = (DynamicBytes) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<Log_bytesEventResponse> log_bytesEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_bytesEventFromLog(log));
    }

    public Flowable<Log_bytesEventResponse> log_bytesEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_BYTES_EVENT));
        return log_bytesEventFlowable(filter);
    }

    public static List<Log_bytes32EventResponse> getLog_bytes32Events(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_BYTES32_EVENT, transactionReceipt);
        ArrayList<Log_bytes32EventResponse> responses = new ArrayList<Log_bytes32EventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_bytes32EventResponse typedResponse = new Log_bytes32EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param0 = (Bytes32) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_bytes32EventResponse getLog_bytes32EventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_BYTES32_EVENT, log);
        Log_bytes32EventResponse typedResponse = new Log_bytes32EventResponse();
        typedResponse.log = log;
        typedResponse.param0 = (Bytes32) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<Log_bytes32EventResponse> log_bytes32EventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_bytes32EventFromLog(log));
    }

    public Flowable<Log_bytes32EventResponse> log_bytes32EventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_BYTES32_EVENT));
        return log_bytes32EventFlowable(filter);
    }

    public static List<Log_intEventResponse> getLog_intEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_INT_EVENT, transactionReceipt);
        ArrayList<Log_intEventResponse> responses = new ArrayList<Log_intEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_intEventResponse typedResponse = new Log_intEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param0 = (Int256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_intEventResponse getLog_intEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_INT_EVENT, log);
        Log_intEventResponse typedResponse = new Log_intEventResponse();
        typedResponse.log = log;
        typedResponse.param0 = (Int256) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<Log_intEventResponse> log_intEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_intEventFromLog(log));
    }

    public Flowable<Log_intEventResponse> log_intEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_INT_EVENT));
        return log_intEventFlowable(filter);
    }

    public static List<Log_named_addressEventResponse> getLog_named_addressEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_NAMED_ADDRESS_EVENT, transactionReceipt);
        ArrayList<Log_named_addressEventResponse> responses = new ArrayList<Log_named_addressEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_named_addressEventResponse typedResponse = new Log_named_addressEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse.val = (Address) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_named_addressEventResponse getLog_named_addressEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_NAMED_ADDRESS_EVENT, log);
        Log_named_addressEventResponse typedResponse = new Log_named_addressEventResponse();
        typedResponse.log = log;
        typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
        typedResponse.val = (Address) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<Log_named_addressEventResponse> log_named_addressEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_named_addressEventFromLog(log));
    }

    public Flowable<Log_named_addressEventResponse> log_named_addressEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_NAMED_ADDRESS_EVENT));
        return log_named_addressEventFlowable(filter);
    }

    public static List<Log_named_arrayEventResponse> getLog_named_arrayEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_NAMED_ARRAY_EVENT, transactionReceipt);
        ArrayList<Log_named_arrayEventResponse> responses = new ArrayList<Log_named_arrayEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_named_arrayEventResponse typedResponse = new Log_named_arrayEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse.val = (DynamicArray<Address>) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_named_arrayEventResponse getLog_named_arrayEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_NAMED_ARRAY_EVENT, log);
        Log_named_arrayEventResponse typedResponse = new Log_named_arrayEventResponse();
        typedResponse.log = log;
        typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
        typedResponse.val = (DynamicArray<Address>) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<Log_named_arrayEventResponse> log_named_arrayEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_named_arrayEventFromLog(log));
    }

    public Flowable<Log_named_arrayEventResponse> log_named_arrayEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_NAMED_ARRAY_EVENT));
        return log_named_arrayEventFlowable(filter);
    }

    public static List<Log_named_bytesEventResponse> getLog_named_bytesEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_NAMED_BYTES_EVENT, transactionReceipt);
        ArrayList<Log_named_bytesEventResponse> responses = new ArrayList<Log_named_bytesEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_named_bytesEventResponse typedResponse = new Log_named_bytesEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse.val = (DynamicBytes) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_named_bytesEventResponse getLog_named_bytesEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_NAMED_BYTES_EVENT, log);
        Log_named_bytesEventResponse typedResponse = new Log_named_bytesEventResponse();
        typedResponse.log = log;
        typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
        typedResponse.val = (DynamicBytes) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<Log_named_bytesEventResponse> log_named_bytesEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_named_bytesEventFromLog(log));
    }

    public Flowable<Log_named_bytesEventResponse> log_named_bytesEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_NAMED_BYTES_EVENT));
        return log_named_bytesEventFlowable(filter);
    }

    public static List<Log_named_bytes32EventResponse> getLog_named_bytes32Events(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_NAMED_BYTES32_EVENT, transactionReceipt);
        ArrayList<Log_named_bytes32EventResponse> responses = new ArrayList<Log_named_bytes32EventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_named_bytes32EventResponse typedResponse = new Log_named_bytes32EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse.val = (Bytes32) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_named_bytes32EventResponse getLog_named_bytes32EventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_NAMED_BYTES32_EVENT, log);
        Log_named_bytes32EventResponse typedResponse = new Log_named_bytes32EventResponse();
        typedResponse.log = log;
        typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
        typedResponse.val = (Bytes32) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<Log_named_bytes32EventResponse> log_named_bytes32EventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_named_bytes32EventFromLog(log));
    }

    public Flowable<Log_named_bytes32EventResponse> log_named_bytes32EventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_NAMED_BYTES32_EVENT));
        return log_named_bytes32EventFlowable(filter);
    }

    public static List<Log_named_decimal_intEventResponse> getLog_named_decimal_intEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_NAMED_DECIMAL_INT_EVENT, transactionReceipt);
        ArrayList<Log_named_decimal_intEventResponse> responses = new ArrayList<Log_named_decimal_intEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_named_decimal_intEventResponse typedResponse = new Log_named_decimal_intEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse.val = (Int256) eventValues.getNonIndexedValues().get(1);
            typedResponse.decimals = (Uint256) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_named_decimal_intEventResponse getLog_named_decimal_intEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_NAMED_DECIMAL_INT_EVENT, log);
        Log_named_decimal_intEventResponse typedResponse = new Log_named_decimal_intEventResponse();
        typedResponse.log = log;
        typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
        typedResponse.val = (Int256) eventValues.getNonIndexedValues().get(1);
        typedResponse.decimals = (Uint256) eventValues.getNonIndexedValues().get(2);
        return typedResponse;
    }

    public Flowable<Log_named_decimal_intEventResponse> log_named_decimal_intEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_named_decimal_intEventFromLog(log));
    }

    public Flowable<Log_named_decimal_intEventResponse> log_named_decimal_intEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_NAMED_DECIMAL_INT_EVENT));
        return log_named_decimal_intEventFlowable(filter);
    }

    public static List<Log_named_decimal_uintEventResponse> getLog_named_decimal_uintEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_NAMED_DECIMAL_UINT_EVENT, transactionReceipt);
        ArrayList<Log_named_decimal_uintEventResponse> responses = new ArrayList<Log_named_decimal_uintEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_named_decimal_uintEventResponse typedResponse = new Log_named_decimal_uintEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse.val = (Uint256) eventValues.getNonIndexedValues().get(1);
            typedResponse.decimals = (Uint256) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_named_decimal_uintEventResponse getLog_named_decimal_uintEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_NAMED_DECIMAL_UINT_EVENT, log);
        Log_named_decimal_uintEventResponse typedResponse = new Log_named_decimal_uintEventResponse();
        typedResponse.log = log;
        typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
        typedResponse.val = (Uint256) eventValues.getNonIndexedValues().get(1);
        typedResponse.decimals = (Uint256) eventValues.getNonIndexedValues().get(2);
        return typedResponse;
    }

    public Flowable<Log_named_decimal_uintEventResponse> log_named_decimal_uintEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_named_decimal_uintEventFromLog(log));
    }

    public Flowable<Log_named_decimal_uintEventResponse> log_named_decimal_uintEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_NAMED_DECIMAL_UINT_EVENT));
        return log_named_decimal_uintEventFlowable(filter);
    }

    public static List<Log_named_intEventResponse> getLog_named_intEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_NAMED_INT_EVENT, transactionReceipt);
        ArrayList<Log_named_intEventResponse> responses = new ArrayList<Log_named_intEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_named_intEventResponse typedResponse = new Log_named_intEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse.val = (Int256) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_named_intEventResponse getLog_named_intEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_NAMED_INT_EVENT, log);
        Log_named_intEventResponse typedResponse = new Log_named_intEventResponse();
        typedResponse.log = log;
        typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
        typedResponse.val = (Int256) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<Log_named_intEventResponse> log_named_intEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_named_intEventFromLog(log));
    }

    public Flowable<Log_named_intEventResponse> log_named_intEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_NAMED_INT_EVENT));
        return log_named_intEventFlowable(filter);
    }

    public static List<Log_named_stringEventResponse> getLog_named_stringEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_NAMED_STRING_EVENT, transactionReceipt);
        ArrayList<Log_named_stringEventResponse> responses = new ArrayList<Log_named_stringEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_named_stringEventResponse typedResponse = new Log_named_stringEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse.val = (Utf8String) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_named_stringEventResponse getLog_named_stringEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_NAMED_STRING_EVENT, log);
        Log_named_stringEventResponse typedResponse = new Log_named_stringEventResponse();
        typedResponse.log = log;
        typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
        typedResponse.val = (Utf8String) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<Log_named_stringEventResponse> log_named_stringEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_named_stringEventFromLog(log));
    }

    public Flowable<Log_named_stringEventResponse> log_named_stringEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_NAMED_STRING_EVENT));
        return log_named_stringEventFlowable(filter);
    }

    public static List<Log_named_uintEventResponse> getLog_named_uintEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_NAMED_UINT_EVENT, transactionReceipt);
        ArrayList<Log_named_uintEventResponse> responses = new ArrayList<Log_named_uintEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_named_uintEventResponse typedResponse = new Log_named_uintEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse.val = (Uint256) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_named_uintEventResponse getLog_named_uintEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_NAMED_UINT_EVENT, log);
        Log_named_uintEventResponse typedResponse = new Log_named_uintEventResponse();
        typedResponse.log = log;
        typedResponse.key = (Utf8String) eventValues.getNonIndexedValues().get(0);
        typedResponse.val = (Uint256) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<Log_named_uintEventResponse> log_named_uintEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_named_uintEventFromLog(log));
    }

    public Flowable<Log_named_uintEventResponse> log_named_uintEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_NAMED_UINT_EVENT));
        return log_named_uintEventFlowable(filter);
    }

    public static List<Log_stringEventResponse> getLog_stringEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_STRING_EVENT, transactionReceipt);
        ArrayList<Log_stringEventResponse> responses = new ArrayList<Log_stringEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_stringEventResponse typedResponse = new Log_stringEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param0 = (Utf8String) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_stringEventResponse getLog_stringEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_STRING_EVENT, log);
        Log_stringEventResponse typedResponse = new Log_stringEventResponse();
        typedResponse.log = log;
        typedResponse.param0 = (Utf8String) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<Log_stringEventResponse> log_stringEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_stringEventFromLog(log));
    }

    public Flowable<Log_stringEventResponse> log_stringEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_STRING_EVENT));
        return log_stringEventFlowable(filter);
    }

    public static List<Log_uintEventResponse> getLog_uintEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOG_UINT_EVENT, transactionReceipt);
        ArrayList<Log_uintEventResponse> responses = new ArrayList<Log_uintEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Log_uintEventResponse typedResponse = new Log_uintEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param0 = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Log_uintEventResponse getLog_uintEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOG_UINT_EVENT, log);
        Log_uintEventResponse typedResponse = new Log_uintEventResponse();
        typedResponse.log = log;
        typedResponse.param0 = (Uint256) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<Log_uintEventResponse> log_uintEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLog_uintEventFromLog(log));
    }

    public Flowable<Log_uintEventResponse> log_uintEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOG_UINT_EVENT));
        return log_uintEventFlowable(filter);
    }

    public static List<LogsEventResponse> getLogsEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOGS_EVENT, transactionReceipt);
        ArrayList<LogsEventResponse> responses = new ArrayList<LogsEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogsEventResponse typedResponse = new LogsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param0 = (DynamicBytes) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static LogsEventResponse getLogsEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOGS_EVENT, log);
        LogsEventResponse typedResponse = new LogsEventResponse();
        typedResponse.log = log;
        typedResponse.param0 = (DynamicBytes) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<LogsEventResponse> logsEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLogsEventFromLog(log));
    }

    public Flowable<LogsEventResponse> logsEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGS_EVENT));
        return logsEventFlowable(filter);
    }

    public static List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.spender = (Address) eventValues.getIndexedValues().get(1);
            typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ApprovalEventResponse getApprovalEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVAL_EVENT, log);
        ApprovalEventResponse typedResponse = new ApprovalEventResponse();
        typedResponse.log = log;
        typedResponse.owner = (Address) eventValues.getIndexedValues().get(0);
        typedResponse.spender = (Address) eventValues.getIndexedValues().get(1);
        typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getApprovalEventFromLog(log));
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public static List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.to = (Address) eventValues.getIndexedValues().get(1);
            typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransferEventResponse getTransferEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
        TransferEventResponse typedResponse = new TransferEventResponse();
        typedResponse.log = log;
        typedResponse.from = (Address) eventValues.getIndexedValues().get(0);
        typedResponse.to = (Address) eventValues.getIndexedValues().get(1);
        typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransferEventFromLog(log));
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.newOwner = (Address) eventValues.getIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (Address) eventValues.getIndexedValues().get(0);
        typedResponse.newOwner = (Address) eventValues.getIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public static List<ObjectUpdatedEventResponse> getObjectUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OBJECTUPDATED_EVENT, transactionReceipt);
        ArrayList<ObjectUpdatedEventResponse> responses = new ArrayList<ObjectUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ObjectUpdatedEventResponse typedResponse = new ObjectUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.objectId = (Bytes32) eventValues.getNonIndexedValues().get(0);
            typedResponse.parentId = (Bytes32) eventValues.getNonIndexedValues().get(1);
            typedResponse.dataHash = (Bytes32) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ObjectUpdatedEventResponse getObjectUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OBJECTUPDATED_EVENT, log);
        ObjectUpdatedEventResponse typedResponse = new ObjectUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.objectId = (Bytes32) eventValues.getNonIndexedValues().get(0);
        typedResponse.parentId = (Bytes32) eventValues.getNonIndexedValues().get(1);
        typedResponse.dataHash = (Bytes32) eventValues.getNonIndexedValues().get(2);
        return typedResponse;
    }

    public Flowable<ObjectUpdatedEventResponse> objectUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getObjectUpdatedEventFromLog(log));
    }

    public Flowable<ObjectUpdatedEventResponse> objectUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OBJECTUPDATED_EVENT));
        return objectUpdatedEventFlowable(filter);
    }

    public static List<DividendWithdrawnEventResponse> getDividendWithdrawnEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DIVIDENDWITHDRAWN_EVENT, transactionReceipt);
        ArrayList<DividendWithdrawnEventResponse> responses = new ArrayList<DividendWithdrawnEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DividendWithdrawnEventResponse typedResponse = new DividendWithdrawnEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.accountId = (Bytes32) eventValues.getIndexedValues().get(0);
            typedResponse.tokenId = (Bytes32) eventValues.getNonIndexedValues().get(0);
            typedResponse.amountOwned = (Uint256) eventValues.getNonIndexedValues().get(1);
            typedResponse.dividendTokenId = (Bytes32) eventValues.getNonIndexedValues().get(2);
            typedResponse.dividendAmountWithdrawn = (Uint256) eventValues.getNonIndexedValues().get(3);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DividendWithdrawnEventResponse getDividendWithdrawnEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DIVIDENDWITHDRAWN_EVENT, log);
        DividendWithdrawnEventResponse typedResponse = new DividendWithdrawnEventResponse();
        typedResponse.log = log;
        typedResponse.accountId = (Bytes32) eventValues.getIndexedValues().get(0);
        typedResponse.tokenId = (Bytes32) eventValues.getNonIndexedValues().get(0);
        typedResponse.amountOwned = (Uint256) eventValues.getNonIndexedValues().get(1);
        typedResponse.dividendTokenId = (Bytes32) eventValues.getNonIndexedValues().get(2);
        typedResponse.dividendAmountWithdrawn = (Uint256) eventValues.getNonIndexedValues().get(3);
        return typedResponse;
    }

    public Flowable<DividendWithdrawnEventResponse> dividendWithdrawnEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDividendWithdrawnEventFromLog(log));
    }

    public Flowable<DividendWithdrawnEventResponse> dividendWithdrawnEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DIVIDENDWITHDRAWN_EVENT));
        return dividendWithdrawnEventFlowable(filter);
    }

    public static List<InternalTokenBalanceUpdateEventResponse> getInternalTokenBalanceUpdateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(INTERNALTOKENBALANCEUPDATE_EVENT, transactionReceipt);
        ArrayList<InternalTokenBalanceUpdateEventResponse> responses = new ArrayList<InternalTokenBalanceUpdateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            InternalTokenBalanceUpdateEventResponse typedResponse = new InternalTokenBalanceUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ownerId = (Bytes32) eventValues.getIndexedValues().get(0);
            typedResponse.msgSender = (Address) eventValues.getIndexedValues().get(1);
            typedResponse.tokenId = (Bytes32) eventValues.getNonIndexedValues().get(0);
            typedResponse.newAmountOwned = (Uint256) eventValues.getNonIndexedValues().get(1);
            typedResponse.functionName = (Utf8String) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static InternalTokenBalanceUpdateEventResponse getInternalTokenBalanceUpdateEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(INTERNALTOKENBALANCEUPDATE_EVENT, log);
        InternalTokenBalanceUpdateEventResponse typedResponse = new InternalTokenBalanceUpdateEventResponse();
        typedResponse.log = log;
        typedResponse.ownerId = (Bytes32) eventValues.getIndexedValues().get(0);
        typedResponse.msgSender = (Address) eventValues.getIndexedValues().get(1);
        typedResponse.tokenId = (Bytes32) eventValues.getNonIndexedValues().get(0);
        typedResponse.newAmountOwned = (Uint256) eventValues.getNonIndexedValues().get(1);
        typedResponse.functionName = (Utf8String) eventValues.getNonIndexedValues().get(2);
        return typedResponse;
    }

    public Flowable<InternalTokenBalanceUpdateEventResponse> internalTokenBalanceUpdateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getInternalTokenBalanceUpdateEventFromLog(log));
    }

    public Flowable<InternalTokenBalanceUpdateEventResponse> internalTokenBalanceUpdateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INTERNALTOKENBALANCEUPDATE_EVENT));
        return internalTokenBalanceUpdateEventFlowable(filter);
    }

    public static List<InternalTokenSupplyUpdateEventResponse> getInternalTokenSupplyUpdateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(INTERNALTOKENSUPPLYUPDATE_EVENT, transactionReceipt);
        ArrayList<InternalTokenSupplyUpdateEventResponse> responses = new ArrayList<InternalTokenSupplyUpdateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            InternalTokenSupplyUpdateEventResponse typedResponse = new InternalTokenSupplyUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenId = (Bytes32) eventValues.getIndexedValues().get(0);
            typedResponse.msgSender = (Address) eventValues.getIndexedValues().get(1);
            typedResponse.newTokenSupply = (Uint256) eventValues.getNonIndexedValues().get(0);
            typedResponse.functionName = (Utf8String) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static InternalTokenSupplyUpdateEventResponse getInternalTokenSupplyUpdateEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(INTERNALTOKENSUPPLYUPDATE_EVENT, log);
        InternalTokenSupplyUpdateEventResponse typedResponse = new InternalTokenSupplyUpdateEventResponse();
        typedResponse.log = log;
        typedResponse.tokenId = (Bytes32) eventValues.getIndexedValues().get(0);
        typedResponse.msgSender = (Address) eventValues.getIndexedValues().get(1);
        typedResponse.newTokenSupply = (Uint256) eventValues.getNonIndexedValues().get(0);
        typedResponse.functionName = (Utf8String) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<InternalTokenSupplyUpdateEventResponse> internalTokenSupplyUpdateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getInternalTokenSupplyUpdateEventFromLog(log));
    }

    public Flowable<InternalTokenSupplyUpdateEventResponse> internalTokenSupplyUpdateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INTERNALTOKENSUPPLYUPDATE_EVENT));
        return internalTokenSupplyUpdateEventFlowable(filter);
    }

    public static List<DividendDistributionEventResponse> getDividendDistributionEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DIVIDENDDISTRIBUTION_EVENT, transactionReceipt);
        ArrayList<DividendDistributionEventResponse> responses = new ArrayList<DividendDistributionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DividendDistributionEventResponse typedResponse = new DividendDistributionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.guid = (Bytes32) eventValues.getIndexedValues().get(0);
            typedResponse.from = (Bytes32) eventValues.getNonIndexedValues().get(0);
            typedResponse.to = (Bytes32) eventValues.getNonIndexedValues().get(1);
            typedResponse.dividendTokenId = (Bytes32) eventValues.getNonIndexedValues().get(2);
            typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(3);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DividendDistributionEventResponse getDividendDistributionEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DIVIDENDDISTRIBUTION_EVENT, log);
        DividendDistributionEventResponse typedResponse = new DividendDistributionEventResponse();
        typedResponse.log = log;
        typedResponse.guid = (Bytes32) eventValues.getIndexedValues().get(0);
        typedResponse.from = (Bytes32) eventValues.getNonIndexedValues().get(0);
        typedResponse.to = (Bytes32) eventValues.getNonIndexedValues().get(1);
        typedResponse.dividendTokenId = (Bytes32) eventValues.getNonIndexedValues().get(2);
        typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(3);
        return typedResponse;
    }

    public Flowable<DividendDistributionEventResponse> dividendDistributionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDividendDistributionEventFromLog(log));
    }

    public Flowable<DividendDistributionEventResponse> dividendDistributionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DIVIDENDDISTRIBUTION_EVENT));
        return dividendDistributionEventFlowable(filter);
    }

    public static List<ObjectCreatedEventResponse> getObjectCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OBJECTCREATED_EVENT, transactionReceipt);
        ArrayList<ObjectCreatedEventResponse> responses = new ArrayList<ObjectCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ObjectCreatedEventResponse typedResponse = new ObjectCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.objectId = (Bytes32) eventValues.getNonIndexedValues().get(0);
            typedResponse.parentId = (Bytes32) eventValues.getNonIndexedValues().get(1);
            typedResponse.dataHash = (Bytes32) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ObjectCreatedEventResponse getObjectCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OBJECTCREATED_EVENT, log);
        ObjectCreatedEventResponse typedResponse = new ObjectCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.objectId = (Bytes32) eventValues.getNonIndexedValues().get(0);
        typedResponse.parentId = (Bytes32) eventValues.getNonIndexedValues().get(1);
        typedResponse.dataHash = (Bytes32) eventValues.getNonIndexedValues().get(2);
        return typedResponse;
    }

    public Flowable<ObjectCreatedEventResponse> objectCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getObjectCreatedEventFromLog(log));
    }

    public Flowable<ObjectCreatedEventResponse> objectCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OBJECTCREATED_EVENT));
        return objectCreatedEventFlowable(filter);
    }

    public static List<SlotFoundEventResponse> getSlotFoundEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SLOTFOUND_EVENT, transactionReceipt);
        ArrayList<SlotFoundEventResponse> responses = new ArrayList<SlotFoundEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SlotFoundEventResponse typedResponse = new SlotFoundEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.who = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.fsig = (Bytes4) eventValues.getNonIndexedValues().get(1);
            typedResponse.keysHash = (Bytes32) eventValues.getNonIndexedValues().get(2);
            typedResponse.slot = (Uint256) eventValues.getNonIndexedValues().get(3);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SlotFoundEventResponse getSlotFoundEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SLOTFOUND_EVENT, log);
        SlotFoundEventResponse typedResponse = new SlotFoundEventResponse();
        typedResponse.log = log;
        typedResponse.who = (Address) eventValues.getNonIndexedValues().get(0);
        typedResponse.fsig = (Bytes4) eventValues.getNonIndexedValues().get(1);
        typedResponse.keysHash = (Bytes32) eventValues.getNonIndexedValues().get(2);
        typedResponse.slot = (Uint256) eventValues.getNonIndexedValues().get(3);
        return typedResponse;
    }

    public Flowable<SlotFoundEventResponse> slotFoundEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSlotFoundEventFromLog(log));
    }

    public Flowable<SlotFoundEventResponse> slotFoundEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SLOTFOUND_EVENT));
        return slotFoundEventFlowable(filter);
    }

    public static List<WARNING_UninitedSlotEventResponse> getWARNING_UninitedSlotEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(WARNING_UNINITEDSLOT_EVENT, transactionReceipt);
        ArrayList<WARNING_UninitedSlotEventResponse> responses = new ArrayList<WARNING_UninitedSlotEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WARNING_UninitedSlotEventResponse typedResponse = new WARNING_UninitedSlotEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.who = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.slot = (Uint256) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static WARNING_UninitedSlotEventResponse getWARNING_UninitedSlotEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(WARNING_UNINITEDSLOT_EVENT, log);
        WARNING_UninitedSlotEventResponse typedResponse = new WARNING_UninitedSlotEventResponse();
        typedResponse.log = log;
        typedResponse.who = (Address) eventValues.getNonIndexedValues().get(0);
        typedResponse.slot = (Uint256) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<WARNING_UninitedSlotEventResponse> wARNING_UninitedSlotEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getWARNING_UninitedSlotEventFromLog(log));
    }

    public Flowable<WARNING_UninitedSlotEventResponse> wARNING_UninitedSlotEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WARNING_UNINITEDSLOT_EVENT));
        return wARNING_UninitedSlotEventFlowable(filter);
    }

    public static List<EntityCreatedEventResponse> getEntityCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ENTITYCREATED_EVENT, transactionReceipt);
        ArrayList<EntityCreatedEventResponse> responses = new ArrayList<EntityCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EntityCreatedEventResponse typedResponse = new EntityCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.entityId = (Bytes32) eventValues.getIndexedValues().get(0);
            typedResponse.entityAdmin = (Bytes32) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static EntityCreatedEventResponse getEntityCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ENTITYCREATED_EVENT, log);
        EntityCreatedEventResponse typedResponse = new EntityCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.entityId = (Bytes32) eventValues.getIndexedValues().get(0);
        typedResponse.entityAdmin = (Bytes32) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<EntityCreatedEventResponse> entityCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getEntityCreatedEventFromLog(log));
    }

    public Flowable<EntityCreatedEventResponse> entityCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ENTITYCREATED_EVENT));
        return entityCreatedEventFlowable(filter);
    }

    public static List<RoleUpdatedEventResponse> getRoleUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ROLEUPDATED_EVENT, transactionReceipt);
        ArrayList<RoleUpdatedEventResponse> responses = new ArrayList<RoleUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleUpdatedEventResponse typedResponse = new RoleUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.objectId = (Bytes32) eventValues.getIndexedValues().get(0);
            typedResponse.contextId = (Bytes32) eventValues.getNonIndexedValues().get(0);
            typedResponse.assignedRoleId = (Bytes32) eventValues.getNonIndexedValues().get(1);
            typedResponse.functionName = (Utf8String) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RoleUpdatedEventResponse getRoleUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEUPDATED_EVENT, log);
        RoleUpdatedEventResponse typedResponse = new RoleUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.objectId = (Bytes32) eventValues.getIndexedValues().get(0);
        typedResponse.contextId = (Bytes32) eventValues.getNonIndexedValues().get(0);
        typedResponse.assignedRoleId = (Bytes32) eventValues.getNonIndexedValues().get(1);
        typedResponse.functionName = (Utf8String) eventValues.getNonIndexedValues().get(2);
        return typedResponse;
    }

    public Flowable<RoleUpdatedEventResponse> roleUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRoleUpdatedEventFromLog(log));
    }

    public Flowable<RoleUpdatedEventResponse> roleUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEUPDATED_EVENT));
        return roleUpdatedEventFlowable(filter);
    }

    public static List<TokenWrappedEventResponse> getTokenWrappedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TOKENWRAPPED_EVENT, transactionReceipt);
        ArrayList<TokenWrappedEventResponse> responses = new ArrayList<TokenWrappedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TokenWrappedEventResponse typedResponse = new TokenWrappedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.entityId = (Bytes32) eventValues.getIndexedValues().get(0);
            typedResponse.tokenWrapper = (Address) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TokenWrappedEventResponse getTokenWrappedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TOKENWRAPPED_EVENT, log);
        TokenWrappedEventResponse typedResponse = new TokenWrappedEventResponse();
        typedResponse.log = log;
        typedResponse.entityId = (Bytes32) eventValues.getIndexedValues().get(0);
        typedResponse.tokenWrapper = (Address) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<TokenWrappedEventResponse> tokenWrappedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTokenWrappedEventFromLog(log));
    }

    public Flowable<TokenWrappedEventResponse> tokenWrappedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENWRAPPED_EVENT));
        return tokenWrappedEventFlowable(filter);
    }

    public static List<FeePaidEventResponse> getFeePaidEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FEEPAID_EVENT, transactionReceipt);
        ArrayList<FeePaidEventResponse> responses = new ArrayList<FeePaidEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FeePaidEventResponse typedResponse = new FeePaidEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.fromId = (Bytes32) eventValues.getIndexedValues().get(0);
            typedResponse.toId = (Bytes32) eventValues.getIndexedValues().get(1);
            typedResponse.tokenId = (Bytes32) eventValues.getNonIndexedValues().get(0);
            typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(1);
            typedResponse.feeType = (Uint256) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FeePaidEventResponse getFeePaidEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FEEPAID_EVENT, log);
        FeePaidEventResponse typedResponse = new FeePaidEventResponse();
        typedResponse.log = log;
        typedResponse.fromId = (Bytes32) eventValues.getIndexedValues().get(0);
        typedResponse.toId = (Bytes32) eventValues.getIndexedValues().get(1);
        typedResponse.tokenId = (Bytes32) eventValues.getNonIndexedValues().get(0);
        typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(1);
        typedResponse.feeType = (Uint256) eventValues.getNonIndexedValues().get(2);
        return typedResponse;
    }

    public Flowable<FeePaidEventResponse> feePaidEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFeePaidEventFromLog(log));
    }

    public Flowable<FeePaidEventResponse> feePaidEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FEEPAID_EVENT));
        return feePaidEventFlowable(filter);
    }

    public static List<SimplePolicyCancelledEventResponse> getSimplePolicyCancelledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SIMPLEPOLICYCANCELLED_EVENT, transactionReceipt);
        ArrayList<SimplePolicyCancelledEventResponse> responses = new ArrayList<SimplePolicyCancelledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SimplePolicyCancelledEventResponse typedResponse = new SimplePolicyCancelledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (Bytes32) eventValues.getIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SimplePolicyCancelledEventResponse getSimplePolicyCancelledEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SIMPLEPOLICYCANCELLED_EVENT, log);
        SimplePolicyCancelledEventResponse typedResponse = new SimplePolicyCancelledEventResponse();
        typedResponse.log = log;
        typedResponse.id = (Bytes32) eventValues.getIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<SimplePolicyCancelledEventResponse> simplePolicyCancelledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSimplePolicyCancelledEventFromLog(log));
    }

    public Flowable<SimplePolicyCancelledEventResponse> simplePolicyCancelledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SIMPLEPOLICYCANCELLED_EVENT));
        return simplePolicyCancelledEventFlowable(filter);
    }

    public static List<SimplePolicyClaimPaidEventResponse> getSimplePolicyClaimPaidEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SIMPLEPOLICYCLAIMPAID_EVENT, transactionReceipt);
        ArrayList<SimplePolicyClaimPaidEventResponse> responses = new ArrayList<SimplePolicyClaimPaidEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SimplePolicyClaimPaidEventResponse typedResponse = new SimplePolicyClaimPaidEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._claimId = (Bytes32) eventValues.getIndexedValues().get(0);
            typedResponse.policyId = (Bytes32) eventValues.getIndexedValues().get(1);
            typedResponse.insuredId = (Bytes32) eventValues.getIndexedValues().get(2);
            typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SimplePolicyClaimPaidEventResponse getSimplePolicyClaimPaidEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SIMPLEPOLICYCLAIMPAID_EVENT, log);
        SimplePolicyClaimPaidEventResponse typedResponse = new SimplePolicyClaimPaidEventResponse();
        typedResponse.log = log;
        typedResponse._claimId = (Bytes32) eventValues.getIndexedValues().get(0);
        typedResponse.policyId = (Bytes32) eventValues.getIndexedValues().get(1);
        typedResponse.insuredId = (Bytes32) eventValues.getIndexedValues().get(2);
        typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<SimplePolicyClaimPaidEventResponse> simplePolicyClaimPaidEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSimplePolicyClaimPaidEventFromLog(log));
    }

    public Flowable<SimplePolicyClaimPaidEventResponse> simplePolicyClaimPaidEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SIMPLEPOLICYCLAIMPAID_EVENT));
        return simplePolicyClaimPaidEventFlowable(filter);
    }

    public static List<SimplePolicyMaturedEventResponse> getSimplePolicyMaturedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SIMPLEPOLICYMATURED_EVENT, transactionReceipt);
        ArrayList<SimplePolicyMaturedEventResponse> responses = new ArrayList<SimplePolicyMaturedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SimplePolicyMaturedEventResponse typedResponse = new SimplePolicyMaturedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (Bytes32) eventValues.getIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SimplePolicyMaturedEventResponse getSimplePolicyMaturedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SIMPLEPOLICYMATURED_EVENT, log);
        SimplePolicyMaturedEventResponse typedResponse = new SimplePolicyMaturedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (Bytes32) eventValues.getIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<SimplePolicyMaturedEventResponse> simplePolicyMaturedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSimplePolicyMaturedEventFromLog(log));
    }

    public Flowable<SimplePolicyMaturedEventResponse> simplePolicyMaturedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SIMPLEPOLICYMATURED_EVENT));
        return simplePolicyMaturedEventFlowable(filter);
    }

    public static List<SimplePolicyPremiumPaidEventResponse> getSimplePolicyPremiumPaidEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SIMPLEPOLICYPREMIUMPAID_EVENT, transactionReceipt);
        ArrayList<SimplePolicyPremiumPaidEventResponse> responses = new ArrayList<SimplePolicyPremiumPaidEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SimplePolicyPremiumPaidEventResponse typedResponse = new SimplePolicyPremiumPaidEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (Bytes32) eventValues.getIndexedValues().get(0);
            typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SimplePolicyPremiumPaidEventResponse getSimplePolicyPremiumPaidEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SIMPLEPOLICYPREMIUMPAID_EVENT, log);
        SimplePolicyPremiumPaidEventResponse typedResponse = new SimplePolicyPremiumPaidEventResponse();
        typedResponse.log = log;
        typedResponse.id = (Bytes32) eventValues.getIndexedValues().get(0);
        typedResponse.amount = (Uint256) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<SimplePolicyPremiumPaidEventResponse> simplePolicyPremiumPaidEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSimplePolicyPremiumPaidEventFromLog(log));
    }

    public Flowable<SimplePolicyPremiumPaidEventResponse> simplePolicyPremiumPaidEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SIMPLEPOLICYPREMIUMPAID_EVENT));
        return simplePolicyPremiumPaidEventFlowable(filter);
    }

    public static List<DiamondCutEventResponse> getDiamondCutEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DIAMONDCUT_EVENT, transactionReceipt);
        ArrayList<DiamondCutEventResponse> responses = new ArrayList<DiamondCutEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DiamondCutEventResponse typedResponse = new DiamondCutEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._diamondCut = (List<FacetCut>) eventValues.getNonIndexedValues().get(0);
            typedResponse._init = (Address) eventValues.getNonIndexedValues().get(1);
            typedResponse._calldata = (DynamicBytes) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DiamondCutEventResponse getDiamondCutEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DIAMONDCUT_EVENT, log);
        DiamondCutEventResponse typedResponse = new DiamondCutEventResponse();
        typedResponse.log = log;
        typedResponse._diamondCut = (List<FacetCut>) eventValues.getNonIndexedValues().get(0);
        typedResponse._init = (Address) eventValues.getNonIndexedValues().get(1);
        typedResponse._calldata = (DynamicBytes) eventValues.getNonIndexedValues().get(2);
        return typedResponse;
    }

    public Flowable<DiamondCutEventResponse> diamondCutEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDiamondCutEventFromLog(log));
    }

    public Flowable<DiamondCutEventResponse> diamondCutEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DIAMONDCUT_EVENT));
        return diamondCutEventFlowable(filter);
    }

    public static List<RoleCanAssignUpdatedEventResponse> getRoleCanAssignUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ROLECANASSIGNUPDATED_EVENT, transactionReceipt);
        ArrayList<RoleCanAssignUpdatedEventResponse> responses = new ArrayList<RoleCanAssignUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleCanAssignUpdatedEventResponse typedResponse = new RoleCanAssignUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse.group = (Utf8String) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RoleCanAssignUpdatedEventResponse getRoleCanAssignUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLECANASSIGNUPDATED_EVENT, log);
        RoleCanAssignUpdatedEventResponse typedResponse = new RoleCanAssignUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.role = (Utf8String) eventValues.getNonIndexedValues().get(0);
        typedResponse.group = (Utf8String) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<RoleCanAssignUpdatedEventResponse> roleCanAssignUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRoleCanAssignUpdatedEventFromLog(log));
    }

    public Flowable<RoleCanAssignUpdatedEventResponse> roleCanAssignUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLECANASSIGNUPDATED_EVENT));
        return roleCanAssignUpdatedEventFlowable(filter);
    }

    public static List<RoleGroupUpdatedEventResponse> getRoleGroupUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ROLEGROUPUPDATED_EVENT, transactionReceipt);
        ArrayList<RoleGroupUpdatedEventResponse> responses = new ArrayList<RoleGroupUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleGroupUpdatedEventResponse typedResponse = new RoleGroupUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse.group = (Utf8String) eventValues.getNonIndexedValues().get(1);
            typedResponse.roleInGroup = (Bool) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RoleGroupUpdatedEventResponse getRoleGroupUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEGROUPUPDATED_EVENT, log);
        RoleGroupUpdatedEventResponse typedResponse = new RoleGroupUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.role = (Utf8String) eventValues.getNonIndexedValues().get(0);
        typedResponse.group = (Utf8String) eventValues.getNonIndexedValues().get(1);
        typedResponse.roleInGroup = (Bool) eventValues.getNonIndexedValues().get(2);
        return typedResponse;
    }

    public Flowable<RoleGroupUpdatedEventResponse> roleGroupUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRoleGroupUpdatedEventFromLog(log));
    }

    public Flowable<RoleGroupUpdatedEventResponse> roleGroupUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEGROUPUPDATED_EVENT));
        return roleGroupUpdatedEventFlowable(filter);
    }

    public static List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.operator = (Address) eventValues.getIndexedValues().get(1);
            typedResponse.approved = (Bool) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ApprovalForAllEventResponse getApprovalForAllEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, log);
        ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
        typedResponse.log = log;
        typedResponse.owner = (Address) eventValues.getIndexedValues().get(0);
        typedResponse.operator = (Address) eventValues.getIndexedValues().get(1);
        typedResponse.approved = (Bool) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getApprovalForAllEventFromLog(log));
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
        return approvalForAllEventFlowable(filter);
    }

    public static List<OrderAddedEventResponse> getOrderAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ORDERADDED_EVENT, transactionReceipt);
        ArrayList<OrderAddedEventResponse> responses = new ArrayList<OrderAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OrderAddedEventResponse typedResponse = new OrderAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.orderId = (Uint256) eventValues.getIndexedValues().get(0);
            typedResponse.maker = (Bytes32) eventValues.getIndexedValues().get(1);
            typedResponse.sellToken = (Bytes32) eventValues.getIndexedValues().get(2);
            typedResponse.sellAmount = (Uint256) eventValues.getNonIndexedValues().get(0);
            typedResponse.sellAmountInitial = (Uint256) eventValues.getNonIndexedValues().get(1);
            typedResponse.buyToken = (Bytes32) eventValues.getNonIndexedValues().get(2);
            typedResponse.buyAmount = (Uint256) eventValues.getNonIndexedValues().get(3);
            typedResponse.buyAmountInitial = (Uint256) eventValues.getNonIndexedValues().get(4);
            typedResponse.state = (Uint256) eventValues.getNonIndexedValues().get(5);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OrderAddedEventResponse getOrderAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ORDERADDED_EVENT, log);
        OrderAddedEventResponse typedResponse = new OrderAddedEventResponse();
        typedResponse.log = log;
        typedResponse.orderId = (Uint256) eventValues.getIndexedValues().get(0);
        typedResponse.maker = (Bytes32) eventValues.getIndexedValues().get(1);
        typedResponse.sellToken = (Bytes32) eventValues.getIndexedValues().get(2);
        typedResponse.sellAmount = (Uint256) eventValues.getNonIndexedValues().get(0);
        typedResponse.sellAmountInitial = (Uint256) eventValues.getNonIndexedValues().get(1);
        typedResponse.buyToken = (Bytes32) eventValues.getNonIndexedValues().get(2);
        typedResponse.buyAmount = (Uint256) eventValues.getNonIndexedValues().get(3);
        typedResponse.buyAmountInitial = (Uint256) eventValues.getNonIndexedValues().get(4);
        typedResponse.state = (Uint256) eventValues.getNonIndexedValues().get(5);
        return typedResponse;
    }

    public Flowable<OrderAddedEventResponse> orderAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOrderAddedEventFromLog(log));
    }

    public Flowable<OrderAddedEventResponse> orderAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORDERADDED_EVENT));
        return orderAddedEventFlowable(filter);
    }

    public static List<OrderCancelledEventResponse> getOrderCancelledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ORDERCANCELLED_EVENT, transactionReceipt);
        ArrayList<OrderCancelledEventResponse> responses = new ArrayList<OrderCancelledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OrderCancelledEventResponse typedResponse = new OrderCancelledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.orderId = (Uint256) eventValues.getIndexedValues().get(0);
            typedResponse.taker = (Bytes32) eventValues.getIndexedValues().get(1);
            typedResponse.sellToken = (Bytes32) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OrderCancelledEventResponse getOrderCancelledEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ORDERCANCELLED_EVENT, log);
        OrderCancelledEventResponse typedResponse = new OrderCancelledEventResponse();
        typedResponse.log = log;
        typedResponse.orderId = (Uint256) eventValues.getIndexedValues().get(0);
        typedResponse.taker = (Bytes32) eventValues.getIndexedValues().get(1);
        typedResponse.sellToken = (Bytes32) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<OrderCancelledEventResponse> orderCancelledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOrderCancelledEventFromLog(log));
    }

    public Flowable<OrderCancelledEventResponse> orderCancelledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORDERCANCELLED_EVENT));
        return orderCancelledEventFlowable(filter);
    }

    public static List<OrderExecutedEventResponse> getOrderExecutedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ORDEREXECUTED_EVENT, transactionReceipt);
        ArrayList<OrderExecutedEventResponse> responses = new ArrayList<OrderExecutedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OrderExecutedEventResponse typedResponse = new OrderExecutedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.orderId = (Uint256) eventValues.getIndexedValues().get(0);
            typedResponse.taker = (Bytes32) eventValues.getIndexedValues().get(1);
            typedResponse.sellToken = (Bytes32) eventValues.getIndexedValues().get(2);
            typedResponse.sellAmount = (Uint256) eventValues.getNonIndexedValues().get(0);
            typedResponse.buyToken = (Bytes32) eventValues.getNonIndexedValues().get(1);
            typedResponse.buyAmount = (Uint256) eventValues.getNonIndexedValues().get(2);
            typedResponse.state = (Uint256) eventValues.getNonIndexedValues().get(3);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OrderExecutedEventResponse getOrderExecutedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ORDEREXECUTED_EVENT, log);
        OrderExecutedEventResponse typedResponse = new OrderExecutedEventResponse();
        typedResponse.log = log;
        typedResponse.orderId = (Uint256) eventValues.getIndexedValues().get(0);
        typedResponse.taker = (Bytes32) eventValues.getIndexedValues().get(1);
        typedResponse.sellToken = (Bytes32) eventValues.getIndexedValues().get(2);
        typedResponse.sellAmount = (Uint256) eventValues.getNonIndexedValues().get(0);
        typedResponse.buyToken = (Bytes32) eventValues.getNonIndexedValues().get(1);
        typedResponse.buyAmount = (Uint256) eventValues.getNonIndexedValues().get(2);
        typedResponse.state = (Uint256) eventValues.getNonIndexedValues().get(3);
        return typedResponse;
    }

    public Flowable<OrderExecutedEventResponse> orderExecutedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOrderExecutedEventFromLog(log));
    }

    public Flowable<OrderExecutedEventResponse> orderExecutedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORDEREXECUTED_EVENT));
        return orderExecutedEventFlowable(filter);
    }

    public static List<TokenInfoUpdatedEventResponse> getTokenInfoUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TOKENINFOUPDATED_EVENT, transactionReceipt);
        ArrayList<TokenInfoUpdatedEventResponse> responses = new ArrayList<TokenInfoUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TokenInfoUpdatedEventResponse typedResponse = new TokenInfoUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.objectId = (Bytes32) eventValues.getIndexedValues().get(0);
            typedResponse.symbol = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse.name = (Utf8String) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TokenInfoUpdatedEventResponse getTokenInfoUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TOKENINFOUPDATED_EVENT, log);
        TokenInfoUpdatedEventResponse typedResponse = new TokenInfoUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.objectId = (Bytes32) eventValues.getIndexedValues().get(0);
        typedResponse.symbol = (Utf8String) eventValues.getNonIndexedValues().get(0);
        typedResponse.name = (Utf8String) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<TokenInfoUpdatedEventResponse> tokenInfoUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTokenInfoUpdatedEventFromLog(log));
    }

    public Flowable<TokenInfoUpdatedEventResponse> tokenInfoUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENINFOUPDATED_EVENT));
        return tokenInfoUpdatedEventFlowable(filter);
    }

    public static List<TokenizationEnabledEventResponse> getTokenizationEnabledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TOKENIZATIONENABLED_EVENT, transactionReceipt);
        ArrayList<TokenizationEnabledEventResponse> responses = new ArrayList<TokenizationEnabledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TokenizationEnabledEventResponse typedResponse = new TokenizationEnabledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.objectId = (Bytes32) eventValues.getNonIndexedValues().get(0);
            typedResponse.tokenSymbol = (Utf8String) eventValues.getNonIndexedValues().get(1);
            typedResponse.tokenName = (Utf8String) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TokenizationEnabledEventResponse getTokenizationEnabledEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TOKENIZATIONENABLED_EVENT, log);
        TokenizationEnabledEventResponse typedResponse = new TokenizationEnabledEventResponse();
        typedResponse.log = log;
        typedResponse.objectId = (Bytes32) eventValues.getNonIndexedValues().get(0);
        typedResponse.tokenSymbol = (Utf8String) eventValues.getNonIndexedValues().get(1);
        typedResponse.tokenName = (Utf8String) eventValues.getNonIndexedValues().get(2);
        return typedResponse;
    }

    public Flowable<TokenizationEnabledEventResponse> tokenizationEnabledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTokenizationEnabledEventFromLog(log));
    }

    public Flowable<TokenizationEnabledEventResponse> tokenizationEnabledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENIZATIONENABLED_EVENT));
        return tokenizationEnabledEventFlowable(filter);
    }

    public static List<FeeScheduleAddedEventResponse> getFeeScheduleAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FEESCHEDULEADDED_EVENT, transactionReceipt);
        ArrayList<FeeScheduleAddedEventResponse> responses = new ArrayList<FeeScheduleAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FeeScheduleAddedEventResponse typedResponse = new FeeScheduleAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._entityId = (Bytes32) eventValues.getNonIndexedValues().get(0);
            typedResponse._feeType = (Uint256) eventValues.getNonIndexedValues().get(1);
            typedResponse.feeSchedule = (FeeSchedule) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FeeScheduleAddedEventResponse getFeeScheduleAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FEESCHEDULEADDED_EVENT, log);
        FeeScheduleAddedEventResponse typedResponse = new FeeScheduleAddedEventResponse();
        typedResponse.log = log;
        typedResponse._entityId = (Bytes32) eventValues.getNonIndexedValues().get(0);
        typedResponse._feeType = (Uint256) eventValues.getNonIndexedValues().get(1);
        typedResponse.feeSchedule = (FeeSchedule) eventValues.getNonIndexedValues().get(2);
        return typedResponse;
    }

    public Flowable<FeeScheduleAddedEventResponse> feeScheduleAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFeeScheduleAddedEventFromLog(log));
    }

    public Flowable<FeeScheduleAddedEventResponse> feeScheduleAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FEESCHEDULEADDED_EVENT));
        return feeScheduleAddedEventFlowable(filter);
    }

    public static List<MakerBasisPointsUpdatedEventResponse> getMakerBasisPointsUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MAKERBASISPOINTSUPDATED_EVENT, transactionReceipt);
        ArrayList<MakerBasisPointsUpdatedEventResponse> responses = new ArrayList<MakerBasisPointsUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MakerBasisPointsUpdatedEventResponse typedResponse = new MakerBasisPointsUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tradingCommissionMakerBP = (Uint16) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MakerBasisPointsUpdatedEventResponse getMakerBasisPointsUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MAKERBASISPOINTSUPDATED_EVENT, log);
        MakerBasisPointsUpdatedEventResponse typedResponse = new MakerBasisPointsUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.tradingCommissionMakerBP = (Uint16) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<MakerBasisPointsUpdatedEventResponse> makerBasisPointsUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMakerBasisPointsUpdatedEventFromLog(log));
    }

    public Flowable<MakerBasisPointsUpdatedEventResponse> makerBasisPointsUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MAKERBASISPOINTSUPDATED_EVENT));
        return makerBasisPointsUpdatedEventFlowable(filter);
    }

    public static List<CollateralRatioUpdatedEventResponse> getCollateralRatioUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(COLLATERALRATIOUPDATED_EVENT, transactionReceipt);
        ArrayList<CollateralRatioUpdatedEventResponse> responses = new ArrayList<CollateralRatioUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CollateralRatioUpdatedEventResponse typedResponse = new CollateralRatioUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.entityId = (Bytes32) eventValues.getIndexedValues().get(0);
            typedResponse.collateralRatio = (Uint256) eventValues.getNonIndexedValues().get(0);
            typedResponse.utilizedCapacity = (Uint256) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CollateralRatioUpdatedEventResponse getCollateralRatioUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(COLLATERALRATIOUPDATED_EVENT, log);
        CollateralRatioUpdatedEventResponse typedResponse = new CollateralRatioUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.entityId = (Bytes32) eventValues.getIndexedValues().get(0);
        typedResponse.collateralRatio = (Uint256) eventValues.getNonIndexedValues().get(0);
        typedResponse.utilizedCapacity = (Uint256) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<CollateralRatioUpdatedEventResponse> collateralRatioUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCollateralRatioUpdatedEventFromLog(log));
    }

    public Flowable<CollateralRatioUpdatedEventResponse> collateralRatioUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(COLLATERALRATIOUPDATED_EVENT));
        return collateralRatioUpdatedEventFlowable(filter);
    }

    public static List<EntityUpdatedEventResponse> getEntityUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ENTITYUPDATED_EVENT, transactionReceipt);
        ArrayList<EntityUpdatedEventResponse> responses = new ArrayList<EntityUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EntityUpdatedEventResponse typedResponse = new EntityUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.entityId = (Bytes32) eventValues.getIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static EntityUpdatedEventResponse getEntityUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ENTITYUPDATED_EVENT, log);
        EntityUpdatedEventResponse typedResponse = new EntityUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.entityId = (Bytes32) eventValues.getIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<EntityUpdatedEventResponse> entityUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getEntityUpdatedEventFromLog(log));
    }

    public Flowable<EntityUpdatedEventResponse> entityUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ENTITYUPDATED_EVENT));
        return entityUpdatedEventFlowable(filter);
    }

    public static List<SimplePolicyCreatedEventResponse> getSimplePolicyCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SIMPLEPOLICYCREATED_EVENT, transactionReceipt);
        ArrayList<SimplePolicyCreatedEventResponse> responses = new ArrayList<SimplePolicyCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SimplePolicyCreatedEventResponse typedResponse = new SimplePolicyCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (Bytes32) eventValues.getIndexedValues().get(0);
            typedResponse.entityId = (Bytes32) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SimplePolicyCreatedEventResponse getSimplePolicyCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SIMPLEPOLICYCREATED_EVENT, log);
        SimplePolicyCreatedEventResponse typedResponse = new SimplePolicyCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (Bytes32) eventValues.getIndexedValues().get(0);
        typedResponse.entityId = (Bytes32) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<SimplePolicyCreatedEventResponse> simplePolicyCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSimplePolicyCreatedEventFromLog(log));
    }

    public Flowable<SimplePolicyCreatedEventResponse> simplePolicyCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SIMPLEPOLICYCREATED_EVENT));
        return simplePolicyCreatedEventFlowable(filter);
    }

    public static List<TokenSaleStartedEventResponse> getTokenSaleStartedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TOKENSALESTARTED_EVENT, transactionReceipt);
        ArrayList<TokenSaleStartedEventResponse> responses = new ArrayList<TokenSaleStartedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TokenSaleStartedEventResponse typedResponse = new TokenSaleStartedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.entityId = (Bytes32) eventValues.getIndexedValues().get(0);
            typedResponse.offerId = (Uint256) eventValues.getNonIndexedValues().get(0);
            typedResponse.tokenSymbol = (Utf8String) eventValues.getNonIndexedValues().get(1);
            typedResponse.tokenName = (Utf8String) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TokenSaleStartedEventResponse getTokenSaleStartedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TOKENSALESTARTED_EVENT, log);
        TokenSaleStartedEventResponse typedResponse = new TokenSaleStartedEventResponse();
        typedResponse.log = log;
        typedResponse.entityId = (Bytes32) eventValues.getIndexedValues().get(0);
        typedResponse.offerId = (Uint256) eventValues.getNonIndexedValues().get(0);
        typedResponse.tokenSymbol = (Utf8String) eventValues.getNonIndexedValues().get(1);
        typedResponse.tokenName = (Utf8String) eventValues.getNonIndexedValues().get(2);
        return typedResponse;
    }

    public Flowable<TokenSaleStartedEventResponse> tokenSaleStartedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTokenSaleStartedEventFromLog(log));
    }

    public Flowable<TokenSaleStartedEventResponse> tokenSaleStartedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENSALESTARTED_EVENT));
        return tokenSaleStartedEventFlowable(filter);
    }

    public static List<FunctionsLockedEventResponse> getFunctionsLockedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FUNCTIONSLOCKED_EVENT, transactionReceipt);
        ArrayList<FunctionsLockedEventResponse> responses = new ArrayList<FunctionsLockedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FunctionsLockedEventResponse typedResponse = new FunctionsLockedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.functionSelectors = (DynamicArray<Bytes4>) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FunctionsLockedEventResponse getFunctionsLockedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FUNCTIONSLOCKED_EVENT, log);
        FunctionsLockedEventResponse typedResponse = new FunctionsLockedEventResponse();
        typedResponse.log = log;
        typedResponse.functionSelectors = (DynamicArray<Bytes4>) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<FunctionsLockedEventResponse> functionsLockedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFunctionsLockedEventFromLog(log));
    }

    public Flowable<FunctionsLockedEventResponse> functionsLockedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FUNCTIONSLOCKED_EVENT));
        return functionsLockedEventFlowable(filter);
    }

    public static List<FunctionsUnlockedEventResponse> getFunctionsUnlockedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FUNCTIONSUNLOCKED_EVENT, transactionReceipt);
        ArrayList<FunctionsUnlockedEventResponse> responses = new ArrayList<FunctionsUnlockedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FunctionsUnlockedEventResponse typedResponse = new FunctionsUnlockedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.functionSelectors = (DynamicArray<Bytes4>) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FunctionsUnlockedEventResponse getFunctionsUnlockedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FUNCTIONSUNLOCKED_EVENT, log);
        FunctionsUnlockedEventResponse typedResponse = new FunctionsUnlockedEventResponse();
        typedResponse.log = log;
        typedResponse.functionSelectors = (DynamicArray<Bytes4>) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<FunctionsUnlockedEventResponse> functionsUnlockedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFunctionsUnlockedEventFromLog(log));
    }

    public Flowable<FunctionsUnlockedEventResponse> functionsUnlockedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FUNCTIONSUNLOCKED_EVENT));
        return functionsUnlockedEventFlowable(filter);
    }

    public static List<MaxDividendDenominationsUpdatedEventResponse> getMaxDividendDenominationsUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MAXDIVIDENDDENOMINATIONSUPDATED_EVENT, transactionReceipt);
        ArrayList<MaxDividendDenominationsUpdatedEventResponse> responses = new ArrayList<MaxDividendDenominationsUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MaxDividendDenominationsUpdatedEventResponse typedResponse = new MaxDividendDenominationsUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldMax = (Uint8) eventValues.getNonIndexedValues().get(0);
            typedResponse.newMax = (Uint8) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MaxDividendDenominationsUpdatedEventResponse getMaxDividendDenominationsUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MAXDIVIDENDDENOMINATIONSUPDATED_EVENT, log);
        MaxDividendDenominationsUpdatedEventResponse typedResponse = new MaxDividendDenominationsUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.oldMax = (Uint8) eventValues.getNonIndexedValues().get(0);
        typedResponse.newMax = (Uint8) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<MaxDividendDenominationsUpdatedEventResponse> maxDividendDenominationsUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMaxDividendDenominationsUpdatedEventFromLog(log));
    }

    public Flowable<MaxDividendDenominationsUpdatedEventResponse> maxDividendDenominationsUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MAXDIVIDENDDENOMINATIONSUPDATED_EVENT));
        return maxDividendDenominationsUpdatedEventFlowable(filter);
    }

    public static List<SupportedTokenAddedEventResponse> getSupportedTokenAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SUPPORTEDTOKENADDED_EVENT, transactionReceipt);
        ArrayList<SupportedTokenAddedEventResponse> responses = new ArrayList<SupportedTokenAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SupportedTokenAddedEventResponse typedResponse = new SupportedTokenAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenAddress = (Address) eventValues.getIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SupportedTokenAddedEventResponse getSupportedTokenAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SUPPORTEDTOKENADDED_EVENT, log);
        SupportedTokenAddedEventResponse typedResponse = new SupportedTokenAddedEventResponse();
        typedResponse.log = log;
        typedResponse.tokenAddress = (Address) eventValues.getIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<SupportedTokenAddedEventResponse> supportedTokenAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSupportedTokenAddedEventFromLog(log));
    }

    public Flowable<SupportedTokenAddedEventResponse> supportedTokenAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SUPPORTEDTOKENADDED_EVENT));
        return supportedTokenAddedEventFlowable(filter);
    }

    public static List<InitializeDiamondEventResponse> getInitializeDiamondEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(INITIALIZEDIAMOND_EVENT, transactionReceipt);
        ArrayList<InitializeDiamondEventResponse> responses = new ArrayList<InitializeDiamondEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            InitializeDiamondEventResponse typedResponse = new InitializeDiamondEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (Address) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static InitializeDiamondEventResponse getInitializeDiamondEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(INITIALIZEDIAMOND_EVENT, log);
        InitializeDiamondEventResponse typedResponse = new InitializeDiamondEventResponse();
        typedResponse.log = log;
        typedResponse.sender = (Address) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<InitializeDiamondEventResponse> initializeDiamondEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getInitializeDiamondEventFromLog(log));
    }

    public Flowable<InitializeDiamondEventResponse> initializeDiamondEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INITIALIZEDIAMOND_EVENT));
        return initializeDiamondEventFlowable(filter);
    }

    public static List<CreateUpgradeEventResponse> getCreateUpgradeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CREATEUPGRADE_EVENT, transactionReceipt);
        ArrayList<CreateUpgradeEventResponse> responses = new ArrayList<CreateUpgradeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CreateUpgradeEventResponse typedResponse = new CreateUpgradeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.who = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.id = (Bytes32) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CreateUpgradeEventResponse getCreateUpgradeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CREATEUPGRADE_EVENT, log);
        CreateUpgradeEventResponse typedResponse = new CreateUpgradeEventResponse();
        typedResponse.log = log;
        typedResponse.who = (Address) eventValues.getIndexedValues().get(0);
        typedResponse.id = (Bytes32) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<CreateUpgradeEventResponse> createUpgradeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCreateUpgradeEventFromLog(log));
    }

    public Flowable<CreateUpgradeEventResponse> createUpgradeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CREATEUPGRADE_EVENT));
        return createUpgradeEventFlowable(filter);
    }

    public static List<UpdateUpgradeExpirationEventResponse> getUpdateUpgradeExpirationEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(UPDATEUPGRADEEXPIRATION_EVENT, transactionReceipt);
        ArrayList<UpdateUpgradeExpirationEventResponse> responses = new ArrayList<UpdateUpgradeExpirationEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UpdateUpgradeExpirationEventResponse typedResponse = new UpdateUpgradeExpirationEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.duration = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static UpdateUpgradeExpirationEventResponse getUpdateUpgradeExpirationEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(UPDATEUPGRADEEXPIRATION_EVENT, log);
        UpdateUpgradeExpirationEventResponse typedResponse = new UpdateUpgradeExpirationEventResponse();
        typedResponse.log = log;
        typedResponse.duration = (Uint256) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<UpdateUpgradeExpirationEventResponse> updateUpgradeExpirationEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getUpdateUpgradeExpirationEventFromLog(log));
    }

    public Flowable<UpdateUpgradeExpirationEventResponse> updateUpgradeExpirationEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPDATEUPGRADEEXPIRATION_EVENT));
        return updateUpgradeExpirationEventFlowable(filter);
    }

    public static List<UpgradeCancelledEventResponse> getUpgradeCancelledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(UPGRADECANCELLED_EVENT, transactionReceipt);
        ArrayList<UpgradeCancelledEventResponse> responses = new ArrayList<UpgradeCancelledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UpgradeCancelledEventResponse typedResponse = new UpgradeCancelledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.who = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.id = (Bytes32) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static UpgradeCancelledEventResponse getUpgradeCancelledEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(UPGRADECANCELLED_EVENT, log);
        UpgradeCancelledEventResponse typedResponse = new UpgradeCancelledEventResponse();
        typedResponse.log = log;
        typedResponse.who = (Address) eventValues.getIndexedValues().get(0);
        typedResponse.id = (Bytes32) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<UpgradeCancelledEventResponse> upgradeCancelledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getUpgradeCancelledEventFromLog(log));
    }

    public Flowable<UpgradeCancelledEventResponse> upgradeCancelledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPGRADECANCELLED_EVENT));
        return upgradeCancelledEventFlowable(filter);
    }

    @Deprecated
    public static NaymsDiamond load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NaymsDiamond(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NaymsDiamond load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NaymsDiamond(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NaymsDiamond load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NaymsDiamond(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NaymsDiamond load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NaymsDiamond(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class FeeAllocation extends StaticStruct {
        public Bytes32 from;

        public Bytes32 to;

        public Bytes32 token;

        public Uint256 fee;

        public Uint256 basisPoints;

        public FeeAllocation(Bytes32 from, Bytes32 to, Bytes32 token, Uint256 fee, Uint256 basisPoints) {
            super(from, to, token, fee, basisPoints);
            this.from = from;
            this.to = to;
            this.token = token;
            this.fee = fee;
            this.basisPoints = basisPoints;
        }
    }

    public static class Entity extends StaticStruct {
        public Bytes32 assetId;

        public Uint256 collateralRatio;

        public Uint256 maxCapacity;

        public Uint256 utilizedCapacity;

        public Bool simplePolicyEnabled;

        public Entity(Bytes32 assetId, Uint256 collateralRatio, Uint256 maxCapacity, Uint256 utilizedCapacity, Bool simplePolicyEnabled) {
            super(assetId, collateralRatio, maxCapacity, utilizedCapacity, simplePolicyEnabled);
            this.assetId = assetId;
            this.collateralRatio = collateralRatio;
            this.maxCapacity = maxCapacity;
            this.utilizedCapacity = utilizedCapacity;
            this.simplePolicyEnabled = simplePolicyEnabled;
        }
    }

    public static class Stakeholders extends DynamicStruct {
        public DynamicArray<Bytes32> roles;

        public DynamicArray<Bytes32> entityIds;

        public DynamicArray<DynamicBytes> signatures;

        public Stakeholders(DynamicArray<Bytes32> roles, DynamicArray<Bytes32> entityIds, DynamicArray<DynamicBytes> signatures) {
            super(roles, entityIds, signatures);
            this.roles = roles;
            this.entityIds = entityIds;
            this.signatures = signatures;
        }
    }

    public static class SimplePolicy extends DynamicStruct {
        public Uint256 startDate;

        public Uint256 maturationDate;

        public Bytes32 asset;

        public Uint256 limit;

        public Bool fundsLocked;

        public Bool cancelled;

        public Uint256 claimsPaid;

        public Uint256 premiumsPaid;

        public DynamicArray<Bytes32> commissionReceivers;

        public DynamicArray<Uint256> commissionBasisPoints;

        public SimplePolicy(Uint256 startDate, Uint256 maturationDate, Bytes32 asset, Uint256 limit, Bool fundsLocked, Bool cancelled, Uint256 claimsPaid, Uint256 premiumsPaid, DynamicArray<Bytes32> commissionReceivers, DynamicArray<Uint256> commissionBasisPoints) {
            super(startDate, maturationDate, asset, limit, fundsLocked, cancelled, claimsPaid, premiumsPaid, commissionReceivers, commissionBasisPoints);
            this.startDate = startDate;
            this.maturationDate = maturationDate;
            this.asset = asset;
            this.limit = limit;
            this.fundsLocked = fundsLocked;
            this.cancelled = cancelled;
            this.claimsPaid = claimsPaid;
            this.premiumsPaid = premiumsPaid;
            this.commissionReceivers = commissionReceivers;
            this.commissionBasisPoints = commissionBasisPoints;
        }
    }

    public static class FeeSchedule extends DynamicStruct {
        public DynamicArray<Bytes32> receiver;

        public DynamicArray<Uint16> basisPoints;

        public FeeSchedule(DynamicArray<Bytes32> receiver, DynamicArray<Uint16> basisPoints) {
            super(receiver, basisPoints);
            this.receiver = receiver;
            this.basisPoints = basisPoints;
        }
    }

    public static class MarketInfo extends StaticStruct {
        public Bytes32 creator;

        public Bytes32 sellToken;

        public Uint256 sellAmount;

        public Uint256 sellAmountInitial;

        public Bytes32 buyToken;

        public Uint256 buyAmount;

        public Uint256 buyAmountInitial;

        public Uint256 feeSchedule;

        public Uint256 state;

        public Uint256 rankNext;

        public Uint256 rankPrev;

        public MarketInfo(Bytes32 creator, Bytes32 sellToken, Uint256 sellAmount, Uint256 sellAmountInitial, Bytes32 buyToken, Uint256 buyAmount, Uint256 buyAmountInitial, Uint256 feeSchedule, Uint256 state, Uint256 rankNext, Uint256 rankPrev) {
            super(creator, sellToken, sellAmount, sellAmountInitial, buyToken, buyAmount, buyAmountInitial, feeSchedule, state, rankNext, rankPrev);
            this.creator = creator;
            this.sellToken = sellToken;
            this.sellAmount = sellAmount;
            this.sellAmountInitial = sellAmountInitial;
            this.buyToken = buyToken;
            this.buyAmount = buyAmount;
            this.buyAmountInitial = buyAmountInitial;
            this.feeSchedule = feeSchedule;
            this.state = state;
            this.rankNext = rankNext;
            this.rankPrev = rankPrev;
        }
    }

    public static class SimplePolicyInfo extends StaticStruct {
        public Uint256 startDate;

        public Uint256 maturationDate;

        public Bytes32 asset;

        public Uint256 limit;

        public Bool fundsLocked;

        public Bool cancelled;

        public Uint256 claimsPaid;

        public Uint256 premiumsPaid;

        public SimplePolicyInfo(Uint256 startDate, Uint256 maturationDate, Bytes32 asset, Uint256 limit, Bool fundsLocked, Bool cancelled, Uint256 claimsPaid, Uint256 premiumsPaid) {
            super(startDate, maturationDate, asset, limit, fundsLocked, cancelled, claimsPaid, premiumsPaid);
            this.startDate = startDate;
            this.maturationDate = maturationDate;
            this.asset = asset;
            this.limit = limit;
            this.fundsLocked = fundsLocked;
            this.cancelled = cancelled;
            this.claimsPaid = claimsPaid;
            this.premiumsPaid = premiumsPaid;
        }
    }

    public static class FacetCut extends DynamicStruct {
        public Address facetAddress;

        public Uint8 action;

        public DynamicArray<Bytes4> functionSelectors;

        public FacetCut(Address facetAddress, Uint8 action, DynamicArray<Bytes4> functionSelectors) {
            super(facetAddress, action, functionSelectors);
            this.facetAddress = facetAddress;
            this.action = action;
            this.functionSelectors = functionSelectors;
        }
    }

    public static class CalculatedFees extends DynamicStruct {
        public Uint256 totalFees;

        public Uint256 totalBP;

        public DynamicArray<FeeAllocation> feeAllocations;

        public CalculatedFees(Uint256 totalFees, Uint256 totalBP, DynamicArray<FeeAllocation> feeAllocations) {
            super(totalFees, totalBP, feeAllocations);
            this.totalFees = totalFees;
            this.totalBP = totalBP;
            this.feeAllocations = feeAllocations;
        }
    }

    public static class LogEventResponse extends BaseEventResponse {
        public Utf8String param0;
    }

    public static class Log_addressEventResponse extends BaseEventResponse {
        public Address param0;
    }

    public static class Log_arrayEventResponse extends BaseEventResponse {
        public DynamicArray<Address> val;
    }

    public static class Log_bytesEventResponse extends BaseEventResponse {
        public DynamicBytes param0;
    }

    public static class Log_bytes32EventResponse extends BaseEventResponse {
        public Bytes32 param0;
    }

    public static class Log_intEventResponse extends BaseEventResponse {
        public Int256 param0;
    }

    public static class Log_named_addressEventResponse extends BaseEventResponse {
        public Utf8String key;

        public Address val;
    }

    public static class Log_named_arrayEventResponse extends BaseEventResponse {
        public Utf8String key;

        public DynamicArray<Address> val;
    }

    public static class Log_named_bytesEventResponse extends BaseEventResponse {
        public Utf8String key;

        public DynamicBytes val;
    }

    public static class Log_named_bytes32EventResponse extends BaseEventResponse {
        public Utf8String key;

        public Bytes32 val;
    }

    public static class Log_named_decimal_intEventResponse extends BaseEventResponse {
        public Utf8String key;

        public Int256 val;

        public Uint256 decimals;
    }

    public static class Log_named_decimal_uintEventResponse extends BaseEventResponse {
        public Utf8String key;

        public Uint256 val;

        public Uint256 decimals;
    }

    public static class Log_named_intEventResponse extends BaseEventResponse {
        public Utf8String key;

        public Int256 val;
    }

    public static class Log_named_stringEventResponse extends BaseEventResponse {
        public Utf8String key;

        public Utf8String val;
    }

    public static class Log_named_uintEventResponse extends BaseEventResponse {
        public Utf8String key;

        public Uint256 val;
    }

    public static class Log_stringEventResponse extends BaseEventResponse {
        public Utf8String param0;
    }

    public static class Log_uintEventResponse extends BaseEventResponse {
        public Uint256 param0;
    }

    public static class LogsEventResponse extends BaseEventResponse {
        public DynamicBytes param0;
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public Address owner;

        public Address spender;

        public Uint256 value;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public Address from;

        public Address to;

        public Uint256 value;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public Address previousOwner;

        public Address newOwner;
    }

    public static class ObjectUpdatedEventResponse extends BaseEventResponse {
        public Bytes32 objectId;

        public Bytes32 parentId;

        public Bytes32 dataHash;
    }

    public static class DividendWithdrawnEventResponse extends BaseEventResponse {
        public Bytes32 accountId;

        public Bytes32 tokenId;

        public Uint256 amountOwned;

        public Bytes32 dividendTokenId;

        public Uint256 dividendAmountWithdrawn;
    }

    public static class InternalTokenBalanceUpdateEventResponse extends BaseEventResponse {
        public Bytes32 ownerId;

        public Address msgSender;

        public Bytes32 tokenId;

        public Uint256 newAmountOwned;

        public Utf8String functionName;
    }

    public static class InternalTokenSupplyUpdateEventResponse extends BaseEventResponse {
        public Bytes32 tokenId;

        public Address msgSender;

        public Uint256 newTokenSupply;

        public Utf8String functionName;
    }

    public static class DividendDistributionEventResponse extends BaseEventResponse {
        public Bytes32 guid;

        public Bytes32 from;

        public Bytes32 to;

        public Bytes32 dividendTokenId;

        public Uint256 amount;
    }

    public static class ObjectCreatedEventResponse extends BaseEventResponse {
        public Bytes32 objectId;

        public Bytes32 parentId;

        public Bytes32 dataHash;
    }

    public static class SlotFoundEventResponse extends BaseEventResponse {
        public Address who;

        public Bytes4 fsig;

        public Bytes32 keysHash;

        public Uint256 slot;
    }

    public static class WARNING_UninitedSlotEventResponse extends BaseEventResponse {
        public Address who;

        public Uint256 slot;
    }

    public static class EntityCreatedEventResponse extends BaseEventResponse {
        public Bytes32 entityId;

        public Bytes32 entityAdmin;
    }

    public static class RoleUpdatedEventResponse extends BaseEventResponse {
        public Bytes32 objectId;

        public Bytes32 contextId;

        public Bytes32 assignedRoleId;

        public Utf8String functionName;
    }

    public static class TokenWrappedEventResponse extends BaseEventResponse {
        public Bytes32 entityId;

        public Address tokenWrapper;
    }

    public static class FeePaidEventResponse extends BaseEventResponse {
        public Bytes32 fromId;

        public Bytes32 toId;

        public Bytes32 tokenId;

        public Uint256 amount;

        public Uint256 feeType;
    }

    public static class SimplePolicyCancelledEventResponse extends BaseEventResponse {
        public Bytes32 id;
    }

    public static class SimplePolicyClaimPaidEventResponse extends BaseEventResponse {
        public Bytes32 _claimId;

        public Bytes32 policyId;

        public Bytes32 insuredId;

        public Uint256 amount;
    }

    public static class SimplePolicyMaturedEventResponse extends BaseEventResponse {
        public Bytes32 id;
    }

    public static class SimplePolicyPremiumPaidEventResponse extends BaseEventResponse {
        public Bytes32 id;

        public Uint256 amount;
    }

    public static class DiamondCutEventResponse extends BaseEventResponse {
        public List<FacetCut> _diamondCut;

        public Address _init;

        public DynamicBytes _calldata;
    }

    public static class RoleCanAssignUpdatedEventResponse extends BaseEventResponse {
        public Utf8String role;

        public Utf8String group;
    }

    public static class RoleGroupUpdatedEventResponse extends BaseEventResponse {
        public Utf8String role;

        public Utf8String group;

        public Bool roleInGroup;
    }

    public static class ApprovalForAllEventResponse extends BaseEventResponse {
        public Address owner;

        public Address operator;

        public Bool approved;
    }

    public static class OrderAddedEventResponse extends BaseEventResponse {
        public Uint256 orderId;

        public Bytes32 maker;

        public Bytes32 sellToken;

        public Uint256 sellAmount;

        public Uint256 sellAmountInitial;

        public Bytes32 buyToken;

        public Uint256 buyAmount;

        public Uint256 buyAmountInitial;

        public Uint256 state;
    }

    public static class OrderCancelledEventResponse extends BaseEventResponse {
        public Uint256 orderId;

        public Bytes32 taker;

        public Bytes32 sellToken;
    }

    public static class OrderExecutedEventResponse extends BaseEventResponse {
        public Uint256 orderId;

        public Bytes32 taker;

        public Bytes32 sellToken;

        public Uint256 sellAmount;

        public Bytes32 buyToken;

        public Uint256 buyAmount;

        public Uint256 state;
    }

    public static class TokenInfoUpdatedEventResponse extends BaseEventResponse {
        public Bytes32 objectId;

        public Utf8String symbol;

        public Utf8String name;
    }

    public static class TokenizationEnabledEventResponse extends BaseEventResponse {
        public Bytes32 objectId;

        public Utf8String tokenSymbol;

        public Utf8String tokenName;
    }

    public static class FeeScheduleAddedEventResponse extends BaseEventResponse {
        public Bytes32 _entityId;

        public Uint256 _feeType;

        public FeeSchedule feeSchedule;
    }

    public static class MakerBasisPointsUpdatedEventResponse extends BaseEventResponse {
        public Uint16 tradingCommissionMakerBP;
    }

    public static class CollateralRatioUpdatedEventResponse extends BaseEventResponse {
        public Bytes32 entityId;

        public Uint256 collateralRatio;

        public Uint256 utilizedCapacity;
    }

    public static class EntityUpdatedEventResponse extends BaseEventResponse {
        public Bytes32 entityId;
    }

    public static class SimplePolicyCreatedEventResponse extends BaseEventResponse {
        public Bytes32 id;

        public Bytes32 entityId;
    }

    public static class TokenSaleStartedEventResponse extends BaseEventResponse {
        public Bytes32 entityId;

        public Uint256 offerId;

        public Utf8String tokenSymbol;

        public Utf8String tokenName;
    }

    public static class FunctionsLockedEventResponse extends BaseEventResponse {
        public DynamicArray<Bytes4> functionSelectors;
    }

    public static class FunctionsUnlockedEventResponse extends BaseEventResponse {
        public DynamicArray<Bytes4> functionSelectors;
    }

    public static class MaxDividendDenominationsUpdatedEventResponse extends BaseEventResponse {
        public Uint8 oldMax;

        public Uint8 newMax;
    }

    public static class SupportedTokenAddedEventResponse extends BaseEventResponse {
        public Address tokenAddress;
    }

    public static class InitializeDiamondEventResponse extends BaseEventResponse {
        public Address sender;
    }

    public static class CreateUpgradeEventResponse extends BaseEventResponse {
        public Address who;

        public Bytes32 id;
    }

    public static class UpdateUpgradeExpirationEventResponse extends BaseEventResponse {
        public Uint256 duration;
    }

    public static class UpgradeCancelledEventResponse extends BaseEventResponse {
        public Address who;

        public Bytes32 id;
    }
}
