#!/bin/bash

# author Ghadi Shayban <gshayban@gmail.com>
#
# Used to vendor a recent release of ASM for Clojure 1.10
# Modified to avoid maintaining a committed vendor state.

set -e

ASMURI=https://gitlab.ow2.org/asm/asm.git
if [ -z ${1+x} ]
then
    echo error: provide an asm git sha / ref
    echo recent asm tags:
    git ls-remote --refs --tags "${ASMURI}" \
        | cut -f 3 -d / | tac | head -n 10
    exit 1
fi

ASMROOT=target/asmvendor
CLJASM=target/vendor/src/java/clojure/asm

mkdir -p "${ASMROOT}"
mkdir -p "${CLJASM}"

if [ ! -d "${ASMROOT}/.git" ]; then
    echo "no repo, shallow clone"
    git clone --quiet --checkout --depth 30 "${ASMURI}" "${ASMROOT}"
fi

pushd "${ASMROOT}" > /dev/null
GITREF=$(git rev-parse $1)

if [[ -z "${GITREF}" ]]; then
    git fetch --quiet origin master
    GITREF=$(git rev-parse $1)
fi

if [[ -z "${GITREF}" ]]; then
    echo "Failed to check find ref $1"
    exit 1
fi

git checkout --quiet "${GITREF}"
popd > /dev/null

git rm -r --ignore-unmatch "${CLJASM}" > /dev/null
mkdir -p "${CLJASM}" "${CLJASM}/commons"

echo "copying classes"
cp "${ASMROOT}/asm/src/main/java/org/objectweb/asm/"*.java "${CLJASM}"

for cls in GeneratorAdapter Method LocalVariablesSorter TableSwitchGenerator;
do
    cp \
        "${ASMROOT}/asm-commons/src/main/java/org/objectweb/asm/commons/${cls}.java" \
        "${CLJASM}/commons"
done

echo "renaming classes"
find "${CLJASM}" -name '*.java' -print0 | xargs -0 sed -iBAK 's/org.objectweb.asm/clojure.asm/g'
find "${CLJASM}" -name '*BAK' -delete
