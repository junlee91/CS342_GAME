build:
	rm -f *.class
	clear
	javac Game_Container.java
	java Game_Container
	clear

clean: 
	rm -f *.class
	clear

test:
	rm -f *.class
	clear
	javac Jae_TestCase.java
	java Jae_TestCase
