cp -f ./target/Troldehvalp-1.0-SNAPSHOT.jar ./server/plugins
echo "moving newly compiled plugin to plugin folder"
cd "./server"
echo "removing previous plugins folder"
rm -rf "plugins/Event"
echo "launching server!"
java -jar paper.jar --nogui
