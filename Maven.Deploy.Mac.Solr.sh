#!/bin/sh

mvn deploy:deploy-file -Dfile=hy.common.solr.jar                              -DpomFile=./src/META-INF/maven/org/hy/common/solr/pom.xml -DrepositoryId=thirdparty -Durl=http://218.21.3.19:1481/repository/thirdparty
mvn deploy:deploy-file -Dfile=hy.common.solr-sources.jar -Dclassifier=sources -DpomFile=./src/META-INF/maven/org/hy/common/solr/pom.xml -DrepositoryId=thirdparty -Durl=http://218.21.3.19:1481/repository/thirdparty
