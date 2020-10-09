#!/bin/bash

CHANNEL_NAME="$1"

CERTIFICATE_CC_SRC_PATH="/opt/gopath/src/github.com/chaincode/certificate-chaincode/java"
USER_CC_SRC_PATH="/opt/gopath/src/github.com/chaincode/user-chaincode/java"

CHAINCODE_LANGUAGE="java"

CERTIFICATE_CHAINCODE_NAME="certificatevalidationcc"
USER_CHAINCODE_NAME="usercc"

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

installCertificateChaincodeOnAllPeers () {
	for peer in 0 1 2 3; do
	installCertificateChaincode $peer
	done
}

instantiateCertificateChaincodeOnAllPeers(){
	for peer in 0 1; do
	instantiateCertificateChaincode $peer
	done
}

installUserChaincodeOnAllPeers () {
	for peer in 0 1 2 3; do
	installUserChaincode $peer
	done
}

instantiateUserChaincodeOnAllPeers(){
	for peer in 0 1; do
	instantiateUserChaincode $peer
	done
}

echo "Creating channel..."
createChannel

echo "Having all peers join the channel..."
joinChannel

echo "Updating anchor peers for org1..."
updateAnchorPeers 0

echo "Installing certificate chaincode on all peers..."
installCertificateChaincodeOnAllPeers

echo "Instantiating certificate chaincode on all peers..."
instantiateCertificateChaincodeOnAllPeers

echo "Installing user chaincode on all peers..."
installUserChaincodeOnAllPeers

echo "Instantiating user chaincode on all peers..."
instantiateUserChaincodeOnAllPeers