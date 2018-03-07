

cd .\bin


rd /s/q .\org\hy\common\solr\junit


jar cvfm hy.common.solr.jar MANIFEST.MF META-INF org

copy hy.common.solr.jar ..
del /q hy.common.solr.jar
cd ..





cd .\src
jar cvfm hy.common.solr-sources.jar MANIFEST.MF META-INF org 
copy hy.common.solr-sources.jar ..
del /q hy.common.solr-sources.jar
cd ..
