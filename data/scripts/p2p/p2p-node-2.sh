pushd .
cd ../../..
sbt "project scalechain-cli" "run-main io.scalechain.blockchain.cli.ScaleChainPeer -p 7644"
popd
