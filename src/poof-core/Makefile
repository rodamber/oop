JAVADIR=/usr/share/java
JARNAME=poof-core

all:
	(cd src; javac -encoding UTF-8 `find . -name \*.java`)
	(cd src; jar cvf ../$(JARNAME).jar `find . -name \*.class -o -name \*.java`)
	(make install)

clean:
	$(RM) $(JARNAME).jar `find . -name \*.class`

install:
	/bin/cp $(JARNAME).jar $(JAVADIR)
