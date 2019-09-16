#!/usr/bin/env bash

# script to set feature toggles according to the local copy of htbhf-applicant-web-ui before running gradle build
# assumes that the web-ui is located at: ../htbhf-applicant-web-ui
# if this is different, set WEB_TESTS_DIR to the correct location
# source ./set_feature_toggles.sh

if [[ -z "${WEB_TESTS_DIR}" ]]; then
  WEB_TESTS_DIR=../htbhf-applicant-web-ui
fi
export FEATURE_TOGGLES=`cat "${WEB_TESTS_DIR}"/features.json`
export TAGS=$(cat ${WEB_TESTS_DIR}/features.json | grep false | sed 's/": false//g' | sed 's/"/ and not @/g' | sed "s/,//g" | sed "s/_ENABLED//g" | tr '\n' ' ')

echo "Using feature toggles: ${FEATURE_TOGGLES}"
echo "Runing tests with: -Dcucumber.options=\"--tags 'not @Ignore ${TAGS}'\""
./gradlew -Dcucumber.options="--tags 'not @Ignore ${TAGS}'" clean build