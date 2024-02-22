

del /Q hy.common.solr.jar
del /Q hy.common.solr-sources.jar


call mvn clean package
cd .\target\classes

rd /s/q .\org\hy\common\solr\junit


jar cvfm hy.common.solr.jar META-INF/MANIFEST.MF META-INF org

copy hy.common.solr.jar ..\..
del /q hy.common.solr.jar
cd ..\..





cd .\src\main\java
xcopy /S ..\resources\* .
jar cvfm hy.common.solr-sources.jar META-INF\MANIFEST.MF META-INF org 
copy hy.common.solr-sources.jar ..\..\..
del /Q hy.common.solr-sources.jar
rd /s/q META-INF
cd ..\..\..

pause