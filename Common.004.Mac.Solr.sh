#!/bin/sh

cd ./bin

rm -R ./org/hy/common/solr/junit

jar cvfm hy.common.solr.jar MANIFEST.MF LICENSE org

cp hy.common.solr.jar ..
rm hy.common.solr.jar
cd ..
