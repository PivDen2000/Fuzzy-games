# Fuzzy-games

This project focuses on applying the generalization principle to solve fuzzy matrix games using Java implementation. The primary objective is to solve linear programming problems with fuzzy numbers. To accomplish this, we have utilized the optimization capabilities of the Apache Commons Math library.

### Research Materials
The materials folder contains a collection of research papers and articles related to fuzzy matrix games with fuzzy numbers. These resources provide valuable insights into the theoretical foundations of the Fuzzy-games project and can help you deepen your understanding of the field.

In addition to research papers, the folder also include example of course work completed by me from the Faculty of Computer Science and Cybernetics at Taras Shevchenko National University of Kyiv. These course work offer practical applications of the Fuzzy-games project and can serve as a source of inspiration for further development.

### Prerequisites
- Java 20
- Apache Commons Math 3.6 or higher

### Installation

- Clone the repository from GitHub
- Download Apache Commons Math library from https://commons.apache.org/proper/commons-math/download_math.cgi
- Extract the downloaded folder and copy the commons-math3-3.6.1 into the lib folder of the project
- Open the project in your preferred IDE (e.g. Eclipse, IntelliJ IDEA)
- Add the commons-math3-3.6.1 folder to the project's build path

### Usage

To use the Fuzzy-games project, follow these steps:

- Create a file named input.txt and place it in the input_output folder.
- In the input.txt file, enter a matrix with fuzzy numbers following the example provided.
- Run the project.
- The program reads input.txt, parses data to a fuzzy matrix, creates systems of linear inequalities in alpha cuts, and solves them using FuzzySolver (number of alpha cuts can be adjusted).
- The result will be written to a file named output.txt in the input_output folder using FuzzyWriter.
- Additionally, a visualization graph of the points will be generated in SVG format with the name image.svg.

### Contributing
You are welcome to contribute to this project by submitting pull requests or raising issues related to bugs or feature requests.

### License
Copyright (c) 2023 Denys Piven