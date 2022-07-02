echo "moving newly compiled plugin to plugin folder"
cp -f ./target/Troldehvalp-1.0-SNAPSHOT.jar ./server/plugins/Troldehvalp-1.0-SNAPSHOT.jar
cd "./server"
echo "launching server!"
java -jar paper.jar --nogui

