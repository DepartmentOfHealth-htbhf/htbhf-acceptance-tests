#!/usr/bin/env bash

export CHROMEDRIVER_VERSION=76.0.3809.68
export CHROMEDRIVER_URL=https://chromedriver.storage.googleapis.com/76.0.3809.68/chromedriver_linux64.zip

if [[ -z "$WORKING_DIR" ]]; then
  export WORKING_DIR=$(pwd)
fi

if [[ -z "$BIN_DIR" ]]; then
  BIN_DIR=${WORKING_DIR}/bin
fi

if [[ ! -e ${BIN_DIR}/chromedriver_${CHROMEDRIVER_VERSION}.ver || ! -e ${BIN_DIR}/chromedriver ]]; then
  echo "Downloading chromedriver ${CHROMEDRIVER_VERSION} from ${CHROMEDRIVER_URL}"
  mkdir -p ${BIN_DIR}
  cd ${BIN_DIR}
  wget -q -O chromedriver.zip "${CHROMEDRIVER_URL}" && unzip -o chromedriver.zip && rm chromedriver.zip
  touch chromedriver_${CHROMEDRIVER_VERSION}.ver
  cd ${WORKING_DIR}
fi
