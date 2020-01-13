#!/usr/bin/env bash

check_variable_is_set(){
    if [[ -z ${!1} ]]; then
        echo "$1 must be set and non empty"
        exit 1
    fi
}

export WORKING_DIR=$(pwd)
export BIN_DIR=${WORKING_DIR}/bin
if [[ -z "${WEB_UI_DIR}" ]]; then
  export WEB_UI_DIR=${WORKING_DIR}/tmp-web-ui
fi

check_variable_is_set NOTIFY_API_KEY

download_web_ui(){
    rm -rf ${WEB_UI_DIR}
    mkdir ${WEB_UI_DIR}
    rm -rf wget_tmp
    mkdir wget_tmp
    cd wget_tmp
    if [[ -z "${WEB_UI_BRANCH}" ]]; then
        echo "Downloading latest release of web ui for tests (WEB_UI_BRANCH environment variable is not set)"
        curl -s https://api.github.com/repos/DepartmentOfHealth-htbhf/htbhf-applicant-web-ui/releases/latest \
            | grep zipball_url \
            | cut -d'"' -f4 \
            | wget -qO web-tests-tmp.zip -i -
    else
        branch_status=$(curl --head -s -L https://github.com/DepartmentOfHealth-htbhf/htbhf-applicant-web-ui/archive/${WEB_UI_BRANCH}.zip | grep -i  "404 Not Found")
        if [[ "${branch_status}" ]]; then
          echo "Web ui branch '${WEB_UI_BRANCH}' does not exist - please edit WEB_UI_BRANCH in config.yml to set a valid branch name, or '' to use the latest release"
          exit 1
        fi
        echo "Downloading  web ui branch '${WEB_UI_BRANCH}' for tests"
        wget -qO web-tests-tmp.zip https://github.com/DepartmentOfHealth-htbhf/htbhf-applicant-web-ui/archive/${WEB_UI_BRANCH}.zip
    fi
    unzip web-tests-tmp.zip
    mv -f */* ${WEB_UI_DIR}
    cd ..
    rm -rf wget_tmp
}

start_web_ui(){
    cd ${WEB_UI_DIR}
    cp sample.env .env
    echo "Running npm install"
    npm install
    echo "Starting web app"
    npm start &
    echo "Starting session details app"
    npm run test:session-details &
    cd ${WORKING_DIR}
}

download_web_ui
start_web_ui
