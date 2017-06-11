JAVADIR=/usr/share/java
JARNAME=poof-textui
#PROJDIR=/Users/rodrigobernardo/Google\ Drive/LEIC/LEIC\ 2º\ Ano/1º\ Semestre/PO/Projecto/project/poof-core

all:
	(cd src; javac -encoding UTF-8 -cp $(JAVADIR)/po-uilib.jar:$(JAVADIR)/poof-support.jar:$(JAVADIR)/poof-core.jar `find . -name \*.java`)
	(cd src; jar cvf ../$(JARNAME).jar `find . -name \*.class -o -name \*.java`)
	(make install)

clean:
	$(RM) $(JARNAME).jar `find . -name \*.class`

install:
	/bin/cp $(JARNAME).jar $(JAVADIR)
