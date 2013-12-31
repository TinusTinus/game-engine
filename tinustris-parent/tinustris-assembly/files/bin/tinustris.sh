#!/bin/bash
#
# Startup script for Tinustris.
# This script assumes that a valid java, version 1.7 or later, is available.

LIB_DIR=../lib

CONFIG_DIR=../etc

NATIVES_DIR=../natives

JAVA_OPTS=-Djava.library.path=${NATIVES_DIR}

JAVA=java

${JAVA} ${JAVA_OPTS} -cp ${CONFIG_DIR}:${LIB_DIR}/* nl.mvdr.tinustris.gui.Tinustris