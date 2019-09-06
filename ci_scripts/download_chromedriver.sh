#!/usr/bin/env bash

if [[ -z "$CHROMEDRIVER_VERSION" ]]; then
  export CHROMEDRIVER_VERSION=76.0.3809.68
fi
if [[ -z "$CHROMEDRIVER_URL" ]]; then
  export CHROMEDRIVER_URL=https://chromedriver.storage.googleapis.com/${CHROMEDRIVER_VERSION}/chromedriver_linux64.zip
fi

CURR_DIR=$(pwd)
if [[ -z "$BIN_DIR" ]]; then
  BIN_DIR=${CURR_DIR}/bin
fi

if [[ ! -e ${BIN_DIR}/chromedriver_${CHROMEDRIVER_VERSION}.ver || ! -e ${BIN_DIR}/chromedriver ]]; then
  echo "Downloading chromedriver ${CHROMEDRIVER_VERSION} from ${CHROMEDRIVER_URL}"
  mkdir -p ${BIN_DIR}
  cd ${BIN_DIR}
  wget -q -O chromedriver.zip "${CHROMEDRIVER_URL}" && unzip -o chromedriver.zip && rm chromedriver.zip
  rm -rf chromedriver_*.ver
  touch chromedriver_${CHROMEDRIVER_VERSION}.ver
  cd ${CURR_DIR}
fi
