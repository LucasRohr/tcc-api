#!/bin/bash

CHANNEL_NAME="$1"

CERTIFICATE_CHAINCODE_NAME="certificatevalidationcc"
USER_CHAINCODE_NAME="usercc"
ACCOUNT_CHAINCODE_NAME="accountcc"
CREDENTIAL_CHAINCODE_NAME="credentialcc"

CERTIFICATE_CC_SRC_PATH="/opt/gopath/src/github.com/chaincode/certificate-chaincode/java"
USER_CC_SRC_PATH="/opt/gopath/src/github.com/chaincode/user-chaincode/java"
ACCOUNT_CC_SRC_PATH="/opt/gopath/src/github.com/chaincode/account-chaincode/java"
CREDENTIAL_CC_SRC_PATH="/opt/gopath/src/github.com/chaincode/credential-chaincode/java"

CHAINCODE_LANGUAGE="java"

COUNTER=1
MAX_RETRY=10
DELAY=3
TIMEOUT=10

. scripts/peer-util.sh

createChannel() {
	setGlobals 0

	peer channel create -o orderer.example.com:7050 -c $CHANNEL_NAME -f ./channel-artifacts/channel.tx >&log.txt
	res=$?

	cat log.txt
	verifyResult $res "Channel creation failed"
	echo "===================== Channel '$CHANNEL_NAME' created ===================== "
}

joinChannel () {
	for peer in 0 1 2 3; do
	joinChannelWithRetry $peer
	echo "===================== peer${peer}.org1 joined channel '$CHANNEL_NAME' ===================== "
	sleep $DELAY
	echo
	done
}

installChaincodeOnAllPeers () {
    CHAINCODE_NAME=$1
    CHAINCODE_PATH=$2

  	for peer in 0 1 2 3; do
    installChaincode $peer $CHAINCODE_NAME $CHAINCODE_PATH
    done
}

instantiateChaincodeOnAllPeers () {
  CHAINCODE_NAME=$1

  for peer in 0 1; do
  instantiateChaincode $peer $CHAINCODE_NAME
  done
}


# =========================================


echo "Creating channel..."
createChannel

echo "Having all peers join the channel..."
joinChannel

echo "Updating anchor peers for org1..."
updateAnchorPeers 0


# === CERTIFICATE ===

echo ""
echo "===== CERTIFICATE ====="
echo ""

echo "Installing certificate chaincode on all peers..."
installChaincodeOnAllPeers $CERTIFICATE_CHAINCODE_NAME $CERTIFICATE_CC_SRC_PATH

echo "Instantiating certificate chaincode on all peers..."
instantiateChaincodeOnAllPeers $CERTIFICATE_CHAINCODE_NAME


# === USER ===

echo ""
echo "===== USER ====="
echo ""

echo "Installing user chaincode on all peers..."
installChaincodeOnAllPeers $USER_CHAINCODE_NAME $USER_CC_SRC_PATH

echo "Instantiating user chaincode on all peers..."
instantiateChaincodeOnAllPeers $USER_CHAINCODE_NAME


# === ACCOUNT ===

echo ""
echo "===== ACCOUNT ====="
echo ""

echo "Installing account chaincode on all peers..."
installChaincodeOnAllPeers $ACCOUNT_CHAINCODE_NAME $ACCOUNT_CC_SRC_PATH

echo "Instantiating account chaincode on all peers..."
instantiateChaincodeOnAllPeers $ACCOUNT_CHAINCODE_NAME


# === CREDENTIAL ===

echo ""
echo "===== CREDENTIAL ====="
echo ""

echo "Installing credential chaincode on all peers..."
installChaincodeOnAllPeers $CREDENTIAL_CHAINCODE_NAME $CREDENTIAL_CC_SRC_PATH

echo "Instantiating credential chaincode on all peers..."
instantiateChaincodeOnAllPeers $CREDENTIAL_CHAINCODE_NAME