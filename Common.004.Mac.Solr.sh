#!/bin/sh

cd ./bin

rm -R ./org/hy/common/solr/junit

jar cvfm hy.common.solr.jar MANIFEST.MF META-INF org

cp hy.common.solr.jar ..
rm hy.common.solr.jar
cd ..
