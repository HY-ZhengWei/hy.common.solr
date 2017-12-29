

cd .\bin

rd /s/q .\org\hy\common\solr\junit

jar cvfm hy.common.solr.jar MANIFEST.MF META-INF org

copy hy.common.solr.jar ..
del /q hy.common.solr.jar
cd ..

