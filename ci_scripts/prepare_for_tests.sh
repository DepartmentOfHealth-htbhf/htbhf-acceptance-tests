#!/usr/bin/env bash

check_variable_is_set(){
    if [[ -z ${!1} ]]; then
        echo "$1 must be set and non empty"
        exit 1
    fi
}

export WORKING_DIR=$(pwd)
export BIN_DIR=${WORKING_DIR}/bin
export WEB_UI_DIR=${WORKING_DIR}/tmp-web-ui

check_variable_is_set CHROMEDRIVER_VERSION
check_variable_is_set CHROMEDRIVER_URL


download_chromedriver(){
    if [[ ! -e ${BIN_DIR}/chromedriver_${CHROMEDRIVER_VERSION}.ver || ! -e ${BIN_DIR}/chromedriver ]]; then
        echo "Downloading chromedriver ${CHROMEDRIVER_VERSION} from ${CHROMEDRIVER_URL}"
        mkdir -p ${BIN_DIR}
        cd ${BIN_DIR}
        wget -q -O chromedriver.zip "${CHROMEDRIVER_URL}" && unzip -o chromedriver.zip && rm chromedriver.zip
        touch chromedriver_${CHROMEDRIVER_VERSION}.ver
        cd ${WORKING_DIR}
    fi
}


download_web_ui(){
    echo "Downloading latest release of web ui for tests"
    rm -rf ${WEB_UI_DIR}
    mkdir ${WEB_UI_DIR}
    curl -s https://api.github.com/repos/DepartmentOfHealth-htbhf/htbhf-applicant-web-ui/releases/latest \
        | grep zipball_url \
        | cut -d'"' -f4 \
        | wget -qO web-tests-tmp.zip -i -
    unzip web-tests-tmp.zip
    mv -f DepartmentOfHealth-htbhf-htbhf-applicant-web-ui-*/* ${WEB_UI_DIR}
    rm -rf DepartmentOfHealth-htbhf-htbhf-applicant-web-ui-*
    rm web-tests-tmp.zip
}

download_chromedriver
download_web_ui