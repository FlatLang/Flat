JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

#.java.class:
#       $(JC) $(JFLAGS) $(shell find src -name "*.java")

SOURCES = $(shell find src -name "*.java")
CLASSES = $(shell find src -name "*.class")

default: $(SOURCES)
		$(JC) $(JFLAGS) $(SOURCES)


REMOVABLE_CLASSES = $(shell find src/net -type d | sed 's/\(.\+\)/\1\/*.class/')#$(shell find src -name "*.class" | sed -r 's/([$$])/*/')
clean: $(CLASSES)
		rm -rf $(REMOVABLE_CLASSES)