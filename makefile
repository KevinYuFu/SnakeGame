# Note: I stole this from lectures (y)

# Make file for java application
NAME = "Snake"

all:
	@echo "Compiling..."
	javac -cp vecmath-1.5.1.jar *.java

run: all
	@echo "Running..."
	java -cp "vecmath-1.5.1.jar:." $(NAME) 60 10 

clean:
	rm -rf *.class
