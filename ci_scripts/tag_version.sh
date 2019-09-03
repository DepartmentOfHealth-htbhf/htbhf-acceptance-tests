#!/bin/bash
# Identifies the new version number, creates a git tag and pushes changes back to github
# Based on http://phdesign.com.au/programming/auto-increment-project-version-from-travis/

# quit at the first error
set -e

# if this is a pull request or branch (non-master) build, then just exit
echo "CIRCLE_BRANCH=$CIRCLE_BRANCH"
if [[ "$CIRCLE_BRANCH" != "master" ]]; then
    echo "Not tagging branch build"
    exit
fi

# make sure we know about all tags
git fetch --tags -q

# Get the latest git tag (e.g. v1.2.43)
GIT_LATEST_TAG=$(git describe --tags $(git rev-list --tags --max-count=1))
# Split out the patch version
PATCH_VERSION=$(echo "$GIT_LATEST_TAG" | sed -E 's/^v?[0-9]{1,}\.[0-9]{1,}\.([0-9]{1,})$/\1/g';)
# Increment the patch version
NEW_PATCH=$(echo "$PATCH_VERSION" | awk '{printf($1+1)}')
# combine with major/minor
MAJOR_MINOR_VERSION=$(echo "$GIT_LATEST_TAG" | sed -E 's/(^v?[0-9]{1,}\.[0-9]{1,}\.)[0-9]{1,}$/\1/g';)
NEW_VERSION="${MAJOR_MINOR_VERSION}${NEW_PATCH}"

# we're currently in a detached head - checkout master so we can tag
git checkout master

# create the tag
git tag ${NEW_VERSION}

# push the new tag back to github
git config --local user.email "dhsc-htbhf-support@equalexperts.com"
git config --local user.name "ci-build"

GITHUB_REPO_SLUG=${CIRCLE_PROJECT_USERNAME}/${CIRCLE_PROJECT_REPONAME}

echo "git push https://[SECRET]@github.com/${GITHUB_REPO_SLUG}.git ${NEW_VERSION}"
git push https://${GH_WRITE_TOKEN}@github.com/${GITHUB_REPO_SLUG}.git ${NEW_VERSION}
