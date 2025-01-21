#!/bin/bash

# Запуск Consul агента
consul agent -dev -client=0.0.0.0 &

# Запуск Payara
$PAYARA_DIR/bin/asadmin start-domain --verbose 