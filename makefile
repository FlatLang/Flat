JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

#.java.class:
#       $(JC) $(JFLAGS) $(shell find src -name "*.java")

ifeq ($(OS),Windows_NT)
SOURCES = $(shell dir *.java /b/s)
CLASSES = $(shell dir *.class /b/s)
REMOVABLE_CLASSES = $(CLASSES)
QUOTED_CLASSES = $(patsubst %,"%",$(REMOVABLE_CLASSES))
else
SOURCES = $(shell find src -name "*.java")
CLASSES = $(shell find src -name "*.class")
REMOVABLE_CLASSES = $(shell find src/net -type d | sed 's/\(.\+\)/\1\/*.class/')#$(shell find src -name "*.class" | sed -r 's/([$$])/*/')
endif

default: $(SOURCES)
	$(JC) $(JFLAGS) $(SOURCES)

clean: $(CLASSES)
ifeq ($(OS),Windows_NT)
	for %%a in ($(QUOTED_CLASSES)) do del "%%a"
else
	rm -rf $(REMOVABLE_CLASSES)
endif