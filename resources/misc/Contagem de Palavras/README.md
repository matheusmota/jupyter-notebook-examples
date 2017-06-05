# analisador
This program receives two input texts, count the words with more than 3 characters for each one and return the 5 most frequent. It also returns all common words with more than 100 occurrences between the two texts, in alphabetical order.

Compile it with 'sbt package', then run with 'spark-submit --class Analisador target/scala-2.11/analisador_2.11-0.1.jar input1.txt input2.txt > output.txt'.