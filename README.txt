
DEMO:

1) run the bootstrap server (runBootstrap.sh in s2pChord)

2) from the logs, get the IP address of the bootstrap server and put it into 
config/chordPeer.cfg:
bootstrap_peer=bootstrap@ip.add.re.ss:5080

3) run the DemoNam

X - publish "Temperature x"  where x is a random room among (bedroom, kitchen, livingroom, bathroom)
X - search for "Temperature x"  where x is a random room among (bedroom, kitchen, livingroom, bathroom)