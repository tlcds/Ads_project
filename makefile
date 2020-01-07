JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        Triplet.java \
        RBNode.java \
        RedBlackTree.java \
        MinHeap.java \
       	RisingCity.java


default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
