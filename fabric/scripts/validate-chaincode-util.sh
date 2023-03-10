#!/bin/bash

source scripts/peer-util.sh

CHANNEL_NAME="mychannel"
CC_SRC_PATH="/opt/gopath/src/github.com/chaincode/certificate-chaincode/java/"
CHAINCODE_LANGUAGE="java"
CHAINCODE_NAME="certificatevalidationcc"
CHAINCODE_VERSION=$1

for PEER in 0 1 2 3; do
	installChaincode $PEER $CHAINCODE_VERSION
done

peer chaincode upgrade -o orderer.example.com:7050 \
    -C $CHANNEL_NAME \
    -n $CHAINCODE_NAME \
    -v $CHAINCODE_VERSION \
    -c '{"Args":["init"]}' \
    -P "AND ('Org1MSP.peer')"
